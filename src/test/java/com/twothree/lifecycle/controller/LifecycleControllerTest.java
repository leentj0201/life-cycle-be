package com.twothree.lifecycle.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twothree.lifecycle.constant.ApiEndpoints;
import com.twothree.lifecycle.dto.lifecycle.*;
import com.twothree.lifecycle.exception.GlobalExceptionHandler;
import com.twothree.lifecycle.service.LifecycleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LifecycleController.class)
@Import(GlobalExceptionHandler.class)
@DisplayName("LifecycleController 테스트")
class LifecycleControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private LifecycleService lifecycleService;
    
    private LifecycleResponseDto responseDto;
    private UUID lifecycleId;
    
    @BeforeEach
    void setUp() {
        lifecycleId = UUID.randomUUID();
        responseDto = LifecycleResponseDto.builder()
                .lifecycleId(lifecycleId)
                .name("유아세례")
                .isActive(true)
                .build();
    }
    
    @Test
    @DisplayName("생애주기 생성 성공")
    void createLifecycle_Success() throws Exception {
        // given
        LifecycleCreateDto dto = new LifecycleCreateDto();
        dto.setChurchId(UUID.randomUUID());
        dto.setName("유아세례");
        
        when(lifecycleService.createLifecycle(any(LifecycleCreateDto.class))).thenReturn(responseDto);
        
        // when & then
        mockMvc.perform(post(ApiEndpoints.Lifecycle.FULL_CREATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
        
        verify(lifecycleService, times(1)).createLifecycle(any(LifecycleCreateDto.class));
    }
    
    @Test
    @DisplayName("생애주기 조회 성공")
    void getLifecycle_Success() throws Exception {
        // given
        LifecycleGetDto dto = new LifecycleGetDto();
        dto.setLifecycleId(lifecycleId);
        
        when(lifecycleService.getLifecycle(lifecycleId)).thenReturn(responseDto);
        
        // when & then
        mockMvc.perform(post(ApiEndpoints.Lifecycle.FULL_GET)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
        
        verify(lifecycleService, times(1)).getLifecycle(lifecycleId);
    }
}

