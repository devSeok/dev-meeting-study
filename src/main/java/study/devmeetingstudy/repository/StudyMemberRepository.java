package study.devmeetingstudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.devmeetingstudy.domain.study.StudyMember;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {
}
