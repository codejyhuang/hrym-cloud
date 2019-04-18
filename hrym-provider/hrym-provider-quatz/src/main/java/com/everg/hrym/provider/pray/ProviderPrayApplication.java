package com.everg.hrym.provider.pray;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by hong on 2019/3/21.
 */
@SpringBootApplication
@MapperScan({"com.everg"})
@ComponentScan(basePackages={"com.everg.hrym"})
@EnableDistributedTransaction
@EnableFeignClients("com.everg.hrym.api")
public class ProviderPrayApplication {


    public static void main(String[] args){
        SpringApplication.run(ProviderPrayApplication.class,args);
    }
}
