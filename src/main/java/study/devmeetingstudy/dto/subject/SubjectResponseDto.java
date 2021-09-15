package study.devmeetingstudy.dto.subject;


import lombok.AllArgsConstructor;
import lombok.Data;
import study.devmeetingstudy.domain.Subject;

@Data
@AllArgsConstructor
public class SubjectResponseDto {
    private Long id;
    private String name;

    public static SubjectResponseDto from(Subject subject){
        return new SubjectResponseDto(
                subject.getId(),
                subject.getName()
        );
    }
}
