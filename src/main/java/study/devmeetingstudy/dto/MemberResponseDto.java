package study.devmeetingstudy.dto;


import lombok.*;
import study.devmeetingstudy.domain.Authority;
import study.devmeetingstudy.domain.Member;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.function.Function;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {

    private String email;
    private int grade;

    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(member.getEmail(), member.getGrade());
    }
}
