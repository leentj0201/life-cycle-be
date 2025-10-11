package com.twothree.backend.dto.lifecycle;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "생애주기 수정 요청 DTO (null 값은 기존 값 유지)")
public class LifecycleUpdateDto {
    
    @Schema(description = "생애주기 ID", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
    @NotNull(message = "생애주기 ID는 필수입니다")
    private UUID lifecycleId;
    
    @Schema(description = "생애주기 이름", example = "유아세례")
    @Size(max = 100, message = "생애주기 이름은 100자를 초과할 수 없습니다")
    private String name;
    
    @Schema(description = "생애주기 설명", example = "유아기 세례에 대한 생애주기")
    private String description;
    
    @Schema(description = "활성화 여부", example = "true")
    private Boolean isActive;
}

