package com.twothree.backend.dto.lifecyclecontent;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "생애주기별 콘텐츠 목록 조회 요청 DTO")
public class LifecycleContentListByLifecycleDto {
    
    @Schema(description = "생애주기 ID", required = true)
    @NotNull(message = "생애주기 ID는 필수입니다")
    private UUID lifecycleId;
}

