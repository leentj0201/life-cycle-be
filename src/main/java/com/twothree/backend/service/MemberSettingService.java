package com.twothree.backend.service;

import com.twothree.backend.dto.membersetting.MemberSettingCreateDto;
import com.twothree.backend.dto.membersetting.MemberSettingResponseDto;
import com.twothree.backend.dto.membersetting.MemberSettingUpdateDto;
import com.twothree.backend.entity.MemberSetting;
import com.twothree.backend.repository.MemberSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberSettingService {
    
    private final MemberSettingRepository memberSettingRepository;
    
    @Transactional
    public MemberSettingResponseDto createMemberSetting(MemberSettingCreateDto dto) {
        MemberSetting ms = new MemberSetting();
        ms.setMemberId(dto.getMemberId());
        ms.setIsNotification(dto.getIsNotification() != null ? dto.getIsNotification() : true);
        
        MemberSetting saved = memberSettingRepository.save(ms);
        return MemberSettingResponseDto.from(saved);
    }
    
    public MemberSettingResponseDto getMemberSetting(UUID memberSettingId) {
        MemberSetting ms = memberSettingRepository.findById(memberSettingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원 설정을 찾을 수 없습니다. ID: " + memberSettingId));
        return MemberSettingResponseDto.from(ms);
    }
    
    public MemberSettingResponseDto getMemberSettingByMember(UUID memberId) {
        MemberSetting ms = memberSettingRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원의 설정을 찾을 수 없습니다. 회원 ID: " + memberId));
        return MemberSettingResponseDto.from(ms);
    }
    
    public List<MemberSettingResponseDto> listMemberSettings() {
        return memberSettingRepository.findAll().stream()
                .map(MemberSettingResponseDto::from)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public MemberSettingResponseDto updateMemberSetting(MemberSettingUpdateDto dto) {
        MemberSetting ms = memberSettingRepository.findById(dto.getMemberSettingId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원 설정을 찾을 수 없습니다. ID: " + dto.getMemberSettingId()));
        
        if (dto.getIsNotification() != null) {
            ms.setIsNotification(dto.getIsNotification());
        }
        
        MemberSetting updated = memberSettingRepository.save(ms);
        return MemberSettingResponseDto.from(updated);
    }
    
    @Transactional
    public void deleteMemberSetting(UUID memberSettingId) {
        if (!memberSettingRepository.existsById(memberSettingId)) {
            throw new IllegalArgumentException("해당 회원 설정을 찾을 수 없습니다. ID: " + memberSettingId);
        }
        memberSettingRepository.deleteById(memberSettingId);
    }
}

