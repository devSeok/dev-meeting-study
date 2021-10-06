package study.devmeetingstudy.service.study;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.common.uploader.Uploader;
import study.devmeetingstudy.domain.enums.DomainType;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.domain.study.StudyFile;
import study.devmeetingstudy.domain.study.StudyMember;
import study.devmeetingstudy.domain.study.enums.StudyAuth;
import study.devmeetingstudy.dto.study.CreatedStudyDto;
import study.devmeetingstudy.dto.study.StudyDto;
import study.devmeetingstudy.dto.study.request.StudySearchCondition;
import study.devmeetingstudy.vo.StudyVO;
import study.devmeetingstudy.dto.study.request.StudySaveReqDto;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        return CreatedStudyDto.builder()
                .study(createdStudy)
                .studyFile(studyFile)
                .online(Study.isDtypeOnline(createdStudy) ? onlineService.saveOnline(studySaveReqDto, createdStudy) : null)
                .offline(!Study.isDtypeOnline(createdStudy) ? offlineService.saveOffline(addressService.findAddress(studySaveReqDto.getAddressId()), createdStudy) : null)
                .studyMember(studyMember)
                .build();
    }

    @Transactional(readOnly = true)
    public List<StudyDto> findStudiesBySearchCondition(StudySearchCondition studySearchCondition) {
        List<Study> studies = studyService.findStudiesByStudySearchCondition(studySearchCondition);
        return studies.stream().map(
                study -> StudyDto.of(study,
                                studyMemberService.findStudyMemberByStudyIdAndAuth(study.getId(), StudyAuth.LEADER),
                                studyFileService.findStudyFileByStudyId(study.getId()))).collect(Collectors.toList());
    }

}
