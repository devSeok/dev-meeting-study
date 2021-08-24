package study.devmeetingstudy.common.exception.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ApiResponseDto<T> {

    private String message;
    private int status;
    private T data;
}
