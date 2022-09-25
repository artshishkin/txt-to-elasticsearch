package net.shyshkin.war.txttoelasticsearch.init;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

//@Component
@RequiredArgsConstructor
@Profile("xlsx-to-db")
public class InitDatabase implements CommandLineRunner {

    private final DataSource populationDataSource;

    @Value("jdbc/population-init.sql")
    private ClassPathResource initDb;

    @Value("jdbc/population-data.sql")
    private ClassPathResource dataDb;

    @Override
    public void run(String... args) throws Exception {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(initDb);
        resourceDatabasePopulator.addScript(dataDb);
        resourceDatabasePopulator.execute(populationDataSource);
    }
}
