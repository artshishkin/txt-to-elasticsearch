package net.shyshkin.war.txttoelasticsearch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.shyshkin.war.txttoelasticsearch.exception.WrongAgeFormatException;
import net.shyshkin.war.txttoelasticsearch.listener.ZipOperationsExecutionListener;
import net.shyshkin.war.txttoelasticsearch.mapper.PopulationMapper;
import net.shyshkin.war.txttoelasticsearch.model.PopulationEntity;
import net.shyshkin.war.txttoelasticsearch.model.PopulationXlsx;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;
import java.util.function.Function;

@Slf4j
@EnableBatchProcessing
@Configuration
@RequiredArgsConstructor
@Profile("xlsx-to-db")
public class XlsxBatchConfig {

    private final JobBuilderFactory jobs;
    private final StepBuilderFactory steps;
    private final PopulationMapper populationMapper;

    @Bean
    Job readPopulationJob(ZipOperationsExecutionListener zipOperations) {
        return jobs.get("import XSLX file into db")
                .incrementer(new RunIdIncrementer())
                .listener(zipOperations)
                .start(readXlsxDataStep())
                .build();
    }

    @Bean
    Step readXlsxDataStep() {
        return steps.get("Read Xlsx file")
                .<PopulationXlsx, PopulationEntity>chunk(10)
                .reader(xlsxPopulationReader(null))
                .processor((Function<PopulationXlsx, PopulationEntity>) populationMapper::toEntity)
//                .writer(list -> list.forEach(item -> log.debug("{}", item)))
                .writer(jdbcItemWriter(null))
                .faultTolerant()
                .skipPolicy((t, skipCount) -> t instanceof WrongAgeFormatException || t.getCause() instanceof WrongAgeFormatException)
                .build();
    }

    @Bean
    @StepScope
    PoiItemReader<PopulationXlsx> xlsxPopulationReader(@Value("#{jobExecutionContext.get('inputFile')}") FileSystemResource resource) {
        return new PoiItemReader<PopulationXlsx>() {{
            setName("xlsxPopulationReader");
            setResource(resource);
            setLinesToSkip(6);
            setMaxItemCount(10);
            setRowMapper(populationRowMapper());
        }};
    }

    @Bean
    RowMapper<PopulationXlsx> populationRowMapper() {
        return rowSet -> {
            String[] row = rowSet.getCurrentRow();
            String age = row[0];
            if (age.contains("80 и")) age = "80";
            for (int i = 1; i < row.length; i++) {
                row[i] = row[i].replace("-", "0");
            }
            try {
                return PopulationXlsx.builder()
                        .sheetName(rowSet.getMetaData().getSheetName())
                        .age(Integer.parseInt(age))
                        .allMenWomen(Long.parseLong(row[1]))
                        .allMen(Long.parseLong(row[2]))
                        .allWomen(Long.parseLong(row[3]))
                        .cityMenWomen(Long.parseLong(row[4]))
                        .cityMen(Long.parseLong(row[5]))
                        .cityWomen(Long.parseLong(row[6]))
                        .countryMenWomen(Long.parseLong(row[7]))
                        .countryMen(Long.parseLong(row[8]))
                        .countryWomen(Long.parseLong(row[9]))
                        .build();
            } catch (NumberFormatException e) {
                throw new WrongAgeFormatException(e);
            }
        };
    }

    @Bean
    @StepScope
    JdbcBatchItemWriter<PopulationEntity> jdbcItemWriter(DataSource populationDataSource) {

        return new JdbcBatchItemWriterBuilder<PopulationEntity>()
                .dataSource(populationDataSource)
                .beanMapped()
                .sql("insert into population (region_id, age, men, women) VALUES (:regionId,:age,:men,:women)")
                .build();
    }

}
