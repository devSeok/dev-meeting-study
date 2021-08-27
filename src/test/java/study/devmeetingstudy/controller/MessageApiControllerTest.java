package study.devmeetingstudy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;
import study.devmeetingstudy.annotation.handlerMethod.MemberDecodeResolver;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.member.enums.Authority;
import study.devmeetingstudy.domain.member.enums.MemberStatus;
import study.devmeetingstudy.domain.message.Message;
import study.devmeetingstudy.domain.message.enums.MessageDeletionStatus;
import study.devmeetingstudy.domain.message.enums.MessageReadStatus;
import study.devmeetingstudy.dto.message.MessageRequestDto;
import study.devmeetingstudy.dto.token.TokenDto;
import study.devmeetingstudy.jwt.TokenProvider;
import study.devmeetingstudy.service.MemberService;
import study.devmeetingstudy.service.message.MessageService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


// 테스트 Mockito 결합
@ExtendWith(MockitoExtension.class)
@Import(HttpEncodingAutoConfiguration.class)
class MessageApiControllerTest {
    /** TODO:
     *      1. 메시지 목록
     *      2. 메시지 보내기
     *          -
     *      3. 메시지
     *
     */

    @InjectMocks
    private MessageApiController messageApiController;

    @Mock
    private MessageService messageService;

    @Mock
    private MemberService memberService;

    private MockMvc mockMvc;

    @BeforeEach
    void init(){
        // 만든 ArgumentResolver 이용.
        mockMvc = MockMvcBuilders.standaloneSetup(messageApiController)
                .addFilter((((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                })))
                .setCustomArgumentResolvers(new MemberDecodeResolver(new ObjectMapper(), new TokenProvider("c3ByaW5nLWJvb3Qtc2VjdXJpdHktand0LXR1dG9yaWFsLWppd29vbi1zcHJpbmctYm9vdC1zZWN1cml0eS1qd3QtdHV0b3JpYWwK")))
                .build();
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

    @DisplayName("메시지 저장 성공")
    @WithMockUser
    @Test
    void sendMessage() throws Exception{
        //given
        // 메시지를 받는 멤버
        Member member = createMember(1L, "dltmddn@na.na", "nick1");
        // 메시지를 보내는 멤버
        Member sender = createMember(2L, "xonic@na.na", "nick2");

        TokenProvider tokenProvider = new TokenProvider("c3ByaW5nLWJvb3Qtc2VjdXJpdHktand0LXR1dG9yaWFsLWppd29vbi1zcHJpbmctYm9vdC1zZWN1cml0eS1qd3QtdHV0b3JpYWwK");

        // 토큰 생성 및 발급
        // 현재 로그인 한 멤버는 sender 이다.
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(sender.getAuthority().toString());
        Authentication token = new UsernamePasswordAuthenticationToken(sender.getId(), sender.getPassword(), Collections.singleton(grantedAuthority));
        // sender 기반으로 token 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(token);

        MessageRequestDto messageRequestDto = new MessageRequestDto("dltmddn@na.na", "Hello");

        // mock 객체가 특정한 값을 반환해야 하는 경우.
        Message message = createMessage(member, sender);
        // controller에서 호출한 getUserOne 모킹
        doReturn(sender).doReturn(member).when(memberService).getUserOne(any(String.class));
        // 새로 생성된 메시지가 리턴됨.
        doReturn(message).when(messageService).send(any(MessageRequestDto.class));

        //when
        // 요청 리퀘스트 폼 생성
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","bearer " + tokenDto.getAccessToken())
                        .content(new ObjectMapper().writeValueAsString(messageRequestDto)));

        //then
        final MvcResult mvcResult = resultActions.andExpect(status().isCreated()).andReturn();
        String a = mvcResult.getResponse().getContentAsString();
        System.out.println(a);
    }

    private Message createMessage(Member member, Member sender) {
        return Message.builder()
                .id(1L)
                .content("하이")
                .senderName(sender.getNickname())
                .senderId(sender.getId())
                .member(member)
                .delflg(MessageDeletionStatus.NOT_DELETED)
                .status(MessageReadStatus.NOT_READ).build();
    }

}