package com.twothree.backend.service;

import com.twothree.backend.dto.departmentcontent.DepartmentContentCreateDto;
import com.twothree.backend.dto.departmentcontent.DepartmentContentResponseDto;
import com.twothree.backend.dto.departmentcontent.DepartmentContentUpdateDto;
import com.twothree.backend.entity.DepartmentContent;
import com.twothree.backend.repository.DepartmentContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentContentService {
    
    private final DepartmentContentRepository departmentContentRepository;
    
    @Transactional
    public DepartmentContentResponseDto createDepartmentContent(DepartmentContentCreateDto dto) {
        DepartmentContent content = new DepartmentContent();
        content.setChurchId(dto.getChurchId());
        content.setDepartmentId(dto.getDepartmentId());
        content.setDepartmentContentType(dto.getDepartmentContentType());
        content.setTitle(dto.getTitle());
        content.setContent(dto.getContent());
        content.setIsActive(true);
        
        DepartmentContent saved = departmentContentRepository.save(content);
        return DepartmentContentResponseDto.from(saved);
    }
    
    public DepartmentContentResponseDto getDepartmentContent(UUID contentId) {
        DepartmentContent content = departmentContentRepository.findByIdAndIsActiveTrue(contentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 부서 콘텐츠를 찾을 수 없습니다. ID: " + contentId));
        return DepartmentContentResponseDto.from(content);
    }
    
    public List<DepartmentContentResponseDto> listDepartmentContents() {
        return departmentContentRepository.findByIsActiveTrue().stream()
                .map(DepartmentContentResponseDto::from)
                .collect(Collectors.toList());
    }
    
    public List<DepartmentContentResponseDto> listDepartmentContentsByDepartment(UUID departmentId) {
        return departmentContentRepository.findByDepartmentIdAndIsActiveTrue(departmentId).stream()
                .map(DepartmentContentResponseDto::from)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public DepartmentContentResponseDto updateDepartmentContent(DepartmentContentUpdateDto dto) {
        DepartmentContent content = departmentContentRepository.findById(dto.getDepartmentContentId())
                .orElseThrow(() -> new IllegalArgumentException("해당 부서 콘텐츠를 찾을 수 없습니다. ID: " + dto.getDepartmentContentId()));
        
        if (dto.getDepartmentContentType() != null) {
            content.setDepartmentContentType(dto.getDepartmentContentType());
        }
        if (dto.getTitle() != null) {
            content.setTitle(dto.getTitle());
        }
        if (dto.getContent() != null) {
            content.setContent(dto.getContent());
        }
        if (dto.getIsActive() != null) {
            content.setIsActive(dto.getIsActive());
        }
        
        DepartmentContent updated = departmentContentRepository.save(content);
        return DepartmentContentResponseDto.from(updated);
    }
    
    @Transactional
    public void deleteDepartmentContent(UUID contentId) {
        DepartmentContent content = departmentContentRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 부서 콘텐츠를 찾을 수 없습니다. ID: " + contentId));
        
        content.setIsActive(false);
        departmentContentRepository.save(content);
    }
}

