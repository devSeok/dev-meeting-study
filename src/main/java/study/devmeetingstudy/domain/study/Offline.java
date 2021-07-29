package study.devmeetingstudy.domain.study;

import lombok.NoArgsConstructor;
import study.devmeetingstudy.domain.Address;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Offline extends Study{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id")
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "address_id")
//    private Address address;

}
