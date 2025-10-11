#!/bin/bash

# 배포 스크립트
set -e

echo "======================================"
echo "생애주기 관리 시스템 배포 시작"
echo "======================================"

# 1. 빌드
echo "1. 애플리케이션 빌드 중..."
./gradlew clean build -x test

# 2. 빌드된 JAR 파일 확인
JAR_FILE=$(ls build/libs/*.jar | grep -v plain | head -1)
if [ -z "$JAR_FILE" ]; then
    echo "❌ JAR 파일을 찾을 수 없습니다."
    exit 1
fi
echo "✅ 빌드 완료: $JAR_FILE"

# 3. 기존 프로세스 종료
echo "2. 기존 프로세스 확인 및 종료..."
if lsof -ti:8080 > /dev/null 2>&1; then
    echo "포트 8080을 사용 중인 프로세스를 종료합니다..."
    lsof -ti:8080 | xargs kill -9 2>/dev/null || true
    sleep 2
fi

# 4. 로그 디렉토리 생성
echo "3. 로그 디렉토리 생성..."
mkdir -p logs

# 5. 애플리케이션 실행
echo "4. 애플리케이션 실행..."
nohup java -jar \
    -Dspring.profiles.active=prod \
    -Xms512m \
    -Xmx1024m \
    -XX:+UseG1GC \
    $JAR_FILE > logs/application.log 2>&1 &

# PID 저장
echo $! > app.pid
echo "✅ 애플리케이션 시작됨 (PID: $(cat app.pid))"

# 6. 헬스체크
echo "5. 서버 시작 대기 중..."
for i in {1..30}; do
    sleep 2
    if curl -s http://localhost:8080/v3/api-docs > /dev/null 2>&1; then
        echo "✅ 서버 시작 완료!"
        echo ""
        echo "======================================"
        echo "배포 완료!"
        echo "======================================"
        echo "Swagger UI: http://localhost:8080/swagger-ui.html"
        echo "API Docs: http://localhost:8080/v3/api-docs"
        echo "로그 파일: logs/application.log"
        echo "PID 파일: app.pid"
        echo ""
        echo "서버 중지: kill \$(cat app.pid)"
        exit 0
    fi
    echo "대기 중... ($i/30)"
done

echo "❌ 서버 시작 실패. 로그를 확인하세요: tail -f logs/application.log"
exit 1

