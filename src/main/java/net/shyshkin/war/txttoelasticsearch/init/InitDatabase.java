package net.shyshkin.war.txttoelasticsearch.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("xlsx-to-db")
public class InitDatabase {

    private final DataSource populationDataSource;

    @Value("jdbc/population-init.sql")
    private ClassPathResource initDb;

    @Value("jdbc/population-data.sql")
    private ClassPathResource dataDb;

    @EventListener(ApplicationStartedEvent.class)
    public void applicationStarted() {
        log.debug("Start initializing database");
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(initDb);
        resourceDatabasePopulator.addScript(dataDb);
        resourceDatabasePopulator.execute(populationDataSource);
        log.debug("Database initialization completed");
    }
}
