# Project Structure

```
java_poc/
├── build.gradle.kts                   # Gradle build configuration
├── settings.gradle.kts
├── EntityRelationshipDiagram.png      # ER diagram image
├── docs/                              # Project documentation
│   ├── architecture.md
│   ├── setup.md
│   ├── data-model.md
│   ├── project-structure.md
│   ├── planned-improvements.md
│   └── api/
│       ├── products.md
│       ├── orders.md
│       ├── payments.md
│       ├── users.md
│       └── customers.md
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── JavaPocApplication.java        # Spring Boot entry point
│   │   │   ├── SwaggerConfig.java             # OpenAPI / Swagger configuration
│   │   │   ├── Controllers/
│   │   │   │   ├── CustomerController.java
│   │   │   │   ├── OrderController.java
│   │   │   │   ├── PaymentsController.java
│   │   │   │   ├── ProductController.java
│   │   │   │   └── UserController.java
│   │   │   ├── Services/
│   │   │   │   ├── CustomerService.java
│   │   │   │   ├── OrderService.java
│   │   │   │   ├── PaymentService.java
│   │   │   │   ├── ProductService.java
│   │   │   │   ├── SecurityService.java       # User/auth service (in progress)
│   │   │   │   └── UserService.java
│   │   │   ├── Repository/
│   │   │   │   ├── CustomerRepository.java
│   │   │   │   ├── OrderItemRepository.java
│   │   │   │   ├── OrderRepository.java
│   │   │   │   ├── PaymentRepository.java
│   │   │   │   ├── ProductRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   ├── Entities/                      # JPA entities (DB-generated via Hibernate Tools)
│   │   │   │   ├── Customers.java
│   │   │   │   ├── OrderItems.java
│   │   │   │   ├── Orders.java
│   │   │   │   ├── Payments.java
│   │   │   │   ├── Products.java
│   │   │   │   └── Users.java
│   │   │   └── Models/                        # DTOs
│   │   │       ├── CustomerDTO.java
│   │   │       ├── OrderDTO.java
│   │   │       ├── OrderItemDTO.java
│   │   │       ├── PaymentDTO.java
│   │   │       ├── ProductDTO.java
│   │   │       └── UserDTO.java
│   │   └── resources/
│   │       ├── application.yaml               # Application configuration
│   │       ├── hibernate.cfg.xml              # Hibernate reverse-engineering config
│   │       └── hibernate.reveng.xml           # Table selection for reverseEngineer task
│   └── test/
│       └── java/com/example/demo/Services/
│           ├── OrderServiceTest.java
│           ├── PaymentServiceTest.java
│           └── ProductServiceTest.java
```
