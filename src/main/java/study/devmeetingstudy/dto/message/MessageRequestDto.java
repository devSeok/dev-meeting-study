package study.devmeetingstudy.dto.message;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageRequestDto {

    private Long memberId;

    private String content;

}
