package study.devmeetingstudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.devmeetingstudy.domain.study.Online;

public interface OnlineRepository extends JpaRepository<Online, Long> {
}
