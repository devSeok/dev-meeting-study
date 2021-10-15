package study.devmeetingstudy.repository.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.devmeetingstudy.domain.message.Message;
import study.devmeetingstudy.domain.member.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Message> findMessagesDesc(Member member) {
        return em.createQuery("select ms from Message ms where ms.member.id = :memberId and ms.deletionStatus = 'NOT_DELETED' order by ms.id desc", Message.class)
                .setParameter("memberId",member.getId())
                .getResultList();
    }


}
