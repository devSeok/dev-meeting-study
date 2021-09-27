package study.devmeetingstudy.domain;


import lombok.*;
import study.devmeetingstudy.dto.address.AddressReqDto;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Address {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "address_3")
    private String address3;

    // 양방향 조회가 필요 없을듯
//    @OneToOne(mappedBy = "address")
//    private Offline offline;

    @Builder
    public Address(Long id, String address1, String address2, String address3) {
        this.id = id;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
    }

    public static Address create(AddressReqDto addressReqDto) {
        return Address.builder()
                .address1(addressReqDto.getAddress1())
                .address2(addressReqDto.getAddress2())
                .address3(addressReqDto.getAddress3())
                .build();
    }

}
