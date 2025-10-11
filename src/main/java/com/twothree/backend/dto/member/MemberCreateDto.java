package com.twothree.backend.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Schema(description = "회원 생성 요청 DTO")
public class MemberCreateDto {
    
    @Schema(description = "교회 ID", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
    @NotNull(message = "교회 ID는 필수입니다")
    private UUID churchId;
    
    @Schema(description = "로그인 ID", example = "user123", required = true)
    @NotBlank(message = "로그인 ID는 필수입니다")
    @Size(max = 255, message = "로그인 ID는 255자를 초과할 수 없습니다")
    private String loginId;
    
    @Schema(description = "비밀번호", example = "password123!", required = true)
    @NotBlank(message = "비밀번호는 필수입니다")
    @Size(max = 255, message = "비밀번호는 255자를 초과할 수 없습니다")
    private String password;
    
    @Schema(description = "이름", example = "홍길동", required = true)
    @NotBlank(message = "이름은 필수입니다")
    @Size(max = 50, message = "이름은 50자를 초과할 수 없습니다")
    private String name;
    
    @Schema(description = "전화번호", example = "010-1234-5678")
    @Size(max = 20, message = "전화번호는 20자를 초과할 수 없습니다")
    private String phone;
    
    @Schema(description = "생년월일", example = "1990-01-01")
    private LocalDate birth;
    
    @Schema(description = "회원 역할", example = "MEMBER")
    @Size(max = 50, message = "회원 역할은 50자를 초과할 수 없습니다")
    private String memberRole;
}

