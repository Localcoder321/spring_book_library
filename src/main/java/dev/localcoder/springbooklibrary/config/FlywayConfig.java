package dev.localcoder.springbooklibrary.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {
    @Bean(initMethod = "migrate")
    @FlywayDataSource
    public Flyway flyway() {
        return Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/library_db", "postgres", "postgres")
                .locations("classpath:db/migration").load();
    }
}
