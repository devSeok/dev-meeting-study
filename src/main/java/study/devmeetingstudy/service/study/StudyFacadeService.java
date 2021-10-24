package study.devmeetingstudy.service.study;

import study.devmeetingstudy.annotation.dto.MemberResolverDto;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.dto.study.CreatedStudyDto;
import study.devmeetingstudy.dto.study.StudyDto;
import study.devmeetingstudy.dto.study.request.StudyPutReqDto;
import study.devmeetingstudy.dto.study.request.StudySaveReqDto;
import study.devmeetingstudy.dto.study.request.StudySearchCondition;

import java.io.IOException;
import java.util.List;

public interface StudyFacadeService {

    CreatedStudyDto storeStudy(StudySaveReqDto studySaveReqDto, Member loginMember) throws IOException;
    CreatedStudyDto replaceStudy(StudyPutReqDto studyPutReqDto, MemberResolverDto memberResolverDto) throws IOException;
    List<StudyDto> findStudiesBySearchCondition(StudySearchCondition studySearchCondition);
    StudyDto findStudyByStudyId(Long studyId);
    void deleteStudy(Long studyId, MemberResolverDto memberResolverDto);
}
