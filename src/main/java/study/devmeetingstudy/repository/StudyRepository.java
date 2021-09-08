package study.devmeetingstudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.devmeetingstudy.domain.study.Study;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {
}
