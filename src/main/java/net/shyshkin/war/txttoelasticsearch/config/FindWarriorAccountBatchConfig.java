package net.shyshkin.war.txttoelasticsearch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.shyshkin.war.txttoelasticsearch.mapper.WarriorMapper;
import net.shyshkin.war.txttoelasticsearch.repository.CityRepository;
import net.shyshkin.war.txttoelasticsearch.repository.WarriorAccountRepository;
import net.shyshkin.war.txttoelasticsearch.repository.WarriorRepository;
import net.shyshkin.war.txttoelasticsearch.service.WebApiService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import reactor.core.publisher.Mono;

@Slf4j
@EnableBatchProcessing
@Configuration
@RequiredArgsConstructor
@Profile("find-warrior-account")
public class FindWarriorAccountBatchConfig {

    private final JobBuilderFactory jobs;
    private final StepBuilderFactory steps;

    private final WebApiService webApiService;
    private final CityRepository cityRepository;
    private final WarriorRepository warriorRepository;
    private final WarriorAccountRepository warriorAccountRepository;
    private final WarriorMapper warriorMapper;

    @Bean
    Job findWarriorAccountJob() {
        return jobs.get("Find warrior Account and write it to Elasticsearch")
                .incrementer(new RunIdIncrementer())
                .start(readWarriorsFindCityAndAccountAndPopulateToElasticsearch(null, null))
                .build();
    }

    @Bean
    @JobScope
    Step readWarriorsFindCityAndAccountAndPopulateToElasticsearch(@Value("#{jobParameters['warriorStartIndex']}") Long startIndex,
                                                                  @Value("#{jobParameters['warriorCount']}") final Long count) {

        return steps.get("Find warrior Account and write it to Elasticsearch")
                .tasklet((contribution, chunkContext) -> {
                    var savedCount = warriorRepository.findAll()
                            .skip(startIndex)
                            .take(count)
                            .map(warriorMapper::toDocWithAccount)
                            .doOnNext(warriorAccount -> log.debug("Processing {}", warriorAccount))
                            .flatMap(warriorAccount -> Mono.just(warriorAccount.getAddress())
                                    .map(address -> address.replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}]", " "))
                                    .flatMap(cityRepository::findCityMatchingAddress)
                                    .doOnNext(city -> {
                                        warriorAccount.setCity(city);
                                        log.debug("Found city: {} for {}", city, warriorAccount);
                                    })
                                    .thenReturn(warriorAccount)
                            )
                            .buffer(1000)
                            .flatMap(warriorAccountRepository::saveAll)
                            .count()
                            .block();

                    log.debug("Total saved warriors count: {}", savedCount);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}
