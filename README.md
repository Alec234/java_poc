# Java POC — Spring Boot REST API

A proof-of-concept e-commerce REST API built with Spring Boot 3, Java 22, and Microsoft SQL Server. The project demonstrates an MVC layered architecture with controllers, services, repositories, JPA entities, and DTOs.

## Documentation

| Document | Description |
|---|---|
| [Architecture](docs/architecture.md) | Layer diagram and responsibilities |
| [Setup](docs/setup.md) | Prerequisites, configuration, build, run, and test instructions |
| [Data Model](docs/data-model.md) | Entity tables, relationships, and order status lifecycle |
| [Project Structure](docs/project-structure.md) | Annotated directory tree |
| [Planned Improvements](docs/planned-improvements.md) | Backlog and roadmap |

### API Reference

All endpoints are prefixed with `/api`. Interactive docs are available via Swagger UI at `http://localhost:8080/swagger-ui/index.html` once the application is running.

| Resource | File |
|---|---|
| Products | [docs/api/products.md](docs/api/products.md) |
| Orders | [docs/api/orders.md](docs/api/orders.md) |
| Payments | [docs/api/payments.md](docs/api/payments.md) |
| Users | [docs/api/users.md](docs/api/users.md) |
| Customers | [docs/api/customers.md](docs/api/customers.md) |
