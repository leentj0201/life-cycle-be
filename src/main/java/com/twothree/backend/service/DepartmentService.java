package com.twothree.backend.service;

import com.twothree.backend.dto.department.DepartmentCreateDto;
import com.twothree.backend.dto.department.DepartmentResponseDto;
import com.twothree.backend.dto.department.DepartmentUpdateDto;
import com.twothree.backend.entity.Department;
import com.twothree.backend.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentService {
    
    private final DepartmentRepository departmentRepository;
    
    @Transactional
    public DepartmentResponseDto createDepartment(DepartmentCreateDto dto) {
        Department department = new Department();
        department.setChurchId(dto.getChurchId());
        department.setName(dto.getName());
        department.setDescription(dto.getDescription());
        department.setIsActive(true);
        
        Department saved = departmentRepository.save(department);
        return DepartmentResponseDto.from(saved);
    }
    
    public DepartmentResponseDto getDepartment(UUID departmentId) {
        Department department = departmentRepository.findByIdAndIsActiveTrue(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 부서를 찾을 수 없습니다. ID: " + departmentId));
        return DepartmentResponseDto.from(department);
    }
    
    public List<DepartmentResponseDto> listDepartments() {
        return departmentRepository.findByIsActiveTrue().stream()
                .map(DepartmentResponseDto::from)
                .collect(Collectors.toList());
    }
    
    public List<DepartmentResponseDto> listDepartmentsByChurch(UUID churchId) {
        return departmentRepository.findByChurchIdAndIsActiveTrue(churchId).stream()
                .map(DepartmentResponseDto::from)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public DepartmentResponseDto updateDepartment(DepartmentUpdateDto dto) {
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("해당 부서를 찾을 수 없습니다. ID: " + dto.getDepartmentId()));
        
        if (dto.getName() != null) {
            department.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            department.setDescription(dto.getDescription());
        }
        if (dto.getIsActive() != null) {
            department.setIsActive(dto.getIsActive());
        }
        
        Department updated = departmentRepository.save(department);
        return DepartmentResponseDto.from(updated);
    }
    
    @Transactional
    public void deleteDepartment(UUID departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 부서를 찾을 수 없습니다. ID: " + departmentId));
        
        department.setIsActive(false);
        departmentRepository.save(department);
    }
}

