package com.twothree.lifecycle.dto.departmentcontent;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "부서 콘텐츠 수정 요청 DTO")
public class DepartmentContentUpdateDto {
    
    @Schema(description = "부서 콘텐츠 ID", required = true)
    @NotNull(message = "부서 콘텐츠 ID는 필수입니다")
    private UUID departmentContentId;
    
    @Schema(description = "콘텐츠 유형", example = "공지사항")
    @Size(max = 50)
    private String departmentContentType;
    
    @Schema(description = "제목", example = "11월 부서 모임 공지")
    @Size(max = 200)
    private String title;
    
    @Schema(description = "내용")
    private String content;
    
    @Schema(description = "활성화 여부", example = "true")
    private Boolean isActive;
}

