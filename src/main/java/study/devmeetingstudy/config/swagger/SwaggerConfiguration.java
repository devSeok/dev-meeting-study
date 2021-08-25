package study.devmeetingstudy.config.swagger;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;



// http://{host}/swagger-ui.html
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    public Docket swaggerAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.swaggerInfo())  // 스워거 정보 등록
                .select()
//                .apis(RequestHandlerSelectors.any())  // 모든 Controller 들이 있는 패키지를 탐색해서 API 문서를 만든다
                .apis(RequestHandlerSelectors.basePackage("study.devmeetingstudy.controller"))  // 모든 Controller 들이 있는 패키지를 탐색해서 API 문서를 만든다
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Arrays.asList(apiKey()));
//                .useDefaultResponseMessages(true); // 기본으로 세팅되는 200, 401, 403, 404 메시지 표시
    }

    private ApiKey apiKey() {
        return new ApiKey("Bearer +accessToken", "Authorization", "header");
    }

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder()
                .title("dev-meeting-study API Documentation")
                .description("")
                .version("1.0.0")
                .build();
    }
}


