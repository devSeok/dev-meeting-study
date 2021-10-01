package study.devmeetingstudy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.study.Offline;
import study.devmeetingstudy.domain.study.Online;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.domain.study.enums.StudyInstanceType;
import study.devmeetingstudy.dto.study.StudyVO;
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
        StudySaveReqDto studySaveReqDto = studyVO.getStudySaveReqDto();
        Subject subject = studyVO.getSubject();
        return studyRepository.save(Study.create(studySaveReqDto, subject));
    }

    private boolean isInstanceOnline(StudyInstanceType studyInstanceType) {
        return studyInstanceType == StudyInstanceType.ONLINE;
    }
}
