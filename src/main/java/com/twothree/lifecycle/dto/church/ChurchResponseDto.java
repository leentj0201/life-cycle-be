package com.twothree.lifecycle.dto.church;

import com.twothree.lifecycle.entity.Church;
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
@Schema(description = "교회 응답 DTO")
public class ChurchResponseDto {
    
    @Schema(description = "교회 ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID churchId;
    
    @Schema(description = "교회 이름", example = "사랑의교회")
    private String name;
    
    @Schema(description = "교회 주소", example = "서울시 강남구 테헤란로 123")
    private String address;
    
    @Schema(description = "전화번호", example = "02-1234-5678")
    private String phone;
    
    @Schema(description = "이메일", example = "contact@church.com")
    private String email;
    
    @Schema(description = "웹사이트", example = "https://www.church.com")
    private String website;
    
    @Schema(description = "담임목사 이름", example = "김목사")
    private String pastorName;
    
    @Schema(description = "교회 설명", example = "주님의 사랑을 전하는 교회")
    private String description;
    
    @Schema(description = "활성화 여부", example = "true")
    private Boolean isActive;
    
    @Schema(description = "생성일시")
    private LocalDateTime createdAt;
    
    @Schema(description = "생성자 ID")
    private UUID createdBy;
    
    @Schema(description = "수정일시")
    private LocalDateTime updatedAt;
    
    @Schema(description = "수정자 ID")
    private UUID updatedBy;
    
    public static ChurchResponseDto from(Church church) {
        return ChurchResponseDto.builder()
                .churchId(church.getId())
                .name(church.getName())
                .address(church.getAddress())
                .phone(church.getPhone())
                .email(church.getEmail())
                .website(church.getWebsite())
                .pastorName(church.getPastorName())
                .description(church.getDescription())
                .isActive(church.getIsActive())
                .createdAt(church.getCreatedAt())
                .createdBy(church.getCreatedBy())
                .updatedAt(church.getUpdatedAt())
                .updatedBy(church.getUpdatedBy())
                .build();
    }
}

