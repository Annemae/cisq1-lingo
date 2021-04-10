# Vulnerability Analysis

## A1:2017 Injection

### Description
Injection gaat over de mogelijkheid om data
door te geven aan een interpreter met kwaadaardige intenties. Dit kan met elke soorten inputs, enviromental
variables, parameters, interne en externe web services en elke soort gebruikers.

Het kan data loss, data corruption, disclosure (leaking to third parties), loss of 
accountability, denial of acces en host takeover veroorzaken.

### Risk
Het is zeker wel een risico binnen mijn project, omdat ik bij mijn 
web request handler niet gebruik maak van een request dto of 
van prepared statements voor de database. 

### Counter-measures
Hibernate gaat SQL injection tegen. 