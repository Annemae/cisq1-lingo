# Vulnerability Analysis

## A1:2017 Injection

### Descriptie
_Wat houdt de vulnerability in?_

Injection gaat over de mogelijkheid om data
door te geven aan een interpreter met kwaadaardige intenties. Code wordt
als het ware _geinjecteerd_ in een applicatie, zodat het vervolgens door de applicatie 
verwerkt wordt en complicaties veroorzaakt. 

Het kan data loss, data corruption, disclosure (leaking to third parties), loss of
accountability, denial of access en host takeover veroorzaken.

### Risico
_Hoe groot is het risico voor deze kwetsbaarheid binnen het project?_

Het is een groot risico binnen dit project, omdat de user inputs gelijk worden gebruikt.
Daarnaast is er ook geen validatie, filtering en sanitization van de user inputs.

_Wat als we authenticatie en autorisatie toevoegen?_

Als je authenticatie en autorisatie toevoegt kan je ervoor zorgen dat je geautoriseerd moet zijn om bijvoorbeeld bepaalde queries uit te voeren.


### Tegenmaatregelen
_Hoe wordt dit risico tegengegaan binnen het project?_

Door middel van prepared statements voor de database. 

_Als je denkt dat (een onderdeel van) een library of framework dit voor je oplost, probeer dan uit te zoeken welk onderdeel dit is en hoe deze dat tegengaat._

**Hibernate** maakt gebruik van prepared statements: "_Hibernate executes all SQL queries and DML operations using prepared statements._" Dit houdt in dat de user inputs
alleen worden toegelaten via parametrisatie. Dit helpt bij het bestrijden van SQL injection.


## A5:2017 Broken Access Control

### Descriptie
_Wat houdt de vulnerability in?_

Broken access control heeft te maken met dat een aanvaller _access_ krijgt tot data, gebruiker- en/of adminaccounts of acties terwijl hij of
zij hier geen autorisatie voor heeft. 

Het kan overname van accounts, extra priveleges toekennen aan onbedoelde partijen
en data-aantasting veroorzaken.

### Risico's
_Hoe groot is het risico voor deze kwetsbaarheid binnen het project?_

Mijn **dependency check report** geeft aan dat _information exposure_ een probleem is. Dit kan komen doordat de primary key in
de URL aangepast kan worden en door CORS misconfiguratie. Door het aanpassen van de primary key
wordt informatie van een game van een bepaalde gebruiker _blootgesteld_ aan een (aanvaller) andere gebruiker.

_Wat als we authenticatie en autorisatie toevoegen?_

Als je authenticatie en autorisatie toevoegt zou je resource gebruik kunnen relateren
aan een gebruiker. Dit helpt tegen broken access control.

### Tegenmaatregelen
_Hoe wordt dit risico tegengegaan binnen het project?_

Door middel van voldoende tests om het risico van broken acces te verkleinen.

_Als je denkt dat (een onderdeel van) een library of framework dit voor je oplost, probeer dan uit te zoeken welk onderdeel dit is en hoe deze dat tegengaat._

N.V.T.

## A8:2017 Insecure Deserialization

### Descriptie
_Wat houdt de vulnerability in?_

Insecure deserialization heeft te maken met aanvallers die de structuur of inhoud
van de serializatie veranderen om zo bepaalde aanvallen uit te kunnen voeren op een machine.

Het kan een grote impact hebben, omdat aanvallers veel kunnen doen met betreffende machines door middel van
remote request execution.
### Risico's
_Hoe groot is het risico voor deze kwetsbaarheid binnen het project?_

Mijn **dependency check report** geeft aan dat _deserialization of untrusted data_ ook een probleem is.
Er is dus zeker een risico binnen mijn project, omdat er geen validatie-
en typeregels worden toegepast op ongeldige data.

_Wat als we authenticatie en autorisatie toevoegen?_

Als je authenticatie en autoratisatie toevoegt, zou je een check kunnen uitvoeren op ongeautoriseerd 
gedrag.

### Tegenmaatregelen
_Hoe wordt dit risico tegengegaan binnen het project?_

Er wordt in dit project volgens mij niets gedaan om insecure deserialization
tegen te gaan.

_Als je denkt dat (een onderdeel van) een library of framework dit voor je oplost, probeer dan uit te zoeken welk onderdeel dit is en hoe deze dat tegengaat._

**Jackson** en **Hibernate** maken gebruik van serialization en deserialization. Volgens mij doen zij echter niet veel / genoeg aan insecure deserialization.
