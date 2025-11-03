# 운영 환경 배포 가이드

## 📋 사전 준비사항

### 1. 필요한 정보
- **AWS EC2 서버**: `3.34.74.224`
- **SSH 키 파일**: `.pem` 또는 `.key` 파일
- **서버 사용자**: `ec2-user` (Amazon Linux) 또는 `ubuntu` (Ubuntu)
- **데이터베이스**: 이미 설정되어 있음

### 2. 로컬 환경 확인
```bash
# Java 17 설치 확인
java -version

# Gradle 설치 확인
./gradlew --version

# 빌드 테스트
./gradlew clean build
```

---

## 🚀 배포 방법

### 방법 1: 자동 배포 스크립트 사용 (추천)

#### 1. 배포 스크립트 생성
이미 생성된 `deploy-to-production.sh` 스크립트를 사용합니다.

#### 2. SSH 키 설정
```bash
# SSH 키 파일 경로 확인
ls ~/.ssh/*.pem

# 권한 설정
chmod 400 ~/.ssh/your-key.pem
```

#### 3. 배포 실행
```bash
chmod +x deploy-to-production.sh
./deploy-to-production.sh
```

---

### 방법 2: 수동 배포 (단계별)

#### Step 1: 로컬에서 빌드
```bash
# 프로젝트 디렉토리로 이동
cd /Users/leentj/IdeaProjects/life-cycle

# 빌드 (테스트 포함)
./gradlew clean build

# 빌드된 JAR 확인
ls -lh build/libs/life-cycle-0.0.1-SNAPSHOT.jar
```

#### Step 2: EC2 서버로 파일 전송
```bash
# JAR 파일 업로드
scp -i ~/.ssh/your-key.pem \
    build/libs/life-cycle-0.0.1-SNAPSHOT.jar \
    ec2-user@3.34.74.224:~/

# 운영 환경 설정 파일 업로드
scp -i ~/.ssh/your-key.pem \
    src/main/resources/application-prod.yml \
    ec2-user@3.34.74.224:~/
```

#### Step 3: EC2 서버 접속
```bash
ssh -i ~/.ssh/your-key.pem \
    ec2-user@3.34.74.224
```

#### Step 4: 서버 환경 설정 (최초 1회)
```bash
# Java 17 설치 확인
java -version

# Java 17이 없는 경우 설치 (Amazon Linux 2)
sudo yum install -y java-17-amazon-corretto-devel

# 또는 Ubuntu의 경우
# sudo apt update
# sudo apt install -y openjdk-17-jdk

# 작업 디렉토리 생성
mkdir -p ~/life-cycle
mkdir -p ~/life-cycle/logs

# 파일 이동
mv life-cycle-0.0.1-SNAPSHOT.jar ~/life-cycle/
mv application-prod.yml ~/life-cycle/
```

#### Step 5: 기존 프로세스 종료 (있는 경우)
```bash
# 실행 중인 Java 프로세스 확인
ps aux | grep java

# PID 파일이 있는 경우
if [ -f ~/life-cycle/app.pid ]; then
    kill $(cat ~/life-cycle/app.pid) 2>/dev/null || true
    rm ~/life-cycle/app.pid
fi

# 포트 8080 사용 프로세스 강제 종료
sudo lsof -ti:8080 | xargs sudo kill -9 2>/dev/null || true
```

#### Step 6: 애플리케이션 실행
```bash
cd ~/life-cycle

# 백그라운드로 실행
nohup java -jar \
    -Dspring.profiles.active=prod \
    -Dspring.config.location=file:./application-prod.yml \
    -Xms512m \
    -Xmx1024m \
    -XX:+UseG1GC \
    life-cycle-0.0.1-SNAPSHOT.jar > logs/application.log 2>&1 &

# PID 저장
echo $! > app.pid

# PID 확인
echo "애플리케이션 시작됨 (PID: $(cat app.pid))"
```

#### Step 7: 배포 확인
```bash
# 로그 확인 (서버 시작 대기)
tail -f logs/application.log

# 프로세스 확인
ps -p $(cat app.pid)

# API 확인 (서버가 완전히 시작된 후)
curl -X POST http://localhost:8080/api/church/list -H "Content-Type: application/json"

# Swagger UI 확인
curl -I http://localhost:8080/swagger-ui.html
```

---

### 방법 3: systemd 서비스로 등록 (권장)

운영 환경에서는 systemd 서비스로 등록하여 자동 재시작, 로그 관리 등을 수행하는 것이 좋습니다.

#### 1. 서비스 파일 생성
```bash
sudo vi /etc/systemd/system/life-cycle.service
```

#### 2. 서비스 설정 내용
```ini
[Unit]
Description=Life Cycle Management System
After=network.target

[Service]
Type=simple
User=ec2-user
WorkingDirectory=/home/ec2-user/life-cycle
ExecStart=/usr/bin/java \
    -Dspring.profiles.active=prod \
    -Dspring.config.location=file:/home/ec2-user/life-cycle/application-prod.yml \
    -Xms512m \
    -Xmx1024m \
    -XX:+UseG1GC \
    -jar /home/ec2-user/life-cycle/life-cycle-0.0.1-SNAPSHOT.jar
SuccessExitStatus=143
Restart=always
RestartSec=10
StandardOutput=append:/home/ec2-user/life-cycle/logs/application.log
StandardError=append:/home/ec2-user/life-cycle/logs/error.log

[Install]
WantedBy=multi-user.target
```

#### 3. 서비스 활성화 및 시작
```bash
# 서비스 리로드
sudo systemctl daemon-reload

# 서비스 활성화 (부팅 시 자동 시작)
sudo systemctl enable life-cycle

# 서비스 시작
sudo systemctl start life-cycle

# 서비스 상태 확인
sudo systemctl status life-cycle

# 로그 확인
sudo journalctl -u life-cycle -f
```

#### 4. 서비스 관리 명령어
```bash
# 시작
sudo systemctl start life-cycle

# 중지
sudo systemctl stop life-cycle

# 재시작
sudo systemctl restart life-cycle

# 상태 확인
sudo systemctl status life-cycle

# 부팅 시 자동 시작 비활성화
sudo systemctl disable life-cycle
```

---

## 🔧 Nginx 리버스 프록시 설정 (선택사항)

HTTPS 및 도메인 연결을 위해 Nginx를 사용할 수 있습니다.

### 1. Nginx 설치
```bash
# Amazon Linux 2
sudo amazon-linux-extras install nginx1

# Ubuntu
sudo apt update
sudo apt install nginx
```

### 2. Nginx 설정
```bash
sudo vi /etc/nginx/conf.d/life-cycle.conf
```

```nginx
server {
    listen 80;
    server_name 3.34.74.224;

    # 클라이언트 요청 크기 제한
    client_max_body_size 10M;

    # API 프록시
    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        
        # 타임아웃 설정
        proxy_connect_timeout 60s;
        proxy_send_timeout 60s;
        proxy_read_timeout 60s;
    }
}
```

### 3. Nginx 시작
```bash
# 설정 테스트
sudo nginx -t

# Nginx 시작
sudo systemctl start nginx

# 부팅 시 자동 시작
sudo systemctl enable nginx
```

---

## 🔐 보안 설정

### 1. 방화벽 설정 (AWS Security Group)
EC2 인스턴스의 보안 그룹에서 다음 포트를 오픈:
- **8080**: Spring Boot 애플리케이션 (직접 접근)
- **80**: HTTP (Nginx 사용 시)
- **443**: HTTPS (SSL 인증서 적용 시)

### 2. 애플리케이션 보안
```yaml
# application-prod.yml에 추가
server:
  error:
    include-stacktrace: never
    include-message: always

# Swagger 비활성화 (선택사항)
springdoc:
  swagger-ui:
    enabled: false
```

### 3. 데이터베이스 비밀번호
환경 변수로 관리:
```bash
export DB_PASSWORD=your-secure-password
```

```yaml
# application-prod.yml
spring:
  datasource:
    password: ${DB_PASSWORD:twothree_user123!}
```

---

## 📊 모니터링

### 1. 로그 모니터링
```bash
# 실시간 로그
tail -f ~/life-cycle/logs/application.log

# 에러 로그만
grep ERROR ~/life-cycle/logs/application.log

# 최근 100줄
tail -100 ~/life-cycle/logs/application.log
```

### 2. 프로세스 모니터링
```bash
# 프로세스 상태
ps aux | grep java

# CPU/메모리 사용량
top -p $(cat app.pid)

# 메모리 상세 정보
jstat -gc $(cat app.pid)
```

### 3. 디스크 사용량
```bash
# 로그 파일 크기
du -sh ~/life-cycle/logs/

# 전체 디스크 사용량
df -h
```

---

## 🔄 업데이트 (무중단 배포)

### Blue-Green 배포 방식

#### 1. 새 버전 빌드 및 업로드
```bash
# 로컬에서
./gradlew clean build
scp -i ~/.ssh/your-key.pem \
    build/libs/life-cycle-0.0.1-SNAPSHOT.jar \
    ec2-user@3.34.74.224:~/life-cycle-new.jar
```

#### 2. 서버에서 교체
```bash
# EC2 서버에서
cd ~/life-cycle

# 백업
cp life-cycle-0.0.1-SNAPSHOT.jar life-cycle-backup.jar

# 새 버전으로 교체
mv ~/life-cycle-new.jar life-cycle-0.0.1-SNAPSHOT.jar

# 재시작
sudo systemctl restart life-cycle

# 상태 확인
sudo systemctl status life-cycle
```

---

## 🆘 트러블슈팅

### 문제 1: 서버가 시작되지 않음
```bash
# 로그 확인
tail -100 logs/application.log

# Java 버전 확인
java -version

# 포트 사용 확인
sudo lsof -i:8080
```

### 문제 2: 데이터베이스 연결 실패
```bash
# DB 연결 테스트
psql -h 3.34.74.224 \
     -U twothree_user \
     -d life-cycle

# 방화벽 확인
telnet 3.34.74.224 5432
```

### 문제 3: 메모리 부족
```bash
# 메모리 사용량 확인
free -m

# JVM 힙 크기 조정
# -Xms256m -Xmx512m (최소 설정)
```

### 문제 4: Out of Memory
```bash
# Heap Dump 생성 설정 추가
java -jar \
    -XX:+HeapDumpOnOutOfMemoryError \
    -XX:HeapDumpPath=logs/heap-dump.hprof \
    app.jar
```

---

## 📈 성능 튜닝

### JVM 옵션 최적화
```bash
java -jar \
    -Dspring.profiles.active=prod \
    -Xms512m \
    -Xmx1024m \
    -XX:+UseG1GC \
    -XX:MaxGCPauseMillis=200 \
    -XX:+UseStringDeduplication \
    -XX:+OptimizeStringConcat \
    app.jar
```

### 데이터베이스 커넥션 풀
```yaml
spring:
  datasource:
    hikari:
      maximum-pool-size: 20      # 동시 접속자 수에 따라 조정
      minimum-idle: 10
      connection-timeout: 30000
      max-lifetime: 1800000
```

---

## 🔄 롤백 방법

### 이전 버전으로 복구
```bash
# 백업 파일로 복구
cd ~/life-cycle
mv life-cycle-0.0.1-SNAPSHOT.jar life-cycle-failed.jar
mv life-cycle-backup.jar life-cycle-0.0.1-SNAPSHOT.jar

# 재시작
sudo systemctl restart life-cycle

# 상태 확인
sudo systemctl status life-cycle
```

---

## 📦 Docker를 사용한 운영 배포

### 1. Docker 이미지 빌드
```bash
# 로컬에서 이미지 빌드
docker build -t life-cycle-backend:v1.0 .

# 이미지 확인
docker images | grep life-cycle
```

### 2. Docker Hub에 푸시 (선택사항)
```bash
# Docker Hub 로그인
docker login

# 태그 지정
docker tag life-cycle-backend:v1.0 yourusername/life-cycle-backend:v1.0

# 푸시
docker push yourusername/life-cycle-backend:v1.0
```

### 3. EC2 서버에서 실행
```bash
# Docker 설치 (Amazon Linux 2)
sudo yum update -y
sudo yum install docker -y
sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -aG docker ec2-user

# 이미지 가져오기
docker pull yourusername/life-cycle-backend:v1.0

# 컨테이너 실행
docker run -d \
    --name life-cycle-backend \
    --restart unless-stopped \
    -p 8080:8080 \
    -v ~/life-cycle/logs:/app/logs \
    -e SPRING_PROFILES_ACTIVE=prod \
    life-cycle-backend:v1.0

# 로그 확인
docker logs -f life-cycle-backend
```

---

## 🌐 접속 URL

### 운영 서버 배포 후:
- **Swagger UI**: `http://3.34.74.224:8080/swagger-ui.html`
- **API Docs**: `http://3.34.74.224:8080/v3/api-docs`
- **Church API**: `http://3.34.74.224:8080/api/church/list`

### 헬스 체크
```bash
curl -X POST \
  http://3.34.74.224:8080/api/church/list \
  -H "Content-Type: application/json"
```

---

## 📝 배포 체크리스트

### 배포 전:
- [x] 로컬에서 빌드 성공 확인
- [x] 모든 테스트 통과 확인
- [ ] application-prod.yml 설정 검토
- [ ] DB 연결 정보 확인
- [ ] SSH 키 파일 준비
- [ ] 서버 접속 확인

### 배포 중:
- [ ] 기존 서비스 중지
- [ ] JAR 파일 업로드
- [ ] 권한 설정
- [ ] 서비스 시작

### 배포 후:
- [ ] 서버 시작 확인
- [ ] API 응답 확인
- [ ] Swagger UI 접속 확인
- [ ] 로그 확인
- [ ] 성능 모니터링

---

## 💡 추가 권장사항

### 1. SSL/TLS 인증서 적용
Let's Encrypt 무료 인증서 사용:
```bash
sudo yum install certbot python3-certbot-nginx
sudo certbot --nginx -d your-domain.com
```

### 2. 로그 로테이션
```bash
# logrotate 설정
sudo vi /etc/logrotate.d/life-cycle

/home/ec2-user/life-cycle/logs/*.log {
    daily
    rotate 30
    compress
    delaycompress
    missingok
    notifempty
}
```

### 3. 자동 백업
정기적으로 JAR 파일과 설정 파일을 백업:
```bash
# crontab 설정
crontab -e

# 매일 새벽 3시 백업
0 3 * * * tar -czf ~/backups/life-cycle-$(date +\%Y\%m\%d).tar.gz ~/life-cycle/
```

### 4. 모니터링 도구
- **Prometheus + Grafana**: 메트릭 수집 및 시각화
- **ELK Stack**: 로그 수집 및 분석
- **AWS CloudWatch**: AWS 네이티브 모니터링

---

## 📞 문제 발생 시

1. **로그 확인**: `tail -f logs/application.log`
2. **프로세스 확인**: `ps aux | grep java`
3. **포트 확인**: `sudo lsof -i:8080`
4. **메모리 확인**: `free -m`
5. **디스크 확인**: `df -h`

문제가 지속되면 로그 파일과 함께 문의하세요.

