package study.devmeetingstudy.dto.study;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import study.devmeetingstudy.domain.study.Offline;
import study.devmeetingstudy.domain.study.StudyFile;
import study.devmeetingstudy.domain.study.enums.StudyType;
import study.devmeetingstudy.dto.address.AddressResDto;
import study.devmeetingstudy.dto.subject.SubjectResDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public static CreatedOfflineStudyResDto of(Offline offline, StudyFile studyFile) {
        return CreatedOfflineStudyResDto.builder()
                .id(offline.getId())
                .title(offline.getTitle())
                .maxMember(offline.getMaxMember())
                .startDate(offline.getStartDate())
                .endDate(offline.getEndDate())
                .studyType(offline.getStudyType())
                .subject(SubjectResDto.from(offline.getSubject()))
                .address(AddressResDto.from(offline.getAddress()))
                .createdDate(offline.getCreatedDate())
                .lastUpdateDate(offline.getLastUpdateDate())
                .file(StudyFileDto.from(studyFile))
                .build();
    }
}
