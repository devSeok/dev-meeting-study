package study.devmeetingstudy.service.study;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.annotation.dto.MemberResolverDto;
import study.devmeetingstudy.common.exception.global.error.exception.BusinessException;
import study.devmeetingstudy.common.exception.global.error.exception.ErrorCode;
import study.devmeetingstudy.common.exception.global.error.exception.ExistsStudyMemberException;
import study.devmeetingstudy.common.exception.global.error.exception.notfound.StudyMemberNotFoundException;
import study.devmeetingstudy.common.exception.global.error.exception.UnableApplyStudyMemberException;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.domain.study.StudyMember;
import study.devmeetingstudy.domain.study.enums.StudyAuth;
import study.devmeetingstudy.domain.study.enums.StudyMemberStatus;
import study.devmeetingstudy.repository.StudyMemberRepository;

import java.util.List;

// TODO 리팩토링하기...
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyMemberService {

    private final StudyMemberRepository studyMemberRepository;

    @Transactional
    public StudyMember saveStudyMember(Member member, Study study){
        checkStudyMaxMember(study);
        checkMemberInStudy(member, study);
        StudyMember studyMember = StudyMember.create(member, study);
        return studyMemberRepository.save(studyMember);
    }

    private void checkMemberInStudy(Member member, Study study) {
        if (existsStudyMemberByStudyIdAndMemberId(study.getId(), member.getId())) {
            throw new ExistsStudyMemberException("해당 스터디에 이미 가입되어 있습니다.");
        }
    }

    private void checkStudyMaxMember(Study study) {
        if (countStudyMemberByStudyId(study.getId()) >= study.getMaxMember()) {
            throw new UnableApplyStudyMemberException("해당 스터디에 멤버가 가득 찼습니다.");
        }
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
        return studyMemberRepository.findStudyMemberByStudyIdAndAuthLeader(studyId).orElseThrow(() -> new StudyMemberNotFoundException("해당 study id로 스터디 리더를 찾을 수 없습니다."));
    }

    public List<StudyMember> findStudyMembersByStudyId(Long studyId) {
        return studyMemberRepository.findStudyMembersByStudyIdAndStatusJOIN(studyId);
    }

    public void authenticateStudyMember(Long studyId, MemberResolverDto memberResolverDto) {
        if (!existsStudyMemberByStudyIdAndMemberId(studyId, memberResolverDto.getId())) {
            throw new BusinessException("해당 스터디의 멤버가 아니므로 접근 불가합니다.", ErrorCode.HANDLE_ACCESS_DENIED);
        }

        if (checkStudyMemberStatusJOIN(studyId, memberResolverDto.getId())) {
            throw new BusinessException("해당 스터디 멤버는 OUT 상태입니다", ErrorCode.HANDLE_ACCESS_DENIED);
        }
    }

    public boolean existsStudyMemberByStudyIdAndMemberId(Long studyId, Long memberId) {
        return studyMemberRepository.existsStudyMemberByStudy_IdAndMember_Id(studyId, memberId);
    }

    public boolean checkStudyMemberStatusJOIN(Long studyId, Long memberId) {
        return studyMemberRepository.findStudyMemberByStudyIdAndMemberId(studyId, memberId).filter(studyMember -> studyMember.getStudyMemberStatus() != StudyMemberStatus.JOIN).isPresent();
    }

    public StudyMember findStudyMemberById(Long studyMemberId) {
        return studyMemberRepository.findStudyMemberByIdAndStatusJOIN(studyMemberId).orElseThrow(() -> new StudyMemberNotFoundException("해당 study member id로 스터디 멤버를 찾을 수 없습니다."));
    }

    public int countStudyMemberByStudyId(Long studyId) {
        return studyMemberRepository.countStudyMembersByStudyIdAndStatusJOIN(studyId);
    }

    @Transactional
    public void deleteStudyMember(Long studyMemberId, MemberResolverDto memberResolverDto) {
        StudyMember studyMember = findStudyMemberById(studyMemberId);
        if (studyMember.isStudyAuthLeader()) {
            checkStudyLeaderOwner(memberResolverDto, studyMember);
            StudyMember.changeStudyMemberStatus(studyMember, StudyMemberStatus.OUT);
            return;
        }
        if (isStudyMemberOwner(memberResolverDto, studyMember)) {
            StudyMember.changeStudyMemberStatus(studyMember, StudyMemberStatus.OUT);
            return;
        }
        throw new BusinessException("본인이 아니면 탈퇴할 수 없습니다.", ErrorCode.CANNOT_DELETE_STUDY_MEMBER);
    }

    private void checkStudyLeaderOwner(MemberResolverDto memberResolverDto, StudyMember studyMember) {
        if (studyMember.isStudyAuthLeader() && isStudyMemberOwner(memberResolverDto, studyMember)) {
            throw new BusinessException("리더는 본인을 삭제할 수 없습니다.", ErrorCode.CANNOT_DELETE_STUDY_MEMBER);
        }
    }

    private boolean isStudyMemberOwner(MemberResolverDto memberResolverDto, StudyMember studyMember) {
        return studyMember.getMember().getId().equals(memberResolverDto.getId());
    }

    public List<StudyMember> findStudyMembersByMemberId(Long memberId) {
        return studyMemberRepository.findStudyMembersByMemberId(memberId);
    }
}

