package study.devmeetingstudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.devmeetingstudy.domain.study.Online;

import java.util.Optional;

public interface OnlineRepository extends JpaRepository<Online, Long> {


    Optional<Online> findOnlineByStudy_Id(Long studyId);
}
