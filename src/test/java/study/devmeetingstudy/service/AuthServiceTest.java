package study.devmeetingstudy.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.member.enums.Authority;
import study.devmeetingstudy.domain.member.enums.MemberStatus;
import study.devmeetingstudy.repository.MemberRepository;

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