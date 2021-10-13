package study.devmeetingstudy.repository.study;

import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.dto.study.request.StudySearchCondition;

import java.util.List;
import java.util.Optional;

public interface StudyRepositoryCustom {

    List<Study> findStudiesByStudySearchCondition(StudySearchCondition studySearchCondition);
    Optional<Study> findStudyById(Long studyId);
}
