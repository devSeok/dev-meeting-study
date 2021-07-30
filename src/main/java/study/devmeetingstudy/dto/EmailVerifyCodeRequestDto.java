package study.devmeetingstudy.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailVerifyCodeRequestDto {

    private String email;
    private String auth_number;
}
