package net.shyshkin.war.txttoelasticsearch.config.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

@Data
@Configuration
@ConfigurationProperties("app.web-service")
public class ApiServiceConfigData {

    private String baseUrl;
    private String citiesEndpoint;
    private MediaType acceptType;

}
