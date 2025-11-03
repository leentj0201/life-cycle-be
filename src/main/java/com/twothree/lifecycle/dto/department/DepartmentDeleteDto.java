package com.twothree.lifecycle.dto.department;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "부서 삭제 요청 DTO")
public class DepartmentDeleteDto {
    
    @Schema(description = "부서 ID", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
    @NotNull(message = "부서 ID는 필수입니다")
    private UUID departmentId;
}

