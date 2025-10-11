package com.twothree.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twothree.backend.constant.ApiEndpoints;
import com.twothree.backend.dto.memberdepartment.*;
import com.twothree.backend.exception.GlobalExceptionHandler;
import com.twothree.backend.service.MemberDepartmentService;
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

@WebMvcTest(MemberDepartmentController.class)
@Import(GlobalExceptionHandler.class)
@DisplayName("MemberDepartmentController 테스트")
class MemberDepartmentControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private MemberDepartmentService memberDepartmentService;
    
    private MemberDepartmentResponseDto responseDto;
    
    @BeforeEach
    void setUp() {
        responseDto = MemberDepartmentResponseDto.builder()
                .memberDepartmentId(UUID.randomUUID())
                .memberId(UUID.randomUUID())
                .departmentId(UUID.randomUUID())
                .isActive(true)
                .build();
    }
    
    @Test
    @DisplayName("회원-부서 매핑 생성 성공")
    void createMemberDepartment_Success() throws Exception {
        // given
        MemberDepartmentCreateDto dto = new MemberDepartmentCreateDto();
        dto.setMemberId(UUID.randomUUID());
        dto.setDepartmentId(UUID.randomUUID());
        
        when(memberDepartmentService.createMemberDepartment(any(MemberDepartmentCreateDto.class)))
                .thenReturn(responseDto);
        
        // when & then
        mockMvc.perform(post(ApiEndpoints.MemberDepartment.FULL_CREATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
        
        verify(memberDepartmentService, times(1)).createMemberDepartment(any(MemberDepartmentCreateDto.class));
    }
    
    @Test
    @DisplayName("회원별 부서 목록 조회")
    void listByMember() throws Exception {
        // given
        MemberDepartmentListByMemberDto dto = new MemberDepartmentListByMemberDto();
        dto.setMemberId(UUID.randomUUID());
        
        when(memberDepartmentService.listMemberDepartmentsByMember(any(UUID.class)))
                .thenReturn(Arrays.asList(responseDto));
        
        // when & then
        mockMvc.perform(post(ApiEndpoints.MemberDepartment.FULL_LIST_BY_MEMBER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
        
        verify(memberDepartmentService, times(1)).listMemberDepartmentsByMember(any(UUID.class));
    }
}

