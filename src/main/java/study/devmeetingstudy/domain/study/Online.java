package study.devmeetingstudy.domain.study;


import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class Online extends Study{

    @Id @GeneratedValue
    @Column(name = "online_id")
    private Long id;

    private String onlineType;

    private String link;
}
