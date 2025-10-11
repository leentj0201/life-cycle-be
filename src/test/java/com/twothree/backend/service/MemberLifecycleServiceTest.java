package com.twothree.backend.service;

import com.twothree.backend.dto.memberlifecycle.MemberLifecycleCreateDto;
import com.twothree.backend.dto.memberlifecycle.MemberLifecycleResponseDto;
import com.twothree.backend.entity.MemberLifecycle;
import com.twothree.backend.repository.MemberLifecycleRepository;
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
@DisplayName("MemberLifecycleService 테스트")
class MemberLifecycleServiceTest {
    
    @Mock
    private MemberLifecycleRepository memberLifecycleRepository;
    
    @InjectMocks
    private MemberLifecycleService memberLifecycleService;
    
    private MemberLifecycle memberLifecycle;
    
    @BeforeEach
    void setUp() {
        memberLifecycle = new MemberLifecycle();
        memberLifecycle.setId(UUID.randomUUID());
        memberLifecycle.setMemberId(UUID.randomUUID());
        memberLifecycle.setLifecycleId(UUID.randomUUID());
        memberLifecycle.setIsActive(true);
    }
    
    @Test
    @DisplayName("회원-생애주기 매핑 생성 성공")
    void createMemberLifecycle_Success() {
        // given
        MemberLifecycleCreateDto dto = new MemberLifecycleCreateDto();
        dto.setMemberId(UUID.randomUUID());
        dto.setLifecycleId(UUID.randomUUID());
        
        when(memberLifecycleRepository.save(any(MemberLifecycle.class))).thenReturn(memberLifecycle);
        
        // when
        MemberLifecycleResponseDto result = memberLifecycleService.createMemberLifecycle(dto);
        
        // then
        assertThat(result).isNotNull();
        verify(memberLifecycleRepository, times(1)).save(any(MemberLifecycle.class));
    }
    
    @Test
    @DisplayName("회원별 생애주기 목록 조회")
    void listByMember() {
        // given
        UUID memberId = UUID.randomUUID();
        when(memberLifecycleRepository.findByMemberIdAndIsActiveTrue(memberId))
                .thenReturn(Arrays.asList(memberLifecycle));
        
        // when
        var result = memberLifecycleService.listMemberLifecyclesByMember(memberId);
        
        // then
        assertThat(result).hasSize(1);
    }
}

