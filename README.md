# HH Service

Backend API for a small job marketplace inspired by HeadHunter. The service supports user authentication, role-based access, job publishing, and job applications with status tracking.

## Tech Stack

- **Java 21**
- **Spring Boot 4.1.0**
- **Spring Web MVC** for REST API
- **Spring Security** for authentication and role-based access
- **JWT** with `jjwt` for stateless authorization
- **Spring Data JPA / Hibernate** for database access
- **PostgreSQL** as the main database
- **H2 Database** for tests
- **Jakarta Bean Validation** for request validation
- **Lombok** for reducing boilerplate code
- **Gradle** as the build tool
- **JUnit 5 / Spring Boot Test / Mockito** for testing

## Features

- User registration and login
- JWT-based authentication
- Roles: `USER` and `HR`
- HR can create job vacancies
- Users can apply to jobs
- Duplicate applications are blocked
- HR can view applications and update application status
- Users can view only their own applications
- Centralized validation and exception handling
- Environment-based configuration for database and JWT secrets

## Project Structure

```text
src/main/java/com/github/xasoek/hh_service
├── config          # Application configuration
├── controller      # REST controllers
├── dto             # Request and response DTOs
├── entity          # JPA entities and enums
├── exception       # Custom exceptions and global handler
├── mapper          # Entity/DTO mapping
├── repository      # Spring Data JPA repositories
├── security        # JWT filter and security helpers
└── service         # Business logic
```

## Requirements

- Java 21
- PostgreSQL
- Gradle wrapper is included, so local Gradle installation is not required

## Environment Variables

The application reads sensitive configuration from environment variables:

| Variable | Default | Description |
| --- | --- | --- |
| `DB_URL` | `jdbc:postgresql://localhost:5432/hh_service` | PostgreSQL JDBC URL |
| `DB_USERNAME` | `postgres` | Database username |
| `DB_PASSWORD` | empty | Database password |
| `JWT_SECRET` | development fallback | Secret key for JWT signing |
| `JWT_EXPIRATION_MS` | `3600000` | JWT expiration time in milliseconds |
| `HIBERNATE_DDL_AUTO` | `update` | Hibernate schema mode |
| `JPA_SHOW_SQL` | `false` | Show SQL queries in logs |

## Run Locally

Create a PostgreSQL database:

```sql
CREATE DATABASE hh_service;
```

Run the application:

```bash
DB_PASSWORD='your_postgres_password' \
JWT_SECRET='change-this-to-a-long-secure-secret-key' \
./gradlew bootRun
```

The API will be available at:

```text
http://localhost:8080
```

## Run Tests

```bash
./gradlew test
```

Tests use the `test` profile and an in-memory H2 database.

## API Overview

### Auth

| Method | Endpoint | Access | Description |
| --- | --- | --- | --- |
| `POST` | `/auth/register` | Public | Register a new user |
| `POST` | `/auth/login` | Public | Login and receive JWT |

Example register request:

```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123"
}
```

Example login response:

```json
{
  "token": "jwt-token"
}
```

Use the token for protected endpoints:

```http
Authorization: Bearer jwt-token
```

### Jobs

| Method | Endpoint | Access | Description |
| --- | --- | --- | --- |
| `GET` | `/jobs` | Authenticated | Get all jobs |
| `POST` | `/jobs` | HR | Create a job |

Example create job request:

```json
{
  "title": "Java Backend Developer",
  "company": "Acme",
  "salary": 1000,
  "description": "Spring Boot backend position"
}
```

### Applications

| Method | Endpoint | Access | Description |
| --- | --- | --- | --- |
| `POST` | `/applications` | USER | Apply to a job |
| `GET` | `/applications` | HR | Get all applications |
| `GET` | `/applications/user/{userId}` | Owner or HR | Get applications by user |
| `GET` | `/applications/job/{jobId}` | HR | Get applications by job |
| `PUT` | `/applications/{id}/status?status=ACCEPTED` | HR | Update application status |

Available application statuses:

```text
SENT, VIEWED, ACCEPTED, REJECTED
```

Example apply request:

```json
{
  "jobId": 1
}
```

### Users

| Method | Endpoint | Access | Description |
| --- | --- | --- | --- |
| `GET` | `/users` | HR | Get all users |
| `POST` | `/users` | HR | Create a user |

## Security Notes

- Passwords are stored with BCrypt.
- JWT is used for stateless authentication.
- Users cannot create applications for another user.
- Users cannot view other users' applications.
- HR-only operations are protected in the service layer.
- Real secrets must be provided through environment variables and should not be committed.

## Future Improvements

- Add Swagger/OpenAPI documentation
- Add Docker Compose with PostgreSQL
- Add Flyway or Liquibase migrations
- Add full CRUD for jobs
- Add company entity and bind HR users to companies
- Add resume/profile functionality
- Add search, filtering, sorting, and pagination for jobs
