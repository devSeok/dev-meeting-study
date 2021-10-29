package study.devmeetingstudy.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.domain.QAddress;
import study.devmeetingstudy.domain.study.StudyMember;

import javax.persistence.EntityManager;
import java.util.List;

import static study.devmeetingstudy.domain.QAddress.*;
import static study.devmeetingstudy.domain.QSubject.subject;
import static study.devmeetingstudy.domain.member.QMember.member;
import static study.devmeetingstudy.domain.study.QOffline.offline;
import static study.devmeetingstudy.domain.study.QOnline.online;
import static study.devmeetingstudy.domain.study.QStudy.study;
import static study.devmeetingstudy.domain.study.QStudyMember.studyMember;

@Repository
@Transactional(readOnly = true)
public class StudyMemberRepositoryImpl implements StudyMemberRepositoryCustom{

    private final JPAQueryFactory query;

    public StudyMemberRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }


    @Override
    public List<StudyMember> findStudyMembersByMemberId(Long memberId) {
        return query
                .selectFrom(studyMember)
                .leftJoin(studyMember.study, study).fetchJoin()
                .leftJoin(studyMember.member, member).fetchJoin()
                .leftJoin(study.online, online).fetchJoin()
                .leftJoin(study.offline, offline).fetchJoin()
                .leftJoin(study.subject, subject).fetchJoin()
                .leftJoin(offline.address, address).fetchJoin()
                .where(
                        studyMember.member.id.eq(memberId)
                )
                .fetch();
    }
}
