package com.twothree.lifecycle.service;

import com.twothree.lifecycle.dto.memberdepartment.MemberDepartmentCreateDto;
import com.twothree.lifecycle.dto.memberdepartment.MemberDepartmentResponseDto;
import com.twothree.lifecycle.dto.memberdepartment.MemberDepartmentUpdateDto;
import com.twothree.lifecycle.entity.MemberDepartment;
import com.twothree.lifecycle.repository.MemberDepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberDepartmentService {
    
    private final MemberDepartmentRepository memberDepartmentRepository;
    
    @Transactional
    public MemberDepartmentResponseDto createMemberDepartment(MemberDepartmentCreateDto dto) {
        MemberDepartment md = new MemberDepartment();
        md.setMemberId(dto.getMemberId());
        md.setDepartmentId(dto.getDepartmentId());
        md.setIsActive(true);
        
        MemberDepartment saved = memberDepartmentRepository.save(md);
        return MemberDepartmentResponseDto.from(saved);
    }
    
    public MemberDepartmentResponseDto getMemberDepartment(UUID memberDepartmentId) {
        MemberDepartment md = memberDepartmentRepository.findByIdAndIsActiveTrue(memberDepartmentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원-부서 매핑을 찾을 수 없습니다. ID: " + memberDepartmentId));
        return MemberDepartmentResponseDto.from(md);
    }
    
    public List<MemberDepartmentResponseDto> listMemberDepartments() {
        return memberDepartmentRepository.findByIsActiveTrue().stream()
                .map(MemberDepartmentResponseDto::from)
                .collect(Collectors.toList());
    }
    
    public List<MemberDepartmentResponseDto> listMemberDepartmentsByMember(UUID memberId) {
        return memberDepartmentRepository.findByMemberIdAndIsActiveTrue(memberId).stream()
                .map(MemberDepartmentResponseDto::from)
                .collect(Collectors.toList());
    }
    
    public List<MemberDepartmentResponseDto> listMemberDepartmentsByDepartment(UUID departmentId) {
        return memberDepartmentRepository.findByDepartmentIdAndIsActiveTrue(departmentId).stream()
                .map(MemberDepartmentResponseDto::from)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public MemberDepartmentResponseDto updateMemberDepartment(MemberDepartmentUpdateDto dto) {
        MemberDepartment md = memberDepartmentRepository.findById(dto.getMemberDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원-부서 매핑을 찾을 수 없습니다. ID: " + dto.getMemberDepartmentId()));
        
        if (dto.getIsActive() != null) {
            md.setIsActive(dto.getIsActive());
        }
        
        MemberDepartment updated = memberDepartmentRepository.save(md);
        return MemberDepartmentResponseDto.from(updated);
    }
    
    @Transactional
    public void deleteMemberDepartment(UUID memberDepartmentId) {
        MemberDepartment md = memberDepartmentRepository.findById(memberDepartmentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원-부서 매핑을 찾을 수 없습니다. ID: " + memberDepartmentId));
        
        md.setIsActive(false);
        memberDepartmentRepository.save(md);
    }
}

