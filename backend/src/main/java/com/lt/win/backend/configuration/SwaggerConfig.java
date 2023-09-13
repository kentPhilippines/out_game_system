package com.lt.win.backend.configuration;


import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {
    @Value("${swagger.enable}")
    private boolean enable;

    @Bean(value = "defaultApi")
    public Docket defaultApi() {

        //在配置好的配置类中增加此段代码即可
        List<Parameter> pars = new ArrayList<>();

        ParameterBuilder ticketParL = new ParameterBuilder();
        /* 语言 */
        ticketParL.name("Accept-Language").description("请求语言:en th zh vi")
                .modelRef(new ModelRef("string")).parameterType("header")
                /*required表示是否必填，defaultValue表示默认值*/
                .required(true).defaultValue("zh").build();
        /* 设备 */
        ParameterBuilder ticketParDevice = new ParameterBuilder();
        ticketParDevice.name("Accept-Device").description("设备:H5(m) PC(d) Android(android) IOS(ios)")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true).defaultValue("m").build();

        /*添加完此处一定要把下边的也加上否则不生效*/
        pars.add(ticketParL.build());
        pars.add(ticketParDevice.build());

        /*配置返回状态码*/
        List<ResponseMessage> responseMessageList = new ArrayList<>();

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enable)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lt.win.backend"))
                .paths(PathSelectors.any())
                .build()
//                .groupName("default")
                .globalOperationParameters(pars)
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList);
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("BWG体育接口文档")
                .description("Sports Backend文档")
                .termsOfServiceUrl("")
                .version("1.0")
                .license("The Apache License")
//                .licenseUrl("http://www.baidu.com")
                .build();
    }

}
