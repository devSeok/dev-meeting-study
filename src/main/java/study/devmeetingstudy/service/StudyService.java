package study.devmeetingstudy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.annotation.dto.MemberResolverDto;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.dto.study.request.StudySaveRequestDto;
import study.devmeetingstudy.repository.AddressRepository;
import study.devmeetingstudy.repository.StudyRepository;
import study.devmeetingstudy.repository.SubjectRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyService {

    private final SubjectRepository subjectRepository;
    private final StudyRepository studyRepository;
    private final AddressRepository addressRepository;

    public Study saveStudy(StudySaveRequestDto dto, MemberResolverDto resolverDto){

        // 카테고리 찾기
//        Optional<Subject> subjects = subjectRepository.findById(1L);
//        // 온라인일때 저장
//
//        Study online = Online.createOnline(dto, subjects);
//        Study save = studyRepository.save(online);
//        if (dto.getStudyInstanceType().equals("Offline")) {
//
//            // 해당 프론트엔드에서 address 값에 시퀀스를 넘겨줘야된다. 그럼 해당 값을 찾아서 보내줘야된다.
//            // 1L 값은 임의로 테스트한 갑이다.`
//            Optional<Address> findAddress = addressRepository.findById(1L);
//            Study offline = Offline.createOffline(dto, subjects, findAddress);
//            save = studyRepository.save(offline);
//        }
        return null;
    }
}
