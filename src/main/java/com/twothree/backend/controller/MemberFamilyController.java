package com.twothree.backend.controller;

import com.twothree.backend.constant.ApiEndpoints;
import com.twothree.backend.dto.memberfamily.*;
import com.twothree.backend.service.MemberFamilyService;
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
@RequestMapping(ApiEndpoints.MemberFamily.BASE)
@RequiredArgsConstructor
@Tag(name = "MemberFamily", description = "가족 관계 관리 API")
public class MemberFamilyController {
    
    private final MemberFamilyService memberFamilyService;
    
    @Operation(summary = "가족 관계 생성")
    @PostMapping(ApiEndpoints.MemberFamily.CREATE)
    public ResponseEntity<MemberFamilyResponseDto> create(@Valid @RequestBody MemberFamilyCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberFamilyService.createMemberFamily(dto));
    }
    
    @Operation(summary = "가족 관계 조회")
    @PostMapping(ApiEndpoints.MemberFamily.GET)
    public ResponseEntity<MemberFamilyResponseDto> get(@Valid @RequestBody MemberFamilyGetDto dto) {
        return ResponseEntity.ok(memberFamilyService.getMemberFamily(dto.getMemberFamilyId()));
    }
    
    @Operation(summary = "가족 관계 목록 조회")
    @PostMapping(ApiEndpoints.MemberFamily.LIST)
    public ResponseEntity<List<MemberFamilyResponseDto>> list() {
        return ResponseEntity.ok(memberFamilyService.listMemberFamilies());
    }
    
    @Operation(summary = "회원별 가족 관계 목록 조회")
    @PostMapping(ApiEndpoints.MemberFamily.LIST_BY_MEMBER)
    public ResponseEntity<List<MemberFamilyResponseDto>> listByMember(@Valid @RequestBody MemberFamilyListByMemberDto dto) {
        return ResponseEntity.ok(memberFamilyService.listMemberFamiliesByMember(dto.getMemberId()));
    }
    
    @Operation(summary = "가족 관계 수정")
    @PostMapping(ApiEndpoints.MemberFamily.UPDATE)
    public ResponseEntity<MemberFamilyResponseDto> update(@Valid @RequestBody MemberFamilyUpdateDto dto) {
        return ResponseEntity.ok(memberFamilyService.updateMemberFamily(dto));
    }
    
    @Operation(summary = "가족 관계 삭제")
    @PostMapping(ApiEndpoints.MemberFamily.DELETE)
    public ResponseEntity<Void> delete(@Valid @RequestBody MemberFamilyDeleteDto dto) {
        memberFamilyService.deleteMemberFamily(dto.getMemberFamilyId());
        return ResponseEntity.noContent().build();
    }
}

