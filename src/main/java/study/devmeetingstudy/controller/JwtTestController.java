package study.devmeetingstudy.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.devmeetingstudy.annotation.JwtMember;

@RestController
@RequestMapping("/test")
public class JwtTestController {

    @GetMapping("/jwt")
    public String test(@JwtMember String id){

        return id;
    }
}
