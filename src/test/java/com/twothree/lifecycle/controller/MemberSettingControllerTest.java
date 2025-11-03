package com.twothree.lifecycle.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twothree.lifecycle.constant.ApiEndpoints;
import com.twothree.lifecycle.dto.membersetting.*;
import com.twothree.lifecycle.exception.GlobalExceptionHandler;
import com.twothree.lifecycle.service.MemberSettingService;
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

@WebMvcTest(MemberSettingController.class)
@Import(GlobalExceptionHandler.class)
@DisplayName("MemberSettingController 테스트")
class MemberSettingControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private MemberSettingService memberSettingService;
    
    private MemberSettingResponseDto responseDto;
    
    @BeforeEach
    void setUp() {
        responseDto = MemberSettingResponseDto.builder()
                .memberSettingId(UUID.randomUUID())
                .memberId(UUID.randomUUID())
                .isNotification(true)
                .build();
    }
    
    @Test
    @DisplayName("회원 설정 생성 성공")
    void create_Success() throws Exception {
        // given
        MemberSettingCreateDto dto = new MemberSettingCreateDto();
        dto.setMemberId(UUID.randomUUID());
        dto.setIsNotification(true);
        
        when(memberSettingService.createMemberSetting(any(MemberSettingCreateDto.class)))
                .thenReturn(responseDto);
        
        // when & then
        mockMvc.perform(post(ApiEndpoints.MemberSetting.FULL_CREATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
        
        verify(memberSettingService, times(1)).createMemberSetting(any(MemberSettingCreateDto.class));
    }
    
    @Test
    @DisplayName("회원별 설정 조회")
    void getByMember() throws Exception {
        // given
        MemberSettingGetByMemberDto dto = new MemberSettingGetByMemberDto();
        dto.setMemberId(UUID.randomUUID());
        
        when(memberSettingService.getMemberSettingByMember(any(UUID.class)))
                .thenReturn(responseDto);
        
        // when & then
        mockMvc.perform(post(ApiEndpoints.MemberSetting.FULL_GET_BY_MEMBER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
        
        verify(memberSettingService, times(1)).getMemberSettingByMember(any(UUID.class));
    }
}

