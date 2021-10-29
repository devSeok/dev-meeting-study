package study.devmeetingstudy.repository.study;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.domain.enums.DeletionStatus;
import study.devmeetingstudy.domain.member.QMember;
import study.devmeetingstudy.domain.study.QStudyMember;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.domain.study.StudyMember;
import study.devmeetingstudy.domain.study.enums.SortedEnum;
import study.devmeetingstudy.domain.study.enums.StudyInstanceType;
import study.devmeetingstudy.domain.study.enums.StudyType;
import study.devmeetingstudy.dto.study.request.StudySearchCondition;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static study.devmeetingstudy.domain.QAddress.address;
import static study.devmeetingstudy.domain.QSubject.subject;
import static study.devmeetingstudy.domain.member.QMember.member;
import static study.devmeetingstudy.domain.study.QOffline.offline;
import static study.devmeetingstudy.domain.study.QOnline.online;
import static study.devmeetingstudy.domain.study.QStudy.study;
import static study.devmeetingstudy.domain.study.QStudyMember.studyMember;

@Repository
@Transactional(readOnly = true)
public class StudyRepositoryImpl implements StudyRepositoryCustom{

    private final JPAQueryFactory query;

    public StudyRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<Study> findStudiesByStudySearchCondition(StudySearchCondition studySearchCondition) {
        return query
                .selectFrom(study)
                .leftJoin(study.offline, offline).fetchJoin()
                .leftJoin(study.online, online).fetchJoin()
                .leftJoin(offline.address, address).fetchJoin()
                .leftJoin(study.subject, subject).fetchJoin()
                .where(
                        titleLike(studySearchCondition.getTitle()),
                        subjectIdEq(studySearchCondition.getSubjectId()),
                        lastIdFactory(studySearchCondition.getLastId(), studySearchCondition.getSorted()),
                        dtypeEq(studySearchCondition.getDtype()),
                        address1Like(studySearchCondition.getAddress1(), studySearchCondition.getDtype()),
                        studyTypeEq(studySearchCondition.getStudyType()),
                        study.deletionStatus.eq(DeletionStatus.NOT_DELETED)
                )
                .limit(studySearchCondition.getOffset())
                .orderBy(sorted(studySearchCondition.getSorted()))
                .fetch();
    }

    private BooleanExpression titleLike(String title) {
        return !isBlank(title) ? study.title.contains(title) : null;
    }

    private boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    private BooleanExpression subjectIdEq(Long subjectId) {
        return subjectId != null ? study.subject.id.eq(subjectId) : null;
    }

    private BooleanExpression address1Like(String address1, StudyInstanceType dtype) {
        if (isDtypeOnline(dtype)) return null;
        return !isBlank(address1) ? offline.address.address1.contains(address1) : null;
    }

    private boolean isDtypeOnline(StudyInstanceType dtype) {
        return dtype == StudyInstanceType.ONLINE;
    }

    private BooleanExpression lastIdFactory(Long lastId, SortedEnum sortedEnum) {
        if (SortedEnum.isDesc(sortedEnum)) return lastIdLt(lastId);
        return lastIdGt(lastId);
    }

    private BooleanExpression lastIdGt(Long lastId) {
        return lastId != null ? study.id.gt(lastId) : null;
    }

    private BooleanExpression lastIdLt(Long lastId) {
        return lastId != null ? study.id.lt(lastId) : null;
    }

    private BooleanExpression dtypeEq(StudyInstanceType dtype) {
        return dtype != null ? study.dtype.eq(dtype) : null;
    }

    private OrderSpecifier<?> sorted(SortedEnum sortedEnum) {
        return sortedEnum == null || SortedEnum.isDesc(sortedEnum) ? study.id.desc() : study.id.asc();
    }

    private BooleanExpression studyTypeEq(StudyType studyType) {
        return studyType != null ? study.studyType.eq(studyType) : null;
    }

    @Override
    public Optional<Study> findStudyById(Long studyId) {
        return Optional.ofNullable(
                query
                .selectFrom(study)
                .leftJoin(study.offline, offline).fetchJoin()
                .leftJoin(study.online, online).fetchJoin()
                .leftJoin(offline.address, address).fetchJoin()
                .leftJoin(study.subject, subject).fetchJoin()
                .where(
                        study.id.eq(studyId),
                        study.deletionStatus.eq(DeletionStatus.NOT_DELETED)
                )
                .fetchOne()
        );
    }

}
