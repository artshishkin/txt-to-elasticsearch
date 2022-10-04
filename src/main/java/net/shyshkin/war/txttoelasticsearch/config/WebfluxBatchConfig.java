package net.shyshkin.war.txttoelasticsearch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.shyshkin.war.txttoelasticsearch.repository.CityRepository;
import net.shyshkin.war.txttoelasticsearch.service.WebApiService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@EnableBatchProcessing
@Configuration
@RequiredArgsConstructor
@Profile("webservice-to-elasticsearch")
public class WebfluxBatchConfig {

    private final JobBuilderFactory jobs;
    private final StepBuilderFactory steps;

    private final WebApiService webApiService;
    private final CityRepository cityRepository;

    @Bean
    Job readCityJob() {
        return jobs.get("Read Cities from web service and write it to Elasticsearch")
                .incrementer(new RunIdIncrementer())
                .start(readCitiesFromWebServiceAndPopulateToElasticsearch())
                .build();
    }

    @Bean
    Step readCitiesFromWebServiceAndPopulateToElasticsearch() {
        return steps.get("Read Cities Data and Save to Elasticsearch")
                .tasklet((contribution, chunkContext) -> {
                    var last = webApiService.getCities()
                            .buffer(1000)
                            .flatMap(cityRepository::saveAll)
                            .blockLast();
                    log.debug("Last city: {}", last);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}
