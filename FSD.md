## 기능 명세서 (일반 사용자 관점) - 생애주기 서비스

### 1. 개요
- **목적**: 일반 사용자가 사용하는 화면을 기준으로 기능을 정의하고, 각 기능에 연결되는 API를 명시
- **대상 사용자**: 일반 회원(부모 포함)
- **전제**: 로그인/인증은 현재 미도입(향후 JWT 계획). 사용자 식별은 `memberId` 전달로 가정

### 2. 화면 구조
- 하단 탭: 홈, 실천, 부서, 생애주기

---

## A. 홈

### A-1. 목적
- 생애주기 여정을 시각화

### A-2. 주요 요소
- 생애주기 여정 다이어그램(카드): 신혼/임신/출산·육아/.../청년기/피니시웰/순금기/자녀독립
- 내 정보 아이콘: 내 정보 화면 팝업

### A-3. 상호작용
- 각 생애주기의 아이콘 클릭 → 해당 생애주기 상세 내용이 모달 팝업으로 보여진다.

### A-4. API 연결
- 생애주기 목록(교회별)
  - POST `/api/lifecycle/list-by-church`
  - Request: `{ "churchId": "UUID" }`
  - Response: 생애주기 배열
---

## B. 실천 탭

### B-1. 목적
- 사용자와 자녀의 부서/생애주기 소식을 기간 중심으로 모아서 확인

### B-2. 주요 요소
- 섹션 1: 나의 실천 과제(부서 소식, 생애주기 소식 혼합 리스트)
- 섹션 2: 자녀의 실천 과제(부모-자녀 연결이 있을 때 표시)
- 필터: 기간(이번 주/이번 달)

### B-3. 상호작용
- 카드 클릭 → 소식 상세(부서/생애주기 컨텐츠 상세 화면으로 이동 공용)
- 필터(나, 자녀1, 자녀2) 변경 → 리스트 재요청 또는 클라이언트 필터링

### B-4. API 연결
- 내 부서/생애주기
  - POST `/api/practice-department-content/list-by-member` → 내 부서별 practice 조회
  - POST `/api/practice-lifecycle-content/list-by-member` → 내 생애주기 practice 조회
  - 자녀 있는 갯수 만큼 위 api 호출

---

## C. 부서 탭

### C-1. 목적
- 교회 내 부서별 소식을 탐색하고 상세를 확인

### C-2. 주요 요소
- 부서 선택 필터(나의 교회 기준 리스트)
- 소식 카드 리스트(제목/부서/작성자/타입 뱃지)
- 정렬: 최신/마감 임박

### C-3. 상호작용
- 부서 선택 → 해당 부서 소식 리스트 로드
- 카드 클릭 → 소식 상세

### C-4. API 연결
- 부서 목록
  - POST `/api/department/list-by-church` with `{ "churchId": "UUID" }`
- 부서 소식 리스트
  - POST `/api/department-content/list-by-department` with `{ "departmentId": "UUID" }`
- 부서 소식 상세
  - POST `/api/department-content/get` with `{ "departmentContentId": "UUID" }`

---

## D. 생애주기 탭

### D-1. 목적
- 생애주기 단계별 소식을 탐색하고 상세를 확인

### D-2. 주요 요소
- 생애주기 선택 필터(나의 교회 기준 리스트)
- 소식 카드 리스트(썸네일/제목/기간/장소/타입 뱃지)

### D-3. 상호작용
- 생애주기 선택 → 해당 생애주기 소식 리스트 로드
- 카드 클릭 → 소식 상세

### D-4. API 연결
- 생애주기 목록
  - POST `/api/lifecycle/list-by-church` with `{ "churchId": "UUID" }`
- 생애주기 소식 리스트
  - POST `/api/lifecycle-content/list-by-lifecycle` with `{ "lifecycleId": "UUID" }`
- 생애주기 소식 상세
  - POST `/api/lifecycle-content/get` with `{ "lifecycleContentId": "UUID" }`

---

## E. 소식 상세(공통 상세 화면)

### E-1. 목적
- 부서/생애주기 소식의 상세 내용을 확인(제목, 본문, 링크, 임베드 등)

### E-2. 주요 요소
- 제목, 본문(리치 텍스트), 장소, 담당자(옵션), 작성자
- 유튜브 링크 임베드(있을 경우), 썸네일/이미지(있을 경우)

### E-3. API 연결
- 부서 소식 단건
  - POST `/api/department-content/get` with `{ "id": "UUID" }`
- 생애주기 소식 단건
  - POST `/api/lifecycle-content/get` with `{ "id": "UUID" }`

---

## F. 내 정보

### F-1. 목적
- 기본 프로필/연락처와 나의 소속(부서 1개, 생애주기 1개), 알림 설정을 관리

### F-2. 주요 요소
- 프로필: 이름, 연락처(전화/이메일)
- 소속 설정: 내 부서 1개 선택, 내 생애주기 1개 선택
- 알림 설정: isNotification

### F-3. 상호작용
- 편집 → 저장 시 서버 반영

### F-4. API 연결
- 회원 조회/목록(필요 시)
  - POST `/api/member/get` with `{ "id": "memberId" }`
- 내 설정 조회/수정
  - POST `/api/member-setting/get-by-member` with `{ "memberId": "UUID" }`
  - POST `/api/member-setting/update` with 설정 필드
- 내 소속(부서/생애주기)
  - 조회: POST `/api/member-department/list-by-member`, `/api/member-lifecycle/list-by-member`
  - 변경: POST `/api/member-department/update`, `/api/member-lifecycle/update` (현재 스펙에 맞춰 사용, 필요 시 create/delete로 정합성 유지)

---

## G. 자녀 관리(부모 사용자)

### G-1. 목적
- 자녀 연결 후, 자녀의 실천 소식을 실천 탭에서 함께 확인

### G-2. 주요 요소
- 자녀 목록, 자녀 추가/해제

### G-3. API 연결
- 자녀 목록
  - POST `/api/member-family/list-by-member` with `{ "memberId": "부모ID" }`
- 자녀 연결/해제
  - POST `/api/member-family/create`, `/api/member-family/delete`

---

## H. 공통 규칙
- 기간 내(`startDate` ≤ 오늘 ≤ `endDate`)이면서 게시 상태인 소식만 리스트/실천 탭에 노출(상세 조회는 예외 허용 가능)
- 정렬 기본값: 최신 등록/수정 순, 보조 정렬: 마감 임박(옵션)
- 유튜브 링크가 있으면 상세에서 임베드 표시

---

## I. 요청/응답 예시(발췌)
- 내 설정 조회
  - Request: `{ "memberId": "550e8400-e29b-41d4-a716-446655440000" }`
  - Response: `{ "memberId": "...", "notificationEnabled": true, "language": "ko", ... }`
- 부서 소식 리스트(청년부 예시)
  - Request: `{ "departmentId": "770e8400-e29b-41d4-a716-446655440002" }`
  - Response: `[ { "id": "...", "title": "여름수련회", "startDate": "2024-07-15", ... } ]`

---

## J. 에러/유효성(요약)
- 필수 파라미터 누락 시 400
- 존재하지 않는 ID는 404(또는 빈 리스트)
- 공통 에러 형식: `API_SPECIFICATION.md` 참고

---

## K. 추후 확장
- 통합 피드 API(`my-practices`, `my-children-practices`) 도입으로 클라이언트 합성 비용 절감
- 알림(JWT 도입 후 사용자별 푸시), 파일 업로드(썸네일/첨부), 포인트/인증

### 17. 일반 사용자 기능 명세
- 홈
  - 생애주기 여정 다이어그램 표시(클릭 시 단계 설명 이동)
  - 우상단 아이콘으로 내 정보 진입
- 실천 탭
  - 나의 실천과제: 내가 속한 부서/생애주기 소식 카드 리스트
  - 자녀 실천과제: 자녀의 부서/생애주기 소식 카드 리스트(부모-자녀 연결 시)
  - 필터/정렬: 기간(이번 주/이번 달), 타입(EVENT/EDUCATION/NOTICE), 최신/마감임박
  - 상세 보기: 리치 텍스트/유튜브 임베드/기간·장소/담당자 정보 확인
- 부서 탭
  - 부서별 소식 카드 리스트 및 상세 보기
  - 관심 부서 설정 토글
- 생애주기 탭
  - 생애주기별 소식 카드 리스트 및 상세 보기
  - 관심 생애주기 설정 토글
- 내 정보
  - 이름, 연락처 수정
  - 내 부서 1개 선택, 내 생애주기 1개 선택(선택 해제 가능 정책은 추후 확정)
  - 푸시 알림 수신 여부, 이메일/SMS 알림 옵션(설정 저장)
- 자녀 관리
  - 부모-자녀 연결/해제(본인 회원 기준 권한 검증)
  - 자녀 목록에서 실천 탭 연동 노출
- 알림(수신)
  - 실천 탭 노출 시작/마감 임박 등 알림 수신(설정에 따름)

### 18. 관리자 기능 명세 (역할별)
- 공통(관리 콘솔)
  - 검색/정렬/페이지네이션, 초안-게시-비공개 상태 전환
  - 노출 기간 수동/자동 설정, 미리보기, 접근 권한 검증
- 슈퍼 관리자 (SUPER_ADMIN)
  - 교회 등록/비활성화, 교회 관리자 지정/해제
  - 전역 설정 관리(인증/보안 정책, 기능 플래그)
- 교회 관리자 (CHURCH_ADMIN)
  - 부서 카테고리 생성/수정/삭제
  - 생애주기 카테고리 생성/수정/삭제
  - 회원 등록/수정/삭제, 부모-자녀 연결/해제
  - 관리자 권한 부여/회수(부서/생애주기 관리자)
  - 콘텐츠 승인/회수(옵션)
- 부서 관리자 (DEPARTMENT_LEADER)
  - 부서 소식 CRUD(초안 작성→검수/승인 플로우는 옵션)
  - 노출 정책 설정(수동 기간/자동: 이벤트 기간 기반)
  - 첨부/썸네일/유튜브 링크 설정, 게시/비공개 전환
- 생애주기 관리자 (LIFECYCLE_MANAGER)
  - 생애주기 소식 CRUD 및 노출 정책 설정(동일)
- 운영/감사
  - 변경 이력(작성/수정/게시/회수) 타임라인 확인
  - 활성/비활성 콘텐츠 현황, 노출 기간 만료 예정 리포트