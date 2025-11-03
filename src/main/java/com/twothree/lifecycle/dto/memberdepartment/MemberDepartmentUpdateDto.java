package com.twothree.lifecycle.dto.memberdepartment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "회원-부서 매핑 수정 요청 DTO")
public class MemberDepartmentUpdateDto {
    
    @Schema(description = "회원-부서 매핑 ID", required = true)
    @NotNull(message = "회원-부서 매핑 ID는 필수입니다")
    private UUID memberDepartmentId;
    
    @Schema(description = "활성화 여부", example = "true")
    private Boolean isActive;
}

