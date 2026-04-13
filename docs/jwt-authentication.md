# School Management — JWT Authentication

## How to run

### Prerequisites
- Java 21
- Docker Desktop (must be running)

### Start the app
```bash
cd /Users/maximus.abela/Desktop/schoolmanagement
./gradlew :service:bootRun
```

This will:
1. Auto-start the MySQL Docker container (via `docker-compose.yml`)
2. Hibernate creates/updates the `student` and `users` tables automatically
3. App starts on `http://localhost:8080`

> The terminal will show `88% EXECUTING` — that's normal. The app is running.

---

## How authentication works

### The flow

```
1. Register or Login  →  you get a JWT token back
2. Use that token     →  in the Authorization header for all other requests
```

### Public endpoints (no token needed)
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/v1/api/auth/register` | Create a new user account |
| POST | `/v1/api/auth/login` | Login with email & password |

### Protected endpoints (token required)
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/v1/api/students` | Get all students |
| GET | `/v1/api/students/{id}` | Get student by ID |
| POST | `/v1/api/students` | Create a student |
| PUT | `/v1/api/students/{id}` | Update a student |
| DELETE | `/v1/api/students/{id}` | Delete a student |

---

## How to test it (step by step)

### Step 1 — Register a user

```bash
curl -X POST http://localhost:8080/v1/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "password": "password123"
  }'
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOi...",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "STUDENT"
}
```

**Copy the `token` value.**

### Step 2 — Use the token to call a protected endpoint

```bash
curl http://localhost:8080/v1/api/students \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOi..."
```

Replace the `eyJhbG...` part with the actual token you got from Step 1.

### Step 3 — Try without a token (should fail)

```bash
curl http://localhost:8080/v1/api/students
```

Response: `403 Forbidden` — because you didn't provide a token.

### Step 4 — Login (if you already registered)

```bash
curl -X POST http://localhost:8080/v1/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'
```

You get a fresh token back.

---

## What each file does

### `application.properties` — JWT config
```properties
jwt.secret=dGhpc2lzYXZlcnlsb25nc2VjcmV0a2V5Zm9yand0dG9rZW5zaWduaW5nMTIzNDU2
jwt.expiration=86400000
```

- **`jwt.secret`** — a Base64-encoded key used to sign tokens. This is like a password that the server uses to create and verify tokens. If someone doesn't know this secret, they can't forge a token.
- **`jwt.expiration`** — how long a token is valid, in milliseconds. `86400000` = 24 hours. After 24 hours, the token expires and the user must login again.

### `User.java` — the database entity
- Stored in the `users` table in MySQL
- Implements Spring Security's `UserDetails` interface so Spring Security can work with it
- Has fields: `id`, `firstName`, `lastName`, `email` (unique), `password` (hashed), `role`
- The `getUsername()` method returns `email` — email is what identifies a user

### `Role.java` — user roles
- Simple enum: `STUDENT`, `TEACHER`, `ADMIN`
- When a user registers, they get `STUDENT` by default
- Stored as a string in the database (e.g. the column contains "STUDENT")

### `UserRepository.java` — database access
- `findByEmail(email)` — looks up a user by their email
- `existsByEmail(email)` — checks if an email is already taken (used during registration)

### `JwtService.java` — creates and validates tokens
- **`generateToken(user)`** — takes a user, builds a JWT containing their email as the "subject", signs it with the secret key, sets the expiration time
- **`extractUsername(token)`** — reads the email out of a token
- **`isTokenValid(token, user)`** — checks the token hasn't expired and the email matches

A JWT token is just a signed string with 3 parts: `header.payload.signature`
- **Header**: says it's a JWT using HMAC-SHA256
- **Payload**: contains the user's email, when it was issued, when it expires
- **Signature**: proves it wasn't tampered with (created using the secret key)

### `AuthService.java` — register and login logic

**Register:**
1. Check if email already exists → throw `UserAlreadyExistsException` if so
2. Hash the password with BCrypt (never store plain text passwords)
3. Save user to database with role `STUDENT`
4. Generate a JWT token
5. Return the token + user info

**Login:**
1. Find user by email → throw `AuthenticationFailedException` if not found
2. Compare provided password against the stored hash → throw `AuthenticationFailedException` if wrong
3. Generate a JWT token
4. Return the token + user info

### `AuthController.java` — HTTP endpoints
- `POST /v1/api/auth/register` — calls `authService.register()`
- `POST /v1/api/auth/login` — calls `authService.login()`
- Both are public (no token needed to access them)

### `SecurityConfig.java` — Spring Security configuration
- **Disables CSRF** — not needed for a stateless REST API
- **Stateless sessions** — the server doesn't store sessions; every request must carry a JWT
- **`/v1/api/auth/**` is public** — register and login don't need a token
- **Everything else requires authentication** — must have a valid JWT
- **Adds the JWT filter** before Spring's default authentication filter
- **Defines `UserDetailsService`** — tells Spring how to load a user by email from the database
- **Defines `PasswordEncoder`** — uses BCrypt for hashing passwords

### `JwtAuthenticationFilter.java` — the filter that runs on every request
This runs **before** your controller code. For every incoming HTTP request:

1. Look for an `Authorization` header
2. If missing or doesn't start with `Bearer ` → skip (let the request through, Spring Security will block it if the endpoint requires auth)
3. Extract the JWT token (everything after "Bearer ")
4. Extract the email from the token
5. Load the user from the database
6. Validate the token (not expired, email matches)
7. If valid → mark the request as authenticated so the controller can handle it

### Error handling (existing system)
Auth exceptions flow through the same `GlobalExceptionHandler`:

| Exception | HTTP Status | Error Code |
|-----------|-------------|------------|
| `AuthenticationFailedException` | 401 Unauthorized | `AUTHENTICATION_FAILED` |
| `UserAlreadyExistsException` | 409 Conflict | `USER_ALREADY_EXISTS` |

Example error response:
```json
{
  "code": "AUTHENTICATION_FAILED",
  "message": "Invalid email or password",
  "metadata": {}
}
```

---

## Project structure

```
interfaces/                              ← API contract (DTOs, exceptions)
├── auth/dto/
│   ├── request/RegisterRequest.java     ← register input
│   ├── request/LoginRequest.java        ← login input
│   └── response/AuthResponse.java       ← token + user info output
├── exception/
│   ├── ErrorCode.java                   ← all error codes
│   ├── ErrorItem.java                   ← error response shape
│   ├── StudentNotFoundException.java
│   ├── AuthenticationFailedException.java
│   └── UserAlreadyExistsException.java
└── student/dto/                         ← student request/response DTOs

service/                                 ← the runnable app
├── auth/
│   ├── User.java                        ← @Entity (users table)
│   ├── Role.java                        ← enum (STUDENT, TEACHER, ADMIN)
│   ├── UserRepository.java              ← DB access
│   ├── JwtService.java                  ← token create/validate
│   ├── AuthService.java                 ← register/login logic
│   └── AuthController.java             ← POST /v1/api/auth/*
├── config/
│   ├── SecurityConfig.java              ← Spring Security rules
│   ├── JwtAuthenticationFilter.java     ← intercepts every request
│   └── DataLoader.java                  ← seeds student data
├── exception/
│   ├── ErrorBuilder.java                ← maps exceptions → error codes
│   └── GlobalExceptionHandler.java      ← catches exceptions → HTTP responses
├── student/
│   ├── Student.java                     ← @Entity
│   ├── StudentRepository.java
│   ├── StudentService.java
│   └── StudentController.java
└── resources/
    └── application.properties           ← DB, Docker, JWT config
```

