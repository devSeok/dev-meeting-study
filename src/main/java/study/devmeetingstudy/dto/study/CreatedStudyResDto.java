package study.devmeetingstudy.dto.study;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import study.devmeetingstudy.domain.study.Offline;
import study.devmeetingstudy.domain.study.Online;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.domain.study.enums.StudyType;
import study.devmeetingstudy.dto.address.AddressResDto;
import study.devmeetingstudy.dto.subject.SubjectResDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CreatedStudyResDto {

    @ApiModelProperty(value = "스터디 아이디", example = "1")
    private Long id;

    @ApiModelProperty(value = "제목", example = "자바 스터디원 모집합니다", dataType = "String")
    private String title;

    @ApiModelProperty(value = "최대 인원", example = "5", dataType = "Integer")
    private int maxMember;

    @ApiModelProperty(value = "시작 일자", example = "2021-09-16", dataType = "date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @ApiModelProperty(value = "끝나는 일자", example = "2021-10-16", dataType = "date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @ApiModelProperty(value = "FREE or PAY", notes = "FREE 혹은 PAY만 넣을 수 있습니다.", example = "FREE")
    private StudyType studyType;

    @ApiModelProperty(value = "스터디 주제", notes = "서브젝트 목록을 요청한 뒤 해당 id를 넘겨주세요.", example = "\"subject\" : {\"id\" : 1, \"subjectName\" : \"자바\"}")
    private SubjectResDto subject;

    @ApiModelProperty(value = "FREE or PAY", notes = "FREE 혹은 PAY만 넣을 수 있습니다.", example = "FREE")
    private List<StudyFileDto> Files;

    // TODO : 온라인이나 오프라인이냐를 어디서 결정하며 어떻게 넣어줘야하는지 생각.
    public static CreatedStudyResDto from(Study study) {
        if (Study.isInstanceOnline(study)) {
            return CreatedOnlineStudyResDto.from((Online) study);
        }
        return CreatedOfflineStudyResDto.from((Offline) study);
    }

    public static boolean isInstanceOnlineResDto(CreatedStudyResDto createdStudyResDto){
        return createdStudyResDto instanceof CreatedOnlineStudyResDto;
    }

}
