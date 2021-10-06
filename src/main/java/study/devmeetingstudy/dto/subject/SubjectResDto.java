package study.devmeetingstudy.dto.subject;


import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import study.devmeetingstudy.domain.Subject;

@Data
public class SubjectResDto {

    @ApiModelProperty(value = "스터디 주제 아이디", example = "1")
    private Long id;

    @ApiModelProperty(value = "스터디 주제 이름", example = "Java")
    private String name;

    @QueryProjection
    public SubjectResDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static SubjectResDto from(Subject subject){
        return new SubjectResDto(
                subject.getId(),
                subject.getName()
        );
    }
}
