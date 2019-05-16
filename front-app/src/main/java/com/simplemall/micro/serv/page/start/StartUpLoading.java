package com.simplemall.micro.serv.page.start;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.simplemall.micro.serv.common.constant.SystemConstants;
import com.simplemall.micro.serv.common.service.JedisUtil;

/**
 * 启动后就加载数据
 * 
 * @author guooo
 *
 */
@Component
public class StartUpLoading implements CommandLineRunner {
	private Logger logger = LoggerFactory.getLogger(StartUpLoading.class);
//Spring Boot应用程序在启动后，会遍历CommandLineRunner接口的实例并运行它们的run方法。
// 也可以利用@Order注解（或者实现Order接口）来规定所有CommandLineRunner实例的运行顺序。
	@Override
	public void run(String... arg0) throws Exception {
		logger.info("--->>>开始初始化加载数据{}", new Date());
		// 加载需要token拦截的URI，能过配置文件或比较便捷的方式，不建议在代码中配置，此处仅为方便演示
		Set<String> uris = new HashSet<>();
		uris.add("/order/create");
		uris.add("/order/view");
		uris.add("/order/state/change");
		uris.add("/payment/pay");
		
		for (String uri : uris) {
			JedisUtil.SETS.sadd(SystemConstants.URL_NEED_CHECK_KEY, uri);
		}
	}

}
