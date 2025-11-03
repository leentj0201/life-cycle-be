package com.twothree.lifecycle.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "회원 삭제 요청 DTO")
public class MemberDeleteDto {
    
    @Schema(description = "회원 ID", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
    @NotNull(message = "회원 ID는 필수입니다")
    private UUID memberId;
}

