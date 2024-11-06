# Starter für das LF08 Projekt

Dieses Projekt kann Spuren von ChatGpt, Nüssen und Milchpulver enthalten.

Martin und Eren haben viel in Pair Programming gearbeitet. Da Tobias probleme mit Git hatte,
haben wir unsere änderungen via .zip ausgetauscht. 
Dadurch sind so gut wie alle commits von Eren durchgeführt worden. 

Um auf den Service zugreifen zu können, muss der Bearer Token angefordert werden und einmal im
Employee Service und diesem Service in Swagger Authentifiziert werden.

Um erkenntlich zu machen, wer an welchem Code gearbeitet hat, wurde dieser mit Kommentaren
versehen. 

## Extra Arbeit
### --- In den Klassen ProjektEntity und ProjektService ---
* Projekten können numerische Grenzen für Qualifikationen zugeordnet werden.
* Projekte haben die möglichkeit zu Messen wie viele Mitarbeiter mit gewissen Qualifikationen vorhanden sind.
* Es gibt keine Kontrolle, ob diese Grenze überschritten oder unterschritten wird.

## Missing/Unfinished

### Tests
### Jira Pflege

## Requirements
* Docker https://docs.docker.com/get-docker/
* Docker compose (bei Windows und Mac schon in Docker enthalten) https://docs.docker.com/compose/install/

## Endpunkt
```
http://localhost:8080
```
## Swagger
```
http://localhost:8080/swagger
```


# Postgres
### Terminal öffnen
für alles gilt, im Terminal im Ordner docker/local sein
```bash
cd docker/local
```
### Postgres starten
```bash
docker compose up
```
Achtung: Der Docker-Container läuft dauerhaft! Wenn er nicht mehr benötigt wird, sollten Sie ihn stoppen.

### Postgres stoppen
```bash
docker compose down
```

### Postgres Datenbank wipen, z.B. bei Problemen
```bash
docker compose down
docker volume rm local_lf8_starter_postgres_data
docker compose up
```

### Intellij-Ansicht für Postgres Datenbank einrichten
```bash
1. Lasse den Docker-Container mit der PostgreSQL-Datenbank laufen
2. im Ordner resources die Datei application.properties öffnen und die URL der Datenbank kopieren
3. rechts im Fenster den Reiter Database öffnen
4. In der Database-Symbolleiste auf das Datenbanksymbol mit dem Schlüssel klicken
5. auf das Pluszeichen klicken
6. Datasource from URL auswählen
7. URL der DB einfügen und PostgreSQL-Treiber auswählen, mit OK bestätigen
8. Username lf8_starter und Passwort secret eintragen (siehe application.properties), mit Apply bestätigen
9. im Reiter Schemas alle Häkchen entfernen und lediglich vor lf8_starter_db und public Häkchen setzen
10. mit Apply und ok bestätigen 
```
# Keycloak

### Keycloak Token
1. Auf der Projektebene [GetBearerToken.http](GetBearerToken.http) öffnen.
2. Neben der Request auf den grünen Pfeil drücken
3. Aus dem Reponse das access_token kopieren