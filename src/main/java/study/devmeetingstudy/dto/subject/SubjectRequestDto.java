package study.devmeetingstudy.dto.subject;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectRequestDto {

    @NotEmpty(message = "스터디 제목은 필수입니다.")
    @ApiModelProperty(value = "스터디 주제", required = true, example = "자바")
    private String subjectName;
}
