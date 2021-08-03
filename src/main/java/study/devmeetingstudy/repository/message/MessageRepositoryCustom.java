package study.devmeetingstudy.repository.message;

import org.springframework.stereotype.Repository;
import study.devmeetingstudy.domain.Member;
import study.devmeetingstudy.domain.Message;

import java.util.List;


public interface MessageRepositoryCustom {

    List<Message> findMessagesDesc(Member member);
}
