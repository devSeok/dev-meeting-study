package study.devmeetingstudy.controller;


import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.devmeetingstudy.common.exception.global.response.ApiResponseDto;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.dto.subject.SubjectRequestDto;
import study.devmeetingstudy.dto.subject.SubjectResponseDto;
import study.devmeetingstudy.service.SubjectService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"5. subject"})
@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
@Slf4j
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<SubjectResponseDto>> saveSubject(@Valid @RequestBody SubjectRequestDto subjectRequestDto){
        Subject savedSubject = subjectService.saveSubject(subjectRequestDto);
        return ResponseEntity.created(URI.create("/api/subjects" + savedSubject.getId()))
                .body(
                        ApiResponseDto.<SubjectResponseDto>builder()
                                .message("생성됨")
                                .status(HttpStatus.CREATED.value())
                                .data(SubjectResponseDto.from(savedSubject))
                                .build()
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<SubjectResponseDto>>> getSubjects(){
        List<Subject> subjects = subjectService.findSubjects();
        return ResponseEntity.ok(
                    ApiResponseDto.<List<SubjectResponseDto>>builder()
                            .message("성공")
                            .status(HttpStatus.OK.value())
                            .data(subjects.stream().map(SubjectResponseDto::from).collect(Collectors.toList()))
                            .build()
                );
    }

    // TODO 단일 리소스 조회 만들기

}
