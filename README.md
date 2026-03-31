# AicaBackend — AICA Platform Backend Services

> The **complete collection of backend services** for the AICA Platform (USP - User Support Portal).  
> Built on MSA (Microservice Architecture) using Spring Boot, with one legacy Monolithic WAS service.  
> ⚠️ This repository is an internal backend service collection for the AICA Platform.

---

## 🗂 Project Structure

```
AicaBackend/
├── back-usp-mon-pki/       # [MON] PKI Certificate Integration Service (Monolithic WAS)
├── back-usp-msa-auth/      # [MSA] Authentication / Member Service
├── back-usp-msa-bat/       # [MSA] Batch Service
├── back-usp-msa-cmm/       # [MSA] Common Features Service
└── back-usp-msa-pms/       # [MSA] Project Management Service
```

---

## 🏗 Architecture Overview

```
┌──────────────────────────────────────────────────────────────────┐
│                    AICA Backend Architecture                      │
│                                                                  │
│  ┌──────────────┐   ┌──────────────┐   ┌──────────────┐         │
│  │ msa-auth     │   │ msa-cmm      │   │ msa-pms      │         │
│  │ (Auth/Member)│   │ (Common)     │   │ (PMS)        │         │
│  │ ai-member    │   │ ai-common    │   │ ai-pms       │         │
│  └──────────────┘   └──────────────┘   └──────────────┘         │
│         │                  │                  │                  │
│  ┌──────┴──────────────────┴──────────────────┴──────┐           │
│  │              Shared MSA Foundation                 │           │
│  │  Spring Boot 2.6.7 / MyBatis / Security / Swagger  │           │
│  │  AICA Framework 2.6.5 / Actuator / Jaeger          │           │
│  └────────────────────────────────────────────────────┘           │
│                                                                  │
│  ┌──────────────┐   ┌──────────────┐                             │
│  │ msa-bat      │   │ mon-pki      │                             │
│  │ (Batch)      │   │ (PKI Cert)   │                             │
│  │ ai-batch     │   │ MagicLine4W  │                             │
│  └──────────────┘   └──────────────┘                             │
└──────────────────────────────────────────────────────────────────┘
```

---

## 📋 Project Summary

| Project | Artifact ID | Architecture | Role | Key Domains |
|---------|------------|--------------|------|-------------|
| `back-usp-mon-pki` | `pki` | Monolithic WAS | PKI certificate integration | MagicLine4Web, PKI, E2E encryption |
| `back-usp-msa-auth` | `ai-member` | MSA (Spring Boot) | Authentication / member management | login, auth, member, account |
| `back-usp-msa-bat` | `ai-batch` | MSA (Spring Boot) | Batch / scheduler | holiday, notification, session |
| `back-usp-msa-cmm` | `ai-common` | MSA (Spring Boot) | Common features | board, banner, popup, qna, terms |
| `back-usp-msa-pms` | `ai-pms` | MSA (Spring Boot) | Project management | bsns, evl, selection, expert, reprt |

---

## 🔵 MSA Group — Spring Boot Microservices

> `back-usp-msa-auth`, `back-usp-msa-bat`, `back-usp-msa-cmm`, `back-usp-msa-pms`  
> All share the same technology stack while independently owning their own domains.

### Shared Tech Stack (All 4 MSA Projects)

| Category | Technology |
|----------|-----------|
| **Java** | 1.8 |
| **Framework** | Spring Boot `2.6.7` |
| **Internal Framework** | AICA Framework `2.6.5` |
| **Security** | Spring Security + AOP |
| **ORM** | MyBatis `2.2.2` |
| **API Docs** | Springfox Swagger `2.9.2` |
| **Monitoring** | Spring Actuator + OpenTracing Jaeger `3.3.1` |
| **Build** | Maven (JAR packaging, Spring Boot Plugin) |
| **Dev Tools** | Lombok, Spring Boot DevTools |

### Service Domain Routing

```
front-end (React)
    │
    ├── /member/api/**   →  back-usp-msa-auth   (Auth / Member)
    ├── /common/api/**   →  back-usp-msa-cmm    (Board / Common)
    ├── /pms/api/**      →  back-usp-msa-pms    (Project Management)
    └── (scheduled)      →  back-usp-msa-bat    (Batch)
```

---

## 🟠 MON Group — Monolithic WAS

> `back-usp-mon-pki`  
> A **legacy Monolithic WAR** service (not Spring Boot MSA), dedicated to PKI certificate processing.

| Item | Details |
|------|---------|
| **Architecture** | Monolithic WAR (Apache Tomcat 9) |
| **Language** | Java 1.8 |
| **Framework** | BNET Library `1.9.2` |
| **External Solution** | Dreamsecurity MagicLine4Web |
| **Special** | Local JAR repository (`/repo`) + environment-specific build profiles |

---

## 🚀 Quick Start Summary

### MSA Projects (auth / bat / cmm / pms)

```bash
# Run
./mvnw spring-boot:run

# Build & Run
./mvnw clean package
java -jar target/{artifact-id}-1.0.0.jar

# With profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### PKI (mon-pki) — Maven Tomcat Plugin

```bash
# Local environment
mvn clean package tomcat9:run-war -P local

# Test environment
mvn clean package tomcat9:run-war -P test
```

> Default port: **80**, Context path: `/pki`

---

## 📖 API Documentation (Common)

After running any MSA service:
```
http://localhost:{port}/swagger-ui.html
```

---

## 📦 Maven Repositories (Common)

| Repository | URL |
|-----------|-----|
| Maven Central | `https://repo.maven.apache.org/maven2` |
| AICA Nexus | `http://133.186.152.147:8081/repository/maven-aica-aisp-release/` |

---

## 📄 Individual Project Documentation

| Project | Korean | English |
|---------|--------|---------|
| `back-usp-mon-pki` | [README.md](./back-usp-mon-pki/README.md) | [README_EN.md](./back-usp-mon-pki/README_EN.md) |
| `back-usp-msa-auth` | [README.md](./back-usp-msa-auth/README.md) | [README_EN.md](./back-usp-msa-auth/README_EN.md) |
| `back-usp-msa-bat` | [README.md](./back-usp-msa-bat/README.md) | [README_EN.md](./back-usp-msa-bat/README_EN.md) |
| `back-usp-msa-cmm` | [README.md](./back-usp-msa-cmm/README.md) | [README_EN.md](./back-usp-msa-cmm/README_EN.md) |
| `back-usp-msa-pms` | [README.md](./back-usp-msa-pms/README.md) | [README_EN.md](./back-usp-msa-pms/README_EN.md) |

---

> ⚠️ This repository is an internal backend service collection for the AICA Platform.
