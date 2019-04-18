package com.everg.hrym.application.app;

import com.everg.hrym.common.core.decoder.HrymResponseEntityDecoder;
import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Created by hong on 2019/3/22.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.everg.hrym")
@ComponentScan(basePackages={"com.everg.hrym"})
public class AppApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        //允许跨域
        config.setAllowCredentials(true);
        //允许向该服务器提交请求的URI,*表示全部
        config.addAllowedOrigin("*");
        //允许访问的头信息,*表示全部
        config.addAllowedHeader("*");
        //允许的method
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        //免检时间,单位是秒
        //config.setMaxAge(3600)
        //Enabling CORS for the whole application
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    @Bean
    public Decoder feignDecoder(){

        HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);
        return new HrymResponseEntityDecoder(new SpringDecoder(objectFactory));
    }

}
