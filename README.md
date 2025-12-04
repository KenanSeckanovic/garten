# Garten – RESTful Webservice (Hochschul-Projekt)

# Projektbeschreibung
Dieses Projekt entstand im Rahmen des Hochschulkurses *Softwarearchitektur* (HKA) 
und dient als Lern- und Übungsprojekt für den Aufbau eines RESTful Webservices. 
Ziel war es, praktische Erfahrung mit moderner Webserver-Architektur, 
Backend-Entwicklung in Java sowie dem Einsatz von Docker, Kubernetes, Datenbanken und Build-Tools zu sammeln.

Der Webserver verarbeitet grundlegende HTTP-Requests (GET, POST, PUT, DELETE) und speichert Daten in einer relationalen Datenbank. 
Das Projekt kann als Container gestartet werden und über Docker bzw. Docker-Compose ausgeführt werden. Eine Ausführung in Kubernetes ist ebenfalls möglich.


# Hinweis:  
Der gesamte Quellcode im Ordner src/main wurde von mir im Rahmen des Projekts implementiert.  
Ein Großteil der Konfigurationsdateien (Docker, Helm, Compose, Projektgrundstruktur) wurde im Kurs bereitgestellt und von mir angepasst bzw. erweitert.


# Features
- RESTful Webservice zur Verarbeitung von GET, POST, PUT und DELETE Requests  
- Persistente Speicherung der Daten in einer PostgreSQL-Datenbank  
- Containerisierte Architektur mit Docker & Docker-Compose  
- Grundlegende Kubernetes-Deployment-Struktur (Helm)  
- Java-Backend (Java 24)  
- Saubere Code-Struktur mit Fokus auf Softwarearchitektur-Prinzipien  
- Fehlerbehandlung & Logging  
- Einsatz von Postman zum Testen der Endpoints
  

# Technologien & Tools
- **Java 24**  
- **Docker & Docker-Compose**  
- **Kubernetes** (theoretisch ausführbar)  
- **Helm**  
- **PostgreSQL**  
- **Gradle**  
- **REST / HTTP**  
- **Postman** für API-Tests  


# Bekannte Probleme / Hinweise
- Das Projekt ist aktuell mit **Java 24** spezifiziert, jedoch erwartet das Build-Setup an einigen Stellen noch **Java 21**.  
  Dieses Verhalten liegt an Teilen der bereitgestellten Build-Konfiguration.  
  Eine Anpassung der Gradle-Version oder des Toolchains-Eintrags kann das Problem lösen.
- Das Projekt befindet sich im **Lern- und Demonstrationsstatus** und ist nicht als produktionsreifer Webservice gedacht.


# Start (Gradle)
Das Projekt wurde zur Entwicklungszeit mit Gradle asugeführt.
