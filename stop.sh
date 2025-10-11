#!/bin/bash

# 서버 중지 스크립트
set -e

echo "======================================"
echo "생애주기 관리 시스템 중지"
echo "======================================"

# PID 파일 확인
if [ -f app.pid ]; then
    PID=$(cat app.pid)
    
    if ps -p $PID > /dev/null 2>&1; then
        echo "프로세스 종료 중... (PID: $PID)"
        kill $PID
        
        # 정상 종료 대기
        for i in {1..10}; do
            if ! ps -p $PID > /dev/null 2>&1; then
                echo "✅ 프로세스가 정상적으로 종료되었습니다."
                rm app.pid
                exit 0
            fi
            sleep 1
        done
        
        # 강제 종료
        echo "프로세스를 강제 종료합니다..."
        kill -9 $PID 2>/dev/null || true
        rm app.pid
        echo "✅ 프로세스가 강제 종료되었습니다."
    else
        echo "⚠️  PID $PID 프로세스가 실행 중이 아닙니다."
        rm app.pid
    fi
else
    echo "⚠️  app.pid 파일이 없습니다."
    
    # 포트 8080을 사용 중인 프로세스 확인
    if lsof -ti:8080 > /dev/null 2>&1; then
        echo "포트 8080을 사용 중인 프로세스를 종료합니다..."
        lsof -ti:8080 | xargs kill -9 2>/dev/null || true
        echo "✅ 포트 8080 프로세스가 종료되었습니다."
    else
        echo "실행 중인 프로세스가 없습니다."
    fi
fi

echo "======================================"
echo "중지 완료"
echo "======================================"

