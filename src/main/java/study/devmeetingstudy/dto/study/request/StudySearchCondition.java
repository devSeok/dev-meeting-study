package study.devmeetingstudy.dto.study.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Setter;
import lombok.ToString;
import study.devmeetingstudy.common.vaildEnum.CheckValidEnum;
import study.devmeetingstudy.domain.study.enums.SortedEnum;
import study.devmeetingstudy.domain.study.enums.StudyInstanceType;
import study.devmeetingstudy.domain.study.enums.StudyType;

@Setter
@ToString
public class StudySearchCondition {

    public static final Integer DEFAULT_OFFSET = 4;

    @ApiModelProperty(value = "스터디 주제 아이디", notes = "스터디 주제 필터링 대상 id", example = "1")
    private Long subjectId;

    @ApiModelProperty(value = "시, 도", notes = "행정 구역상 광역지방자치단체 (특별시, 광역시, 도 등)", example = "서울특별시")
    private String address1;

    @ApiModelProperty(value = "스터디 인스턴스 타입(ONLINE, OFFLINE)", notes = "필터링 대상 id")
    @CheckValidEnum(target = StudyInstanceType.class, message = "스터디 유형이 ONLINE or OFFLINE이여야 합니다.")
    private StudyInstanceType dtype;

    @ApiModelProperty(value = "스터디 아이디 (마지막 아이디)", example = "1", notes = "맨 처음 요청할 때는 null이고, 재 요청할 때는 마지막 요소의 id를 넘겨주세요")
    private Long lastId;

    @ApiModelProperty(value = "스터디 제목", notes = "스터디 제목 검색")
    private String title;

    @ApiModelProperty(value = "게시물 갯수", example = "1", notes = "한번에 보여줄 게시물의 갯수")
    private Integer offset;

    @ApiModelProperty(value = "정렬 방법", notes = "기본값 Desc, id 기준 최신 순(Desc), 나중 순(Asc)")
    @CheckValidEnum(target = SortedEnum.class, message = "정렬 유형은 Desc or Asc 만 가능합니다.")
    private SortedEnum sorted;

    @ApiModelProperty(value = "스터디 타입", notes = "FREE or PAY")
    private StudyType studyType;

    @Builder
    public StudySearchCondition(Long subjectId, String address1, StudyInstanceType dtype, Long lastId, String title, Integer offset) {
        this.subjectId = subjectId;
        this.address1 = address1;
        this.dtype = dtype;
        this.lastId = lastId;
        this.title = title;
        this.offset = offset;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public String getAddress1() {
        return address1;
    }

    public StudyInstanceType getDtype() {
        return dtype;
    }

    public Long getLastId() {
        return lastId;
    }

    public String getTitle() {
        return title;
    }

    public Integer getOffset() {
        if (isIntegerEmptyOrZero(offset)) return DEFAULT_OFFSET;
        return offset;
    }

    private boolean isIntegerEmptyOrZero(Integer integer) {
        return integer == null || integer.equals(0);
    }

    public SortedEnum getSorted() {
        return sorted;
    }

    public StudyType getStudyType() {
        return studyType;
    }
}
