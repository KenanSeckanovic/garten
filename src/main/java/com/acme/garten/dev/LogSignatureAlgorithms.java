package com.acme.garten.dev;

import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import static org.springframework.context.annotation.Bean.Bootstrap.BACKGROUND;

/// Beim ApplicationReadyEvent werden Informationen für die Entwickler/innen im Hinblick auf Security (-Algorithmen)
/// protokolliert. Da es viele Algorithmen gibt und die Ausgabe lang wird, wird diese Funktionalität nur mit dem
/// Profile logSecurity und nicht allgemein verwendet
sealed interface LogSignatureAlgorithms permits DevConfig {
    /// Bean-Definition, um einen _Listener_ bereitzustellen, damit die im JDK vorhandenen Signature-Algorithmen
    /// aufgelistet werden
    ///
    /// @return Listener für die Ausgabe der Signature-Algorithmen
    @Bean(bootstrap = BACKGROUND)
    @Profile("logSignature")
    @SuppressWarnings("LambdaBodyLength")
    default ApplicationListener<ApplicationReadyEvent> logSignatureAlgorithms() {
        final var log = LoggerFactory.getLogger(LogSignatureAlgorithms.class);
        return _ -> Arrays
            .stream(Security.getProviders())
            .forEach(provider -> logSignatureAlgorithms(provider, log));
    }

    @SuppressWarnings("PMD.AvoidLiteralsInIfCondition")
    private void logSignatureAlgorithms(final Provider provider, final Logger log) {
        provider
            .getServices()
            .forEach(service -> {
                if ("Signature".contentEquals(service.getType())) {
                    log.debug("{}", service.getAlgorithm());
                }
            });
    }
}
