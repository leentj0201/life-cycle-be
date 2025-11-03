package com.twothree.lifecycle.service;

import com.twothree.lifecycle.dto.membersetting.MemberSettingCreateDto;
import com.twothree.lifecycle.dto.membersetting.MemberSettingResponseDto;
import com.twothree.lifecycle.dto.membersetting.MemberSettingUpdateDto;
import com.twothree.lifecycle.entity.MemberSetting;
import com.twothree.lifecycle.repository.MemberSettingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemberSettingService 테스트")
class MemberSettingServiceTest {
    
    @Mock
    private MemberSettingRepository memberSettingRepository;
    
    @InjectMocks
    private MemberSettingService memberSettingService;
    
    private MemberSetting memberSetting;
    
    @BeforeEach
    void setUp() {
        memberSetting = new MemberSetting();
        memberSetting.setId(UUID.randomUUID());
        memberSetting.setMemberId(UUID.randomUUID());
        memberSetting.setIsNotification(true);
    }
    
    @Test
    @DisplayName("회원 설정 생성 성공")
    void createMemberSetting_Success() {
        // given
        MemberSettingCreateDto dto = new MemberSettingCreateDto();
        dto.setMemberId(UUID.randomUUID());
        dto.setIsNotification(true);
        
        when(memberSettingRepository.save(any(MemberSetting.class))).thenReturn(memberSetting);
        
        // when
        MemberSettingResponseDto result = memberSettingService.createMemberSetting(dto);
        
        // then
        assertThat(result).isNotNull();
        verify(memberSettingRepository, times(1)).save(any(MemberSetting.class));
    }
    
    @Test
    @DisplayName("회원별 설정 조회")
    void getMemberSettingByMember() {
        // given
        UUID memberId = UUID.randomUUID();
        when(memberSettingRepository.findByMemberId(memberId)).thenReturn(Optional.of(memberSetting));
        
        // when
        MemberSettingResponseDto result = memberSettingService.getMemberSettingByMember(memberId);
        
        // then
        assertThat(result).isNotNull();
    }
    
    @Test
    @DisplayName("회원 설정 수정 성공")
    void updateMemberSetting_Success() {
        // given
        MemberSettingUpdateDto dto = new MemberSettingUpdateDto();
        dto.setMemberSettingId(memberSetting.getId());
        dto.setIsNotification(false);
        
        when(memberSettingRepository.findById(any(UUID.class))).thenReturn(Optional.of(memberSetting));
        when(memberSettingRepository.save(any(MemberSetting.class))).thenReturn(memberSetting);
        
        // when
        MemberSettingResponseDto result = memberSettingService.updateMemberSetting(dto);
        
        // then
        assertThat(result).isNotNull();
        verify(memberSettingRepository, times(1)).save(memberSetting);
    }
}

