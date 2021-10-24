package study.devmeetingstudy.domain.study;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.devmeetingstudy.domain.Address;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.study.enums.StudyType;
import study.devmeetingstudy.dto.study.request.StudyPutReqDto;
import study.devmeetingstudy.dto.study.request.StudySaveReqDto;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Offline {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offline_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @Builder
    public Offline(Address address, Study study) {
        this.address = address;
        this.study = study;
    }

    public static Offline create(Address address, Study study){
        return Offline.builder()
                .address(address)
                .study(study)
                .build();
    }

    public static Offline replace(Address address, Offline foundOffline) {
        foundOffline.address = address;
        return foundOffline;
    }
}
