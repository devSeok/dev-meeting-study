package study.devmeetingstudy.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import study.devmeetingstudy.annotation.dto.MemberResolverDto;
import study.devmeetingstudy.common.exception.global.error.exception.UserException;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.member.enums.Authority;
import study.devmeetingstudy.domain.member.enums.MemberStatus;
import study.devmeetingstudy.repository.MemberRepository;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    private static String EMAIL = "test@naver.com";

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;


    private Member buildMember(String email, String password, String nickname) {
        return Member.builder()
                .id(1L)
                .email(email)
                .password(password)
                .authority(Authority.ROLE_USER)
                .grade(0)
                .status(MemberStatus.ACTIVE)
                .nickname(nickname)
                .build();
    }

    @Test
    @DisplayName("이메일로 맴버 찾기")
    void memberInfoTest() {

        // given
        given(memberRepository.findByEmail(anyString())).willReturn(
                Optional.of(buildMember(EMAIL, "1234", "1234"))
        );

        // whe
        Member memberInfo = memberService.getMemberInfo(EMAIL);

        Assertions.assertThat(memberInfo.getEmail()).isEqualTo(EMAIL);
    }

    @Test
    @DisplayName("유저 정보가 없는 에러")
    void memberInfoCTest() {

        // given
        given(memberRepository.findByEmail(anyString())).willThrow(
                new UserException("유저 정보가 없습니다.")
        );
        
        try {
            // when
            memberService.getMemberInfo(EMAIL);
        } catch (UserException u){
            Assertions.assertThat(u.getMessage()).isEqualTo("유저 정보가 없습니다.");
        }
    }

    @Test
    @DisplayName("맴버 status OUT update 회원탈퇴")
    void deleteMemberTest(){

        // given
        given(memberRepository.findById(anyLong())).willReturn(
                Optional.of(buildMember(EMAIL, "1234", "1234"))
        );
        MemberResolverDto memberResolverDto = new MemberResolverDto(1L, "1234");

        // when
        memberService.deleteMember(memberResolverDto);

        //then
        Optional<Member> findMember = memberRepository.findById(1L);
        Assertions.assertThat(findMember.get().getStatus()).isEqualTo(MemberStatus.OUT);
    }
}