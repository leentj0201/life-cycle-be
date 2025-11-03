# 생애주기 관리 시스템 (Life Cycle Management System)

교회의 생애주기별 교육 과정과 부서 관리를 위한 Spring Boot 기반 REST API 시스템입니다.

## 🏗 환경 구성

### 3단계 환경 구성
1. **LOCAL**: 로컬 개발 환경 + INT-DB
2. **INT**: EC2 통합 테스트 환경 + INT-DB  
3. **PROD**: 운영 환경 + PROD-DB (미래 구성)

자세한 환경 설정은 [ENVIRONMENT_SETUP.md](ENVIRONMENT_SETUP.md)를 참조하세요.

## 🚀 빠른 시작

### 로컬 개발 환경
```bash
# 서버 실행
./gradlew bootRun

# API 테스트
curl -X POST http://localhost:8080/api/church/list -H "Content-Type: application/json" -d '{}'

# Swagger UI
open http://localhost:8080/swagger-ui.html
```

### INT 서버 배포
```bash
# INT 환경으로 배포
./deploy-to-int.sh

# INT 서버 테스트
curl -X POST http://3.34.74.224:8080/api/church/list -H "Content-Type: application/json" -d '{}'
```

## 🌐 접속 URL

### LOCAL 환경
- **API Base**: `http://localhost:8080/api`
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **API Docs**: `http://localhost:8080/v3/api-docs`

### INT 환경  
- **API Base**: `http://3.34.74.224:8080/api`
- **Swagger UI**: `http://3.34.74.224:8080/swagger-ui.html`
- **API Docs**: `http://3.34.74.224:8080/v3/api-docs`

## 📋 주요 기능

### 핵심 도메인 (9개)
- 🏛 **교회 관리** (Church Management)
- 👥 **회원 관리** (Member Management)  
- 🏢 **부서 관리** (Department Management)
- 🔄 **생애주기 관리** (Lifecycle Management)
- 📄 **부서 컨텐츠** (Department Content)
- 📄 **생애주기 컨텐츠** (Lifecycle Content)
- 🤝 **회원-부서 관계** (Member-Department Relations)
- 🔄 **회원-생애주기 관계** (Member-Lifecycle Relations)
- 👨‍👩‍👧‍👦 **가족 관계 관리** (Family Relations)
- ⚙️ **회원 설정** (Member Settings)

### API 특징
- ✅ **완전한 CRUD** 지원 (Create, Read, Update, Delete)
- ✅ **RESTful API** 설계 (모든 요청은 POST + JSON Body)
- ✅ **Swagger 문서화** (상호작용 가능한 API 문서)
- ✅ **논리적 삭제** (데이터 무결성 보장)
- ✅ **JPA Auditing** (생성/수정 이력 자동 관리)

## 🛠 기술 스택

- **Framework**: Spring Boot 3.2
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA (Hibernate)
- **Documentation**: Swagger/OpenAPI 3.0
- **Build Tool**: Gradle
- **Java Version**: 17
- **Testing**: JUnit 5, Spring Boot Test

## 📖 문서

- 📚 **[환경 설정 가이드](ENVIRONMENT_SETUP.md)** - LOCAL/INT/PROD 환경 구성
- 🚀 **[배포 가이드](DEPLOYMENT.md)** - Docker 및 스크립트 배포
- 🚀 **[운영 배포 가이드](PRODUCTION_DEPLOYMENT.md)** - 운영 환경 배포 상세 가이드
- 📋 **[API 명세서](API_SPECIFICATION.md)** - 상세 API 문서
- 🧪 **[Postman Collection](life-cycle-api.postman_collection.json)** - API 테스트용

## 🧪 테스트

### 단위 테스트 실행
```bash
# 전체 테스트 실행
./gradlew test

# 특정 테스트 클래스 실행
./gradlew test --tests "ChurchRepositoryTest"

# 테스트 결과 확인
./gradlew test --info
```

### 현재 테스트 현황
- **총 66개 테스트** 모두 통과 ✅
- **Repository 테스트**: JPA 레포지토리 기능 검증
- **Service 테스트**: 비즈니스 로직 검증  
- **Controller 테스트**: REST API 엔드포인트 검증

## 📦 배포 스크립트

```bash
# 로컬 서버 실행/중지
./deploy.sh      # 로컬 서버 시작
./stop.sh        # 로컬 서버 중지

# INT 서버 배포
./deploy-to-int.sh    # INT 환경으로 배포

# 운영 서버 배포 (미래)
./deploy-to-production.sh  # PROD 환경으로 배포 (미구성)
```

## 🔧 개발 환경 설정

### 필수 요구사항
- Java 17+
- Gradle 7.0+
- PostgreSQL 13+

### 로컬 개발 설정
1. 레포지토리 클론
2. `application.yml` 데이터베이스 설정 확인
3. `./gradlew bootRun` 실행
4. `http://localhost:8080/swagger-ui.html` 접속

### IDE 설정 (IntelliJ IDEA)
- Annotation Processing 활성화 (Lombok)
- Google Java Code Style 적용
- Spring Boot DevTools 활성화

## 🌟 특별한 설계 특징

### 1. 통일된 API 설계
모든 API는 POST 요청과 JSON RequestBody를 사용:
```bash
# 모든 CRUD 요청이 동일한 패턴
POST /api/church/create    # 생성
POST /api/church/get       # 조회  
POST /api/church/list      # 목록
POST /api/church/update    # 수정
POST /api/church/delete    # 삭제
```

### 2. 중앙집중식 엔드포인트 관리
```java
// ApiEndpoints.java - 모든 API 경로를 상수로 관리
ApiEndpoints.Church.FULL_CREATE  // "/api/church/create"
ApiEndpoints.Member.FULL_LIST    // "/api/member/list"
```

### 3. BaseEntity 상속 구조
```java
// 모든 엔티티는 공통 필드 자동 관리
@MappedSuperclass
public abstract class BaseEntity {
    private LocalDateTime createdAt;    // 생성일시 자동
    private UUID createdBy;             // 생성자 ID
    private LocalDateTime updatedAt;    // 수정일시 자동  
    private UUID updatedBy;             // 수정자 ID
}
```

## 🤝 기여 방법

1. 이슈 생성 또는 확인
2. 기능 브랜치 생성 (`feature/amazing-feature`)
3. 변경사항 커밋 (`git commit -m 'Add some AmazingFeature'`)
4. 브랜치에 푸시 (`git push origin feature/AmazingFeature`)
5. Pull Request 생성

## 📝 라이선스

이 프로젝트는 Apache 2.0 라이선스 하에 배포됩니다.

## 📞 지원 및 문의

- **개발팀**: TwoThree Team
- **이메일**: support@twothree.com
- **문서 업데이트**: 2024-01-15

---

> 💡 **Tip**: 프로젝트 시작 전에 [ENVIRONMENT_SETUP.md](ENVIRONMENT_SETUP.md)를 반드시 읽어보세요!