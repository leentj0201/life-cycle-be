package com.twothree.lifecycle.dto.memberdepartment;

import com.twothree.lifecycle.entity.MemberDepartment;
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
@Schema(description = "회원-부서 매핑 응답 DTO")
public class MemberDepartmentResponseDto {
    
    @Schema(description = "회원-부서 매핑 ID")
    private UUID memberDepartmentId;
    
    @Schema(description = "회원 ID")
    private UUID memberId;
    
    @Schema(description = "부서 ID")
    private UUID departmentId;
    
    @Schema(description = "활성화 여부")
    private Boolean isActive;
    
    @Schema(description = "생성일시")
    private LocalDateTime createdAt;
    
    @Schema(description = "생성자 ID")
    private UUID createdBy;
    
    @Schema(description = "수정일시")
    private LocalDateTime updatedAt;
    
    @Schema(description = "수정자 ID")
    private UUID updatedBy;
    
    public static MemberDepartmentResponseDto from(MemberDepartment md) {
        return MemberDepartmentResponseDto.builder()
                .memberDepartmentId(md.getId())
                .memberId(md.getMemberId())
                .departmentId(md.getDepartmentId())
                .isActive(md.getIsActive())
                .createdAt(md.getCreatedAt())
                .createdBy(md.getCreatedBy())
                .updatedAt(md.getUpdatedAt())
                .updatedBy(md.getUpdatedBy())
                .build();
    }
}

