package com.twothree.lifecycle.dto.membersetting;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "회원 설정 조회 요청 DTO")
public class MemberSettingGetDto {
    
    @Schema(description = "회원 설정 ID", required = true)
    @NotNull(message = "회원 설정 ID는 필수입니다")
    private UUID memberSettingId;
}

