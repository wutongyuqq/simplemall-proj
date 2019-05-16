package com.simplemall.micro.serv.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 银行卡四要素验证工具类
 * @author gbf
 *
 */
public class BankSysUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        //System.out.println(check("郭兵锋","420621198401010001","6226090271395069","13112345678"));
	}
	
	/**
	 * 四要素验证
	 * @param name 姓名
	 * @param idCard 身份证号
	 * @param bankNo 银行卡号
	 * @param mobile 银行预留手机号
	 * @return true 表示一致，false表示不一致
	 */
	public static boolean check(String name, String idCard, String bankNo, String mobile){
		Boolean ck = checkByJnlz(name,idCard,bankNo,mobile);
		if(ck == null){//扩展其他收费借口
			ck = checkByApi1(name,idCard,bankNo,mobile);
		}
		return ck;
	}
	
	private static final String URL = "http://lundroid.market.alicloudapi.com/lianzhuo/verifi?";
	private static final String APP_CODE = "96663088c49d446a803519d2f586e7d3";
	
	/**
	 * 济南联卓信息技术有限公司提供的 四要素接口
	 * 文档地址：https://market.aliyun.com/products/57000002/cmapi014429.html?spm=5176.2020520132.101.61.wguKjg#sku=yuncode842900008
	 * @return
	 */
	private static Boolean checkByJnlz(String name, String idCard, String bankNo, String mobile){
		try {
			name  = URLEncoder.encode(name, "utf-8");
			String param = "acct_name="+name+"&acct_pan="+bankNo+"&cert_id="+idCard;//+"&phone_num="+mobile;
			String result = get(URL+param,APP_CODE);
			//System.out.println(result);
			//认证成功时：{"data":{},"resp":{"code":0,"desc":"OK"}}
			//认证失败时：{"data":{},"resp":{"code":5,"desc":"持卡人认证失败"}}
			JSONObject resp = JSONObject.parseObject(result).getJSONObject("resp");
			if(resp.getIntValue("code") == 0){
				return true;
			}else{
				return false;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	/**
	 * 其他收费的四要素验证接口
	 * @param name
	 * @param idCard
	 * @param bankNo
	 * @param mobile
	 * @return
	 */
	private static Boolean checkByApi1(String name, String idCard, String bankNo, String mobile){
		return false;
	}
	
	/**
	 * get请求 阿里云市场的银行卡四要素接口
	 * 按文档要求，设置请求头，文档详见
	 * https://market.aliyun.com/products/57000002/cmapi014429.html?spm=5176.2020520132.101.61.wguKjg#sku=yuncode842900008
	 * @param url
	 * @return
	 */
	private static String get(String url, String appCode){
		String result = null;
		//创建HttpClientBuilder  
	    HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
	    //HttpClient  
	    CloseableHttpClient httpClient = httpClientBuilder.build();  
	    HttpGet get = new HttpGet(url);
		//设置的请求头

	    get.setHeader("Authorization","APPCODE "+appCode);
		// 执行GET请求
		try {
			HttpResponse response = httpClient.execute(get);
			//获取响应消息实体  
            HttpEntity entity = response.getEntity();  
            //响应状态  
            //System.out.println("status:" + response.getStatusLine());  
            //判断响应实体是否为空  
            if (entity != null) {  
                //System.out.println("contentEncoding:" + entity.getContentEncoding());  
            	if(response.getStatusLine().getStatusCode() == 200){
            		result = EntityUtils.toString(entity, "UTF-8");
            	}
            	else{
            		result = EntityUtils.toString(entity, "UTF-8");
            	}
                
            }
            //HttpUtils.closeClient(httpClient);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	    return result;
	}

}
