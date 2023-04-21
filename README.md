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
JDK 17 o superior
Spring boot Framework 
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
use pg admin to display the data, for this you need to open PgAdmin at url [localhost:80](localhost:80)
add manually the server following the steps:
1. Open pgAdmin and connect to a PostgreSQL instance.
2. Log with User : admin@admin.com and password: admin
3. In the left-hand pane, right-click on "Servers" and select "Register" > "Server". 
4. In the "General" tab, enter a name for the server in the "Name" field. 
5. In the "Connection" tab, enter the following information:
Host name/address: the IP address or hostname of the PostgreSQL server you want to connect to for instance postgres.
Port: the port number used by the PostgreSQL server. The default port is 5432.
Maintenance database: the name of the database you want to use for managing the server.
Username: the username you want to use to connect to the server.
Password: the password for the username you entered above.
In the "Advanced" tab, you can configure additional server settings, such as SSL mode, timeout settings, and others. These settings are optional and can be left at their default values.

Once you have entered all the required information, click "Save" to create the server.

You should now see the newly created server listed under "Servers" in the left-hand pane. To connect to the server, right-click on it and select "Connect Server". If the connection is successful, you should see a new node for the server appear in the left-hand pane.
## Run Test ⚙️
```console
mvn test
```

## Documentation 📖
[Click link](http://localhost:8080/api/swagger-ui/index.html#/)

## How to setup SonarLint with IntelliJ IDEA
1. Go to File > Settings > Plugins and select Marketplace. Now enter “SonarLint” and click install.
2. Now, go to File > Settings > Tools > SonarLint and click on the ‘+’ sign under SonarQube / SonarCloud connections.
3. Enter a connection name, then select sonarqube and enter SonarQube server URL and then click next.
4. Now choose Token or Login / Password and enter the value then click Next and the Finish.
5. For mapping your local project with the project on SonarQube. Go to “File > Settings > Tools > SonarLint >Project Settings” and check the option Bind project to SonarQube / SonarCloud and select the configured server from dropdown
6. Click on the search list and select the corresponding project from the list and click apply and ok.

## How to Anaalyze your code with SonarLint?

1. Go to “Analyze” and select “Analyze all files with SonarLint” or Click on SonarLint tab and play
2. Happy coding!


