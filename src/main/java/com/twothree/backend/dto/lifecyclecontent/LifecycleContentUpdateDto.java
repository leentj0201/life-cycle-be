package com.twothree.backend.dto.lifecyclecontent;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "생애주기 콘텐츠 수정 요청 DTO")
public class LifecycleContentUpdateDto {
    
    @Schema(description = "생애주기 콘텐츠 ID", required = true)
    @NotNull(message = "생애주기 콘텐츠 ID는 필수입니다")
    private UUID lifecycleContentId;
    
    @Schema(description = "콘텐츠 유형")
    @Size(max = 50)
    private String lifecycleContentType;
    
    @Schema(description = "제목")
    @Size(max = 200)
    private String title;
    
    @Schema(description = "내용")
    private String content;
    
    @Schema(description = "활성화 여부")
    private Boolean isActive;
}

