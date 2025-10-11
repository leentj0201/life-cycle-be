package com.twothree.backend.service;

import com.twothree.backend.dto.lifecyclecontent.LifecycleContentCreateDto;
import com.twothree.backend.dto.lifecyclecontent.LifecycleContentResponseDto;
import com.twothree.backend.dto.lifecyclecontent.LifecycleContentUpdateDto;
import com.twothree.backend.entity.LifecycleContent;
import com.twothree.backend.repository.LifecycleContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LifecycleContentService {
    
    private final LifecycleContentRepository lifecycleContentRepository;
    
    @Transactional
    public LifecycleContentResponseDto createLifecycleContent(LifecycleContentCreateDto dto) {
        LifecycleContent content = new LifecycleContent();
        content.setChurchId(dto.getChurchId());
        content.setLifecycleId(dto.getLifecycleId());
        content.setLifecycleContentType(dto.getLifecycleContentType());
        content.setTitle(dto.getTitle());
        content.setContent(dto.getContent());
        content.setIsActive(true);
        
        LifecycleContent saved = lifecycleContentRepository.save(content);
        return LifecycleContentResponseDto.from(saved);
    }
    
    public LifecycleContentResponseDto getLifecycleContent(UUID contentId) {
        LifecycleContent content = lifecycleContentRepository.findByIdAndIsActiveTrue(contentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 생애주기 콘텐츠를 찾을 수 없습니다. ID: " + contentId));
        return LifecycleContentResponseDto.from(content);
    }
    
    public List<LifecycleContentResponseDto> listLifecycleContents() {
        return lifecycleContentRepository.findByIsActiveTrue().stream()
                .map(LifecycleContentResponseDto::from)
                .collect(Collectors.toList());
    }
    
    public List<LifecycleContentResponseDto> listLifecycleContentsByLifecycle(UUID lifecycleId) {
        return lifecycleContentRepository.findByLifecycleIdAndIsActiveTrue(lifecycleId).stream()
                .map(LifecycleContentResponseDto::from)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public LifecycleContentResponseDto updateLifecycleContent(LifecycleContentUpdateDto dto) {
        LifecycleContent content = lifecycleContentRepository.findById(dto.getLifecycleContentId())
                .orElseThrow(() -> new IllegalArgumentException("해당 생애주기 콘텐츠를 찾을 수 없습니다. ID: " + dto.getLifecycleContentId()));
        
        if (dto.getLifecycleContentType() != null) {
            content.setLifecycleContentType(dto.getLifecycleContentType());
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
        
        LifecycleContent updated = lifecycleContentRepository.save(content);
        return LifecycleContentResponseDto.from(updated);
    }
    
    @Transactional
    public void deleteLifecycleContent(UUID contentId) {
        LifecycleContent content = lifecycleContentRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 생애주기 콘텐츠를 찾을 수 없습니다. ID: " + contentId));
        
        content.setIsActive(false);
        lifecycleContentRepository.save(content);
    }
}

