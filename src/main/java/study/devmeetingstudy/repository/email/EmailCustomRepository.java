package study.devmeetingstudy.repository.email;

import study.devmeetingstudy.domain.Email;

import java.util.Optional;

public interface EmailCustomRepository<T> {

    Optional<Email> findByEmail(String email);
}
