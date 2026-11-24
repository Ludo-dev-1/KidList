package com.christmaswishlist.infrastructure.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class NeonDatabaseUrlTest {
    @Test
    void convertsNeonConnectionStringToJdbcUrl() {
        NeonDatabaseUrl connection = NeonDatabaseUrl.parse(
                "postgresql://user%2Bname:p%40ss%3Aword@db.example:5433/neondb"
                        + "?sslmode=require&channel_binding=require");

        assertEquals(
                "jdbc:postgresql://db.example:5433/neondb?sslmode=require&channelBinding=require",
                connection.jdbcUrl());
        assertEquals("user+name", connection.username());
        assertEquals("p@ss:word", connection.password());
    }

    @Test
    void rejectsConnectionStringsWithoutCredentials() {
        assertThrows(IllegalArgumentException.class,
                () -> NeonDatabaseUrl.parse("postgresql://db.example/neondb"));
    }

    @Test
    void acceptsQuotedConnectionStringsFromEnvFiles() {
        NeonDatabaseUrl connection = NeonDatabaseUrl.parse(
                "\"postgresql://user:password@db.example/neondb?sslmode=require\"");

        assertEquals("jdbc:postgresql://db.example:5432/neondb?sslmode=require", connection.jdbcUrl());
    }
}
