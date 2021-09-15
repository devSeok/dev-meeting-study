package study.devmeetingstudy.controller;


import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.devmeetingstudy.common.exception.global.response.ApiResponseDto;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.dto.subject.SubjectRequestDto;
import study.devmeetingstudy.dto.subject.SubjectResponseDto;
import study.devmeetingstudy.service.SubjectService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"5. subject"})
@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<SubjectResponseDto>> saveSubject(@Valid @RequestBody SubjectRequestDto subjectRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ApiResponseDto<>("생성됨", HttpStatus.CREATED.value(), SubjectResponseDto.from(subjectService.saveSubject(subjectRequestDto)))
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<SubjectResponseDto>>> getSubjects(){
        List<Subject> subjects = subjectService.findSubjects();
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApiResponseDto<>("성공", HttpStatus.OK.value(),
                                subjects.stream().map(SubjectResponseDto::from).collect(Collectors.toList()))
                );
    }

}
