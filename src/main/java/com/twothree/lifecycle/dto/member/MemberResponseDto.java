package com.twothree.lifecycle.dto.member;

import com.twothree.lifecycle.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회원 응답 DTO")
public class MemberResponseDto {
    
    @Schema(description = "회원 ID")
    private UUID memberId;
    
    @Schema(description = "교회 ID")
    private UUID churchId;
    
    @Schema(description = "로그인 ID")
    private String loginId;
    
    @Schema(description = "이름")
    private String name;
    
    @Schema(description = "전화번호")
    private String phone;
    
    @Schema(description = "생년월일")
    private LocalDate birth;
    
    @Schema(description = "회원 역할")
    private String memberRole;
    
    @Schema(description = "활성화 여부")
    private Boolean isActive;
    
    @Schema(description = "마지막 로그인 일시")
    private OffsetDateTime lastLoginAt;
    
    @Schema(description = "생성일시")
    private LocalDateTime createdAt;
    
    @Schema(description = "생성자 ID")
    private UUID createdBy;
    
    @Schema(description = "수정일시")
    private LocalDateTime updatedAt;
    
    @Schema(description = "수정자 ID")
    private UUID updatedBy;
    
    public static MemberResponseDto from(Member member) {
        return MemberResponseDto.builder()
                .memberId(member.getId())
                .churchId(member.getChurchId())
                .loginId(member.getLoginId())
                .name(member.getName())
                .phone(member.getPhone())
                .birth(member.getBirth())
                .memberRole(member.getMemberRole())
                .isActive(member.getIsActive())
                .lastLoginAt(member.getLastLoginAt())
                .createdAt(member.getCreatedAt())
                .createdBy(member.getCreatedBy())
                .updatedAt(member.getUpdatedAt())
                .updatedBy(member.getUpdatedBy())
                .build();
    }
}

