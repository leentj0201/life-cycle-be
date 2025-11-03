package com.twothree.lifecycle.dto.memberfamily;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "가족 관계 삭제 요청 DTO")
public class MemberFamilyDeleteDto {
    
    @Schema(description = "가족 관계 ID", required = true)
    @NotNull(message = "가족 관계 ID는 필수입니다")
    private UUID memberFamilyId;
}

