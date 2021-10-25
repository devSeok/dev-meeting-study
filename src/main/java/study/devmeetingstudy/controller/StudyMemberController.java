package study.devmeetingstudy.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import study.devmeetingstudy.annotation.JwtMember;
import study.devmeetingstudy.annotation.dto.MemberResolverDto;
import study.devmeetingstudy.common.exception.global.response.ApiResDto;
import study.devmeetingstudy.domain.study.StudyMember;
import study.devmeetingstudy.dto.study.response.StudyMemberResDto;
import study.devmeetingstudy.service.MemberService;
import study.devmeetingstudy.service.study.StudyMemberService;
import study.devmeetingstudy.service.study.StudyService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"6. StudyMember"})
@RestController
@RequestMapping("/api/studies/{studyId}")
@RequiredArgsConstructor
@Slf4j
public class StudyMemberController {

    private final StudyService studyService;
    private final StudyMemberService studyMemberService;
    private final MemberService memberService;

    @GetMapping("/study-members")
    @ApiOperation(value = "스터디 멤버 목록 조회", notes = "studyId에 해당하는 스터디 멤버 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "스터디 멤버 목록 조회 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 403, message = "리소스 접근 권한 없음"),
            @ApiResponse(code = 404, message = "리소스를 찾을 수 없음")
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ApiResDto<List<StudyMemberResDto>>> getStudyMembers(@PathVariable Long studyId,
                                                                              @ApiIgnore @JwtMember MemberResolverDto memberResolverDto) {
        log.info("StudyMemberController.getStudyMembers");
        studyMemberService.authenticateStudyMember(studyId, memberResolverDto);
        List<StudyMember> studyMembers = studyMemberService.findStudyMembersByStudyId(studyId);
        return ResponseEntity.ok(
                ApiResDto.<List<StudyMemberResDto>>builder()
                        .message("성공")
                        .status(HttpStatus.OK.value())
                        .data(studyMembers.stream().map(StudyMemberResDto::from).collect(Collectors.toList()))
                        .build()
        );
    }

    @GetMapping("/study-members/{studyMemberId}")
    @ApiOperation(value = "스터디 멤버 조회", notes = "studyId에 해당하는 스터디 멤버를 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "스터디 멤버 조회 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 403, message = "리소스 접근 권한 없음"),
            @ApiResponse(code = 404, message = "리소스를 찾을 수 없음")
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ApiResDto<StudyMemberResDto>> getStudyMember(@PathVariable Long studyId,
                                                                       @PathVariable Long studyMemberId,
                                                                       @ApiIgnore @JwtMember MemberResolverDto memberResolverDto) {
        studyMemberService.authenticateStudyMember(studyId, memberResolverDto);
        StudyMember studyMember = studyMemberService.findStudyMemberById(studyMemberId);
        return ResponseEntity.ok(
                ApiResDto.<StudyMemberResDto>builder()
                        .message("성공")
                        .status(HttpStatus.OK.value())
                        .data(StudyMemberResDto.from(studyMember))
                        .build()
        );
    }

    @PostMapping("/study-members")
    @ApiOperation(value = "스터디 멤버 신청", notes = "해당 스터디의 max member를 체크 후 스터디 멤버로 등록합니다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "스터디 멤버 생성 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 404, message = "리소스를 찾을 수 없음")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ApiResDto<StudyMemberResDto>> applyStudyMember(@PathVariable Long studyId,
                                                                         @ApiIgnore @JwtMember MemberResolverDto memberResolverDto) {
        StudyMember studyMember = studyMemberService.saveStudyMember(memberService.getUserOne(memberResolverDto.getId()), studyService.findStudyById(studyId));
        return ResponseEntity.created(URI.create("/api/studies" + studyId + "/study-members" + studyMember.getId()))
                .body(
                        ApiResDto.<StudyMemberResDto>builder()
                                .message("생성됨")
                                .status(HttpStatus.CREATED.value())
                                .data(StudyMemberResDto.from(studyMember))
                                .build()
                );
    }

    @DeleteMapping("/study-members/{studyMemberId}")
    @ApiOperation(value = "스터디 탈퇴", notes = "studyMemberId에 해당 하는 스터디 멤버를 OUT 처리 합니다.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "스터디 멤버 탈퇴 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 404, message = "리소스를 찾을 수 없음")
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteStudyMember(@PathVariable Long studyId,
                                                  @PathVariable Long studyMemberId,
                                                  @ApiIgnore @JwtMember MemberResolverDto memberResolverDto) {
        studyMemberService.authenticateStudyMember(studyId, memberResolverDto);
        studyMemberService.deleteStudyMember(studyMemberId, memberResolverDto);
        return ResponseEntity.noContent().build();
    }
}
