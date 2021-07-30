package study.devmeetingstudy.domain.study;

import lombok.NoArgsConstructor;
import study.devmeetingstudy.domain.Address;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Offline extends Study{

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "address_id")
//    private Address address;

}
