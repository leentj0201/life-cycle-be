package com.twothree.lifecycle.dto.lifecycle;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "생애주기 조회 요청 DTO")
public class LifecycleGetDto {
    
    @Schema(description = "생애주기 ID", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
    @NotNull(message = "생애주기 ID는 필수입니다")
    private UUID lifecycleId;
}

