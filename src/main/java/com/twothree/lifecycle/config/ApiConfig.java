package com.twothree.lifecycle.config;

import com.twothree.lifecycle.constant.ApiEndpoints;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * API 설정 클래스
 */
@Configuration
public class ApiConfig {
    
    @Value("${api.base-url:http://localhost:8080}")
    private String baseUrl;
    
    @Value("${api.version:v1}")
    private String version;
    
    @Value("${server.port:8080}")
    private int port;
    
    /**
     * API Base URL
     */
    public String getBaseUrl() {
        return baseUrl;
    }
    
    /**
     * API 버전
     */
    public String getVersion() {
        return version;
    }
    
    /**
     * 서버 포트
     */
    public int getPort() {
        return port;
    }
    
    /**
     * Swagger UI URL
     */
    public String getSwaggerUrl() {
        return baseUrl + ApiEndpoints.Swagger.UI_INDEX;
    }
    
    /**
     * API Docs URL
     */
    public String getApiDocsUrl() {
        return baseUrl + ApiEndpoints.Swagger.API_DOCS;
    }
    
    /**
     * Church API Base URL
     */
    public String getChurchApiBaseUrl() {
        return baseUrl + ApiEndpoints.Church.BASE;
    }
}

