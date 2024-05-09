# 🛫 Aeroplanner REST API

This project is a Flight Booking Application RESTful API built with Spring Boot.

## 📋 Prerequisites

Before you begin, ensure you have met the following requirements:

- You have installed **Java v17**
- You have installed **Maven (mvn)**
- You have a **Postgres server** running

## 🚀Running Aeroplanner REST API Locally

Follow these steps to get the application running on your machine:


1. **Clone the application**

Use the following command to clone the application:
```
git clone https://github.com/jcilacad/aeroplanner-rest-api.git
```


2. **Setup your database configuration**

Create a database named "aeroplanner_db" using the following command:
```sql
CREATE DATABASE aeroplanner_db
```
Tip: this can also be done using `pgAdmin`

3. **Generate a secret key and JWT expiration time**

- The secret key must be an HMAC hash string of 256 bits. 
  Example: `507c4db58311630bdfa4ed5d4b8a562ca2f43370e03a3df411b3784a805681f7`
- The JWT expiration time is expressed in milliseconds.
  Example: `3600000`


4. **Create a Gmail App Password**

Follow the instructions [here](https://knowledge.workspace.google.com/kb/how-to-create-app-passwords-000009237) to create a Gmail App Password.

5. **Create a "env.properties" file in the root directory**

Your directory structure should look like this:
```
- aeroplanner-rest-api 
  - src
  - env.properties
```

Inside the "env.properties" file, add the following properties (replace the placeholders with your actual data):
```
DATABASE_NAME=aeroplanner_db
DATABASE_USERNAME=<DATABASE USERNAME>
DATABASE_PASSWORD=<DATABASE PASSWORD>

JWT_SECRET_KEY=<GENERATED SECRET KEY>
JWT_EXPIRATION_TIME=<TOKEN EXPIRATION TIME>

SUPER_ADMIN_NAME=<SUPER ADMIN NAME>
SUPER_ADMIN_EMAIL=<SUPER ADMIN EMAIL>
# Password must contain at least one uppercase letter, one lowercase letter, one digit, one special character and be at least 8 characters long.
SUPER_ADMIN_PASSWORD=<SUPER ADMIN PASSWORD>

GMAIL_USERNAME=<GOOGLE ACCOUNT EMAIL>
GMAIL_PASSWORD=<GENERATED APP PASSWORD>
```


6. **Run the application**

Use the following command to run the application:
```
mvn clean spring-boot:run
```

## Run unit tests

Use the following command to run the unit tests:
```
mvn verify
```
This way, unit tests are executed along JaCoCo tool to gather the code coverage which can be read from Sonarqube

## Analyze code quality using SonarQube

- Have a **Sonarqube server** running
- Generate an authentication token
- Use the following command to analyze the application's code
```
mvn sonar:sonar -D"sonar.token=<SONAR_AUTH_TOKEN>" -D"sonar.host.url=<SONAR_HOST>"
```
- The `sonar.host.url` property can be omitted if using the default, which is `http://localhost:9000`
