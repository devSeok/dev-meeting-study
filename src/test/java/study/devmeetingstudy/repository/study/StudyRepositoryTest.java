package study.devmeetingstudy.repository.study;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.domain.Address;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.domain.study.enums.StudyInstanceType;
import study.devmeetingstudy.domain.study.enums.StudyType;
import study.devmeetingstudy.dto.address.AddressReqDto;
import study.devmeetingstudy.dto.study.request.StudySaveReqDto;
import study.devmeetingstudy.dto.study.request.StudySearchCondition;
import study.devmeetingstudy.dto.subject.SubjectReqDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
// 해당 테스트에서, Generated key가 공유되기 때문에
class StudyRepositoryTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private StudyRepository studyRepository;

    @DisplayName("스터디 검색 조건으로 스터디 목록 조회")
    @Test
    @Transactional
    void findByStudySearchConditionDesc() throws Exception {

        AddressReqDto addressReqDto = new AddressReqDto("서울시", "강남구", "서초동");
        SubjectReqDto subjectReqDto = new SubjectReqDto(1L, "Java");
        Address address = Address.create(addressReqDto);
        Subject subject = Subject.create(subjectReqDto);
        em.persist(address);
        em.persist(subject);

        for (int i = 0; i < 10; i++) {
            Study study = Study.create(getMockOnlineReqDto(subjectReqDto), subject);
            studyRepository.save(study);
        }

        /* TODO 스터디파일, 스터디 맴버 넣어주기
                스터디 컨디션 적용하기
                1. title 검색 (like)
                2. lastIdgt studyId
                3. offset
        */
        StudySearchCondition searchCondition = StudySearchCondition.builder()
                .title("자바")
                .dtype(StudyInstanceType.ONLINE)
                .offset(4)
                .build();

        List<Study> studies = studyRepository.findStudiesByStudySearchCondition(searchCondition);

        assertEquals(4, studies.size());
    }

    @DisplayName("스터디 조회")
    @Test
    @Transactional
    void findStudyById() throws Exception{
        //given
        AddressReqDto addressReqDto = new AddressReqDto("서울시", "강남구", "서초동");
        SubjectReqDto subjectReqDto = new SubjectReqDto( "Java");
        Subject subject = Subject.create(subjectReqDto);
        em.persist(subject);
        Address address = Address.create(addressReqDto);
        em.persist(address);
        Study createdStudy = studyRepository.save(Study.create(getMockOnlineReqDto(subjectReqDto), subject));
        em.flush();
        em.clear();
        //when
        Optional<Study> findStudy = studyRepository.findStudyById(createdStudy.getId());

        //then
        assertNotNull(findStudy.get());
    }

    private StudySaveReqDto getMockOnlineReqDto(SubjectReqDto subjectReqDto) {
        return StudySaveReqDto.builder()
                .title("자바 스터디원 구합니다")
                .maxMember(5)
                .startDate(LocalDate.of(2021, 9, 24))
                .endDate(LocalDate.of(2021, 10, 25))
                .studyType(StudyType.FREE)
                .dtype(StudyInstanceType.ONLINE)
                .subjectId(subjectReqDto.getId())
                .link("https://dfsdf.sdfd.d.")
                .onlineType("디스코드")
                .content("자바 스터디원 모집합니다 현재 2분 남았습니다.")
                .build();
    }

    @DisplayName("스터디가 이미 존재하는지 확인 boolean")
    @Test
    void existStudyById() throws Exception {
        //given
        System.out.println(studyRepository.existsStudyById(1L));
        //when
        //then
    }

}