package com.twothree.backend.dto.memberfamily;

import com.twothree.backend.entity.MemberFamily;
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
@Schema(description = "가족 관계 응답 DTO")
public class MemberFamilyResponseDto {
    
    @Schema(description = "가족 관계 ID")
    private UUID memberFamilyId;
    
    @Schema(description = "회원 ID")
    private UUID memberId;
    
    @Schema(description = "가족 회원 ID")
    private UUID familyMemberId;
    
    @Schema(description = "관계 유형")
    private String relationType;
    
    @Schema(description = "생성일시")
    private LocalDateTime createdAt;
    
    @Schema(description = "생성자 ID")
    private UUID createdBy;
    
    @Schema(description = "수정일시")
    private LocalDateTime updatedAt;
    
    @Schema(description = "수정자 ID")
    private UUID updatedBy;
    
    public static MemberFamilyResponseDto from(MemberFamily mf) {
        return MemberFamilyResponseDto.builder()
                .memberFamilyId(mf.getId())
                .memberId(mf.getMemberId())
                .familyMemberId(mf.getFamilyMemberId())
                .relationType(mf.getRelationType())
                .createdAt(mf.getCreatedAt())
                .createdBy(mf.getCreatedBy())
                .updatedAt(mf.getUpdatedAt())
                .updatedBy(mf.getUpdatedBy())
                .build();
    }
}

