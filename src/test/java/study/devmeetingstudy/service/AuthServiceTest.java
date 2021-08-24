package study.devmeetingstudy.service;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;
import study.devmeetingstudy.common.exception.global.error.exception.MessageNotFoundException;
import study.devmeetingstudy.common.exception.global.error.exception.SignupDuplicateException;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.member.enums.Authority;
import study.devmeetingstudy.domain.member.enums.MemberStatus;
import study.devmeetingstudy.dto.member.MemberRequestDto;
import study.devmeetingstudy.dto.member.MemberResponseDto;
import study.devmeetingstudy.dto.token.TokenDto;
import study.devmeetingstudy.jwt.TokenProvider;
import study.devmeetingstudy.repository.MemberRepository;
import study.devmeetingstudy.service.message.MessageService;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@Transactional
class AuthServiceTest {

    private static final String EMAIL = "tes1t@naver.com";
    private static final String PASSWORD = "1234";

    private final AuthService authService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final EntityManager em;
    private Member member;

    // 미리 member 빌드
    @BeforeEach
    void initializeService() {
        member = setMember(EMAIL, PASSWORD);
    }

    private Member setMember(String email, String password){
        return Member.builder()
                .email(email)
                .password(password)
                .authority(Authority.ROLE_USER)
                .status(MemberStatus.OUT)
                .build();
    }

    // requestDto  코드
    private MemberRequestDto requestDto(String email, String password){
      return  new MemberRequestDto(
                email,
                password
        );
    }

    @Test
    @DisplayName("회원가입")
    void signup(){
        MemberRequestDto memberDto = requestDto(member.getEmail(), member.getPassword());
        MemberResponseDto signup = authService.signup(memberDto);

        em.flush();
        em.clear();

        Assertions.assertThat(signup.getEmail()).isEqualTo(EMAIL);
    }

    @Test
    @DisplayName("중복가입체크")
    void checkSignup(){
        MemberRequestDto memberDto = requestDto(member.getEmail(), member.getPassword());
        authService.signup(memberDto);

        em.flush();
        em.clear();
        assertThrows(SignupDuplicateException.class, () ->  authService.signup(memberDto));
    }

    @Test
    @DisplayName("로그인")
    void loginNotEmpty(){
        MemberRequestDto memberDto = requestDto(member.getEmail(), member.getPassword());
        authService.signup(memberDto);

        em.flush();
        em.clear();

        TokenDto login = authService.login(memberDto, httpResponse());

        //TODO : 어떤값을 비교할 대상을 정하지 못했다.
        // 다른 방법은 api에서 호출되는 토큰값이랑 서비스에서 호출되는 토큰값이랑 비교를 해야되나? 생각이 든다.
        // 아직 시도는 해보지 않았지만, 추후 비교를 해보자
    }

    private HttpServletResponse httpResponse(){
        ServletWebRequest servletContainer = (ServletWebRequest) RequestContextHolder.getRequestAttributes();
        assert servletContainer != null;
        return servletContainer.getResponse();
    }






}