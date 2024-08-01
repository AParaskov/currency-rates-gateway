# Currency Rates Gateway

## Purpose of the application:
The purpose of this application is to get the rates of currencies from fixer.io where the base currency is EUR, store them locally and perform operations with the stored data.

## Technology stack:
- Java 17
- Spring Boot 3
- MySQL
- RabbitMQ
- Redis

## Instructions for running the application locally:
1. You will need MySQL, Redis and RabbitMQ servers running locally on your machine.
2. The application.properties file should be configured for your local environment to connect to each of the servers mentioned above and accessKey value should be provided in order to pull the data from fixer.io


