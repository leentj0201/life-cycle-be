package com.twothree.lifecycle.controller;

import com.twothree.lifecycle.constant.ApiEndpoints;
import com.twothree.lifecycle.dto.church.ChurchCreateDto;
import com.twothree.lifecycle.dto.church.ChurchDeleteDto;
import com.twothree.lifecycle.dto.church.ChurchGetDto;
import com.twothree.lifecycle.dto.church.ChurchResponseDto;
import com.twothree.lifecycle.dto.church.ChurchUpdateDto;
import com.twothree.lifecycle.service.ChurchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiEndpoints.Church.BASE)
@RequiredArgsConstructor
@Tag(name = "Church", description = "교회 관리 API")
public class ChurchController {
    
    private final ChurchService churchService;
    
    /**
     * 교회 생성
     */
    @Operation(summary = "교회 생성", description = "새로운 교회를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "교회 생성 성공",
                    content = @Content(schema = @Schema(implementation = ChurchResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터",
                    content = @Content)
    })
    @PostMapping(ApiEndpoints.Church.CREATE)
    public ResponseEntity<ChurchResponseDto> createChurch(@Valid @RequestBody ChurchCreateDto dto) {
        ChurchResponseDto response = churchService.createChurch(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * 교회 단건 조회
     */
    @Operation(summary = "교회 조회", description = "교회 ID로 교회 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = ChurchResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "해당 교회를 찾을 수 없음",
                    content = @Content)
    })
    @PostMapping(ApiEndpoints.Church.GET)
    public ResponseEntity<ChurchResponseDto> getChurch(@Valid @RequestBody ChurchGetDto dto) {
        ChurchResponseDto response = churchService.getChurch(dto.getChurchId());
        return ResponseEntity.ok(response);
    }
    
    /**
     * 전체 교회 목록 조회
     */
    @Operation(summary = "교회 목록 조회", description = "활성화된 모든 교회 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @PostMapping(ApiEndpoints.Church.LIST)
    public ResponseEntity<List<ChurchResponseDto>> listChurches() {
        List<ChurchResponseDto> response = churchService.listChurches();
        return ResponseEntity.ok(response);
    }
    
    /**
     * 교회 정보 수정
     */
    @Operation(summary = "교회 정보 수정", description = "교회 정보를 수정합니다. null 값은 기존 값을 유지합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(schema = @Schema(implementation = ChurchResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "해당 교회를 찾을 수 없음",
                    content = @Content)
    })
    @PostMapping(ApiEndpoints.Church.UPDATE)
    public ResponseEntity<ChurchResponseDto> updateChurch(@Valid @RequestBody ChurchUpdateDto dto) {
        ChurchResponseDto response = churchService.updateChurch(dto);
        return ResponseEntity.ok(response);
    }
    
    /**
     * 교회 삭제 (논리 삭제)
     */
    @Operation(summary = "교회 삭제", description = "교회를 논리 삭제합니다. (isActive = false)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "400", description = "해당 교회를 찾을 수 없음",
                    content = @Content)
    })
    @PostMapping(ApiEndpoints.Church.DELETE)
    public ResponseEntity<Void> deleteChurch(@Valid @RequestBody ChurchDeleteDto dto) {
        churchService.deleteChurch(dto.getChurchId());
        return ResponseEntity.noContent().build();
    }
}

