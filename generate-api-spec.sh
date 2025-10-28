#!/bin/bash

# API 스펙 생성 스크립트
set -e

# 색상 정의
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

# 설정
SERVER_URL="${SERVER_URL:-http://localhost:8080}"
OUTPUT_DIR="./api-spec"
DOCS_DIR="./docs"

echo -e "${GREEN}======================================"
echo "API 스펙 문서 생성 시작"
echo -e "======================================${NC}"

# 출력 디렉토리 생성
mkdir -p "$OUTPUT_DIR"
mkdir -p "$DOCS_DIR"

# 서버 상태 확인
echo -e "${YELLOW}1. 서버 상태 확인 중...${NC}"
for i in {1..30}; do
    if curl -s "$SERVER_URL/v3/api-docs" > /dev/null 2>&1; then
        echo -e "${GREEN}✅ 서버가 실행 중입니다.${NC}"
        break
    fi
    
    if [ $i -eq 30 ]; then
        echo -e "${RED}❌ 서버가 실행되지 않았습니다. 서버를 먼저 실행하세요:${NC}"
        echo "./gradlew bootRun"
        exit 1
    fi
    
    echo "서버 시작 대기 중... ($i/30)"
    sleep 2
done

# OpenAPI JSON 스펙 다운로드
echo -e "${YELLOW}2. OpenAPI JSON 스펙 다운로드 중...${NC}"
curl -s "$SERVER_URL/v3/api-docs" | jq '.' > "$OUTPUT_DIR/openapi.json"
if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ OpenAPI JSON 스펙 저장: $OUTPUT_DIR/openapi.json${NC}"
else
    echo -e "${RED}❌ OpenAPI JSON 다운로드 실패${NC}"
    exit 1
fi

# OpenAPI YAML 스펙 다운로드 (서버가 지원하는 경우)
echo -e "${YELLOW}3. OpenAPI YAML 스펙 다운로드 시도 중...${NC}"
if curl -s "$SERVER_URL/v3/api-docs.yaml" > "$OUTPUT_DIR/openapi.yaml" 2>/dev/null; then
    echo -e "${GREEN}✅ OpenAPI YAML 스펙 저장: $OUTPUT_DIR/openapi.yaml${NC}"
else
    echo -e "${YELLOW}⚠️ YAML 형태는 지원하지 않습니다. JSON을 YAML로 변환합니다.${NC}"
    # JSON을 YAML로 변환 (yq가 설치된 경우)
    if command -v yq &> /dev/null; then
        yq eval -P "$OUTPUT_DIR/openapi.json" > "$OUTPUT_DIR/openapi.yaml"
        echo -e "${GREEN}✅ JSON을 YAML로 변환 완료: $OUTPUT_DIR/openapi.yaml${NC}"
    else
        echo -e "${YELLOW}⚠️ yq가 설치되지 않아 YAML 변환을 건너뜁니다.${NC}"
    fi
fi

# API 엔드포인트 목록 추출
echo -e "${YELLOW}4. API 엔드포인트 목록 추출 중...${NC}"
jq -r '.paths | keys[]' "$OUTPUT_DIR/openapi.json" | sort > "$OUTPUT_DIR/endpoints.txt"
echo -e "${GREEN}✅ 엔드포인트 목록 저장: $OUTPUT_DIR/endpoints.txt${NC}"

# API 태그 목록 추출
echo -e "${YELLOW}5. API 태그 목록 추출 중...${NC}"
jq -r '.tags[]?.name' "$OUTPUT_DIR/openapi.json" 2>/dev/null | sort > "$OUTPUT_DIR/tags.txt"
echo -e "${GREEN}✅ 태그 목록 저장: $OUTPUT_DIR/tags.txt${NC}"

# 스키마 목록 추출
echo -e "${YELLOW}6. 데이터 스키마 목록 추출 중...${NC}"
jq -r '.components.schemas | keys[]' "$OUTPUT_DIR/openapi.json" | sort > "$OUTPUT_DIR/schemas.txt"
echo -e "${GREEN}✅ 스키마 목록 저장: $OUTPUT_DIR/schemas.txt${NC}"

# 결과 요약
echo -e "${GREEN}7. 생성된 파일 요약:${NC}"
echo "📁 $OUTPUT_DIR/"
echo "  📄 openapi.json - OpenAPI 3.0 JSON 스펙"
if [ -f "$OUTPUT_DIR/openapi.yaml" ]; then
    echo "  📄 openapi.yaml - OpenAPI 3.0 YAML 스펙"
fi
echo "  📄 endpoints.txt - API 엔드포인트 목록 ($(wc -l < "$OUTPUT_DIR/endpoints.txt")개)"
echo "  📄 tags.txt - API 태그 목록 ($(wc -l < "$OUTPUT_DIR/tags.txt")개)"
echo "  📄 schemas.txt - 데이터 스키마 목록 ($(wc -l < "$OUTPUT_DIR/schemas.txt")개)"

echo ""
echo -e "${GREEN}======================================"
echo "API 스펙 문서 생성 완료!"
echo -e "======================================${NC}"
echo ""
echo -e "${YELLOW}💡 다음 단계:${NC}"
echo "1. Swagger UI: $SERVER_URL/swagger-ui.html"
echo "2. OpenAPI 스펙: $OUTPUT_DIR/openapi.json"
echo "3. 엔드포인트 목록: $OUTPUT_DIR/endpoints.txt"
echo ""
echo -e "${YELLOW}💡 추가 도구로 활용 가능:${NC}"
echo "- Postman: openapi.json 파일을 Postman으로 임포트"
echo "- Insomnia: openapi.json 파일을 Insomnia로 임포트"
echo "- Swagger Editor: openapi.json을 편집기에서 열기"
echo "- Code Generator: openapi-generator로 클라이언트 SDK 생성"
