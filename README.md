# DowJonesIndex

## Overview
This project is a Spring Boot based REST application developed as part of the RBC coding challenge.
It allows users to upload bulk Dow Jones Index stock data, query records by stock ticker, and add new stock records.

The solution is designed to be simple, clean, and easy to run locally, keeping in mind the time-boxed nature of the exercise.

---

## Tech Stack
- Java 11
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

### 1. Bulk Upload Stock Data