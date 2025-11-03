package com.twothree.lifecycle.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twothree.lifecycle.constant.ApiEndpoints;
import com.twothree.lifecycle.dto.memberlifecycle.*;
import com.twothree.lifecycle.exception.GlobalExceptionHandler;
import com.twothree.lifecycle.service.MemberLifecycleService;
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

@WebMvcTest(MemberLifecycleController.class)
@Import(GlobalExceptionHandler.class)
@DisplayName("MemberLifecycleController 테스트")
class MemberLifecycleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberLifecycleService memberLifecycleService;

    private MemberLifecycleResponseDto responseDto;

    @BeforeEach
    void setUp() {
        responseDto = MemberLifecycleResponseDto.builder()
                .memberLifecycleId(UUID.randomUUID())
                .memberId(UUID.randomUUID())
                .lifecycleId(UUID.randomUUID())
                .isActive(true)
                .build();
    }

    @Test
    @DisplayName("회원-생애주기 매핑 생성 성공")
    void create_Success() throws Exception {
        // given
        MemberLifecycleCreateDto dto = new MemberLifecycleCreateDto();
        dto.setMemberId(UUID.randomUUID());
        dto.setLifecycleId(UUID.randomUUID());

        when(memberLifecycleService.createMemberLifecycle(any(MemberLifecycleCreateDto.class)))
                .thenReturn(responseDto);

        // when & then
        mockMvc.perform(post(ApiEndpoints.MemberLifecycle.FULL_CREATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(memberLifecycleService, times(1)).createMemberLifecycle(any(MemberLifecycleCreateDto.class));
    }
}
