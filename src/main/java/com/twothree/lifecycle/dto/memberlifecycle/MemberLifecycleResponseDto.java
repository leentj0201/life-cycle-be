package com.twothree.lifecycle.dto.memberlifecycle;

import com.twothree.lifecycle.entity.MemberLifecycle;
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
@Schema(description = "회원-생애주기 매핑 응답 DTO")
public class MemberLifecycleResponseDto {
    
    @Schema(description = "회원-생애주기 매핑 ID")
    private UUID memberLifecycleId;
    
    @Schema(description = "회원 ID")
    private UUID memberId;
    
    @Schema(description = "생애주기 ID")
    private UUID lifecycleId;
    
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
    
    public static MemberLifecycleResponseDto from(MemberLifecycle ml) {
        return MemberLifecycleResponseDto.builder()
                .memberLifecycleId(ml.getId())
                .memberId(ml.getMemberId())
                .lifecycleId(ml.getLifecycleId())
                .isActive(ml.getIsActive())
                .createdAt(ml.getCreatedAt())
                .createdBy(ml.getCreatedBy())
                .updatedAt(ml.getUpdatedAt())
                .updatedBy(ml.getUpdatedBy())
                .build();
    }
}

