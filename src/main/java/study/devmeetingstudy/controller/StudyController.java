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
import study.devmeetingstudy.domain.enums.DomainType;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.dto.study.CreatedOfflineStudyResDto;
import study.devmeetingstudy.dto.study.CreatedOnlineStudyResDto;
import study.devmeetingstudy.dto.study.CreatedStudyResDto;
import study.devmeetingstudy.dto.study.StudyVO;
import study.devmeetingstudy.dto.study.request.StudySaveReqDto;
import study.devmeetingstudy.service.StudyService;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@Api(tags = {"3. Study"})
@RestController
@RequestMapping("/api/studies")
@RequiredArgsConstructor
@Slf4j
public class StudyController {

    private final StudyService studyService;
    private final Uploader uploader;

    @PostMapping(consumes = {MediaType.MULTIPART_MIXED_VALUE})
    @ApiOperation(value = "스터디 저장", notes = "온라인일시 link(String), onlineType(String)가 추가되고, 오프라인일시 Address(Object)가 추가됩니다. ")
    @ApiImplicitParam(name = "file", value = "이미지 파일, 하나만 넣어주세요")
    @ApiResponses({
            @ApiResponse(code = 201, message = "스터디 생성됨"),
            @ApiResponse(code = 400, message = "잘못된 요청")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ApiResDto<? extends CreatedStudyResDto>> saveStudy(@ModelAttribute StudySaveReqDto studySaveReqDto,
                                                                             @RequestPart MultipartFile file,
                                                                             @ApiIgnore @JwtMember MemberResolverDto memberResolverDto) throws IOException {
        Study createdStudy = studyService.saveStudy(StudyVO.of(studySaveReqDto, uploader.upload(file, DomainType.STUDY.value())), memberResolverDto);
        CreatedStudyResDto createdStudyResDto = CreatedStudyResDto.from(createdStudy);
        if (CreatedStudyResDto.isInstanceOnlineResDto(createdStudyResDto)){
            return ResponseEntity.created(URI.create("/api/studies/" + createdStudy.getId()))
                    .body(
                            ApiResDto.<CreatedOnlineStudyResDto>builder()
                                    .message("생성됨")
                                    .status(HttpStatus.CREATED.value())
                                    .data((CreatedOnlineStudyResDto) CreatedStudyResDto.from(createdStudy))
                                    .build()
                    );
        }
        return ResponseEntity.created(URI.create("/api/studies/" + createdStudy.getId()))
                .body(
                        ApiResDto.<CreatedOfflineStudyResDto>builder()
                                .message("생성됨")
                                .status(HttpStatus.CREATED.value())
                                .data((CreatedOfflineStudyResDto) CreatedStudyResDto.from(createdStudy))
                                .build()
                );
    }


}
