package com.twothree.lifecycle.service;

import com.twothree.lifecycle.dto.member.MemberCreateDto;
import com.twothree.lifecycle.dto.member.MemberResponseDto;
import com.twothree.lifecycle.dto.member.MemberUpdateDto;
import com.twothree.lifecycle.entity.Member;
import com.twothree.lifecycle.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemberService 테스트")
class MemberServiceTest {
    
    @Mock
    private MemberRepository memberRepository;
    
    @InjectMocks
    private MemberService memberService;
    
    private Member member;
    private UUID memberId;
    private UUID churchId;
    
    @BeforeEach
    void setUp() {
        memberId = UUID.randomUUID();
        churchId = UUID.randomUUID();
        
        member = new Member();
        member.setId(memberId);
        member.setChurchId(churchId);
        member.setLoginId("testuser");
        member.setPassword("password123");
        member.setName("테스트유저");
        member.setPhone("010-1234-5678");
        member.setBirth(LocalDate.of(1990, 1, 1));
        member.setMemberRole("MEMBER");
        member.setIsActive(true);
    }
    
    @Test
    @DisplayName("회원 생성 성공")
    void createMember_Success() {
        // given
        MemberCreateDto dto = new MemberCreateDto();
        dto.setChurchId(churchId);
        dto.setLoginId("newuser");
        dto.setPassword("password");
        dto.setName("새회원");
        
        when(memberRepository.existsByLoginId(anyString())).thenReturn(false);
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        
        // when
        MemberResponseDto result = memberService.createMember(dto);
        
        // then
        assertThat(result).isNotNull();
        verify(memberRepository, times(1)).existsByLoginId(dto.getLoginId());
        verify(memberRepository, times(1)).save(any(Member.class));
    }
    
    @Test
    @DisplayName("회원 생성 실패 - 로그인 ID 중복")
    void createMember_DuplicateLoginId() {
        // given
        MemberCreateDto dto = new MemberCreateDto();
        dto.setLoginId("duplicate");
        
        when(memberRepository.existsByLoginId(anyString())).thenReturn(true);
        
        // when & then
        assertThatThrownBy(() -> memberService.createMember(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 사용 중인 로그인 ID입니다");
        
        verify(memberRepository, never()).save(any(Member.class));
    }
    
    @Test
    @DisplayName("회원 조회 성공")
    void getMember_Success() {
        // given
        when(memberRepository.findByIdAndIsActiveTrue(memberId)).thenReturn(Optional.of(member));
        
        // when
        MemberResponseDto result = memberService.getMember(memberId);
        
        // then
        assertThat(result).isNotNull();
        assertThat(result.getMemberId()).isEqualTo(memberId);
        verify(memberRepository, times(1)).findByIdAndIsActiveTrue(memberId);
    }
    
    @Test
    @DisplayName("교회별 회원 목록 조회")
    void listMembersByChurch() {
        // given
        when(memberRepository.findByChurchIdAndIsActiveTrue(churchId))
                .thenReturn(Arrays.asList(member));
        
        // when
        List<MemberResponseDto> result = memberService.listMembersByChurch(churchId);
        
        // then
        assertThat(result).hasSize(1);
        verify(memberRepository, times(1)).findByChurchIdAndIsActiveTrue(churchId);
    }
    
    @Test
    @DisplayName("회원 정보 수정 성공")
    void updateMember_Success() {
        // given
        MemberUpdateDto dto = new MemberUpdateDto();
        dto.setMemberId(memberId);
        dto.setName("수정된이름");
        
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        
        // when
        MemberResponseDto result = memberService.updateMember(dto);
        
        // then
        assertThat(result).isNotNull();
        verify(memberRepository, times(1)).save(member);
    }
    
    @Test
    @DisplayName("회원 삭제 성공")
    void deleteMember_Success() {
        // given
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        
        // when
        memberService.deleteMember(memberId);
        
        // then
        assertThat(member.getIsActive()).isFalse();
        verify(memberRepository, times(1)).save(member);
    }
}

