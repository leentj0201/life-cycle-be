package com.twothree.lifecycle.dto.departmentcontent;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "부서 콘텐츠 생성 요청 DTO")
public class DepartmentContentCreateDto {
    
    @Schema(description = "교회 ID", required = true)
    @NotNull(message = "교회 ID는 필수입니다")
    private UUID churchId;
    
    @Schema(description = "부서 ID", required = true)
    @NotNull(message = "부서 ID는 필수입니다")
    private UUID departmentId;
    
    @Schema(description = "콘텐츠 유형", example = "공지사항", required = true)
    @NotBlank(message = "콘텐츠 유형은 필수입니다")
    @Size(max = 50)
    private String departmentContentType;
    
    @Schema(description = "제목", example = "11월 부서 모임 공지", required = true)
    @NotBlank(message = "제목은 필수입니다")
    @Size(max = 200)
    private String title;
    
    @Schema(description = "내용", example = "11월 부서 모임은 매주 금요일 7시에 진행됩니다.")
    private String content;
}

