package study.devmeetingstudy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import study.devmeetingstudy.domain.User;
import study.devmeetingstudy.domain.UserStatus;
import study.devmeetingstudy.dto.UserDto;
import study.devmeetingstudy.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException((email)));
    }

    public Long save(UserDto userDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        userDto.setGrade(0);
        userDto.setStatus(UserStatus.active);

        return userRepository.save(
                User.builder()
                .email(userDto.getEmail())
                .auth(userDto.getAuth())
                .password(userDto.getPassword())
                        .grade(userDto.getGrade())
                .build()
        ).getId();
    }
}

