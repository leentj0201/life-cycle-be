# 환경별 서버 구성 가이드

## 📋 개요

생애주기 관리 시스템은 **3단계 환경**으로 구성됩니다:

1. **LOCAL**: 로컬 개발 환경 + INT-DB
2. **INT**: EC2 통합 테스트 환경 + INT-DB  
3. **PROD**: 운영 환경 + PROD-DB (미래 구성)

---

## 🏗 환경별 구성

### 1. LOCAL 환경 (개발)

**목적**: 로컬 개발 및 단위 테스트  
**서버**: `http://localhost:8080`  
**데이터베이스**: INT-DB (공유)  
**설정 파일**: `application.yml` (기본)

#### 특징:
- ✅ 로컬에서 개발 중인 코드 실행
- ✅ INT-DB에 직접 연결하여 실제 데이터로 테스트
- ✅ Swagger UI 활성화
- ✅ 상세한 로깅
- ✅ 개발자 편의 기능 모두 활성화

#### 실행 방법:
```bash
# 기본 프로필로 실행 (local 환경)
./gradlew bootRun

# 또는 명시적으로 local 프로필 지정
./gradlew bootRun --args='--spring.profiles.active=local'
```

#### 접속 정보:
- **API Base**: `http://localhost:8080/api`
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **API Docs**: `http://localhost:8080/v3/api-docs`

---

### 2. INT 환경 (통합 테스트)

**목적**: 통합 테스트 및 QA 검증  
**서버**: `http://ec2-54-180-152-24.ap-northeast-2.compute.amazonaws.com:8080`  
**데이터베이스**: INT-DB (LOCAL과 동일)  
**설정 파일**: `application-int.yml`

#### 특징:
- ✅ 실제 운영과 유사한 EC2 환경에서 실행
- ✅ LOCAL과 동일한 INT-DB 사용 (데이터 일관성)
- ✅ Swagger UI 활성화 (테스트 편의성)
- ✅ 적절한 로깅 레벨
- ✅ QA팀의 통합 테스트 환경

#### 배포 방법:
```bash
# INT 서버로 배포
./deploy-to-int.sh

# SSH 키 경로 지정 (필요한 경우)
export SSH_KEY=~/your-key.pem
./deploy-to-int.sh
```

#### 접속 정보:
- **API Base**: `http://ec2-54-180-152-24.ap-northeast-2.compute.amazonaws.com:8080/api`
- **Swagger UI**: `http://ec2-54-180-152-24.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui.html`
- **API Docs**: `http://ec2-54-180-152-24.ap-northeast-2.compute.amazonaws.com:8080/v3/api-docs`

#### 서버 관리:
```bash
# 로그 확인
ssh -i ~/.ssh/your-key.pem ec2-user@ec2-54-180-152-24.ap-northeast-2.compute.amazonaws.com 'tail -f ~/life-cycle-int/logs/int-application.log'

# 프로세스 상태 확인
ssh -i ~/.ssh/your-key.pem ec2-user@ec2-54-180-152-24.ap-northeast-2.compute.amazonaws.com 'ps -p $(cat ~/life-cycle-int/int-app.pid)'

# 서버 중지
ssh -i ~/.ssh/your-key.pem ec2-user@ec2-54-180-152-24.ap-northeast-2.compute.amazonaws.com 'kill $(cat ~/life-cycle-int/int-app.pid)'
```

---

### 3. PROD 환경 (운영) - 미래 구성

**목적**: 실제 서비스 운영  
**서버**: `https://api.your-domain.com` (미래 도메인)  
**데이터베이스**: PROD-DB (별도 구성 예정)  
**설정 파일**: `application-prod.yml`

#### 특징:
- ⚠️ **아직 구성되지 않음** (미래 계획)
- 🔒 보안 강화 (Swagger UI 비활성화)
- 🔒 최소한의 로깅
- 🔒 에러 정보 제한적 노출
- 🔒 성능 최적화
- 🔒 별도 PROD-DB 사용

#### 향후 계획:
1. 별도 운영 서버 구축
2. PROD-DB 구성
3. 도메인 및 SSL 인증서 적용
4. 모니터링 시스템 구축
5. 백업 및 복구 시스템 구축

---

## 🌐 데이터베이스 구성

### INT-DB (현재 사용 중)
- **호스트**: `ec2-54-180-152-24.ap-northeast-2.compute.amazonaws.com`
- **포트**: `5432`
- **데이터베이스**: `life-cycle`
- **사용자**: `twothree_user`
- **용도**: LOCAL + INT 환경에서 공유

### PROD-DB (미래 구성)
- **호스트**: `PROD-DB-HOST` (미정)
- **포트**: `5432`
- **데이터베이스**: `life-cycle-prod`
- **사용자**: `prod_user`
- **용도**: PROD 환경 전용

---

## 🚀 배포 플로우

### 개발 플로우
```
1. LOCAL에서 개발 및 테스트
   ↓
2. INT 서버로 배포 (./deploy-to-int.sh)
   ↓ 
3. QA팀의 통합 테스트
   ↓
4. 승인 후 PROD 배포 (미래)
```

### 현재 지원되는 배포
- ✅ **LOCAL → INT**: `./deploy-to-int.sh`
- ❌ **INT → PROD**: 아직 미구성

---

## 📁 파일 구조

```
src/main/resources/
├── application.yml           # LOCAL 환경 (기본)
├── application-int.yml       # INT 환경
├── application-prod.yml      # PROD 환경 (미래용)
└── application-test.yml      # 테스트 환경

scripts/
├── deploy-to-int.sh         # INT 서버 배포
├── deploy-to-production.sh  # 기존 (현재는 사실상 INT용)
├── deploy.sh                # 로컬 서버 실행
└── stop.sh                  # 로컬 서버 중지
```

---

## ⚙️ 환경별 설정 비교

| 항목 | LOCAL | INT | PROD |
|------|-------|-----|------|
| **서버 주소** | localhost:8080 | EC2:8080 | 미래 도메인 |
| **데이터베이스** | INT-DB | INT-DB | PROD-DB |
| **Swagger UI** | ✅ 활성화 | ✅ 활성화 | ❌ 비활성화 |
| **로깅 레벨** | DEBUG/INFO | INFO | WARN |
| **SQL 로그** | ✅ 표시 | ❌ 비표시 | ❌ 비표시 |
| **에러 스택트레이스** | ✅ 표시 | ✅ 표시 | ❌ 숨김 |
| **JVM 메모리** | 기본값 | 512M-1G | 1G-2G |
| **커넥션 풀** | 10개 | 15개 | 20개 |

---

## 🔧 환경 전환 방법

### 로컬에서 다른 프로필 실행
```bash
# INT 프로필로 로컬 실행 (INT-DB 연결)
./gradlew bootRun --args='--spring.profiles.active=int'

# PROD 프로필로 로컬 실행 (미래에 PROD-DB 연결)
./gradlew bootRun --args='--spring.profiles.active=prod'
```

### 환경변수로 프로필 지정
```bash
export SPRING_PROFILES_ACTIVE=int
./gradlew bootRun
```

### JAR 실행 시 프로필 지정
```bash
java -jar -Dspring.profiles.active=int build/libs/life-cycle-0.0.1-SNAPSHOT.jar
```

---

## 🧪 테스트 방법

### 1. LOCAL 환경 테스트
```bash
# 서버 실행
./gradlew bootRun

# API 테스트
curl -X POST http://localhost:8080/api/church/list \
  -H "Content-Type: application/json" -d '{}'

# Swagger UI
open http://localhost:8080/swagger-ui.html
```

### 2. INT 환경 테스트
```bash
# INT 서버 배포
./deploy-to-int.sh

# API 테스트
curl -X POST http://ec2-54-180-152-24.ap-northeast-2.compute.amazonaws.com:8080/api/church/list \
  -H "Content-Type: application/json" -d '{}'

# Swagger UI
open http://ec2-54-180-152-24.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui.html
```

---

## 🔍 문제 해결

### 1. LOCAL 환경 문제
```bash
# 포트 충돌 확인
lsof -i:8080

# 로그 확인
tail -f logs/application.log

# 데이터베이스 연결 확인
psql -h ec2-54-180-152-24.ap-northeast-2.compute.amazonaws.com \
     -U twothree_user -d life-cycle
```

### 2. INT 환경 문제
```bash
# 서버 접속
ssh -i ~/.ssh/your-key.pem ec2-user@ec2-54-180-152-24.ap-northeast-2.compute.amazonaws.com

# 로그 확인
tail -f ~/life-cycle-int/logs/int-application.log

# 프로세스 확인
ps aux | grep java

# 포트 확인
sudo lsof -i:8080
```

### 3. 네트워크 문제
```bash
# 서버 응답 확인
curl -I http://ec2-54-180-152-24.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui.html

# DNS 확인
nslookup ec2-54-180-152-24.ap-northeast-2.compute.amazonaws.com

# 보안 그룹 확인 (AWS Console)
# - 포트 8080 인바운드 규칙 확인
# - 소스 IP 범위 확인
```

---

## 📊 모니터링

### INT 환경 모니터링
```bash
# 시스템 리소스
ssh -i ~/.ssh/your-key.pem ec2-user@ec2-54-180-152-24.ap-northeast-2.compute.amazonaws.com << 'EOF'
echo "=== CPU 및 메모리 사용량 ==="
top -bn1 | head -10

echo "=== 디스크 사용량 ==="
df -h

echo "=== 프로세스 상태 ==="
ps aux | grep java

echo "=== 로그 마지막 10줄 ==="
tail -10 ~/life-cycle-int/logs/int-application.log
EOF
```

### 자동 헬스 체크
```bash
# 간단한 헬스 체크 스크립트
#!/bin/bash
for env in localhost:8080 ec2-54-180-152-24.ap-northeast-2.compute.amazonaws.com:8080; do
    echo "=== $env 헬스 체크 ==="
    if curl -s "$env/api/church/list" -H "Content-Type: application/json" -d '{}' > /dev/null; then
        echo "✅ $env: 정상"
    else
        echo "❌ $env: 비정상"
    fi
done
```

---

## 📅 마이그레이션 계획

### 현재 (Phase 1)
- ✅ LOCAL (개발) + INT-DB
- ✅ INT (테스트) + INT-DB

### 향후 (Phase 2)
- 🔄 PROD-DB 구축
- 🔄 PROD 서버 구축
- 🔄 도메인 및 SSL 적용
- 🔄 CI/CD 파이프라인 구축

### 장기 (Phase 3)
- 🔄 컨테이너 환경 (Docker/Kubernetes)
- 🔄 마이크로서비스 분리
- 🔄 로드 밸런싱
- 🔄 자동 스케일링

---

## 📞 문의 및 지원

- **개발팀**: TwoThree Team
- **이메일**: support@twothree.com
- **환경 관련 문의**: 환경별 설정이나 배포 문제 시 연락

---

> 📝 **주의사항**  
> - INT-DB는 LOCAL과 INT 환경에서 공유되므로 데이터 변경 시 주의하세요.
> - PROD 환경은 아직 구성되지 않았으니 실제 운영 데이터와 혼동하지 마세요.
> - 배포 전에는 반드시 LOCAL 환경에서 충분한 테스트를 진행하세요.

**마지막 업데이트**: 2024-01-15
