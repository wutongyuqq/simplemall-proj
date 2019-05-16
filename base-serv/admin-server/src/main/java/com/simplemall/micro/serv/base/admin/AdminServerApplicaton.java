package com.simplemall.micro.serv.base.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 入口方法-
 * 
 * @author guooo
 *
 */
@SpringBootApplication
@EnableAdminServer
@EnableZuulProxy
@EnableDiscoveryClient
@EnableOAuth2Sso
public class AdminServerApplicaton {

	public static void main(String[] args) {
		SpringApplication.run(AdminServerApplicaton.class, args);
	}

}
