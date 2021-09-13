package study.devmeetingstudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.member.Member;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

//    Optional<Subject> findBySubjectName(String SubjectNam);
}
