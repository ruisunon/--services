# Spring Boot 3.0 Security with JWT Implementation

This project demonstrates the implementation of security using Spring Boot 3.0 and JSON Web Tokens (JWT). It includes
the following features:

## Features

* User registration and login with JWT authentication
* Password encryption using BCrypt
* Role-based authorization with Spring Security
* Customized access denied handling
* Logout mechanism
* Refresh token
* Blacklisting access token after user logs out
* Controller Method-level security

## Technologies

* Spring Boot 3.0
* Spring Security
* JSON Web Tokens (JWT)
* BCrypt
* Maven
* Redis
* PostgreSQL

## Getting Started

To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Maven 3+

To build and run the project, follow these steps:

* Add database "playing_around" to postgres
* Build the project: mvn clean install
* Run the project: mvn spring-boot:run

-> The application will be available at http://localhost:8080.

If we want to blacklist access token in redis, 
update useExpiringMapToBlackListAccessToken to false in application.yml.
To start redis in docker:
sudo docker ps --all
sudo docker start redisContainerId
e.g: sudo docker start c8f83c94f866