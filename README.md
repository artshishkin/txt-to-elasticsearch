[![GitHub issues](https://img.shields.io/github/issues/artshishkin/txt-to-elasticsearch)](https://github.com/artshishkin/txt-to-elasticsearch/issues)
![Spring Boot version][springver]
![Spring Batch][springbatch]
![Elasticsearch Container][elasticsearch-container-ver]
![MySQL Container][mysql-container-ver]
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
    - `java -jar txt-to-elasticsearch-0.0.1-SNAPSHOT.jar --spring.profiles.active=txt-to-elasticsearch zipFile=./../data/input/Priziv1volna.zip`

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

#### Import regions population data into DB

1. Start MySQL
   - from folder `docker-compose` run
   - `docker-compose up -d`
2. Build project
   - `mvn clean package`
3. Run regions population import job to MySQL
   - from folder `target` run
   - `java -jar txt-to-elasticsearch-0.0.1-SNAPSHOT.jar --spring.profiles.active=xlsx-to-db zipFile=./../data/input/Bulletin_2022.zip`

#### Import cities data from VK into Elasticsearch

1. Start Elasticsearch
   - from folder `docker-compose` run
   - `docker-compose up -d`
2. Build project
   - `mvn clean package`
3. Run mobilization import job to Elasticsearch
   - from folder `target` run
   - `java -jar txt-to-elasticsearch-0.0.1-SNAPSHOT.jar --spring.profiles.active=webservice-to-elasticsearch`

#### Fixing Error during Start of elastic search with docker in Windows

- In case of error in Windows
   - `max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]`
   - use commands for Windows
   - `wsl -d docker-desktop`
   - `sysctl -w vm.max_map_count=262144`
   - or permanently add `vm.max_map_count = 262144` to __/etc/sysctl.conf__


[springver]: https://img.shields.io/badge/dynamic/xml?label=Spring%20Boot&query=%2F%2A%5Blocal-name%28%29%3D%27project%27%5D%2F%2A%5Blocal-name%28%29%3D%27parent%27%5D%2F%2A%5Blocal-name%28%29%3D%27version%27%5D&url=https%3A%2F%2Fraw.githubusercontent.com%2Fartshishkin%2Ftxt-to-elasticsearch%2Fmaster%2Fpom.xml&logo=Spring&labelColor=white&color=grey
[licence]: https://img.shields.io/github/license/artshishkin/txt-to-elasticsearch.svg
[springbatch]: https://img.shields.io/static/v1?label=&message=Spring%20Batch&labelColor=white&color=grey&cacheSeconds=60&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAB8AAAAgCAYAAADqgqNBAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAAYXSURBVFhHvVcJUJRlGH52l+Va7ptEFFAIVMwLksPUKZlMMnOYMTUb80BNSxkUI81GRSMEUYEJ8CpTJserHC3FGg9AdDMVREXz4hA55FpYlt1lt/f7+BdHUwRSH4aZ/3/f//ve93uP53sX+h6ipvm+Pv18rPDWM4jRA2zImYdZB4bgr/JshP/khANFqYKmexAxD4Tn5+JgURp2XloDF5k7jI3MSNK+tEldD02bGjGh2zDIJYjLuoIuGS+szEPCmbmQiCWwMLYRpI9Dp9fhofIB+tr4YuWY3ZAZWwqaZ6NT403qBsSdnIF79ddga+YMsej5WWIRqG4uQ1j/jzF3xDpB+nQ803imfCWO3fwRDjJXSCUmgrTraNE0QdFaj8gRcRjrNUWQPo7/GP/z9l4y/BXMpZYw70LoOgPbWtFaS3tZYWloBjztBgqadnQYv11bhMScSCioeKxM7CASifgHLwJtOi3qWqrh7xJCTqTDSCzlcpFSrdBvyluMgsozsDa171C8DKg0zfxwH/jOwxT/aIiPFG/D1aqzsDd3eamGGUylMjjKeiGrIIGKuBhiVkwmJDT07KsAa1eRWAQxXlxquw2xTtcmPL466Nkf1bnY1syFmOk+VeSrcUKlVaKV/s2MzCEe7TEZyeNPwFQiI2YqJ490wmcvFlpqN8Z8Pg7DsDuiGE6y3o+TzOm7B/HDxbX0oYZ63VaQ/j+wwzDOd7Hoi8+DN8HDxo/LGQ1L/CbbfnOrrpB71IcuhYm+kahvqUIRtZ9EZESXiRH/uCdQtNZBq9di1tDVmBe4HramjtzoulOfoLe1N8RtejV2XFiFzw6H4lqVnC+aOWwVtkw4DVcrT9Q0V6BN3716ULepUKusRGifD7F90kWM9pzM5dn/7MHsg8NwtvQotRo1WqWiVP/1HxFoJA7W6tQY5ByCqKBUyEys+IKCB7lIOxeNZrrhrE0dOqVdHTlZR1Hrbz8Ei0Ymw8HclcuLqy9gy7konnOJSAIvO3+seXvfo5wzpttbmEwbaKGhnI/3/hQzhsTyxQyHr28l/UYYEyk9eeGwLRpaH/LLaEFgAga7hHI5UTc25i3EpYpTtM4UlqZ2WBSYhAHOb3K9qEpRpne06MVf2ECQkh+F/NLfiXv0RIcWiBy+DgG9w7herVUh9Xw05GXZ/PKRSoyh1Ci4PGLgF3if6sWA3ZfiyeEMSI1MaC8RIgYsQbjvbK5TaZRo0Sog2n5htV5edgyxb+2Am3V/rixvvIVk8rhCcYd6UoW+tgOwJCgFr1l5cH1pww0k5S7AA8VdjHALw8LARBqrTLlOXnYc6ee/REtbE38P6BXGU2AYRPJLfkMcFVzSe9kQ7S9K0f9ckMTzHeQ+AVHBaR15zSs5ggx5LBWchoaDZoS4T8Ti4M1cx3Cj5m94Owzlz8wRFuLb1DkmEnM4y/pgSXAKHagf19+nAyXmzEdJ4006eRNSw3Pac55VsAG/XEvn+VRpm/GR/1JM8lvAFzFkXU7AryyEFGbWKlMHxyD89fYQMrAr+fTd/TS3WVGAxYgMWM8PYkBS7kLk3jtEehuKgITXRYDbuEcFxzZlQ+KVqjx+cjOaPqIo1H5OgXwDVjyJeQuoHc9xbnYwd8O4ftOw53I8H7NYe03wmYPpb8Tw7xkOXfue9N8SlVrw9IX7zcU0/2WC9ilj1K3aQu6EUtNIA2Qj/BwDsWxUZgfj3ai5yMOn1DZS20jJqBK+jgGIDskgh9nVDM4XrCbYN3qdjqp7JGJGbaV5wZjrDfiPcQNOECFkXljJvW5QVeNd75k0jcYJWmrN6zuw90oSFepO+DgO4zI27cafmoWr1fmQSW1gZizDspBMeNn7c/2TeKZxA9Lly3GcHLGkfLWom4gm4zHGM0LQPkKGfAWO3thGI7YTj9ic4Wt5WjrDc40zNNEIvObkdJQ0FPOBx45m+BVjdsHV0gMn7+xHWn40J55GVS3e6TcV8wO/E1Z2ji4ZN6CgIofqYQ4kEikNgg1ws/RCdUs52EDibu2DVWOzeMV3Fd0ybsC+K1uw69Ja/iuGsdvyUdvg79pOqd0CM95T7LuyWXjqCfT6fwGjZJbsBYNd9gAAAABJRU5ErkJggg==
[elasticsearch-container-ver]: https://img.shields.io/badge/dynamic/yaml?label=Elasticsearch&query=$.ELASTIC_VERSION&url=https%3A%2F%2Fraw.githubusercontent.com%2Fartshishkin%2Ftxt-to-elasticsearch%2Fmaster%2Fdocker-compose%2Fenv-versions.yaml&logoWidth=40&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAFYAAAAkCAYAAAATvM09AAAABGdBTUEAALGPC/xhBQAAAAlwSFlzAAASdAAAEnQB3mYfeAAABBNJREFUaEPlmU1IFGEYx9/xc2sLtzzkFuZXRHgIO9TBCN0gr56CFKK8dOoWRR07ZFEkEV2CyMJS+oAw6GKQ7tlDEiRSmCtWElG5kOWmNr3Pu8/I7HzsvM/MzuyEPxjnfR6dZf2/z/t/P0ZROcwDi2/OsOUvfRhl2dyaYqXROozWJ2RhVxdn2c/xVoycUZc/s9gRT33nmaHxNOuenMAoSz2/Zk60ZQMfKMG7FOmxHSRRAaV8u3hu4aWCmfWBlLCTd2NsaqAZI3doAq8XHIUFQUu4KMD8XJW4ewHELYrAKbwHRF6PtavSeG0aW+4J1HtX0izxkHssGCsCzf42/zzWVlinoV9scWFCMtK1vypvvmsfJjTKvI9AOyyFlfHTQggLlG09xqJ7r2MkCa9A5ZK5AmGWVy4mSXm/MHms7CRVCL+tav9EF1VDJ1IO1LwkiSTvGAKk5ZaR71+j2KIDov4vJO4n2Rif/BR+lyXHClYzC9jyh9LKGLY8ErAVGJ9VJZ4j77zCgpvJywjknYDnjLu2k1xkpxVF6IWFYWisNPinwPN0aQEl37GxRQhr9E7t8zXg92ADRpyqdk3YnTVxkaBSyYfl3M23GJnJrGb7TT3eKO5UqEO7UHmNBt6xVnuLweYWbGUxVv/a5FURibi61E3b8BOsqSxVxBUZnMEMEd0/nYPH/Fpol0fsPg7sQX9BB0EnaAhhp2eC2e+5FreI1Nspa4T/HaioiSuE3dXYIIIgIItr1+ce8yLkNmaZ19F/MHfIO9GLFiE8NqooLF4n2zVmPva+wpYcS93BdaRXrFYFdrTzaxT9OXTCwiycsqu6AKF+Bb2ogBBW4cI2hURYu1k4dMCX5JK18+sULt30CGHPnjvPnj1+hCk6FGFTnbWsJlqGkRnKtrFYyOy8xOR17eoVEfhNJvM3r6iA+3ETDDDkZfB0CENF7WnClj2hFpYPf72P5kNYAbZDQQ+fvO6F1GTBT0cdzgg0Aq1YGWAPH1ZkRQVCJ6zMiVMxMJ4NOJFjBT+WfrPqB7cxkuNQE22xP7/8h73rOIqRNWFbGVAsQCOnYrdENmDLP+LlFaxtdJi9mJvGjBlqdfgJTKZUUQHT5EWpWmq1WpFMdGIrlzBULYiqP0KkYPJYqFqVi+tEIUTNR7Gr1ouogOXkpZ6+ICWuV+yqFYBJDLwtcPhSD169eBEVyLuOVW5dZoqF7/ppAUaCPjuAkVKIlYnjBmFq4RtrfjqAkXdRZVYFRsTrZ2z7AlQp36s6vSCkIL3zKrlzw7OoslVqBeVclILMG1c3SAsLDM++Z30fJjGSx02V2lEIge2O+goJSVg9u0eeiDWpHSDm8wOH2Z5YNWYKzEqaDb1mbOTXhDgYBx/WezHMe+LiP7RtcnC7Osb+AQgSzKe9xJg9AAAAAElFTkSuQmCC&labelColor=white&color=grey
[mysql-container-ver]: https://img.shields.io/badge/dynamic/yaml?label=MySQL&query=$.MYSQL_VERSION&url=https%3A%2F%2Fraw.githubusercontent.com%2Fartshishkin%2Ftxt-to-elasticsearch%2Fmaster%2Fdocker-compose%2Fenv-versions.yaml&logoWidth=40&logo=data:image/png;base64,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&labelColor=white&color=grey
