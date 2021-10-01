package study.devmeetingstudy.repository.study;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.dto.study.request.StudySearchCondition;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class StudyRepositoryImpl implements StudyRepositoryCustom {

    private final JPAQueryFactory query;

    public StudyRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public List<Study> findByStudySearchCondition(StudySearchCondition studySearchCondition) {
        return null;
    }


}
