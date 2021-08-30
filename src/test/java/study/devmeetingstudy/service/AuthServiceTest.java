package study.devmeetingstudy.service;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;
import study.devmeetingstudy.common.exception.global.error.exception.SignupDuplicateException;
import study.devmeetingstudy.controller.MemberController;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.member.enums.Authority;
import study.devmeetingstudy.domain.member.enums.MemberStatus;
import study.devmeetingstudy.dto.member.request.MemberSignupRequestDto;
import study.devmeetingstudy.dto.member.response.MemberResponseDto;
import study.devmeetingstudy.jwt.TokenProvider;
import study.devmeetingstudy.repository.MemberRepository;
import study.devmeetingstudy.repository.RefreshTokenRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {


    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberService memberService;


    @Test
    public void test(){

         Member member = Member.builder()
                .email("esdsd@nave.ckm")
                .nickname("tsesds")
                .password("test")
                .authority(Authority.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .build();

    }
}