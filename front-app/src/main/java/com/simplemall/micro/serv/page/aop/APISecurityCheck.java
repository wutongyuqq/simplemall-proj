package com.simplemall.micro.serv.page.aop;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.simplemall.micro.serv.common.bean.RestAPIResult;
import org.apache.log4j.spi.LoggerFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.simplemall.micro.serv.common.service.JedisUtil;
import com.simplemall.micro.serv.page.security.JWTUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Aspect
@Order(0)
@Component
public class APISecurityCheck {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	/**
	 * 客户端给出的签名字段
	 */
	private static final String access_token = "accessToken";

	/**
	 * 前置通知：所有接口在执行业务之前，需要先进行参数合法性校验 执行顺序为1
	 * 
	 * @param joinPoint
	 * @throws Exception
	 */
	@Before("execution(public * com.simplemall.micro.serv.page.api.*.* (..))")
	public void doBeforeInService(JoinPoint joinPoint) throws Exception {
		RestAPIResult<String> restAPIResult = new RestAPIResult<>();
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();
		HttpServletResponse response =sra.getResponse();
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Method", "POST,GET");
		// 需要过滤URI的请求，有些不需要token的地方直接跳过不再校验
		String requestPath = request.getRequestURI(); // 请求的URL
		logger.info("request path = " + requestPath);
		if(("/acc/").contains(requestPath)){
			return;
		}
		Map<String, String[]> inputParamMap = request.getParameterMap();
		Iterator<String> keyIter = inputParamMap.keySet().iterator();
		while (keyIter.hasNext()) {
			String currKey = keyIter.next();
			String value = ((String[]) inputParamMap.get(currKey))[0].toString();
			if (access_token.equals(currKey)) {
				//验证此jwt是否已经被注销，由于jwt在有效期均有效，本案例借助redis实现注销机制
				if(JedisUtil.KEYS.exists(value)){
					restAPIResult.error("token已注销，请勿重复使用！");
					restAPIResult.setRespData("");
					return;
					//throw new Exception("token已注销，请勿重复使用！");
				}
				try {
					JWTUtils.parseJWT(value);
					restAPIResult.success("token校验成功！");
					restAPIResult.setRespData("");
					return;
				} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
						| IllegalArgumentException exception) {
					restAPIResult.error("token校验失败！");
					restAPIResult.setRespData("");
					return;
					//throw new Exception("token校验失败！");
				}
			}else{
				restAPIResult.error("未传入token！");
				restAPIResult.setRespData("");
				System.out.println("---" + currKey + "------------" + value + "--------");
				return;
				//throw new Exception("未传入token！");
			}
		}
		restAPIResult.error("未传入token！");
		restAPIResult.setRespData("");
		return;
		//throw new Exception("未传入token！");
	}
}
