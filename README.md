# To Do Lists Web API

### Overview

This is a simple web api built to demonstrate RESTful API design.
It is built using Spring Boot with dependencies on Hibernate, Postgres, and lombok (to make boiler plate code simpler).

### Local Development

The web server expects a **Postgres** database running on localhost at port `5432`.

##### Running the application's database

You can either run *psql* or your own *local Postgres* server, or you can run the **dockerized postgres** container provided in the `docker-compose.yml` file.

For the purpose of this demo, we'll use a dockerized database. That is, a database packaged in a docker container. To run the database, run `docker compose up -d db`. This will start your database as a daemon.

* If you want to tear down your database, but keep the persisted data, run `docker compose down`

* If you want to delete all data created, run `docker compose down -v`, which will remove the volume of persisted data.

##### Running the server

To run the server, run `mvn spring-boot:server`. You can then access the todolists by hitting `/api/v1/todolists`.

#### Manually testing the server

This application leverages a Swagger UI integration. As such, you can play around with the endpoints by navigating to http://127.0.0.1:8080/swagger-ui/index.html, the generated Swagger UI client.
