#!/bin/bash

# AWS EC2 운영 서버 배포 스크립트
set -e

# 색상 정의
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 설정
SSH_KEY="${SSH_KEY:-$HOME/twothree-int.pem}"
SERVER_USER="${SERVER_USER:-ec2-user}"
SERVER_HOST="${SERVER_HOST:-ec2-54-180-152-24.ap-northeast-2.compute.amazonaws.com}"
APP_NAME="life-cycle-0.0.1-SNAPSHOT.jar"
REMOTE_DIR="~/life-cycle"

echo -e "${GREEN}======================================"
echo "운영 환경 배포 시작"
echo -e "======================================${NC}"

# 1. SSH 키 확인
if [ ! -f "$SSH_KEY" ]; then
    echo -e "${RED}❌ SSH 키 파일을 찾을 수 없습니다: $SSH_KEY${NC}"
    echo -e "${YELLOW}SSH_KEY 환경 변수를 설정하거나 스크립트를 수정하세요.${NC}"
    echo "예: export SSH_KEY=~/.ssh/your-actual-key.pem"
    exit 1
fi

# 2. 로컬 빌드
echo -e "${GREEN}1. 애플리케이션 빌드 중...${NC}"
./gradlew clean build

if [ $? -ne 0 ]; then
    echo -e "${RED}❌ 빌드 실패${NC}"
    exit 1
fi

JAR_FILE=$(ls build/libs/*.jar | grep -v plain | head -1)
if [ -z "$JAR_FILE" ]; then
    echo -e "${RED}❌ JAR 파일을 찾을 수 없습니다.${NC}"
    exit 1
fi
echo -e "${GREEN}✅ 빌드 완료: $JAR_FILE${NC}"

# 3. 서버 연결 테스트
echo -e "${GREEN}2. 서버 연결 테스트 중...${NC}"
ssh -i "$SSH_KEY" -o ConnectTimeout=10 "$SERVER_USER@$SERVER_HOST" "echo '연결 성공'" 2>/dev/null
if [ $? -ne 0 ]; then
    echo -e "${RED}❌ 서버 연결 실패${NC}"
    echo "서버 주소: $SERVER_USER@$SERVER_HOST"
    echo "SSH 키: $SSH_KEY"
    exit 1
fi
echo -e "${GREEN}✅ 서버 연결 성공${NC}"

# 4. 디렉토리 생성
echo -e "${GREEN}3. 서버 디렉토리 준비 중...${NC}"
ssh -i "$SSH_KEY" "$SERVER_USER@$SERVER_HOST" << 'EOF'
mkdir -p ~/life-cycle
mkdir -p ~/life-cycle/logs
mkdir -p ~/life-cycle/backup
EOF

# 5. 기존 파일 백업
echo -e "${GREEN}4. 기존 파일 백업 중...${NC}"
ssh -i "$SSH_KEY" "$SERVER_USER@$SERVER_HOST" << 'EOF'
if [ -f ~/life-cycle/life-cycle-0.0.1-SNAPSHOT.jar ]; then
    BACKUP_NAME="life-cycle-backup-$(date +%Y%m%d_%H%M%S).jar"
    mv ~/life-cycle/life-cycle-0.0.1-SNAPSHOT.jar ~/life-cycle/backup/$BACKUP_NAME
    echo "백업 완료: $BACKUP_NAME"
fi
EOF

# 6. JAR 파일 업로드
echo -e "${GREEN}5. JAR 파일 업로드 중...${NC}"
scp -i "$SSH_KEY" "$JAR_FILE" "$SERVER_USER@$SERVER_HOST:~/life-cycle/$APP_NAME"
if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ 파일 업로드 완료${NC}"
else
    echo -e "${RED}❌ 파일 업로드 실패${NC}"
    exit 1
fi

# 7. 운영 설정 파일 업로드
echo -e "${GREEN}6. 설정 파일 업로드 중...${NC}"
scp -i "$SSH_KEY" src/main/resources/application-prod.yml \
    "$SERVER_USER@$SERVER_HOST:~/life-cycle/"

# 8. 기존 프로세스 종료 및 새 프로세스 시작
echo -e "${GREEN}7. 애플리케이션 재시작 중...${NC}"
ssh -i "$SSH_KEY" "$SERVER_USER@$SERVER_HOST" << 'EOF'
cd ~/life-cycle

# 기존 프로세스 종료
if [ -f app.pid ]; then
    OLD_PID=$(cat app.pid)
    if ps -p $OLD_PID > /dev/null 2>&1; then
        echo "기존 프로세스 종료 중 (PID: $OLD_PID)..."
        kill $OLD_PID
        sleep 5
        
        # 강제 종료
        if ps -p $OLD_PID > /dev/null 2>&1; then
            kill -9 $OLD_PID 2>/dev/null || true
        fi
    fi
    rm app.pid
fi

# 포트 정리
sudo lsof -ti:8080 | xargs sudo kill -9 2>/dev/null || true
sleep 2

# 새 프로세스 시작
nohup java -jar \
    -Dspring.profiles.active=prod \
    -Dspring.config.location=file:./application-prod.yml \
    -Xms512m \
    -Xmx1024m \
    -XX:+UseG1GC \
    life-cycle-0.0.1-SNAPSHOT.jar > logs/application.log 2>&1 &

echo $! > app.pid
echo "새 프로세스 시작됨 (PID: $(cat app.pid))"
EOF

# 9. 배포 확인
echo -e "${GREEN}8. 배포 확인 중...${NC}"
sleep 10

for i in {1..30}; do
    HTTP_CODE=$(curl -s -o /dev/null -w "%{http_code}" \
        -X POST \
        "http://$SERVER_HOST:8080/api/church/list" \
        -H "Content-Type: application/json" 2>/dev/null)
    
    if [ "$HTTP_CODE" = "200" ]; then
        echo -e "${GREEN}✅ 서버가 정상적으로 시작되었습니다!${NC}"
        echo ""
        echo -e "${GREEN}======================================"
        echo "배포 완료!"
        echo -e "======================================${NC}"
        echo "Swagger UI: http://$SERVER_HOST:8080/swagger-ui.html"
        echo "API Docs: http://$SERVER_HOST:8080/v3/api-docs"
        echo ""
        echo "로그 확인: ssh -i $SSH_KEY $SERVER_USER@$SERVER_HOST 'tail -f ~/life-cycle/logs/application.log'"
        exit 0
    fi
    
    echo "서버 시작 대기 중... ($i/30)"
    sleep 2
done

echo -e "${RED}❌ 서버 시작 실패. 로그를 확인하세요.${NC}"
echo "로그 확인: ssh -i $SSH_KEY $SERVER_USER@$SERVER_HOST 'tail -100 ~/life-cycle/logs/application.log'"
exit 1

