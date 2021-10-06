package study.devmeetingstudy.dto.address;


import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import study.devmeetingstudy.domain.Address;

@Getter
public class AddressResDto {

    @ApiModelProperty(value = "주소 Id", example = "1")
    private final Long id;

    @ApiModelProperty(value = "도/특별시/광역시", required = true, example = "서울특별시")
    private final String address1;

    @ApiModelProperty(value = "시/군/구", required = true, example = "강남구")
    private final String address2;

    @ApiModelProperty(value = "읍/면/동", required = true, example = "서초동")
    private final String address3;

    public AddressResDto(Long id, String address1, String address2, String address3) {
        this.id = id;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
    }

    public static AddressResDto from(Address address){
        return new AddressResDto(
                address.getId(),
                address.getAddress1(),
                address.getAddress2(),
                address.getAddress3());
    }
}
