# API 엔드포인트

> **참고**: 모든 API 엔드포인트는 `ApiEndpoints.java` 상수 클래스에서 중앙 관리됩니다.

## 서버 정보

### 로컬 개발 서버
- **Base URL**: `http://localhost:8080`
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **API Docs**: `http://localhost:8080/v3/api-docs`

### 운영 서버
- **Base URL**: `http://3.34.74.224:8080`
- **Swagger UI**: `http://3.34.74.224:8080/swagger-ui.html`
- **API Docs**: `http://3.34.74.224:8080/v3/api-docs`

---

## Church API (교회 관리)

### Base Path
```java
ApiEndpoints.Church.BASE = "/api/church"
```

### 1. 교회 생성
- **URL**: `POST /api/church/create`
- **상수**: `ApiEndpoints.Church.FULL_CREATE`
- **Request Body**:
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
- **Response**: `201 Created` + ChurchResponseDto

### 2. 교회 조회
- **URL**: `POST /api/church/get`
- **상수**: `ApiEndpoints.Church.FULL_GET`
- **Request Body**:
```json
{
  "churchId": "123e4567-e89b-12d3-a456-426614174000"
}
```
- **Response**: `200 OK` + ChurchResponseDto

### 3. 교회 목록 조회
- **URL**: `POST /api/church/list`
- **상수**: `ApiEndpoints.Church.FULL_LIST`
- **Request Body**: 없음 (빈 JSON `{}` 전송)
- **Response**: `200 OK` + ChurchResponseDto[]

### 4. 교회 정보 수정
- **URL**: `POST /api/church/update`
- **상수**: `ApiEndpoints.Church.FULL_UPDATE`
- **Request Body** (null 값은 기존 값 유지):
```json
{
  "churchId": "123e4567-e89b-12d3-a456-426614174000",
  "name": "사랑의교회",
  "address": "서울시 강남구 테헤란로 456",
  "phone": "02-9876-5432",
  "isActive": true
}
```
- **Response**: `200 OK` + ChurchResponseDto

### 5. 교회 삭제 (논리 삭제)
- **URL**: `POST /api/church/delete`
- **상수**: `ApiEndpoints.Church.FULL_DELETE`
- **Request Body**:
```json
{
  "churchId": "123e4567-e89b-12d3-a456-426614174000"
}
```
- **Response**: `204 No Content`

---

## cURL 예제

### 교회 생성
```bash
curl -X POST http://localhost:8080/api/church/create \
  -H "Content-Type: application/json" \
  -d '{
    "name": "사랑의교회",
    "address": "서울시 강남구 테헤란로 123",
    "phone": "02-1234-5678",
    "email": "contact@church.com",
    "pastorName": "김목사"
  }'
```

### 교회 목록 조회
```bash
curl -X POST http://localhost:8080/api/church/list \
  -H "Content-Type: application/json"
```

### 교회 조회
```bash
curl -X POST http://localhost:8080/api/church/get \
  -H "Content-Type: application/json" \
  -d '{
    "churchId": "your-church-id-here"
  }'
```

---

## 응답 예시

### ChurchResponseDto
```json
{
  "churchId": "123e4567-e89b-12d3-a456-426614174000",
  "name": "사랑의교회",
  "address": "서울시 강남구 테헤란로 123",
  "phone": "02-1234-5678",
  "email": "contact@church.com",
  "website": "https://www.church.com",
  "pastorName": "김목사",
  "description": "주님의 사랑을 전하는 교회",
  "isActive": true,
  "createdAt": "2025-10-11T14:30:00",
  "createdBy": null,
  "updatedAt": "2025-10-11T14:30:00",
  "updatedBy": null
}
```

---

## 에러 응답

### 400 Bad Request
```json
{
  "error": "해당 교회를 찾을 수 없습니다. ID: xxx"
}
```

### Validation Error
```json
{
  "name": "교회 이름은 필수입니다",
  "email": "올바른 이메일 형식이 아닙니다"
}
```

---

## 엔드포인트 상수 관리

### ApiEndpoints.java
모든 API 엔드포인트는 `com.twothree.backend.constant.ApiEndpoints` 클래스에서 관리됩니다.

```java
// Church API
ApiEndpoints.Church.BASE = "/api/church"
ApiEndpoints.Church.CREATE = "/create"
ApiEndpoints.Church.GET = "/get"
ApiEndpoints.Church.LIST = "/list"
ApiEndpoints.Church.UPDATE = "/update"
ApiEndpoints.Church.DELETE = "/delete"

// Full paths
ApiEndpoints.Church.FULL_CREATE = "/api/church/create"
ApiEndpoints.Church.FULL_GET = "/api/church/get"
ApiEndpoints.Church.FULL_LIST = "/api/church/list"
ApiEndpoints.Church.FULL_UPDATE = "/api/church/update"
ApiEndpoints.Church.FULL_DELETE = "/api/church/delete"

// Server URLs
ApiEndpoints.Server.LOCAL = "http://localhost:8080"
ApiEndpoints.Server.PRODUCTION = "http://3.34.74.224:8080"

// Swagger
ApiEndpoints.Swagger.UI = "/swagger-ui.html"
ApiEndpoints.Swagger.UI_INDEX = "/swagger-ui/index.html"
ApiEndpoints.Swagger.API_DOCS = "/v3/api-docs"
```

### application.yml
```yaml
server:
  port: 8080
  address: localhost

api:
  base-url: http://localhost:8080
  version: v1

springdoc:
  api-docs:
    path: /v3/api-docs
  swagger-ui:
    path: /swagger-ui.html
```

### 환경 변수로 설정 변경
```bash
# 포트 변경
export SERVER_PORT=9090

# API Base URL 변경
export API_BASE_URL=http://your-domain.com

# API 버전 변경
export API_VERSION=v2
```

