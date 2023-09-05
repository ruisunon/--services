#AWS S3 with Spring boot

This is a sample spring-boot project to demonstrate to upload and download files from aws s3

Blog post - https://www.rajith.me/2020/07/file-upload-and-download-apis-with.html

## Requirements
* Java 8
* Apache Maven 3.5.0 or higher

## How to Run

- Clone the project
- Add the bucket name you are using to application.yml file
- Build the project  
```
mvn clean install
```
- Run the application
```
java -jar target/file-service-1.0.0-SNAPSHOT.jar
```

### Reference Documentation
For further reference, please consider the following sections:

(Using aws credentials) https://docs.aws.amazon.com/sdk-for-java/v2/developer-guide/credentials.html