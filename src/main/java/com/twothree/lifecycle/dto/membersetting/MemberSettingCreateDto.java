package com.twothree.lifecycle.dto.membersetting;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "회원 설정 생성 요청 DTO")
public class MemberSettingCreateDto {
    
    @Schema(description = "회원 ID", required = true)
    @NotNull(message = "회원 ID는 필수입니다")
    private UUID memberId;
    
    @Schema(description = "알림 수신 여부", example = "true")
    private Boolean isNotification;
}

