package study.devmeetingstudy.dto.address;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestDto {

    @ApiModelProperty(value = "도/특별시/광역시", required = true, example = "서울특별시")
    private String address1;

    @ApiModelProperty(value = "시/군/구", required = true, example = "강남구")
    private String address2;

    @ApiModelProperty(value = "읍/면/동", required = true, example = "서초동")
    private String address3;
    
}
