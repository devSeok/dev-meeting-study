package study.devmeetingstudy.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.devmeetingstudy.annotation.JwtMember;
import study.devmeetingstudy.annotation.dto.MemberResolverDto;
import study.devmeetingstudy.common.exception.global.response.ApiResponseDto;
import study.devmeetingstudy.dto.member.MemberResponseDto;
import study.devmeetingstudy.service.MemberService;

@Api(tags = {"2. member"})

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    @ApiOperation(value = "사용자확인", notes = "토큰값으로 나의 정보 값 체크")
    public ResponseEntity<MemberResponseDto> getMyMemberInfo() {

        return ResponseEntity.ok(memberService.getMyInfo());
    }

    @GetMapping("/{email}")
    @ApiOperation(value = "이메일 체크", notes = "토큰값기반으로 이메일 체크")
    public ResponseEntity<MemberResponseDto> getMemberInfo(@PathVariable String email) {
        return ResponseEntity.ok(memberService.getMemberInfo(email));
    }

    @DeleteMapping("/me")
    @ApiOperation(value = "사용자탈퇴", notes = "사용자 자발적인 탈퇴")
    public ResponseEntity delete(@JwtMember MemberResolverDto dto){
        memberService.deleteMember(dto);

      return ResponseEntity.noContent().build();
    }
}
