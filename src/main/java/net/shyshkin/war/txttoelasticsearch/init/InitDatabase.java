package net.shyshkin.war.txttoelasticsearch.init;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@RequiredArgsConstructor
public class InitDatabase implements CommandLineRunner {

    private final DataSource populationDataSource;

    @Value("jdbc/population-init.sql")
    private ClassPathResource initDb;

    @Override
    public void run(String... args) throws Exception {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(initDb);
        resourceDatabasePopulator.execute(populationDataSource);
    }
}
