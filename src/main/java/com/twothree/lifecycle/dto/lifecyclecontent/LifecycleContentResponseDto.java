package com.twothree.lifecycle.dto.lifecyclecontent;

import com.twothree.lifecycle.entity.LifecycleContent;
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
@Schema(description = "생애주기 콘텐츠 응답 DTO")
public class LifecycleContentResponseDto {
    
    @Schema(description = "생애주기 콘텐츠 ID")
    private UUID lifecycleContentId;
    
    @Schema(description = "교회 ID")
    private UUID churchId;
    
    @Schema(description = "생애주기 ID")
    private UUID lifecycleId;
    
    @Schema(description = "콘텐츠 유형")
    private String lifecycleContentType;
    
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
    
    public static LifecycleContentResponseDto from(LifecycleContent content) {
        return LifecycleContentResponseDto.builder()
                .lifecycleContentId(content.getId())
                .churchId(content.getChurchId())
                .lifecycleId(content.getLifecycleId())
                .lifecycleContentType(content.getLifecycleContentType())
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

