package com.twothree.backend.dto.departmentcontent;

import com.twothree.backend.entity.DepartmentContent;
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
@Schema(description = "부서 콘텐츠 응답 DTO")
public class DepartmentContentResponseDto {
    
    @Schema(description = "부서 콘텐츠 ID")
    private UUID departmentContentId;
    
    @Schema(description = "교회 ID")
    private UUID churchId;
    
    @Schema(description = "부서 ID")
    private UUID departmentId;
    
    @Schema(description = "콘텐츠 유형")
    private String departmentContentType;
    
    @Schema(description = "제목")
    private String title;
    
    @Schema(description = "내용")
    private String content;
    
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
    
    public static DepartmentContentResponseDto from(DepartmentContent content) {
        return DepartmentContentResponseDto.builder()
                .departmentContentId(content.getId())
                .churchId(content.getChurchId())
                .departmentId(content.getDepartmentId())
                .departmentContentType(content.getDepartmentContentType())
                .title(content.getTitle())
                .content(content.getContent())
                .isActive(content.getIsActive())
                .createdAt(content.getCreatedAt())
                .createdBy(content.getCreatedBy())
                .updatedAt(content.getUpdatedAt())
                .updatedBy(content.getUpdatedBy())
                .build();
    }
}

