package study.devmeetingstudy.controller;


import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import study.devmeetingstudy.annotation.JwtMember;
import study.devmeetingstudy.annotation.dto.MemberResolverDto;
import study.devmeetingstudy.common.exception.global.response.ApiResDto;
import study.devmeetingstudy.common.uploader.Uploader;
import study.devmeetingstudy.domain.enums.DomainType;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.domain.study.StudyFile;
import study.devmeetingstudy.dto.study.CreatedStudyDto;
import study.devmeetingstudy.dto.study.request.StudySaveReqDto;
import study.devmeetingstudy.dto.study.request.StudySearchCondition;
import study.devmeetingstudy.dto.study.response.CreatedOfflineStudyResDto;
import study.devmeetingstudy.dto.study.response.CreatedOnlineStudyResDto;
import study.devmeetingstudy.dto.study.response.CreatedStudyResDto;
import study.devmeetingstudy.service.*;
import study.devmeetingstudy.service.study.*;
import study.devmeetingstudy.vo.StudyVO;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

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
    private final StudyFacadeService studyFacadeService;

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

        CreatedStudyDto createdStudyDto = studyFacadeService.store(studySaveReqDto, loginMember);

        if (Study.isDtypeOnline(createdStudyDto.getStudy())) {
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
    @ApiOperation(value = "스터디 저장", notes = "온라인일시 link(String), onlineType(String)가 추가되고, 오프라인일시 Address(Object)가 추가됩니다. ")
    @ApiImplicitParam(name = "file", value = "이미지 파일, 하나만 넣어주세요")
    @ApiResponses({
            @ApiResponse(code = 200, message = "스터디 목록 조회 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청")
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ApiResDto<? extends CreatedStudyResDto>> getStudies(@ModelAttribute StudySearchCondition studySearchCondition,
                                                                              Pageable pageable) throws IOException {
        log.info("StudyController.getStudies");
        return null;
    }

    @GetMapping("/{studyId}")
    @ApiOperation(value = "스터디 저장", notes = "온라인일시 link(String), onlineType(String)가 추가되고, 오프라인일시 Address(Object)가 추가됩니다. ")
    @ApiImplicitParam(name = "file", value = "이미지 파일, 하나만 넣어주세요")
    @ApiResponses({
            @ApiResponse(code = 200, message = "스터디 조회 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ApiResDto<? extends CreatedStudyResDto>> getStudy(@PathVariable Long studyId) throws IOException {
        log.info("StudyController.getStudy");
        return null;
    }


}
