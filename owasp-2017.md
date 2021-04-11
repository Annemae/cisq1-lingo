# Vulnerability Analysis

## A1:2017 Injection

### Description
Injection gaat over de mogelijkheid om data
door te geven aan een interpreter met kwaadaardige intenties. Dit kan met elke soorten inputs, enviromental
variables, parameters, interne en externe web services en elke soort gebruikers.

Het kan data loss, data corruption, disclosure (leaking to third parties), loss of
accountability, denial of access en host takeover veroorzaken.

### Risk
Het is zeker wel een risico binnen mijn project, omdat ik bij mijn
web request handler niet gebruik maak van een request dto, ik whitelist de toegestande user inputs niet, of
van prepared statements voor de database.

Als je authenticatie en autorisatie toevoegt zou je resource gebruik kunnen relateren
aan een gebruiker.
### Counter-measures
Hibernate gaat SQL injection tegen.


## A5:2017 Broken Access Control

### Description
Broken access control heeft te maken met dat een aanvaller _acces_ heeft tot data, gebruiker- en/of adminaccounts of acties terwijl hij of
zij hier geen autorisatie voor heeft. 

### Risk
Mijn **dependency check report** geeft aan dat _information exposure_ een probleem is. Dit kan komen doordat de primary key in
de URL aangepast kan worden. Hierdoor wordt informatie van iemand anders zijn of haar game _blootgesteld_ aan een ander.

Als je authenticatie en autorisatie toevoegt zou je resource gebruik kunnen relateren
aan een gebruiker. 
### Counter-measures


## A8:2017 Insecure Deserialization

### Description
Insecure deserialization heeft te maken met een aanval die de structuur of inhoud
van de serializatie aanpast om zo bepaalde acties uit te voeren op een machine.

### Risk
Mijn **dependency check report** geeft aan dat _deserialization of untrusted data_ ook een probleem is.

Als je authenticatie en autorisatie zal geen invloed hebben op het serializen. Het is
gewoon belangrijk dat er gebruik wordt gemaakt van uitvoerige validatie- en typeregels
om ongeldige data te voorkomen.
### Counter-measures
In mijn project wordt er gebruik gemaakt van serialisatie door **Hibernate** en **Jackson**. Hibernate zet mijn
objecten om naar objecten die passen in de database en Jackson zet bijvoorbeeld mijn DTO-data om naar JSON.