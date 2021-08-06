package study.devmeetingstudy.repository.message;

import org.springframework.stereotype.Repository;
import study.devmeetingstudy.domain.message.Message;
import study.devmeetingstudy.domain.member.Member;

import java.util.List;


public interface MessageRepositoryCustom {

    List<Message> findMessagesDesc(Member member);
}
