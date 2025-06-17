# MDD

## Start the backend project

### Create the database

Create a new database mysql :

> CREATE DATABASE mdd;

The schema will be automatically generate with `spring.jpa.hibernate.ddl-auto=update`.

### Add the environment variables

In your IDE, add and custom the following environment variables:

```
APP_DB_HOST=localhost
APP_DB_PORT=3006
APP_DB_NAME=myDatabase
APP_DB_USER=root
APP_DB_PASSWORD=password
JWT_KEY=your_jwt_key
```

### Start the project

Git clone:

> git clone https://github.com/MPorret/Developpez-une-application-full-stack-complete

Go inside folder:

> cd Developpez-une-application-full-stack-complete/back

Install dependencies:

> mvn clean install

Launch API:

> mvn spring-boot:run

## Start the frontend project

Go inside folder:

> cd Developpez-une-application-full-stack-complete/front

Install dependencies:

> npm install

Launch Front-end:

> ng serve;

Access to the app : http://localhost:4200
