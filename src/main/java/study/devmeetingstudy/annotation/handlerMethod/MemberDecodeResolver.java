package study.devmeetingstudy.annotation.handlerMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import study.devmeetingstudy.annotation.JwtMember;
import study.devmeetingstudy.annotation.dto.MemberResolverDto;
import study.devmeetingstudy.common.exception.global.error.exception.ErrorCode;
import study.devmeetingstudy.common.exception.global.error.exception.TokenException;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.jwt.TokenProvider;
import study.devmeetingstudy.repository.MemberRepository;

@Slf4j
@RequiredArgsConstructor
@Component
public class MemberDecodeResolver implements HandlerMethodArgumentResolver {

    private final ObjectMapper objectMapper;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    /**
     * resolveArgument를 수행할지 결정할 수 있는 메서드이다.
     * true가 리턴되면 resolveArgument가 수행된다.
     * 위에서 생성한 TokenMemberEmail Annoation과 파라미터의 타입이 MemberResolverDto 일 경우에만 resolveArgument를 수행시킨다.
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isTokenMember = parameter
                .getParameterAnnotation(JwtMember.class) != null;

//        boolean isString = String.class.equals(parameter.getParameterType());

        boolean isParameter = parameter.getParameterType().equals(MemberResolverDto.class);

        return isTokenMember && isParameter;
    }

    @Override
    public MemberResolverDto resolveArgument(
            MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory
    ) throws Exception {
        String authorizationHeader = webRequest.getHeader("Authorization");
        log.info("Authorization Header ::: " + authorizationHeader);

        if (authorizationHeader == null) {
            throw new TokenException("Access Token이 존재하지 않습니다." , ErrorCode.ACCESSTOKEN_NOT_HAVE);
        }

        String jwtToken = authorizationHeader.substring(7);
        Authentication authentication = tokenProvider.getAuthentication(jwtToken);

        Member findMember = memberRepository.findById(Long.valueOf(authentication.getName()))
                .orElseThrow(
                        () -> new TokenException("Access Token이 존재하지 않습니다." , ErrorCode.ACCESSTOKEN_NOT_HAVE)
                );

        return MemberResolverDto.from(findMember);
    }
}
