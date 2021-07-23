package study.devmeetingstudy.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import study.devmeetingstudy.service.UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;


    // 인증을 무시할 결로를 설정해뒀습니다.
    // static 하위 폴드는 무조건 접근이 가능하도록 설정하였습니다.


    // 로그인 성공후에 에러가 발생하였다. 999
    // 해결 방안
    // https://stackoverflow.com/questions/61029340/spring-security-redirects-to-page-with-status-code-999/61029341
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**")
                .antMatchers("/favicon.ico", "/resources/**", "/error");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/" ,"/login", "/signup", "/user").permitAll() // 누구나 접근 허용
//                    .antMatchers("/").hasRole("USER") // USER, ADMIN만 접근 가능
                    .antMatchers("/admin").hasRole("ADMIN") // ADMIN만 접근 가능
                    .anyRequest().authenticated() // 나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 가능
                .and()
                    .formLogin()
                    .loginPage("/login") // 로그인 페이지 링크
                    .defaultSuccessUrl("/") // 로그인 성공 후 리다이렉트 주소
                .and()
                    .logout() // 로그아웃 설정
                        .logoutSuccessUrl("/login") // 로그아웃 성공시 리다이렉트 주소
                        .invalidateHttpSession(true) // 세션 날리기
        ;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception { // 9
        auth.userDetailsService(userService)
                // 해당 서비스(userService)에서는 UserDetailsService를 implements해서
                // loadUserByUsername() 구현해야함 (서비스 참고)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
