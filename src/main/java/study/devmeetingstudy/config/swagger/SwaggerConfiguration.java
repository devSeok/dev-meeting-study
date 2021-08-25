package study.devmeetingstudy.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;


// http://{host}/swagger-ui.html
// jwt 해더 인증 추가  : https://www.baeldung.com/spring-boot-swagger-jwt
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket swaggerAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
                .consumes(getConsumeContentTypes())
                .produces(getProduceContentTypes())
                .apiInfo(swaggerInfo())  // 스워거 정보 등록
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()))
                .useDefaultResponseMessages(false)
                .select()
//                .apis(RequestHandlerSelectors.any())  // 모든 Controller 들이 있는 패키지를 탐색해서 API 문서를 만든다
                .apis(RequestHandlerSelectors.basePackage("study.devmeetingstudy.controller"))  // 모든 Controller 들이 있는 패키지를 탐색해서 API 문서를 만든다
                .paths(PathSelectors.ant("/**"))
                .build();
//                .useDefaultResponseMessages(true); // 기본으로 세팅되는 200, 401, 403, 404 메시지 표시
    }

    private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }

    private ApiKey apiKey() {
        return new ApiKey("Bearer +accessToken", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;

        return Collections.singletonList(new SecurityReference("Bearer +accessToken", authorizationScopes));
    }

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder()
                .title("dev-meeting-study API Documentation")
                .description("인증외에 다른 api는 액세스 토큰값이 필요합니다. " +
                        "매번 인증하기 귀찬은것은 아래 Authorize 버튼 한번 연동하면 로그인 세션처럼 매번 인증 해더를 안넣고 사용이 가능합니다.")
                .contact(new Contact("[dev-meeting-study]",
                        "https://github.com/devSeok/dev-meeting-study",
                        "stay0073@naver.com")
                )
                .version("1.0")
                .build();
    }
}


