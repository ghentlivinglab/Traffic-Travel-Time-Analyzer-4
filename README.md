# Indeling repository
/Analyse: Bevat alle documentatie ivm analyse zoals use case diagrammen enz.
/applicatie/Scrapers: Bevat alle perl scripts die worden uitgevoerd om de verschillende providers te scrapen
/applicatie/Scripts: Bevat perl scripts en SQL scripts die éénmalig zijn uitgevoerd om o.a de trajecten pdf naar insert statements om te zetten.
/applicatie/verkeer*: De verschillende netbeans projecten van het project. Het 'verkeer' project is het overkoepelend project.
/db: bevat een dump van de database
/docs: bevat de aangeleverde documentatie
/build: [//TODO] Bevat de war file van de website en de jar file van de verkeer polling service.

# Opzetten test omgeving
Zowel in de war als jar file zit een application.conf bestand dat de configuratie bevat. Hierin staat o.a de API keys en het pad naar de scrapers map. 
Er is ook een database-dev.conf bestand dat de database configuratie bevat (wanneer general.inProduction = false).
// TODO verder uitschrijven


# Groepsleden

1. Aaron Mousavi
2. Dwight Kerkhove
3. Jaron Vervynckt
4. Niels Verbeeck
5. Thomas Clauwaert
6. Tomas Bolckmans

# Oprichting van het regionaal verkeerscentrum Gent
**Klant** : Mobiliteitsbedrijf stad gent http://www.verkeer.gent

Het Mobiliteitsbedrijf van de stad gent is sinds 2014 bezig met het opzetten van een regionaal
verkeerscentrum. Het is de bedoeling dat op termijn het verkeer in de regio constant gemonitored
wordt, op semi-automatische basis op normale werkdagen en bemand tijdens piekmomenten en
evenementen. Tijdens de week is het de bedoeling dat onverwachte incidenten, calamiteiten of
significante verhogingen van de reistijden automatisch gesignaleerd worden aan de verantwoor-
delijke, die dan de nodige acties kan ondernemen. De gegevens zouden ook constant beschikbaar
zijn voor het publiek via een website, sociale media en open data. Op die manier kunnen mensen
de beste route en het beste moment kiezen om hun verplaatsingen te maken in de regio.
Momenteel loopt al een proefproject via het platform van de Gentse Start-up Waylay. Gegevens
worden automatisch verwerkt, tweets en sms’en worden uitgestuurd als er relevante informatie
beschikbaar is voor de weggebruiker in en rond Gent. Het proefproject kan geraadpleegd worden
op https://twitter.com/VerkeerGentB en http://www.verkeer.gent

Een belangrijke parameter bij degelijk verkeersmanagement zijn reistijden. Die kunnen ener-
zijds gebruikt worden om de impact van bijvoorbeeld wegenwerken en beleidsmaatregelen te
evalueren. Anderzijds worden ze real-time gedeeld met de weggebruikers en / of worden er alerts
uitgestuurd als de reistijd op bepaalde trajecten buitengewoon lang wordt. Op die manier kunnen
mensen hun verplaatsing via een andere route, een ander moment of een ander transportmiddel
maken.

Er zijn tegenwoordig heel wat providers van reistijden (Google, Here, Waze, TomTom, Flow, ...).
De reistijden worden meestal berekend via ’floating car data’. Doordat de locatie en snelheid van
een hele hoop voertuigen geaggregeerd wordt per wegsegment, kunnen reistijden en vertragingen
berekend worden. De locatie wordt meestal verzameld via de GPS, smartphone of tracker die
onder aan de wagen bevestigd is. Daarnaast loopt er een proefproject met de Universiteit Gent
waarbij de reistijden op de stadsring (R40) gemeten worden aan de hand van bluetooth-scanners.
Deze registreren de unieke adressen van headsets en carkits op verschillende plaatsen en bereke-
nen zo de reistijden.

Er zijn veel verschillende aanbieders van reistijden, er zit heel veel verschil op de prijs en kwaliteit
van de aangeboden diensten. Stad Gent en het Mobiliteitsbedrijf willen uiteraard de best moge-
lijke gegevens verkrijgen aan de best mogelijke prijs. Daarom zouden we graag een testproject
opzetten waarbij we de reistijden van verschillende providers vergelijken.
De bedoeling van dit project is om de API van verschillende aanbieders te gaan pollen door een
aantal routes door te geven en reistijden terug te vragen. Dit op een manier dat de verkregen
resultaten goed vergelijkbaar zijn met elkaar.
