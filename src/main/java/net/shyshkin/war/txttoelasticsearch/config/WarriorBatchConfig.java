package net.shyshkin.war.txttoelasticsearch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.shyshkin.war.txttoelasticsearch.listener.ZipOperationsExecutionListener;
import net.shyshkin.war.txttoelasticsearch.mapper.WarriorMapper;
import net.shyshkin.war.txttoelasticsearch.model.WarriorDoc;
import net.shyshkin.war.txttoelasticsearch.model.WarriorTxt;
import net.shyshkin.war.txttoelasticsearch.writer.ElasticsearchItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;

import java.nio.charset.Charset;
import java.util.function.Function;

@Slf4j
@EnableBatchProcessing
@Configuration
@RequiredArgsConstructor
@Profile("txt-to-elasticsearch")
public class WarriorBatchConfig {

    private final JobBuilderFactory jobs;
    private final StepBuilderFactory steps;
    private final WarriorMapper warriorMapper;
    private final ElasticsearchItemWriter elasticWriter;

    @Value("${app.reader.txt.encoding}")
    private Charset charsetEncoding;

    @Bean
    Job readWarriorJob(ZipOperationsExecutionListener zipOperations) {
        return jobs.get("Read Warrior from txt file")
                .incrementer(new RunIdIncrementer())
                .listener(zipOperations)
                .start(readWarriorDataStep())
                .build();
    }

    private Step readWarriorDataStep() {
        return steps.get("Read Warrior Data and Save to Elasticsearch")
                .<WarriorTxt, WarriorDoc>chunk(1000)
                .reader(txtItemReader(null))
                .processor((Function<? super WarriorTxt, ? extends WarriorDoc>) warriorMapper::toDoc)
                .writer(elasticWriter)
                .build();
    }

    @Bean
    @StepScope
    FlatFileItemReader<WarriorTxt> txtItemReader(@Value("#{jobExecutionContext.get('inputFile')}") FileSystemResource resource) {
        return new FlatFileItemReaderBuilder<WarriorTxt>()
                .name("txtItemReader")
                .encoding(charsetEncoding.name())
                .linesToSkip(0)
                .resource(resource)
                .delimited()
                .delimiter("\t")
                .names("Full Name", "Birth Date", "Address", "District Number", "District Name")
                .targetType(WarriorTxt.class)
//                .maxItemCount(10)
                .build();
    }

}
