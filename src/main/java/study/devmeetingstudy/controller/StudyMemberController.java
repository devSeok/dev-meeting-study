package study.devmeetingstudy.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.devmeetingstudy.common.exception.global.response.ApiResDto;
import study.devmeetingstudy.domain.study.StudyMember;
import study.devmeetingstudy.dto.study.response.StudyMemberResDto;
import study.devmeetingstudy.service.study.StudyMemberService;

import java.util.List;

@Api(tags = {"6. StudyMember"})
@RestController
@RequestMapping("/api/studyMembers")
@RequiredArgsConstructor
@Slf4j
public class StudyMemberController {

    private final StudyMemberService studyMemberService;

    /* TODO 지금 당장 하지말고 study PUT, DELETE 이후 진행.
            로그인 한 멤버가 해당 스터디에 속해있는지 체크 필요함.


    */
    @GetMapping("/{studyId}")
    @ApiOperation(value = "스터디 멤버 목록 조회", notes = "studyId에 해당하는 스터디 멤버 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "스터디 멤버 목록 조회 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청")
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ApiResDto<StudyMemberResDto>> getStudyMembers(@PathVariable Long studyId) {
        List<StudyMember> studyMembers = studyMemberService.findStudyMembersByStudyId(studyId);
        return null;
    }
}
