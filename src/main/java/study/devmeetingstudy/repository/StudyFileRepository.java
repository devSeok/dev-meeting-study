package study.devmeetingstudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.devmeetingstudy.domain.study.StudyFile;

import java.util.List;
import java.util.Optional;

public interface StudyFileRepository extends JpaRepository<StudyFile, Long> {

    List<StudyFile> findFirstByStudy_Id(Long studyId);
}
