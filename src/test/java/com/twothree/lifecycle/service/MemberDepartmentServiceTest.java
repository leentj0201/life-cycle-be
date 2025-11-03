package com.twothree.lifecycle.service;

import com.twothree.lifecycle.dto.memberdepartment.MemberDepartmentCreateDto;
import com.twothree.lifecycle.dto.memberdepartment.MemberDepartmentResponseDto;
import com.twothree.lifecycle.entity.MemberDepartment;
import com.twothree.lifecycle.repository.MemberDepartmentRepository;
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
@DisplayName("MemberDepartmentService 테스트")
class MemberDepartmentServiceTest {
    
    @Mock
    private MemberDepartmentRepository memberDepartmentRepository;
    
    @InjectMocks
    private MemberDepartmentService memberDepartmentService;
    
    private MemberDepartment memberDepartment;
    private UUID memberDepartmentId;
    private UUID memberId;
    private UUID departmentId;
    
    @BeforeEach
    void setUp() {
        memberDepartmentId = UUID.randomUUID();
        memberId = UUID.randomUUID();
        departmentId = UUID.randomUUID();
        
        memberDepartment = new MemberDepartment();
        memberDepartment.setId(memberDepartmentId);
        memberDepartment.setMemberId(memberId);
        memberDepartment.setDepartmentId(departmentId);
        memberDepartment.setIsActive(true);
    }
    
    @Test
    @DisplayName("회원-부서 매핑 생성 성공")
    void createMemberDepartment_Success() {
        // given
        MemberDepartmentCreateDto dto = new MemberDepartmentCreateDto();
        dto.setMemberId(memberId);
        dto.setDepartmentId(departmentId);
        
        when(memberDepartmentRepository.save(any(MemberDepartment.class))).thenReturn(memberDepartment);
        
        // when
        MemberDepartmentResponseDto result = memberDepartmentService.createMemberDepartment(dto);
        
        // then
        assertThat(result).isNotNull();
        verify(memberDepartmentRepository, times(1)).save(any(MemberDepartment.class));
    }
    
    @Test
    @DisplayName("회원별 부서 목록 조회")
    void listMemberDepartmentsByMember() {
        // given
        when(memberDepartmentRepository.findByMemberIdAndIsActiveTrue(memberId))
                .thenReturn(Arrays.asList(memberDepartment));
        
        // when
        var result = memberDepartmentService.listMemberDepartmentsByMember(memberId);
        
        // then
        assertThat(result).hasSize(1);
    }
    
    @Test
    @DisplayName("회원-부서 매핑 삭제 성공")
    void deleteMemberDepartment_Success() {
        // given
        when(memberDepartmentRepository.findById(memberDepartmentId)).thenReturn(Optional.of(memberDepartment));
        
        // when
        memberDepartmentService.deleteMemberDepartment(memberDepartmentId);
        
        // then
        assertThat(memberDepartment.getIsActive()).isFalse();
        verify(memberDepartmentRepository, times(1)).save(memberDepartment);
    }
}

