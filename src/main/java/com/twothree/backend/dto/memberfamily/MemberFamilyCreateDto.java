package com.twothree.backend.dto.memberfamily;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "가족 관계 생성 요청 DTO")
public class MemberFamilyCreateDto {
    
    @Schema(description = "회원 ID", required = true)
    @NotNull(message = "회원 ID는 필수입니다")
    private UUID memberId;
    
    @Schema(description = "가족 회원 ID", required = true)
    @NotNull(message = "가족 회원 ID는 필수입니다")
    private UUID familyMemberId;
    
    @Schema(description = "관계 유형", example = "부모", required = true)
    @NotBlank(message = "관계 유형은 필수입니다")
    @Size(max = 50)
    private String relationType;
}

