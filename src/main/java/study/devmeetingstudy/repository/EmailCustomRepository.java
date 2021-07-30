package study.devmeetingstudy.repository;

import study.devmeetingstudy.domain.Email;

import java.util.Optional;

public interface EmailCustomRepository<T> {

    Optional<Email> findByEmail(String email);
}
