package com.twothree.lifecycle.dto.memberlifecycle;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "회원-생애주기 매핑 조회 요청 DTO")
public class MemberLifecycleGetDto {
    
    @Schema(description = "회원-생애주기 매핑 ID", required = true)
    @NotNull(message = "회원-생애주기 매핑 ID는 필수입니다")
    private UUID memberLifecycleId;
}

