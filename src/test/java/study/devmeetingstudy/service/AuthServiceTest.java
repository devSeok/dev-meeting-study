package study.devmeetingstudy.service;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;
import study.devmeetingstudy.common.exception.global.error.exception.SignupDuplicateException;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.member.enums.Authority;
import study.devmeetingstudy.domain.member.enums.MemberStatus;
import study.devmeetingstudy.dto.member.request.MemberSignupRequestDto;
import study.devmeetingstudy.dto.member.response.MemberResponseDto;
import study.devmeetingstudy.jwt.TokenProvider;
import study.devmeetingstudy.repository.MemberRepository;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@Transactional
class AuthServiceTest {

    private static final String EMAIL = "tes1t@naver.com";
    private static final String PASSWORD = "1234";
    private static final String NICKNAME = "테스트";

    private final AuthService authService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final EntityManager em;
    private Member member;

    // 미리 member 빌드
    @BeforeEach
    void initializeService() {
        member = setMember(EMAIL, NICKNAME, PASSWORD);
    }

    private Member setMember(String email, String nickname, String password){
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .authority(Authority.ROLE_USER)
                .status(MemberStatus.OUT)
                .build();
    }

    // requestDto  코드
    private MemberSignupRequestDto requestDto(String email, String nickname, String password){
      return  new MemberSignupRequestDto(
                email,
                nickname,
                password
        );
    }

    @Test
    @DisplayName("회원가입")
    void signup(){
        MemberSignupRequestDto memberDto = requestDto(member.getEmail(),member.getNickname(), member.getPassword());
        Member signup = authService.signup(memberDto);

        em.flush();
        em.clear();

        Assertions.assertThat(signup.getEmail()).isEqualTo(EMAIL);
    }

    @Test
    @DisplayName("중복가입체크")
    void checkSignup(){
        MemberSignupRequestDto memberDto = requestDto(member.getEmail(), member.getNickname(), member.getPassword());
        authService.signup(memberDto);

        em.flush();
        em.clear();
        assertThrows(SignupDuplicateException.class, () ->  authService.signup(memberDto));
    }

    @Test
    @DisplayName("로그인")
    void loginNotEmpty(){
//        MemberSignupRequestDto memberDto = requestDto(member.getEmail(), member.getNickname(), member.getPassword());
//        authService.signup(memberDto);

//        em.flush();
//        em.clear();

//        TokenDto login = authService.login(memberDto, httpResponse());

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