package study.devmeetingstudy.domain.study;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.devmeetingstudy.domain.Address;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.study.enums.StudyType;
import study.devmeetingstudy.dto.study.request.StudyStoreRequestDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

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
                  LocalDateTime statDate, LocalDateTime endDate,
                  StudyType studyType, Address address) {

        super(subject, title, maxMember, statDate, endDate, studyType);
        this.address = address;
    }

    public static Offline createOffline(StudyStoreRequestDto dto, Optional<Subject> subject,  Optional<Address> findAddress){
        return Offline.builder()
                .subject(subject.get())
                .title(dto.getTitle())
                .maxMember(dto.getMaxMember())
                .statDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .studyType(StudyType.FREE)
                .address(findAddress.get())
                .build();
    }

}
