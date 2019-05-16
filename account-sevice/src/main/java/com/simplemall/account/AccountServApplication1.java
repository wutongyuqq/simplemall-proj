package com.simplemall.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * 启动类
 *
 * @author guooo
 *
 */
@SpringBootApplication
//注解用来将当前应用加入到服务治理体系中。
@EnableDiscoveryClient
@EnableHystrix
public class AccountServApplication1 {

    public static void main(String[] args) {
        SpringApplication.run(AccountServApplication1.class, args);
    }

}
