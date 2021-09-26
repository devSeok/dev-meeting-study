package study.devmeetingstudy.controller;


import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    @ApiImplicitParam(name = "files", value = "이미지 파일 배열, 하나만 넣어주세요")
    @ApiResponses({
            @ApiResponse(code = 201, message = "생성됨"),
            @ApiResponse(code = 400, message = "잘못된 요청")
    })
    public ResponseEntity<ApiResDto<? extends CreatedStudyResDto>> saveStudy(@RequestPart StudySaveReqDto studySaveReqDto,
                                                                             @RequestPart @NotNull List<MultipartFile> files,
                                                                             @ApiIgnore @JwtMember MemberResolverDto memberResolverDto) throws IOException {
        Study createdStudy = studyService.saveStudy(StudyVO.of(studySaveReqDto, uploader.uploadFiles(files, DomainType.STUDY.value())), memberResolverDto);
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
