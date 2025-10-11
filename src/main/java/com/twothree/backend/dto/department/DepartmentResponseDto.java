package com.twothree.backend.dto.department;

import com.twothree.backend.entity.Department;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "부서 응답 DTO")
public class DepartmentResponseDto {
    
    @Schema(description = "부서 ID")
    private UUID departmentId;
    
    @Schema(description = "교회 ID")
    private UUID churchId;
    
    @Schema(description = "부서 이름")
    private String name;
    
    @Schema(description = "부서 설명")
    private String description;
    
    @Schema(description = "활성화 여부")
    private Boolean isActive;
    
    @Schema(description = "생성일시")
    private LocalDateTime createdAt;
    
    @Schema(description = "생성자 ID")
    private UUID createdBy;
    
    @Schema(description = "수정일시")
    private LocalDateTime updatedAt;
    
    @Schema(description = "수정자 ID")
    private UUID updatedBy;
    
    public static DepartmentResponseDto from(Department department) {
        return DepartmentResponseDto.builder()
                .departmentId(department.getId())
                .churchId(department.getChurchId())
                .name(department.getName())
                .description(department.getDescription())
                .isActive(department.getIsActive())
                .createdAt(department.getCreatedAt())
                .createdBy(department.getCreatedBy())
                .updatedAt(department.getUpdatedAt())
                .updatedBy(department.getUpdatedBy())
                .build();
    }
}

