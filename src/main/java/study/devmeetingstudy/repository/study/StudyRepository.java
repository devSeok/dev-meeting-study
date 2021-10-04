package study.devmeetingstudy.repository.study;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.devmeetingstudy.domain.study.Study;

public interface StudyRepository extends JpaRepository<Study, Long>, StudyRepositoryCustom {
}
