package net.shyshkin.war.txttoelasticsearch.config;

import net.shyshkin.war.txttoelasticsearch.config.data.ApiServiceConfigData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient webClient(ApiServiceConfigData configData) {
        return WebClient.builder()
                .baseUrl(configData.getBaseUrl())
                .build();
    }
}
