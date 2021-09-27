package study.devmeetingstudy.domain.study;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.devmeetingstudy.domain.Address;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.study.enums.StudyType;
import study.devmeetingstudy.dto.study.request.StudySaveReqDto;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("Offline")
public class Offline extends Study{

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Builder
    public Offline(Subject subject, String title, int maxMember,
                   LocalDate statDate, LocalDate endDate,
                   StudyType studyType, Address address) {

        super(subject, title, maxMember, statDate, endDate, studyType);
        this.address = address;
    }

    public static Offline create(StudySaveReqDto dto, Subject subject, Address address){
        return Offline.builder()
                .subject(subject)
                .title(dto.getTitle())
                .maxMember(dto.getMaxMember())
                .statDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .studyType(dto.getStudyType())
                .address(address)
                .build();
    }

}
