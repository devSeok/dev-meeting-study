package study.devmeetingstudy.repository.email;

import org.springframework.data.jpa.repository.JpaRepository;
import study.devmeetingstudy.domain.Email;

public interface EmailRepository extends JpaRepository<Email, Long>, EmailCustomRepository<Email> {
}
