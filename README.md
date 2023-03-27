# psg-trading-restful-service
<!DOCTYPE html>
<html>
  <body>
    <img src="https://trello.com/1/cards/6420c34e23e7bf3b275d574f/attachments/6420c360abd41591a2a6aa60/previews/6420c361abd41591a2a6aa70/download/image.png" alt="PSG_Login">
  </body>
</html>

## Description
API to 


## Hands on 🚀
Development of a trading API solution aimed at sharing market prices as up-to-date as possible for PSG. Spring Boot 3.0.4 is being used as the framework

### Pre-requisitos 📋

```
JDK 11 o superior
Spring boot Framework version '3.0.4'
Maven 
IntelliJ 2021.3 
Docker and Docker Compose  
```

## Installation 🔧

### Intellij
The following tutorial is presented for an environment on **Windows 11

_In case you want to launch the application from IntelliJ it is necessary to follow the following steps:_

1. Clone the project locally and open it with IntelliJ
2. We must wait for all the dependencies in the build.gradle file to be loaded
3. To start the service, just touch the commands Ctrl+F9 to Build the application, and Shift+f10 to launch it._

To run from the console, just run the command:
```
mvn package
java -jar target/nombre-del-archivo-jar-generado.jar
```
### Docker

[Click the link and follow instructions](https://docs.docker.com/desktop/install/windows-install/)

### Docker Compose
Make sure you switch to Compose V2 with the docker compose CLI plugin or by activating the Use Docker Compose V2 setting in Docker Desktop. For more information see
[link](https://docs.docker.com/compose/install/)

## Run DB 🗄️
In order to have a succesful build of the application you must run the postgres base
to do so you can follow the steps:
1. Open a terminal or command prompt and navigate to the directory where the Docker Compose YAML file is located.
2. To start the containers defined in the Docker Compose YAML file, run the following command:
```console
docker-compose up -d
```
3. To stop the containers, use the following command:
```console
docker-compose down
```
This command will stop and remove the containers, networks, and volumes defined in the Docker Compose YAML file.

## Run Test ⚙️
```console
mvn test
```

## Documentation 📖
[Click link](http://localhost:8080/swagger-ui/index.html#/)


