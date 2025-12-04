package com.acme.garten;

import com.acme.garten.dev.DevConfig;
import com.acme.garten.mail.MailProps;
import com.acme.garten.security.KeycloakProps;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import static com.acme.garten.Banner.TEXT;

/// Klasse mit der main-Methode für die Anwendung auf Basis von Spring Boot.
@SpringBootApplication(proxyBeanMethods = false)
@Import(DevConfig.class)
@EnableConfigurationProperties({MailProps.class, KeycloakProps.class})
@EnableJpaRepositories
@EnableAsync
public final class Application {
    private Application() {
    }

    /// Hauptprogramm, um den Microservice zu starten.
    ///
    /// @param args Evtl. zusätzliche Argumente für den Start des Microservice
    public static void main(final String... args) {
        new SpringApplicationBuilder(Application.class)
            .banner((_, _, out) -> out.println(TEXT))
            .run(args);
    }
}
