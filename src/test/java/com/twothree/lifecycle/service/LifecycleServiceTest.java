package com.twothree.lifecycle.service;

import com.twothree.lifecycle.dto.lifecycle.LifecycleCreateDto;
import com.twothree.lifecycle.dto.lifecycle.LifecycleResponseDto;
import com.twothree.lifecycle.entity.Lifecycle;
import com.twothree.lifecycle.repository.LifecycleRepository;
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
@DisplayName("LifecycleService 테스트")
class LifecycleServiceTest {
    
    @Mock
    private LifecycleRepository lifecycleRepository;
    
    @InjectMocks
    private LifecycleService lifecycleService;
    
    private Lifecycle lifecycle;
    private UUID lifecycleId;
    private UUID churchId;
    
    @BeforeEach
    void setUp() {
        lifecycleId = UUID.randomUUID();
        churchId = UUID.randomUUID();
        
        lifecycle = new Lifecycle();
        lifecycle.setId(lifecycleId);
        lifecycle.setChurchId(churchId);
        lifecycle.setName("유아세례");
        lifecycle.setDescription("유아세례 설명");
        lifecycle.setIsActive(true);
    }
    
    @Test
    @DisplayName("생애주기 생성 성공")
    void createLifecycle_Success() {
        // given
        LifecycleCreateDto dto = new LifecycleCreateDto();
        dto.setChurchId(churchId);
        dto.setName("유아세례");
        
        when(lifecycleRepository.save(any(Lifecycle.class))).thenReturn(lifecycle);
        
        // when
        LifecycleResponseDto result = lifecycleService.createLifecycle(dto);
        
        // then
        assertThat(result).isNotNull();
        verify(lifecycleRepository, times(1)).save(any(Lifecycle.class));
    }
    
    @Test
    @DisplayName("생애주기 조회 성공")
    void getLifecycle_Success() {
        // given
        when(lifecycleRepository.findByIdAndIsActiveTrue(lifecycleId)).thenReturn(Optional.of(lifecycle));
        
        // when
        LifecycleResponseDto result = lifecycleService.getLifecycle(lifecycleId);
        
        // then
        assertThat(result).isNotNull();
        assertThat(result.getLifecycleId()).isEqualTo(lifecycleId);
    }
    
    @Test
    @DisplayName("교회별 생애주기 목록 조회")
    void listLifecyclesByChurch() {
        // given
        when(lifecycleRepository.findByChurchIdAndIsActiveTrue(churchId))
                .thenReturn(Arrays.asList(lifecycle));
        
        // when
        var result = lifecycleService.listLifecyclesByChurch(churchId);
        
        // then
        assertThat(result).hasSize(1);
    }
}

