package com.twothree.backend.controller;

import com.twothree.backend.constant.ApiEndpoints;
import com.twothree.backend.dto.memberlifecycle.*;
import com.twothree.backend.service.MemberLifecycleService;
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
@RequestMapping(ApiEndpoints.MemberLifecycle.BASE)
@RequiredArgsConstructor
@Tag(name = "MemberLifecycle", description = "회원-생애주기 매핑 관리 API")
public class MemberLifecycleController {
    
    private final MemberLifecycleService memberLifecycleService;
    
    @Operation(summary = "회원-생애주기 매핑 생성")
    @PostMapping(ApiEndpoints.MemberLifecycle.CREATE)
    public ResponseEntity<MemberLifecycleResponseDto> create(@Valid @RequestBody MemberLifecycleCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberLifecycleService.createMemberLifecycle(dto));
    }
    
    @Operation(summary = "회원-생애주기 매핑 조회")
    @PostMapping(ApiEndpoints.MemberLifecycle.GET)
    public ResponseEntity<MemberLifecycleResponseDto> get(@Valid @RequestBody MemberLifecycleGetDto dto) {
        return ResponseEntity.ok(memberLifecycleService.getMemberLifecycle(dto.getMemberLifecycleId()));
    }
    
    @Operation(summary = "회원-생애주기 매핑 목록 조회")
    @PostMapping(ApiEndpoints.MemberLifecycle.LIST)
    public ResponseEntity<List<MemberLifecycleResponseDto>> list() {
        return ResponseEntity.ok(memberLifecycleService.listMemberLifecycles());
    }
    
    @Operation(summary = "회원별 생애주기 목록 조회")
    @PostMapping(ApiEndpoints.MemberLifecycle.LIST_BY_MEMBER)
    public ResponseEntity<List<MemberLifecycleResponseDto>> listByMember(@Valid @RequestBody MemberLifecycleListByMemberDto dto) {
        return ResponseEntity.ok(memberLifecycleService.listMemberLifecyclesByMember(dto.getMemberId()));
    }
    
    @Operation(summary = "생애주기별 회원 목록 조회")
    @PostMapping(ApiEndpoints.MemberLifecycle.LIST_BY_LIFECYCLE)
    public ResponseEntity<List<MemberLifecycleResponseDto>> listByLifecycle(@Valid @RequestBody MemberLifecycleListByLifecycleDto dto) {
        return ResponseEntity.ok(memberLifecycleService.listMemberLifecyclesByLifecycle(dto.getLifecycleId()));
    }
    
    @Operation(summary = "회원-생애주기 매핑 수정")
    @PostMapping(ApiEndpoints.MemberLifecycle.UPDATE)
    public ResponseEntity<MemberLifecycleResponseDto> update(@Valid @RequestBody MemberLifecycleUpdateDto dto) {
        return ResponseEntity.ok(memberLifecycleService.updateMemberLifecycle(dto));
    }
    
    @Operation(summary = "회원-생애주기 매핑 삭제")
    @PostMapping(ApiEndpoints.MemberLifecycle.DELETE)
    public ResponseEntity<Void> delete(@Valid @RequestBody MemberLifecycleDeleteDto dto) {
        memberLifecycleService.deleteMemberLifecycle(dto.getMemberLifecycleId());
        return ResponseEntity.noContent().build();
    }
}

