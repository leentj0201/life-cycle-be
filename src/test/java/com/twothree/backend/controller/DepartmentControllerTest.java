package com.twothree.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twothree.backend.constant.ApiEndpoints;
import com.twothree.backend.dto.department.*;
import com.twothree.backend.exception.GlobalExceptionHandler;
import com.twothree.backend.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartmentController.class)
@Import(GlobalExceptionHandler.class)
@DisplayName("DepartmentController 테스트")
class DepartmentControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private DepartmentService departmentService;
    
    private DepartmentResponseDto responseDto;
    private UUID departmentId;
    private UUID churchId;
    
    @BeforeEach
    void setUp() {
        departmentId = UUID.randomUUID();
        churchId = UUID.randomUUID();
        
        responseDto = DepartmentResponseDto.builder()
                .departmentId(departmentId)
                .churchId(churchId)
                .name("청년부")
                .description("청년부 설명")
                .isActive(true)
                .build();
    }
    
    @Test
    @DisplayName("부서 생성 성공")
    void createDepartment_Success() throws Exception {
        // given
        DepartmentCreateDto dto = new DepartmentCreateDto();
        dto.setChurchId(churchId);
        dto.setName("청년부");
        
        when(departmentService.createDepartment(any(DepartmentCreateDto.class))).thenReturn(responseDto);
        
        // when & then
        mockMvc.perform(post(ApiEndpoints.Department.FULL_CREATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.departmentId").value(departmentId.toString()));
        
        verify(departmentService, times(1)).createDepartment(any(DepartmentCreateDto.class));
    }
    
    @Test
    @DisplayName("부서 조회 성공")
    void getDepartment_Success() throws Exception {
        // given
        DepartmentGetDto dto = new DepartmentGetDto();
        dto.setDepartmentId(departmentId);
        
        when(departmentService.getDepartment(departmentId)).thenReturn(responseDto);
        
        // when & then
        mockMvc.perform(post(ApiEndpoints.Department.FULL_GET)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.departmentId").value(departmentId.toString()));
        
        verify(departmentService, times(1)).getDepartment(departmentId);
    }
    
    @Test
    @DisplayName("교회별 부서 목록 조회")
    void listDepartmentsByChurch() throws Exception {
        // given
        DepartmentListByChurchDto dto = new DepartmentListByChurchDto();
        dto.setChurchId(churchId);
        
        when(departmentService.listDepartmentsByChurch(churchId)).thenReturn(Arrays.asList(responseDto));
        
        // when & then
        mockMvc.perform(post(ApiEndpoints.Department.FULL_LIST_BY_CHURCH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
        
        verify(departmentService, times(1)).listDepartmentsByChurch(churchId);
    }
}

