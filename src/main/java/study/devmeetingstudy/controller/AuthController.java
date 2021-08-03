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
import study.devmeetingstudy.dto.*;
import study.devmeetingstudy.jwt.TokenProvider;
import study.devmeetingstudy.service.AuthService;
import study.devmeetingstudy.service.EmailService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

import static study.devmeetingstudy.jwt.TokenProvider.*;

@Api(tags = {"1. Auth"})
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    private final EmailService emailService;

    @PostMapping("/signup")
    @ApiOperation(value = "회원가입")
    public ApiResponseDto signup(@Valid @RequestBody MemberRequestDto memberRequestDto) {

        return new ApiResponseDto(
                "성공적으로 회원가입되었습니다",
                200,
                authService.signup(memberRequestDto));
    }

    // set cookie 참고 사이트 : https://dncjf64.tistory.com/292
    @PostMapping("/login")
    @ApiOperation(value = "로그인")
    public ApiResponseDto login(@Valid @RequestBody MemberRequestDto memberRequestDto, HttpServletResponse response) {
        TokenDto login = authService.login(memberRequestDto, response);


//        // TODO : 좀더 좋은 방법인 코드로 해결해야될듯합니다.
//        Cookie cookie = new Cookie("accessToken", login.getAccessToken());
//        cookie.setMaxAge((int) ACCESS_TOKEN_EXPIRE_TIME);
//        cookie.setSecure(true);
//        cookie.setHttpOnly(true);

//        response.addCookie(cookie);

        return new ApiResponseDto(
                "성공적으로 로그인 되었습니다.",
                200,
                login);
    }

    @PostMapping("/reissue")
    @ApiOperation(value = "토큰 재발급")
    public ApiResponseDto reissue(@RequestBody TokenRequestDto tokenRequestDto) {

        return new ApiResponseDto(
                "성공적으로 토큰 재발급 되었습니다.",
                200,
                authService.reissue(tokenRequestDto));
    }

    @PostMapping("/email") // 이메일 인증 코드 보내기
    public boolean emailAuth(@RequestBody EmailRequestDto emailRequestDto) throws Exception {
        emailService.sendSimpleMessage(emailRequestDto.getEmail());

        return true;
//        return new ApiResponseDto(
//                "성공적으로 메일을 보냈습니다",
//                200,
    }

    @PostMapping("/verifyCode") // 이메일 인증 코드 검증
    public boolean verifyCode(@RequestBody EmailVerifyCodeRequestDto code) {

        return emailService.emailCheck(code);
    }
}
