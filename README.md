# 🚀 Java_todo-API

This is a RESTful API developed with Spring Boot and JDK-23.

The API allows users to register, log in to the application, manage their profile information, view and manage the list of available tasks.

## 1. Overview

This API fulfills the following requirements:

### Functional Requirements (FR)

- [x] User registration
- [x] User authentication
- [x] Retrieve logged-in user's profile
- [x] Search tasks by name
- [x] Task management (creation, deletion, updating)
- [x] User management (creation, updating, reading)

### Business Rules (BR)

- [x] Users cannot register with a duplicate username
- [x] Users can only manage tasks created by them
- [x] Within their profile, users can only edit their name and username
- [x] To access the endpoints, a token generated at login must be sent in all requests, except for the public routes "/login" and "users/login"

### Non-Functional Requirements (NFR)

- [x] User passwords must be encrypted
- [x] Application data must be persisted in a PostgreSQL database
- [x] Users must be identified by a JWT (JSON Web Token)

## 2. Getting Started

### Prerequisites

- JDK-23
- Docker
- PostgreSQL

### Setup

1. Clone the repository:
   ```
   git clone https://github.com/your-username/Java_todo-API.git
   cd Java_todo-API
   ```

2. Start the PostgreSQL database using Docker:
   ```
   docker-compose up -d
   ```

3. Build and run the Spring Boot application:
   ```
   ./mvnw spring-boot:run
   ```

## 3. API Endpoints

- `POST /users/register`: Register a new user
- `POST /login`: Authenticate a user and receive a JWT
- `GET /users/me`: Get the logged-in user's profile
- `PUT /users/me`: Update the logged-in user's profile
- `DELETE /users/me`: DELETE the logged-in user's profile
- `POST /todo`: Create a new task
- `GET /todo/{id}`: Get a specific task
- `PATCH /todo/{id}`: Update a specific task
- `DELH /todo/{id}`: Delete a specific task
- `GET /todo?q={taskName}`: Search for tasks by H /todo

## 4. AuthenticatodoAll endpoints except `/login` and `users/login` require authentication. Include the JWT token in the `Authorization` header of your requests:

```
Authorization: Bearer your-jwt-token
```

## 5. Error Handling

The API uses standard HTTP status codes to indicate the success or failure of requests. Detailed error messages are provided in the response body when applicable.

## 6. Security

- User passwords are encrypted before being stored in the database
- JWTs are used for authentication and authorization

