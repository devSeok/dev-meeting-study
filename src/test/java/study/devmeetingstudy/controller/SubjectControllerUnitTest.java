package study.devmeetingstudy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import study.devmeetingstudy.annotation.handlerMethod.MemberDecodeResolver;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.member.enums.Authority;
import study.devmeetingstudy.domain.member.enums.MemberStatus;
import study.devmeetingstudy.dto.subject.SubjectReqDto;
import study.devmeetingstudy.dto.token.TokenDto;
import study.devmeetingstudy.jwt.TokenProvider;
import study.devmeetingstudy.repository.MemberRepository;
import study.devmeetingstudy.service.SubjectService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SubjectControllerUnitTest {

    @InjectMocks
    private SubjectController subjectController;

    @Mock
    private SubjectService subjectService;

    @Mock
    private MemberRepository memberRepository;

    private MockMvc mockMvc;

    private Member loginMember;

    private TokenProvider tokenProvider;

    @BeforeEach
    void init(){
        tokenProvider = new TokenProvider("c3ByaW5nLWJvb3Qtc2VjdXJpdHktand0LXR1dG9yaWFsLWppd29vbi1zcHJpbmctYm9vdC1zZWN1cml0eS1qd3QtdHV0b3JpYWwK");
        // 만든 ArgumentResolver 이용.
        mockMvc = MockMvcBuilders.standaloneSetup(subjectController)
                .addFilter((((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                })))
                .setCustomArgumentResolvers(new MemberDecodeResolver(tokenProvider, memberRepository))
                .build();
        // 메시지를 받는 멤버
        // 메시지를 보내는 멤버
        loginMember = createMember(1L, "dltmddn@na.na", "nick1");
    }

    private Member createMember(Long id, String email, String nickname){
        return Member.builder()
                .authority(Authority.ROLE_USER)
                .email(email)
                .password("123456")
                .status(MemberStatus.ACTIVE)
                .grade(0)
                .nickname(nickname)
                .id(id)
                .build();
    }

    @DisplayName("스터디 주제 저장 201 Created")
    @Test
    public void saveSubject() throws Exception{
        //given
        TokenDto tokenDto = getTokenDto();
        SubjectReqDto subjectReqDto = new SubjectReqDto("자바");
        Subject expectedSubject = createSubject(1L, subjectReqDto.getSubjectName());
        doReturn(expectedSubject).when(subjectService).saveSubject(any(SubjectReqDto.class));

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/subjects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","bearer " + tokenDto.getAccessToken())
                        .content(new ObjectMapper().writeValueAsString(subjectReqDto)));

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isCreated()).andReturn();
        String body = mvcResult.getResponse().getContentAsString();

        JSONObject data = (JSONObject) getDataOfJSON(body);
        Subject subject = createSubject((Long) data.get("id"), (String) data.get("name"));

        assertEquals(expectedSubject, subject);
    }

    private Subject createSubject(Long id, String subjectName){
        return Subject.builder()
                .id(id)
                .name(subjectName)
                .build();
    }

    private TokenDto getTokenDto() {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(loginMember.getAuthority().toString());
        Authentication token = new UsernamePasswordAuthenticationToken(loginMember.getId(), loginMember.getPassword(), Collections.singleton(grantedAuthority));
        // loginMember 기반으로 token 생성
        return tokenProvider.generateTokenDto(token);
    }

    private Object getDataOfJSON(String body) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject json = (JSONObject) jsonParser.parse(body);
        return json.get("data");
    }

    @DisplayName("스터디 주제 목록 200 Ok")
    @Test
    public void getSubjects() throws Exception{
        //given
        TokenDto tokenDto = getTokenDto();
        String[] subjects = {"Java", "Typescript", "Javascript", "Python", "HTML/CSS", "C", "C++"};
        List<Subject> subjectList = new ArrayList<>();
        for (int i = 0; i < subjects.length; i++){
            subjectList.add(createSubject((long) i+1, subjects[i]));
        }

        doReturn(subjectList).when(subjectService).findSubjects();

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/subjects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","bearer " + tokenDto.getAccessToken()));

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String body = mvcResult.getResponse().getContentAsString();
        JSONArray data = (JSONArray) getDataOfJSON(body);

        assertEquals(subjects.length, data.size());
    }


}