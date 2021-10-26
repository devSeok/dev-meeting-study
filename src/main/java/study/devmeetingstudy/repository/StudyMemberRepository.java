package study.devmeetingstudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import study.devmeetingstudy.domain.study.StudyMember;
import study.devmeetingstudy.domain.study.enums.StudyAuth;

import java.util.List;
import java.util.Optional;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long>, StudyMemberRepositoryCustom {

    @Query("select sm from StudyMember sm left join fetch sm.member where sm.study.id = :studyId and sm.studyAuth = :studyAuth")
    List<StudyMember> findStudyMembersByStudyIdAndStudyAuth(@Param("studyId") Long studyId, @Param("studyAuth") StudyAuth studyAuth);

    @Query("select sm from StudyMember sm left join fetch sm.member where sm.study.id = :studyId and sm.studyMemberStatus = 'JOIN'")
    List<StudyMember> findStudyMembersByStudyIdAndStatusJOIN(@Param("studyId") Long studyId);

    @Query("select sm from StudyMember sm left join fetch sm.member where sm.study.id = :studyId and sm.studyAuth = 'LEADER'")
    Optional<StudyMember> findStudyMemberByStudyIdAndAuthLeader(@Param("studyId") Long studyId);

    boolean existsStudyMemberByStudy_IdAndMember_Id(@Param("studyId") Long studyId, @Param("memberId") Long memberId);

    @Query("select sm from StudyMember sm left join fetch sm.member where sm.id = :studyMemberId and sm.studyMemberStatus = 'JOIN'")
    Optional<StudyMember> findStudyMemberByIdAndStatusJOIN(@Param("studyMemberId") Long studyMemberId);

    @Query("select count(sm.id) from StudyMember sm where sm.study.id = :studyId and sm.studyMemberStatus = 'JOIN'")
    int countStudyMembersByStudyIdAndStatusJOIN(@Param("studyId") Long studyId);

    @Query("select sm from StudyMember sm left join fetch sm.member where sm.study.id = :studyId and sm.member.id = :memberId")
    Optional<StudyMember> findStudyMemberByStudyIdAndMemberId(@Param("studyId") Long studyId, @Param("memberId") Long memberId);

}
