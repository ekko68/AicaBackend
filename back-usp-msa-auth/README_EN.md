# back-usp-msa-auth — AICA Authentication MSA Service

> A dedicated **MSA (Microservice) server** for member authentication and account management in the AICA Platform (USP).  
> Handles JWT-based authentication, social login, member registration/withdrawal, and menu access control.
> ⚠️ This project is an internal PKI authentication service for the AICA Platform.

---

## 📌 Project Overview

| Item               | Details                                |
| ------------------ | -------------------------------------- |
| Artifact ID        | `ai-member`                            |
| Group ID           | `aicluster`                            |
| Version            | `1.0.0`                                |
| Java               | 1.8                                    |
| Framework          | Spring Boot `2.6.7`                    |
| Internal Framework | AICA Framework `2.6.5`                 |
| Architecture       | MSA (JAR packaging)                    |
| ORM                | MyBatis `2.2.2`                        |
| Deployment         | Spring Boot Embedded Tomcat            |
| API Docs           | Springfox Swagger `2.9.2`              |
| Monitoring         | Spring Actuator + OpenTracing (Jaeger) |

---

## 📁 Project Structure

```
back-usp-msa-auth/
├── pom.xml
├── mvnw / mvnw.cmd             # Maven Wrapper
├── thunder-tests/              # Thunder Client API test files
└── src/
    └── main/
        ├── java/
        │   └── aicluster/member/
        │       ├── AiMemberApplication.java    # Spring Boot entry point
        │       ├── api/                        # REST API layer
        │       │   ├── account/                # Account management (password change, etc.)
        │       │   ├── auth/                   # Authentication (JWT, token refresh)
        │       │   ├── code/                   # Common codes
        │       │   ├── help/                   # Help / inquiry
        │       │   ├── insider/                # Internal user management
        │       │   ├── login/                  # Login processing
        │       │   ├── logout/                 # Logout, token invalidation
        │       │   ├── member/                 # Member CRUD
        │       │   ├── module/                 # Module management
        │       │   └── self/                   # Self profile view/edit
        │       ├── common/                     # Common utilities, exception handling
        │       └── config/                     # Spring config (Security, JWT, etc.)
        └── resources/
            └── application.yml / application-*.yml
```

---

## 🛠 Tech Stack

### Core

- **Spring Boot** `2.6.7` — MSA server framework
- **Java** `1.8`
- **AICA Framework** `2.6.5` — Internal common framework

### Security & Authentication

- **Spring Security** — API security filter chain
- **JWT (JSON Web Token)** — Stateless authentication tokens
- **Spring Boot Starter AOP** — Authentication/authorization AOP

### Data

- **MyBatis** `2.2.2` — SQL Mapper ORM

### API & Documentation

- **Springfox Swagger** `2.9.2` — Automatic API documentation
- **Swagger UI** — Browser-based API testing

### Monitoring & Observability

- **Spring Boot Actuator** — Health check, metrics (k8s Probe support)
- **OpenTracing + Jaeger** `3.3.1` — Distributed tracing

### Development Tools

- **Lombok** — Boilerplate reduction
- **Spring Boot DevTools** — Hot reload

---

## 🔐 Key API Domains

| Module    | Path                     | Description                             |
| --------- | ------------------------ | --------------------------------------- |
| `login`   | `/member/api/login/**`   | Login (standard / social)               |
| `logout`  | `/member/api/logout/**`  | Logout, token invalidation              |
| `auth`    | `/member/api/auth/**`    | JWT validation, menu access control     |
| `account` | `/member/api/account/**` | Account settings (password, etc.)       |
| `member`  | `/member/api/member/**`  | Member registration / edit / withdrawal |
| `self`    | `/member/api/self/**`    | Self profile view / edit                |
| `code`    | `/member/api/code/**`    | Common code lookup                      |
| `help`    | `/member/api/help/**`    | Help / inquiry                          |
| `insider` | `/member/api/insider/**` | Internal admin API                      |
| `module`  | `/member/api/module/**`  | Module-level features                   |

---

## 🚀 Getting Started

```bash
# Run
./mvnw spring-boot:run

# Build & Run
./mvnw clean package
java -jar target/ai-member-1.0.0.jar

# With profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

---

## 📖 API Documentation (Swagger)

```
http://localhost:{port}/swagger-ui.html
```

---

> ⚠️ This project is an internal authentication MSA service for the AICA Platform.
