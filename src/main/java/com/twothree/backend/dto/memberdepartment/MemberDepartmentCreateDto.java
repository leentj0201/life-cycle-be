package com.twothree.backend.dto.memberdepartment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "회원-부서 매핑 생성 요청 DTO")
public class MemberDepartmentCreateDto {
    
    @Schema(description = "회원 ID", required = true)
    @NotNull(message = "회원 ID는 필수입니다")
    private UUID memberId;
    
    @Schema(description = "부서 ID", required = true)
    @NotNull(message = "부서 ID는 필수입니다")
    private UUID departmentId;
}

