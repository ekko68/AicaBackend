# back-usp-msa-cmm — AICA 공통기능 MSA 서비스

> AICA 플랫폼(USP)의 **공통 기능** 전담 MSA 서버입니다.  
> 게시판, 배너, 팝업, 이벤트, 설문, Q&A, 약관 등 포털 전반에서 공유되는 기능을 제공합니다.
> ⚠️ 이 프로젝트는 AICA 플랫폼 내부용 공통기능 MSA 서버입니다.

---

## 📌 프로젝트 개요

| 항목            | 내용                                   |
| --------------- | -------------------------------------- |
| 아티팩트 ID     | `ai-common`                            |
| 그룹 ID         | `aicluster`                            |
| 버전            | `1.0.0`                                |
| Java            | 1.8                                    |
| 프레임워크      | Spring Boot `2.6.7`                    |
| 내부 프레임워크 | AICA Framework `2.6.5`                 |
| 아키텍처        | MSA (JAR 패키징)                       |
| ORM             | MyBatis `2.2.2`                        |
| API 문서        | Springfox Swagger `2.9.2`              |
| 테스트 커버리지 | JaCoCo `0.8.6`                         |
| 모니터링        | Spring Actuator + OpenTracing (Jaeger) |

---

## 📁 프로젝트 구조

```
back-usp-msa-cmm/
├── pom.xml
├── mvnw / mvnw.cmd             # Maven Wrapper
└── src/
    └── main/
        ├── java/
        │   └── aicluster/common/
        │       ├── AiCommonApplication.java    # Spring Boot 진입점
        │       ├── api/                        # REST API 레이어
        │       │   ├── banner/                 # 배너 관리
        │       │   ├── board/                  # 게시판 (공지, 자료실 등)
        │       │   ├── event/                  # 이벤트 관리
        │       │   ├── holiday/                # 공휴일 정보
        │       │   ├── log/                    # 접속/활동 로그
        │       │   ├── popup/                  # 팝업 관리
        │       │   ├── qna/                    # Q&A 문의
        │       │   ├── survey/                 # 설문조사
        │       │   └── terms/                  # 약관 관리
        │       ├── common/                     # 공통 유틸, 예외처리
        │       └── config/                     # Spring 설정
        └── resources/
            └── application.yml / application-*.yml
```

---

## 🛠 기술 스택

### Core

- **Spring Boot** `2.6.7`
- **Java** `1.8`
- **AICA Framework** `2.6.5` — 사내 공통 프레임워크

### 보안

- **Spring Security** — API 보안
- **Spring Boot Starter AOP** — 공통 처리 AOP

### 데이터

- **MyBatis** `2.2.2` — SQL Mapper ORM

### API & 문서화

- **Springfox Swagger** `2.9.2` — API 문서 자동화

### 테스트

- **JaCoCo** `0.8.6` — 코드 커버리지 분석

### 모니터링 & 관찰성

- **Spring Boot Actuator** — 헬스체크, 메트릭
- **OpenTracing + Jaeger** `3.3.1` — 분산 트레이싱

### 개발 도구

- **Lombok** — 보일러플레이트 제거
- **Spring Boot DevTools** — 핫 리로드

---

## 🗂 주요 API 도메인

| 모듈      | 경로                     | 설명                           |
| --------- | ------------------------ | ------------------------------ |
| `banner`  | `/common/api/banner/**`  | 배너 CRUD                      |
| `board`   | `/common/api/board/**`   | 게시판 (공지, 자료실, 뉴스 등) |
| `event`   | `/common/api/event/**`   | 이벤트 관리                    |
| `holiday` | `/common/api/holiday/**` | 공휴일 조회                    |
| `log`     | `/common/api/log/**`     | 활동 로그 기록                 |
| `popup`   | `/common/api/popup/**`   | 팝업 관리                      |
| `qna`     | `/common/api/qna/**`     | Q&A 문의 처리                  |
| `survey`  | `/common/api/survey/**`  | 설문조사                       |
| `terms`   | `/common/api/terms/**`   | 이용약관 / 개인정보처리방침    |

---

## 🚀 실행 방법

### 실행

```bash
./mvnw spring-boot:run
```

### 빌드

```bash
./mvnw clean package
java -jar target/ai-common-1.0.0.jar
```

### 프로파일 적용

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

---

## 📖 API 문서 (Swagger)

```
http://localhost:{port}/swagger-ui.html
```

---

> ⚠️ 이 프로젝트는 AICA 플랫폼 내부용 공통기능 MSA 서버입니다.
