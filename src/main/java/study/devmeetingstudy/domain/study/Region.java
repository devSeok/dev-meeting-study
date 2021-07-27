package study.devmeetingstudy.domain.study;

import lombok.NoArgsConstructor;
import study.devmeetingstudy.domain.Address;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Region extends Study{

    @Id @GeneratedValue
    @Column(name = "region_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

}
