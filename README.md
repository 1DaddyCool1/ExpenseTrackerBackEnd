# ExpenseTracker

![License: MIT](https://img.shields.io/badge/license-MIT-blue)
![Language: Java](https://img.shields.io/badge/language-Java-orange)
![Framework: Spring Boot](https://img.shields.io/badge/framework-Spring%20Boot-lightgrey)

Short description: ExpenseTracker is a Spring Boot backend for tracking expenses and income. It is suitable as a starter backend for mobile or web clients: stores expense records, supports authentication with JWT, and works with relational databases (H2/Postgres).

## Table of Contents
- [About](#about)  
- [Features](#features)  
- [Requirements](#requirements)  
- [Installation](#installation)  
- [Configuration](#configuration)  
- [Running](#running)  
- [API — Examples](#api--examples)  
- [Development](#development)  
- [Testing](#testing)  
- [Deployment](#deployment)  
- [Contributing](#contributing)  
- [License](#license)  
- [Authors](#authors)  
- [Notes](#notes)

## About
ExpenseTracker is a Java (Spring Boot 3.4.2) backend for personal or team expense tracking. The project uses Spring Web, Spring Data JPA, Spring Security and JJWT for JWT authentication. H2 is used for in-memory local development and PostgreSQL is recommended for production.

## Features
- CRUD for expense records and categories
- User authentication and authorization via JWT
- Persistent storage with Spring Data JPA (H2 for dev, PostgreSQL for prod)
- JSON serialization via Jackson
- Simple local development with H2 console

## Requirements
- Java 17+ (Spring Boot 3.x compatible)
- Gradle (project includes Gradle Wrapper: ./gradlew)
- PostgreSQL (for production)
- Docker (optional, for containerized deployment)
- IDE plugin for Lombok (project uses Lombok)

## Installation

Clone the repository:
```bash
git clone https://github.com/1DaddyCool1/ExpenseTracker.git
cd ExpenseTracker
```

Build the project:
```bash
./gradlew build
```

Run the application locally (development):
```bash
./gradlew bootRun
```

Build an executable JAR and run:
```bash
./gradlew bootJar
java -jar build/libs/ExpenseTracker-1.0-SNAPSHOT.jar
```

On Windows use `gradlew.bat` instead of `./gradlew`.

## Configuration

Typical properties can be placed in `src/main/resources/application.properties` or provided via environment variables.

Example H2 (development):
```properties
spring.datasource.url=jdbc:h2:mem:expensetracker;DB_CLOSE_DELAY=-1
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.jpa.show-sql=true
```

Example PostgreSQL (production):
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/expensetracker
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

Recommended environment variables:
- SPRING_DATASOURCE_URL
- SPRING_DATASOURCE_USERNAME
- SPRING_DATASOURCE_PASSWORD
- JWT_SECRET — secret used to sign JWT tokens
- SPRING_PROFILES_ACTIVE — e.g. dev, prod

Store JWT_SECRET securely (CI secrets, cloud secret manager, etc.).

## Running and Endpoints

After startup the app runs by default at: http://localhost:8080

Common endpoints (actual controller paths may vary — check src/ for exact mappings):
- POST /api/auth/login — obtain JWT token
- POST /api/auth/register — register a new user
- GET /api/expenses — list expenses (requires Authorization)
- POST /api/expenses — create expense
- GET /api/expenses/{id} — get expense by id
- PUT /api/expenses/{id} — update expense
- DELETE /api/expenses/{id} — delete expense

Example create request:
```bash
curl -X POST http://localhost:8080/api/expenses \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{"amount":15.50,"currency":"USD","category":"Food","comment":"coffee","date":"2025-11-01"}'
```

## Development

- The project uses Lombok — enable Lombok support in your IDE (IntelliJ/Eclipse).
- Recommended workflow:
  - Create feature branches: feature/<short-description>
  - Open PRs against main
  - Include tests for new functionality

Useful Gradle commands:
- Run app: ./gradlew bootRun
- Build: ./gradlew build
- Run tests: ./gradlew test
- Clean: ./gradlew clean

## Testing
Tests use JUnit 5 (junit-jupiter). Run tests with:
```bash
./gradlew test
```

To add code coverage, configure JaCoCo or a similar tool in build.gradle.

## Deployment
Deployment options:
- Docker: build image with the generated JAR and run, passing environment variables for production.
- CI/CD: use GitHub Actions / GitLab CI to build, test and push images to a registry and deploy.

Example Dockerfile:
```dockerfile
FROM eclipse-temurin:17-jre
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["java","-jar","/app.jar"]
```

For production, use PostgreSQL and set SPRING_PROFILES_ACTIVE=prod. Tune connection pool and logging for production usage.

## Contributing
1. Fork the repo.
2. Create a branch: git checkout -b feature/your-feature
3. Make changes and add tests.
4. Run tests locally.
5. Open a pull request to main with a clear description.

Consider adding a CONTRIBUTING.md to formalize contribution rules.

## License
This README assumes the project will use the MIT License. If you prefer another license, replace the LICENSE file accordingly.

## Authors
- 1DaddyCool1 — repository owner

## Notes
Key dependencies (from build.gradle):
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Security
- Jackson Databind
- H2 (dev)
- PostgreSQL (prod)
- JJWT (JWT)
- Lombok

If you want, I can:
- Commit this README.md to the repository,
- Add a LICENSE file (MIT or other) and commit it,
- Create example application.yml for profiles (dev/prod),
- Add a basic GitHub Actions workflow for build & test.
