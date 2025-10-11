package com.twothree.backend.controller;

import com.twothree.backend.constant.ApiEndpoints;
import com.twothree.backend.dto.department.DepartmentCreateDto;
import com.twothree.backend.dto.department.DepartmentDeleteDto;
import com.twothree.backend.dto.department.DepartmentGetDto;
import com.twothree.backend.dto.department.DepartmentListByChurchDto;
import com.twothree.backend.dto.department.DepartmentResponseDto;
import com.twothree.backend.dto.department.DepartmentUpdateDto;
import com.twothree.backend.service.DepartmentService;
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
@RequestMapping(ApiEndpoints.Department.BASE)
@RequiredArgsConstructor
@Tag(name = "Department", description = "부서 관리 API")
public class DepartmentController {
    
    private final DepartmentService departmentService;
    
    @Operation(summary = "부서 생성", description = "새로운 부서를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "부서 생성 성공",
                    content = @Content(schema = @Schema(implementation = DepartmentResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터",
                    content = @Content)
    })
    @PostMapping(ApiEndpoints.Department.CREATE)
    public ResponseEntity<DepartmentResponseDto> createDepartment(@Valid @RequestBody DepartmentCreateDto dto) {
        DepartmentResponseDto response = departmentService.createDepartment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @Operation(summary = "부서 조회", description = "부서 ID로 부서 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = DepartmentResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "해당 부서를 찾을 수 없음",
                    content = @Content)
    })
    @PostMapping(ApiEndpoints.Department.GET)
    public ResponseEntity<DepartmentResponseDto> getDepartment(@Valid @RequestBody DepartmentGetDto dto) {
        DepartmentResponseDto response = departmentService.getDepartment(dto.getDepartmentId());
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "부서 목록 조회", description = "활성화된 모든 부서 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @PostMapping(ApiEndpoints.Department.LIST)
    public ResponseEntity<List<DepartmentResponseDto>> listDepartments() {
        List<DepartmentResponseDto> response = departmentService.listDepartments();
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "교회별 부서 목록 조회", description = "특정 교회의 활성화된 부서 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @PostMapping(ApiEndpoints.Department.LIST_BY_CHURCH)
    public ResponseEntity<List<DepartmentResponseDto>> listDepartmentsByChurch(@Valid @RequestBody DepartmentListByChurchDto dto) {
        List<DepartmentResponseDto> response = departmentService.listDepartmentsByChurch(dto.getChurchId());
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "부서 정보 수정", description = "부서 정보를 수정합니다. null 값은 기존 값을 유지합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(schema = @Schema(implementation = DepartmentResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "해당 부서를 찾을 수 없음",
                    content = @Content)
    })
    @PostMapping(ApiEndpoints.Department.UPDATE)
    public ResponseEntity<DepartmentResponseDto> updateDepartment(@Valid @RequestBody DepartmentUpdateDto dto) {
        DepartmentResponseDto response = departmentService.updateDepartment(dto);
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "부서 삭제", description = "부서를 논리 삭제합니다. (isActive = false)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "400", description = "해당 부서를 찾을 수 없음",
                    content = @Content)
    })
    @PostMapping(ApiEndpoints.Department.DELETE)
    public ResponseEntity<Void> deleteDepartment(@Valid @RequestBody DepartmentDeleteDto dto) {
        departmentService.deleteDepartment(dto.getDepartmentId());
        return ResponseEntity.noContent().build();
    }
}

