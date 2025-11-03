package com.twothree.lifecycle.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twothree.lifecycle.constant.ApiEndpoints;
import com.twothree.lifecycle.dto.member.*;
import com.twothree.lifecycle.exception.GlobalExceptionHandler;
import com.twothree.lifecycle.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
@Import(GlobalExceptionHandler.class)
@DisplayName("MemberController 테스트")
class MemberControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private MemberService memberService;
    
    private MemberResponseDto responseDto;
    private UUID memberId;
    private UUID churchId;
    
    @BeforeEach
    void setUp() {
        memberId = UUID.randomUUID();
        churchId = UUID.randomUUID();
        
        responseDto = MemberResponseDto.builder()
                .memberId(memberId)
                .churchId(churchId)
                .loginId("testuser")
                .name("테스트유저")
                .phone("010-1234-5678")
                .birth(LocalDate.of(1990, 1, 1))
                .memberRole("MEMBER")
                .isActive(true)
                .build();
    }
    
    @Test
    @DisplayName("회원 생성 성공")
    void createMember_Success() throws Exception {
        // given
        MemberCreateDto dto = new MemberCreateDto();
        dto.setChurchId(churchId);
        dto.setLoginId("newuser");
        dto.setPassword("password123");
        dto.setName("새회원");
        
        when(memberService.createMember(any(MemberCreateDto.class))).thenReturn(responseDto);
        
        // when & then
        mockMvc.perform(post(ApiEndpoints.Member.FULL_CREATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.memberId").value(memberId.toString()));
        
        verify(memberService, times(1)).createMember(any(MemberCreateDto.class));
    }
    
    @Test
    @DisplayName("회원 조회 성공")
    void getMember_Success() throws Exception {
        // given
        MemberGetDto dto = new MemberGetDto();
        dto.setMemberId(memberId);
        
        when(memberService.getMember(memberId)).thenReturn(responseDto);
        
        // when & then
        mockMvc.perform(post(ApiEndpoints.Member.FULL_GET)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberId").value(memberId.toString()));
        
        verify(memberService, times(1)).getMember(memberId);
    }
    
    @Test
    @DisplayName("교회별 회원 목록 조회")
    void listMembersByChurch() throws Exception {
        // given
        MemberListByChurchDto dto = new MemberListByChurchDto();
        dto.setChurchId(churchId);
        
        when(memberService.listMembersByChurch(churchId)).thenReturn(Arrays.asList(responseDto));
        
        // when & then
        mockMvc.perform(post(ApiEndpoints.Member.FULL_LIST_BY_CHURCH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
        
        verify(memberService, times(1)).listMembersByChurch(churchId);
    }
    
    @Test
    @DisplayName("회원 수정 성공")
    void updateMember_Success() throws Exception {
        // given
        MemberUpdateDto dto = new MemberUpdateDto();
        dto.setMemberId(memberId);
        dto.setName("수정된이름");
        
        when(memberService.updateMember(any(MemberUpdateDto.class))).thenReturn(responseDto);
        
        // when & then
        mockMvc.perform(post(ApiEndpoints.Member.FULL_UPDATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
        
        verify(memberService, times(1)).updateMember(any(MemberUpdateDto.class));
    }
    
    @Test
    @DisplayName("회원 삭제 성공")
    void deleteMember_Success() throws Exception {
        // given
        MemberDeleteDto dto = new MemberDeleteDto();
        dto.setMemberId(memberId);
        
        doNothing().when(memberService).deleteMember(memberId);
        
        // when & then
        mockMvc.perform(post(ApiEndpoints.Member.FULL_DELETE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());
        
        verify(memberService, times(1)).deleteMember(memberId);
    }
}

