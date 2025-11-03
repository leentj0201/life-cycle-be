package com.twothree.lifecycle.service;

import com.twothree.lifecycle.dto.lifecycle.LifecycleCreateDto;
import com.twothree.lifecycle.dto.lifecycle.LifecycleResponseDto;
import com.twothree.lifecycle.dto.lifecycle.LifecycleUpdateDto;
import com.twothree.lifecycle.entity.Lifecycle;
import com.twothree.lifecycle.repository.LifecycleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LifecycleService {
    
    private final LifecycleRepository lifecycleRepository;
    
    @Transactional
    public LifecycleResponseDto createLifecycle(LifecycleCreateDto dto) {
        Lifecycle lifecycle = new Lifecycle();
        lifecycle.setChurchId(dto.getChurchId());
        lifecycle.setName(dto.getName());
        lifecycle.setDescription(dto.getDescription());
        lifecycle.setIsActive(true);
        
        Lifecycle saved = lifecycleRepository.save(lifecycle);
        return LifecycleResponseDto.from(saved);
    }
    
    public LifecycleResponseDto getLifecycle(UUID lifecycleId) {
        Lifecycle lifecycle = lifecycleRepository.findByIdAndIsActiveTrue(lifecycleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 생애주기를 찾을 수 없습니다. ID: " + lifecycleId));
        return LifecycleResponseDto.from(lifecycle);
    }
    
    public List<LifecycleResponseDto> listLifecycles() {
        return lifecycleRepository.findByIsActiveTrue().stream()
                .map(LifecycleResponseDto::from)
                .collect(Collectors.toList());
    }
    
    public List<LifecycleResponseDto> listLifecyclesByChurch(UUID churchId) {
        return lifecycleRepository.findByChurchIdAndIsActiveTrue(churchId).stream()
                .map(LifecycleResponseDto::from)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public LifecycleResponseDto updateLifecycle(LifecycleUpdateDto dto) {
        Lifecycle lifecycle = lifecycleRepository.findById(dto.getLifecycleId())
                .orElseThrow(() -> new IllegalArgumentException("해당 생애주기를 찾을 수 없습니다. ID: " + dto.getLifecycleId()));
        
        if (dto.getName() != null) {
            lifecycle.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            lifecycle.setDescription(dto.getDescription());
        }
        if (dto.getIsActive() != null) {
            lifecycle.setIsActive(dto.getIsActive());
        }
        
        Lifecycle updated = lifecycleRepository.save(lifecycle);
        return LifecycleResponseDto.from(updated);
    }
    
    @Transactional
    public void deleteLifecycle(UUID lifecycleId) {
        Lifecycle lifecycle = lifecycleRepository.findById(lifecycleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 생애주기를 찾을 수 없습니다. ID: " + lifecycleId));
        
        lifecycle.setIsActive(false);
        lifecycleRepository.save(lifecycle);
    }
}

