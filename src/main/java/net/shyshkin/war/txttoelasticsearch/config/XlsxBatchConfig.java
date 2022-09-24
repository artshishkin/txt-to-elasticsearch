package net.shyshkin.war.txttoelasticsearch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;

@Slf4j
@EnableBatchProcessing
@Configuration
@RequiredArgsConstructor
@Profile("xlsx-to-db")
public class XlsxBatchConfig {

    private final JobBuilderFactory jobs;
    private final StepBuilderFactory steps;

    @Bean
    Job readWarriorJob() {
        return jobs.get("import XSLX file into db")
                .incrementer(new RunIdIncrementer())
                .start(readXlsxDataStep())
                .build();
    }

    @Bean
    Step readXlsxDataStep() {
        return steps.get("Read Xlsx file")
                .<PopulationXlsx, PopulationXlsx>chunk(100)
                .reader(xlsxPopulationReader(null))
                .writer(list -> list.forEach(item -> log.debug("{}", item)))
//                .faultTolerant()
//                .skip(FormulaParseException.class)
//                .skipPolicy(new AlwaysSkipItemSkipPolicy())
                .build();
    }

    @Bean
    @StepScope
    PoiItemReader<PopulationXlsx> xlsxPopulationReader(@Value("#{jobParameters['xlsxPopulationFile']}") FileSystemResource resource) {
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
            return PopulationXlsx.builder()
                    .sheetName(rowSet.getMetaData().getSheetName())
                    .age(row[0])
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
        };
    }

}
