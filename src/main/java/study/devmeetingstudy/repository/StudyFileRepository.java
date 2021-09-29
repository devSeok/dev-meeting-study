package study.devmeetingstudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.devmeetingstudy.domain.study.StudyFile;

public interface StudyFileRepository extends JpaRepository<StudyFile, Long> {
}
