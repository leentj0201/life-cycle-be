package com.twothree.lifecycle.dto.department;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "부서 수정 요청 DTO (null 값은 기존 값 유지)")
public class DepartmentUpdateDto {
    
    @Schema(description = "부서 ID", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
    @NotNull(message = "부서 ID는 필수입니다")
    private UUID departmentId;
    
    @Schema(description = "부서 이름", example = "청년부")
    @Size(max = 100, message = "부서 이름은 100자를 초과할 수 없습니다")
    private String name;
    
    @Schema(description = "부서 설명", example = "청년들의 신앙 성장을 돕는 부서")
    private String description;
    
    @Schema(description = "활성화 여부", example = "true")
    private Boolean isActive;
}

