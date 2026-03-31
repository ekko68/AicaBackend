# back-usp-mon-pki — PKI Certificate Integration Service

> A **Monolithic WAS web service** for integrating the PKI (Public Key Infrastructure) certificate solution into the AICA User Support Portal (USP).  
> Handles digital certificate authentication using the **MagicLine4Web** solution by Dreamsecurity.
> ⚠️ This project is an internal PKI authentication service for the AICA Platform.

---

## 📌 Project Overview

| Item              | Details                       |
| ----------------- | ----------------------------- |
| Artifact ID       | `pki`                         |
| Group ID          | `aicluster`                   |
| Version           | `1.0.0`                       |
| Packaging         | WAR                           |
| Java              | 1.8                           |
| Architecture      | Monolithic WAS (Spring MVC)   |
| Deployment        | Tomcat 9 (Maven Plugin)       |
| Framework         | BNET Library `1.9.2`          |
| External Solution | MagicLine4Web (Dreamsecurity) |

---

## 📁 Project Structure

```
back-usp-mon-pki/
├── pom.xml                     # Maven build config (WAR packaging)
├── repo/                       # Local JAR repository (3rd-party libraries)
│   └── 3rd-party/
│       ├── jcaos-1.4.11.2.jar          # MagicLine encryption library
│       ├── magice2e-1.0.1.6.jar         # MagicLine E2E
│       ├── MagicJCrypto-v2.0.0.0.jar   # MagicLine JCrypto
│       ├── magicline-e2e-4.0.0.0.jar   # MagicLine E2E communication
│       └── ml4web_server-4.5.0.7.jar   # MagicLine4Web server library
└── src/
    └── main/
        ├── java/               # Java source (Spring MVC Controller, Service, etc.)
        └── resources/
            ├── local/          # Local environment config (incl. magicline)
            ├── test/           # Test/staging environment config
            └── prod/           # Production environment config
```

---

## 🛠 Tech Stack

| Category         | Technology                                       |
| ---------------- | ------------------------------------------------ |
| **Java**         | 1.8                                              |
| **Framework**    | BNET Library `1.9.2` (internal common framework) |
| **PKI Solution** | MagicLine4Web (Dreamsecurity)                    |
| **Server**       | Tomcat 9                                         |
| **Build**        | Maven (WAR packaging)                            |
| **SSO**          | json-simple `1.1.1`                              |

### Key 3rd-party Libraries (Local JAR)

| Library         | Version  | Purpose                  |
| --------------- | -------- | ------------------------ |
| `jcaos`         | 1.4.11.2 | MagicLine encryption     |
| `magice2e`      | 1.0.1.6  | E2E secure communication |
| `MagicJCrypto`  | v2.0.0.0 | JCrypto processing       |
| `magicline-e2e` | 4.0.0.0  | MagicLine E2E            |
| `ml4web_server` | 4.5.0.7  | MagicLine4Web server     |

---

## ⚙️ Build Profiles

| Profile | Description                   |
| ------- | ----------------------------- |
| `local` | Local development environment |
| `test`  | Test / staging environment    |
| `prod`  | Production environment        |

> MagicLine4Web config files are separated per environment under `resources/{env}/magicline/`.

---

## 🚀 Getting Started

### Local Development (Maven Tomcat Plugin)

**Option 1: IDE (Eclipse)**

1. Right-click project → `[Run As > Maven Build]`
2. In Edit Configuration:
   - **Goals**: `clean package tomcat9:run-war`
   - **Profiles**: `local`
3. Click `[Apply]` → `[Run]`

**Option 2: Command Line**

```bash
# Local environment
mvn clean package tomcat9:run-war -P local

# Test environment
mvn clean package tomcat9:run-war -P test
```

> Default port: **80**, Context path: `/pki`

### Stop Server

- Select the Maven Build Console in IDE → Click **Terminate (■)** button

---

## 🔐 Key Features

- **PKI Certificate Processing**: Digital signature verification and certificate validation via MagicLine4Web
- **E2E Encryption**: Encrypted client-server communication
- **SSO Integration**: SSO token processing using json-simple

---

## 📦 Maven Repositories

| Repository    | URL                                                               |
| ------------- | ----------------------------------------------------------------- |
| Maven Central | `https://repo.maven.apache.org/maven2`                            |
| AICA Nexus    | `http://133.186.152.147:8081/repository/maven-aica-aisp-release/` |
| Local JAR     | `file://${project.basedir}/repo`                                  |

---

> ⚠️ This project is an internal PKI authentication service for the AICA Platform.
