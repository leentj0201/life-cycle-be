#!/bin/bash

# AWS EC2 운영 서버 배포 스크립트
set -e

# 색상 정의
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 설정
SSH_KEY="${SSH_KEY:-$HOME/.ssh/life-cycle-prod.pem}"
SERVER_USER="${SERVER_USER:-ubuntu}"
SERVER_HOST="${SERVER_HOST:-ec2-43-200-180-98.ap-northeast-2.compute.amazonaws.com}"
APP_NAME="life-cycle-0.0.1-SNAPSHOT.jar"
REMOTE_DIR="~/life-cycle-prod"
PROFILE="prod"

echo -e "${GREEN}=========================================="
echo "PROD 환경 배포 시작 (운영 서버)"
echo -e "==========================================${NC}"
echo -e "${BLUE}환경: PROD (Production)${NC}"  
echo -e "${BLUE}서버: $SERVER_HOST${NC}"
echo -e "${BLUE}프로필: $PROFILE${NC}"
echo -e "${BLUE}데이터베이스: PROD-DB (미래)${NC}"
echo ""

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
echo -e "${GREEN}3. PROD 서버 디렉토리 준비 중...${NC}"
ssh -i "$SSH_KEY" "$SERVER_USER@$SERVER_HOST" << 'EOF'
mkdir -p ~/life-cycle-prod
mkdir -p ~/life-cycle-prod/logs
mkdir -p ~/life-cycle-prod/backup
mkdir -p ~/life-cycle-prod/config
EOF

# 5. 기존 파일 백업
echo -e "${GREEN}4. 기존 파일 백업 중...${NC}"
ssh -i "$SSH_KEY" "$SERVER_USER@$SERVER_HOST" << 'EOF'
cd ~/life-cycle-prod
if [ -f life-cycle-0.0.1-SNAPSHOT.jar ]; then
    BACKUP_NAME="life-cycle-prod-backup-$(date +%Y%m%d_%H%M%S).jar"
    mv life-cycle-0.0.1-SNAPSHOT.jar backup/$BACKUP_NAME
    echo "PROD 백업 완료: $BACKUP_NAME"
fi
EOF

# 6. JAR 파일 업로드
echo -e "${GREEN}5. JAR 파일 업로드 중...${NC}"
scp -i "$SSH_KEY" "$JAR_FILE" "$SERVER_USER@$SERVER_HOST:~/life-cycle-prod/$APP_NAME"
if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ 파일 업로드 완료${NC}"
else
    echo -e "${RED}❌ 파일 업로드 실패${NC}"
    exit 1
fi

# 7. PROD 설정 파일 업로드
echo -e "${GREEN}6. PROD 설정 파일 업로드 중...${NC}"
scp -i "$SSH_KEY" src/main/resources/application-prod.yml \
    "$SERVER_USER@$SERVER_HOST:~/life-cycle-prod/"

# 8. 기존 프로세스 종료 및 새 프로세스 시작
echo -e "${GREEN}7. PROD 애플리케이션 재시작 중...${NC}"
ssh -i "$SSH_KEY" "$SERVER_USER@$SERVER_HOST" << 'EOF'
cd ~/life-cycle-prod

echo "기존 PROD 프로세스 확인 및 종료..."

# 기존 프로세스 종료
if [ -f prod-app.pid ]; then
    OLD_PID=$(cat prod-app.pid)
    if ps -p $OLD_PID > /dev/null 2>&1; then
        echo "기존 PROD 프로세스 종료 중 (PID: $OLD_PID)..."
        kill $OLD_PID
        sleep 5
        
        # 강제 종료
        if ps -p $OLD_PID > /dev/null 2>&1; then
            kill -9 $OLD_PID 2>/dev/null || true
        fi
    fi
    rm prod-app.pid
fi

# 포트 8080에서 실행 중인 다른 프로세스 확인 및 종료
PORT_PID=$(sudo lsof -ti:8080 2>/dev/null || true)
if [ -n "$PORT_PID" ]; then
    echo "포트 8080을 사용하는 프로세스 종료 중 (PID: $PORT_PID)..."
    sudo kill -9 $PORT_PID 2>/dev/null || true
fi

sleep 3

# 새 PROD 프로세스 시작
echo "PROD 환경으로 새 프로세스 시작 중..."
nohup java -jar \
    -Dspring.profiles.active=prod \
    -Dspring.config.location=file:./application-prod.yml \
    -Xms1024m \
    -Xmx2048m \
    -XX:+UseG1GC \
    -Dserver.port=8080 \
    life-cycle-0.0.1-SNAPSHOT.jar > logs/prod-application.log 2>&1 &

echo $! > prod-app.pid
echo "새 PROD 프로세스 시작됨 (PID: $(cat prod-app.pid))"
echo "로그 파일: ~/life-cycle-prod/logs/prod-application.log"
EOF

# 9. 배포 확인
echo -e "${GREEN}8. PROD 서버 배포 확인 중...${NC}"
sleep 15

for i in {1..30}; do
    HTTP_CODE=$(curl -s -o /dev/null -w "%{http_code}" \
        -X POST \
        "http://$SERVER_HOST:8080/api/church/list" \
        -H "Content-Type: application/json" 2>/dev/null)
    
    if [ "$HTTP_CODE" = "200" ]; then
        echo -e "${GREEN}✅ PROD 서버가 정상적으로 시작되었습니다!${NC}"
        echo ""
        echo -e "${GREEN}=========================================="
        echo "PROD 환경 배포 완료!"
        echo -e "==========================================${NC}"
        echo -e "${BLUE}🌐 PROD 서버 정보:${NC}"
        echo "• 서버 URL: http://$SERVER_HOST:8080"
        echo "• Swagger UI: http://$SERVER_HOST:8080/swagger-ui.html (운영에서는 비활성화)"
        echo "• API Docs: http://$SERVER_HOST:8080/v3/api-docs (운영에서는 비활성화)"
        echo "• 환경: PROD (Production)"
        echo "• 데이터베이스: PROD-DB (미래 구성)"
        echo ""
        echo -e "${YELLOW}💡 유용한 명령어:${NC}"
        echo "• 로그 확인: ssh -i $SSH_KEY $SERVER_USER@$SERVER_HOST 'tail -f ~/life-cycle-prod/logs/prod-application.log'"
        echo "• 프로세스 확인: ssh -i $SSH_KEY $SERVER_USER@$SERVER_HOST 'ps -p \$(cat ~/life-cycle-prod/prod-app.pid)'"
        echo "• 서버 중지: ssh -i $SSH_KEY $SERVER_USER@$SERVER_HOST 'kill \$(cat ~/life-cycle-prod/prod-app.pid)'"
        echo ""
        echo -e "${GREEN}🧪 테스트 API 호출:${NC}"
        echo "curl -X POST http://$SERVER_HOST:8080/api/church/list -H 'Content-Type: application/json' -d '{}'"
        
        exit 0
    fi
    
    echo "PROD 서버 시작 대기 중... ($i/30)"
    sleep 2
done

echo -e "${RED}❌ PROD 서버 시작 실패. 로그를 확인하세요.${NC}"
echo -e "${YELLOW}로그 확인 명령어:${NC}"
echo "ssh -i $SSH_KEY $SERVER_USER@$SERVER_HOST 'tail -100 ~/life-cycle-prod/logs/prod-application.log'"
echo ""
echo -e "${YELLOW}디버깅 도움말:${NC}"
echo "1. 서버 접속: ssh -i $SSH_KEY $SERVER_USER@$SERVER_HOST"
echo "2. 프로세스 확인: ps aux | grep java"
echo "3. 포트 확인: sudo lsof -i:8080"
echo "4. Java 버전 확인: java -version"
echo "5. 디스크 공간 확인: df -h"
echo "6. 메모리 확인: free -m"

exit 1

