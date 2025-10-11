package com.twothree.backend.dto.lifecyclecontent;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "생애주기 콘텐츠 조회 요청 DTO")
public class LifecycleContentGetDto {
    
    @Schema(description = "생애주기 콘텐츠 ID", required = true)
    @NotNull(message = "생애주기 콘텐츠 ID는 필수입니다")
    private UUID lifecycleContentId;
}

