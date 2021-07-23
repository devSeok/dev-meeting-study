package study.devmeetingstudy.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import study.devmeetingstudy.domain.UserStatus;

@Getter
@Setter
public class UserDto {

    private String email;
    private String password;

    private String name;

    private String auth; // 권한
    private int grade;  // 평점

    private UserStatus status;

}
