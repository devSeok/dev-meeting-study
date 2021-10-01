package study.devmeetingstudy.repository.study;

import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.dto.study.request.StudySearchCondition;

import java.util.List;

public interface StudyRepositoryCustom {

    List<Study> findByStudySearchCondition(StudySearchCondition studySearchCondition);
}
