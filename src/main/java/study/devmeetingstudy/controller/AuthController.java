package study.devmeetingstudy.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.devmeetingstudy.common.exception.global.error.exception.EmailVerifyCodeNotFoundException;
import study.devmeetingstudy.common.exception.global.error.exception.ErrorCode;
import study.devmeetingstudy.common.exception.global.error.exception.UserException;
import study.devmeetingstudy.common.exception.global.response.ApiResponseDto;
import study.devmeetingstudy.domain.Email;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.dto.email.EmailRequestDto;
import study.devmeetingstudy.dto.email.EmailVerifyCodeRequestDto;
import study.devmeetingstudy.dto.member.request.MemberLoginRequestDto;
import study.devmeetingstudy.dto.member.request.MemberSignupRequestDto;
import study.devmeetingstudy.dto.token.response.MemberLoginTokenResponseDto;
import study.devmeetingstudy.dto.member.response.MemberResponseDto;
import study.devmeetingstudy.dto.member.response.MemberSignupResponseDto;
import study.devmeetingstudy.dto.token.TokenDto;
import study.devmeetingstudy.dto.token.request.TokenRequestDto;
import study.devmeetingstudy.dto.token.response.TokenReissueResponseDto;
import study.devmeetingstudy.service.AuthService;
import study.devmeetingstudy.service.EmailService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(tags = "인증", value = "controller")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    private final EmailService emailService;

    @PostMapping("/signup")
    @ApiOperation(value = "회원가입")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원가입", response = MemberResponseDto.class),
    })
    public ApiResponseDto<Member> signup(@Valid @RequestBody MemberSignupRequestDto memberSignupRequestDto) {
        Member signup = authService.signup(memberSignupRequestDto);

        return new ApiResponseDto(
                "성공적으로 회원가입되었습니다",
                200,
                MemberSignupResponseDto.from(signup));
    }

    // set cookie 참고 사이트 : https://dncjf64.tistory.com/292
    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "성공시 jwt 토큰값을 쿠키 해더에 넣어서 반환합니다.")
    public ApiResponseDto<Member> login(@Valid @RequestBody MemberLoginRequestDto memberRequestDto, HttpServletResponse response) {
        TokenDto login = authService.login(memberRequestDto, response);

        return new ApiResponseDto(
                "성공적으로 로그인 되었습니다.",
                200,
                MemberLoginTokenResponseDto.from(login));
    }

    @PostMapping("/reissue")
    @ApiOperation(value = "토큰 재발급")
    public ApiResponseDto<Member> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        TokenDto reissue = authService.reissue(tokenRequestDto);

        return new ApiResponseDto(
                "성공적으로 토큰 재발급 되었습니다.",
                200,
                TokenReissueResponseDto.from(reissue)
               );
    }

    @PostMapping("/email") // 이메일 인증 코드 보내기
    @ApiOperation(value = "이메일 인증코드 보내기")
    public ApiResponseDto<Email> emailAuth(@RequestBody EmailRequestDto emailRequestDto) throws Exception {
        emailService.sendSimpleMessage(emailRequestDto.getEmail());

        return new ApiResponseDto(
                "성공적으로 메일을 보냈습니다",
                200,
                Boolean.TRUE
        );
    }

    @PostMapping("/verifyCode") // 이메일 인증 코드 검증
    @ApiOperation(value = "이메일 인증코드 검증")
    public ApiResponseDto<Email> verifyCode(@RequestBody EmailVerifyCodeRequestDto code) {
        boolean emailCheckBool = emailService.emailCheck(code);

        if (!emailCheckBool) {
            throw new EmailVerifyCodeNotFoundException(
                    "유효하지 않거나 마지막으로 온 유효 번호가 아닙니다.",
                    ErrorCode.EMAIL_CODE_NOTFOUND
            );
        }

        return new ApiResponseDto(
                "메일인증이 성공되었습니다.",
                200,
                true
        );
    }
}
