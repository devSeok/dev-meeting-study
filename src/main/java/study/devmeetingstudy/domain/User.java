package study.devmeetingstudy.domain;


import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import study.devmeetingstudy.domain.Base.BaseTimeEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
public class User extends BaseTimeEntity implements UserDetails{

    public User() {

    }

    @Id @GeneratedValue
    @Column(name = "user_id", unique = true)
    private Long id;

    private String email;

    @Column(name = "name", length = 30)
    private String name;
    private String password;
    private String auth; // 권한

    @ColumnDefault("0")
    private int grade;  // 평점

    @Enumerated(EnumType.STRING)
    private UserStatus status;  // 회원상태

    @Builder
    public User(String email, String name, String password, String auth, int grade, UserStatus status) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.auth = auth;
        this.grade = grade;
        this.status = status;
    }

    // 사용자의 권한을 콜렉션 형태로 반환
    // 단, 클래스 자료형은 GrantedAuthority를 구현해야함
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : auth.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return  email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 패스워드가 만료되었는지 확인
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        return true;
    }
}
