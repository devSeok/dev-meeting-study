package study.devmeetingstudy.common.exception.global.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class ApiResponseDto<T> {

    private String message;
    private int status;
    private T data;

    @Builder
    public ApiResponseDto(String message, int status, T data){
        this.message = message;
        this.status = status;
        this.data = data;
    }
}
