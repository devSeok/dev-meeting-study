package study.devmeetingstudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.devmeetingstudy.domain.study.Offline;

import java.util.Optional;

public interface OfflineRepository extends JpaRepository<Offline, Long> {

    Optional<Offline> findOfflineByStudy_Id(Long studyId);
}
