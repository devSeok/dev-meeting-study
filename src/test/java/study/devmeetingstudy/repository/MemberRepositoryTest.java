package study.devmeetingstudy.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.dto.member.request.MemberSignupReqDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("닉네임 체크")
    @Test
    void existsNickname() throws Exception{
        //given
        PasswordEncoder pe = new BCryptPasswordEncoder();
        Member createdMember = Member.createMember(new MemberSignupReqDto("dltmddn@naver.com", "asdf", "1234"), pe);
        em.persist(createdMember);
        em.flush();
        em.clear();

        //when
        boolean asdf = memberRepository.existsByNickname("asdf");

        //then
        assertTrue(asdf);
    }
}