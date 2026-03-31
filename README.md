# AicaBackend — AICA 플랫폼 백엔드 서비스 모음

> AICA 플랫폼(USP - 사용자지원포털)의 **전체 백엔드 프로젝트 모음**입니다.  
> MSA(Microservice Architecture) 기반의 Spring Boot 서비스와 레거시 Monolithic WAS로 구성됩니다.
> ⚠️ 이 저장소는 AICA 플랫폼 내부용 백엔드 서비스 모음입니다.

---

## 🗂 프로젝트 구조

```
AicaBackend/
├── back-usp-mon-pki/       # [MON] PKI 공동인증서 연계 서비스 (Monolithic WAS)
├── back-usp-msa-auth/      # [MSA] 인증/회원 서비스
├── back-usp-msa-bat/       # [MSA] 배치 서비스
├── back-usp-msa-cmm/       # [MSA] 공통기능 서비스
└── back-usp-msa-pms/       # [MSA] 사업관리 서비스
```

---

## 🏗 아키텍처 구성

```
┌──────────────────────────────────────────────────────────────────┐
│                     AICA 백엔드 아키텍처                          │
│                                                                  │
│  ┌──────────────┐   ┌──────────────┐   ┌──────────────┐         │
│  │ msa-auth     │   │ msa-cmm      │   │ msa-pms      │         │
│  │ (인증/회원)   │   │ (공통기능)    │   │ (사업관리)    │         │
│  │ ai-member    │   │ ai-common    │   │ ai-pms       │         │
│  └──────────────┘   └──────────────┘   └──────────────┘         │
│         │                  │                  │                  │
│  ┌──────┴──────────────────┴──────────────────┴──────┐           │
│  │              Spring Boot MSA 공통 기반              │           │
│  │  Spring Boot 2.6.7 / MyBatis / Security / Swagger  │           │
│  │  AICA Framework 2.6.5 / Actuator / Jaeger          │           │
│  └────────────────────────────────────────────────────┘           │
│                                                                  │
│  ┌──────────────┐   ┌──────────────┐                             │
│  │ msa-bat      │   │ mon-pki      │                             │
│  │ (배치처리)    │   │ (PKI 인증서)  │                             │
│  │ ai-batch     │   │ MagicLine4W  │                             │
│  └──────────────┘   └──────────────┘                             │
└──────────────────────────────────────────────────────────────────┘
```

---

## 📋 프로젝트 별 역할 요약

| 프로젝트 | 아티팩트 ID | 아키텍처 | 역할 | 주요 도메인 |
|----------|------------|---------|------|------------|
| `back-usp-mon-pki` | `pki` | Monolithic WAS | 공동인증서(PKI) 연계 | MagicLine4Web, PKI, E2E 암호화 |
| `back-usp-msa-auth` | `ai-member` | MSA (Spring Boot) | 인증 / 회원관리 | login, auth, member, account |
| `back-usp-msa-bat` | `ai-batch` | MSA (Spring Boot) | 배치 / 스케줄러 | holiday, notification, session |
| `back-usp-msa-cmm` | `ai-common` | MSA (Spring Boot) | 공통기능 | board, banner, popup, qna, terms |
| `back-usp-msa-pms` | `ai-pms` | MSA (Spring Boot) | 사업관리 | bsns, evl, selection, expert, reprt |

---

## 🔵 MSA 그룹 — Spring Boot 기반 마이크로서비스

> `back-usp-msa-auth`, `back-usp-msa-bat`, `back-usp-msa-cmm`, `back-usp-msa-pms`  
> 동일한 기술 스택을 기반으로 각자 독립된 도메인을 담당합니다.

### 공통 기술 스택 (MSA 4개)

| 분류 | 기술 |
|------|------|
| **Java** | 1.8 |
| **프레임워크** | Spring Boot `2.6.7` |
| **사내 프레임워크** | AICA Framework `2.6.5` |
| **보안** | Spring Security + AOP |
| **ORM** | MyBatis `2.2.2` |
| **API 문서** | Springfox Swagger `2.9.2` |
| **모니터링** | Spring Actuator + OpenTracing Jaeger `3.3.1` |
| **빌드** | Maven (JAR 패키징, Spring Boot Plugin) |
| **개발 도구** | Lombok, Spring Boot DevTools |

### 서비스 간 역할 분리

```
front-end (React)
    │
    ├── /member/api/**   →  back-usp-msa-auth   (인증/회원)
    ├── /common/api/**   →  back-usp-msa-cmm    (게시판/공통)
    ├── /pms/api/**      →  back-usp-msa-pms    (사업관리)
    └── (스케줄)         →  back-usp-msa-bat    (배치)
```

---

## 🟠 MON 그룹 — Monolithic WAS

> `back-usp-mon-pki`  
> Spring Boot MSA가 아닌 **레거시 Monolithic WAS** 구조로, 공동인증서 전용 처리 서버입니다.

| 항목 | 내용 |
|------|------|
| **아키텍처** | Monolithic WAR (Apache Tomcat 9) |
| **언어** | Java 1.8 |
| **프레임워크** | BNET Library `1.9.2` |
| **외부 솔루션** | 드림시큐리티 MagicLine4Web |
| **특징** | 로컬 JAR 저장소(`/repo`) 포함, 환경별 빌드 프로파일 지원 |

---

## 🚀 실행 방법 요약

### MSA 프로젝트 (auth / bat / cmm / pms)

```bash
# 실행
./mvnw spring-boot:run

# 빌드 후 실행
./mvnw clean package
java -jar target/{artifact-id}-1.0.0.jar

# 프로파일 지정
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### PKI (mon-pki) — Maven Tomcat Plugin

```bash
# 로컬 환경 실행
mvn clean package tomcat9:run-war -P local

# 테스트 환경 실행
mvn clean package tomcat9:run-war -P test
```

> 기본 포트: **80**, 컨텍스트 경로: `/pki`

---

## 📖 API 문서 (공통)

각 MSA 서비스 실행 후 아래에서 확인:
```
http://localhost:{port}/swagger-ui.html
```

---

## 📦 Maven 저장소 (공통)

| 저장소 | URL |
|-------|-----|
| Maven Central | `https://repo.maven.apache.org/maven2` |
| AICA Nexus | `http://133.186.152.147:8081/repository/maven-aica-aisp-release/` |

---

## 📄 각 프로젝트 상세 문서

| 프로젝트 | 한국어 | 영어 |
|----------|--------|------|
| `back-usp-mon-pki` | [README.md](./back-usp-mon-pki/README.md) | [README_EN.md](./back-usp-mon-pki/README_EN.md) |
| `back-usp-msa-auth` | [README.md](./back-usp-msa-auth/README.md) | [README_EN.md](./back-usp-msa-auth/README_EN.md) |
| `back-usp-msa-bat` | [README.md](./back-usp-msa-bat/README.md) | [README_EN.md](./back-usp-msa-bat/README_EN.md) |
| `back-usp-msa-cmm` | [README.md](./back-usp-msa-cmm/README.md) | [README_EN.md](./back-usp-msa-cmm/README_EN.md) |
| `back-usp-msa-pms` | [README.md](./back-usp-msa-pms/README.md) | [README_EN.md](./back-usp-msa-pms/README_EN.md) |

---

> ⚠️ 이 저장소는 AICA 플랫폼 내부용 백엔드 서비스 모음입니다.
