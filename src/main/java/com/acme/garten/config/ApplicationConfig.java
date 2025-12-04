package com.acme.garten.config;

import org.jspecify.annotations.Nullable;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

/// Konfigurationsklasse für die Anwendung bzw. den Microservice
public final class ApplicationConfig {
    /// Konstruktor mit package private für Spring
    ApplicationConfig() {
    }

    /// Registrierung für GraalVM:
    /// - `PEM`- und `CRT`-Dateien für TLS
    /// - SQL-Skripte für Flyway
    public static class GraalVmHints implements RuntimeHintsRegistrar {
        /// Konstruktor mit package private für Spring
        GraalVmHints() {
        }

        @Override
        public void registerHints(final RuntimeHints hints, @Nullable final ClassLoader classLoader) {
            hints.resources()
                .registerPattern("*.pem")
                .registerPattern("*.crt")
                .registerPattern("*.sql");
        }
    }
}
