package com.twothree.backend.service;

import com.twothree.backend.dto.memberfamily.MemberFamilyCreateDto;
import com.twothree.backend.dto.memberfamily.MemberFamilyResponseDto;
import com.twothree.backend.entity.MemberFamily;
import com.twothree.backend.repository.MemberFamilyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemberFamilyService 테스트")
class MemberFamilyServiceTest {
    
    @Mock
    private MemberFamilyRepository memberFamilyRepository;
    
    @InjectMocks
    private MemberFamilyService memberFamilyService;
    
    private MemberFamily memberFamily;
    
    @BeforeEach
    void setUp() {
        memberFamily = new MemberFamily();
        memberFamily.setId(UUID.randomUUID());
        memberFamily.setMemberId(UUID.randomUUID());
        memberFamily.setFamilyMemberId(UUID.randomUUID());
        memberFamily.setRelationType("부모");
    }
    
    @Test
    @DisplayName("가족 관계 생성 성공")
    void createMemberFamily_Success() {
        // given
        MemberFamilyCreateDto dto = new MemberFamilyCreateDto();
        dto.setMemberId(UUID.randomUUID());
        dto.setFamilyMemberId(UUID.randomUUID());
        dto.setRelationType("부모");
        
        when(memberFamilyRepository.save(any(MemberFamily.class))).thenReturn(memberFamily);
        
        // when
        MemberFamilyResponseDto result = memberFamilyService.createMemberFamily(dto);
        
        // then
        assertThat(result).isNotNull();
        verify(memberFamilyRepository, times(1)).save(any(MemberFamily.class));
    }
    
    @Test
    @DisplayName("회원별 가족 관계 목록 조회")
    void listByMember() {
        // given
        UUID memberId = UUID.randomUUID();
        when(memberFamilyRepository.findByMemberId(memberId))
                .thenReturn(Arrays.asList(memberFamily));
        
        // when
        var result = memberFamilyService.listMemberFamiliesByMember(memberId);
        
        // then
        assertThat(result).hasSize(1);
    }
}

