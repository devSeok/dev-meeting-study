package study.devmeetingstudy.dto.subject;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import study.devmeetingstudy.domain.Subject;

@Data
@AllArgsConstructor
public class SubjectResponseDto {

    @ApiModelProperty(value = "스터디 주제 아이디", example = "1")
    private Long id;

    @ApiModelProperty(value = "스터디 주제 이름", example = "Java")
    private String name;

    public static SubjectResponseDto from(Subject subject){
        return new SubjectResponseDto(
                subject.getId(),
                subject.getName()
        );
    }
}
