package study.devmeetingstudy.dto.subject;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectRequestDto {

    @ApiModelProperty(value = "스터디 주제 ID", example = "1", dataType = "Long")
    private Long id;

    @NotEmpty(message = "스터디 주제는 필수입니다.")
    @ApiModelProperty(value = "스터디 주제", required = true, example = "자바", dataType = "String")
    private String subjectName;

    public SubjectRequestDto(String subjectName){
        this.subjectName = subjectName;
    }
}
