package com.twothree.backend.constant;

/**
 * API 엔드포인트 상수 관리
 */
public final class ApiEndpoints {
    
    private ApiEndpoints() {
        // 인스턴스화 방지
    }
    
    // Base Paths
    public static final String API_BASE = "/api";
    
    /**
     * Church API 엔드포인트
     */
    public static final class Church {
        private Church() {}
        
        public static final String BASE = API_BASE + "/church";
        public static final String CREATE = "/create";
        public static final String GET = "/get";
        public static final String LIST = "/list";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        
        // Full paths
        public static final String FULL_CREATE = BASE + CREATE;
        public static final String FULL_GET = BASE + GET;
        public static final String FULL_LIST = BASE + LIST;
        public static final String FULL_UPDATE = BASE + UPDATE;
        public static final String FULL_DELETE = BASE + DELETE;
    }
    
    /**
     * Member API 엔드포인트
     */
    public static final class Member {
        private Member() {}
        
        public static final String BASE = API_BASE + "/member";
        public static final String CREATE = "/create";
        public static final String GET = "/get";
        public static final String LIST = "/list";
        public static final String LIST_BY_CHURCH = "/list-by-church";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        
        // Full paths
        public static final String FULL_CREATE = BASE + CREATE;
        public static final String FULL_GET = BASE + GET;
        public static final String FULL_LIST = BASE + LIST;
        public static final String FULL_LIST_BY_CHURCH = BASE + LIST_BY_CHURCH;
        public static final String FULL_UPDATE = BASE + UPDATE;
        public static final String FULL_DELETE = BASE + DELETE;
    }
    
    /**
     * Department API 엔드포인트
     */
    public static final class Department {
        private Department() {}
        
        public static final String BASE = API_BASE + "/department";
        public static final String CREATE = "/create";
        public static final String GET = "/get";
        public static final String LIST = "/list";
        public static final String LIST_BY_CHURCH = "/list-by-church";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        
        // Full paths
        public static final String FULL_CREATE = BASE + CREATE;
        public static final String FULL_GET = BASE + GET;
        public static final String FULL_LIST = BASE + LIST;
        public static final String FULL_LIST_BY_CHURCH = BASE + LIST_BY_CHURCH;
        public static final String FULL_UPDATE = BASE + UPDATE;
        public static final String FULL_DELETE = BASE + DELETE;
    }
    
    /**
     * Lifecycle API 엔드포인트
     */
    public static final class Lifecycle {
        private Lifecycle() {}
        
        public static final String BASE = API_BASE + "/lifecycle";
        public static final String CREATE = "/create";
        public static final String GET = "/get";
        public static final String LIST = "/list";
        public static final String LIST_BY_CHURCH = "/list-by-church";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        
        // Full paths
        public static final String FULL_CREATE = BASE + CREATE;
        public static final String FULL_GET = BASE + GET;
        public static final String FULL_LIST = BASE + LIST;
        public static final String FULL_LIST_BY_CHURCH = BASE + LIST_BY_CHURCH;
        public static final String FULL_UPDATE = BASE + UPDATE;
        public static final String FULL_DELETE = BASE + DELETE;
    }
    
    /**
     * DepartmentContent API 엔드포인트
     */
    public static final class DepartmentContent {
        private DepartmentContent() {}
        
        public static final String BASE = API_BASE + "/department-content";
        public static final String CREATE = "/create";
        public static final String GET = "/get";
        public static final String LIST = "/list";
        public static final String LIST_BY_DEPARTMENT = "/list-by-department";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        
        // Full paths
        public static final String FULL_CREATE = BASE + CREATE;
        public static final String FULL_GET = BASE + GET;
        public static final String FULL_LIST = BASE + LIST;
        public static final String FULL_LIST_BY_DEPARTMENT = BASE + LIST_BY_DEPARTMENT;
        public static final String FULL_UPDATE = BASE + UPDATE;
        public static final String FULL_DELETE = BASE + DELETE;
    }
    
    /**
     * LifecycleContent API 엔드포인트
     */
    public static final class LifecycleContent {
        private LifecycleContent() {}
        
        public static final String BASE = API_BASE + "/lifecycle-content";
        public static final String CREATE = "/create";
        public static final String GET = "/get";
        public static final String LIST = "/list";
        public static final String LIST_BY_LIFECYCLE = "/list-by-lifecycle";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        
        // Full paths
        public static final String FULL_CREATE = BASE + CREATE;
        public static final String FULL_GET = BASE + GET;
        public static final String FULL_LIST = BASE + LIST;
        public static final String FULL_LIST_BY_LIFECYCLE = BASE + LIST_BY_LIFECYCLE;
        public static final String FULL_UPDATE = BASE + UPDATE;
        public static final String FULL_DELETE = BASE + DELETE;
    }
    
    /**
     * MemberDepartment API 엔드포인트
     */
    public static final class MemberDepartment {
        private MemberDepartment() {}
        
        public static final String BASE = API_BASE + "/member-department";
        public static final String CREATE = "/create";
        public static final String GET = "/get";
        public static final String LIST = "/list";
        public static final String LIST_BY_MEMBER = "/list-by-member";
        public static final String LIST_BY_DEPARTMENT = "/list-by-department";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        
        // Full paths
        public static final String FULL_CREATE = BASE + CREATE;
        public static final String FULL_GET = BASE + GET;
        public static final String FULL_LIST = BASE + LIST;
        public static final String FULL_LIST_BY_MEMBER = BASE + LIST_BY_MEMBER;
        public static final String FULL_LIST_BY_DEPARTMENT = BASE + LIST_BY_DEPARTMENT;
        public static final String FULL_UPDATE = BASE + UPDATE;
        public static final String FULL_DELETE = BASE + DELETE;
    }
    
    /**
     * MemberLifecycle API 엔드포인트
     */
    public static final class MemberLifecycle {
        private MemberLifecycle() {}
        
        public static final String BASE = API_BASE + "/member-lifecycle";
        public static final String CREATE = "/create";
        public static final String GET = "/get";
        public static final String LIST = "/list";
        public static final String LIST_BY_MEMBER = "/list-by-member";
        public static final String LIST_BY_LIFECYCLE = "/list-by-lifecycle";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        
        // Full paths
        public static final String FULL_CREATE = BASE + CREATE;
        public static final String FULL_GET = BASE + GET;
        public static final String FULL_LIST = BASE + LIST;
        public static final String FULL_LIST_BY_MEMBER = BASE + LIST_BY_MEMBER;
        public static final String FULL_LIST_BY_LIFECYCLE = BASE + LIST_BY_LIFECYCLE;
        public static final String FULL_UPDATE = BASE + UPDATE;
        public static final String FULL_DELETE = BASE + DELETE;
    }
    
    /**
     * MemberFamily API 엔드포인트
     */
    public static final class MemberFamily {
        private MemberFamily() {}
        
        public static final String BASE = API_BASE + "/member-family";
        public static final String CREATE = "/create";
        public static final String GET = "/get";
        public static final String LIST = "/list";
        public static final String LIST_BY_MEMBER = "/list-by-member";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        
        // Full paths
        public static final String FULL_CREATE = BASE + CREATE;
        public static final String FULL_GET = BASE + GET;
        public static final String FULL_LIST = BASE + LIST;
        public static final String FULL_LIST_BY_MEMBER = BASE + LIST_BY_MEMBER;
        public static final String FULL_UPDATE = BASE + UPDATE;
        public static final String FULL_DELETE = BASE + DELETE;
    }
    
    /**
     * MemberSetting API 엔드포인트
     */
    public static final class MemberSetting {
        private MemberSetting() {}
        
        public static final String BASE = API_BASE + "/member-setting";
        public static final String CREATE = "/create";
        public static final String GET = "/get";
        public static final String GET_BY_MEMBER = "/get-by-member";
        public static final String LIST = "/list";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        
        // Full paths
        public static final String FULL_CREATE = BASE + CREATE;
        public static final String FULL_GET = BASE + GET;
        public static final String FULL_GET_BY_MEMBER = BASE + GET_BY_MEMBER;
        public static final String FULL_LIST = BASE + LIST;
        public static final String FULL_UPDATE = BASE + UPDATE;
        public static final String FULL_DELETE = BASE + DELETE;
    }
    
    /**
     * Swagger 엔드포인트
     */
    public static final class Swagger {
        private Swagger() {}
        
        public static final String UI = "/swagger-ui.html";
        public static final String UI_INDEX = "/swagger-ui/index.html";
        public static final String API_DOCS = "/v3/api-docs";
        public static final String API_DOCS_YAML = "/v3/api-docs.yaml";
    }
    
    /**
     * 서버 URL
     */
    public static final class Server {
        private Server() {}
        
        public static final String LOCAL = "http://localhost:8080";
        public static final String PRODUCTION = "http://ec2-54-180-152-24.ap-northeast-2.compute.amazonaws.com:8080";
        
        // Full URLs
        public static String getLocalSwaggerUrl() {
            return LOCAL + Swagger.UI_INDEX;
        }
        
        public static String getProductionSwaggerUrl() {
            return PRODUCTION + Swagger.UI_INDEX;
        }
        
        public static String getLocalApiDocsUrl() {
            return LOCAL + Swagger.API_DOCS;
        }
        
        public static String getProductionApiDocsUrl() {
            return PRODUCTION + Swagger.API_DOCS;
        }
    }
}

