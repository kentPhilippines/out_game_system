package com.lt.win.apiend.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    public ResourceBundleMessageSource getMessageSource() throws Exception {
        ResourceBundleMessageSource rbms = new ResourceBundleMessageSource();
        rbms.setDefaultEncoding("UTF-8");
        rbms.setBasenames("i18n/ValidationMessages"); // 此为文件目录 ValidationMessages是文件名
        rbms.setCacheSeconds(-1);
        return rbms;
    }

    @Bean
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        try {
            validator.setValidationMessageSource(getMessageSource());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return validator;
    }



//    @Resource
//    private AuthenticationInterceptor authenticationInterceptor;
//
//    @Resource
//    RepeatedSubmit repeatedSubmit;

//    @Autowired
//    private MessageSource messageSource;
//
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authenticationInterceptor);
//        registry.addInterceptor(repeatedSubmit);
////        registry.addInterceptor(repeatedSubmit);
//    }
//
//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//
//    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        //静态资源 swagger-bootstrap-ui
//        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }


//    /**
//     * 简单跨域就是GET，HEAD和POST请求，
//     * 但是POST请求的"Content-Type"只能是application/x-www-form-urlencoded, multipart/form-data 或 text/plain反之，
//     * 就是非简单跨域，此跨域有一个预检机制，说直白点，就是会发两次请求，一次OPTIONS请求，一次真正的请求
//     * @return
//     */
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowCredentials(true)// 允许cookies跨域
//                        .allowedOrigins("*")// #允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin
//                        .allowedHeaders("*")// #允许访问的头信息,*表示全部
//                        .maxAge(18000L)// 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
//                        .allowedMethods("*");// 允许提交请求的方法,*表示全部允许
//            }
//        };
//    }

}

