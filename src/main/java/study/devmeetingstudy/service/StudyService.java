package study.devmeetingstudy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.annotation.dto.MemberResolverDto;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.study.Offline;
import study.devmeetingstudy.domain.study.Online;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.domain.study.StudyFile;
import study.devmeetingstudy.domain.study.enums.StudyInstanceType;
import study.devmeetingstudy.dto.study.StudyVO;
import study.devmeetingstudy.dto.study.request.StudySaveReqDto;
import study.devmeetingstudy.repository.AddressRepository;
import study.devmeetingstudy.repository.StudyFileRepository;
import study.devmeetingstudy.repository.StudyRepository;
import study.devmeetingstudy.repository.SubjectRepository;

import java.util.Map;

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
        if (isInstanceOnline(studySaveReqDto.getStudyInstanceType())) {
            return studyRepository.save(Online.create(studySaveReqDto, subject));
        }
        return studyRepository.save(Offline.create(studySaveReqDto, subject, addressService.findAddress(studySaveReqDto.getAddressId())));
    }

    private boolean isInstanceOnline(StudyInstanceType studyInstanceType) {
        return studyInstanceType == StudyInstanceType.ONLINE;
    }
}
