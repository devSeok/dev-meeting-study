package study.devmeetingstudy.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class StudyFile {

    @Id @GeneratedValue
    @Column(name = "study_file_id")
    private Long id;
}
