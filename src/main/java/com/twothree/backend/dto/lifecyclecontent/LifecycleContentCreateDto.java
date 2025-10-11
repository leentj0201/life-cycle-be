package com.twothree.backend.dto.lifecyclecontent;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "생애주기 콘텐츠 생성 요청 DTO")
public class LifecycleContentCreateDto {
    
    @Schema(description = "교회 ID", required = true)
    @NotNull(message = "교회 ID는 필수입니다")
    private UUID churchId;
    
    @Schema(description = "생애주기 ID", required = true)
    @NotNull(message = "생애주기 ID는 필수입니다")
    private UUID lifecycleId;
    
    @Schema(description = "콘텐츠 유형", example = "가이드", required = true)
    @NotBlank(message = "콘텐츠 유형은 필수입니다")
    @Size(max = 50)
    private String lifecycleContentType;
    
    @Schema(description = "제목", example = "유아세례 준비 사항", required = true)
    @NotBlank(message = "제목은 필수입니다")
    @Size(max = 200)
    private String title;
    
    @Schema(description = "내용", example = "유아세례를 위한 준비 절차...")
    private String content;
}

