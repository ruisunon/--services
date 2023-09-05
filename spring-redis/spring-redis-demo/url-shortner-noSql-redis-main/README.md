# URL-Shortner API Using java and NoSQL Database (Redis)

## Setup

* Install Java 15
* Install Maven
* Install Redis Server

Start the redis Server before running the applicaion

```
redis-server
```

Run the below commands in the core

```
mvn clean
```

```
mvn install
```

Execute any of the two commands below to run the application

```
java -jar target/url-shortner-redis-0.0.1-SNAPSHOT.jar
```

```
mvn spring-boot:run
```

The Default application-port is 9090, port for redis is 6380 and base-url is set to /femoral (all can be changed in application.properties)

---

# API

POST request to http://localhost:9090/femoral/url with the url to shorten in the request-body

* Sample Response

```
{
   "code":"kxnphmjg",
   "originalUrl":"https://github.com/hardikSinghBehl",
   "message":"SuccessFully Shortened Given Url",
   "timestamp":"2021-05-20T18:39:31.395465003"
}
```

The Received id is the shortened-url and can be used in the following form 
* http://localhost:9090/femoral/url/{recievedId}
* Will be redirected to the orginal url on success