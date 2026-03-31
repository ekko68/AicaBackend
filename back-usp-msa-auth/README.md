# back-usp-msa-auth — AICA 인증 MSA 서비스

> AICA 플랫폼(USP)의 **회원 인증 및 계정 관리** 전담 MSA(Microservice) 서버입니다.  
> JWT 기반 인증, 소셜/SNS 로그인, 회원가입/탈퇴, 메뉴 권한 등을 처리합니다.
> ⚠️ 이 프로젝트는 AICA 플랫폼 내부용 인증 MSA 서버입니다.

---

## 📌 프로젝트 개요

| 항목            | 내용                                   |
| --------------- | -------------------------------------- |
| 아티팩트 ID     | `ai-member`                            |
| 그룹 ID         | `aicluster`                            |
| 버전            | `1.0.0`                                |
| Java            | 1.8                                    |
| 프레임워크      | Spring Boot `2.6.7`                    |
| 내부 프레임워크 | AICA Framework `2.6.5`                 |
| 아키텍처        | MSA (JAR 패키징)                       |
| ORM             | MyBatis `2.2.2`                        |
| 배포            | Spring Boot Embedded Tomcat            |
| API 문서        | Springfox Swagger `2.9.2`              |
| 모니터링        | Spring Actuator + OpenTracing (Jaeger) |

---

## 📁 프로젝트 구조

```
back-usp-msa-auth/
├── pom.xml
├── mvnw / mvnw.cmd             # Maven Wrapper
├── thunder-tests/              # Thunder Client API 테스트 파일
└── src/
    └── main/
        ├── java/
        │   └── aicluster/member/
        │       ├── AiMemberApplication.java    # Spring Boot 진입점
        │       ├── api/                        # REST API 레이어
        │       │   ├── account/                # 계정 관리 (비밀번호 변경 등)
        │       │   ├── auth/                   # 인증 (JWT, 토큰 갱신)
        │       │   ├── code/                   # 공통 코드
        │       │   ├── help/                   # 도움말/문의
        │       │   ├── insider/                # 내부 사용자 관리
        │       │   ├── login/                  # 로그인 처리
        │       │   ├── logout/                 # 로그아웃 처리
        │       │   ├── member/                 # 회원 CRUD
        │       │   ├── module/                 # 모듈 관리
        │       │   └── self/                   # 본인 정보 조회/수정
        │       ├── common/                     # 공통 유틸, 예외처리
        │       └── config/                     # Spring 설정 (Security, JWT 등)
        └── resources/
            └── application.yml / application-*.yml
```

---

## 🛠 기술 스택

### Core

- **Spring Boot** `2.6.7` — MSA 서버 프레임워크
- **Java** `1.8`
- **AICA Framework** `2.6.5` — 사내 공통 프레임워크

### 보안 & 인증

- **Spring Security** — API 보안 필터 체인
- **JWT (JSON Web Token)** — 무상태 인증 토큰
- **Spring Boot Starter AOP** — 인증/권한 AOP 처리

### 데이터

- **MyBatis** `2.2.2` — SQL Mapper ORM

### API & 문서화

- **Springfox Swagger** `2.9.2` — API 문서 자동화
- **Swagger UI** — 브라우저 기반 API 테스트

### 모니터링 & 관찰성

- **Spring Boot Actuator** — 헬스체크, 메트릭 (k8s Probe 대응)
- **OpenTracing + Jaeger** `3.3.1` — 분산 트레이싱

### 개발 도구

- **Lombok** — 보일러플레이트 코드 제거
- **Spring Boot DevTools** — 개발 시 핫 리로드

---

## 🔐 주요 API 도메인

| 모듈      | 경로                     | 설명                     |
| --------- | ------------------------ | ------------------------ |
| `login`   | `/member/api/login/**`   | 로그인 (일반/SNS)        |
| `logout`  | `/member/api/logout/**`  | 로그아웃, 토큰 무효화    |
| `auth`    | `/member/api/auth/**`    | JWT 검증, 메뉴 권한 조회 |
| `account` | `/member/api/account/**` | 계정 설정 (비밀번호 등)  |
| `member`  | `/member/api/member/**`  | 회원 가입/수정/탈퇴      |
| `self`    | `/member/api/self/**`    | 본인 정보 조회/수정      |
| `code`    | `/member/api/code/**`    | 공통 코드 조회           |
| `help`    | `/member/api/help/**`    | 문의/도움말              |
| `insider` | `/member/api/insider/**` | 내부 관리자용 API        |
| `module`  | `/member/api/module/**`  | 모듈 단위 기능           |

---

## 🚀 실행 방법

### 설치 및 실행

```bash
# Maven Wrapper 사용
./mvnw spring-boot:run

# 또는 직접 실행
mvn spring-boot:run
```

### 빌드

```bash
./mvnw clean package
java -jar target/ai-member-1.0.0.jar
```

### 프로파일 적용

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

---

## 📖 API 문서 (Swagger)

서버 실행 후 아래 URL에서 API 명세 확인:

```
http://localhost:{port}/swagger-ui.html
```

---

## 📦 Maven 저장소

| 저장소        | URL                                                               |
| ------------- | ----------------------------------------------------------------- |
| Maven Central | `https://repo.maven.apache.org/maven2`                            |
| AICA Nexus    | `http://133.186.152.147:8081/repository/maven-aica-aisp-release/` |

---

> ⚠️ 이 프로젝트는 AICA 플랫폼 내부용 인증 MSA 서버입니다.
