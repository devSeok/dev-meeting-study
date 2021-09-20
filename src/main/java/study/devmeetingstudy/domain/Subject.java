package study.devmeetingstudy.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.devmeetingstudy.dto.subject.SubjectRequestDto;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Subject{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long id;

    @Column(name = "subject_name", length = 30, nullable = false)
    private String name;

    @Builder
    public Subject(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public static Subject create(SubjectRequestDto subjectRequestDto){
        return Subject.builder()
                .name(subjectRequestDto.getSubjectName())
                .build();
    }

}
