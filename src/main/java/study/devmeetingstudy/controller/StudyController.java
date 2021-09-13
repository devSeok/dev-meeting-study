package study.devmeetingstudy.controller;


import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.devmeetingstudy.annotation.JwtMember;
import study.devmeetingstudy.annotation.dto.MemberResolverDto;
import study.devmeetingstudy.dto.study.request.StudyStoreRequestDto;
import study.devmeetingstudy.service.AuthService;
import study.devmeetingstudy.service.StudyService;

import javax.validation.Valid;

@Api(tags = {"3. Study"})
@RestController
@RequestMapping("/api/study")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService service;

    @PostMapping
    public String store(
            @Valid @ModelAttribute StudyStoreRequestDto dto,
            @JwtMember MemberResolverDto resolverDto
    ){

        service.save(dto, resolverDto);
        System.out.println(dto.toString());
           return  "test";
    }
}
