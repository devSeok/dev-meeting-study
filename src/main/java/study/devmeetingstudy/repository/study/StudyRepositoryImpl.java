package study.devmeetingstudy.repository.study;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.domain.study.enums.SortedEnum;
import study.devmeetingstudy.domain.study.enums.StudyInstanceType;
import study.devmeetingstudy.dto.study.request.StudySearchCondition;

import javax.persistence.EntityManager;
import java.util.List;

import static study.devmeetingstudy.domain.QAddress.address;
import static study.devmeetingstudy.domain.QSubject.subject;
import static study.devmeetingstudy.domain.study.QOffline.offline;
import static study.devmeetingstudy.domain.study.QOnline.online;
import static study.devmeetingstudy.domain.study.QStudy.study;

// TODO 게시글의 정렬이 최신순인지 아닌지를 판단하고, 렌더링 도중 정렬이 변경된다면,
//      아예 새로 요청하는 기능을 넣는다.
@Repository
public class StudyRepositoryImpl implements StudyRepositoryCustom{

    private final JPAQueryFactory query;

    public StudyRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public List<Study> findByStudySearchConditionDesc(StudySearchCondition studySearchCondition) {
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
                        dtypeEq(studySearchCondition.getStudyInstanceType()),
                        address1Like(studySearchCondition.getAddress1(), studySearchCondition.getStudyInstanceType())
                )
                .limit(studySearchCondition.getOffset())
                .orderBy(sorted(studySearchCondition.getSorted()))
                .fetch();
    }

    public BooleanExpression titleLike(String title) {
        return !isBlank(title) ? study.title.contains(title) : null;
    }

    private boolean isBlank(String str) {
        return str == null || !str.trim().isEmpty();
    }

    public BooleanExpression subjectIdEq(Long subjectId) {
        return subjectId != null ? study.subject.id.eq(subjectId) : null;
    }

    public BooleanExpression address1Like(String address1, StudyInstanceType dtype) {
        if (isDtypeOnline(dtype)) return null;
        return !isBlank(address1) ? offline.address.address1.contains(address1) : null;
    }

    private boolean isDtypeOnline(StudyInstanceType dtype) {
        return dtype == StudyInstanceType.ONLINE;
    }

    public BooleanExpression lastIdFactory(Long lastId, SortedEnum sortedEnum) {
        if (SortedEnum.isDesc(sortedEnum)) return lastIdLt(lastId);
        return lastIdGt(lastId);
    }

    public BooleanExpression lastIdGt(Long lastId) {
        return lastId != null ? study.id.gt(lastId) : null;
    }

    public BooleanExpression lastIdLt(Long lastId) {
        return lastId != null ? study.id.lt(lastId) : null;
    }

    public BooleanExpression dtypeEq(StudyInstanceType dtype) {
        return dtype != null ? study.dtype.eq(dtype) : null;
    }

    public OrderSpecifier<?> sorted(SortedEnum sortedEnum) {
        return sortedEnum == null || SortedEnum.isDesc(sortedEnum) ? study.id.desc() : study.id.asc();
    }
}
