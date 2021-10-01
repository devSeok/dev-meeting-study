package study.devmeetingstudy.dto.study.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import study.devmeetingstudy.domain.study.Offline;
import study.devmeetingstudy.domain.study.Online;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.domain.study.StudyFile;
import study.devmeetingstudy.domain.study.enums.StudyType;
import study.devmeetingstudy.dto.study.StudyFileDto;
import study.devmeetingstudy.dto.study.StudyVO;
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

    @ApiModelProperty(value = "스터디 파일", notes = "", example = "FREE")
    private StudyFileDto file;

    // TODO : 온라인이나 오프라인이냐를 어디서 결정하며 어떻게 넣어줘야하는지 생각.
    public static CreatedStudyResDto of(StudyVO studyVO, StudyFile studyFile) {
        if (Study.isDtypeOnline(studyVO.getStudy())) {
            return CreatedOnlineStudyResDto.of(studyVO, studyFile);
        }
        return CreatedOfflineStudyResDto.of(studyVO, studyFile);
    }

    public static boolean isInstanceOnlineResDto(CreatedStudyResDto createdStudyResDto){
        return createdStudyResDto instanceof CreatedOnlineStudyResDto;
    }

}
