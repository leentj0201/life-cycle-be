package com.twothree.lifecycle.service;

import com.twothree.lifecycle.dto.member.MemberCreateDto;
import com.twothree.lifecycle.dto.member.MemberResponseDto;
import com.twothree.lifecycle.dto.member.MemberUpdateDto;
import com.twothree.lifecycle.entity.Member;
import com.twothree.lifecycle.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    
    private final MemberRepository memberRepository;
    
    /**
     * 회원 생성
     */
    @Transactional
    public MemberResponseDto createMember(MemberCreateDto dto) {
        // 로그인 ID 중복 체크
        if (memberRepository.existsByLoginId(dto.getLoginId())) {
            throw new IllegalArgumentException("이미 사용 중인 로그인 ID입니다: " + dto.getLoginId());
        }
        
        Member member = new Member();
        member.setChurchId(dto.getChurchId());
        member.setLoginId(dto.getLoginId());
        member.setPassword(dto.getPassword()); // TODO: 비밀번호 암호화 필요
        member.setName(dto.getName());
        member.setPhone(dto.getPhone());
        member.setBirth(dto.getBirth());
        member.setMemberRole(dto.getMemberRole() != null ? dto.getMemberRole() : "MEMBER");
        member.setIsActive(true);
        
        Member savedMember = memberRepository.save(member);
        return MemberResponseDto.from(savedMember);
    }
    
    /**
     * 회원 단건 조회
     */
    public MemberResponseDto getMember(UUID memberId) {
        Member member = memberRepository.findByIdAndIsActiveTrue(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다. ID: " + memberId));
        return MemberResponseDto.from(member);
    }
    
    /**
     * 전체 회원 목록 조회
     */
    public List<MemberResponseDto> listMembers() {
        return memberRepository.findByIsActiveTrue().stream()
                .map(MemberResponseDto::from)
                .collect(Collectors.toList());
    }
    
    /**
     * 교회별 회원 목록 조회
     */
    public List<MemberResponseDto> listMembersByChurch(UUID churchId) {
        return memberRepository.findByChurchIdAndIsActiveTrue(churchId).stream()
                .map(MemberResponseDto::from)
                .collect(Collectors.toList());
    }
    
    /**
     * 회원 정보 수정
     */
    @Transactional
    public MemberResponseDto updateMember(MemberUpdateDto dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다. ID: " + dto.getMemberId()));
        
        if (dto.getPassword() != null) {
            member.setPassword(dto.getPassword()); // TODO: 비밀번호 암호화 필요
        }
        if (dto.getName() != null) {
            member.setName(dto.getName());
        }
        if (dto.getPhone() != null) {
            member.setPhone(dto.getPhone());
        }
        if (dto.getBirth() != null) {
            member.setBirth(dto.getBirth());
        }
        if (dto.getMemberRole() != null) {
            member.setMemberRole(dto.getMemberRole());
        }
        if (dto.getIsActive() != null) {
            member.setIsActive(dto.getIsActive());
        }
        
        Member updatedMember = memberRepository.save(member);
        return MemberResponseDto.from(updatedMember);
    }
    
    /**
     * 회원 삭제 (논리 삭제)
     */
    @Transactional
    public void deleteMember(UUID memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다. ID: " + memberId));
        
        member.setIsActive(false);
        memberRepository.save(member);
    }
}

