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
import study.devmeetingstudy.dto.message.MessageResponseDto;
import study.devmeetingstudy.common.exception.global.response.ApiResponseDto;
import study.devmeetingstudy.dto.member.response.MemberResponseDto;
import study.devmeetingstudy.service.MemberService;
import study.devmeetingstudy.service.message.MessageService;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<ApiResponseDto> sendMessage(@JwtMember String email, @RequestBody MessageRequestDto messageRequestDto){
        // 이게 맞는 코드일까..?
        Member loginMember = memberService.getUserOne(email);
        Member member = memberService.getUserOne(messageRequestDto.getEmail());
        messageRequestDto.setSender(loginMember);
        messageRequestDto.setMember(member);

        Message message = messageService.send(messageRequestDto);
        return new ResponseEntity<>(
                new ApiResponseDto(
                        "생성됨",
                        HttpStatus.CREATED.value(),
                        MessageResponseDto.of(message, loginMember, member)),
                HttpStatus.CREATED);
    }


    @GetMapping
    @ApiOperation(value = "메시지 목록")
    public ResponseEntity<ApiResponseDto> showMessages(@JwtMember String email){
        Member member = memberService.getUserOne(email);
        List<Message> messages = messageService.getMessages(member);
        return new ResponseEntity<>(
                new ApiResponseDto(
                        "성공",
                        HttpStatus.OK.value(),
                        messages.stream().map((message) ->
                                MessageResponseDto.of(message, memberService.getUserOne(message.getSenderId()), member)).collect(Collectors.toList())),
                HttpStatus.OK);
    }


}
