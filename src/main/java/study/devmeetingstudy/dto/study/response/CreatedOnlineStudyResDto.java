package study.devmeetingstudy.dto.study.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import study.devmeetingstudy.domain.study.StudyFile;
import study.devmeetingstudy.domain.study.StudyMember;
import study.devmeetingstudy.domain.study.enums.StudyInstanceType;
import study.devmeetingstudy.domain.study.enums.StudyType;
import study.devmeetingstudy.dto.study.CreatedStudyDto;
import study.devmeetingstudy.dto.study.StudyFileDto;
import study.devmeetingstudy.vo.StudyVO;
import study.devmeetingstudy.dto.subject.SubjectResDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class CreatedOnlineStudyResDto extends CreatedStudyResDto {

    @ApiModelProperty(value = "링크 주소", example = "https://discord.com/asdfasdfasdfasdf", notes = "StudyInstanceType이 온라인이면, 필요하다면 링크를 넘겨줍니다. 나중에 수정할 수 있도록 제공할 예정.")
    private String link;

    @ApiModelProperty(value = "소프트웨어 종류", example = "디스코드",
            notes = "StudyInstanceType이 온라인이면, 소프트웨어 종류를 넘겨줍니다. 나중에 수정할 수 있도록 제공할 예정. 예) 디스코드, 구글 밋 등")
    private String onlineType;

    @Builder(access = AccessLevel.PRIVATE)
    public CreatedOnlineStudyResDto(Long id, String title,
                                    int maxMember, LocalDate startDate,
                                    LocalDate endDate, StudyType studyType,
                                    SubjectResDto subject, String onlineType,
                                    String link, LocalDateTime createdDate,
                                    LocalDateTime lastUpdateDate, StudyFileDto file,
                                    String content, StudyMemberResDto studyMember,
                                    StudyInstanceType dtype) {
        super(id, title, maxMember, startDate, endDate, createdDate, lastUpdateDate, studyType, subject, file, content, studyMember, dtype);
        this.onlineType = onlineType;
        this.link = link;
    }

    public static CreatedOnlineStudyResDto from(CreatedStudyDto createdStudyDto) {
        return CreatedOnlineStudyResDto.builder()
                .id(createdStudyDto.getStudy().getId())
                .title(createdStudyDto.getStudy().getTitle())
                .maxMember(createdStudyDto.getStudy().getMaxMember())
                .startDate(createdStudyDto.getStudy().getStartDate())
                .endDate(createdStudyDto.getStudy().getEndDate())
                .studyType(createdStudyDto.getStudy().getStudyType())
                .subject(SubjectResDto.from(createdStudyDto.getStudy().getSubject()))
                .onlineType(createdStudyDto.getOnline().getOnlineType())
                .link(createdStudyDto.getOnline().getLink())
                .createdDate(createdStudyDto.getStudy().getCreatedDate())
                .lastUpdateDate(createdStudyDto.getStudy().getLastUpdateDate())
                .file(StudyFileDto.from(createdStudyDto.getStudyFile()))
                .content(createdStudyDto.getStudy().getContent())
                .studyMember(StudyMemberResDto.from(createdStudyDto.getStudyMember()))
                .dtype(createdStudyDto.getStudy().getDtype())
                .build();
    }
}
