package com.twothree.lifecycle.dto.memberfamily;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "가족 관계 수정 요청 DTO")
public class MemberFamilyUpdateDto {
    
    @Schema(description = "가족 관계 ID", required = true)
    @NotNull(message = "가족 관계 ID는 필수입니다")
    private UUID memberFamilyId;
    
    @Schema(description = "관계 유형", example = "부모")
    @Size(max = 50)
    private String relationType;
}

