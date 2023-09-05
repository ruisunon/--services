# Spring Boot Microservices 

<p align="center">
	<img src="https://badgen.net/badge/API version/1.0/red">
	<img src="https://badgen.net/badge/Spring Boot/3.0.1/cyan">
	<img src="https://badgen.net/badge/icon/Docker?icon=docker&label">
</p>

<p style="font-size:5px;"> Spring Boot REST microservices for food and drinks ordering using displaying variety of technologies. Each service uses its own instance of database choosing from NoSql and SQL. Data access is being achived with JPA and JDBC using DAO & Reposiotry pattern with help of Flyway & hibernate to migrate schemas. Internal architecture of each service follows standard layered architecture.</p>

<p style="font-size:5px;"> At the top level I use microservice architecture. Each service is layyered API with its own database, required packages for connection to DB, HTTP endpoints.</p>

<p>For more information check the sections bellow.</p>

<p align="center" >
  <img width="75%" src="https://user-images.githubusercontent.com/45321513/210616653-a1b87c6b-f0e4-418e-9441-599c12e7a16d.png" />
  <p align="center">Overall project structure and used technologies</p>
</p>

# Contents

- Products service:
  - Redis as priamry DB with Redis commander
  - Apache kafka for publishing events
  - Swagger UI with OpenAPI
  
- Orders service:
  - PostgreSQL with PgAdmin
  - Apache kafka for consuming events
  - Swagger UI with OpenAPI
  - JDBC with Flyway migration
  - Aspects for logging and tracing
  - Dockerization of dependencies

- Restaurants service:
  - MongoDB with MongoCompas
  - Apache kafka for consuming events
  - Swagger UI with OpenAPI
  - Aspects for logging and tracing
  - Dockerization of dependencies

- Customers service:
  - PostgreSQL with PgAdmin
  - Swagger UI with OpenAPI
  - JDBC with Flyway migration
  - Aspects for logging and tracing
  - Dockerization of dependencies

- Delivery:
    - MongoDB with MongoCompas
    - Apache kafka for consuming events
    - Swagger UI with OpenAPI
    - Aspects for logging and tracing
    - Dockerization of dependencies
 
- Technologies shared among all services:
  - Spring Boot
  - Layered architecture
  - Spring Cloud Gateway 
  - Configuration Server with GitHub
  - Eureka Service Discovery
  - Grafana & Prometheus dashboard
  - Distributed tracing using Micrometer & Zipkin
  - System monitoring via Actuators
  - Dockerization of databases, services, servers, dashboards and more

- Coming soon
  - JWT Authentication & Authorization
  - ELK Stack
  - Jenkins CI/CD

<p>
 To run all required containers with Docker compose orchestration : 
	
	docker-compose up
</p>

Author: Armin Smajlagic
