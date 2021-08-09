package study.devmeetingstudy.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.devmeetingstudy.annotation.JwtMember;
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
    @ApiOperation(value = "사용자확인", notes = "토큰값으로 사용자 누군지 체크")
    @ApiParam(value = "test", required = true)
    public ResponseEntity<MemberResponseDto> getMyMemberInfo() {


        return ResponseEntity.ok(memberService.getMyInfo());
    }

    @GetMapping("/{email}")
    public ResponseEntity<MemberResponseDto> getMemberInfo(@PathVariable String email) {
        return ResponseEntity.ok(memberService.getMemberInfo(email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id, @JwtMember String userId){
        memberService.deleteMember(id);

      return ResponseEntity.noContent().build();
    }
}
