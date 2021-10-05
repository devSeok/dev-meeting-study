package study.devmeetingstudy.dto.study.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.domain.study.StudyFile;
import study.devmeetingstudy.domain.study.enums.StudyInstanceType;
import study.devmeetingstudy.domain.study.enums.StudyType;
import study.devmeetingstudy.dto.study.CreatedStudyDto;
import study.devmeetingstudy.dto.study.StudyFileDto;
import study.devmeetingstudy.vo.StudyVO;
import study.devmeetingstudy.dto.subject.SubjectResDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreatedStudyResDto {

    @ApiModelProperty(value = "스터디 아이디", example = "1")
    private Long id;

    @ApiModelProperty(value = "제목", example = "자바 스터디원 모집합니다", dataType = "String")
    private String title;

    @ApiModelProperty(value = "최대 인원", example = "5", dataType = "Integer")
    private int maxMember;

    @ApiModelProperty(value = "시작 일자", example = "2021-09-16", dataType = "date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-mm-dd")
    private LocalDate startDate;

    @ApiModelProperty(value = "끝나는 일자", example = "2021-10-16", dataType = "date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-mm-dd")
    private LocalDate endDate;

    @ApiModelProperty(value = "스터디 생성 일자")
    private LocalDateTime createdDate;

    @ApiModelProperty(value = "스터디 최근 일자")
    private LocalDateTime lastUpdateDate;

    @ApiModelProperty(value = "FREE or PAY", notes = "FREE 혹은 PAY만 넣을 수 있습니다.", example = "FREE")
    private StudyType studyType;

    @ApiModelProperty(value = "스터디 주제", notes = "서브젝트 목록을 요청한 뒤 해당 id를 넘겨주세요.", example = "\"subject\" : {\"id\" : 1, \"subjectName\" : \"자바\"}")
    private SubjectResDto subject;

    @ApiModelProperty(value = "스터디 파일", example = "FREE")
    private StudyFileDto file;

    @ApiModelProperty(value = "스터디 내용", notes = "스터디 내용 들어갑니다.", example = "자바 스터디원 구합니다.")
    private String content;

    @ApiModelProperty(value = "스터디 맴버", notes = "생성된 스터디의 리더 정보")
    private StudyMemberResDto studyMember;

    @ApiModelProperty(value = "스터디 인스턴스 타입", notes = "ONLINE or OFFLINE", example = "ONLINE")
    private StudyInstanceType dtype;

}
