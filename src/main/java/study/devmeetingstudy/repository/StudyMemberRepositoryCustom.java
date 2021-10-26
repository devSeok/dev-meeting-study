package study.devmeetingstudy.repository;

import study.devmeetingstudy.domain.study.StudyMember;

import java.util.List;

public interface StudyMemberRepositoryCustom {

    List<StudyMember> findStudyMembersByMemberId(Long memberId);
}
