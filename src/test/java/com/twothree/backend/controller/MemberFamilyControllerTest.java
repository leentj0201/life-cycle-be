package com.twothree.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twothree.backend.constant.ApiEndpoints;
import com.twothree.backend.dto.memberfamily.*;
import com.twothree.backend.exception.GlobalExceptionHandler;
import com.twothree.backend.service.MemberFamilyService;
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

@WebMvcTest(MemberFamilyController.class)
@Import(GlobalExceptionHandler.class)
@DisplayName("MemberFamilyController 테스트")
class MemberFamilyControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private MemberFamilyService memberFamilyService;
    
    private MemberFamilyResponseDto responseDto;
    
    @BeforeEach
    void setUp() {
        responseDto = MemberFamilyResponseDto.builder()
                .memberFamilyId(UUID.randomUUID())
                .memberId(UUID.randomUUID())
                .familyMemberId(UUID.randomUUID())
                .relationType("부모")
                .build();
    }
    
    @Test
    @DisplayName("가족 관계 생성 성공")
    void create_Success() throws Exception {
        // given
        MemberFamilyCreateDto dto = new MemberFamilyCreateDto();
        dto.setMemberId(UUID.randomUUID());
        dto.setFamilyMemberId(UUID.randomUUID());
        dto.setRelationType("부모");
        
        when(memberFamilyService.createMemberFamily(any(MemberFamilyCreateDto.class)))
                .thenReturn(responseDto);
        
        // when & then
        mockMvc.perform(post(ApiEndpoints.MemberFamily.FULL_CREATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
        
        verify(memberFamilyService, times(1)).createMemberFamily(any(MemberFamilyCreateDto.class));
    }
    
    @Test
    @DisplayName("회원별 가족 관계 목록 조회")
    void listByMember() throws Exception {
        // given
        MemberFamilyListByMemberDto dto = new MemberFamilyListByMemberDto();
        dto.setMemberId(UUID.randomUUID());
        
        when(memberFamilyService.listMemberFamiliesByMember(any(UUID.class)))
                .thenReturn(Arrays.asList(responseDto));
        
        // when & then
        mockMvc.perform(post(ApiEndpoints.MemberFamily.FULL_LIST_BY_MEMBER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
        
        verify(memberFamilyService, times(1)).listMemberFamiliesByMember(any(UUID.class));
    }
}

