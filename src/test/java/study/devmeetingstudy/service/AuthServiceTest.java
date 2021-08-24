package study.devmeetingstudy.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.member.enums.Authority;
import study.devmeetingstudy.domain.member.enums.MemberStatus;
import study.devmeetingstudy.dto.member.MemberRequestDto;
import study.devmeetingstudy.dto.member.MemberResponseDto;
import study.devmeetingstudy.service.message.MessageService;

import javax.persistence.EntityManager;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@Transactional
class AuthServiceTest {

    private final AuthService authService;
    private final EntityManager em;
    private Member member;

    // 미리 member 빌드
    @BeforeEach
    void initializeService() {
        member = setMember("tes1t@naver.com", "1234");

    }

    private Member setMember(String email, String password){
        return Member.builder()
                .email(email)
                .password(password)
                .authority(Authority.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .build();
    }

    @Test
    @Transactional
    void 회원가입(){
        MemberRequestDto memberRequestDto = new MemberRequestDto(
                member.getEmail(),
                member.getPassword()
        );
        MemberResponseDto signup = authService.signup(memberRequestDto);

        em.flush();
        em.clear();

        System.out.println(signup.getEmail());


    }


}