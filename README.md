# Movie-inf

This repository contains a Java web application built with the Spring boot Framework that retrieves movie information and checks if it won the "Best Picture" Oscar.

### Functionality

* Fetches movie details from the [OMDB Movies API](https://www.omdbapi.com/).
* Reads a CSV file containing Oscar winners (1927-2010).
* Determines if a movie won Best Picture based on the API data and Oscar winner list.
* Exposes a REST API for retrieving movie information and Oscar win status.

### Technologies

* Java (supported version)
* Spring boot Framework
* JPA (for database persistence)
* ## Database

The application uses a MySql database that runs with DockerCompose to store movie information retrieved from the API. A JPA data layer facilitates interaction with the database.

### Data Source

* **OMDB Movies API:** Used for fetching movie details.
* **CSV file:** Contains historical Oscar winners (1927-2010).

### Running the Application

1. Run `docker-compose.yml` file with `docker-compose up -d`
2. Configure your database connection details in `application-local.yml`.
3. You need an apiKey for retriving data from OMDB API, Get an API Key from [OMDB API site](https://www.omdbapi.com/) and replace it in `application-local.yml` api-key property.
4. Ensure you have the required libraries in your classpath (refer to pom.xml for details).
5. Run the application(`mvn clean spring-boot:run`).

### API Documentation

The application exposes a REST API for retrieving movie information and Oscar win status.

*Example:**

* **GET http://localhost:8093/movie?name=[Name of movie]** : Retrieves information about a specific movie by its name, including title, year, and Oscar win status.
