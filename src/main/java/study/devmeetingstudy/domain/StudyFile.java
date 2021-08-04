package study.devmeetingstudy.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import study.devmeetingstudy.domain.study.Study;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class StudyFile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @Column(length = 30, name = "study_file_name")
    private String name;

    @Column(name = "file_path")
    private String path;

}
