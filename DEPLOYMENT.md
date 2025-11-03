# 배포 가이드

## 배포 방법

### 방법 1: 일반 JAR 배포 (추천)

#### 1. 빌드 및 배포 (자동)
```bash
chmod +x deploy.sh
./deploy.sh
```

#### 2. 서버 중지
```bash
chmod +x stop.sh
./stop.sh
```

#### 3. 수동 배포
```bash
# 빌드
./gradlew clean build -x test

# 실행
java -jar -Dspring.profiles.active=prod build/libs/*.jar

# 백그라운드 실행
nohup java -jar -Dspring.profiles.active=prod build/libs/*.jar > logs/application.log 2>&1 &
```

---

### 방법 2: Docker 배포

#### 1. Docker 이미지 빌드
```bash
docker build -t life-cycle-backend:latest .
```

#### 2. Docker 컨테이너 실행
```bash
docker run -d \
  --name life-cycle-backend \
  -p 8080:8080 \
  -v $(pwd)/logs:/app/logs \
  -e SPRING_PROFILES_ACTIVE=prod \
  life-cycle-backend:latest
```

#### 3. Docker Compose 사용
```bash
docker-compose up -d
```

#### 4. 로그 확인
```bash
docker logs -f life-cycle-backend
```

#### 5. 컨테이너 중지 및 삭제
```bash
docker-compose down
# 또는
docker stop life-cycle-backend
docker rm life-cycle-backend
```

---

### 방법 3: AWS EC2 서버 직접 배포

#### 1. 서버 접속
```bash
ssh -i your-key.pem ec2-user@3.34.74.224
```

#### 2. Java 17 설치 확인
```bash
java -version
# Java 17이 없으면 설치
sudo yum install java-17-amazon-corretto-devel
```

#### 3. 빌드된 JAR 파일 업로드
```bash
# 로컬에서 실행
scp -i your-key.pem build/libs/life-cycle-0.0.1-SNAPSHOT.jar \
  ec2-user@3.34.74.224:~/
```

#### 4. 서버에서 실행
```bash
# EC2 서버에서
nohup java -jar \
  -Dspring.profiles.active=prod \
  -Xms512m \
  -Xmx1024m \
  life-cycle-0.0.1-SNAPSHOT.jar > app.log 2>&1 &

# PID 저장
echo $! > app.pid
```

#### 5. 프로세스 관리
```bash
# 프로세스 확인
ps -ef | grep java

# 로그 확인
tail -f app.log

# 서버 중지
kill $(cat app.pid)
```

---

## 환경별 설정

### 로컬 개발 환경
```bash
./gradlew bootRun
# 또는
java -jar build/libs/*.jar
```

### 운영 환경
```bash
java -jar -Dspring.profiles.active=prod build/libs/*.jar
```

### 테스트 환경
```bash
./gradlew test
```

---

## 헬스 체크

### 서버 상태 확인
```bash
# API 호출 확인
curl http://localhost:8080/api/church/list -X POST -H "Content-Type: application/json"

# Swagger UI 확인
curl -I http://localhost:8080/swagger-ui.html

# API Docs 확인
curl http://localhost:8080/v3/api-docs
```

---

## 운영 환경 설정 (application-prod.yml)

### 주요 설정:
- **포트**: 8080
- **주소**: 0.0.0.0 (모든 인터페이스에서 접근 가능)
- **데이터베이스**: AWS EC2 PostgreSQL
- **로그 레벨**: INFO (운영 환경)
- **JPA ddl-auto**: validate (스키마 검증만)
- **압축**: 활성화
- **커넥션 풀**: HikariCP 최적화

### 로그 파일:
- 위치: `logs/application.log`
- 최대 크기: 10MB
- 최대 보관 기간: 30일

---

## 배포 체크리스트

### 배포 전:
- [ ] 모든 테스트 통과 확인 (`./gradlew test`)
- [ ] application-prod.yml 설정 확인
- [ ] 데이터베이스 연결 정보 확인
- [ ] 포트 8080 사용 가능 확인

### 배포 후:
- [ ] 서버 시작 확인 (`curl http://localhost:8080/v3/api-docs`)
- [ ] Swagger UI 접속 확인
- [ ] API 엔드포인트 테스트
- [ ] 로그 파일 확인 (`tail -f logs/application.log`)

---

## 트러블슈팅

### 포트가 이미 사용 중인 경우
```bash
# 포트 사용 프로세스 확인
lsof -ti:8080

# 프로세스 종료
lsof -ti:8080 | xargs kill -9
```

### 메모리 부족
```bash
# JVM 메모리 조정
java -jar -Xms256m -Xmx512m app.jar
```

### 데이터베이스 연결 실패
- application-prod.yml의 DB 연결 정보 확인
- 방화벽/보안 그룹 설정 확인
- DB 서버 상태 확인

---

## 성능 최적화

### JVM 옵션
```bash
java -jar \
  -Xms512m \
  -Xmx1024m \
  -XX:+UseG1GC \
  -XX:MaxGCPauseMillis=200 \
  -XX:+UseStringDeduplication \
  app.jar
```

### 데이터베이스 커넥션 풀
`application-prod.yml`에서 HikariCP 설정 조정:
- maximum-pool-size: 10
- minimum-idle: 5
- connection-timeout: 30000

---

## 모니터링

### 로그 모니터링
```bash
# 실시간 로그 확인
tail -f logs/application.log

# 에러 로그 확인
grep ERROR logs/application.log

# 최근 100줄 확인
tail -100 logs/application.log
```

### 프로세스 모니터링
```bash
# CPU/메모리 사용량 확인
top -p $(cat app.pid)

# 상세 정보
ps aux | grep java
```

---

## 보안 권장사항

### 운영 환경에서:
1. **Swagger UI 비활성화** (선택사항)
   ```yaml
   springdoc:
     swagger-ui:
       enabled: false
   ```

2. **민감 정보 환경 변수로 관리**
   ```bash
   export DB_PASSWORD=your-secure-password
   ```

3. **HTTPS 사용** (Nginx 리버스 프록시)

4. **방화벽 설정** (필요한 포트만 오픈)

---

## 참고 URL

### 로컬 개발
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/v3/api-docs

### 운영 서버
- Swagger UI: http://3.34.74.224:8080/swagger-ui.html
- API Docs: http://3.34.74.224:8080/v3/api-docs

