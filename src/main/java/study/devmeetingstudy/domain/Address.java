package study.devmeetingstudy.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import study.devmeetingstudy.domain.study.Offline;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Address {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    private String address_1;

    private String address_2;

    private String address_3;

    @OneToOne(mappedBy = "address")
    private Offline offline;

}