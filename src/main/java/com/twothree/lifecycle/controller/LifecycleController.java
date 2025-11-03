package com.twothree.lifecycle.controller;

import com.twothree.lifecycle.constant.ApiEndpoints;
import com.twothree.lifecycle.dto.lifecycle.LifecycleCreateDto;
import com.twothree.lifecycle.dto.lifecycle.LifecycleDeleteDto;
import com.twothree.lifecycle.dto.lifecycle.LifecycleGetDto;
import com.twothree.lifecycle.dto.lifecycle.LifecycleListByChurchDto;
import com.twothree.lifecycle.dto.lifecycle.LifecycleResponseDto;
import com.twothree.lifecycle.dto.lifecycle.LifecycleUpdateDto;
import com.twothree.lifecycle.service.LifecycleService;
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
@RequestMapping(ApiEndpoints.Lifecycle.BASE)
@RequiredArgsConstructor
@Tag(name = "Lifecycle", description = "생애주기 관리 API")
public class LifecycleController {
    
    private final LifecycleService lifecycleService;
    
    @Operation(summary = "생애주기 생성", description = "새로운 생애주기를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "생애주기 생성 성공",
                    content = @Content(schema = @Schema(implementation = LifecycleResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터",
                    content = @Content)
    })
    @PostMapping(ApiEndpoints.Lifecycle.CREATE)
    public ResponseEntity<LifecycleResponseDto> createLifecycle(@Valid @RequestBody LifecycleCreateDto dto) {
        LifecycleResponseDto response = lifecycleService.createLifecycle(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @Operation(summary = "생애주기 조회", description = "생애주기 ID로 생애주기 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = LifecycleResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "해당 생애주기를 찾을 수 없음",
                    content = @Content)
    })
    @PostMapping(ApiEndpoints.Lifecycle.GET)
    public ResponseEntity<LifecycleResponseDto> getLifecycle(@Valid @RequestBody LifecycleGetDto dto) {
        LifecycleResponseDto response = lifecycleService.getLifecycle(dto.getLifecycleId());
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "생애주기 목록 조회", description = "활성화된 모든 생애주기 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @PostMapping(ApiEndpoints.Lifecycle.LIST)
    public ResponseEntity<List<LifecycleResponseDto>> listLifecycles() {
        List<LifecycleResponseDto> response = lifecycleService.listLifecycles();
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "교회별 생애주기 목록 조회", description = "특정 교회의 활성화된 생애주기 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @PostMapping(ApiEndpoints.Lifecycle.LIST_BY_CHURCH)
    public ResponseEntity<List<LifecycleResponseDto>> listLifecyclesByChurch(@Valid @RequestBody LifecycleListByChurchDto dto) {
        List<LifecycleResponseDto> response = lifecycleService.listLifecyclesByChurch(dto.getChurchId());
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "생애주기 정보 수정", description = "생애주기 정보를 수정합니다. null 값은 기존 값을 유지합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(schema = @Schema(implementation = LifecycleResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "해당 생애주기를 찾을 수 없음",
                    content = @Content)
    })
    @PostMapping(ApiEndpoints.Lifecycle.UPDATE)
    public ResponseEntity<LifecycleResponseDto> updateLifecycle(@Valid @RequestBody LifecycleUpdateDto dto) {
        LifecycleResponseDto response = lifecycleService.updateLifecycle(dto);
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "생애주기 삭제", description = "생애주기를 논리 삭제합니다. (isActive = false)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "400", description = "해당 생애주기를 찾을 수 없음",
                    content = @Content)
    })
    @PostMapping(ApiEndpoints.Lifecycle.DELETE)
    public ResponseEntity<Void> deleteLifecycle(@Valid @RequestBody LifecycleDeleteDto dto) {
        lifecycleService.deleteLifecycle(dto.getLifecycleId());
        return ResponseEntity.noContent().build();
    }
}

