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
        List<StudyMember> studyMemberByIdAndStudyAuth = studyMemberRepository.findStudyMembersByStudyIdAndStudyAuth(id, StudyAuth.LEADER);
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

    @DisplayName("멤버가 스터디 멤버인지")
    @Test
    void existsStudyMemberByStudy_IdAndMember_Id() throws Exception {
        //given

        //when
        studyMemberRepository.existsStudyMemberByStudy_IdAndMember_Id(1L, 1L);
        //then
    }

    @DisplayName("해당 스터디의 스터디 멤버 카운트")
    @Test
    void countStudyMembersByStudyIdAndStatusJOIN() throws Exception {
        //given

        //when
        studyMemberRepository.countStudyMembersByStudyIdAndStatusJOIN(1L);
        //then
    }

    @DisplayName("멤버 아이디에 해당하는 스터디 맴버 조회")
    @Test
    void findStudyMemberByMemberId() throws Exception {
        //given

        //when
        studyMemberRepository.findStudyMemberByStudyIdAndMemberId(1L, 1L);
        //then
    }

    @DisplayName("멤버 아이디에 해당하는 스터디 멤버 조회 (내 스터디 목록)")
    @Test
    void findStudyMembersByMemberId() throws Exception {
        //given

        //when

        studyMemberRepository.findStudyMembersByMemberId(1L);
        //then
    }
}