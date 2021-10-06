package study.devmeetingstudy.dto.study.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import study.devmeetingstudy.domain.study.Online;

@Data
public class OnlineResDto {

    @ApiModelProperty(value = "온라인 아이디", example = "1")
    private Long id;

    @ApiModelProperty(value = "링크 주소", example = "https://discord.com/asdfasdfasdfasdf")
    private String link;

    @ApiModelProperty(value = "소프트웨어 종류", example = "디스코드")
    private String onlineType;

    public OnlineResDto(Long id, String link, String onlineType) {
        this.id = id;
        this.link = link;
        this.onlineType = onlineType;
    }

    public static OnlineResDto from(Online online) {
        return new OnlineResDto(online.getId(), online.getLink(), online.getOnlineType());
    }
}
