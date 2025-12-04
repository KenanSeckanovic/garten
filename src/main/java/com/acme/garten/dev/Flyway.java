package com.acme.garten.dev;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;

/// Migrationsstrategie für _Flyway_ im Profile `dev`: Tabellen, Indexe etc. löschen und dann neu aufbauen
sealed interface Flyway permits DevConfig {
    /// Bean-Definition, um eine Migrationsstrategie für _Flyway_ im Profile `dev` bereitzustellen, so dass zuerst alle
    /// Tabellen, Indexe etc. gelöscht und dann neu aufgebaut werden
    ///
    /// @return `FlywayMigrationStrategy`
    @Bean
    default FlywayMigrationStrategy flywayMigrationStrategy() {
        return flyway -> {
            // Loeschen aller DB-Objekte im Schema: Tabellen, Indexe, Stored Procedures, Trigger, Views, ...
            // insbesondere die Tabelle flyway_schema_history
            flyway.clean();
            // Start der DB-Migration
            flyway.migrate();
        };
    }
}
