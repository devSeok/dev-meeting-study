package study.devmeetingstudy.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.devmeetingstudy.common.exception.global.error.ErrorResponse;
import study.devmeetingstudy.common.exception.global.error.exception.EmailVerifyCodeNotFoundException;
import study.devmeetingstudy.common.exception.global.error.exception.ErrorCode;
import study.devmeetingstudy.common.exception.global.response.ApiResDto;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.dto.email.EmailReqDto;
import study.devmeetingstudy.dto.email.EmailVerifyCodeReqDto;
import study.devmeetingstudy.dto.member.request.MemberLoginReqDto;
import study.devmeetingstudy.dto.member.request.MemberSignupReqDto;
import study.devmeetingstudy.dto.token.response.MemberLoginTokenResDto;
import study.devmeetingstudy.dto.member.response.MemberSignupResDto;
import study.devmeetingstudy.dto.token.TokenDto;
import study.devmeetingstudy.dto.token.request.TokenReqDto;
import study.devmeetingstudy.dto.token.response.TokenReissueResDto;
import study.devmeetingstudy.service.AuthService;
import study.devmeetingstudy.service.EmailService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;

@Api(tags = "인증", value = "controller")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    private final EmailService emailService;

    @PostMapping("/signup")
    @ApiOperation(value = "회원가입")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원가입 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ApiResDto<MemberSignupResDto>> signup(@Valid @RequestBody MemberSignupReqDto memberSignupReqDto) {
        Member signup = authService.signup(memberSignupReqDto);
        return ResponseEntity.created(URI.create("/api/member" + signup.getEmail()))
                .body(
                        ApiResDto.<MemberSignupResDto>builder()
                                .message("생성됨")
                                .status(HttpStatus.CREATED.value())
                                .data(MemberSignupResDto.from(signup))
                                .build()
                );
    }

    // set cookie 참고 사이트 : https://dncjf64.tistory.com/292
    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "성공시 jwt 토큰값을 쿠키 해더에 넣어서 반환합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ApiResDto<MemberLoginTokenResDto>> login(@Valid @RequestBody MemberLoginReqDto memberRequestDto,
                                                                   HttpServletResponse response) {
        TokenDto login = authService.login(memberRequestDto, response);

        return ResponseEntity.ok(
                ApiResDto.<MemberLoginTokenResDto>builder()
                        .message("성공")
                        .status(HttpStatus.OK.value())
                        .data(MemberLoginTokenResDto.from(login))
                        .build()
        );
    }

    @PostMapping("/reissue")
    @ApiOperation(value = "토큰 재발급")
    @ApiResponses({
            @ApiResponse(code = 200, message = "토큰 재발급 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ApiResDto<TokenReissueResDto>> reissue(@RequestBody TokenReqDto tokenReqDto) {
        TokenDto reissue = authService.reissue(tokenReqDto);

        return ResponseEntity.ok(
                ApiResDto.<TokenReissueResDto>builder()
                        .message("성공")
                        .status(HttpStatus.OK.value())
                        .data(TokenReissueResDto.from(reissue))
                        .build()
        );
    }

    @PostMapping("/email") // 이메일 인증 코드 보내기
    @ApiOperation(value = "이메일 인증코드 보내기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "인증코드 보내기 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ApiResDto<Boolean>> emailAuth(@RequestBody EmailReqDto emailReqDto) throws Exception {
        emailService.sendSimpleMessage(emailReqDto.getEmail());

        return ResponseEntity.ok(
                ApiResDto.<Boolean>builder()
                        .message("성공")
                        .status(HttpStatus.OK.value())
                        .data(Boolean.TRUE)
                        .build()
        );
    }

    @PostMapping("/verifyCode") // 이메일 인증 코드 검증
    @ApiOperation(value = "이메일 인증코드 검증")
    @ApiResponses({
            @ApiResponse(code = 200, message = "메일 인증 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ApiResDto<Boolean>> verifyCode(@RequestBody EmailVerifyCodeReqDto code) {
        boolean emailCheckBool = emailService.emailCheck(code);

        if (!emailCheckBool) {
            throw new EmailVerifyCodeNotFoundException(
                    "유효하지 않거나 마지막으로 온 유효 번호가 아닙니다.",
                    ErrorCode.EMAIL_CODE_NOTFOUND
            );
        }
        return ResponseEntity.ok(
                ApiResDto.<Boolean>builder()
                        .message("성공")
                        .status(HttpStatus.OK.value())
                        .data(Boolean.TRUE)
                        .build()
        );
    }
}
