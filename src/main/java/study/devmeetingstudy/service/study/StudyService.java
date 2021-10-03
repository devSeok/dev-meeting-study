package study.devmeetingstudy.service.study;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.domain.study.enums.StudyInstanceType;
import study.devmeetingstudy.vo.StudyVO;
import study.devmeetingstudy.dto.study.request.StudySaveReqDto;
import study.devmeetingstudy.repository.study.StudyRepository;

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
}