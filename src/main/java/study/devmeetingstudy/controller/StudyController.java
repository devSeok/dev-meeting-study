package study.devmeetingstudy.controller;


import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import study.devmeetingstudy.annotation.JwtMember;
import study.devmeetingstudy.annotation.dto.MemberResolverDto;
import study.devmeetingstudy.common.exception.global.response.ApiResDto;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.dto.study.request.StudyPutReqDto;
import study.devmeetingstudy.dto.study.CreatedStudyDto;
import study.devmeetingstudy.dto.study.StudyDto;
import study.devmeetingstudy.dto.study.request.StudySaveReqDto;
import study.devmeetingstudy.dto.study.request.StudySearchCondition;
import study.devmeetingstudy.dto.study.response.*;
import study.devmeetingstudy.service.*;
import study.devmeetingstudy.service.study.*;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/* TODO : file이 null이라면 나중에 해당 주제에 맞는 이미지를 넣어주는걸로 바꾸기.
 *        enum valid 처리하기 (임시방편으로 체크중)
 */
@Api(tags = {"3. Study"})
@RestController
@RequestMapping("/api/studies")
@RequiredArgsConstructor
@Slf4j
public class StudyController {

    private final MemberService memberService;
    private final StudyFacadeServiceImpl studyFacadeService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "스터디 저장", notes = "온라인일시 link(String), onlineType(String)가 추가되고, 오프라인일시 Address(Object)가 추가됩니다. ")
    @ApiImplicitParam(name = "file", value = "이미지 파일, 하나만 넣어주세요")
    @ApiResponses({
            @ApiResponse(code = 201, message = "스터디 생성됨"),
            @ApiResponse(code = 400, message = "잘못된 요청")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ApiResDto<? extends CreatedStudyResDto>> saveStudy(@Valid @ModelAttribute StudySaveReqDto studySaveReqDto,
                                                                             @ApiIgnore @JwtMember MemberResolverDto memberResolverDto) throws IOException {
        log.info("StudyController.saveStudy");
        Member loginMember = memberService.getUserOne(memberResolverDto.getId());
        CreatedStudyDto createdStudyDto = studyFacadeService.storeStudy(studySaveReqDto, loginMember);
        return getCreatedApiResDto(createdStudyDto);
    }

    private ResponseEntity<ApiResDto<? extends CreatedStudyResDto>> getCreatedApiResDto(CreatedStudyDto createdStudyDto) {
        if (createdStudyDto.getStudy().isDtypeOnline()) {
            return ResponseEntity.created(URI.create("/api/studies/" + createdStudyDto.getStudy().getId()))
                    .body(
                            ApiResDto.<CreatedOnlineStudyResDto>builder()
                                    .message("생성됨")
                                    .status(HttpStatus.CREATED.value())
                                    .data(CreatedOnlineStudyResDto.from(createdStudyDto))
                                    .build()
                    );
        }

        return ResponseEntity.created(URI.create("/api/studies/" + createdStudyDto.getStudy().getId()))
                .body(
                        ApiResDto.<CreatedOfflineStudyResDto>builder()
                                .message("생성됨")
                                .status(HttpStatus.CREATED.value())
                                .data(CreatedOfflineStudyResDto.from(createdStudyDto))
                                .build()
                );
    }

    @GetMapping
    @ApiOperation(value = "스터디 목록", notes = "파라미터 nullable 이므로, 넣어줘도 되고 안넣어줘도 됨. 대신 필터 정보가 변경되었으면 반드시 재요청 후 lastId만 변경돼 요청 할 수 있도록 하여아함.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "스터디 목록 조회 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청")
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ApiResDto<List<FoundStudiesResDto>>> getStudies(@Valid @ModelAttribute StudySearchCondition studySearchCondition) throws IOException {
        log.info("StudyController.getStudies");
        List<StudyDto> studies = studyFacadeService.findStudiesBySearchCondition(studySearchCondition);
        return ResponseEntity.ok(
                ApiResDto.<List<FoundStudiesResDto>>builder()
                        .message("성공")
                        .status(HttpStatus.OK.value())
                        .data(studies.stream().map(FoundStudiesResDto::from).collect(Collectors.toList()))
                        .build()
        );
    }

    @GetMapping("/my")
    @ApiOperation(value = "내 스터디 목록", notes = "로그인 한 멤버의 현재 가입한 스터디 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "내 스터디 목록 조회 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청")
    })
    public ResponseEntity<ApiResDto<List<FoundStudiesResDto>>> getMyStudies(@ApiIgnore @JwtMember MemberResolverDto memberResolverDto) {
        log.info("StudyController.getMyStudies");
        List<StudyDto> studies = studyFacadeService.findStudiesByMemberId(memberResolverDto.getId());
        return ResponseEntity.ok(
                ApiResDto.<List<FoundStudiesResDto>>builder()
                        .message("성공")
                        .status(HttpStatus.OK.value())
                        .data(studies.stream().map(FoundStudiesResDto::from).collect(Collectors.toList()))
                        .build()
        );
    }

    @GetMapping("/{studyId}")
    @ApiOperation(value = "스터디 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "스터디 조회 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청")
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ApiResDto<FoundStudyResDto>> getStudy(@PathVariable Long studyId) {
        log.info("StudyController.getStudy");
        StudyDto studyDto = studyFacadeService.findStudyByStudyId(studyId);
        return ResponseEntity.ok(
                ApiResDto.<FoundStudyResDto>builder()
                        .message("성공")
                        .status(HttpStatus.OK.value())
                        .data(FoundStudyResDto.from(studyDto))
                        .build()
        );
    }

    @PutMapping("/{studyId}")
    @ApiOperation(value = "스터디 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "스터디 수정 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 403, message = "리소스 권한 없음")
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ApiResDto<? extends CreatedStudyResDto>> putStudy(@PathVariable Long studyId,
                                                                            @Valid @ModelAttribute StudyPutReqDto studyPutReqDto,
                                                                            @ApiIgnore @JwtMember MemberResolverDto memberResolverDto) throws IOException {
        log.info("StudyController.putStudy");
        studyPutReqDto.setStudyId(studyId);
        return getOkApiResDto(studyFacadeService.replaceStudy(studyPutReqDto, memberResolverDto));
    }

    private ResponseEntity<ApiResDto<? extends CreatedStudyResDto>> getOkApiResDto(CreatedStudyDto replaceStudy) {
        if (replaceStudy.getStudy().isDtypeOnline()) {
            return ResponseEntity.ok(
                    ApiResDto.<CreatedOnlineStudyResDto>builder()
                            .message("성공")
                            .status(HttpStatus.OK.value())
                            .data(CreatedOnlineStudyResDto.from(replaceStudy))
                            .build()
            );
        }

        return ResponseEntity.ok(
                ApiResDto.<CreatedOfflineStudyResDto>builder()
                        .message("성공")
                        .status(HttpStatus.OK.value())
                        .data(CreatedOfflineStudyResDto.from(replaceStudy))
                        .build()
        );
    }

    @DeleteMapping("/{studyId}")
    @ApiOperation(value = "스터디 삭제")
    @ApiResponses({
            @ApiResponse(code = 204, message = "스터디 삭제 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 403, message = "리소스 권한 없음")
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteStudy(@PathVariable Long studyId,
                                            @JwtMember MemberResolverDto memberResolverDto) {
        log.info("StudyController.deleteStudy");
        studyFacadeService.deleteStudy(studyId, memberResolverDto);
        return ResponseEntity.noContent().build();
    }
}

