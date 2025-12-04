package com.acme.garten.mail;

import com.acme.garten.entity.Garten;
import jakarta.mail.internet.InternetAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import static jakarta.mail.Message.RecipientType.TO;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

/// Mail-Client
@Service
@SuppressWarnings("ClassNamePrefixedWithPackageName")
public class MailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    /// Objekt für Jakarta Mail, um E-Mails zu verschicken
    private final JavaMailSender mailSender;

    /// Injizierte Properties für Spring Mail
    private final MailProps mailProps;

    /// Mailserver
    @Value("${spring.mail.host}")
    @SuppressWarnings("NullAway.Init")
    private String mailhost;

    /// Konstruktor mit package private für Constructor Injection bei Spring
    ///
    /// @param mailSender Injiziertes Objekt für Spring Mail
    /// @param mailProps Injiziertes Property-Objekt für Spring Mail
    MailService(final JavaMailSender mailSender, final MailProps mailProps) {
        this.mailSender = mailSender;
        this.mailProps = mailProps;
    }

    /// E-Mail senden, dass es einen neuen Garten gibt
    ///
    /// @param neuerGarten Das Objekt des neuen Gartens
    @Async
    public void send(final Garten neuerGarten) {
        @SuppressWarnings("LambdaBodyLength")
        final MimeMessagePreparator preparator = mimeMessage -> {
            final var from = new InternetAddress(mailProps.from());
            mimeMessage.setFrom(from);
            final var to = new InternetAddress(mailProps.sales());
            mimeMessage.setRecipient(TO, to);
            mimeMessage.setSubject("Neuer Garten " + neuerGarten.getId());
            final var body = "<strong>Neuer Garten:</strong> <em>" + neuerGarten.getName() + "</em>";
            mimeMessage.setText(body);
            mimeMessage.setHeader(CONTENT_TYPE, TEXT_HTML_VALUE);

            LOGGER.trace("send: Thread-ID={}, mailhost={}, from={}, to={}, body={}", Thread.currentThread().threadId(),
                mailhost, from, to, body);
        };

        try {
            mailSender.send(preparator);
        } catch (MailSendException | MailAuthenticationException _) {
            LOGGER.warn("Email nicht gesendet: Ist der Mailserver {} erreichbar?", mailhost);
        }
    }
}
