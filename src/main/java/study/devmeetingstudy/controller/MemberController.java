package study.devmeetingstudy.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.devmeetingstudy.annotation.JwtMember;
import study.devmeetingstudy.annotation.dto.MemberResolverDto;
import study.devmeetingstudy.common.exception.global.response.ApiResponseDto;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.dto.member.response.MemberResponseDto;
import study.devmeetingstudy.service.MemberService;

@Api(tags = {"2. member"})

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    @ApiOperation(value = "사용자확인", notes = "토큰값으로 나의 정보 값 체크")
    public ApiResponseDto<MemberResponseDto> getMyMemberInfo() {
        Member memberInfo = memberService.getMyInfo();

        return new ApiResponseDto<MemberResponseDto>(
                "정상적으로 이메일 체크되었습니다",
                200,
                MemberResponseDto.from(memberInfo)
        );
    }

    @GetMapping("/{email}")
    @ApiOperation(value = "이메일 체크", notes = "토큰값기반으로 이메일 체크")
    public ApiResponseDto<MemberResponseDto> getMemberInfo(@PathVariable String email) {
        Member memberInfo = memberService.getMemberInfo(email);

        return new ApiResponseDto<MemberResponseDto>(
                "정상적으로 이메일 체크되었습니다",
                200,
                MemberResponseDto.from(memberInfo)
        );
    }

    @DeleteMapping("/me")
    @ApiOperation(value = "사용자탈퇴", notes = "사용자 자발적인 탈퇴")
    public ResponseEntity<Void> delete(@JwtMember MemberResolverDto dto) {
        memberService.deleteMember(dto);

        return ResponseEntity.noContent().build();
    }
}
