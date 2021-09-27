package study.devmeetingstudy.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import study.devmeetingstudy.annotation.JwtMember;
import study.devmeetingstudy.annotation.dto.MemberResolverDto;
import study.devmeetingstudy.common.exception.global.error.ErrorResponse;
import study.devmeetingstudy.common.exception.global.response.ApiResDto;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.message.Message;
import study.devmeetingstudy.dto.message.MessageReqDto;
import study.devmeetingstudy.dto.message.MessageResDto;
import study.devmeetingstudy.service.AuthService;
import study.devmeetingstudy.vo.MessageVO;
import study.devmeetingstudy.service.MemberService;
import study.devmeetingstudy.service.MessageService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"1. Messages"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
@Slf4j
public class MessageController {

    private final MessageService messageService;
    private final MemberService memberService;
    private final AuthService authService;

    /*
    * TODO
    *  멤버 확인 코드 작성.
    * */

    /**
     * save Message
     * @return
     */
    @PostMapping
    @ApiOperation(value = "메시지 보내기", notes = "로그인 한 유저가 email 대상에게 메시지를 보냅니다. (메시지 리소스를 생성합니다.)")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "메시지 생성 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ApiResDto<MessageResDto>> saveMessage(@ApiIgnore @JwtMember MemberResolverDto memberResolverDto,
                                                                @Valid @RequestBody MessageReqDto messageReqDto){
        Member loginMember = memberService.getUserOne(memberResolverDto.getId());
        Member member = memberService.getMemberInfo(messageReqDto.getEmail());
        Message message = messageService.send(new MessageVO(messageReqDto.getContent(), member, loginMember));
        return ResponseEntity
                .created(URI.create("/api/messages/" + message.getId()))
                .body(
                        ApiResDto.<MessageResDto>builder()
                                .message("생성됨")
                                .status(HttpStatus.CREATED.value())
                                .data(MessageResDto.of(message, loginMember, member))
                                .build()
                );
    }

    @GetMapping
    @ApiOperation(value = "메시지 목록", notes = "로그인 한 유저에 대한 메시지 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "메시지 목록 조회 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ApiResDto<List<MessageResDto>>> getMessages(@ApiIgnore @JwtMember MemberResolverDto MemberResolverDto){
        Member loginMember = memberService.getUserOne(MemberResolverDto.getId());
        List<Message> messages = messageService.findMessages(loginMember);
        return ResponseEntity.ok(
                ApiResDto.<List<MessageResDto>>builder()
                        .message("성공")
                        .status(HttpStatus.OK.value())
                        .data(messages.stream()
                                .map((message) ->
                                MessageResDto.of(message, memberService.getUserOne(message.getSenderId()), loginMember))
                                .collect(Collectors.toList()))
                        .build()
        );
    }

    // TODO 보낸 메시지 기능 추가하기
    @GetMapping("/{id}")
    @ApiOperation(value = "메시지 조회", notes = "로그인 한 유저에 대한 messageId에 해당하는 리소스를 조회하며, 읽음 상태를 변경합니다.")
    @ApiImplicitParam(name = "id", value = "메시지 아이디")
    @ApiResponses({
            @ApiResponse(code = 200, message = "메시지 조회 성공 (메시지 읽음 상태 업데이트)"),
            @ApiResponse(code = 400, message = "잘못된 요청", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ApiResDto<MessageResDto>> getMessage(@ApiIgnore @JwtMember MemberResolverDto memberResolverDto,
                                                               @PathVariable Long id){
        Member loginMember = memberService.getUserOne(memberResolverDto.getId());
        Message message = messageService.findMessage(id);
        authService.checkUserInfo(message.getMember().getId(), memberResolverDto);
        return ResponseEntity.ok(
                ApiResDto.<MessageResDto>builder()
                        .message("성공")
                        .status(HttpStatus.OK.value())
                        .data(MessageResDto.of(message, memberService.getUserOne(message.getSenderId()), loginMember))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "메시지 삭제", notes = "로그인 한 유저에 대한 messageId에 해당하는 리소스를 삭제합니다 (삭제 상태 업데이트 처리)")
    @ApiImplicitParam(name = "id", value = "메시지 아이디")
    @ApiResponses({
            @ApiResponse(code = 204, message = "메시지 삭제 성공 (메시지 삭제 상태 업데이트)"),
            @ApiResponse(code = 400, message = "잘못된 요청", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteMessage(@ApiIgnore @JwtMember MemberResolverDto memberResolverDto,
                                              @PathVariable Long id){
        Message message = messageService.findMessage(id);
        authService.checkUserInfo(message.getMember().getId(), memberResolverDto);
        messageService.deleteMessage(message);
        return ResponseEntity.noContent().build();
    }
}
