package study.devmeetingstudy.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import study.devmeetingstudy.annotation.JwtMember;
import study.devmeetingstudy.annotation.dto.MemberResolverDto;
import study.devmeetingstudy.common.exception.global.error.ErrorResponse;
import study.devmeetingstudy.common.exception.global.response.ApiResponseDto;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.dto.member.response.MemberResponseDto;
import study.devmeetingstudy.service.AuthService;
import study.devmeetingstudy.service.MemberService;

@Api(tags = {"2. member"})

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final AuthService authService;

    @GetMapping("/me")
    @ApiOperation(value = "사용자 확인", notes = "토큰값으로 나의 정보 값 체크")
    @ApiResponses({
            @ApiResponse(code = 200, message = "멤버 조회 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ApiResponseDto<MemberResponseDto>> getMyMemberInfo() {
        Member memberInfo = memberService.getMyInfo();

        return ResponseEntity.ok(
                ApiResponseDto.<MemberResponseDto>builder()
                        .message("성공")
                        .status(HttpStatus.OK.value())
                        .data(MemberResponseDto.from(memberInfo))
                        .build()
        );
    }

    @GetMapping("/{email}")
    @ApiOperation(value = "이메일 체크", notes = "토큰값기반으로 이메일 체크")
    @ApiResponses({
            @ApiResponse(code = 200, message = "이메일로 멤버 조회 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ApiResponseDto<MemberResponseDto>> getMemberInfo(@PathVariable String email) {
        Member memberInfo = memberService.getMemberInfo(email);

        return ResponseEntity.ok(
                ApiResponseDto.<MemberResponseDto>builder()
                        .message("성공")
                        .status(HttpStatus.OK.value())
                        .data(MemberResponseDto.from(memberInfo))
                        .build()
        );
    }

    @DeleteMapping("/me")
    @ApiOperation(value = "사용자탈퇴", notes = "사용자 자발적인 탈퇴")
    @ApiResponses({
            @ApiResponse(code = 204, message = "삭제 성공 (멤버 삭제 상태 업데이트)"),
            @ApiResponse(code = 400, message = "잘못된 요청", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@ApiIgnore @JwtMember MemberResolverDto dto) {
        memberService.deleteMember(dto);
        return ResponseEntity.noContent().build();
    }
}
