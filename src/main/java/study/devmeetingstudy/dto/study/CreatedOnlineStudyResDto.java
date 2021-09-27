package study.devmeetingstudy.dto.study;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import study.devmeetingstudy.domain.study.Online;
import study.devmeetingstudy.domain.study.enums.StudyType;
import study.devmeetingstudy.dto.subject.SubjectResDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CreatedOnlineStudyResDto extends CreatedStudyResDto {

    @ApiModelProperty(value = "링크 주소", example = "https://discord.com/asdfasdfasdfasdf", notes = "StudyInstanceType이 온라인이면, 필요하다면 링크를 넘겨줍니다. 나중에 수정할 수 있도록 제공할 예정.")
    private String link;

    @ApiModelProperty(value = "소프트웨어 종류", example = "디스코드",
            notes = "StudyInstanceType이 온라인이면, 소프트웨어 종류를 넘겨줍니다. 나중에 수정할 수 있도록 제공할 예정. 예) 디스코드, 구글 밋 등")
    private String onlineType;

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
