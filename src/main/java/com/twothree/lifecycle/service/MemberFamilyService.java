package com.twothree.lifecycle.service;

import com.twothree.lifecycle.dto.memberfamily.MemberFamilyCreateDto;
import com.twothree.lifecycle.dto.memberfamily.MemberFamilyResponseDto;
import com.twothree.lifecycle.dto.memberfamily.MemberFamilyUpdateDto;
import com.twothree.lifecycle.entity.MemberFamily;
import com.twothree.lifecycle.repository.MemberFamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberFamilyService {
    
    private final MemberFamilyRepository memberFamilyRepository;
    
    @Transactional
    public MemberFamilyResponseDto createMemberFamily(MemberFamilyCreateDto dto) {
        MemberFamily mf = new MemberFamily();
        mf.setMemberId(dto.getMemberId());
        mf.setFamilyMemberId(dto.getFamilyMemberId());
        mf.setRelationType(dto.getRelationType());
        
        MemberFamily saved = memberFamilyRepository.save(mf);
        return MemberFamilyResponseDto.from(saved);
    }
    
    public MemberFamilyResponseDto getMemberFamily(UUID memberFamilyId) {
        MemberFamily mf = memberFamilyRepository.findById(memberFamilyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가족 관계를 찾을 수 없습니다. ID: " + memberFamilyId));
        return MemberFamilyResponseDto.from(mf);
    }
    
    public List<MemberFamilyResponseDto> listMemberFamilies() {
        return memberFamilyRepository.findAll().stream()
                .map(MemberFamilyResponseDto::from)
                .collect(Collectors.toList());
    }
    
    public List<MemberFamilyResponseDto> listMemberFamiliesByMember(UUID memberId) {
        return memberFamilyRepository.findByMemberId(memberId).stream()
                .map(MemberFamilyResponseDto::from)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public MemberFamilyResponseDto updateMemberFamily(MemberFamilyUpdateDto dto) {
        MemberFamily mf = memberFamilyRepository.findById(dto.getMemberFamilyId())
                .orElseThrow(() -> new IllegalArgumentException("해당 가족 관계를 찾을 수 없습니다. ID: " + dto.getMemberFamilyId()));
        
        if (dto.getRelationType() != null) {
            mf.setRelationType(dto.getRelationType());
        }
        
        MemberFamily updated = memberFamilyRepository.save(mf);
        return MemberFamilyResponseDto.from(updated);
    }
    
    @Transactional
    public void deleteMemberFamily(UUID memberFamilyId) {
        if (!memberFamilyRepository.existsById(memberFamilyId)) {
            throw new IllegalArgumentException("해당 가족 관계를 찾을 수 없습니다. ID: " + memberFamilyId);
        }
        memberFamilyRepository.deleteById(memberFamilyId);
    }
}

