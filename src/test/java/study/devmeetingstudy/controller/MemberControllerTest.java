package study.devmeetingstudy.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.jwt.TokenProvider;
import study.devmeetingstudy.service.MemberService;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(MemberControllerTest.class)
class MemberControllerTest {


    @MockBean
    private MemberService memberService;

    @MockBean
    private TokenProvider tokenProvider;

    private MockMvc mvc;

//    @InjectMocks
//    private MemberController memberController;

    @Test
    @WithMockUser
    void test() throws Exception {



        RequestBuilder reuqest = MockMvcRequestBuilders.get("/api/study/test")
//                .header("Authorization", "Bearer " + test)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mvc.perform(reuqest).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

}