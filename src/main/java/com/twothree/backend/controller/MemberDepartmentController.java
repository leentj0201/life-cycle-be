package com.twothree.backend.controller;

import com.twothree.backend.constant.ApiEndpoints;
import com.twothree.backend.dto.memberdepartment.*;
import com.twothree.backend.service.MemberDepartmentService;
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
@RequestMapping(ApiEndpoints.MemberDepartment.BASE)
@RequiredArgsConstructor
@Tag(name = "MemberDepartment", description = "회원-부서 매핑 관리 API")
public class MemberDepartmentController {
    
    private final MemberDepartmentService memberDepartmentService;
    
    @Operation(summary = "회원-부서 매핑 생성")
    @PostMapping(ApiEndpoints.MemberDepartment.CREATE)
    public ResponseEntity<MemberDepartmentResponseDto> create(@Valid @RequestBody MemberDepartmentCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberDepartmentService.createMemberDepartment(dto));
    }
    
    @Operation(summary = "회원-부서 매핑 조회")
    @PostMapping(ApiEndpoints.MemberDepartment.GET)
    public ResponseEntity<MemberDepartmentResponseDto> get(@Valid @RequestBody MemberDepartmentGetDto dto) {
        return ResponseEntity.ok(memberDepartmentService.getMemberDepartment(dto.getMemberDepartmentId()));
    }
    
    @Operation(summary = "회원-부서 매핑 목록 조회")
    @PostMapping(ApiEndpoints.MemberDepartment.LIST)
    public ResponseEntity<List<MemberDepartmentResponseDto>> list() {
        return ResponseEntity.ok(memberDepartmentService.listMemberDepartments());
    }
    
    @Operation(summary = "회원별 부서 목록 조회")
    @PostMapping(ApiEndpoints.MemberDepartment.LIST_BY_MEMBER)
    public ResponseEntity<List<MemberDepartmentResponseDto>> listByMember(@Valid @RequestBody MemberDepartmentListByMemberDto dto) {
        return ResponseEntity.ok(memberDepartmentService.listMemberDepartmentsByMember(dto.getMemberId()));
    }
    
    @Operation(summary = "부서별 회원 목록 조회")
    @PostMapping(ApiEndpoints.MemberDepartment.LIST_BY_DEPARTMENT)
    public ResponseEntity<List<MemberDepartmentResponseDto>> listByDepartment(@Valid @RequestBody MemberDepartmentListByDepartmentDto dto) {
        return ResponseEntity.ok(memberDepartmentService.listMemberDepartmentsByDepartment(dto.getDepartmentId()));
    }
    
    @Operation(summary = "회원-부서 매핑 수정")
    @PostMapping(ApiEndpoints.MemberDepartment.UPDATE)
    public ResponseEntity<MemberDepartmentResponseDto> update(@Valid @RequestBody MemberDepartmentUpdateDto dto) {
        return ResponseEntity.ok(memberDepartmentService.updateMemberDepartment(dto));
    }
    
    @Operation(summary = "회원-부서 매핑 삭제")
    @PostMapping(ApiEndpoints.MemberDepartment.DELETE)
    public ResponseEntity<Void> delete(@Valid @RequestBody MemberDepartmentDeleteDto dto) {
        memberDepartmentService.deleteMemberDepartment(dto.getMemberDepartmentId());
        return ResponseEntity.noContent().build();
    }
}

