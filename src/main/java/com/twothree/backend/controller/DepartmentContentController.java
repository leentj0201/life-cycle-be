package com.twothree.backend.controller;

import com.twothree.backend.constant.ApiEndpoints;
import com.twothree.backend.dto.departmentcontent.*;
import com.twothree.backend.service.DepartmentContentService;
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
@RequestMapping(ApiEndpoints.DepartmentContent.BASE)
@RequiredArgsConstructor
@Tag(name = "DepartmentContent", description = "부서 콘텐츠 관리 API")
public class DepartmentContentController {
    
    private final DepartmentContentService departmentContentService;
    
    @Operation(summary = "부서 콘텐츠 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "생성 성공",
                    content = @Content(schema = @Schema(implementation = DepartmentContentResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping(ApiEndpoints.DepartmentContent.CREATE)
    public ResponseEntity<DepartmentContentResponseDto> create(@Valid @RequestBody DepartmentContentCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentContentService.createDepartmentContent(dto));
    }
    
    @Operation(summary = "부서 콘텐츠 조회")
    @PostMapping(ApiEndpoints.DepartmentContent.GET)
    public ResponseEntity<DepartmentContentResponseDto> get(@Valid @RequestBody DepartmentContentGetDto dto) {
        return ResponseEntity.ok(departmentContentService.getDepartmentContent(dto.getDepartmentContentId()));
    }
    
    @Operation(summary = "부서 콘텐츠 목록 조회")
    @PostMapping(ApiEndpoints.DepartmentContent.LIST)
    public ResponseEntity<List<DepartmentContentResponseDto>> list() {
        return ResponseEntity.ok(departmentContentService.listDepartmentContents());
    }
    
    @Operation(summary = "부서별 콘텐츠 목록 조회")
    @PostMapping(ApiEndpoints.DepartmentContent.LIST_BY_DEPARTMENT)
    public ResponseEntity<List<DepartmentContentResponseDto>> listByDepartment(@Valid @RequestBody DepartmentContentListByDepartmentDto dto) {
        return ResponseEntity.ok(departmentContentService.listDepartmentContentsByDepartment(dto.getDepartmentId()));
    }
    
    @Operation(summary = "부서 콘텐츠 수정")
    @PostMapping(ApiEndpoints.DepartmentContent.UPDATE)
    public ResponseEntity<DepartmentContentResponseDto> update(@Valid @RequestBody DepartmentContentUpdateDto dto) {
        return ResponseEntity.ok(departmentContentService.updateDepartmentContent(dto));
    }
    
    @Operation(summary = "부서 콘텐츠 삭제")
    @PostMapping(ApiEndpoints.DepartmentContent.DELETE)
    public ResponseEntity<Void> delete(@Valid @RequestBody DepartmentContentDeleteDto dto) {
        departmentContentService.deleteDepartmentContent(dto.getDepartmentContentId());
        return ResponseEntity.noContent().build();
    }
}

