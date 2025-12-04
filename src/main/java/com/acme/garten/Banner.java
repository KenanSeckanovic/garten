package com.acme.garten;

// import module java.base; // NOSONAR
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.Objects;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.SpringVersion;

/// Banner als String-Konstante für den Start des Servers.
///
/// @author [Jürgen Zimmermann](mailto:Juergen.Zimmermann@h-ka.de)
@SuppressWarnings({
    "AccessOfSystemProperties",
    "CallToSystemGetenv",
    "UtilityClassCanBeEnum",
    "UtilityClass",
    "ClassUnconnectedToPackage"
})
final class Banner {

    // http://patorjk.com/software/taag/#p=display&f=Slant&t=kunde%202025.4.1
    private static final String FIGLET = """
            __                   __        ___   ____ ___   ________ __   ___
           / /____  ______  ____/ /__     |__ \\ / __ \\__ \\ / ____/ // /  <  /
          / //_/ / / / __ \\/ __  / _ \\    __/ // / / /_/ //___ \\/ // /_  / /
         / ,< / /_/ / / / / /_/ /  __/   / __// /_/ / __/____/ /__  __/ / /
        /_/|_|\\__,_/_/ /_/\\__,_/\\___/   /____/\\____/____/_____(_)/_/ (_)_/
        """;
    private static final String SERVICE_HOST = System.getenv("KUNDE_SERVICE_HOST");
    private static final String KUBERNETES = SERVICE_HOST == null
        ? "N/A"
        : "KUNDE_SERVICE_HOST=" + SERVICE_HOST + ", KUNDE_SERVICE_PORT=" + System.getenv("KUNDE_SERVICE_PORT");
    private static final String CLASSPATH_ORIG = System.getProperty("java.class.path");
    private static final String CLASSPATH = CLASSPATH_ORIG.length() > 120 ? "<very long>" : CLASSPATH_ORIG;

    /// Banner für den Server-Start.
    static final String TEXT = """

        $figlet
        (C) Juergen Zimmermann, Hochschule Karlsruhe
        Version             2025.4.1
        Spring Boot         $springBoot
        Spring Framework    $spring
        Java                $java
        Betriebssystem      $os
        Rechnername         $rechnername
        IP-Adresse          $ip
        Heap: Size          $heapSize
        Heap: Free          $heapFree
        Kubernetes          $kubernetes
        Username            $username
        JVM Locale          $locale
        OpenAPI             /swagger-ui.html /v3/api-docs.yaml
        CLASSPATH           $classpath
        """
        .replace("$figlet", FIGLET)
        .replace("$springBoot", SpringBootVersion.getVersion())
        .replace("$spring", Objects.requireNonNull(SpringVersion.getVersion()))
        .replace("$java", Runtime.version() + " - " + System.getProperty("java.vendor"))
        .replace("$os", System.getProperty("os.name"))
        .replace("$rechnername", getLocalhost().getHostName())
        .replace("$ip", getLocalhost().getHostAddress())
        .replace("$heapSize", Runtime.getRuntime().totalMemory() / (1024L * 1024L) + " MiB")
        .replace("$heapFree", Runtime.getRuntime().freeMemory() / (1024L * 1024L) + " MiB")
        .replace("$kubernetes", KUBERNETES)
        .replace("$username", System.getProperty("user.name"))
        .replace("$locale", Locale.getDefault().toString())
        .replace("$classpath", CLASSPATH);

    @SuppressWarnings("ImplicitCallToSuper")
    private Banner() {
    }

    private static InetAddress getLocalhost() {
        try {
            return InetAddress.getLocalHost();
        } catch (final UnknownHostException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
