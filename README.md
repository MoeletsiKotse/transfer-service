### Objective

Using Java and Spring Boot, Ledger service.

### Brief

The domain is simple internal wallet transfers: move money between two accounts
and keep an accurate ledger even under failures and heavy concurrency.

### Tasks

-   Implement assignment using:

    -   Language: **Java**
    -   Framework: **Spring Boot**
    -   Docker

## Prerequisites

Make sure you have the following installed:

- Java 17+
- Maven 3.6+
- Docker
- Docker Compose

## Build the Project

Compile the Spring Boot application and package it into a `.jar` file.

You can run it using below

```bash
./mvnw clean package


docker build -t ledger-service:latest .
```


Run the following to run the project in Containerized environment

```bash
./mvnw clean package


./mvnw spring-boot:run

```

```bash using docker run or
docker run -p 8080:8080 transfer-service 
```

```bash using docker compose
docker compose up -d

docker compose logs -f
```

## swagger doc on (http://localhost:8080/swagger-ui/index.html#)
