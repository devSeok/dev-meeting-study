package study.devmeetingstudy.dto.study.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import study.devmeetingstudy.domain.study.StudyFile;
import study.devmeetingstudy.domain.study.enums.StudyType;
import study.devmeetingstudy.dto.address.AddressResDto;
import study.devmeetingstudy.dto.study.CreatedStudyDto;
import study.devmeetingstudy.dto.study.StudyFileDto;
import study.devmeetingstudy.vo.StudyVO;
import study.devmeetingstudy.dto.subject.SubjectResDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class CreatedOfflineStudyResDto extends CreatedStudyResDto {

    @ApiModelProperty(value = "주소", notes = "주소를 저장 요청 한 뒤 해당 id를 넘겨줍니다.")
    private AddressResDto address;

    @Builder
    public CreatedOfflineStudyResDto(Long id, String title,
                                     int maxMember, LocalDate startDate,
                                     LocalDate endDate, StudyType studyType,
                                     SubjectResDto subject, AddressResDto address,
                                     LocalDateTime createdDate, LocalDateTime lastUpdateDate,
                                     StudyFileDto file, String content,
                                     StudyMemberResDto studyMemberResDto) {
        super(id, title, maxMember, startDate, endDate, createdDate, lastUpdateDate, studyType, subject, file, content, studyMemberResDto);
        this.address = address;
    }

    public static CreatedOfflineStudyResDto from(CreatedStudyDto createdStudyDto) {
        return CreatedOfflineStudyResDto.builder()
                .id(createdStudyDto.getStudy().getId())
                .title(createdStudyDto.getStudy().getTitle())
                .maxMember(createdStudyDto.getStudy().getMaxMember())
                .startDate(createdStudyDto.getStudy().getStartDate())
                .endDate(createdStudyDto.getStudy().getEndDate())
                .studyType(createdStudyDto.getStudy().getStudyType())
                .subject(SubjectResDto.from(createdStudyDto.getStudy().getSubject()))
                .address(AddressResDto.from(createdStudyDto.getOffline().getAddress()))
                .createdDate(createdStudyDto.getStudy().getCreatedDate())
                .lastUpdateDate(createdStudyDto.getStudy().getLastUpdateDate())
                .file(StudyFileDto.from(createdStudyDto.getStudyFile()))
                .content(createdStudyDto.getStudy().getContent())
                .studyMemberResDto(StudyMemberResDto.from(createdStudyDto.getStudyMember()))
                .build();
    }
}
