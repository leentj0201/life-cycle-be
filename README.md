# 생애주기 관리 시스템 (Life-Cycle Management System)

교회 생애주기 관리를 위한 백엔드 REST API 시스템입니다.

## 📋 프로젝트 정보

- **프레임워크**: Spring Boot 3.2.0
- **언어**: Java 17
- **빌드 도구**: Gradle 8.14
- **데이터베이스**: PostgreSQL
- **API 문서**: Swagger (OpenAPI 3.0)

## 🚀 빠른 시작

### 1. 개발 환경 실행
```bash
./gradlew bootRun
```

### 2. 배포 환경 실행
```bash
./deploy.sh
```

### 3. 서버 중지
```bash
./stop.sh
```

## 🏗️ 프로젝트 구조

```
src/
├── main/
│   ├── java/com/twothree/backend/
│   │   ├── config/          # 설정 클래스
│   │   ├── constant/        # API 엔드포인트 상수
│   │   ├── controller/      # REST API 컨트롤러 (10개)
│   │   ├── dto/             # 요청/응답 DTO (61개)
│   │   ├── entity/          # JPA 엔티티 (14개)
│   │   ├── exception/       # 전역 예외 처리
│   │   ├── repository/      # JPA Repository (10개)
│   │   └── service/         # 비즈니스 로직 (10개)
│   └── resources/
│       ├── application.yml       # 기본 설정
│       ├── application-prod.yml  # 운영 환경 설정
│       └── application-test.yml  # 테스트 환경 설정
└── test/
    └── java/                # 테스트 코드 (17개)
```

## 📚 도메인 모델

### 핵심 도메인 (10개)
1. **Church** - 교회
2. **Member** - 회원
3. **Department** - 부서
4. **Lifecycle** - 생애주기
5. **DepartmentContent** - 부서 콘텐츠
6. **LifecycleContent** - 생애주기 콘텐츠
7. **MemberDepartment** - 회원-부서 매핑
8. **MemberLifecycle** - 회원-생애주기 매핑
9. **MemberFamily** - 가족 관계
10. **MemberSetting** - 회원 설정

## 🌐 API 엔드포인트

### Swagger UI
```
http://localhost:8080/swagger-ui.html
```

### API Docs (OpenAPI JSON)
```
http://localhost:8080/v3/api-docs
```

### 주요 엔드포인트
- **Church**: `/api/church/*`
- **Member**: `/api/member/*`
- **Department**: `/api/department/*`
- **Lifecycle**: `/api/lifecycle/*`
- 기타: 자세한 내용은 [API_ENDPOINTS.md](API_ENDPOINTS.md) 참조

## 🧪 테스트

### 전체 테스트 실행
```bash
./gradlew test
```

### 특정 테스트 실행
```bash
./gradlew test --tests "ChurchServiceTest"
./gradlew test --tests "*ControllerTest"
```

### 테스트 결과
- **총 테스트**: 66개
- **성공**: 66개
- **실패**: 0개

## 🔧 기술 스택

### Backend
- Spring Boot 3.2.0
- Spring Data JPA
- Spring Validation
- PostgreSQL Driver

### Documentation
- SpringDoc OpenAPI (Swagger) 2.2.0

### Test
- JUnit 5
- Mockito
- Spring Test
- AssertJ

### Build & Deploy
- Gradle 8.14
- Docker & Docker Compose

## 📦 배포

자세한 배포 가이드는 [DEPLOYMENT.md](DEPLOYMENT.md)를 참조하세요.

### 간단 배포
```bash
# 1. 빌드 및 배포
./deploy.sh

# 2. 상태 확인
curl http://localhost:8080/v3/api-docs

# 3. 중지
./stop.sh
```

### Docker 배포
```bash
# 이미지 빌드
docker build -t life-cycle-backend .

# 컨테이너 실행
docker-compose up -d

# 로그 확인
docker-compose logs -f
```

## 🔐 환경 설정

### 개발 환경 (기본)
```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:postgresql://ec2-54-180-152-24.ap-northeast-2.compute.amazonaws.com:5432/life-cycle
```

### 운영 환경 (-Dspring.profiles.active=prod)
```yaml
server:
  port: 8080
  address: 0.0.0.0

logging:
  level:
    root: INFO
```

## 📊 통계

- **총 API 엔드포인트**: 61개
- **총 Java 클래스**: 111개
- **코드 라인 수**: ~10,000 라인
- **테스트 커버리지**: 핵심 비즈니스 로직

## 🤝 기여

버그 리포트나 기능 제안은 이슈로 등록해주세요.

## 📄 라이선스

Apache License 2.0

## 📞 문의

- **Team**: TwoThree Team
- **Email**: support@twothree.com

---

## 🔗 관련 문서

- [API 엔드포인트 가이드](API_ENDPOINTS.md)
- [배포 가이드](DEPLOYMENT.md)
- [Swagger 가이드](SWAGGER.md)

