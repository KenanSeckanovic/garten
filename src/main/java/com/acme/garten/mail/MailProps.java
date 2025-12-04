package com.acme.garten.mail;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/// Spring-Konfiguration für Properties app. mail
/// @param from Emailadresse des Absenders
/// @param sales Emailadresse des Vertriebs
@ConfigurationProperties(prefix = "app.mail")
public record MailProps(
    @DefaultValue("Theo Test <theo@test.de>")
    String from,

    @DefaultValue("Maxi Musterfrau <maxi.musterfrau@test.de>")
    String sales
) {
}
