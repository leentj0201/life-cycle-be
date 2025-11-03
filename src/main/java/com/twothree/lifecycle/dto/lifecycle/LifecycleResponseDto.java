package com.twothree.lifecycle.dto.lifecycle;

import com.twothree.lifecycle.entity.Lifecycle;
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
@Schema(description = "생애주기 응답 DTO")
public class LifecycleResponseDto {
    
    @Schema(description = "생애주기 ID")
    private UUID lifecycleId;
    
    @Schema(description = "교회 ID")
    private UUID churchId;
    
    @Schema(description = "생애주기 이름")
    private String name;
    
    @Schema(description = "생애주기 설명")
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
    
    public static LifecycleResponseDto from(Lifecycle lifecycle) {
        return LifecycleResponseDto.builder()
                .lifecycleId(lifecycle.getId())
                .churchId(lifecycle.getChurchId())
                .name(lifecycle.getName())
                .description(lifecycle.getDescription())
                .isActive(lifecycle.getIsActive())
                .createdAt(lifecycle.getCreatedAt())
                .createdBy(lifecycle.getCreatedBy())
                .updatedAt(lifecycle.getUpdatedAt())
                .updatedBy(lifecycle.getUpdatedBy())
                .build();
    }
}

