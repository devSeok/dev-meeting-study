package study.devmeetingstudy.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import study.devmeetingstudy.domain.study.StudyMember;
import study.devmeetingstudy.domain.study.enums.StudyAuth;

import java.util.List;

@DataJpaTest
class StudyMemberRepositoryTest {

    @Autowired
    private StudyMemberRepository studyMemberRepository;

    @DisplayName("스터디 멤버 조회")
    @Test
    void findStudyMemberByIdAndStudyAuth() throws Exception{
        //given
        Long id = 1L;
        //when
        List<StudyMember> studyMemberByIdAndStudyAuth = studyMemberRepository.findByStudyIdAndStudyAuth(id, StudyAuth.LEADER);
        //then
    }

    @DisplayName("스터디 멤버 목록 조회")
    @Test
    void findStudyMembersByStudyIdAndStatusJOIN() throws Exception{
        //given
        Long id = 1L;
        //when
        List<StudyMember> byStudy_id = studyMemberRepository.findStudyMembersByStudyIdAndStatusJOIN(id);
        //then
    }
}