package study.devmeetingstudy.repository;

import org.springframework.stereotype.Repository;
import study.devmeetingstudy.domain.Email;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class EmailRepositoryImpl implements EmailCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Email> findByEmail(String email){
        Optional<Email> find = entityManager.createQuery("select e from Email e where e.email = :email" + " order by e.id desc", Email.class)
                .setParameter("email", email)
                .setMaxResults(1).getResultList().stream().findFirst();

        return find;
    }

}
