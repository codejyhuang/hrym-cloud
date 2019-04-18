package com.everg.hrym.server.zuul;

import com.everg.hrym.server.zuul.filter.ErrorFilter;
import com.everg.hrym.server.zuul.filter.PostFilter;
import com.everg.hrym.server.zuul.filter.PreFilter;
import com.everg.hrym.server.zuul.filter.RouteFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * zuul网关
 *
 * @author hong
 * @create 2019-04-1 14:52
 **/
@SpringBootApplication
@EnableZuulProxy
@EnableHystrixDashboard
public class ZuulServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulServerApplication.class, args);
    }

    @Bean
    public ErrorFilter errorFilter() {
        return new ErrorFilter();
    }

    @Bean
    public PostFilter postFilter() {
        return new PostFilter();
    }

    @Bean
    public PreFilter preFilter() {
        return new PreFilter();
    }

//    @Bean
//    public RouteFilter routeRequestFilter() {
//        return new RouteFilter();
//    }



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
}
