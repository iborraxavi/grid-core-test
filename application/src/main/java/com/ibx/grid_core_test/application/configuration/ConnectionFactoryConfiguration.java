package com.ibx.grid_core_test.application.configuration;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
public class ConnectionFactoryConfiguration {

    @Bean
    public ConnectionFactoryInitializer initializer(final ConnectionFactory connectionFactory) {
        final ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);

        final CompositeDatabasePopulator compositeDatabasePopulator = new CompositeDatabasePopulator();
        compositeDatabasePopulator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("db/schema.sql")));
        compositeDatabasePopulator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("db/data.sql")));
        initializer.setDatabasePopulator(compositeDatabasePopulator);
        return initializer;
    }
}
