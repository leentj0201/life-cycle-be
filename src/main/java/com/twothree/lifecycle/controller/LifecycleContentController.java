package com.twothree.lifecycle.controller;

import com.twothree.lifecycle.constant.ApiEndpoints;
import com.twothree.lifecycle.dto.lifecyclecontent.*;
import com.twothree.lifecycle.service.LifecycleContentService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping(ApiEndpoints.LifecycleContent.BASE)
@RequiredArgsConstructor
@Tag(name = "LifecycleContent", description = "생애주기 콘텐츠 관리 API")
public class LifecycleContentController {
    
    private final LifecycleContentService lifecycleContentService;
    
    @Operation(summary = "생애주기 콘텐츠 생성")
    @PostMapping(ApiEndpoints.LifecycleContent.CREATE)
    public ResponseEntity<LifecycleContentResponseDto> create(@Valid @RequestBody LifecycleContentCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lifecycleContentService.createLifecycleContent(dto));
    }
    
    @Operation(summary = "생애주기 콘텐츠 조회")
    @PostMapping(ApiEndpoints.LifecycleContent.GET)
    public ResponseEntity<LifecycleContentResponseDto> get(@Valid @RequestBody LifecycleContentGetDto dto) {
        return ResponseEntity.ok(lifecycleContentService.getLifecycleContent(dto.getLifecycleContentId()));
    }
    
    @Operation(summary = "생애주기 콘텐츠 목록 조회")
    @PostMapping(ApiEndpoints.LifecycleContent.LIST)
    public ResponseEntity<List<LifecycleContentResponseDto>> list() {
        return ResponseEntity.ok(lifecycleContentService.listLifecycleContents());
    }
    
    @Operation(summary = "생애주기별 콘텐츠 목록 조회")
    @PostMapping(ApiEndpoints.LifecycleContent.LIST_BY_LIFECYCLE)
    public ResponseEntity<List<LifecycleContentResponseDto>> listByLifecycle(@Valid @RequestBody LifecycleContentListByLifecycleDto dto) {
        return ResponseEntity.ok(lifecycleContentService.listLifecycleContentsByLifecycle(dto.getLifecycleId()));
    }
    
    @Operation(summary = "생애주기 콘텐츠 수정")
    @PostMapping(ApiEndpoints.LifecycleContent.UPDATE)
    public ResponseEntity<LifecycleContentResponseDto> update(@Valid @RequestBody LifecycleContentUpdateDto dto) {
        return ResponseEntity.ok(lifecycleContentService.updateLifecycleContent(dto));
    }
    
    @Operation(summary = "생애주기 콘텐츠 삭제")
    @PostMapping(ApiEndpoints.LifecycleContent.DELETE)
    public ResponseEntity<Void> delete(@Valid @RequestBody LifecycleContentDeleteDto dto) {
        lifecycleContentService.deleteLifecycleContent(dto.getLifecycleContentId());
        return ResponseEntity.noContent().build();
    }
}

