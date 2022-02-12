# Pet-Store Implementation

This is an implementation for the pet store applicaiton based on Spring-Boot.

## Overview

Start your server as a simple Spring-Boot application
```
mvn spring-boot:run
```
Or package it then run it as a Java application
```
mvn package
java -jar target/petstore-0.0.1-SNAPSHOT.jar
```

You can view the api documentation in swagger-ui by pointing to  
http://localhost:8080/

## Docker

To start the server via docker, please run the following commands:
```sh

docker build -t nkodumoori/petstore-spring-boot-docker
docker run -d -p 80:8080 nkodumoori/petstore
docker pull openapitools/petstore




``` 

## Security

Used Channel Seucre for Authentication
 
## Configuration

Spring parameters in application.properties:
* Server port : `server.port` (default=8080)
* API base path : 'server.servlet.context-path`. In the docker image the base path can also be set with the `server.servlet.context-path` environment variable.

## Database
I have set up with H2 databse

## Deployement with Properites
* Created application-local.properties & applicaiton-cloud.properties, based on the environment these will be loaded

## Test cases
Implemeted the test cases for service layer based on Mocking
  