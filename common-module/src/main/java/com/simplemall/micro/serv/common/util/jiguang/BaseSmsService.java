package com.simplemall.micro.serv.common.util.jiguang;

import cn.jsms.api.common.model.RecipientPayload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Random;

@Transactional
@Service
public abstract class BaseSmsService {

    /**
     * 有效期10分钟(毫秒数)
     */
    private final static int VALID_TIME_1 = 600000;

    /**
     * 有效期10分钟
     */
    private final static String VALID_TIME_2 = "10";

    /**
     * 发送时间限制2分钟一次
     */
    private final static int LIMIT_TIME = 120000;

    /**
     * 校验时间
     */
    private final static int LIMIT_TIME_1 = 60000;

    /**
     *
     */
    private final static String MOBILE_PATTERN = "^((17[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

//    @Autowired
//    private SmsTemplateMapper smsTemplateMapper;


    /**
     * 发送短信的方法
     *
     * @param mobile
     * @param templateId
     * @param temp_para
     * @return
     */
    abstract Map<String, Object> sendSms(String mobile, String templateId, Map<String, String> temp_para);
    /**
     * 发送短信的方法
     *
     * @param mobile
     * @param templateId
     * @param temp_para
     * @return
     */
    abstract Map<String, Object> sendSmsTest(String mobile, String templateId, Map<String, String> temp_para);
    /**
     * 发送验证码的方法
     *
     * @param mobile
     * @param templateId
     * @param temp_para
     * @return
     */
    abstract Map<String, Object> sendSMSCode(String mobile, String templateId, Map<String, String> temp_para);
    /**
     * 发送验证码的方法Test
     *
     * @param mobile
     * @param templateId
     * @param temp_para
     * @return
     */
    abstract Map<String, Object> sendSMSCodeTest(String mobile, String templateId, Map<String, String> temp_para);
    /**
     * 发送批量短信的方法
     *
     * @param templateId
     * @param recipients
     * @return
     */
    abstract Map<String, Object> sendBatchTemplateSMS(String templateId, RecipientPayload[] recipients);
    /**
     * 发送批量短信的方法Test
     *
     * @param templateId
     * @param recipients
     * @return
     */
    abstract Map<String, Object> sendBatchTemplateSMSTest(String templateId, RecipientPayload[] recipients);
    /**
     * @param mobile
     * @throws UnknownHostException
     * @throws ParseException
     * @throws Exception            做了一些验证，后续有需要加上去
     */
    public Map<String, Object> sendMessage(String mobile, String templateId, Map<String, String> temp_para)
            throws UnknownHostException, ParseException {
        /*
        Map<String, Object> result = new HashMap<String, Object>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        Date limit = new Date(now.getTime() - LIMIT_TIME_1);
        // 安全限制
      if (smsMapper.isSafe(simpleDateFormat.format(limit), simpleDateFormat.format(now)) > Integer.valueOf(ConfigUtils.get("SMS_COUNT"))) {
		//if (smsMapper.isSafe("2016-11-23 17:11:00","2016-11-23 17:12:00")>Integer.valueOf(ConfigUtils.get(ConfigContants.SMS_COUNT))) {
            // 关闭系统短信开关
            configMapper.updateValueByKey("false", ConfigContants.SMS_ALLOWED);
            // 更新系统缓存
            CacheUtils.put(ConfigUtils.getCacheKey(ConfigContants.SMS_ALLOWED), "false");
            String[] messages1 = new String[2];
            messages1[0] = "911";
            messages1[1] = "911";
            sendSms(ConfigUtils.get(ConfigContants.SMS_SAFE_MOBILE), templateId, messages1);
            result.put("resultCode", ResultCodeInfo.E999999);
            result.put("errorMsg", ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E999999));
            return result;
        }*/

        // 系统短信开关
        // String flag = ConfigUtils.get(ConfigContants.SMS_ALLOWED);

        // 验证模板ID是否存在
/*
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("templateId", templateId);
        List<SmsTemplateVO> listSmsTemplate = smsTemplateMapper.list(condition);

        if (listSmsTemplate.size() == 0) {
            result.put("resultCode", ResultCodeInfo.E100010);
            result.put("errorMsg", ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E100010));
            return result;
        }
*/
        return sendSms(mobile, templateId, temp_para);
    }
    /**
     * @param mobile
     * @throws UnknownHostException
     * @throws ParseException
     * @throws Exception            做了一些验证，后续有需要加上去
     */
    public Map<String, Object> sendMessageTest(String mobile, String templateId, Map<String, String> temp_para)
            throws UnknownHostException, ParseException {
        return sendSmsTest(mobile, templateId, temp_para);
    }
    /**
     * 获取验证码
     *
     * @return
     */
    public static String randomFor6() {
        Random random = new Random();
        String result = "";
        for (int i = 0; i < 6; i++) {
            result += random.nextInt(10);
        }
        return result;
    }

    /**
     * 比较两个时间的大小
     *
     * @param d1
     * @param d2
     * @return
     */
    public boolean compareDate(Date d1, Date d2) {
        if (d1.getTime() > d2.getTime()) {
            return true;
        } else {
            return false;
        }
    }

}
