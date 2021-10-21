package study.devmeetingstudy.service.study;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.annotation.dto.MemberResolverDto;
import study.devmeetingstudy.common.uploader.Uploader;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.enums.DomainType;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.study.*;
import study.devmeetingstudy.domain.study.enums.StudyAuth;
import study.devmeetingstudy.dto.study.CreatedStudyDto;
import study.devmeetingstudy.dto.study.StudyDto;
import study.devmeetingstudy.dto.study.request.StudyPutReqDto;
import study.devmeetingstudy.dto.study.request.StudySearchCondition;
import study.devmeetingstudy.vo.StudyReplaceVO;
import study.devmeetingstudy.vo.StudySaveVO;
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

    // TODO 파일이 비어있을시 기본 이미지 추가.
    @Transactional
    public CreatedStudyDto store(StudySaveReqDto studySaveReqDto, Member loginMember) throws IOException {
        Map<String, String> uploadFileInfo = uploader.upload(studySaveReqDto.getFile(), DomainType.STUDY);
        Study createdStudy = studyService.saveStudy(StudySaveVO.of(studySaveReqDto, subjectService.findSubjectById(studySaveReqDto.getSubjectId())));
        StudyFile studyFile = studyFileService.saveStudyFile(createdStudy, uploadFileInfo);
        StudyMember studyMember = studyMemberService.saveStudyLeader(loginMember, createdStudy);
        return CreatedStudyDto.builder()
                .study(createdStudy)
                .studyFile(studyFile)
                .online(createdStudy.isDtypeOnline() ? onlineService.saveOnline(studySaveReqDto, createdStudy) : null)
                .offline(!createdStudy.isDtypeOnline() ? offlineService.saveOffline(addressService.findAddressById(studySaveReqDto.getAddressId()), createdStudy) : null)
                .studyMember(studyMember)
                .build();
    }

    public List<StudyDto> findStudiesBySearchCondition(StudySearchCondition studySearchCondition) {
        List<Study> studies = studyService.findStudiesByStudySearchCondition(studySearchCondition);
        return studies.stream().map(
                study -> StudyDto.of(study,
                                studyMemberService.findStudyMemberByStudyIdAndStudyAuth(study.getId(), StudyAuth.LEADER),
                                studyFileService.findStudyFileByStudyId(study.getId()))
        ).collect(Collectors.toList());
    }

    public StudyDto findStudyByStudyId(Long studyId) {
        Study study = studyService.findStudyFetchJoinById(studyId);
        return StudyDto.of(study,
                studyMemberService.findStudyMemberByStudyIdAndStudyAuth(study.getId(), StudyAuth.LEADER),
                studyFileService.findStudyFileByStudyId(study.getId()));
    }

    @Transactional
    public CreatedStudyDto replaceStudy(StudyPutReqDto studyPutReqDto, MemberResolverDto memberResolverDto) throws IOException {
        Long studyId = studyPutReqDto.getStudyId();
        Study foundStudy = studyService.findStudyById(studyId);

        Subject foundSubject = subjectService.findSubjectById(studyPutReqDto.getSubjectId());
        Object onlineOrOffline = SyncOrReplaceOnlineOrOffline(studyPutReqDto, foundStudy);

        return CreatedStudyDto.builder()
                .study(studyService.replaceStudy(StudyReplaceVO.of(studyPutReqDto, foundSubject), foundStudy))
                .online(onlineOrOffline instanceof Online ? (Online) onlineOrOffline : null)
                .offline(onlineOrOffline instanceof Offline ? (Offline) onlineOrOffline : null)
                .studyMember(studyMemberService.findStudyMemberByStudyIdAndStudyAuth(studyId, StudyAuth.LEADER).get(0))
                .studyFile(replaceStudyFile(studyPutReqDto))
                .build();
    }

    private Object SyncOrReplaceOnlineOrOffline(StudyPutReqDto studyPutReqDto, Study foundStudy) {
        if (!foundStudy.getDtype().equals(studyPutReqDto.getDtype())) {
            return syncOnlineOrOffline(studyPutReqDto, foundStudy);
        }
        return replaceOnlineOrOffline(studyPutReqDto);
    }

    private Object syncOnlineOrOffline(StudyPutReqDto studyPutReqDto, Study foundStudy) {
        if (studyPutReqDto.getDtype().isOnline()) {
            return deleteOfflineAndSaveOnline(studyPutReqDto, foundStudy);
        }
        return deleteOnlineAndSaveOffline(studyPutReqDto, foundStudy);
    }

    private Online deleteOfflineAndSaveOnline(StudyPutReqDto studyPutReqDto, Study foundStudy) {
        Study.removeOffline(foundStudy);
        offlineService.deleteOffline(offlineService.findOfflineByStudyId(studyPutReqDto.getStudyId()));
        return onlineService.saveOnline(StudySaveReqDto.of(studyPutReqDto), foundStudy);
    }

    private Offline deleteOnlineAndSaveOffline(StudyPutReqDto studyPutReqDto, Study foundStudy) {
        Study.removeOnline(foundStudy);
        onlineService.deleteOnline(onlineService.findOnlineByStudyId(studyPutReqDto.getStudyId()));
        return offlineService.saveOffline(addressService.findAddressById(studyPutReqDto.getAddressId()), foundStudy);
    }

    private Object replaceOnlineOrOffline(StudyPutReqDto studyPutReqDto) {
        if (studyPutReqDto.getDtype().isOnline()) {
            return onlineService.replaceOnline(studyPutReqDto, onlineService.findOnlineById(studyPutReqDto.getOnlineId()));
        }
        return offlineService.replaceOffline(
                addressService.findAddressById(studyPutReqDto.getAddressId()),
                offlineService.findOfflineById(studyPutReqDto.getOfflineId()));
    }

    private StudyFile replaceStudyFile(StudyPutReqDto studyPutReqDto) throws IOException {
        if (studyPutReqDto.getFile() != null && !studyPutReqDto.getFile().isEmpty()) {
            Map<String, String> upload = uploader.upload(studyPutReqDto.getFile(), DomainType.STUDY);
            StudyFile studyFile = studyFileService.findStudyFileById(studyPutReqDto.getStudyFileId());
            return studyFileService.replaceStudyFile(upload, studyFile);
        }
        return studyFileService.findStudyFileById(studyPutReqDto.getStudyFileId());
    }

}
