package study.devmeetingstudy.dto.study;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import study.devmeetingstudy.domain.study.StudyFile;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StudyFileDto {

    @ApiModelProperty(value = "스터디 파일 아이디")
    private Long id;

    @ApiModelProperty(value = "스터디 파일 이름", notes = "파일 이름")
    private String name;

    @ApiModelProperty(value = "파일 경로", notes = "S3에 업로드 된 경로")
    private String path;

    public static StudyFileDto from(StudyFile studyFile){
        return new StudyFileDto(
                studyFile.getId(),
                studyFile.getName(),
                studyFile.getPath()
        );
    }
}
