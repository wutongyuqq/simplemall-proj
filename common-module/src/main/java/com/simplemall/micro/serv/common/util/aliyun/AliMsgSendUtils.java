/*
package com.simplemall.micro.serv.common.util.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

*/
/**
 * 使用阿里云发送短信的工具类
 * 阿里云短信模板，文字内容无法发送，只能发送不超过6位的验证码内容
 * 备注:编码采用UTF-8
 * 国际短信发送请勿参照此接口
 * @author gbf
 * 
 * 依赖了2个jar包
 * 1:aliyun-java-sdk-core.jar
 * 2:aliyun-java-sdk-dysmsapi.jar
 *//*

public class AliMsgSendUtils {
	protected static Logger logger = LoggerFactory.getLogger(AliMsgSendUtils.class);

	//产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAInpfs1CGqSDU7";
    static final String accessKeySecret = "i00FKTpNc2jf4cuu1yPaWvsCHxpbUP";
    
    //短信头 签名，签名要到阿里云控制台审核
    static final String name = "考拉矿工";
    //阿里云短信模板 在阿里云控制台的短信服务模块  查看模板管理，找到相关场景的模板code
    public static final String YZM = "SMS_134316694";//验证码短信模板
	public static final String CZTZ = "SMS_134250191";//充值通知短信模板
	public static final String GJTZ = "SMS_135802203";//购机通知短信模板
	public static final String TBTZ = "SMS_135797227";//提币通知短信模板
	public static final String TXTZ = "SMS_135802162";//提现通知短信模板
	public static final String WJMBTZ = "SMS_135802162";//委托卖币通知短信模板(阿里还没通过)
	public static final String TJCGTZ = "SMS_135792371";//推荐成功通知短信模板
    //超时时间 
    static final String connectTimeout = "10000";
    static final String readTimeout = "10000";

    public static void main(String[] params){
//    	//发送验证码
//    	sendMsg(AliMsgSendUtils.YZM,"15871479786","032187");
//    	//发送充值成功的通知
//    	sendMsg(AliMsgSendUtils.CZTZ,"15871479786",null);
//
//    	//查询某个号码今天的发送历史记录
//    	getSendHistory("15871479786",new Date());
		sendMsg(AliMsgSendUtils.TJCGTZ,"13971601941","{\"phone\":\"" + "13971601940" + "\"}");
    }
    
	*/
/**
	 * 短信发送接口
	 * 参考文档：短信发送API(SendSms)---JAVA
	 * https://help.aliyun.com/document_detail/55284.html?spm=5176.10629532.106.1.614b1cbeE8vlhl
	 * @param changjing 场景，比如是验证码场景 AliMsgSendUtils.YZM 还是充值通知短信  AliMsgSendUtils.CZTZ 
	 * @param phoneNum 手机号
	 * @param code 验证码 （验证码模式下才需要）
	 * @return map 对象 error 0表示接口调用成功
	 *//*

	public static Map<String,String> sendMsg(String changjing, String phoneNum, String code){
		Map<String,String> map = new HashMap<String,String>();
		List<Dict> dictList = DictUtils.getDictList("KC_SMS_SWITCH");
		if(dictList!=null) {
			if (("0").equals(dictList.get(0).getValue())) {
				map.put("error", "0");
				map.put("msg", "短信开关已关闭");
				System.out.println("短信开关已关闭");
				return map;
			}
		}
		map.put("error", "1");
		if(StringUtils.isEmpty(changjing)){
			map.put("msg", "请输入调用场景");
        	return map;
        }
		if(StringUtils.isEmpty(phoneNum)){
			map.put("msg", "请输入手机号");
        	return map;
        }
		
		//可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", connectTimeout);
        System.setProperty("sun.net.client.defaultReadTimeout", readTimeout);
		try{
	        //初始化acsClient,暂不支持region化
	        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
	        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
	        IAcsClient acsClient = new DefaultAcsClient(profile);

	        //组装请求对象-具体描述见控制台-文档部分内容
	        SendSmsRequest request = new SendSmsRequest();
	        //必填:待发送手机号
	        request.setPhoneNumbers(phoneNum);
	        
	        //必填:短信签名-可在短信控制台中找到
	        request.setSignName(name);
	        
	        //必填:短信模板-可在短信控制台中找到
//	        if(changjing.equals(YZM)){//验证码
	        	request.setTemplateCode(changjing);
	        	//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		        //request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");
				if(changjing.equals(YZM)) {
					request.setTemplateParam("{\"code\":\"" + code + "\"}");
				}else{
					request.setTemplateParam(code);
				}
	        	
//	        }else{//充值通知
//	        	request.setTemplateCode(CZTZ);
//	        }
	        
	        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
	        //request.setOutId("yourOutId");
	        
	        //hint 此处可能会抛出异常，注意catch
	        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
//	        System.out.println("短信接口返回的数据----------------");
//	        System.out.println("Code=" + sendSmsResponse.getCode());
//	        System.out.println("Message=" + sendSmsResponse.getMessage());
//	        System.out.println("RequestId=" + sendSmsResponse.getRequestId());
//	        System.out.println("BizId=" + sendSmsResponse.getBizId());
	        
	        if(sendSmsResponse.getCode() == null){
	        	map.put("msg", "短信调用接口失败");
	        }
	        else if(sendSmsResponse.getCode().equals("OK")) {
	        	map.put("error", "0");
	        	map.put("msg", "请求成功");
	        	sendSmsResponse.getBizId();
	        }else if(sendSmsResponse.getCode().equals("isv.MOBILE_NUMBER_ILLEGAL")){
	        	map.put("msg", "非法手机号");
	        }else if(sendSmsResponse.getCode().equals("isv.AMOUNT_NOT_ENOUGH")){
	        	map.put("msg", "账户余额不足");
	        }else if(sendSmsResponse.getCode().equals("isv.BLACK_KEY_CONTROL_LIMIT")){
	        	map.put("msg", "黑名单管控");
	        }else if(sendSmsResponse.getCode().equals("isv.MOBILE_COUNT_OVER_LIMIT")){
	        	map.put("msg", "手机号码数量超过限制");
	        }else if(sendSmsResponse.getCode().equals("isv.OUT_OF_SERVICE")){
	        	map.put("msg", "业务停机");
	        }else{
	        	if(logger.isWarnEnabled()){
	        		logger.warn("阿里云短信发送失败："+sendSmsResponse.getMessage());
	        	}
	        	System.out.println("Message=" + sendSmsResponse.getMessage());
	        	map.put("msg", "短信接口调用失败："+sendSmsResponse.getMessage());
	        }
		}catch(Exception e){
			map.put("msg", "发生异常，短信调用接口失败");
		}
		return map;

	}
	
	*/
/**
	 * 查询每个号码的短信发送的10条历史记录  支持30天内记录查询
	 * @param phoneNum
	 * @param date
	 * @return 历史记录集合，每条记录是map类型，PhoneNum表示手机号 SendDate表示发送时间 ReceiveDate表示接收时间  SendStatus表示发送状态  Content表示短信内容
	 *//*

	public static List<Map<String,Object>> getSendHistory(String phoneNum, Date date){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if(StringUtils.isEmpty(phoneNum)){
			return list;
		}
		if(date == null || date.after(new Date())){
			date = new Date();
		}
		try {
			 QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(phoneNum,date,null);
//			 System.out.println("短信明细查询接口返回数据----------------");
//	         System.out.println("Code=" + querySendDetailsResponse.getCode());
//	         System.out.println("Message=" + querySendDetailsResponse.getMessage());
	         
			for (QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs()) {
//				System.out.println("Content=" + smsSendDetailDTO.getContent());//短信内容
//				System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());//错误码
//				System.out.println("OutId=" + smsSendDetailDTO.getOutId());//我方提供的流水号
//				System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());//手机号
//				System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());//接收时间
//				System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());//发送时间
//				System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());//发送状态
//				System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());//短信模板编号
				
				Map<String,Object > map = new HashMap<String,Object >();
				map.put("PhoneNum", smsSendDetailDTO.getPhoneNum());
				map.put("SendDate", smsSendDetailDTO.getSendDate());
				map.put("ReceiveDate", smsSendDetailDTO.getReceiveDate());
				map.put("SendStatus", smsSendDetailDTO.getSendStatus());
				map.put("Content", smsSendDetailDTO.getContent());
				list.add(map);
			}
//			System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());//总条数
//			System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());//requestId
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return list;
	}
	
	*/
/**
	 * 查询发送的10条历史记录  支持30天内记录查询
	 * 参考阿里云sdk demo中的 SmsDemo.java中实例代码
	 * @param phoneNum 手机号
	 * @param date 发送日期
	 * @param bizId 业务流水号，是在短信发送接口中，若发送方设置了request.setOutId("yourOutId")
	 * @return
	 * @throws ClientException
	 *//*

    private static QuerySendDetailsResponse querySendDetails(String phoneNum, Date date, String bizId) throws ClientException {
    	

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", connectTimeout);
        System.setProperty("sun.net.client.defaultReadTimeout", readTimeout);

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber(phoneNum);
        if(StringUtils.isNotEmpty(bizId)){
        	//可选-流水号
            request.setBizId(bizId);
        }
        
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(date));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }
}
*/
