package com.twothree.lifecycle.controller;

import com.twothree.lifecycle.constant.ApiEndpoints;
import com.twothree.lifecycle.dto.membersetting.*;
import com.twothree.lifecycle.service.MemberSettingService;
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
@RequestMapping(ApiEndpoints.MemberSetting.BASE)
@RequiredArgsConstructor
@Tag(name = "MemberSetting", description = "회원 설정 관리 API")
public class MemberSettingController {
    
    private final MemberSettingService memberSettingService;
    
    @Operation(summary = "회원 설정 생성")
    @PostMapping(ApiEndpoints.MemberSetting.CREATE)
    public ResponseEntity<MemberSettingResponseDto> create(@Valid @RequestBody MemberSettingCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberSettingService.createMemberSetting(dto));
    }
    
    @Operation(summary = "회원 설정 조회")
    @PostMapping(ApiEndpoints.MemberSetting.GET)
    public ResponseEntity<MemberSettingResponseDto> get(@Valid @RequestBody MemberSettingGetDto dto) {
        return ResponseEntity.ok(memberSettingService.getMemberSetting(dto.getMemberSettingId()));
    }
    
    @Operation(summary = "회원별 설정 조회")
    @PostMapping(ApiEndpoints.MemberSetting.GET_BY_MEMBER)
    public ResponseEntity<MemberSettingResponseDto> getByMember(@Valid @RequestBody MemberSettingGetByMemberDto dto) {
        return ResponseEntity.ok(memberSettingService.getMemberSettingByMember(dto.getMemberId()));
    }
    
    @Operation(summary = "회원 설정 목록 조회")
    @PostMapping(ApiEndpoints.MemberSetting.LIST)
    public ResponseEntity<List<MemberSettingResponseDto>> list() {
        return ResponseEntity.ok(memberSettingService.listMemberSettings());
    }
    
    @Operation(summary = "회원 설정 수정")
    @PostMapping(ApiEndpoints.MemberSetting.UPDATE)
    public ResponseEntity<MemberSettingResponseDto> update(@Valid @RequestBody MemberSettingUpdateDto dto) {
        return ResponseEntity.ok(memberSettingService.updateMemberSetting(dto));
    }
    
    @Operation(summary = "회원 설정 삭제")
    @PostMapping(ApiEndpoints.MemberSetting.DELETE)
    public ResponseEntity<Void> delete(@Valid @RequestBody MemberSettingDeleteDto dto) {
        memberSettingService.deleteMemberSetting(dto.getMemberSettingId());
        return ResponseEntity.noContent().build();
    }
}

