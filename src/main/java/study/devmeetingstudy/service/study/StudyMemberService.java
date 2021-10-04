package study.devmeetingstudy.service.study;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.domain.study.StudyMember;
import study.devmeetingstudy.repository.StudyMemberRepository;

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
}
