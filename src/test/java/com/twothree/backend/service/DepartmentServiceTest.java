package com.twothree.backend.service;

import com.twothree.backend.dto.department.DepartmentCreateDto;
import com.twothree.backend.dto.department.DepartmentResponseDto;
import com.twothree.backend.dto.department.DepartmentUpdateDto;
import com.twothree.backend.entity.Department;
import com.twothree.backend.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DepartmentService 테스트")
class DepartmentServiceTest {
    
    @Mock
    private DepartmentRepository departmentRepository;
    
    @InjectMocks
    private DepartmentService departmentService;
    
    private Department department;
    private UUID departmentId;
    private UUID churchId;
    
    @BeforeEach
    void setUp() {
        departmentId = UUID.randomUUID();
        churchId = UUID.randomUUID();
        
        department = new Department();
        department.setId(departmentId);
        department.setChurchId(churchId);
        department.setName("청년부");
        department.setDescription("청년부 설명");
        department.setIsActive(true);
    }
    
    @Test
    @DisplayName("부서 생성 성공")
    void createDepartment_Success() {
        // given
        DepartmentCreateDto dto = new DepartmentCreateDto();
        dto.setChurchId(churchId);
        dto.setName("청년부");
        
        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        
        // when
        DepartmentResponseDto result = departmentService.createDepartment(dto);
        
        // then
        assertThat(result).isNotNull();
        verify(departmentRepository, times(1)).save(any(Department.class));
    }
    
    @Test
    @DisplayName("부서 조회 성공")
    void getDepartment_Success() {
        // given
        when(departmentRepository.findByIdAndIsActiveTrue(departmentId)).thenReturn(Optional.of(department));
        
        // when
        DepartmentResponseDto result = departmentService.getDepartment(departmentId);
        
        // then
        assertThat(result).isNotNull();
        assertThat(result.getDepartmentId()).isEqualTo(departmentId);
    }
    
    @Test
    @DisplayName("교회별 부서 목록 조회")
    void listDepartmentsByChurch() {
        // given
        when(departmentRepository.findByChurchIdAndIsActiveTrue(churchId))
                .thenReturn(Arrays.asList(department));
        
        // when
        List<DepartmentResponseDto> result = departmentService.listDepartmentsByChurch(churchId);
        
        // then
        assertThat(result).hasSize(1);
    }
    
    @Test
    @DisplayName("부서 수정 성공")
    void updateDepartment_Success() {
        // given
        DepartmentUpdateDto dto = new DepartmentUpdateDto();
        dto.setDepartmentId(departmentId);
        dto.setName("수정된부서");
        
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        
        // when
        DepartmentResponseDto result = departmentService.updateDepartment(dto);
        
        // then
        assertThat(result).isNotNull();
        verify(departmentRepository, times(1)).save(department);
    }
    
    @Test
    @DisplayName("부서 삭제 성공")
    void deleteDepartment_Success() {
        // given
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        
        // when
        departmentService.deleteDepartment(departmentId);
        
        // then
        assertThat(department.getIsActive()).isFalse();
        verify(departmentRepository, times(1)).save(department);
    }
}

