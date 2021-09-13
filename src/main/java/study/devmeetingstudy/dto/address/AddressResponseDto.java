package study.devmeetingstudy.dto.address;


import lombok.AllArgsConstructor;
import lombok.Getter;
import study.devmeetingstudy.domain.Address;

@Getter
public class AddressResponseDto {

    private Long id;
    private String address1;
    private String address2;
    private String address3;

    private AddressResponseDto(Long id, String address1, String address2, String address3) {
        this.id = id;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
    }

    public static AddressResponseDto from(Address address){
        return new AddressResponseDto(
                address.getId(),
                address.getAddress1(),
                address.getAddress2(),
                address.getAddress3());
    }
}
