package study.devmeetingstudy.repository.study;

import study.devmeetingstudy.dto.study.StudyDto;
import study.devmeetingstudy.dto.study.request.StudySearchCondition;

import java.util.List;

public interface StudyRepositoryCustom {

    List<StudyDto> findByStudySearchConditionDesc(StudySearchCondition studySearchCondition);
}
