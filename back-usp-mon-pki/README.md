# back-usp-mon-pki — PKI 공동인증서 연계 서비스

> ⚠️ 이 프로젝트는 AICA 플랫폼 내부용 PKI 인증 서비스입니다.
> 사용자지원포털(USP)의 **공동인증서(PKI) 솔루션 연계**를 위한 Monolithic WAS 웹서비스 프로젝트입니다.  
> 드림시큐리티 **MagicLine4Web** 솔루션을 활용한 공인인증 처리 전담 서버입니다.

---

## 📌 프로젝트 개요

| 항목        | 내용                         |
| ----------- | ---------------------------- |
| 아티팩트 ID | `pki`                        |
| 그룹 ID     | `aicluster`                  |
| 버전        | `1.0.0`                      |
| 패키징      | WAR                          |
| Java        | 1.8                          |
| 아키텍처    | Monolithic WAS (Spring MVC)  |
| 배포        | Tomcat 9 (Maven Plugin)      |
| 프레임워크  | BNET Library `1.9.2`         |
| 외부 솔루션 | MagicLine4Web (드림시큐리티) |

---

## 📁 프로젝트 구조

```
back-usp-mon-pki/
├── pom.xml                     # Maven 빌드 설정 (WAR 패키징)
├── repo/                       # 로컬 JAR 저장소 (3rd-party 라이브러리)
│   └── 3rd-party/
│       ├── jcaos-1.4.11.2.jar          # MagicLine 암호화 라이브러리
│       ├── magice2e-1.0.1.6.jar         # MagicLine E2E
│       ├── MagicJCrypto-v2.0.0.0.jar   # MagicLine JCrypto
│       ├── magicline-e2e-4.0.0.0.jar   # MagicLine E2E 통신
│       └── ml4web_server-4.5.0.7.jar   # MagicLine4Web 서버 라이브러리
└── src/
    └── main/
        ├── java/               # Java 소스 (Spring MVC Controller, Service 등)
        └── resources/
            ├── local/          # 로컬 환경 설정 (magicline 포함)
            ├── test/           # 테스트 환경 설정
            └── prod/           # 운영 환경 설정
```

---

## 🛠 기술 스택

| 분류           | 기술                                        |
| -------------- | ------------------------------------------- |
| **Java**       | 1.8                                         |
| **프레임워크** | BNET Library `1.9.2` (사내 공통 프레임워크) |
| **PKI 솔루션** | MagicLine4Web (드림시큐리티)                |
| **서버**       | Tomcat 9                                    |
| **빌드**       | Maven (WAR 패키징)                          |
| **SSO**        | json-simple `1.1.1`                         |

### 주요 3rd-party 라이브러리 (로컬 JAR)

| 라이브러리      | 버전     | 용도               |
| --------------- | -------- | ------------------ |
| `jcaos`         | 1.4.11.2 | MagicLine 암호화   |
| `magice2e`      | 1.0.1.6  | E2E 보안 통신      |
| `MagicJCrypto`  | v2.0.0.0 | JCrypto 처리       |
| `magicline-e2e` | 4.0.0.0  | MagicLine E2E      |
| `ml4web_server` | 4.5.0.7  | MagicLine4Web 서버 |

---

## ⚙️ 빌드 환경 프로파일

| 프로파일 | 설명                 |
| -------- | -------------------- |
| `local`  | 로컬 개발 환경       |
| `test`   | 테스트/스테이징 환경 |
| `prod`   | 운영 환경            |

> MagicLine4Web 설정 파일은 환경별로 분리되어 `resources/{env}/magicline/` 하위에 위치합니다.

---

## 🚀 실행 방법

### 로컬 개발 환경 (Maven Tomcat Plugin)

**방법 1: Eclipse/IDE에서 실행**

1. 프로젝트에서 `[Run As > Maven Build]` 클릭
2. Edit Configuration 창에서 입력:
   - **Goals**: `clean package tomcat9:run-war`
   - **Profiles**: `local`
3. `[Apply]` → `[Run]` 클릭

**방법 2: 커맨드라인**

```bash
# 로컬 환경
mvn clean package tomcat9:run-war -P local

# 테스트 환경
mvn clean package tomcat9:run-war -P test
```

> 기본 포트: **80**, 컨텍스트 경로: `/pki`

### 종료 방법

- IDE Console에서 해당 Maven Build Console 창 선택 후 **Terminate (■)** 버튼 클릭

---

## 🔐 주요 기능

- **공동인증서(PKI) 처리**: MagicLine4Web을 통한 공인 전자서명 및 인증서 검증
- **E2E 암호화**: 클라이언트-서버 간 암호화 통신 처리
- **SSO 연계**: json-simple을 활용한 SSO 토큰 처리

---

## 📦 Maven 저장소

| 저장소        | URL                                                               |
| ------------- | ----------------------------------------------------------------- |
| Maven Central | `https://repo.maven.apache.org/maven2`                            |
| AICA Nexus    | `http://133.186.152.147:8081/repository/maven-aica-aisp-release/` |
| 로컬 JAR      | `file://${project.basedir}/repo`                                  |

---

> ⚠️ 이 프로젝트는 AICA 플랫폼 내부용 PKI 인증 서비스입니다.
