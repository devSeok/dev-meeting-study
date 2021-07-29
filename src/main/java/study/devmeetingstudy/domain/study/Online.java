package study.devmeetingstudy.domain.study;


import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Online extends Study{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "online_id")
    private Long id;

    private String onlineType;

    private String link;
}
