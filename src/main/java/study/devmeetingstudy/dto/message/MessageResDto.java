package study.devmeetingstudy.dto.message;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.message.Message;
import study.devmeetingstudy.domain.message.enums.MessageDeletionStatus;
import study.devmeetingstudy.domain.message.enums.MessageReadStatus;
import study.devmeetingstudy.dto.member.response.MemberResDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MessageResDto {
    @ApiModelProperty(value = "메시지 아이디", example = "1", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "메시지를 보낸 사람")
    private MemberResDto sender;

    @ApiModelProperty(value = "메시지를 받은 사람")
    private MemberResDto member;

    @ApiModelProperty(value = "메시지 내용", example = "하이")
    private String content;

    @ApiModelProperty(value = "메시지 삭제 상태", example = "NOT_DELETED")
    private MessageDeletionStatus delflg;

    @ApiModelProperty(value = "메시지 읽음 상태", example = "READ")
    private MessageReadStatus status;

    @ApiModelProperty(value = "보낸 일자")
    private LocalDateTime createdDate;

    @ApiModelProperty(value = "마지막으로 업데이트 된 일자")
    private LocalDateTime lastUpdateDate;


    public static MessageResDto of(Message message, Member sender, Member member){
        return new MessageResDto(
                message.getId(),
                MemberResDto.from(sender),
                MemberResDto.from(member),
                message.getContent(),
                message.getDelflg(),
                message.getStatus(),
                message.getCreatedDate(),
                message.getLastUpdateDate());
    }
}
