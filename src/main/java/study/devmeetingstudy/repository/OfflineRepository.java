package study.devmeetingstudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.devmeetingstudy.domain.study.Offline;

public interface OfflineRepository extends JpaRepository<Offline, Long> {
}
