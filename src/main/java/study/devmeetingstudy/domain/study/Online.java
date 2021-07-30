package study.devmeetingstudy.domain.study;


import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Online extends Study{

    private String onlineType;

    private String link;
}
