# back-usp-msa-pms — AICA 사업관리 MSA 서비스

> AICA 플랫폼(USP)의 **사업관리(PMS, Project Management System)** 전담 MSA 서버입니다.  
> 사업 공고, 신청, 협약, 평가/선정, 전문가 관리, 성과 보고 등 사업 전 주기를 처리합니다.
> ⚠️ 이 프로젝트는 AICA 플랫폼 내부용 사업관리 MSA 서버입니다.

---

## 📌 프로젝트 개요

| 항목            | 내용                                   |
| --------------- | -------------------------------------- |
| 아티팩트 ID     | `ai-pms`                               |
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
back-usp-msa-pms/
├── pom.xml
├── mvnw / mvnw.cmd             # Maven Wrapper
└── src/
    └── main/
        ├── java/
        │   └── aicluster/pms/
        │       ├── AiPmsApplication.java       # Spring Boot 진입점
        │       ├── api/                        # REST API 레이어
        │       │   ├── bsns/                   # 사업 기본 정보
        │       │   ├── bsnsapply/              # 사업 신청
        │       │   ├── bsnsplan/               # 사업 계획
        │       │   ├── career/                 # 경력 관리
        │       │   ├── cnvnchange/             # 협약 변경
        │       │   ├── cnvnchangehist/         # 협약 변경 이력
        │       │   ├── cnvncncls/              # 협약 해지
        │       │   ├── cnvntrmnat/             # 협약 종료
        │       │   ├── common/                 # PMS 공통 기능
        │       │   ├── evl/                    # 평가 관리
        │       │   ├── evlresult/              # 평가 결과
        │       │   ├── excclc/                 # 정산/지출 관리
        │       │   ├── expert/                 # 전문가 관리
        │       │   ├── expertClfc/             # 전문가 분류
        │       │   ├── expertReqst/            # 전문가 요청
        │       │   ├── infontcn/               # 정보 공지
        │       │   ├── oper/                   # 운영 관리
        │       │   ├── pblanc/                 # 공고 관리
        │       │   ├── reprt/                  # 성과 보고
        │       │   ├── rslt/                   # 결과 관리
        │       │   ├── selection/              # 선정 관리
        │       │   ├── slctnObjc/              # 선정 대상
        │       │   └── stdnt/                  # 학생/입주기업 관리
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

### 모니터링 & 관찰성

- **Spring Boot Actuator** — 헬스체크, 메트릭 (k8s Probe)
- **OpenTracing + Jaeger** `3.3.1` — 분산 트레이싱

### 개발 도구

- **Lombok** — 보일러플레이트 제거
- **Spring Boot DevTools** — 핫 리로드

---

## 🗂 주요 API 도메인

| 모듈          | 설명                   |
| ------------- | ---------------------- |
| `pblanc`      | 사업 공고 발행 및 조회 |
| `bsnsapply`   | 사업 신청 접수 및 처리 |
| `bsns`        | 사업 정보 CRUD         |
| `bsnsplan`    | 사업 계획서 관리       |
| `evl`         | 평가 항목/위원 관리    |
| `evlresult`   | 평가 결과 처리         |
| `selection`   | 선정 처리              |
| `expert`      | 전문가 등록 및 관리    |
| `expertReqst` | 전문가 지원/요청       |
| `cnvnchange`  | 협약 변경 처리         |
| `cnvncncls`   | 협약 해지              |
| `cnvntrmnat`  | 협약 종료              |
| `excclc`      | 지출/정산 관리         |
| `reprt`       | 성과 보고서 관리       |
| `rslt`        | 최종 결과 관리         |
| `stdnt`       | 입주기업 / 학생 관리   |
| `career`      | 경력 관리              |
| `oper`        | 운영 관리              |
| `infontcn`    | 정보 공지              |

---

## 🚀 실행 방법

### 실행

```bash
./mvnw spring-boot:run
```

### 빌드

```bash
./mvnw clean package
java -jar target/ai-pms-1.0.0.jar
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

> ⚠️ 이 프로젝트는 AICA 플랫폼 내부용 사업관리 MSA 서버입니다.
