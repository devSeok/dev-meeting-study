package study.devmeetingstudy.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.devmeetingstudy.common.exception.global.response.ApiResponseDto;
import study.devmeetingstudy.dto.MemberRequestDto;
import study.devmeetingstudy.dto.TokenRequestDto;
import study.devmeetingstudy.service.AuthService;

import javax.validation.Valid;

@Api(tags = {"1. Auth"})
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    @ApiOperation(value = "회원가입")
    public ApiResponseDto signup(@Valid @RequestBody MemberRequestDto memberRequestDto) {

        return new ApiResponseDto(
                "성공적으로 회원가입되었습니다",
                200,
                authService.signup(memberRequestDto));
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인")
    public ApiResponseDto login(@Valid @RequestBody MemberRequestDto memberRequestDto) {

        return new ApiResponseDto(
                "성공적으로 로그인 되었습니다.",
                200,
                authService.login(memberRequestDto));
    }

    @PostMapping("/reissue")
    @ApiOperation(value = "토큰 재발급")
    public ApiResponseDto reissue(@RequestBody TokenRequestDto tokenRequestDto) {

        return new ApiResponseDto(
                "성공적으로 토큰 재발급 되었습니다.",
                200,
                authService.reissue(tokenRequestDto));

    }

}
