package study.devmeetingstudy.service.study;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.common.exception.global.error.exception.StudyNotFoundException;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.domain.study.enums.StudyInstanceType;
import study.devmeetingstudy.dto.study.request.StudySearchCondition;
import study.devmeetingstudy.vo.StudyVO;
import study.devmeetingstudy.repository.study.StudyRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;
    private final AddressService addressService;


    @Transactional
    public Study saveStudy(StudyVO studyVO) {
        return studyRepository.save(Study.create(studyVO.getStudySaveReqDto(), studyVO.getSubject()));
    }

    private boolean isInstanceOnline(StudyInstanceType studyInstanceType) {
        return studyInstanceType == StudyInstanceType.ONLINE;
    }

    public List<Study> findStudiesByStudySearchCondition(StudySearchCondition studySearchCondition) {
        return studyRepository.findStudiesByStudySearchCondition(studySearchCondition);
    }

    public Study findStudyById(Long studyId) {
        return studyRepository.findStudyById(studyId).orElseThrow(() -> new StudyNotFoundException("스터디를 찾을 수 없습니다."));
    }
}
