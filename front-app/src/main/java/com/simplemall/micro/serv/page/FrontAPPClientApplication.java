
package com.simplemall.micro.serv.page;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;

import java.nio.charset.Charset;
import java.util.Base64;

@SuppressWarnings("Since15")
@ComponentScan
@EnableFeignClients
@EnableHystrix
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class FrontAPPClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrontAPPClientApplication.class, args);
	}

	//首先先使用ribbon提供的LoadBalanced注解加在RestTemplate上面，
	// 这个注解会自动构造LoadBalancerClient接口的实现类并注册到Spring容器中。
	//接下来使用RestTemplate进行rest操作的时候，会自动使用负载均衡策略，
	// 它内部会在RestTemplate中加入LoadBalancerInterceptor这个拦截器，
	// 这个拦截器的作用就是使用负载均衡13681024852

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	/**
	 * ribbon loadBalance algorithm
	 *其中RandomRule表示随机策略、RoundRobin表示轮询策略、WeightedResponseTimeRule表示加权策略、区域感知轮询负载均衡（ZoneAwareRule
	 * BestAvailableRule表示请求数最少策略等等。
	 * @return
	 */
	@Bean
	public IRule ribbonRule() {
		return new RandomRule();
	}


	//定义一个Bean修改头信息进行客户端认证
	@Bean
	public HttpHeaders getHeader() {
		HttpHeaders headers=new HttpHeaders();
		String auth="jmxjava:jmxhello";//认证的原始信息
		byte[] encodeAuth= Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")));//将原始认证信息进行Base64加密
		String authHeader="Basic "+new String(encodeAuth);//加密后的认证信息要与Basic有个空格
		headers.set("Authorization", authHeader);
		return headers;
	}
}
