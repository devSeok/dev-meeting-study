package study.devmeetingstudy.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import study.devmeetingstudy.service.message.MessageService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"1. Messages"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
@Slf4j
public class MessageApiController {

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
    @ApiOperation(value = "메시지 보내기")
    public ResponseEntity<ApiResponseDto<MessageResponseDto>> sendMessage(@JwtMember MemberResolverDto memberResolverDto, @RequestBody MessageRequestDto messageRequestDto){
        Member loginMember = memberService.getUserOne(memberResolverDto.getId());
        Member member = memberService.getMemberInfo(messageRequestDto.getEmail());
        Message message = messageService.send(new MessageVO(messageRequestDto.getContent(), member, loginMember));
        return ResponseEntity.created(URI.create("/api/messages/"+message.getId()))
                .body(
                        new ApiResponseDto<>("생성됨", HttpStatus.CREATED.value(), MessageResponseDto.of(message, loginMember, member)
                ));
    }

    @GetMapping
    @ApiOperation(value = "메시지 목록")
    public ResponseEntity<ApiResponseDto<List<MessageResponseDto>>> showMessages(@JwtMember MemberResolverDto MemberResolverDto){
        Member loginMember = memberService.getUserOne(MemberResolverDto.getId());
        List<Message> messages = messageService.getMessages(loginMember);
        return ResponseEntity.ok()
                .body(
                        new ApiResponseDto<>("성공", HttpStatus.OK.value(),
                                messages.stream().map((message) -> MessageResponseDto.of(message, memberService.getUserOne(message.getSenderId()), loginMember))
                                        .collect(Collectors.toList()))
                );

    }

    @GetMapping("/{messageId}")
    @ApiOperation(value = "메시지 조회")
    public ResponseEntity<ApiResponseDto<MessageResponseDto>> showMessage(@JwtMember MemberResolverDto MemberResolverDto, @PathVariable Long messageId){
        Member loginMember = memberService.getUserOne(MemberResolverDto.getId());
        // 같은 멤버 처리
        Message message = messageService.getMessage(messageId);
        return ResponseEntity.ok()
                .body(
                        new ApiResponseDto<>("성공", HttpStatus.OK.value(), MessageResponseDto.of(message, memberService.getUserOne(message.getSenderId()), loginMember))
                );

    }

    @DeleteMapping("/{messageId}")
    @ApiOperation(value = "메시지 삭제")
    public ResponseEntity<Void> deleteMessage(@JwtMember MemberResolverDto memberResolverDto, @PathVariable Long messageId){
        Message message = messageService.getMessage(messageId);
        authService.checkUserInfo(message.getMember().getId(), memberResolverDto);
        messageService.deleteMessage(message);
        return ResponseEntity.noContent().build();
    }
}
