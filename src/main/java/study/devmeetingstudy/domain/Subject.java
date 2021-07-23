package study.devmeetingstudy.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Subject{

    @Id @GeneratedValue
    private Long id;

    @Column(name = "name", length = 30, nullable = false)
    private String name;
    
    @Column(name = "content", length = 255, nullable = false)
    private String content;
}
