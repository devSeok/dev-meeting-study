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
    public ResponseEntity<ApiResponseDto<MemberSignupResponseDto>> signup(@Valid @RequestBody MemberSignupRequestDto memberSignupRequestDto) {
        Member signup = authService.signup(memberSignupRequestDto);
        return ResponseEntity.created(URI.create("/api/member" + signup.getEmail()))
                .body(
                        ApiResponseDto.<MemberSignupResponseDto>builder()
                                .message("생성됨")
                                .status(HttpStatus.CREATED.value())
                                .data(MemberSignupResponseDto.from(signup))
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
    public ResponseEntity<ApiResponseDto<MemberLoginTokenResponseDto>> login(@Valid @RequestBody MemberLoginRequestDto memberRequestDto,
                                                             HttpServletResponse response) {
        TokenDto login = authService.login(memberRequestDto, response);

        return ResponseEntity.ok(
                ApiResponseDto.<MemberLoginTokenResponseDto>builder()
                        .message("성공")
                        .status(HttpStatus.OK.value())
                        .data(MemberLoginTokenResponseDto.from(login))
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
    public ResponseEntity<ApiResponseDto<TokenReissueResponseDto>> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        TokenDto reissue = authService.reissue(tokenRequestDto);

        return ResponseEntity.ok(
                ApiResponseDto.<TokenReissueResponseDto>builder()
                        .message("성공")
                        .status(HttpStatus.OK.value())
                        .data(TokenReissueResponseDto.from(reissue))
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
    public ResponseEntity<ApiResponseDto<Boolean>> emailAuth(@RequestBody EmailRequestDto emailRequestDto) throws Exception {
        emailService.sendSimpleMessage(emailRequestDto.getEmail());

        return ResponseEntity.ok(
                ApiResponseDto.<Boolean>builder()
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
    public ResponseEntity<ApiResponseDto<Boolean>> verifyCode(@RequestBody EmailVerifyCodeRequestDto code) {
        boolean emailCheckBool = emailService.emailCheck(code);

        if (!emailCheckBool) {
            throw new EmailVerifyCodeNotFoundException(
                    "유효하지 않거나 마지막으로 온 유효 번호가 아닙니다.",
                    ErrorCode.EMAIL_CODE_NOTFOUND
            );
        }
        return ResponseEntity.ok(
                ApiResponseDto.<Boolean>builder()
                        .message("성공")
                        .status(HttpStatus.OK.value())
                        .data(Boolean.TRUE)
                        .build()
        );
    }
}
