
# Spring Boot application with Redis in persistence layer
[![Java CI with Maven](https://github.com/andrei-punko/spring-boot-redis/actions/workflows/maven.yml/badge.svg)](https://github.com/andrei-punko/spring-boot-redis/actions/workflows/maven.yml)
[![Coverage](.github/badges/jacoco.svg)](https://github.com/andrei-punko/spring-boot-redis/actions/workflows/maven.yml)
[![Branches](.github/badges/branches.svg)](https://github.com/andrei-punko/spring-boot-redis/actions/workflows/maven.yml)

Based on [Spring Boot app template](https://github.com/andrei-punko/spring-boot-template)

Includes web-server on port 9080 with `/articles` endpoint exposed.  
Supports CRUD set of operations and R with pagination

## Prerequisites:
- JDK 11
- Maven 3

## How to build:
    mvn clean install

#### Build Docker image with application inside:
    docker build ./ -t spring-boot-redis-app

## Start application using starting script:
Use [run.bat](./run.bat) script

## Start application by running executable jar (Redis should be started before that manually):
    java -jar target/spring-boot-redis-0.0.1-SNAPSHOT.jar \
     --spring.redis.host=localhost \
     --spring.redis.port=6379

## Same thing but using Spring profile to determine properties (Redis should be started before that manually):
    java -jar target/spring-boot-redis-0.0.1-SNAPSHOT.jar \
     --spring.profiles.active=dev

## Start application using Maven (Redis should be started before that manually):
    mvn spring-boot:run -Dspring-boot.run.arguments="\
     --spring.redis.host=localhost \
     --spring.redis.port=6379

## Same thing but using Spring profile to determine properties (Redis should be started before that manually):
    mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=dev

## Start application using set of two Docker containers - one with Redis and another with application:
    docker-compose up

## Link for quick check:  
http://localhost:9080/articles

## Swagger documentation:  
http://localhost:9080/swagger-ui.html

## Useful CURL commands:
- New article addition:  
`curl -i -H "Accept: application/json" -H "Content-Type: application/json" -d '{ "title": "Some tittle", "text": "Some text", "author": { "id": 1 } }' -X POST http://localhost:9080/articles`

- Get existing article:  
`curl -i http://localhost:9080/articles/1`

- Update existing article:  
`curl -i -H "Accept: application/json" -H "Content-Type: application/json" -d '{ "title": "Another tittle" }' -X PATCH http://localhost:9080/articles/2`

- Get list of articles with pagination support:  
`curl -i 'http://localhost:9080/articles?size=2&page=4&sort=author.firstName,DESC'`

- Deletion of article:  
`curl -i -X DELETE http://localhost:9080/articles/1`
