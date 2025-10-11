-- 생애주기 Database DDL

-- ============================================================================
-- 1. 교회 테이블
-- ============================================================================

-- 교회 테이블
CREATE TABLE church (
                        church_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                        name VARCHAR(100) NOT NULL,
                        address TEXT,
                        phone VARCHAR(20),
                        email VARCHAR(100),
                        website VARCHAR(200),
                        pastor_name VARCHAR(50),
                        description TEXT,
                        is_active BOOLEAN DEFAULT true,
                        created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                        created_by UUID,
                        updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                        updated_by UUID
);

-- 교회 테이블 COMMENT
COMMENT ON TABLE church IS '교회 정보를 관리하는 테이블 (다중 교회 지원)';
COMMENT ON COLUMN church.church_id IS '교회 고유 식별자 (UUID)';
COMMENT ON COLUMN church.name IS '교회명 (최대 100자)';
COMMENT ON COLUMN church.address IS '교회 주소';
COMMENT ON COLUMN church.phone IS '교회 전화번호';
COMMENT ON COLUMN church.email IS '교회 이메일';
COMMENT ON COLUMN church.website IS '교회 웹사이트 URL';
COMMENT ON COLUMN church.pastor_name IS '담임목사명 (최대 50자)';
COMMENT ON COLUMN church.description IS '교회 설명';
COMMENT ON COLUMN church.is_active IS '교회 활성 상태 (기본값: true)';
COMMENT ON COLUMN church.created_at IS '생성일시 (UTC)';
COMMENT ON COLUMN church.updated_at IS '수정일시 (UTC, 자동 업데이트)';
COMMENT ON COLUMN church.updated_by IS '수정자 회원 ID (외래키)';

-- ============================================================================
-- 2. 부서 테이블
-- ============================================================================

-- 부서 테이블
CREATE TABLE department (
                            department_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                            church_id UUID NOT NULL,
                            name VARCHAR(100) NOT NULL,
                            description TEXT,
                            is_active BOOLEAN DEFAULT true,
                            created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                            created_by UUID,
                            updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                            updated_by UUID
);

-- 부서 테이블 인덱스
CREATE INDEX idx_department_church_id ON department(church_id);

-- 부서 테이블 COMMENT
COMMENT ON TABLE department IS '교회 부서 정보를 관리하는 테이블 (청년부, 유아부, 초등부 등)';
COMMENT ON COLUMN department.department_id IS '부서 고유 식별자 (UUID)';
COMMENT ON COLUMN department.church_id IS '소속 교회 ID (외래키)';
COMMENT ON COLUMN department.name IS '부서명 (최대 100자)';
COMMENT ON COLUMN department.description IS '부서 설명';
COMMENT ON COLUMN department.is_active IS '부서 활성 상태 (기본값: true)';
COMMENT ON COLUMN department.created_at IS '생성일시 (UTC)';
COMMENT ON COLUMN department.created_by IS '생성자 회원 ID (외래키)';
COMMENT ON COLUMN department.updated_at IS '수정일시 (UTC, 자동 업데이트)';
COMMENT ON COLUMN department.updated_by IS '수정자 회원 ID (외래키)';

-- ============================================================================
-- 3. 부서 콘텐츠 테이블
-- ============================================================================

-- 부서 콘텐츠 테이블
CREATE TABLE department_content (
                                    department_content_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                    church_id UUID NOT NULL,
                                    department_id UUID NOT NULL,
                                    department_content_type varchar(50) not null,
                                    title VARCHAR(200) NOT NULL,
                                    content TEXT,
                                    is_active BOOLEAN DEFAULT true,
                                    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                    created_by UUID,
                                    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                    updated_by UUID
);

-- 부서 콘텐트 테이블 인덱스
CREATE INDEX idx_department_content_church_id ON department_content(church_id);
CREATE INDEX idx_department_content_department_id ON department_content(department_id);

-- 부서 콘텐츠 테이블 COMMENT
COMMENT ON TABLE department_content IS '다양한 형태의 부서 콘텐츠를 관리하는 테이블 (글, 동영상, 오디오, 이미지, 문서, 링크)';
COMMENT ON COLUMN department_content.department_content_id IS '콘텐츠 고유 식별자 (UUID)';
COMMENT ON COLUMN department_content.church_id IS '소속 교회 ID (외래키)';
COMMENT ON COLUMN department_content.department_id IS '부서 ID (외래키)';
COMMENT ON COLUMN department_content.department_content_type IS '콘텐츠 타입 (ARTICLE/VIDEO/AUDIO/IMAGE/DOCUMENT/LINK)';
COMMENT ON COLUMN department_content.title IS '콘텐츠 제목 (최대 200자)';
COMMENT ON COLUMN department_content.content IS '콘텐츠 본문 (텍스트)';
COMMENT ON COLUMN department_content.is_active IS '컨텐츠 활성 상태 (기본값: true)';
COMMENT ON COLUMN department_content.created_at IS '생성일시 (UTC)';
COMMENT ON COLUMN department_content.created_by IS '생성자 회원 ID (외래키)';
COMMENT ON COLUMN department_content.updated_at IS '수정일시 (UTC, 자동 업데이트)';
COMMENT ON COLUMN department_content.updated_by IS '수정자 회원 ID (외래키)';


-- ============================================================================
-- 4. 생애주기 테이블
-- ============================================================================

-- 생애주기 테이블
CREATE TABLE lifecycle (
                           lifecycle_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                           church_id UUID NOT NULL,
                           name VARCHAR(100) NOT NULL,
                           description TEXT,
                           is_active BOOLEAN DEFAULT true,
                           created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                           created_by UUID,
                           updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                           updated_by UUID
);

-- 생애주기 테이블 인덱스
CREATE INDEX idx_lifecycle_church_id ON lifecycle(church_id);

-- 부서 테이블 COMMENT
COMMENT ON TABLE lifecycle IS '생애주기 정보를 관리하는 테이블 (신혼, 출산, 초등학교, 자녀 독립기 )';
COMMENT ON COLUMN lifecycle.lifecycle_id IS '생애주기 고유 식별자 (UUID)';
COMMENT ON COLUMN lifecycle.church_id IS '소속 교회 ID (외래키)';
COMMENT ON COLUMN lifecycle.name IS '생애주기명 (최대 100자)';
COMMENT ON COLUMN lifecycle.description IS '생애주기 설명';
COMMENT ON COLUMN lifecycle.is_active IS '생애주기 활성 상태 (기본값: true)';
COMMENT ON COLUMN lifecycle.created_at IS '생성일시 (UTC)';
COMMENT ON COLUMN lifecycle.created_by IS '생성자 회원 ID (외래키)';
COMMENT ON COLUMN lifecycle.updated_at IS '수정일시 (UTC, 자동 업데이트)';
COMMENT ON COLUMN lifecycle.updated_by IS '수정자 회원 ID (외래키)';

-- ============================================================================
-- 5. 생애주기 콘텐츠 테이블
-- ============================================================================

-- 생애주기 콘텐츠 테이블
CREATE TABLE lifecycle_content (
                                   lifecycle_content_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                   church_id UUID NOT NULL,
                                   lifecycle_id UUID NOT NULL,
                                   lifecycle_content_type varchar(50) not null,
                                   title VARCHAR(200) NOT NULL,
                                   content TEXT,
                                   is_active BOOLEAN DEFAULT true,
                                   created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                   created_by UUID,
                                   updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                   updated_by UUID
);

-- 생애주기 콘텐트 테이블 인덱스
CREATE INDEX idx_lifecycle_content_church_id ON lifecycle_content(church_id);
CREATE INDEX idx_lifecycle_content_lifecycle_id ON lifecycle_content(lifecycle_id);

-- 부서 콘텐츠 테이블 COMMENT
COMMENT ON TABLE lifecycle_content IS '다양한 형태의 부서 콘텐츠를 관리하는 테이블 (글, 동영상, 오디오, 이미지, 문서, 링크)';
COMMENT ON COLUMN lifecycle_content.lifecycle_content_id IS '콘텐츠 고유 식별자 (UUID)';
COMMENT ON COLUMN lifecycle_content.church_id IS '소속 교회 ID (외래키)';
COMMENT ON COLUMN lifecycle_content.lifecycle_id IS '생애주기 ID (외래키)';
COMMENT ON COLUMN lifecycle_content.lifecycle_content_type IS '콘텐츠 타입 (ARTICLE/VIDEO/AUDIO/IMAGE/DOCUMENT/LINK)';
COMMENT ON COLUMN lifecycle_content.title IS '콘텐츠 제목 (최대 200자)';
COMMENT ON COLUMN lifecycle_content.content IS '콘텐츠 본문 (텍스트)';
COMMENT ON COLUMN lifecycle_content.is_active IS '컨텐츠 활성 상태 (기본값: true)';
COMMENT ON COLUMN lifecycle_content.created_at IS '생성일시 (UTC)';
COMMENT ON COLUMN lifecycle_content.created_by IS '생성자 회원 ID (외래키)';
COMMENT ON COLUMN lifecycle_content.updated_at IS '수정일시 (UTC, 자동 업데이트)';
COMMENT ON COLUMN lifecycle_content.updated_by IS '수정자 회원 ID (외래키)';


-- ============================================================================
-- 7. 회원 테이블
-- ============================================================================

-- 회원 테이블
CREATE TABLE member (
                      member_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                      church_id UUID NOT NULL,
                      login_id VARCHAR(255) NOT NULL,
                      password VARCHAR(255) NOT NULL,
                      name VARCHAR(50) NOT NULL,
                      phone VARCHAR(20),
                      birth DATE,
                      member_role VARCHAR(50) DEFAULT 'MEMBER',
                      is_active BOOLEAN DEFAULT true,
                      last_login_at TIMESTAMP WITH TIME ZONE,
                      created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                      created_by UUID,
                      updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                      updated_by UUID
);

-- 회원 테이블 인덱스
CREATE INDEX idx_member_church_id ON member(church_id);
CREATE INDEX idx_member_is_active ON member(is_active);

-- 회원 테이블 COMMENT
COMMENT ON TABLE member IS '교인 정보를 관리하는 테이블 (4단계 권한 체계 포함)';
COMMENT ON COLUMN member.member_id IS '회원 고유 식별자 (UUID)';
COMMENT ON COLUMN member.church_id IS '소속 교회 ID (외래키)';
COMMENT ON COLUMN member.login_id IS '로그인 ID';
COMMENT ON COLUMN member.password IS '비밀번호';
COMMENT ON COLUMN member.name IS '이름 (최대 50자)';
COMMENT ON COLUMN member.phone IS '전화번호';
COMMENT ON COLUMN member.birth IS '생년월일';
COMMENT ON COLUMN member.member_role IS '회원 역할 (기본값: MEMBER)';
COMMENT ON COLUMN member.is_active IS '계정 활성 상태 (기본값: true)';
COMMENT ON COLUMN member.last_login_at IS '최종 로그인 일시';
COMMENT ON COLUMN member.created_at IS '생성일시 (UTC)';
COMMENT ON COLUMN member.created_by IS '생성자 회원 ID (외래키)';
COMMENT ON COLUMN member.updated_at IS '수정일시 (UTC, 자동 업데이트)';
COMMENT ON COLUMN member.updated_by IS '수정자 회원 ID (외래키)';

-- ============================================================================
-- 7. 회원의 자녀 테이블
-- ============================================================================

-- 회원 가족 테이블
CREATE TABLE member_family (
                             member_family_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                             member_id UUID NOT NULL,
                             family_member_id UUID NOT NULL,
                             relation_type VARCHAR(50) NOT NULL,
                             created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                             created_by UUID,
                             updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                             updated_by UUID
);

-- 회원 자녀 테이블 COMMENT
COMMENT ON TABLE member_family IS '회원 가족 관리하는 테이블';
COMMENT ON COLUMN member_family.member_family_id IS '가족 관계 고유 식별자 (UUID)';
COMMENT ON COLUMN member_family.member_id IS '기준 회원 ID (외래키)';
COMMENT ON COLUMN member_family.family_member_id IS '지녀 회원 ID (외래키)';
COMMENT ON COLUMN member_family.relation_type IS '회원와의 관계';
COMMENT ON COLUMN member_family.created_at IS '생성일시 (UTC)';
COMMENT ON COLUMN member_family.created_by IS '생성자 회원 ID (외래키)';
COMMENT ON COLUMN member_family.updated_at IS '수정일시 (UTC, 자동 업데이트)';
COMMENT ON COLUMN member_family.updated_by IS '수정자 회원 ID (외래키)';

-- ============================================================================
-- 7. 회원의 부서 테이블
-- ============================================================================

-- 회원-부서 연결 테이블
CREATE TABLE member_department (
                                 member_department_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                 member_id UUID NOT NULL,
                                 department_id UUID NOT NULL,
                                 is_active BOOLEAN DEFAULT true,
                                 created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                 created_by UUID,
                                 updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                 updated_by UUID
);

-- 회원-부서 테이블 COMMENT
COMMENT ON TABLE member_department IS '회원와 부서 간의 소속 관계를 관리하는 연결 테이블';
COMMENT ON COLUMN member_department.member_department_id IS '회원-부서 연결 고유 식별자 (UUID)';
COMMENT ON COLUMN member_department.member_id IS '회원 ID (외래키)';
COMMENT ON COLUMN member_department.department_id IS '부서 ID (외래키)';
COMMENT ON COLUMN member_department.is_active IS '소속 활성 상태 (기본값: true)';
COMMENT ON COLUMN member_department.created_at IS '생성일시 (UTC)';
COMMENT ON COLUMN member_department.created_by IS '생성자 회원 ID (외래키)';
COMMENT ON COLUMN member_department.updated_at IS '수정일시 (UTC, 자동 업데이트)';
COMMENT ON COLUMN member_department.updated_by IS '수정자 회원 ID (외래키)';

-- ============================================================================
-- 7. 회원의 생애주기 테이블
-- ============================================================================

-- 회원의 생애주기 테이블
CREATE TABLE member_lifecycle (
                                member_lifecycle_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                member_id UUID NOT NULL,
                                lifecycle_id UUID NOT NULL,
                                is_active BOOLEAN DEFAULT true,
                                created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                created_by UUID,
                                updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                updated_by UUID
);

-- 회원-생애주기 테이블 COMMENT
COMMENT ON TABLE member_lifecycle IS '회원의 생애주기 단계 진행 상황을 관리하는 연결 테이블';
COMMENT ON COLUMN member_lifecycle.member_lifecycle_id IS '회원-생애주기 연결 고유 식별자 (UUID)';
COMMENT ON COLUMN member_lifecycle.member_id IS '회원 ID (외래키)';
COMMENT ON COLUMN member_lifecycle.lifecycle_id IS '생애주기 단계 ID (외래키)';
COMMENT ON COLUMN member_lifecycle.is_active IS '단계 시작일시 (UTC)';
COMMENT ON COLUMN member_lifecycle.created_at IS '생성일시 (UTC)';
COMMENT ON COLUMN member_lifecycle.created_by IS '생성자 회원 ID (외래키)';
COMMENT ON COLUMN member_lifecycle.updated_at IS '수정일시 (UTC, 자동 업데이트)';
COMMENT ON COLUMN member_lifecycle.updated_by IS '수정자 회원 ID (외래키)';


-- ============================================================================
-- 8. 회원 설정 테이블
-- ============================================================================

-- 회원 설정 테이블
CREATE TABLE member_setting (
                              member_setting_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                              member_id UUID NOT NULL UNIQUE,
                              is_notification BOOLEAN DEFAULT true,
                              created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                              created_by UUID,
                              updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                              updated_by UUID
);

-- 회원 설정 테이블 COMMENT
COMMENT ON TABLE member_setting IS '회원별 앱 설정을 관리하는 테이블';
COMMENT ON COLUMN member_setting.member_setting_id IS '회원 설정 고유 식별자 (UUID)';
COMMENT ON COLUMN member_setting.member_id IS '회원 ID (외래키, 유니크)';
COMMENT ON COLUMN member_setting.is_notification IS '알림 활성화 여부 (기본값: true)';
COMMENT ON COLUMN member_setting.created_at IS '생성일시 (UTC)';
COMMENT ON COLUMN member_setting.created_by IS '생성자 회원 ID (외래키)';
COMMENT ON COLUMN member_setting.updated_at IS '수정일시 (UTC, 자동 업데이트)';
COMMENT ON COLUMN member_setting.updated_by IS '수정자 회원 ID (외래키)';

-- ============================================================================
-- 14. 기본 데이터 삽입
-- ============================================================================

-- 12단계 생애주기 기본 데이터
--
-- INSERT INTO lifecycle (stage_number, name, description, min_age, max_age, color_code, icon_name) VALUES
-- (1, '영아기', '0-2세 영아들을 위한 생애주기', 0, 2, '#FFB6C1', 'baby'),
-- (2, '유아기', '3-5세 유아들을 위한 생애주기', 3, 5, '#FFA07A', 'child'),
-- (3, '아동기', '6-8세 아동들을 위한 생애주기', 6, 8, '#98FB98', 'smile'),
-- (4, '초등기', '9-12세 초등학생들을 위한 생애주기', 9, 12, '#87CEEB', 'book'),
-- (5, '소년기', '13-15세 소년들을 위한 생애주기', 13, 15, '#DDA0DD', 'members'),
-- (6, '청소년기', '16-18세 청소년들을 위한 생애주기', 16, 18, '#F0E68C', 'star'),
-- (7, '청년기', '19-29세 청년들을 위한 생애주기', 19, 29, '#20B2AA', 'heart'),
-- (8, '성인기', '30-49세 성인들을 위한 생애주기', 30, 49, '#CD853F', 'briefcase'),
-- (9, '중년기', '50-64세 중년들을 위한 생애주기', 50, 64, '#4682B4', 'home'),
-- (10, '준고령기', '65-74세 준고령자들을 위한 생애주기', 65, 74, '#9370DB', 'leaf'),
-- (11, '고령기', '75-84세 고령자들을 위한 생애주기', 75, 84, '#BC8F8F', 'tree'),
-- (12, '초고령기', '85세 이상 초고령자들을 위한 생애주기', 85, NULL, '#778899', 'crown');