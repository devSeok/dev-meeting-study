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
import study.devmeetingstudy.annotation.dto.MemberResolverDto;
import study.devmeetingstudy.annotation.handlerMethod.MemberDecodeResolver;
import study.devmeetingstudy.common.exception.global.error.CustomGlobalExceptionHandler;
import study.devmeetingstudy.common.exception.global.error.exception.UserInfoMismatchException;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.member.enums.Authority;
import study.devmeetingstudy.domain.member.enums.MemberStatus;
import study.devmeetingstudy.domain.message.Message;
import study.devmeetingstudy.domain.message.enums.MessageDeletionStatus;
import study.devmeetingstudy.domain.message.enums.MessageReadStatus;
import study.devmeetingstudy.dto.message.MessageRequestDto;
import study.devmeetingstudy.service.AuthService;
import study.devmeetingstudy.vo.MessageVO;
import study.devmeetingstudy.dto.token.TokenDto;
import study.devmeetingstudy.jwt.TokenProvider;
import study.devmeetingstudy.repository.MemberRepository;
import study.devmeetingstudy.service.MemberService;
import study.devmeetingstudy.service.MessageService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


// 테스트 Mockito 결합
@ExtendWith(MockitoExtension.class)
class MessageControllerUnitTest {
    /** TODO: 멤버 인증 처리 관련 테스트 케이스 작성 모두 완료?
     *      1. 메시지 보내기
     *      2. 메시지 목록
     *          -
     *      3. 메시지 조회
 *              - 메시지 읽음 상태 수정
     *      4. 메시지 삭제 상태 수정
     *
     */

    @InjectMocks
    private MessageController messageApiController;

    @Mock
    private MessageService messageService;

    @Mock
    private MemberService memberService;

    //ArgumentResolver 호출 위해서
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private AuthService authService;

    private MockMvc mockMvc;

    private Member loginMember;

    private Member member;

    private TokenProvider tokenProvider;

    @BeforeEach
    void init(){
        // 만든 ArgumentResolver 이용.
        tokenProvider = new TokenProvider("c3ByaW5nLWJvb3Qtc2VjdXJpdHktand0LXR1dG9yaWFsLWppd29vbi1zcHJpbmctYm9vdC1zZWN1cml0eS1qd3QtdHV0b3JpYWwK");
        mockMvc = MockMvcBuilders.standaloneSetup(messageApiController)
                .addFilter((((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                })))
                .setCustomArgumentResolvers(new MemberDecodeResolver(tokenProvider, memberRepository))
                .setControllerAdvice(new CustomGlobalExceptionHandler())
                .build();
        // 메시지를 받는 멤버
        member = createMember(1L, "dltmddn@na.na", "nick1");
        // 메시지를 보내는 멤버
        loginMember = createMember(2L, "xonic@na.na", "nick2");
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

    @DisplayName("메시지 보내기 201 created")
    @Test
    void sendMessage() throws Exception{
        //given
        // 토큰 생성 및 발급
        // 현재 로그인 한 멤버는 sender 이다.
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(loginMember.getAuthority().toString());
        Authentication token = new UsernamePasswordAuthenticationToken(loginMember.getId(), loginMember.getPassword(), Collections.singleton(grantedAuthority));
        // sender 기반으로 token 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(token);

        MessageRequestDto messageRequestDto = new MessageRequestDto("dltmddn@na.na", "Hello");

        // mock 객체가 특정한 값을 반환해야 하는 경우.
        Message message = createMessage(1L, member, loginMember);
        // controller에서 호출한 getUserOne 모킹
        // sender userOne
        // member memberInfo
        doReturn(Optional.of(loginMember)).when(memberRepository).findById(anyLong());
        doReturn(loginMember).when(memberService).getUserOne(any(Long.class));
        doReturn(member).when(memberService).getMemberInfo(any(String.class));
        // 새로 생성된 메시지가 리턴됨.
        doReturn(message).when(messageService).send(any(MessageVO.class));

        //when
        // 요청 리퀘스트 폼 생성
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","bearer " + tokenDto.getAccessToken())
                        .content(new ObjectMapper().writeValueAsString(messageRequestDto)));

        //then
        final MvcResult mvcResult = resultActions.andExpect(status().isCreated()).andReturn();
        String body = mvcResult.getResponse().getContentAsString();
        // jackson에서 왜 No Argument Constructor 가 필요한지 알아보기.
        JSONObject data = (JSONObject) getDataOfJSON(body);
        Long id = (Long) data.get("id");
        assertEquals(1L, id);
    }

    private Object getDataOfJSON(String body) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject json = (JSONObject) jsonParser.parse(body);
        return json.get("data");
    }

    private Message createMessage(Long id, Member member, Member sender) {
        return Message.builder()
                .id(id)
                .content("하이")
                .senderName(sender.getNickname())
                .senderId(sender.getId())
                .member(member)
                .delflg(MessageDeletionStatus.NOT_DELETED)
                .status(MessageReadStatus.NOT_READ).createdDate(LocalDateTime.now()).lastUpdateDate(LocalDateTime.now()).build();
    }

    @DisplayName("메시지 목록 200 Ok")
    @Test
    void showMessages() throws Exception {
        // 메시지 5개 생성
        //given
        List<Message> messages = new ArrayList<>();
        for (long i = 1; i <= 5; i++){
            messages.add(createMessage(i, member, loginMember));
        }
        // 토큰 생성 및 발급
        // 현재 로그인 한 멤버는 member 이다.
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getAuthority().toString());
        Authentication token = new UsernamePasswordAuthenticationToken(member.getId(), member.getPassword(), Collections.singleton(grantedAuthority));
        // member 기반으로 token 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(token);
        doReturn(Optional.of(loginMember)).when(memberRepository).findById(anyLong());
        doReturn(member).doReturn(loginMember).doReturn(loginMember).doReturn(loginMember).doReturn(loginMember).doReturn(loginMember).when(memberService).getUserOne(any(Long.class));
        doReturn(messages).when(messageService).findMessages(any(Member.class));

        //when
        // 요청 리퀘스트 폼 생성
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","bearer " + tokenDto.getAccessToken()));
        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String body = mvcResult.getResponse().getContentAsString();
        JSONArray data = (JSONArray) getDataOfJSON(body);

        assertEquals(5, data.size());
    }


    @DisplayName("메시지 조회 200 Ok")
    @Test
    void showMessage() throws Exception{
        //given
        // 메시지 생성
        Message createdMessage = createMessage(1L, loginMember, member);

        //생성 후 메시지 읽음 상태 수정
        Message readMessage = Message.changeReadStatus(MessageReadStatus.READ, createdMessage);
        // 토큰 생성 및 발급
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(loginMember.getAuthority().toString());
        Authentication token = new UsernamePasswordAuthenticationToken(loginMember.getId(), loginMember.getPassword(), Collections.singleton(grantedAuthority));
        // loginMember 기반으로 token 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(token);
        doReturn(Optional.of(loginMember)).when(memberRepository).findById(anyLong());
        doReturn(loginMember).doReturn(member).when(memberService).getUserOne(anyLong());
        doReturn(readMessage).when(messageService).findMessage(anyLong());

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/messages/" + createdMessage.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "bearer " + tokenDto.getAccessToken()));

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String body = mvcResult.getResponse().getContentAsString();
        JSONObject data = (JSONObject) getDataOfJSON(body);

        assertEquals(MessageReadStatus.READ.toString(), data.get("status"));
    }

    @DisplayName("메시지 삭제 204 No Content")
    @Test
    void deleteMessage() throws Exception{
        //given
        // 메시지 생성
        Message createdMessage = createMessage(1L, loginMember, member);
        //생성 후 메시지 삭제 상태 수정
        Message deletedMessage = Message.changeDeletionStatus(MessageDeletionStatus.DELETED, createdMessage);
        // 토큰 생성 및 발급
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(loginMember.getAuthority().toString());
        Authentication token = new UsernamePasswordAuthenticationToken(loginMember.getId(), loginMember.getPassword(), Collections.singleton(grantedAuthority));
        // loginMember 기반으로 token 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(token);
        doReturn(Optional.of(loginMember)).when(memberRepository).findById(anyLong());
        doReturn(createdMessage).when(messageService).findMessage(anyLong());
        doNothing().when(authService).checkUserInfo(anyLong(), any(MemberResolverDto.class));
        doReturn(deletedMessage).when(messageService).deleteMessage(any(Message.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/messages/" + createdMessage.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "bearer " + tokenDto.getAccessToken()));

        //then
        resultActions.andExpect(status().isNoContent()).andReturn();
    }

    @DisplayName("메시지 삭제 400 Bad Request")
    @Test
    void deleteMessage_UserInfoNotFoundException() throws Exception{
        //given
        // 메시지 생성
        Message createdMessage = createMessage(1L, member, loginMember);
        //생성 후 메시지 삭제 상태 수정
        Message deletedMessage = Message.changeDeletionStatus(MessageDeletionStatus.DELETED, createdMessage);
        // 토큰 생성 및 발급
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(loginMember.getAuthority().toString());
        Authentication token = new UsernamePasswordAuthenticationToken(loginMember.getId(), loginMember.getPassword(), Collections.singleton(grantedAuthority));
        // loginMember 기반으로 token 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(token);
        // sender 입장에서 확인한다.
        doReturn(Optional.of(loginMember)).when(memberRepository).findById(anyLong());
        doReturn(createdMessage).when(messageService).findMessage(anyLong());
        doThrow(new UserInfoMismatchException("유저 정보가 일치하지 않습니다.")).when(authService).checkUserInfo(anyLong(), any(MemberResolverDto.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/messages/" + createdMessage.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "bearer " + tokenDto.getAccessToken()));

        //then
        resultActions.andExpect(status().isBadRequest()).andReturn();
    }

}