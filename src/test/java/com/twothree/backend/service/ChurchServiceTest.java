package com.twothree.backend.service;

import com.twothree.backend.dto.church.ChurchCreateDto;
import com.twothree.backend.dto.church.ChurchResponseDto;
import com.twothree.backend.dto.church.ChurchUpdateDto;
import com.twothree.backend.entity.Church;
import com.twothree.backend.repository.ChurchRepository;
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
@DisplayName("ChurchService 테스트")
class ChurchServiceTest {
    
    @Mock
    private ChurchRepository churchRepository;
    
    @InjectMocks
    private ChurchService churchService;
    
    private Church church;
    private UUID churchId;
    
    @BeforeEach
    void setUp() {
        churchId = UUID.randomUUID();
        church = new Church();
        church.setId(churchId);
        church.setName("테스트 교회");
        church.setAddress("서울시 강남구");
        church.setPhone("02-1234-5678");
        church.setEmail("test@church.com");
        church.setWebsite("https://test-church.com");
        church.setPastorName("김목사");
        church.setDescription("테스트 교회입니다");
        church.setIsActive(true);
    }
    
    @Test
    @DisplayName("교회 생성 성공")
    void createChurch_Success() {
        // given
        ChurchCreateDto dto = new ChurchCreateDto();
        dto.setName("새로운 교회");
        dto.setAddress("서울시 강남구");
        dto.setPhone("02-1234-5678");
        dto.setEmail("new@church.com");
        dto.setWebsite("https://new-church.com");
        dto.setPastorName("김목사");
        dto.setDescription("새로운 교회입니다");
        
        when(churchRepository.save(any(Church.class))).thenReturn(church);
        
        // when
        ChurchResponseDto result = churchService.createChurch(dto);
        
        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("테스트 교회");
        verify(churchRepository, times(1)).save(any(Church.class));
    }
    
    @Test
    @DisplayName("교회 단건 조회 성공")
    void getChurch_Success() {
        // given
        when(churchRepository.findByIdAndIsActiveTrue(churchId)).thenReturn(Optional.of(church));
        
        // when
        ChurchResponseDto result = churchService.getChurch(churchId);
        
        // then
        assertThat(result).isNotNull();
        assertThat(result.getChurchId()).isEqualTo(churchId);
        assertThat(result.getName()).isEqualTo("테스트 교회");
        verify(churchRepository, times(1)).findByIdAndIsActiveTrue(churchId);
    }
    
    @Test
    @DisplayName("교회 단건 조회 실패 - 존재하지 않는 교회")
    void getChurch_NotFound() {
        // given
        UUID nonExistentId = UUID.randomUUID();
        when(churchRepository.findByIdAndIsActiveTrue(nonExistentId)).thenReturn(Optional.empty());
        
        // when & then
        assertThatThrownBy(() -> churchService.getChurch(nonExistentId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 교회를 찾을 수 없습니다");
        
        verify(churchRepository, times(1)).findByIdAndIsActiveTrue(nonExistentId);
    }
    
    @Test
    @DisplayName("전체 교회 목록 조회")
    void listChurches() {
        // given
        Church church2 = new Church();
        church2.setId(UUID.randomUUID());
        church2.setName("교회2");
        church2.setIsActive(true);
        
        when(churchRepository.findByIsActiveTrue()).thenReturn(Arrays.asList(church, church2));
        
        // when
        List<ChurchResponseDto> result = churchService.listChurches();
        
        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("테스트 교회");
        assertThat(result.get(1).getName()).isEqualTo("교회2");
        verify(churchRepository, times(1)).findByIsActiveTrue();
    }
    
    @Test
    @DisplayName("교회 정보 수정 성공")
    void updateChurch_Success() {
        // given
        ChurchUpdateDto dto = new ChurchUpdateDto();
        dto.setChurchId(churchId);
        dto.setName("수정된 교회");
        dto.setPastorName("이목사");
        dto.setIsActive(true);
        
        when(churchRepository.findById(churchId)).thenReturn(Optional.of(church));
        when(churchRepository.save(any(Church.class))).thenReturn(church);
        
        // when
        ChurchResponseDto result = churchService.updateChurch(dto);
        
        // then
        assertThat(result).isNotNull();
        verify(churchRepository, times(1)).findById(churchId);
        verify(churchRepository, times(1)).save(any(Church.class));
    }
    
    @Test
    @DisplayName("교회 정보 수정 실패 - 존재하지 않는 교회")
    void updateChurch_NotFound() {
        // given
        ChurchUpdateDto dto = new ChurchUpdateDto();
        dto.setChurchId(UUID.randomUUID());
        dto.setName("수정된 교회");
        
        when(churchRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        
        // when & then
        assertThatThrownBy(() -> churchService.updateChurch(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 교회를 찾을 수 없습니다");
        
        verify(churchRepository, times(1)).findById(any(UUID.class));
        verify(churchRepository, never()).save(any(Church.class));
    }
    
    @Test
    @DisplayName("교회 삭제 성공 (논리 삭제)")
    void deleteChurch_Success() {
        // given
        when(churchRepository.findById(churchId)).thenReturn(Optional.of(church));
        when(churchRepository.save(any(Church.class))).thenReturn(church);
        
        // when
        churchService.deleteChurch(churchId);
        
        // then
        verify(churchRepository, times(1)).findById(churchId);
        verify(churchRepository, times(1)).save(any(Church.class));
        assertThat(church.getIsActive()).isFalse();
    }
    
    @Test
    @DisplayName("교회 삭제 실패 - 존재하지 않는 교회")
    void deleteChurch_NotFound() {
        // given
        UUID nonExistentId = UUID.randomUUID();
        when(churchRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        
        // when & then
        assertThatThrownBy(() -> churchService.deleteChurch(nonExistentId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 교회를 찾을 수 없습니다");
        
        verify(churchRepository, times(1)).findById(nonExistentId);
        verify(churchRepository, never()).save(any(Church.class));
    }
    
    @Test
    @DisplayName("교회 정보 부분 수정 - null 값은 수정하지 않음")
    void updateChurch_PartialUpdate() {
        // given
        ChurchUpdateDto dto = new ChurchUpdateDto();
        dto.setChurchId(churchId);
        dto.setName("수정된 이름");
        // 다른 필드는 null
        
        when(churchRepository.findById(churchId)).thenReturn(Optional.of(church));
        when(churchRepository.save(any(Church.class))).thenReturn(church);
        
        // when
        churchService.updateChurch(dto);
        
        // then
        assertThat(church.getName()).isEqualTo("수정된 이름");
        assertThat(church.getAddress()).isEqualTo("서울시 강남구"); // 기존 값 유지
        assertThat(church.getPhone()).isEqualTo("02-1234-5678"); // 기존 값 유지
        verify(churchRepository, times(1)).save(church);
    }
}

