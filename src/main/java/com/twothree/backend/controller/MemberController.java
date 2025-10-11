package com.twothree.backend.controller;

import com.twothree.backend.constant.ApiEndpoints;
import com.twothree.backend.dto.member.MemberCreateDto;
import com.twothree.backend.dto.member.MemberDeleteDto;
import com.twothree.backend.dto.member.MemberGetDto;
import com.twothree.backend.dto.member.MemberListByChurchDto;
import com.twothree.backend.dto.member.MemberResponseDto;
import com.twothree.backend.dto.member.MemberUpdateDto;
import com.twothree.backend.service.MemberService;
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
@RequestMapping(ApiEndpoints.Member.BASE)
@RequiredArgsConstructor
@Tag(name = "Member", description = "회원 관리 API")
public class MemberController {
    
    private final MemberService memberService;
    
    @Operation(summary = "회원 생성", description = "새로운 회원을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원 생성 성공",
                    content = @Content(schema = @Schema(implementation = MemberResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터",
                    content = @Content)
    })
    @PostMapping(ApiEndpoints.Member.CREATE)
    public ResponseEntity<MemberResponseDto> createMember(@Valid @RequestBody MemberCreateDto dto) {
        MemberResponseDto response = memberService.createMember(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @Operation(summary = "회원 조회", description = "회원 ID로 회원 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = MemberResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "해당 회원을 찾을 수 없음",
                    content = @Content)
    })
    @PostMapping(ApiEndpoints.Member.GET)
    public ResponseEntity<MemberResponseDto> getMember(@Valid @RequestBody MemberGetDto dto) {
        MemberResponseDto response = memberService.getMember(dto.getMemberId());
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "회원 목록 조회", description = "활성화된 모든 회원 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @PostMapping(ApiEndpoints.Member.LIST)
    public ResponseEntity<List<MemberResponseDto>> listMembers() {
        List<MemberResponseDto> response = memberService.listMembers();
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "교회별 회원 목록 조회", description = "특정 교회의 활성화된 회원 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @PostMapping(ApiEndpoints.Member.LIST_BY_CHURCH)
    public ResponseEntity<List<MemberResponseDto>> listMembersByChurch(@Valid @RequestBody MemberListByChurchDto dto) {
        List<MemberResponseDto> response = memberService.listMembersByChurch(dto.getChurchId());
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "회원 정보 수정", description = "회원 정보를 수정합니다. null 값은 기존 값을 유지합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(schema = @Schema(implementation = MemberResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "해당 회원을 찾을 수 없음",
                    content = @Content)
    })
    @PostMapping(ApiEndpoints.Member.UPDATE)
    public ResponseEntity<MemberResponseDto> updateMember(@Valid @RequestBody MemberUpdateDto dto) {
        MemberResponseDto response = memberService.updateMember(dto);
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "회원 삭제", description = "회원을 논리 삭제합니다. (isActive = false)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "400", description = "해당 회원을 찾을 수 없음",
                    content = @Content)
    })
    @PostMapping(ApiEndpoints.Member.DELETE)
    public ResponseEntity<Void> deleteMember(@Valid @RequestBody MemberDeleteDto dto) {
        memberService.deleteMember(dto.getMemberId());
        return ResponseEntity.noContent().build();
    }
}

