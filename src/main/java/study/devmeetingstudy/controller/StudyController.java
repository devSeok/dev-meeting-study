package study.devmeetingstudy.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import study.devmeetingstudy.annotation.JwtMember;
import study.devmeetingstudy.annotation.dto.MemberResolverDto;
import study.devmeetingstudy.common.exception.global.response.ApiResponseDto;
import study.devmeetingstudy.common.uploader.Uploader;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.dto.study.StudyResponseDto;
import study.devmeetingstudy.dto.study.request.StudySaveRequestDto;
import study.devmeetingstudy.service.StudyService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Api(tags = {"3. Study"})
@RestController
@RequestMapping("/api/studies")
@RequiredArgsConstructor
@Slf4j
public class StudyController {

    private final StudyService studyService;
    private final Uploader uploader;

    //@RequestBody StudySaveRequestDto studySaveRequestDto,
    @PostMapping
    @ApiResponses({
            @ApiResponse(code = 201, message = "생성됨")
    })
    public ResponseEntity<ApiResponseDto<StudyResponseDto>> saveStudy(@RequestParam("files") MultipartFile files,
                                                                      @ApiIgnore @JwtMember MemberResolverDto memberResolverDto) throws IOException {
//        for (MultipartFile file : files){
//            String aStatic = uploader.upload(file, "static");
//            System.out.println(aStatic);
//        }
        String aStatic = uploader.upload(files, "static");
        System.out.println(aStatic);
//        Study study = studyService.saveStudy(studySaveRequestDto, resolverDto);
        return null;
    }
}
