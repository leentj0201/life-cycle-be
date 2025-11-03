package com.twothree.lifecycle.service;

import com.twothree.lifecycle.dto.church.ChurchCreateDto;
import com.twothree.lifecycle.dto.church.ChurchResponseDto;
import com.twothree.lifecycle.dto.church.ChurchUpdateDto;
import com.twothree.lifecycle.entity.Church;
import com.twothree.lifecycle.repository.ChurchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChurchService {
    
    private final ChurchRepository churchRepository;
    
    /**
     * 교회 생성
     */
    @Transactional
    public ChurchResponseDto createChurch(ChurchCreateDto dto) {
        Church church = new Church();
        church.setName(dto.getName());
        church.setAddress(dto.getAddress());
        church.setPhone(dto.getPhone());
        church.setEmail(dto.getEmail());
        church.setWebsite(dto.getWebsite());
        church.setPastorName(dto.getPastorName());
        church.setDescription(dto.getDescription());
        church.setIsActive(true);
        
        Church savedChurch = churchRepository.save(church);
        return ChurchResponseDto.from(savedChurch);
    }
    
    /**
     * 교회 단건 조회
     */
    public ChurchResponseDto getChurch(UUID churchId) {
        Church church = churchRepository.findByIdAndIsActiveTrue(churchId)
                .orElseThrow(() -> new IllegalArgumentException("해당 교회를 찾을 수 없습니다. ID: " + churchId));
        return ChurchResponseDto.from(church);
    }
    
    /**
     * 전체 교회 목록 조회 (활성화된 교회만)
     */
    public List<ChurchResponseDto> listChurches() {
        return churchRepository.findByIsActiveTrue().stream()
                .map(ChurchResponseDto::from)
                .collect(Collectors.toList());
    }
    
    /**
     * 교회 정보 수정
     */
    @Transactional
    public ChurchResponseDto updateChurch(ChurchUpdateDto dto) {
        Church church = churchRepository.findById(dto.getChurchId())
                .orElseThrow(() -> new IllegalArgumentException("해당 교회를 찾을 수 없습니다. ID: " + dto.getChurchId()));
        
        if (dto.getName() != null) {
            church.setName(dto.getName());
        }
        if (dto.getAddress() != null) {
            church.setAddress(dto.getAddress());
        }
        if (dto.getPhone() != null) {
            church.setPhone(dto.getPhone());
        }
        if (dto.getEmail() != null) {
            church.setEmail(dto.getEmail());
        }
        if (dto.getWebsite() != null) {
            church.setWebsite(dto.getWebsite());
        }
        if (dto.getPastorName() != null) {
            church.setPastorName(dto.getPastorName());
        }
        if (dto.getDescription() != null) {
            church.setDescription(dto.getDescription());
        }
        if (dto.getIsActive() != null) {
            church.setIsActive(dto.getIsActive());
        }
        
        Church updatedChurch = churchRepository.save(church);
        return ChurchResponseDto.from(updatedChurch);
    }
    
    /**
     * 교회 삭제 (논리 삭제 - isActive를 false로 변경)
     */
    @Transactional
    public void deleteChurch(UUID churchId) {
        Church church = churchRepository.findById(churchId)
                .orElseThrow(() -> new IllegalArgumentException("해당 교회를 찾을 수 없습니다. ID: " + churchId));
        
        church.setIsActive(false);
        churchRepository.save(church);
    }
}

