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
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.dto.study.StudyResponseDto;
import study.devmeetingstudy.dto.study.request.StudySaveRequestDto;
import study.devmeetingstudy.service.StudyService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Api(tags = {"3. Study"})
@RestController
@RequestMapping("/api/study")
@RequiredArgsConstructor
@Slf4j
public class StudyController {

    private final StudyService studyService;

    @PostMapping
    @ApiResponses({
            @ApiResponse(code = 201, message = "생성됨")
    })
    public ResponseEntity<ApiResponseDto<StudyResponseDto>> saveStudy(@RequestBody StudySaveRequestDto studySaveRequestDto,
                                                                      @ApiIgnore @JwtMember MemberResolverDto resolverDto,
                                                                      HttpServletRequest httpServletRequest){
        log.info(httpServletRequest.getContextPath());
        List<MultipartFile> files = studySaveRequestDto.getFiles();
        for (MultipartFile file : files){
            System.out.println(file);
        }
//        Study study = studyService.saveStudy(studySaveRequestDto, resolverDto);
        return null;
    }
}
