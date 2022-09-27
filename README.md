[![GitHub issues](https://img.shields.io/github/issues/artshishkin/txt-to-elasticsearch)](https://github.com/artshishkin/txt-to-elasticsearch/issues)
![Spring Boot version][springver]
![Spring Batch][springbatch]
![Project licence][licence]

# Mobilization of Orks

Importing Orks' mobilization data published by [Anonymous TV](https://twitter.com/YourAnonTV/status/1573290421270507520)

#### Import mobilization data into Elasticsearch

1. Start Elasticsearch
    - from folder `docker-compose` run
    - `docker-compose up -d`
2. Build project
    - `mvn clean package`
3. Run mobilization import job to Elasticsearch
    - from folder `target` run
    - `java -jar txt-to-elasticsearch-0.0.1-SNAPSHOT.jar --spring.profiles.active=txt-to-elasticsearch zipFile=data/input/Priziv1volna.zip`

#### View mobilization data in Kibana

1. Visit Kibana console
    - [http://localhost:5601/](http://localhost:5601/)
2. Create index pattern
    - Menu &rarr; Management &rarr; Kibana &rarr; Index Patterns &rarr;
    - Create index pattern
    - Name: `warriors*`
    - Timestamp field: `birthDate`
3. View all the data
    - Menu &rarr; Discover &rarr;
    - Search: leave empty
    - Period: last 60 years
    - Refresh
4. Search for certain data 
    - Search:
    - `address : МОСКВА and fullName.keyword :  С* and fullName : СЕРГЕЙ`
    - `address : МОСКВА and fullName.keyword :  С* and fullName : СЕРГЕЙ and birthDate >=22.09.1992 and birthDate <=27.09.1993`

[springver]: https://img.shields.io/badge/dynamic/xml?label=Spring%20Boot&query=%2F%2A%5Blocal-name%28%29%3D%27project%27%5D%2F%2A%5Blocal-name%28%29%3D%27parent%27%5D%2F%2A%5Blocal-name%28%29%3D%27version%27%5D&url=https%3A%2F%2Fraw.githubusercontent.com%2Fartshishkin%2Ftxt-to-elasticsearch%2Fmaster%2Fpom.xml&logo=Spring&labelColor=white&color=grey
[licence]: https://img.shields.io/github/license/artshishkin/txt-to-elasticsearch.svg
[springbatch]: https://img.shields.io/static/v1?label=&message=Spring%20Batch&labelColor=white&color=grey&cacheSeconds=60&logo=data:image/png;base64,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