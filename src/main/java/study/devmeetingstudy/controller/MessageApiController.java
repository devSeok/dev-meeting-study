package study.devmeetingstudy.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.devmeetingstudy.common.exception.global.response.ApiResponseDto;
import study.devmeetingstudy.dto.member.response.MemberResponseDto;
import study.devmeetingstudy.service.MemberService;
import study.devmeetingstudy.service.message.MessageService;

@Api(tags = {"1. Messages"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
@Slf4j
public class MessageApiController {
    private final MessageService messageService;
    private final MemberService memberService;

    /**
     * save Message
     * @return
     */
    @PostMapping
    @ApiOperation(value = "메시지 보내기")
    public ResponseEntity<ApiResponseDto> sendMessage(){
        return new ResponseEntity<>(
                new ApiResponseDto(
                        "성공",
                        HttpStatus.CREATED.value(),
                        new ResponseMessage()),
                HttpStatus.CREATED);
    }

    @Data
    class ResponseMessage{
        private MemberResponseDto member;
        private MemberResponseDto sender;
        private String content;
    }


//    @GetMapping
//    @ApiOperation(value = "메시지 리스트")
//    public ResponseEntity<ApiResponseDto> getMessages(){
//
//    }







}
