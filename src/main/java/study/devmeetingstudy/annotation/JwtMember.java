package study.devmeetingstudy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


//TODO : @ApiIgnore 포함 되게 하기
// 참고 사이트 : https://sun-22.tistory.com/76
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface JwtMember {
}
