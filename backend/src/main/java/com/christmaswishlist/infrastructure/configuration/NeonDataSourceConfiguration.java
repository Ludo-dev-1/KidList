package com.christmaswishlist.infrastructure.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Primary;

@Configuration(proxyBeanMethods = false)
public class NeonDataSourceConfiguration {
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.hikari")
    HikariDataSource dataSource(DataSourceProperties properties, Environment environment) {
        String databaseUrl = environment.getProperty("DATABASE_URL");
        String explicitJdbcUrl = properties.getUrl();
        if (databaseUrl != null && !databaseUrl.isBlank()
                && (explicitJdbcUrl == null || explicitJdbcUrl.isBlank())) {
            NeonDatabaseUrl connection = NeonDatabaseUrl.parse(databaseUrl);
            return DataSourceBuilder.create()
                    .type(HikariDataSource.class)
                    .url(connection.jdbcUrl())
                    .username(connection.username())
                    .password(connection.password())
                    .build();
        }
        if (explicitJdbcUrl == null || explicitJdbcUrl.isBlank()) {
            properties.setUrl("jdbc:postgresql://localhost:5432/christmas_wishlist");
        }
        return properties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }
}
