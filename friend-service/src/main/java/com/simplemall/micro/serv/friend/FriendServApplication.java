package com.simplemall.micro.serv.friend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class FriendServApplication {

	public static void main(String[] args) {
		SpringApplication.run(FriendServApplication.class, args);
	}
}
