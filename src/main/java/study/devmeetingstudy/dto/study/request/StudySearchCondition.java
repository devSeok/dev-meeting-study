package study.devmeetingstudy.dto.study.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import study.devmeetingstudy.domain.study.enums.StudyInstanceType;

@Data
public class StudySearchCondition {

    @ApiModelProperty(value = "스터디 주제 아이디", notes = "스터디 주제 필터링 대상 id", example = "1")
    private Long subjectId;
    @ApiModelProperty(value = "시, 도", notes = "행정 구역상 광역지방자치단체 (특별시, 광역시, 도 등)", example = "서울특별시")
    private String address1;
    @ApiModelProperty(value = "스터디 주제 아이디", notes = "필터링 대상 id")
    private StudyInstanceType studyInstanceType;
    @ApiModelProperty(value = "스터디 주제 아이디", notes = "필터링 대상 id")
    private Long lastId;
    @ApiModelProperty(value = "스터디 주제 아이디", notes = "필터링 대상 id")
    private String title;
}
