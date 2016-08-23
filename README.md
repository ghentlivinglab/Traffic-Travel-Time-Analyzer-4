# Project Info [Eng]

The city of Ghent is setting up a traffic control center and wants to know:
- traveltimes on the most important routes in and around the city
- impact of events, roadworks, accidents, weather, time of year, .. on those traveltimes
- the differences in travel times between different providers

A project was started with the engineering faculty of Ghent University in 2016. Four groups of students built a monitoring tool to acquire and analyze travel time from: Waze, Coyote, Tomtom and Google Maps. The tool contains a map with real-time traveltimes, a comparison tool to compare traveltimes of different suppliers, a comparison tool to compare travel times between two given periodes (e.g. period during roadworks, and a 'normal' period), and an API.

All the source code from Group 4 can be found in this repository and can be used freely, please mention the names of the authors when further elaborating on the code: 1. Aaron Mousavi 2. Dwight Kerkhove 4. Niels Verbeeck 5. Thomas Clauwaert 6. Tomas Bolckmans. City of Ghent is very thankful towards these enthusiast en competent engineers for the great work!

For more information: contact verkeer@stad.gent


# Indeling repository

* /Analyse: Bevat alle documentatie ivm analyse zoals use case diagrammen enz.
* /applicatie/Scrapers: Bevat alle perl scripts die worden uitgevoerd om de verschillende providers te scrapen
* /applicatie/Scripts: Bevat perl scripts en SQL scripts die éénmalig zijn uitgevoerd om o.a de trajecten pdf naar insert statements om te zetten.
* /applicatie/verkeer*: De verschillende netbeans projecten van het project. Het 'verkeer' project is het overkoepelend project.
* /db: bevat een dump van de database
* /docs: bevat de aangeleverde documentatie
* /build: Bevat de war file van de website en de jar file van de verkeer polling service.

# Opzetten test omgeving

Er wordt gebruik gemaakt van een databank, dus de database die je onder /db kan vinden moet eerst gerestored worden naar de databank 'verkeer'.

Zowel in de war van de web app als jar file van de polling service zit een application.conf bestand dat de configuratie bevat. Hierin staat o.a de API keys en het pad naar de scrapers map. 
```
general.inProduction=true
APIKeys.TomTom = "5j7n539vbsbf6frb7kwzxtc6"
APIKeys.Here.AppId = "rT5jJhpUtSJEYrkEXFSd"
APIKeys.Here.AppCode = "MDfihaAj-_kw7eMW2dIb-A"
APIKeys.Google.AppCode = "AIzaSyDSFudE3RU-uIFK4ID8i32bnq-rGRRtpGw"
APIKeys.BingMaps = "Al2KtFQ_rRUJ7fHbjzEMRiYMIa0Kz-XAcrg47_u1wduJ_BbNY8rcwP3WoCusE5-n"
ScrapePath = "/scrapers/"
PerlPath = "/usr/bin/perl"
```
Er is ook een database-prod.conf bestand dat de database configuratie bevat (database-dev.conf wordt gebruikt wanneer general.inProduction=false is aangegeven):
```
database {  
      user = "root"
      password = ""
      connection = "//localhost:3306/verkeer"
}
```

Zodra de juiste configuratie is ingesteld volstaat het de war te deployen op glassfish en naar de website te surfen. Om de polling service te starten kan je via de command line hetvolgende uitvoeren:
```
java -jar VerkeerPollService-1.0-SNAPSHOT.jar
```

Vanuit netbeans kan je ook het project starten, alle projecten zijn maven projecten die je kan openen in netbeans. Er is ook een overkoepelend project verkeer dat gebruikt kan worden om 'build with dependencies' uit te voeren. Daarna volstaat het ofwel VerkeerWeb of VerkeerPollService te starten

De scrapers kunnen ook getest worden aan de hand van 2 bash scripts, namelijk testscrapers.sh en testpoiscrapers.sh. Beide bevinden zich in de map Scrapers.

Een volledig installatiehandleiding die alle stappen doorloopt kan je ook terugvinden in het projectdossier.

# Documentatie

[Projectdossier](https://github.ugent.be/iii-vop2016/verkeer-4/blob/master/Analyse/projectdossier-vop.pdf)

[Algemene Analyse](https://github.ugent.be/iii-vop2016/verkeer-4/wiki/Analyse)

[Structuur & requirements van scrapers](https://github.ugent.be/iii-vop2016/verkeer-4/wiki/Documentatie-Scrapers)




# Groepsleden

1. Aaron Mousavi
2. Dwight Kerkhove
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
