package study.devmeetingstudy.dto.study;

import io.swagger.annotations.ApiModelProperty;
import study.devmeetingstudy.domain.study.Online;
import study.devmeetingstudy.domain.study.enums.StudyType;
import study.devmeetingstudy.dto.subject.SubjectResDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CreatedOnlineStudyResDto extends CreatedStudyResDto {

    @ApiModelProperty(value = "주소", notes = "주소를 저장 요청 한 뒤 해당 id를 넘겨줍니다.")
    private String onlineType;

    @ApiModelProperty(value = "이미지 경로", notes = "해당 스터디 연관된 파일 (현재는 추가 기능 없이 하나의 파일만)")
    private String link;

    public CreatedOnlineStudyResDto(Long id, String title,
                                    int maxMember, LocalDate startDate,
                                    LocalDate endDate, StudyType studyType,
                                    SubjectResDto subject, String onlineType,
                                    String link, List<StudyFileDto> files) {
        super(id, title, maxMember, startDate, endDate, studyType, subject, files);
        this.onlineType = onlineType;
        this.link = link;
    }

    public static CreatedOnlineStudyResDto from(Online online){
        return new CreatedOnlineStudyResDto(
                online.getId(),
                online.getTitle(),
                online.getMaxMember(),
                online.getStartDate(),
                online.getEndDate(),
                online.getStudyType(),
                SubjectResDto.from(online.getSubject()),
                online.getOnlineType(),
                online.getLink(),
                online.getFiles().stream().map(StudyFileDto::from).collect(Collectors.toList())
        );
    }
}
