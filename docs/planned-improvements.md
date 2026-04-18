# Planned Improvements

The following features are identified as next steps (see `README.txt` for developer notes):

- **Logging service** — structured logs with correlation IDs and per-request timing
- **Consistent error responses** — `@ControllerAdvice` returning a standard error envelope (`code`, `message`, `details`)
- **Input validation** — `@Valid` annotations on request DTOs
- **Pagination on orders and users** — `Pageable` / `Page<T>` support
- **Inventory race condition handling** — optimistic locking (`@Version`) on `Products` with `409 Conflict` responses
- **Payment idempotency key** — idempotency key field on `Payments` to handle retried requests
- **Spring Security with JWT** — role-based access (`ADMIN`, `CUSTOMER`) using the existing `Users` table
- **DTO projections** — constructor projections and fetch joins to avoid N+1 queries
