package com.simplemall.micro.serv.page.api;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.simplemall.micro.serv.common.bean.account.Message;
import com.simplemall.micro.serv.common.util.SlidercaptchaValidateUtil;
import com.simplemall.micro.serv.common.util.jiguang.JsmsService;
import com.simplemall.micro.serv.common.util.jiguang.JsmsUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.simplemall.micro.serv.common.bean.RestAPIResult;
import com.simplemall.micro.serv.common.bean.account.AccAddress;
import com.simplemall.micro.serv.common.bean.account.Account;
import com.simplemall.micro.serv.common.constant.SystemConstants;
import com.simplemall.micro.serv.common.service.JedisUtil;
import com.simplemall.micro.serv.common.util.UUIDUtils;
import com.simplemall.micro.serv.page.client.AccountFeignClient;
import com.simplemall.micro.serv.page.security.JWTUtils;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import sun.security.provider.MD5;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 采用feign客户端调用服务
 * 
 * @author guooo
 *
 */
@Api(value = "用户服务", tags = "用户服务接口")
@RestController
@RefreshScope // 使用该注解的类，会在接到SpringCloud配置中心配置刷新的时候，自动将新的配置更新到该类对应的字段中。需要重新触发加载动作可以使用POST方式请求/refresh接口，该接口位于spring-boot-starter-actuator依赖，调用前需添加否则404。
public class APIAccountController {

	private Logger logger = LoggerFactory.getLogger(APIAccountController.class);

	/**
	 * 短信开关
	 */
	@Value("${switch.sms}")
	private boolean switchSMS;

	@Autowired
	private AccountFeignClient accountFeignClient;

	@ApiOperation(value = "用户登陆")
	@RequestMapping(value = "acc/login", method = { RequestMethod.POST })
	public RestAPIResult<String> login(@ApiParam(value = "手机号") @RequestParam(required = true) String phone,
			@ApiParam(value = "密码") @RequestParam(required = true) String password, HttpServletRequest request,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Method", "POST,GET");
		RestAPIResult<String> restAPIResult = new RestAPIResult<>();
/*		//返回验证结果, request表单中必须包含csessionid, token, sig
		//验证码二次验证  手机验证
		Boolean captchaResult = SlidercaptchaValidateUtil.captchaAliyunValidate(request,true);
		if(!captchaResult) {
			restAPIResult.error("滑块验证失败!");
			restAPIResult.setRespData("");
			restAPIResult.setRespCode(9999);
			return restAPIResult;
		}*/
			String codeRedis = JedisUtil.STRINGS.get(phone);
		if(codeRedis!=null && password!=null &&(password).equals(codeRedis)){
				//是登陆还是注册
			Account account = accountFeignClient.selectByPhone(phone);
			if(account==null||account.getTid()==null){
				account = new Account();
				account.setPhone(phone);
				//注册
				account = accountFeignClient.signup(account);
				try {
					// 正常情况返回jwt
					JSONObject subject = new JSONObject(true);
					subject.put("tid", account.getTid());
					// token此处定义12小时有效，据实际应用场景确定有效性，也可以定义刷新机制，保持用户token的使用时限
					String accessToken = JWTUtils.createJWT(UUIDUtils.getUUID(), subject.toJSONString(),
							12 * 60 * 60 * 1000L);
					//JedisUtil.STRINGS.set("")
					restAPIResult.setRespData(accessToken);
					Map map =new HashMap<>();
					Message message = accountFeignClient.selectByUserId(account.getTid());
					map.put("account",account);
					map.put("message",message);
					restAPIResult.setRespMap(map);
				} catch (Exception e) {
					logger.error("生成jwt异常{}", e);
				}
			}else{
				//登录
				try {
					// 正常情况返回jwt
					JSONObject subject = new JSONObject(true);
					subject.put("tid", account.getTid());
					// token此处定义12小时有效，据实际应用场景确定有效性，也可以定义刷新机制，保持用户token的使用时限
					String accessToken = JWTUtils.createJWT(UUIDUtils.getUUID(), subject.toJSONString(),
							12 * 60 * 60 * 1000L);
					//JedisUtil.STRINGS.set("")
					restAPIResult.setRespData(accessToken);
					Map map =new HashMap<>();
					Message message = accountFeignClient.selectByUserId(account.getTid());
					map.put("account",account);
					map.put("message",message);
					restAPIResult.setRespMap(map);
				} catch (Exception e) {
					logger.error("生成jwt异常{}", e);
				}
			}

		}else{
			restAPIResult.error("登录或注册失败!");
			restAPIResult.setRespCode(44444);
			restAPIResult.setRespData("");
		}
		logger.info("login result = {}", restAPIResult.getRespData());
		return restAPIResult;
	}

	/**
	 * 获得短信验证码
	 *
	 * @param request
	 * @param phoneNum
	 * @return
	 */
	@RequestMapping(value = "acc/getPhoneCode", method = { RequestMethod.POST })
	public RestAPIResult<String> getPhoneCode(String phoneNum, HttpServletRequest request) {
		//返回验证结果, request表单中必须包含csessionid, token, sig
		//验证码二次验证
		RestAPIResult<String> restAPIResult = new RestAPIResult<>();
		Boolean captchaResult = SlidercaptchaValidateUtil.captchaAliyunValidate(request, true);
		if (!captchaResult) {
			restAPIResult.error("滑块验证失败!");
			restAPIResult.setRespCode(9999);
			restAPIResult.setRespData("");
			return restAPIResult;
		}
		try {
			Map<String, String> temp_para = new HashMap<>();
			//使用极光短信接口发送验证码
			String code = JsmsUtils.getCode();
			JedisUtil.STRINGS.set(phoneNum,code);
			temp_para.put("code", code);
			logger.info("验证码为："+code);
			//暂时先不发短信
			//Map<String, Object> map = JsmsService.sendSms(phoneNum, JsmsUtils.YZM, temp_para);
			Map<String, Object> map =new HashMap<>();
			map.put("error","0");
			if (map.get("error").equals("0")) {
				restAPIResult.success(code);
				return restAPIResult;
			} else {
				restAPIResult.error("短信发送失败!");
				restAPIResult.setRespCode(5555);
				restAPIResult.setRespData(map.toString());
				return restAPIResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			restAPIResult.error("系统错误!");
			restAPIResult.setRespCode(6666);
			restAPIResult.setRespData("");
		}
		return restAPIResult;
	}

	/**
	 * @param message
	 * @return
	 */
	@ApiOperation(value = "用户资料完善")
	@RequestMapping(value = "acc/improveMessage", method = { RequestMethod.POST })
		public RestAPIResult<String> improveMessage(Message message ,HttpServletRequest request,HttpServletResponse response) {
/*		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Method", "POST,GET");*/
		RestAPIResult<String> restAPIResult = new RestAPIResult<>();
		int rtn = accountFeignClient.improveMessage(message);
		if (rtn<1) {
			restAPIResult = new RestAPIResult<>("完善失败!");
		} else {
			if (switchSMS) {
				logger.info("开始发送注册成功短信！");
			}
		}
		restAPIResult.setRespData("");
		logger.info("login result = {}", restAPIResult.getRespData());
		return restAPIResult;
	}

	/**
	 * query account's address list
	 *
	 * @param accountTid
	 * @return
	 */
	@ApiOperation(value = "获取用户地址列表")
	@RequestMapping(value = "address/list/{accountTid}", method = RequestMethod.POST)
	public RestAPIResult<List<AccAddress>> queryAccAddress(@PathVariable("accountTid") String accountTid,
														   String accessToken) {
		RestAPIResult<List<AccAddress>> apiResult = new RestAPIResult<>();
		List<AccAddress> liString = accountFeignClient.getList(accountTid);
		apiResult.setRespData(liString);
		return apiResult;
	}

	/**
	 * query account's address list
	 *
	 * @return
	 */
	@ApiOperation(value = "获取用户信息列表")
	@RequestMapping(value = "acc/list",method = RequestMethod.POST)
	public RestAPIResult<PageInfo<Map<Object,Object>>> queryAccAccount(Message message,@RequestParam(required = true) Integer currentPage,@RequestParam(required = true) Integer pageSize,String accessToken) {
		RestAPIResult<PageInfo<Map<Object,Object>>> apiResult = new RestAPIResult<>();
		PageInfo<Map<Object,Object>> pageInfo = accountFeignClient.getAccList(message,currentPage,pageSize);
		apiResult.setRespData(pageInfo);
		return apiResult;
	}
	/**
	 * query account's address list
	 *
	 * @return
	 */
	@ApiOperation(value = "获取用户详情")
	@RequestMapping(value = "acc/detail",method = RequestMethod.POST)
	public RestAPIResult<Map<Object,Object>> getAccAccount(@RequestParam(required = true) String userId,@RequestParam(required = true) String tid,String accessToken) {
		RestAPIResult<Map<Object,Object>> apiResult = new RestAPIResult<>();
		Map<Object,Object> map = accountFeignClient.getAccDetail(tid);
		apiResult.setRespData(map);
		return apiResult;
	}
	/**
	 * query account's address list
	 *
	 * @return
	 */
	@ApiOperation(value = "容联获取sig")
	@RequestMapping(value = "acc/getSig",method = RequestMethod.GET)
	public RestAPIResult<String> getSig(@RequestParam(required = true) String userId,@RequestParam(required = true) String timestamp,String accessToken) {
		String appId = "8aaf0708679d082f0167a0f8bb0601e6";
		String apptoken = "1c9a119f64fe3783fddc536a6632b0f2";
		String userName2 = "fyc4";
		String timestamp2 = "20181213042353";
		RestAPIResult<String> apiResult = new RestAPIResult<>();
		String sig = md5(appId+userName2+timestamp2+apptoken);
		apiResult.setRespData(sig);
		return apiResult;
	}
	//写一个md5加密的方法
	public  static String md5(String plainText) {
		//定义一个字节数组
		byte[] secretBytes = null;
		try {
			// 生成一个MD5加密计算摘要
			MessageDigest md = MessageDigest.getInstance("MD5");
			//对字符串进行加密
			md.update(plainText.getBytes());
			//获得加密后的数据
			secretBytes = md.digest();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("没有md5这个算法！");
		}
		//将加密后的数据转换为16进制数字
		String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
		// 如果生成数字未满32位，需要前面补0
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code = "0" + md5code;
		}
		return md5code;
	}
	/**
	 * logout
	 * 
	 * @param accountTid
	 * @param accessToken
	 *            用于注销时写入redis，因jwt有效期内均有效 ，本案例借助redis实现注销机制
	 * @return
	 */
	@ApiOperation(value = "注销")
	@RequestMapping(value = "acc/logout", method = RequestMethod.POST)
	public RestAPIResult<Boolean> logout(String accountTid, String accessToken) {
		// 将用户的accessToken写入缓存，并给于失效日期，用户退出后，再以此token请求即为无效请求
		// 解析出失效时间，写入缓存
		Claims claims = JWTUtils.parseJWT(accessToken);
		long terminal = claims.getExpiration().getTime();
		JedisUtil.STRINGS.set(accessToken, accessToken);
		JedisUtil.KEYS.expireAt(accessToken, terminal);
		return new RestAPIResult<>();
	}
	//主函数调用测试
	public static void main(String[] args) {
		String appId = "8aaf0708679d082f0167a0f8bb0601e6";
		String apptoken = "1c9a119f64fe3783fddc536a6632b0f2";
		String userName2 = "fyc4";
		String timestamp2 = "20181213042353";
		System.out.println(md5(appId+userName2+timestamp2+apptoken));
	}
}
