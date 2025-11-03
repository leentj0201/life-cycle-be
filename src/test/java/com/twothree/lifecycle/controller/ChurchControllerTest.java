package com.twothree.lifecycle.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twothree.lifecycle.constant.ApiEndpoints;
import com.twothree.lifecycle.dto.church.ChurchCreateDto;
import com.twothree.lifecycle.dto.church.ChurchDeleteDto;
import com.twothree.lifecycle.dto.church.ChurchGetDto;
import com.twothree.lifecycle.dto.church.ChurchResponseDto;
import com.twothree.lifecycle.dto.church.ChurchUpdateDto;
import com.twothree.lifecycle.exception.GlobalExceptionHandler;
import com.twothree.lifecycle.service.ChurchService;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ChurchController.class)
@Import(GlobalExceptionHandler.class)
@DisplayName("ChurchController 테스트")
class ChurchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ChurchService churchService;

    private ChurchResponseDto responseDto;
    private UUID churchId;

    @BeforeEach
    void setUp() {
        churchId = UUID.randomUUID();
        responseDto = ChurchResponseDto.builder()
            .churchId(churchId)
            .name("테스트 교회")
            .address("서울시 강남구")
            .phone("02-1234-5678")
            .email("test@church.com")
            .website("https://test-church.com")
            .pastorName("김목사")
            .description("테스트 교회입니다")
            .isActive(true)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }

    @Test
    @DisplayName("POST /api/church/create - 교회 생성 성공")
    void createChurch_Success() throws Exception {
        // given
        ChurchCreateDto dto = new ChurchCreateDto();
        dto.setName("새로운 교회");
        dto.setAddress("서울시 강남구");
        dto.setPhone("02-1234-5678");
        dto.setEmail("new@church.com");
        dto.setWebsite("https://new-church.com");
        dto.setPastorName("김목사");
        dto.setDescription("새로운 교회입니다");

        when(churchService.createChurch(any(ChurchCreateDto.class))).thenReturn(responseDto);

        // when & then
        mockMvc.perform(post(ApiEndpoints.Church.FULL_CREATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.churchId").value(churchId.toString()))
            .andExpect(jsonPath("$.name").value("테스트 교회"))
            .andExpect(jsonPath("$.address").value("서울시 강남구"))
            .andExpect(jsonPath("$.phone").value("02-1234-5678"));

        verify(churchService, times(1)).createChurch(any(ChurchCreateDto.class));
    }

    @Test
    @DisplayName("POST /api/church/create - 유효성 검증 실패 (이름 누락)")
    void createChurch_ValidationFail() throws Exception {
        // given
        ChurchCreateDto dto = new ChurchCreateDto();
        // name을 설정하지 않음
        dto.setAddress("서울시 강남구");

        // when & then
        mockMvc.perform(post(ApiEndpoints.Church.FULL_CREATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andDo(print())
            .andExpect(status().isBadRequest());

        verify(churchService, never()).createChurch(any(ChurchCreateDto.class));
    }

    @Test
    @DisplayName("POST /api/church/get - 교회 단건 조회 성공")
    void getChurch_Success() throws Exception {
        // given
        ChurchGetDto dto = new ChurchGetDto();
        dto.setChurchId(churchId);

        when(churchService.getChurch(churchId)).thenReturn(responseDto);

        // when & then
        mockMvc.perform(post(ApiEndpoints.Church.FULL_GET)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.churchId").value(churchId.toString()))
            .andExpect(jsonPath("$.name").value("테스트 교회"));

        verify(churchService, times(1)).getChurch(churchId);
    }

    @Test
    @DisplayName("POST /api/church/get - 존재하지 않는 교회 조회")
    void getChurch_NotFound() throws Exception {
        // given
        ChurchGetDto dto = new ChurchGetDto();
        dto.setChurchId(churchId);

        when(churchService.getChurch(churchId))
            .thenThrow(new IllegalArgumentException("해당 교회를 찾을 수 없습니다"));

        // when & then
        mockMvc.perform(post(ApiEndpoints.Church.FULL_GET)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("해당 교회를 찾을 수 없습니다"));

        verify(churchService, times(1)).getChurch(churchId);
    }

    @Test
    @DisplayName("POST /api/church/list - 전체 교회 목록 조회")
    void listChurches() throws Exception {
        // given
        ChurchResponseDto responseDto2 = ChurchResponseDto.builder()
            .churchId(UUID.randomUUID())
            .name("교회2")
            .isActive(true)
            .build();

        List<ChurchResponseDto> churches = Arrays.asList(responseDto, responseDto2);
        when(churchService.listChurches()).thenReturn(churches);

        // when & then
        mockMvc.perform(post(ApiEndpoints.Church.FULL_LIST)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].name").value("테스트 교회"))
            .andExpect(jsonPath("$[1].name").value("교회2"));

        verify(churchService, times(1)).listChurches();
    }

    @Test
    @DisplayName("POST /api/church/update - 교회 정보 수정 성공")
    void updateChurch_Success() throws Exception {
        // given
        ChurchUpdateDto dto = new ChurchUpdateDto();
        dto.setChurchId(churchId);
        dto.setName("수정된 교회");
        dto.setPastorName("이목사");

        ChurchResponseDto updatedResponse = ChurchResponseDto.builder()
            .churchId(churchId)
            .name("수정된 교회")
            .pastorName("이목사")
            .isActive(true)
            .build();

        when(churchService.updateChurch(any(ChurchUpdateDto.class))).thenReturn(updatedResponse);

        // when & then
        mockMvc.perform(post(ApiEndpoints.Church.FULL_UPDATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.churchId").value(churchId.toString()))
            .andExpect(jsonPath("$.name").value("수정된 교회"))
            .andExpect(jsonPath("$.pastorName").value("이목사"));

        verify(churchService, times(1)).updateChurch(any(ChurchUpdateDto.class));
    }

    @Test
    @DisplayName("POST /api/church/update - 유효성 검증 실패 (ID 누락)")
    void updateChurch_ValidationFail() throws Exception {
        // given
        ChurchUpdateDto dto = new ChurchUpdateDto();
        // churchId를 설정하지 않음
        dto.setName("수정된 교회");

        // when & then
        mockMvc.perform(post(ApiEndpoints.Church.FULL_UPDATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andDo(print())
            .andExpect(status().isBadRequest());

        verify(churchService, never()).updateChurch(any(ChurchUpdateDto.class));
    }

    @Test
    @DisplayName("POST /api/church/delete - 교회 삭제 성공")
    void deleteChurch_Success() throws Exception {
        // given
        ChurchDeleteDto dto = new ChurchDeleteDto();
        dto.setChurchId(churchId);

        doNothing().when(churchService).deleteChurch(churchId);

        // when & then
        mockMvc.perform(post(ApiEndpoints.Church.FULL_DELETE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andDo(print())
            .andExpect(status().isNoContent());

        verify(churchService, times(1)).deleteChurch(churchId);
    }

    @Test
    @DisplayName("POST /api/church/delete - 유효성 검증 실패 (ID 누락)")
    void deleteChurch_ValidationFail() throws Exception {
        // given
        ChurchDeleteDto dto = new ChurchDeleteDto();
        // churchId를 설정하지 않음

        // when & then
        mockMvc.perform(post(ApiEndpoints.Church.FULL_DELETE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andDo(print())
            .andExpect(status().isBadRequest());

        verify(churchService, never()).deleteChurch(any(UUID.class));
    }

    @Test
    @DisplayName("POST /api/church/create - 이메일 형식 검증")
    void createChurch_EmailValidation() throws Exception {
        // given
        ChurchCreateDto dto = new ChurchCreateDto();
        dto.setName("새로운 교회");
        dto.setEmail("invalid-email"); // 잘못된 이메일 형식

        // when & then
        mockMvc.perform(post(ApiEndpoints.Church.FULL_CREATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andDo(print())
            .andExpect(status().isBadRequest());

        verify(churchService, never()).createChurch(any(ChurchCreateDto.class));
    }
}

