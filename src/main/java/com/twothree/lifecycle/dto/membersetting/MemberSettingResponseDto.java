package com.twothree.lifecycle.dto.membersetting;

import com.twothree.lifecycle.entity.MemberSetting;
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
@Schema(description = "회원 설정 응답 DTO")
public class MemberSettingResponseDto {
    
    @Schema(description = "회원 설정 ID")
    private UUID memberSettingId;
    
    @Schema(description = "회원 ID")
    private UUID memberId;
    
    @Schema(description = "알림 수신 여부")
    private Boolean isNotification;
    
    @Schema(description = "생성일시")
    private LocalDateTime createdAt;
    
    @Schema(description = "생성자 ID")
    private UUID createdBy;
    
    @Schema(description = "수정일시")
    private LocalDateTime updatedAt;
    
    @Schema(description = "수정자 ID")
    private UUID updatedBy;
    
    public static MemberSettingResponseDto from(MemberSetting ms) {
        return MemberSettingResponseDto.builder()
                .memberSettingId(ms.getId())
                .memberId(ms.getMemberId())
                .isNotification(ms.getIsNotification())
                .createdAt(ms.getCreatedAt())
                .createdBy(ms.getCreatedBy())
                .updatedAt(ms.getUpdatedAt())
                .updatedBy(ms.getUpdatedBy())
                .build();
    }
}

