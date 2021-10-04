package study.devmeetingstudy.controller;


import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.devmeetingstudy.common.exception.global.response.ApiResDto;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.dto.subject.SubjectReqDto;
import study.devmeetingstudy.dto.subject.SubjectResDto;
import study.devmeetingstudy.service.study.SubjectService;

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
    public ResponseEntity<ApiResDto<SubjectResDto>> saveSubject(@Valid @RequestBody SubjectReqDto subjectReqDto){
        Subject savedSubject = subjectService.saveSubject(subjectReqDto);
        return ResponseEntity.created(URI.create("/api/subjects" + savedSubject.getId()))
                .body(
                        ApiResDto.<SubjectResDto>builder()
                                .message("생성됨")
                                .status(HttpStatus.CREATED.value())
                                .data(SubjectResDto.from(savedSubject))
                                .build()
                );
    }

    @GetMapping
    public ResponseEntity<ApiResDto<List<SubjectResDto>>> getSubjects(){
        List<Subject> subjects = subjectService.findSubjects();
        return ResponseEntity.ok(
                    ApiResDto.<List<SubjectResDto>>builder()
                            .message("성공")
                            .status(HttpStatus.OK.value())
                            .data(subjects.stream().map(SubjectResDto::from).collect(Collectors.toList()))
                            .build()
                );
    }

    // TODO 단일 리소스 조회 만들기

}
