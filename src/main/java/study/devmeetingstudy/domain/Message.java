package study.devmeetingstudy.domain;

import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import study.devmeetingstudy.domain.base.BaseTimeEntity;
import study.devmeetingstudy.domain.enums.MessageStatus;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    @Enumerated(EnumType.STRING)
    private MessageStatus status;


    private String sender_name;

}
