# back-usp-msa-pms — AICA Project Management MSA Service

> A dedicated **MSA server for Project Management System (PMS)** in the AICA Platform (USP).  
> Manages the full lifecycle of business projects including announcements, applications, agreements, evaluation/selection, expert management, and performance reporting.
> ⚠️ This project is an internal PKI authentication service for the AICA Platform.

---

## 📌 Project Overview

| Item               | Details                                |
| ------------------ | -------------------------------------- |
| Artifact ID        | `ai-pms`                               |
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
back-usp-msa-pms/
├── pom.xml
├── mvnw / mvnw.cmd             # Maven Wrapper
└── src/
    └── main/
        ├── java/
        │   └── aicluster/pms/
        │       ├── AiPmsApplication.java       # Spring Boot entry point
        │       ├── api/                        # REST API layer
        │       │   ├── bsns/                   # Business basic information
        │       │   ├── bsnsapply/              # Business application
        │       │   ├── bsnsplan/               # Business plan
        │       │   ├── career/                 # Career management
        │       │   ├── cnvnchange/             # Agreement change
        │       │   ├── cnvnchangehist/         # Agreement change history
        │       │   ├── cnvncncls/              # Agreement cancellation
        │       │   ├── cnvntrmnat/             # Agreement termination
        │       │   ├── common/                 # PMS common features
        │       │   ├── evl/                    # Evaluation management
        │       │   ├── evlresult/              # Evaluation results
        │       │   ├── excclc/                 # Expense / settlement management
        │       │   ├── expert/                 # Expert management
        │       │   ├── expertClfc/             # Expert classification
        │       │   ├── expertReqst/            # Expert request
        │       │   ├── infontcn/               # Information notice
        │       │   ├── oper/                   # Operations management
        │       │   ├── pblanc/                 # Public announcement management
        │       │   ├── reprt/                  # Performance reporting
        │       │   ├── rslt/                   # Result management
        │       │   ├── selection/              # Selection management
        │       │   ├── slctnObjc/              # Selection targets
        │       │   └── stdnt/                  # Resident company / student management
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

### Monitoring & Observability

- **Spring Boot Actuator** — Health check, metrics (k8s Probe)
- **OpenTracing + Jaeger** `3.3.1` — Distributed tracing

### Development Tools

- **Lombok** — Boilerplate reduction
- **Spring Boot DevTools** — Hot reload

---

## 🗂 Key API Domains

| Module        | Description                                    |
| ------------- | ---------------------------------------------- |
| `pblanc`      | Business announcement publishing and lookup    |
| `bsnsapply`   | Business application submission and processing |
| `bsns`        | Business information CRUD                      |
| `bsnsplan`    | Business plan management                       |
| `evl`         | Evaluation item / reviewer management          |
| `evlresult`   | Evaluation result processing                   |
| `selection`   | Selection processing                           |
| `expert`      | Expert registration and management             |
| `expertReqst` | Expert application / request                   |
| `cnvnchange`  | Agreement change processing                    |
| `cnvncncls`   | Agreement cancellation                         |
| `cnvntrmnat`  | Agreement termination                          |
| `excclc`      | Expense / settlement management                |
| `reprt`       | Performance report management                  |
| `rslt`        | Final result management                        |
| `stdnt`       | Resident company / student management          |
| `career`      | Career management                              |
| `oper`        | Operations management                          |
| `infontcn`    | Information notice                             |

---

## 🚀 Getting Started

```bash
# Run
./mvnw spring-boot:run

# Build & Run
./mvnw clean package
java -jar target/ai-pms-1.0.0.jar

# With profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

---

## 📖 API Documentation (Swagger)

```
http://localhost:{port}/swagger-ui.html
```

---

> ⚠️ This project is an internal Project Management MSA service for the AICA Platform.
