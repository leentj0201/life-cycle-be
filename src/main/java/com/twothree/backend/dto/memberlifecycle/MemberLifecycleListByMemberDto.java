package com.twothree.backend.dto.memberlifecycle;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "회원별 생애주기 목록 조회 요청 DTO")
public class MemberLifecycleListByMemberDto {
    
    @Schema(description = "회원 ID", required = true)
    @NotNull(message = "회원 ID는 필수입니다")
    private UUID memberId;
}

