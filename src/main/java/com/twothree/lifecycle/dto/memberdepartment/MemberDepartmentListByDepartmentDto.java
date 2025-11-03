package com.twothree.lifecycle.dto.memberdepartment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "부서별 회원 목록 조회 요청 DTO")
public class MemberDepartmentListByDepartmentDto {
    
    @Schema(description = "부서 ID", required = true)
    @NotNull(message = "부서 ID는 필수입니다")
    private UUID departmentId;
}

