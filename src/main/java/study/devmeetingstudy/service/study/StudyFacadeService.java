package study.devmeetingstudy.service.study;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.common.uploader.Uploader;
import study.devmeetingstudy.domain.enums.DomainType;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.study.Online;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.domain.study.StudyFile;
import study.devmeetingstudy.domain.study.StudyMember;
import study.devmeetingstudy.dto.study.CreatedStudyDto;
import study.devmeetingstudy.vo.StudyVO;
import study.devmeetingstudy.dto.study.request.StudySaveReqDto;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyFacadeService {

    private final StudyService studyService;
    private final AddressService addressService;
    private final OfflineService offlineService;
    private final OnlineService onlineService;
    private final StudyFileService studyFileService;
    private final StudyMemberService studyMemberService;
    private final SubjectService subjectService;
    private final Uploader uploader;

    @Transactional
    public CreatedStudyDto store(StudySaveReqDto studySaveReqDto, Member loginMember) throws IOException {

        Map<String, String> uploadFileInfo = uploader.upload(studySaveReqDto.getFile(), DomainType.STUDY.value());
        Study createdStudy = studyService.saveStudy(StudyVO.of(studySaveReqDto, subjectService.findSubject(studySaveReqDto.getSubjectId())));
        StudyFile studyFile = studyFileService.saveStudyFile(createdStudy, uploadFileInfo);
        StudyMember studyMember = studyMemberService.saveStudyLeader(loginMember, createdStudy);
        if (Study.isDtypeOnline(createdStudy)) {
            return CreatedStudyDto.builder()
                    .study(createdStudy)
                    .studyFile(studyFile)
                    .online(onlineService.saveOnline(studySaveReqDto, createdStudy))
                    .studyMember(studyMember)
                    .build();
        }
        return CreatedStudyDto.builder()
                .study(createdStudy)
                .studyFile(studyFile)
                .offline(offlineService.saveOffline(addressService.findAddress(studySaveReqDto.getAddressId()), createdStudy))
                .studyMember(studyMember)
                .build();
    }


}
