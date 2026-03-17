# School Management API

Minimal Spring Boot CRUD API for students.

## Tech
- Java 21
- Spring Boot
- Spring Data JPA
- H2 in-memory database

## Run
```bash
./mvnw spring-boot:run
```

## Test
```bash
./mvnw test
```

## Endpoints
- `GET /api/students`
- `GET /api/students/{id}`
- `POST /api/students`
- `PUT /api/students/{id}`
- `DELETE /api/students/{id}`

### Example create request
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Ana","lastName":"Silva","gradeLevel":"Grade 10"}'
```

