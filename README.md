# To Do Lists Web API

### Overview

This is a simple web api built to demonstrate RESTful API design. It is built using Spring Boot with dependencies on Hibernate, Postgres, and lombok (to make boiler plate code simpler).

### Local Development

The web server expects a postgres database running on localhost at port 5432. You can either run psql or your own local postgres server, or you can run the dockerized postgres container provided in the docker-compose.yml file. To run the docker container, run `docker-compose up -d db`. This will start your database as a daemon. If you want to tear down your database, but keep the persisted data, run `docker-compose down`. If you want to delete all data created, run `docker-compose down -v`, which will remove the volume of persisted data.

To run the server, run `mvn spring-server:boot`. You can then access the todolists by hitting `/api/v1/todolists`.