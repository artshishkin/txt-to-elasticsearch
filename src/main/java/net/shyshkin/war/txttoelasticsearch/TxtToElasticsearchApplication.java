package net.shyshkin.war.txttoelasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TxtToElasticsearchApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(TxtToElasticsearchApplication.class, args);
        System.exit(SpringApplication.exit(applicationContext));
    }

}
