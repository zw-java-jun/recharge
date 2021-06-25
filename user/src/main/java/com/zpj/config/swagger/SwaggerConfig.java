package com.zpj.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket docket(){
        ApiInfo apiInfo=new ApiInfoBuilder()
                .title("用户管理")//api标题
                .description("用户管理相关接口描述")//api描述
                .version("1.0.0")//版本号
                .contact("zpj")//本API负责人的联系信息
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo.DEFAULT)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zpj.user"))
                .build();
    }
}
