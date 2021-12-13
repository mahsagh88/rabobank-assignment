package nl.rabobank;
/**
 * @author Provide
 *
 * 
 */

import java.util.Collections;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

   
    @PostConstruct
    private void init() {

    }

    @Bean
    public Docket taskApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot.autoconfigure.web.servlet.error")))
                .paths(Predicates.not(PathSelectors.regex("*/error")))
                .build();
    }
 

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Bank API",
                "Bank API for push.",
                "1.0",
                "Terms of service",
                new Contact("Bank", "www.bank.io", "support@bank.io"),
                "License of API",
                "API license URL",
                Collections.emptyList());
    }
}
