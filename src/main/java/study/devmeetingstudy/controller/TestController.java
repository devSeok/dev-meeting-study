package study.devmeetingstudy.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {

    @GetMapping
    public TestDTO test(){
        return new TestDTO("AA","TEST");
    }


    @Data
    @AllArgsConstructor
    static class TestDTO{
        String name;
        String message;
    }
}
