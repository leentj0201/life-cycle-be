package com.twothree.backend.service;

import com.twothree.backend.dto.memberlifecycle.MemberLifecycleCreateDto;
import com.twothree.backend.dto.memberlifecycle.MemberLifecycleResponseDto;
import com.twothree.backend.dto.memberlifecycle.MemberLifecycleUpdateDto;
import com.twothree.backend.entity.MemberLifecycle;
import com.twothree.backend.repository.MemberLifecycleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberLifecycleService {
    
    private final MemberLifecycleRepository memberLifecycleRepository;
    
    @Transactional
    public MemberLifecycleResponseDto createMemberLifecycle(MemberLifecycleCreateDto dto) {
        MemberLifecycle ml = new MemberLifecycle();
        ml.setMemberId(dto.getMemberId());
        ml.setLifecycleId(dto.getLifecycleId());
        ml.setIsActive(true);
        
        MemberLifecycle saved = memberLifecycleRepository.save(ml);
        return MemberLifecycleResponseDto.from(saved);
    }
    
    public MemberLifecycleResponseDto getMemberLifecycle(UUID memberLifecycleId) {
        MemberLifecycle ml = memberLifecycleRepository.findByIdAndIsActiveTrue(memberLifecycleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원-생애주기 매핑을 찾을 수 없습니다. ID: " + memberLifecycleId));
        return MemberLifecycleResponseDto.from(ml);
    }
    
    public List<MemberLifecycleResponseDto> listMemberLifecycles() {
        return memberLifecycleRepository.findByIsActiveTrue().stream()
                .map(MemberLifecycleResponseDto::from)
                .collect(Collectors.toList());
    }
    
    public List<MemberLifecycleResponseDto> listMemberLifecyclesByMember(UUID memberId) {
        return memberLifecycleRepository.findByMemberIdAndIsActiveTrue(memberId).stream()
                .map(MemberLifecycleResponseDto::from)
                .collect(Collectors.toList());
    }
    
    public List<MemberLifecycleResponseDto> listMemberLifecyclesByLifecycle(UUID lifecycleId) {
        return memberLifecycleRepository.findByLifecycleIdAndIsActiveTrue(lifecycleId).stream()
                .map(MemberLifecycleResponseDto::from)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public MemberLifecycleResponseDto updateMemberLifecycle(MemberLifecycleUpdateDto dto) {
        MemberLifecycle ml = memberLifecycleRepository.findById(dto.getMemberLifecycleId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원-생애주기 매핑을 찾을 수 없습니다. ID: " + dto.getMemberLifecycleId()));
        
        if (dto.getIsActive() != null) {
            ml.setIsActive(dto.getIsActive());
        }
        
        MemberLifecycle updated = memberLifecycleRepository.save(ml);
        return MemberLifecycleResponseDto.from(updated);
    }
    
    @Transactional
    public void deleteMemberLifecycle(UUID memberLifecycleId) {
        MemberLifecycle ml = memberLifecycleRepository.findById(memberLifecycleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원-생애주기 매핑을 찾을 수 없습니다. ID: " + memberLifecycleId));
        
        ml.setIsActive(false);
        memberLifecycleRepository.save(ml);
    }
}

