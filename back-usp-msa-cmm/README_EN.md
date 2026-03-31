# back-usp-msa-cmm — AICA Common Features MSA Service

> A dedicated **MSA server for shared/common features** across the AICA Platform (USP).  
> Provides board, banner, popup, event, survey, Q&A, terms, and other portal-wide shared functionality.
> ⚠️ This project is an internal PKI authentication service for the AICA Platform.

---

## 📌 Project Overview

| Item               | Details                                |
| ------------------ | -------------------------------------- |
| Artifact ID        | `ai-common`                            |
| Group ID           | `aicluster`                            |
| Version            | `1.0.0`                                |
| Java               | 1.8                                    |
| Framework          | Spring Boot `2.6.7`                    |
| Internal Framework | AICA Framework `2.6.5`                 |
| Architecture       | MSA (JAR packaging)                    |
| ORM                | MyBatis `2.2.2`                        |
| API Docs           | Springfox Swagger `2.9.2`              |
| Test Coverage      | JaCoCo `0.8.6`                         |
| Monitoring         | Spring Actuator + OpenTracing (Jaeger) |

---

## 📁 Project Structure

```
back-usp-msa-cmm/
├── pom.xml
├── mvnw / mvnw.cmd             # Maven Wrapper
└── src/
    └── main/
        ├── java/
        │   └── aicluster/common/
        │       ├── AiCommonApplication.java    # Spring Boot entry point
        │       ├── api/                        # REST API layer
        │       │   ├── banner/                 # Banner management
        │       │   ├── board/                  # Board (notice, archive, news, etc.)
        │       │   ├── event/                  # Event management
        │       │   ├── holiday/                # Public holiday information
        │       │   ├── log/                    # Access / activity logs
        │       │   ├── popup/                  # Popup management
        │       │   ├── qna/                    # Q&A inquiries
        │       │   ├── survey/                 # Surveys
        │       │   └── terms/                  # Terms of service / privacy policy
        │       ├── common/                     # Common utilities, exception handling
        │       └── config/                     # Spring configuration
        └── resources/
            └── application.yml / application-*.yml
```

---

## 🛠 Tech Stack

### Core

- **Spring Boot** `2.6.7`
- **Java** `1.8`
- **AICA Framework** `2.6.5` — Internal common framework

### Security

- **Spring Security** — API security
- **Spring Boot Starter AOP** — Common processing AOP

### Data

- **MyBatis** `2.2.2` — SQL Mapper ORM

### API & Documentation

- **Springfox Swagger** `2.9.2` — API documentation

### Testing

- **JaCoCo** `0.8.6` — Code coverage analysis

### Monitoring & Observability

- **Spring Boot Actuator** — Health check, metrics
- **OpenTracing + Jaeger** `3.3.1` — Distributed tracing

### Development Tools

- **Lombok** — Boilerplate reduction
- **Spring Boot DevTools** — Hot reload

---

## 🗂 Key API Domains

| Module    | Path                     | Description                         |
| --------- | ------------------------ | ----------------------------------- |
| `banner`  | `/common/api/banner/**`  | Banner CRUD                         |
| `board`   | `/common/api/board/**`   | Board (notice, archive, news, etc.) |
| `event`   | `/common/api/event/**`   | Event management                    |
| `holiday` | `/common/api/holiday/**` | Public holiday lookup               |
| `log`     | `/common/api/log/**`     | Activity log recording              |
| `popup`   | `/common/api/popup/**`   | Popup management                    |
| `qna`     | `/common/api/qna/**`     | Q&A inquiry handling                |
| `survey`  | `/common/api/survey/**`  | Survey management                   |
| `terms`   | `/common/api/terms/**`   | Terms of service / privacy policy   |

---

## 🚀 Getting Started

```bash
# Run
./mvnw spring-boot:run

# Build & Run
./mvnw clean package
java -jar target/ai-common-1.0.0.jar

# With profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

---

## 📖 API Documentation (Swagger)

```
http://localhost:{port}/swagger-ui.html
```

---

> ⚠️ This project is an internal common features MSA service for the AICA Platform.
