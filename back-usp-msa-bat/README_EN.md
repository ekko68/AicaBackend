# back-usp-msa-bat — AICA Batch MSA Service

> A dedicated **MSA batch processing server** for the AICA Platform (USP).  
> Handles scheduled and event-driven tasks including holiday sync, member status updates, notifications, and session cleanup.
> ⚠️ This project is an internal PKI authentication service for the AICA Platform.

---

## 📌 Project Overview

| Item               | Details                                |
| ------------------ | -------------------------------------- |
| Artifact ID        | `ai-batch`                             |
| Group ID           | `aicluster`                            |
| Version            | `1.0.0`                                |
| Java               | 1.8                                    |
| Framework          | Spring Boot `2.6.7`                    |
| Internal Framework | AICA Framework `2.6.5`                 |
| Architecture       | MSA (JAR packaging)                    |
| ORM                | MyBatis `2.2.2`                        |
| API Docs           | Springfox Swagger `2.9.2`              |
| Monitoring         | Spring Actuator + OpenTracing (Jaeger) |

---

## 📁 Project Structure

```
back-usp-msa-bat/
├── pom.xml
├── mvnw / mvnw.cmd             # Maven Wrapper
└── src/
    └── main/
        ├── java/
        │   └── aicluster/batch/
        │       ├── AiBatchApplication.java     # Spring Boot entry point
        │       ├── api/                        # REST API / Scheduler layer
        │       │   ├── holiday/                # Public holiday data sync
        │       │   ├── member/                 # Member status batch (dormant, expiry)
        │       │   ├── mvn/                    # Maven utility batch
        │       │   ├── notification/           # Notification dispatch scheduler
        │       │   └── session/                # Session cleanup batch
        │       ├── common/                     # Common utilities, exception handling
        │       └── config/                     # Spring config (Scheduler, Security, etc.)
        └── resources/
            └── application.yml / application-*.yml
```

---

## 🛠 Tech Stack

### Core

- **Spring Boot** `2.6.7` — MSA server framework
- **Java** `1.8`
- **AICA Framework** `2.6.5` — Internal common framework

### Security

- **Spring Security** — API security
- **Spring Boot Starter AOP** — Common processing AOP

### Data

- **MyBatis** `2.2.2` — SQL Mapper ORM

### API & Documentation

- **Springfox Swagger** `2.9.2` — API documentation

### Monitoring & Observability

- **Spring Boot Actuator** — Health check, metrics
- **OpenTracing + Jaeger** `3.3.1` — Distributed tracing

### Development Tools

- **Lombok** — Boilerplate reduction
- **Spring Boot DevTools** — Hot reload

---

## 🔄 Key Batch Domains

| Module         | Description                                 |
| -------------- | ------------------------------------------- |
| `holiday`      | Sync public holiday data to the database    |
| `member`       | Convert dormant accounts, handle expiry     |
| `notification` | Email / SMS notification dispatch scheduler |
| `session`      | Clean up expired sessions                   |
| `mvn`          | Internal Maven utility tasks                |

---

## 🚀 Getting Started

```bash
# Run
./mvnw spring-boot:run

# Build & Run
./mvnw clean package
java -jar target/ai-batch-1.0.0.jar

# With profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

---

## 📖 API Documentation (Swagger)

```
http://localhost:{port}/swagger-ui.html
```

---

> ⚠️ This project is an internal batch MSA service for the AICA Platform.
