# Setup Guide

## Prerequisites

| Requirement | Version |
|---|---|
| Java | 22 |
| Gradle | 8+ (wrapper included) |
| Microsoft SQL Server | 2017+ |

A running SQL Server instance is required with:
- A database named `FirstJavaApi`
- A login with username `java_app` and appropriate read/write permissions

---

## Configuration

Database and JPA settings are defined in `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:sqlserver://<HOST>:1433;databaseName=FirstJavaApi;encrypt=false;TrustServerCertificate=true;
    username: java_app
    password: JavaPass
    driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver
  jpa:
    database-platform: org.hibernate.dialect.SQLServerDialect
    hibernate:
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
        implicit-strategy: org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl
```

Update `<HOST>` to match your SQL Server instance (e.g., `localhost` or a named instance path). For production use, externalize credentials via environment variables or a secrets manager.

---

## Building and Running

The project uses the Gradle wrapper — no local Gradle installation is required.

**Build:**
```bash
./gradlew build
```

**Run (development):**
```bash
./gradlew bootRun
```

The application starts on **port 8080** by default.

**Produce a runnable JAR:**
```bash
./gradlew bootJar
java -jar build/libs/demo-0.0.1-SNAPSHOT.jar
```

**Reverse-engineer entities from the database** (optional, requires a live database):
```bash
./gradlew reverseEngineer
```
This task uses Hibernate Tools to regenerate the JPA entity classes under `src/main/java/com/example/demo/Entities/` based on `src/main/resources/hibernate.reveng.xml`.

---

## Running Tests

```bash
./gradlew test
```

Unit tests use JUnit 5 and Mockito. All service-layer tests are located under `src/test/java/com/example/demo/Services/` and run without a live database.

| Test Class | Covers |
|---|---|
| `OrderServiceTest` | Order creation, submission, cancellation, total calculation |
| `ProductServiceTest` | Product filtering, pagination, sort parameter handling |
| `PaymentServiceTest` | Payment capture (idempotency), refund logic |
