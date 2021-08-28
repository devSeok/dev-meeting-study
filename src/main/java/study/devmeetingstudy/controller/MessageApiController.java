package study.devmeetingstudy.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.devmeetingstudy.annotation.JwtMember;
import study.devmeetingstudy.common.exception.global.response.ApiResponseDto;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.message.Message;
import study.devmeetingstudy.domain.message.enums.MessageDeletionStatus;
import study.devmeetingstudy.domain.message.enums.MessageReadStatus;
import study.devmeetingstudy.dto.member.MemberResponseDto;
import study.devmeetingstudy.dto.message.MessageRequestDto;
import study.devmeetingstudy.service.MemberService;
import study.devmeetingstudy.service.message.MessageService;

@Api(tags = {"1. Messages"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
@Slf4j
public class MessageApiController {
    private final MessageService messageService;
    // 임시로
    private final MemberService memberService;

    /**
     * save Message
     * @return
     */
    @PostMapping
    @ApiOperation(value = "메시지 보내기")
    public ResponseEntity<ApiResponseDto> sendMessage(@RequestBody MessageRequestDto messageRequestDto, @JwtMember String email){
        // 이게 맞는 코드일까..?
        Member sender = memberService.getUserOne(email);
        Member member = memberService.getUserOne(messageRequestDto.getEmail());
        messageRequestDto.setSender(sender);
        messageRequestDto.setMember(member);

        Message message = messageService.send(messageRequestDto);
        return new ResponseEntity<>(
                new ApiResponseDto(
                        "성공",
                        HttpStatus.CREATED.value(),
                        new ResponseMessage(
                                message.getId(),
                                message.getSenderId(),
                                message.getSenderName(),
                                new MemberResponseDto(member.getId(), member.getNickname(), member.getEmail(), member.getGrade()),
                                message.getContent(),
                                message.getDelflg(),
                                message.getStatus()
                        )),
                HttpStatus.CREATED);
    }

    @Data
    @AllArgsConstructor
    class ResponseMessage{
        private Long id;
        private Long senderId;
        private String senderName;
        private MemberResponseDto member;
        private String content;
        private MessageDeletionStatus delflg;
        private MessageReadStatus status;
    }


//    @GetMapping
//    @ApiOperation(value = "메시지 리스트")
//    public ResponseEntity<ApiResponseDto> getMessages(){
//
//    }
}
