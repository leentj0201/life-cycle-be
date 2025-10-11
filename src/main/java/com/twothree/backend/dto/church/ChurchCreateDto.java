package com.twothree.backend.dto.church;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "교회 생성 요청 DTO")
public class ChurchCreateDto {
    
    @Schema(description = "교회 이름", example = "사랑의교회", required = true)
    @NotBlank(message = "교회 이름은 필수입니다")
    @Size(max = 100, message = "교회 이름은 100자를 초과할 수 없습니다")
    private String name;
    
    @Schema(description = "교회 주소", example = "서울시 강남구 테헤란로 123")
    private String address;
    
    @Schema(description = "전화번호", example = "02-1234-5678")
    @Size(max = 20, message = "전화번호는 20자를 초과할 수 없습니다")
    private String phone;
    
    @Schema(description = "이메일", example = "contact@church.com")
    @Email(message = "올바른 이메일 형식이 아닙니다")
    @Size(max = 100, message = "이메일은 100자를 초과할 수 없습니다")
    private String email;
    
    @Schema(description = "웹사이트", example = "https://www.church.com")
    @Size(max = 200, message = "웹사이트는 200자를 초과할 수 없습니다")
    private String website;
    
    @Schema(description = "담임목사 이름", example = "김목사")
    @Size(max = 50, message = "담임목사 이름은 50자를 초과할 수 없습니다")
    private String pastorName;
    
    @Schema(description = "교회 설명", example = "주님의 사랑을 전하는 교회")
    private String description;
}

