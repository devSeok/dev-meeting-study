package study.devmeetingstudy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.annotation.dto.MemberResolverDto;
import study.devmeetingstudy.common.exception.global.error.exception.*;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.RefreshToken;
import study.devmeetingstudy.domain.member.enums.MemberStatus;
import study.devmeetingstudy.dto.member.request.MemberLoginReqDto;
import study.devmeetingstudy.dto.member.request.MemberSignupReqDto;
import study.devmeetingstudy.dto.token.TokenDto;
import study.devmeetingstudy.dto.token.request.TokenReqDto;
import study.devmeetingstudy.jwt.TokenProvider;
import study.devmeetingstudy.repository.MemberRepository;
import study.devmeetingstudy.repository.RefreshTokenRepository;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public Member signup(MemberSignupReqDto memberRequestDto) {
        signupValidation(memberRequestDto);
        Member createMember = Member.createMember(memberRequestDto, passwordEncoder);

        return memberRepository.save(createMember);
    }

    @Transactional
    public TokenDto login(MemberLoginReqDto memberRequestDto, HttpServletResponse response) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();

        Member byEmail = memberRepository.findByEmail(memberRequestDto.getEmail())
                .orElseThrow(() -> new UserException("유저 정보가 없습니다."));

        userLoginValidation(byEmail, memberRequestDto);


        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 인증정보 쿠키 저장
        tokenProvider.createCookie(response, tokenDto.getAccessToken());

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.createRefreshToken(authentication, tokenDto);

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenReqDto tokenReqDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenReqDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenReqDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenReqDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }

    public void checkUserInfo(Long id, MemberResolverDto dto){
        if (!jwtWithParameter(id, dto)) throw new UserInfoMismatchException("유저 정보가 일치하지 않습니다.");
    }

    private boolean jwtWithParameter(Long id, MemberResolverDto dto){
        return dto.getId().equals(id);
    }

    private void signupValidation(MemberSignupReqDto memberRequestDto){
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new SignupDuplicateException("이미 가입되어 있는 유저입니다.");
        }

        if (memberRepository.existsByNickname(memberRequestDto.getNickname())) {
            throw new SignupDuplicateException("이미 사용중인 닉네임입니다.", ErrorCode.NICKNAME_DUPLICATION);
        }
    }

    private void userLoginValidation(Member byEmail, MemberLoginReqDto memberRequestDto){
        if (byEmail.getStatus() != MemberStatus.ACTIVE) {
            throw new UserOutException("탈퇴한 회원입니다.", ErrorCode.USER_OUT);
        }

        if(!passwordEncoder.matches(memberRequestDto.getPassword(), byEmail.getPassword())) {
            throw new UserOutException("비밀번호가 다릅니다.", ErrorCode.USER_NOT_PASSWORD);
        }
    }
}
