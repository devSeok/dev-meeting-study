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
import study.devmeetingstudy.annotation.JwtMember;
import study.devmeetingstudy.annotation.dto.MemberResolverDto;
import study.devmeetingstudy.common.exception.global.response.ApiResponseDto;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.message.Message;
import study.devmeetingstudy.dto.message.MessageRequestDto;
import study.devmeetingstudy.dto.message.MessageResponseDto;
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
    @ApiResponses({
            @ApiResponse(code = 201, message = "생성됨")
    })
    public ResponseEntity<ApiResponseDto<MessageResponseDto>> sendMessage(@JwtMember MemberResolverDto memberResolverDto, @Valid @RequestBody MessageRequestDto messageRequestDto){
        Member loginMember = memberService.getUserOne(memberResolverDto.getId());
        Member member = memberService.getMemberInfo(messageRequestDto.getEmail());
        Message message = messageService.send(new MessageVO(messageRequestDto.getContent(), member, loginMember));
        return ResponseEntity.created(URI.create("/api/messages/"+message.getId()))
                .body(
                        new ApiResponseDto<>("생성됨", HttpStatus.CREATED.value(), MessageResponseDto.of(message, loginMember, member))
                );
    }

    @GetMapping
    @ApiOperation(value = "메시지 목록", notes = "로그인 한 유저에 대한 메시지 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
    })
    public ResponseEntity<ApiResponseDto<List<MessageResponseDto>>> showMessages(@JwtMember MemberResolverDto MemberResolverDto){
        Member loginMember = memberService.getUserOne(MemberResolverDto.getId());
        List<Message> messages = messageService.findMessages(loginMember);
        return ResponseEntity.ok()
                .body(
                        new ApiResponseDto<>("성공", HttpStatus.OK.value(),
                                messages.stream().map((message) -> MessageResponseDto.of(message, memberService.getUserOne(message.getSenderId()), loginMember))
                                        .collect(Collectors.toList()))
                );

    }

    @GetMapping("/{messageId}")
    @ApiOperation(value = "메시지 조회", notes = "로그인 한 유저에 대한 messageId에 해당하는 리소스를 조회하며, 읽음 상태를 변경합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
    })
    public ResponseEntity<ApiResponseDto<MessageResponseDto>> showMessage(@JwtMember MemberResolverDto MemberResolverDto, @PathVariable Long messageId){
        Member loginMember = memberService.getUserOne(MemberResolverDto.getId());
        // 같은 멤버 처리
        Message message = messageService.findMessage(messageId);
        return ResponseEntity.ok()
                .body(
                        new ApiResponseDto<>("성공", HttpStatus.OK.value(), MessageResponseDto.of(message, memberService.getUserOne(message.getSenderId()), loginMember))
                );

    }

    @DeleteMapping("/{messageId}")
    @ApiOperation(value = "메시지 삭제", notes = "로그인 한 유저에 대한 messageId에 해당하는 리소스를 삭제합니다 (삭제 상태 업데이트 처리)")
    @ApiResponses({
            @ApiResponse(code = 204, message = "삭제됨")
    })
    public ResponseEntity<Void> deleteMessage(@JwtMember MemberResolverDto memberResolverDto, @PathVariable Long messageId){
        Message message = messageService.findMessage(messageId);
        authService.checkUserInfo(message.getMember().getId(), memberResolverDto);
        messageService.deleteMessage(message);
        return ResponseEntity.noContent().build();
    }
}
