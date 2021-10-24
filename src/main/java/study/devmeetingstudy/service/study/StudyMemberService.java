package study.devmeetingstudy.service.study;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.common.exception.global.error.exception.notfound.StudyMemberNotFoundException;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.domain.study.StudyMember;
import study.devmeetingstudy.domain.study.enums.StudyAuth;
import study.devmeetingstudy.repository.StudyMemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyMemberService {

    private final StudyMemberRepository studyMemberRepository;

    @Transactional
    public StudyMember saveStudyMember(Member member, Study study){
        StudyMember studyMember = StudyMember.create(member, study);
        return studyMemberRepository.save(studyMember);
    }

    @Transactional
    public StudyMember saveStudyLeader(Member member, Study study){
        StudyMember studyMember = StudyMember.createAuthLeader(member, study);
        return studyMemberRepository.save(studyMember);
    }

    public List<StudyMember> findStudyMemberByStudyIdAndStudyAuth(Long studyId, StudyAuth studyAuth) {
        return studyMemberRepository.findStudyMembersByStudyIdAndStudyAuth(studyId, studyAuth);
    }

    public StudyMember findStudyMemberByStudyIdAndAuthLeader(Long studyId) {
        return studyMemberRepository.findStudyMemberByStudyIdAndAuthLeader(studyId).orElseThrow(() -> new StudyMemberNotFoundException("해당 study id로 스터디 멤버를 찾을 수 없습니다."));
    }

    public List<StudyMember> findStudyMembersByStudyId(Long studyId) {
        return null;
    }
}
