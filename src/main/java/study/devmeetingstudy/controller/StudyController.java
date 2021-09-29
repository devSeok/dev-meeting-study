package study.devmeetingstudy.controller;


import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import study.devmeetingstudy.annotation.JwtMember;
import study.devmeetingstudy.annotation.dto.MemberResolverDto;
import study.devmeetingstudy.common.exception.global.response.ApiResDto;
import study.devmeetingstudy.common.uploader.Uploader;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.enums.DomainType;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.domain.study.StudyFile;
import study.devmeetingstudy.dto.study.CreatedOfflineStudyResDto;
import study.devmeetingstudy.dto.study.CreatedOnlineStudyResDto;
import study.devmeetingstudy.dto.study.CreatedStudyResDto;
import study.devmeetingstudy.dto.study.StudyVO;
import study.devmeetingstudy.dto.study.request.StudySaveReqDto;
import study.devmeetingstudy.service.*;

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

    private final StudyService studyService;
    private final Uploader uploader;
    private final MemberService memberService;
    private final SubjectService subjectService;
    private final StudyFileService studyFileService;
    private final StudyMemberService studyMemberService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "스터디 저장", notes = "온라인일시 link(String), onlineType(String)가 추가되고, 오프라인일시 Address(Object)가 추가됩니다. ")
    @ApiImplicitParam(name = "file", value = "이미지 파일, 하나만 넣어주세요")
    @ApiResponses({
            @ApiResponse(code = 201, message = "스터디 생성됨"),
            @ApiResponse(code = 400, message = "잘못된 요청")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ApiResDto<? extends CreatedStudyResDto>> saveStudy(@Valid @ModelAttribute StudySaveReqDto studySaveReqDto,
                                                                             @RequestPart("file") MultipartFile file,
                                                                             @ApiIgnore @JwtMember MemberResolverDto memberResolverDto) throws IOException {
        Member loginMember = memberService.getUserOne(memberResolverDto.getId());

        Map<String, String> uploadFileInfo = uploader.upload(file, DomainType.STUDY.value());
        Subject subject = subjectService.findSubject(studySaveReqDto.getSubjectId());
        Study createdStudy = studyService.saveStudy(StudyVO.of(studySaveReqDto, subject));
        StudyFile studyFile = studyFileService.saveStudyFile(createdStudy, uploadFileInfo);
        studyMemberService.saveStudyLeader(loginMember, createdStudy);

        CreatedStudyResDto createdStudyResDto = CreatedStudyResDto.of(createdStudy, studyFile);
        if (CreatedStudyResDto.isInstanceOnlineResDto(createdStudyResDto)){
            return ResponseEntity.created(URI.create("/api/studies/" + createdStudy.getId()))
                    .body(
                            ApiResDto.<CreatedOnlineStudyResDto>builder()
                                    .message("생성됨")
                                    .status(HttpStatus.CREATED.value())
                                    .data((CreatedOnlineStudyResDto) createdStudyResDto)
                                    .build()
                    );
        }
        return ResponseEntity.created(URI.create("/api/studies/" + createdStudy.getId()))
                .body(
                        ApiResDto.<CreatedOfflineStudyResDto>builder()
                                .message("생성됨")
                                .status(HttpStatus.CREATED.value())
                                .data((CreatedOfflineStudyResDto) createdStudyResDto)
                                .build()
                );
    }


}
