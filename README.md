# DowJonesIndex

## Overview
This project is a Spring Boot based REST application developed as part of the RBC coding challenge.
It allows users to upload bulk Dow Jones Index stock data, query records by stock ticker, and add new stock records.

The solution is designed to be simple, clean, and easy to run locally, keeping in mind the time-boxed nature of the exercise.

---

## Tech Stack
- Java 17
- Spring Boot
- Spring Data JPA
- H2 In-Memory Database
- Maven
- Swagger (Springdoc OpenAPI)
- Docker & Docker Compose

---

## Features
- Bulk upload of Dow Jones stock data using CSV / .data file
- Query stock data by ticker symbol (e.g. AA)
- Add a new stock record via REST API
- Swagger UI for API documentation and testing
- Dockerized setup for easy local execution

---

## API Endpoints

### 1. Bulk Upload Stock Data (POST /api/stock-data/bulk-insert)
### 2. Add Single Stock Record (POST /api/stock-data/add)
### 3. Get Stock Data by Ticker Symbol (GET /api/stock-data/{stock})
### 4. Update an existing stock record using ID. (PUT /api/stock-data/{id})
### 5. Delete Stock Data by ID (DELETE /api/stock-data/{id})

---

## Testing
- Unit Tests Implemented
- Service layer tests(Mockito)
- Controller layer tests(@WebMvcTest)
- Repository tests(@DataJpaTest)

---

## Validation & Error Handling
- Input validation is implemented using Bean Validation ('@Valid')
- Mandatory fields are validated at Controller layer
- Invalida request returned HTTP 400 (Bad Request)
- Validation scenarios are covered using unit tests with MockMvc

## API Documentation (Swagger)
- Once the application is running locally, Swagger UI can be accessed at: 
- http://localhost:8080/swagger-ui/index.html#/stock-data-controller/addStockData