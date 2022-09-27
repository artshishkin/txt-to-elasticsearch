package net.shyshkin.war.txttoelasticsearch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.shyshkin.war.txttoelasticsearch.exception.WrongFormatException;
import net.shyshkin.war.txttoelasticsearch.listener.ZipOperationsExecutionListener;
import net.shyshkin.war.txttoelasticsearch.mapper.PopulationMapper;
import net.shyshkin.war.txttoelasticsearch.model.PopulationEntity;
import net.shyshkin.war.txttoelasticsearch.model.PopulationXlsx;
import net.shyshkin.war.txttoelasticsearch.partitioner.RangePartitioner;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.streaming.StreamingXlsxItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import javax.sql.DataSource;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
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

    @Value("${app.range-partitioner.expected-max-size:15000}")
    private Integer partitionerExpectedMaxSize;

    @Bean
    Job readPopulationJob(ZipOperationsExecutionListener zipOperations) {
        return jobs.get("import XSLX file into db")
                .incrementer(new RunIdIncrementer())
                .listener(zipOperations)
                .start(partitionStep())
                .build();
    }

    @Bean
    Step partitionStep() {
        return steps.get("partitionStep")
                .partitioner(readXlsxDataStep().getName(), new RangePartitioner(partitionerExpectedMaxSize))
                .step(readXlsxDataStep())
                .gridSize(10) //HikariCP default max connection pool size - may be optimal
                .taskExecutor(new SimpleAsyncTaskExecutor("async-"))
                .build();
    }

    @Bean
    Step readXlsxDataStep() {
        return steps.get("Read Xlsx file")
                .<PopulationXlsx, PopulationEntity>chunk(100)
                .reader(xlsxPopulationReader(null, null, null))
                .processor((Function<PopulationXlsx, PopulationEntity>) populationMapper::toEntity)
                .writer(jdbcItemWriter(null))
//                .writer(regionIdLoggingItemWriter())
//                .writer(loggingItemWriter())
                .faultTolerant()
                .skipPolicy((t, skipCount) -> WrongFormatException.isCauseOf(t))
                .build();
    }

    @Bean
    @StepScope
    StreamingXlsxItemReader<PopulationXlsx> xlsxPopulationReader(
            @Value("#{jobExecutionContext.get('inputFile')}") FileSystemResource resource,
            @Value("#{stepExecutionContext['minValue']}") Integer minValue,
            @Value("#{stepExecutionContext['maxValue']}") Integer maxValue) {
        log.debug("Creating bean xlsxPopulationReader");
        return new StreamingXlsxItemReader<>() {{
            setName("xlsxPopulationReader");
            setResource(resource);
            setLinesToSkip(6);
            setCurrentItemCount(minValue);
            setMaxItemCount(maxValue);
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
            } catch (Exception e) {
                throw new WrongFormatException(e);
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

    private ItemWriter<PopulationEntity> regionIdLoggingItemWriter() {
        Set<String> printed = ConcurrentHashMap.newKeySet();
        return entities -> {
            entities.forEach(entity -> {
                if (!printed.contains(entity.getRegionId())) {
                    log.debug("Writing {}", entity);
                    printed.add(entity.getRegionId());
                }
            });
        };
    }

    private ItemWriter<PopulationEntity> loggingItemWriter() {
        return entities -> entities.forEach(entity -> log.debug("Writing -> {}", entity));
    }

}
