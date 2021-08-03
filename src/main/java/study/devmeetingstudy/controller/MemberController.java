package study.devmeetingstudy.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.devmeetingstudy.annotation.JwtMember;
import study.devmeetingstudy.dto.MemberResponseDto;
import study.devmeetingstudy.service.MemberService;

@Api(tags = {"2. member"})

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    @ApiOperation(value = "사용자확인", notes = "토큰값으로 사용자 누군지 체크")
    @ApiParam(value = "test", required = true)
    public ResponseEntity<MemberResponseDto> getMyMemberInfo(@JwtMember String name) {


        return ResponseEntity.ok(memberService.getMyInfo());
    }

    @GetMapping("/{email}")
    public ResponseEntity<MemberResponseDto> getMemberInfo(@PathVariable String email) {
        return ResponseEntity.ok(memberService.getMemberInfo(email));
    }
}
