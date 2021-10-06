package study.devmeetingstudy.dto.study.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import study.devmeetingstudy.domain.study.Offline;
import study.devmeetingstudy.dto.address.AddressResDto;

@Data
public class OfflineResDto {

    @ApiModelProperty(value = "오프라인 아이디", example = "1")
    private Long id;

    @ApiModelProperty(value = "주소", notes = "오프라인에 해당하는 주소가 들어갑니다.")
    private AddressResDto address;

    public OfflineResDto(Long id, AddressResDto address) {
        this.id = id;
        this.address = address;
    }

    public static OfflineResDto from(Offline offline) {
        return new OfflineResDto(offline.getId(), AddressResDto.from(offline.getAddress()));
    }
}
