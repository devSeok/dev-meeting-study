package study.devmeetingstudy.repository.study;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.devmeetingstudy.domain.Address;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.domain.study.enums.StudyInstanceType;
import study.devmeetingstudy.domain.study.enums.StudyType;
import study.devmeetingstudy.dto.address.AddressReqDto;
import study.devmeetingstudy.vo.StudyVO;
import study.devmeetingstudy.dto.study.request.StudySaveReqDto;
import study.devmeetingstudy.dto.study.request.StudySearchCondition;
import study.devmeetingstudy.dto.subject.SubjectReqDto;
import study.devmeetingstudy.service.study.AddressService;
import study.devmeetingstudy.service.study.StudyService;
import study.devmeetingstudy.service.study.SubjectService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDate;
import java.util.List;

import static study.devmeetingstudy.domain.study.QOffline.offline;
import static study.devmeetingstudy.domain.study.QOnline.online;
import static study.devmeetingstudy.domain.study.QStudy.study;

@SpringBootTest
class StudyRepositoryTest {

    @PersistenceContext
    private EntityManager em;

    private JPAQueryFactory queryFactory;

    @Autowired
    private StudyService studyService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private StudyRepository studyRepository;

    @BeforeEach
    void before(){
        queryFactory = new JPAQueryFactory(em);
    }

    @DisplayName("테스트")
    @Test
    void findByStudySearchCondition() throws Exception {

        AddressReqDto addressReqDto = new AddressReqDto("서울시", "강남구", "서초동");
        SubjectReqDto subjectReqDto = new SubjectReqDto(1L, "Java");
        Subject subject = subjectService.saveSubject(subjectReqDto);
        Address address = addressService.saveAddress(addressReqDto);


        for (int i = 0; i < 10; i++) {
            studyService.saveStudy(StudyVO.of(getMockOnlineReqDto(subjectReqDto), subject));
        }

        /* TODO 스터디파일, 스터디 맴버 넣어주기
                스터디 컨디션 적용하기
                1. title 검색 (like)
                2. lastIdgt studyId
                3. offset

        */
        StudySearchCondition searchCondition = StudySearchCondition.builder()
                .title("자바")
                .dtype(StudyInstanceType.OFFLINE)
                .offset(4)
                .build();

        List<Study> studies = studyRepository.findByStudySearchConditionDesc(searchCondition);

        System.out.println(studies);
    }

    @DisplayName("테스트2")
    @Test
    void test() throws Exception{
        //given
        List<Study> fetch = queryFactory.selectFrom(study)
                .leftJoin(study.offline, offline).fetchJoin()
                .leftJoin(study.online, online).fetchJoin()
                .fetch();
        //when
        //then
        System.out.println(fetch);
    }

    private StudySaveReqDto getMockOnlineReqDto(SubjectReqDto subjectReqDto) {
        return StudySaveReqDto.builder()
                .title("자바 스터디원 구합니다")
                .maxMember(5)
                .startDate(LocalDate.of(2021, 9, 24))
                .endDate(LocalDate.of(2021, 10, 25))
                .studyType(StudyType.FREE)
                .studyInstanceType(StudyInstanceType.ONLINE)
                .subjectId(subjectReqDto.getId())
                .link("https://dfsdf.sdfd.d.")
                .onlineType("디스코드")
                .content("자바 스터디원 모집합니다 현재 2분 남았습니다.")
                .build();
    }

}