package study.devmeetingstudy.repository.study;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import study.devmeetingstudy.domain.QAddress;
import study.devmeetingstudy.domain.QSubject;
import study.devmeetingstudy.domain.study.*;
import study.devmeetingstudy.dto.QStudyDto;
import study.devmeetingstudy.dto.StudyDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static study.devmeetingstudy.domain.QAddress.address;
import static study.devmeetingstudy.domain.QSubject.subject;
import static study.devmeetingstudy.domain.study.QOffline.offline;
import static study.devmeetingstudy.domain.study.QOnline.online;
import static study.devmeetingstudy.domain.study.QStudy.study;
import static study.devmeetingstudy.domain.study.QStudyFile.studyFile;
import static study.devmeetingstudy.domain.study.QStudyMember.studyMember;

@DataJpaTest
class StudyRepositoryTest {

    @PersistenceContext
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    void before(){
        queryFactory = new JPAQueryFactory(em);
    }

    @DisplayName("테스트")
    @Test
    void findByStudySearchCondition() throws Exception {
        for (int i = 0; i < 10; i++) {

        }
        List resultList = em.createQuery("select s from Study s").getResultList();

        //when
        //then
        System.out.println(resultList);
    }

}