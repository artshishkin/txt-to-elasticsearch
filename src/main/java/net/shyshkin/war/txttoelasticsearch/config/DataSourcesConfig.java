package net.shyshkin.war.txttoelasticsearch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourcesConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.population-datasource")
    DataSource populationDataSource() {
        return DataSourceBuilder
                .create()
                .build();
    }

}
