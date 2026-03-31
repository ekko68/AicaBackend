# back-usp-msa-bat — AICA Batch MSA 서비스

> AICA 플랫폼(USP)의 **배치(정기/비정기) 처리** 전담 MSA 서버입니다.  
> 휴일 정보, 회원 상태, 알림 발송, 세션 정리 등 스케줄링 기반 작업을 처리합니다.
> ⚠️ 이 프로젝트는 AICA 플랫폼 내부용 배치 MSA 서버입니다.

---

## 📌 프로젝트 개요

| 항목            | 내용                                   |
| --------------- | -------------------------------------- |
| 아티팩트 ID     | `ai-batch`                             |
| 그룹 ID         | `aicluster`                            |
| 버전            | `1.0.0`                                |
| Java            | 1.8                                    |
| 프레임워크      | Spring Boot `2.6.7`                    |
| 내부 프레임워크 | AICA Framework `2.6.5`                 |
| 아키텍처        | MSA (JAR 패키징)                       |
| ORM             | MyBatis `2.2.2`                        |
| API 문서        | Springfox Swagger `2.9.2`              |
| 모니터링        | Spring Actuator + OpenTracing (Jaeger) |

---

## 📁 프로젝트 구조

```
back-usp-msa-bat/
├── pom.xml
├── mvnw / mvnw.cmd             # Maven Wrapper
└── src/
    └── main/
        ├── java/
        │   └── aicluster/batch/
        │       ├── AiBatchApplication.java     # Spring Boot 진입점
        │       ├── api/                        # REST API / Scheduler 레이어
        │       │   ├── holiday/                # 공휴일 정보 배치 처리
        │       │   ├── member/                 # 회원 상태 배치 (휴면, 만료 등)
        │       │   ├── mvn/                    # Maven 관련 유틸 배치
        │       │   ├── notification/           # 알림 발송 배치
        │       │   └── session/               # 세션 정리 배치
        │       ├── common/                     # 공통 유틸, 예외처리
        │       └── config/                     # Spring 설정 (스케줄러, Security 등)
        └── resources/
            └── application.yml / application-*.yml
```

---

## 🛠 기술 스택

### Core

- **Spring Boot** `2.6.7` — MSA 서버 프레임워크
- **Java** `1.8`
- **AICA Framework** `2.6.5` — 사내 공통 프레임워크

### 보안

- **Spring Security** — API 보안
- **Spring Boot Starter AOP** — 공통 처리 AOP

### 데이터

- **MyBatis** `2.2.2` — SQL Mapper ORM

### API & 문서화

- **Springfox Swagger** `2.9.2` — API 문서 자동화

### 모니터링 & 관찰성

- **Spring Boot Actuator** — 헬스체크, 메트릭
- **OpenTracing + Jaeger** `3.3.1` — 분산 트레이싱

### 개발 도구

- **Lombok** — 보일러플레이트 제거
- **Spring Boot DevTools** — 핫 리로드

---

## 🔄 주요 배치 도메인

| 모듈           | 설명                           |
| -------------- | ------------------------------ |
| `holiday`      | 공휴일 정보 수집 및 DB 갱신    |
| `member`       | 휴면 회원 전환, 계정 만료 처리 |
| `notification` | 이메일/SMS 알림 발송 스케줄러  |
| `session`      | 만료 세션 정리                 |
| `mvn`          | 내부 Maven 관련 유틸 작업      |

---

## 🚀 실행 방법

### 실행

```bash
./mvnw spring-boot:run
```

### 빌드

```bash
./mvnw clean package
java -jar target/ai-batch-1.0.0.jar
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

> ⚠️ 이 프로젝트는 AICA 플랫폼 내부용 배치 MSA 서버입니다.
