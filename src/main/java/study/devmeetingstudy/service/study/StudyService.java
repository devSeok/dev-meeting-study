package study.devmeetingstudy.service.study;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.common.exception.global.error.exception.notfound.StudyNotFoundException;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.domain.study.enums.StudyInstanceType;
import study.devmeetingstudy.dto.study.request.StudySearchCondition;
import study.devmeetingstudy.vo.StudyReplaceVO;
import study.devmeetingstudy.vo.StudySaveVO;
import study.devmeetingstudy.repository.study.StudyRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;
    private final AddressService addressService;


    @Transactional
    public Study saveStudy(StudySaveVO studySaveVO) {
        return studyRepository.save(Study.create(studySaveVO.getStudySaveReqDto(), studySaveVO.getSubject()));
    }

    public List<Study> findStudiesByStudySearchCondition(StudySearchCondition studySearchCondition) {
        return studyRepository.findStudiesByStudySearchCondition(studySearchCondition);
    }

    public Study findStudyFetchJoinById(Long studyId) {
        return studyRepository.findStudyById(studyId).orElseThrow(() -> new StudyNotFoundException("스터디를 찾을 수 없습니다."));
    }

    public Study findStudyById(Long studyId) {
        return studyRepository.findById(studyId).orElseThrow(() -> new StudyNotFoundException("스터디를 찾을 수 없습니다"));
    }

    public boolean existsStudyByStudyId(Long studyId) {
        return studyRepository.existsStudyById(studyId);
    }

    @Transactional
    public Study replaceStudy(StudyReplaceVO studyReplaceVO, Study foundStudy) {
        return Study.replace(studyReplaceVO, foundStudy);
    }
}
