package study.devmeetingstudy.dto.study.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import study.devmeetingstudy.domain.study.Offline;
import study.devmeetingstudy.domain.study.StudyFile;
import study.devmeetingstudy.domain.study.enums.StudyType;
import study.devmeetingstudy.dto.address.AddressResDto;
import study.devmeetingstudy.dto.study.StudyFileDto;
import study.devmeetingstudy.dto.study.StudyVO;
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
                                     StudyFileDto file) {
        super(id, title, maxMember, startDate, endDate, createdDate, lastUpdateDate, studyType, subject, file);
        this.address = address;
    }

    public static CreatedOfflineStudyResDto of(StudyVO studyVO, StudyFile studyFile) {
        return CreatedOfflineStudyResDto.builder()
                .id(studyVO.getStudy().getId())
                .title(studyVO.getStudy().getTitle())
                .maxMember(studyVO.getStudy().getMaxMember())
                .startDate(studyVO.getStudy().getStartDate())
                .endDate(studyVO.getStudy().getEndDate())
                .studyType(studyVO.getStudy().getStudyType())
                .subject(SubjectResDto.from(studyVO.getStudy().getSubject()))
                .address(AddressResDto.from(studyVO.getOffline().getAddress()))
                .createdDate(studyVO.getStudy().getCreatedDate())
                .lastUpdateDate(studyVO.getStudy().getLastUpdateDate())
                .file(StudyFileDto.from(studyFile))
                .build();
    }
}
