# 생애주기 관리 시스템 API 명세서

## 📋 개요

**시스템명**: 생애주기 관리 시스템 (Life Cycle Management System)  
**버전**: v1.0  
**Base URL**: `http://localhost:8080` (개발) / `http://ec2-54-180-152-24.ap-northeast-2.compute.amazonaws.com:8080` (운영)  
**API 문서**: `/swagger-ui.html`  
**OpenAPI 스펙**: `/v3/api-docs`  

### 시스템 목적
교회의 생애주기별 교육 과정과 부서 관리를 위한 REST API 시스템

### 인증 방식
- 현재: 인증 없음 (개발 단계)
- 향후: JWT 토큰 기반 인증 예정

---

## 🏗 시스템 아키텍처

### 도메인 구조
```
교회(Church) 1:N 부서(Department) 1:N 부서컨텐츠(DepartmentContent)
     │             │
     │             └─ 1:N 회원부서(MemberDepartment) N:1 회원(Member)
     │                                                    │
     └─ 1:N 생애주기(Lifecycle) 1:N 생애주기컨텐츠(LifecycleContent)   │
              │                                              │
              └─ 1:N 회원생애주기(MemberLifecycle) ────────────┘
                                                         │
                                              1:N 회원가족(MemberFamily)
                                                         │
                                               1:1 회원설정(MemberSetting)
```

### 기술 스택
- **Framework**: Spring Boot 3.2
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA
- **Documentation**: Swagger/OpenAPI 3.0
- **Build Tool**: Gradle

---

## 📡 API 엔드포인트 목록

### 1. 교회 관리 (Church)

| Method | Endpoint | 설명 |
|--------|----------|------|
| POST | `/api/church/create` | 교회 생성 |
| POST | `/api/church/get` | 교회 조회 |
| POST | `/api/church/list` | 교회 목록 조회 |
| POST | `/api/church/update` | 교회 수정 |
| POST | `/api/church/delete` | 교회 삭제 (논리삭제) |

### 2. 회원 관리 (Member)

| Method | Endpoint | 설명 |
|--------|----------|------|
| POST | `/api/member/create` | 회원 생성 |
| POST | `/api/member/get` | 회원 조회 |
| POST | `/api/member/list` | 회원 목록 조회 |
| POST | `/api/member/list-by-church` | 특정 교회 회원 목록 조회 |
| POST | `/api/member/update` | 회원 수정 |
| POST | `/api/member/delete` | 회원 삭제 (논리삭제) |

### 3. 부서 관리 (Department)

| Method | Endpoint | 설명 |
|--------|----------|------|
| POST | `/api/department/create` | 부서 생성 |
| POST | `/api/department/get` | 부서 조회 |
| POST | `/api/department/list` | 부서 목록 조회 |
| POST | `/api/department/list-by-church` | 특정 교회 부서 목록 조회 |
| POST | `/api/department/update` | 부서 수정 |
| POST | `/api/department/delete` | 부서 삭제 (논리삭제) |

### 4. 생애주기 관리 (Lifecycle)

| Method | Endpoint | 설명 |
|--------|----------|------|
| POST | `/api/lifecycle/create` | 생애주기 생성 |
| POST | `/api/lifecycle/get` | 생애주기 조회 |
| POST | `/api/lifecycle/list` | 생애주기 목록 조회 |
| POST | `/api/lifecycle/list-by-church` | 특정 교회 생애주기 목록 조회 |
| POST | `/api/lifecycle/update` | 생애주기 수정 |
| POST | `/api/lifecycle/delete` | 생애주기 삭제 (논리삭제) |

### 5. 부서 컨텐츠 관리 (DepartmentContent)

| Method | Endpoint | 설명 |
|--------|----------|------|
| POST | `/api/department-content/create` | 부서 컨텐츠 생성 |
| POST | `/api/department-content/get` | 부서 컨텐츠 조회 |
| POST | `/api/department-content/list` | 부서 컨텐츠 목록 조회 |
| POST | `/api/department-content/list-by-department` | 특정 부서 컨텐츠 목록 조회 |
| POST | `/api/department-content/update` | 부서 컨텐츠 수정 |
| POST | `/api/department-content/delete` | 부서 컨텐츠 삭제 (논리삭제) |

### 6. 생애주기 컨텐츠 관리 (LifecycleContent)

| Method | Endpoint | 설명 |
|--------|----------|------|
| POST | `/api/lifecycle-content/create` | 생애주기 컨텐츠 생성 |
| POST | `/api/lifecycle-content/get` | 생애주기 컨텐츠 조회 |
| POST | `/api/lifecycle-content/list` | 생애주기 컨텐츠 목록 조회 |
| POST | `/api/lifecycle-content/list-by-lifecycle` | 특정 생애주기 컨텐츠 목록 조회 |
| POST | `/api/lifecycle-content/update` | 생애주기 컨텐츠 수정 |
| POST | `/api/lifecycle-content/delete` | 생애주기 컨텐츠 삭제 (논리삭제) |

### 7. 회원-부서 관계 관리 (MemberDepartment)

| Method | Endpoint | 설명 |
|--------|----------|------|
| POST | `/api/member-department/create` | 회원-부서 관계 생성 |
| POST | `/api/member-department/get` | 회원-부서 관계 조회 |
| POST | `/api/member-department/list` | 회원-부서 관계 목록 조회 |
| POST | `/api/member-department/list-by-member` | 특정 회원의 부서 목록 조회 |
| POST | `/api/member-department/list-by-department` | 특정 부서의 회원 목록 조회 |
| POST | `/api/member-department/update` | 회원-부서 관계 수정 |
| POST | `/api/member-department/delete` | 회원-부서 관계 삭제 (논리삭제) |

### 8. 회원-생애주기 관계 관리 (MemberLifecycle)

| Method | Endpoint | 설명 |
|--------|----------|------|
| POST | `/api/member-lifecycle/create` | 회원-생애주기 관계 생성 |
| POST | `/api/member-lifecycle/get` | 회원-생애주기 관계 조회 |
| POST | `/api/member-lifecycle/list` | 회원-생애주기 관계 목록 조회 |
| POST | `/api/member-lifecycle/list-by-member` | 특정 회원의 생애주기 목록 조회 |
| POST | `/api/member-lifecycle/list-by-lifecycle` | 특정 생애주기의 회원 목록 조회 |
| POST | `/api/member-lifecycle/update` | 회원-생애주기 관계 수정 |
| POST | `/api/member-lifecycle/delete` | 회원-생애주기 관계 삭제 (논리삭제) |

### 9. 회원 가족 관리 (MemberFamily)

| Method | Endpoint | 설명 |
|--------|----------|------|
| POST | `/api/member-family/create` | 가족 관계 생성 |
| POST | `/api/member-family/get` | 가족 관계 조회 |
| POST | `/api/member-family/list` | 가족 관계 목록 조회 |
| POST | `/api/member-family/list-by-member` | 특정 회원의 가족 목록 조회 |
| POST | `/api/member-family/update` | 가족 관계 수정 |
| POST | `/api/member-family/delete` | 가족 관계 삭제 (논리삭제) |

### 10. 회원 설정 관리 (MemberSetting)

| Method | Endpoint | 설명 |
|--------|----------|------|
| POST | `/api/member-setting/create` | 회원 설정 생성 |
| POST | `/api/member-setting/get` | 회원 설정 조회 |
| POST | `/api/member-setting/get-by-member` | 특정 회원의 설정 조회 |
| POST | `/api/member-setting/list` | 회원 설정 목록 조회 |
| POST | `/api/member-setting/update` | 회원 설정 수정 |
| POST | `/api/member-setting/delete` | 회원 설정 삭제 (논리삭제) |

---

## 📝 API 상세 명세

### 1. 교회 관리 (Church API)

#### 1.1 교회 생성
**Endpoint**: `POST /api/church/create`

**Request Body**:
```json
{
  "name": "사랑의교회",
  "address": "서울시 강남구 테헤란로 123",
  "phone": "02-1234-5678",
  "email": "contact@sarang.church",
  "website": "https://www.sarang.church",
  "pastorName": "김목사",
  "description": "은혜가 넘치는 교회입니다."
}
```

**Response (201 Created)**:
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "사랑의교회",
  "address": "서울시 강남구 테헤란로 123",
  "phone": "02-1234-5678",
  "email": "contact@sarang.church",
  "website": "https://www.sarang.church",
  "pastorName": "김목사",
  "description": "은혜가 넘치는 교회입니다.",
  "isActive": true,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

#### 1.2 교회 조회
**Endpoint**: `POST /api/church/get`

**Request Body**:
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000"
}
```

**Response (200 OK)**:
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "사랑의교회",
  "address": "서울시 강남구 테헤란로 123",
  "phone": "02-1234-5678",
  "email": "contact@sarang.church",
  "website": "https://www.sarang.church",
  "pastorName": "김목사",
  "description": "은혜가 넘치는 교회입니다.",
  "isActive": true,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

#### 1.3 교회 목록 조회
**Endpoint**: `POST /api/church/list`

**Request Body**:
```json
{}
```

**Response (200 OK)**:
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "name": "사랑의교회",
    "address": "서울시 강남구 테헤란로 123",
    "phone": "02-1234-5678",
    "email": "contact@sarang.church",
    "website": "https://www.sarang.church",
    "pastorName": "김목사",
    "description": "은혜가 넘치는 교회입니다.",
    "isActive": true,
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-15T10:30:00"
  }
]
```

#### 1.4 교회 수정
**Endpoint**: `POST /api/church/update`

**Request Body**:
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "사랑의교회 (수정됨)",
  "address": "서울시 강남구 테헤란로 456",
  "phone": "02-9876-5432",
  "email": "info@sarang.church",
  "website": "https://www.sarang.church",
  "pastorName": "박목사",
  "description": "더욱 은혜가 넘치는 교회입니다."
}
```

**Response (200 OK)**:
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "사랑의교회 (수정됨)",
  "address": "서울시 강남구 테헤란로 456",
  "phone": "02-9876-5432",
  "email": "info@sarang.church",
  "website": "https://www.sarang.church",
  "pastorName": "박목사",
  "description": "더욱 은혜가 넘치는 교회입니다.",
  "isActive": true,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T11:45:00"
}
```

#### 1.5 교회 삭제 (논리삭제)
**Endpoint**: `POST /api/church/delete`

**Request Body**:
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000"
}
```

**Response (204 No Content)**:
```
(빈 응답)
```

---

### 2. 회원 관리 (Member API)

#### 2.1 회원 생성
**Endpoint**: `POST /api/member/create`

**Request Body**:
```json
{
  "churchId": "550e8400-e29b-41d4-a716-446655440000",
  "loginId": "hong123",
  "password": "securePassword123!",
  "name": "홍길동",
  "phone": "010-1234-5678",
  "birth": "1990-05-15",
  "memberRole": "MEMBER"
}
```

**Response (201 Created)**:
```json
{
  "id": "660e8400-e29b-41d4-a716-446655440001",
  "churchId": "550e8400-e29b-41d4-a716-446655440000",
  "loginId": "hong123",
  "name": "홍길동",
  "phone": "010-1234-5678",
  "birth": "1990-05-15",
  "memberRole": "MEMBER",
  "isActive": true,
  "lastLoginAt": null,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

#### 2.2 특정 교회 회원 목록 조회
**Endpoint**: `POST /api/member/list-by-church`

**Request Body**:
```json
{
  "churchId": "550e8400-e29b-41d4-a716-446655440000"
}
```

**Response (200 OK)**:
```json
[
  {
    "id": "660e8400-e29b-41d4-a716-446655440001",
    "churchId": "550e8400-e29b-41d4-a716-446655440000",
    "loginId": "hong123",
    "name": "홍길동",
    "phone": "010-1234-5678",
    "birth": "1990-05-15",
    "memberRole": "MEMBER",
    "isActive": true,
    "lastLoginAt": null,
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-15T10:30:00"
  }
]
```

---

### 3. 부서 관리 (Department API)

#### 3.1 부서 생성
**Endpoint**: `POST /api/department/create`

**Request Body**:
```json
{
  "churchId": "550e8400-e29b-41d4-a716-446655440000",
  "name": "청년부",
  "description": "19-29세 청년들을 위한 부서",
  "targetAgeMin": 19,
  "targetAgeMax": 29,
  "responsiblePerson": "김청년전도사"
}
```

**Response (201 Created)**:
```json
{
  "id": "770e8400-e29b-41d4-a716-446655440002",
  "churchId": "550e8400-e29b-41d4-a716-446655440000",
  "name": "청년부",
  "description": "19-29세 청년들을 위한 부서",
  "targetAgeMin": 19,
  "targetAgeMax": 29,
  "responsiblePerson": "김청년전도사",
  "isActive": true,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

---

### 4. 생애주기 관리 (Lifecycle API)

#### 4.1 생애주기 생성
**Endpoint**: `POST /api/lifecycle/create`

**Request Body**:
```json
{
  "churchId": "550e8400-e29b-41d4-a716-446655440000",
  "name": "청년기",
  "description": "19-29세 청년들을 위한 생애주기",
  "minAge": 19,
  "maxAge": 29,
  "color": "#20B2AA",
  "icon": "heart"
}
```

**Response (201 Created)**:
```json
{
  "id": "880e8400-e29b-41d4-a716-446655440003",
  "churchId": "550e8400-e29b-41d4-a716-446655440000",
  "name": "청년기",
  "description": "19-29세 청년들을 위한 생애주기",
  "minAge": 19,
  "maxAge": 29,
  "color": "#20B2AA",
  "icon": "heart",
  "isActive": true,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

---

### 5. 관계형 API

#### 5.1 회원-부서 관계 생성
**Endpoint**: `POST /api/member-department/create`

**Request Body**:
```json
{
  "memberId": "660e8400-e29b-41d4-a716-446655440001",
  "departmentId": "770e8400-e29b-41d4-a716-446655440002",
  "joinDate": "2024-01-15",
  "role": "MEMBER",
  "note": "새로 등록된 청년"
}
```

**Response (201 Created)**:
```json
{
  "id": "990e8400-e29b-41d4-a716-446655440004",
  "memberId": "660e8400-e29b-41d4-a716-446655440001",
  "departmentId": "770e8400-e29b-41d4-a716-446655440002",
  "joinDate": "2024-01-15",
  "role": "MEMBER",
  "note": "새로 등록된 청년",
  "isActive": true,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

#### 5.2 특정 회원의 부서 목록 조회
**Endpoint**: `POST /api/member-department/list-by-member`

**Request Body**:
```json
{
  "memberId": "660e8400-e29b-41d4-a716-446655440001"
}
```

**Response (200 OK)**:
```json
[
  {
    "id": "990e8400-e29b-41d4-a716-446655440004",
    "memberId": "660e8400-e29b-41d4-a716-446655440001",
    "departmentId": "770e8400-e29b-41d4-a716-446655440002",
    "joinDate": "2024-01-15",
    "role": "MEMBER",
    "note": "새로 등록된 청년",
    "isActive": true,
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-15T10:30:00"
  }
]
```

---

## 🔒 인증 및 권한

### 현재 상태
- **인증**: 없음 (개발 단계)
- **권한**: 모든 API 접근 가능

### 향후 계획
- **JWT 토큰** 기반 인증 구현
- **회원 역할별 권한 관리**:
  - `SUPER_ADMIN`: 전체 시스템 관리
  - `CHURCH_ADMIN`: 교회 관리
  - `DEPARTMENT_LEADER`: 부서 관리
  - `MEMBER`: 개인 정보 관리

---

## 📊 데이터 모델

### 공통 필드 (BaseEntity)
모든 엔티티는 다음 공통 필드를 가집니다:
- `createdAt`: 생성일시 (LocalDateTime)
- `createdBy`: 생성자 ID (UUID)
- `updatedAt`: 수정일시 (LocalDateTime)
- `updatedBy`: 수정자 ID (UUID)
- `isActive`: 활성 상태 (Boolean, 기본값: true)

### 주요 엔티티

#### Church (교회)
```json
{
  "id": "UUID",
  "name": "string (최대 100자)",
  "address": "string",
  "phone": "string (최대 20자)",
  "email": "string (최대 100자)",
  "website": "string (최대 200자)",
  "pastorName": "string (최대 50자)",
  "description": "string",
  "isActive": "boolean"
}
```

#### Member (회원)
```json
{
  "id": "UUID",
  "churchId": "UUID (FK)",
  "loginId": "string (최대 255자)",
  "password": "string (최대 255자, 암호화됨)",
  "name": "string (최대 50자)",
  "phone": "string (최대 20자)",
  "birth": "date",
  "memberRole": "enum [SUPER_ADMIN, CHURCH_ADMIN, DEPARTMENT_LEADER, MEMBER]",
  "isActive": "boolean",
  "lastLoginAt": "LocalDateTime"
}
```

#### Department (부서)
```json
{
  "id": "UUID",
  "churchId": "UUID (FK)",
  "name": "string (최대 100자)",
  "description": "string",
  "targetAgeMin": "integer",
  "targetAgeMax": "integer",
  "responsiblePerson": "string (최대 50자)",
  "isActive": "boolean"
}
```

#### Lifecycle (생애주기)
```json
{
  "id": "UUID",
  "churchId": "UUID (FK)",
  "name": "string (최대 50자)",
  "description": "string",
  "minAge": "integer",
  "maxAge": "integer",
  "color": "string (색상 코드, 최대 7자)",
  "icon": "string (아이콘명, 최대 20자)",
  "isActive": "boolean"
}
```

---

## ⚠️ 에러 응답

### 공통 에러 형식
```json
{
  "error": "에러 메시지",
  "timestamp": "2024-01-15T10:30:00",
  "path": "/api/church/create"
}
```

### 주요 HTTP 상태 코드
- `200 OK`: 성공
- `201 Created`: 생성 성공
- `204 No Content`: 삭제 성공
- `400 Bad Request`: 잘못된 요청 데이터
- `404 Not Found`: 리소스를 찾을 수 없음
- `500 Internal Server Error`: 서버 내부 오류

### 유효성 검증 오류
```json
{
  "name": "교회 이름은 필수입니다",
  "email": "올바른 이메일 형식이 아닙니다"
}
```

---

## 📚 사용 예제

### cURL 예제

#### 교회 생성
```bash
curl -X POST http://localhost:8080/api/church/create \
  -H "Content-Type: application/json" \
  -d '{
    "name": "사랑의교회",
    "address": "서울시 강남구 테헤란로 123",
    "phone": "02-1234-5678",
    "email": "contact@sarang.church",
    "pastorName": "김목사"
  }'
```

#### 교회 목록 조회
```bash
curl -X POST http://localhost:8080/api/church/list \
  -H "Content-Type: application/json" \
  -d '{}'
```

### JavaScript 예제

#### 교회 생성
```javascript
const response = await fetch('http://localhost:8080/api/church/create', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    name: '사랑의교회',
    address: '서울시 강남구 테헤란로 123',
    phone: '02-1234-5678',
    email: 'contact@sarang.church',
    pastorName: '김목사'
  })
});

const church = await response.json();
console.log(church);
```

---

## 🔧 개발 도구

### Swagger UI
- **URL**: `http://localhost:8080/swagger-ui.html`
- **기능**: 인터랙티브 API 문서 및 테스트

### OpenAPI 스펙
- **JSON**: `http://localhost:8080/v3/api-docs`
- **YAML**: `http://localhost:8080/v3/api-docs.yaml`

### Postman Collection
- OpenAPI 스펙을 Postman으로 임포트하여 사용 가능

---

## 📝 버전 히스토리

### v1.0 (2024-01-15)
- 초기 API 구현
- 9개 도메인 CRUD 완성
- Swagger 문서화 완료
- PostgreSQL 연동 완료

### 향후 계획
- JWT 인증 시스템 구현
- 페이징 및 정렬 기능 추가
- 파일 업로드 기능 추가
- 알림 시스템 구현
- 통계 및 리포트 API 추가

---

## 📞 문의 및 지원

- **개발팀**: TwoThree Team
- **이메일**: support@twothree.com
- **문서 업데이트**: 2024-01-15

---

> 이 문서는 Swagger UI와 함께 사용하시길 권장합니다.  
> 최신 정보는 `/swagger-ui.html`에서 확인하세요.
