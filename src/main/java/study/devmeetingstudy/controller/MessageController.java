package study.devmeetingstudy.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.devmeetingstudy.common.exception.global.response.ApiResponseDto;
import study.devmeetingstudy.domain.Member;
import study.devmeetingstudy.service.message.MessageService;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<ApiResponseDto> sendMessage(){


        return new ResponseEntity<ApiResponseDto>(
                new ApiResponseDto(
                        "성공",
                        HttpStatus.OK.value(),
                        new ResponseMessage()),
                HttpStatus.OK);
    }

    @Data
    class ResponseMessage{
        private Member member;
        private Member sender;
        private String content;
    }

}
