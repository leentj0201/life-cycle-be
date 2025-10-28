package com.twothree.backend.config;

import com.twothree.backend.constant.ApiEndpoints;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger (OpenAPI) 설정
 */
@Configuration
public class SwaggerConfig {
    
    @Value("${api.base-url:http://localhost:8080}")
    private String baseUrl;
    
    @Value("${api.version:v1}")
    private String apiVersion;
    
    @Bean
    public OpenAPI openAPI() {
        Server localServer = new Server();
        localServer.setUrl(ApiEndpoints.Server.LOCAL);
        localServer.setDescription("로컬 개발 서버 (Local + INT-DB)");

        Server intServer = new Server();
        intServer.setUrl(ApiEndpoints.Server.INT);
        intServer.setDescription("통합 테스트 서버 (INT + INT-DB)");

        Server prodServer = new Server();
        prodServer.setUrl(ApiEndpoints.Server.PRODUCTION);
        prodServer.setDescription("운영 서버 (PROD + PROD-DB)");
        
        Contact contact = new Contact();
        contact.setName("TwoThree Team");
        contact.setEmail("support@twothree.com");
        
        License license = new License();
        license.setName("Apache 2.0");
        license.setUrl("https://www.apache.org/licenses/LICENSE-2.0");
        
        Info info = new Info()
                .title("생애주기 관리 시스템 API")
                .version(apiVersion)
                    .description("교회 생애주기 관리 시스템의 REST API 문서입니다.\n\n" +
                            "## 환경별 서버\n" +
                            "- **Local**: " + ApiEndpoints.Server.LOCAL + " (개발환경 + INT-DB)\n" +
                            "- **INT**: " + ApiEndpoints.Server.INT + " (테스트환경 + INT-DB)\n" +
                            "- **PROD**: " + ApiEndpoints.Server.PRODUCTION + " (운영환경 + PROD-DB)\n\n" +
                            "## 주요 API 엔드포인트\n" +
                            "- Church API: `" + ApiEndpoints.Church.BASE + "`\n" +
                            "- Member API: `" + ApiEndpoints.Member.BASE + "`\n" +
                            "- Department API: `" + ApiEndpoints.Department.BASE + "`\n" +
                            "- Lifecycle API: `" + ApiEndpoints.Lifecycle.BASE + "`\n\n" +
                            "## 문서 링크\n" +
                            "- Swagger UI: `" + ApiEndpoints.Swagger.UI_INDEX + "`\n" +
                            "- API Docs JSON: `" + ApiEndpoints.Swagger.API_DOCS + "`")
                .contact(contact)
                .license(license);
        
            return new OpenAPI()
                    .info(info)
                    .servers(List.of(localServer, intServer, prodServer));
    }
}

