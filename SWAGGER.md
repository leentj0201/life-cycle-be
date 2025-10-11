# Swagger (OpenAPI) 문서

## 접속 방법

애플리케이션을 실행한 후 아래 URL로 접속하세요.

### Swagger UI (웹 인터페이스)
```
http://localhost:8080/swagger-ui/index.html
```

또는

```
http://localhost:8080/swagger-ui.html
```

### OpenAPI JSON 스펙
```
http://localhost:8080/v3/api-docs
```

### OpenAPI YAML 스펙
```
http://localhost:8080/v3/api-docs.yaml
```

## 주요 기능

### 1. API 문서 자동 생성
- 모든 REST API 엔드포인트가 자동으로 문서화됩니다
- Request/Response DTO의 스키마 정보 제공
- 각 필드의 설명과 예제값 포함

### 2. API 테스트
- Swagger UI에서 직접 API를 테스트할 수 있습니다
- "Try it out" 버튼을 클릭하여 요청 파라미터 입력
- "Execute" 버튼으로 실제 API 호출

### 3. 스키마 정보
- 각 DTO의 상세 스키마 정보 확인
- 필수 필드, 데이터 타입, 제약 조건 등

## Church API 엔드포인트

### 1. POST /api/church/create
교회 생성

**Request Body:**
```json
{
  "name": "사랑의교회",
  "address": "서울시 강남구 테헤란로 123",
  "phone": "02-1234-5678",
  "email": "contact@church.com",
  "website": "https://www.church.com",
  "pastorName": "김목사",
  "description": "주님의 사랑을 전하는 교회"
}
```

### 2. POST /api/church/get
교회 단건 조회

**Request Body:**
```json
{
  "churchId": "123e4567-e89b-12d3-a456-426614174000"
}
```

### 3. POST /api/church/list
전체 교회 목록 조회 (활성화된 교회만)

**Request Body:** 없음

### 4. POST /api/church/update
교회 정보 수정 (null 값은 기존 값 유지)

**Request Body:**
```json
{
  "churchId": "123e4567-e89b-12d3-a456-426614174000",
  "name": "사랑의교회",
  "address": "서울시 강남구 테헤란로 456",
  "phone": "02-9876-5432",
  "isActive": true
}
```

### 5. POST /api/church/delete
교회 삭제 (논리 삭제 - isActive = false)

**Request Body:**
```json
{
  "churchId": "123e4567-e89b-12d3-a456-426614174000"
}
```

## 설정 정보

### SwaggerConfig.java
- API 문서 제목, 버전, 설명 등 설정
- 서버 URL 설정
- 연락처 정보, 라이센스 정보

### 의존성 (build.gradle)
```gradle
implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0'
```

## 추가 설정 (선택사항)

### application.yml에 추가 가능한 설정
```yaml
springdoc:
  api-docs:
    path: /v3/api-docs
  swagger-ui:
    path: /swagger-ui.html
    enabled: true
    operations-sorter: method
    tags-sorter: alpha
```

## 주의사항

1. **프로덕션 환경**: 보안상 프로덕션 환경에서는 Swagger UI 접근을 제한하는 것이 좋습니다.
2. **context-path**: 현재 설정에서 context-path가 `/api`로 되어 있으므로, 실제 접속 URL은 다를 수 있습니다.
   - Swagger UI: `http://localhost:8080/api/swagger-ui/index.html`
   - API Docs: `http://localhost:8080/api/v3/api-docs`

## 참고 자료

- [SpringDoc OpenAPI 공식 문서](https://springdoc.org/)
- [OpenAPI 3.0 명세](https://swagger.io/specification/)

