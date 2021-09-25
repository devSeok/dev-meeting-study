package study.devmeetingstudy.dto.address;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import springfox.documentation.annotations.ApiIgnore;

@Data
@NoArgsConstructor
public class AddressRequestDto {

    @ApiModelProperty(value = "주소 아이디", required = false, example = "1")
    private Long id;

    @ApiModelProperty(value = "도/특별시/광역시", required = true, example = "서울특별시")
    private String address1;

    @ApiModelProperty(value = "시/군/구", required = true, example = "강남구")
    private String address2;

    @ApiModelProperty(value = "읍/면/동", required = true, example = "서초동")
    private String address3;

    public AddressRequestDto(String address1, String address2, String address3){
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
    }
    
}
