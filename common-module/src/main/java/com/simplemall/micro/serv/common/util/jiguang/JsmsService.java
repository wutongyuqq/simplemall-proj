package com.simplemall.micro.serv.common.util.jiguang;

import cn.jiguang.common.resp.ResponseWrapper;
import cn.jsms.api.SendSMSResult;
import cn.jsms.api.ValidSMSResult;
import cn.jsms.api.account.AppBalanceResult;
import cn.jsms.api.common.SMSClient;
import cn.jsms.api.common.model.BatchSMSPayload;
import cn.jsms.api.common.model.BatchSMSResult;
import cn.jsms.api.common.model.RecipientPayload;
import cn.jsms.api.common.model.SMSPayload;
import cn.jsms.api.template.SendTempSMSResult;
import cn.jsms.api.template.TemplatePayload;
import com.simplemall.micro.serv.common.service.JedisUtil;
import com.sun.javaws.CacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: fanyoucai
 * @Date: 2018/5/25.
 */
public final class JsmsService{

    private static Logger LOG = LoggerFactory.getLogger(JsmsService.class);
    // TODO 此处需要替换成开发者自己的AK(在极光访问控制台寻找)
    static final String ACCESSS_KEY_ID = "2ee6e2b7948ea7db90030768";
    static final String ACCESS_KEY_SECRET = "ccddd7f8e5343916b3cab6e5";

    //发送验证码短信
    public static Map<String, Object>  sendSMSCode(String mobile, String templateId, Map<String, String> temp_para) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
 /*       List<Dict> dictList = DictUtils.getDictList("KC_SMS_SWITCH");
        if(dictList!=null) {
            if (("0").equals(dictList.get(0).getValue())) {
                resultMap.put("error", "0");
                resultMap.put("resultCode", 200);
                resultMap.put("msg", "短信开关已关闭");
                System.out.println("短信开关已关闭");
                return resultMap;
            }
        }*/
        if (temp_para == null) {
            temp_para = new HashMap<String, String>();
        }
        SMSClient smsClient = new SMSClient(ACCESS_KEY_SECRET, ACCESSS_KEY_ID);
        try {
            SMSPayload smsPayload = SMSPayload.newBuilder()
                    .setMobileNumber(mobile)
                    .setTempId(Integer.valueOf(templateId))
                    .setTempPara(temp_para)
                    .build();
            //发送验证码短信
            SendSMSResult res = smsClient.sendSMSCode(smsPayload);
            if (res == null) {
                resultMap.put("msg", "短信调用接口失败");
            }
            //res.getResponseCode() == 200 成功
            else if (res.getResponseCode() == 200) {
                resultMap.put("error", "0");
                resultMap.put("resultCode", 200);
                resultMap.put("messageId", res.getMessageId());
                LOG.info(resultMap.toString());
                //缓存获取验证码id
                String msgId = JedisUtil.STRINGS.set(mobile,res.getMessageId());
            } else if (res.getResponseCode() == 50004) {
                resultMap.put("msg", "手机号码为空");
            } else if (res.getResponseCode() == 50014) {
                resultMap.put("msg", "可发短信余量不足");
            } else if (res.getResponseCode() == 50036) {
                resultMap.put("msg", "应用被列为黑名单");
            } else if (res.getResponseCode() == 50031) {
                resultMap.put("msg", "recipients 短信接收者数量超过1000");
            } else if (res.getResponseCode() == 50037) {
                resultMap.put("msg", "短信内容存在敏感词汇");
            } else if (res.getResponseCode() == 50022) {
                resultMap.put("msg", "模板中参数未全部替换");
            } else if (res.getResponseCode() == 50034) {
                resultMap.put("msg", "重复发送");
            } else if (res.getResponseCode() == 50009) {
                resultMap.put("msg", "发送超频");
            } else {
                if (LOG.isWarnEnabled()) {
                    LOG.warn("极光短信发送失败：" + res.getOriginalContent());
                }
                System.out.println("Message=" + res.getOriginalContent());
                resultMap.put("resultCode", res.getResponseCode());
                resultMap.put("msg", "短信接口调用失败：" + res.getOriginalContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
            resultMap.put("errorMsg", "发生异常，短信调用接口失败");
        }
        return resultMap;
    }
    //验证验证码
    public static Map<String, Object>  sendValidSMSCode(String mobile,String code) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //缓存获取验证码id
        String msgId = JedisUtil.STRINGS.get(mobile);
        SMSClient smsClient = new SMSClient(ACCESS_KEY_SECRET, ACCESSS_KEY_ID);
        try {
            //验证验证码
            ValidSMSResult res = smsClient.sendValidSMSCode(msgId,code);
            if (res == null) {
                resultMap.put("msg", "短信验证码验证失败");
            }
            //res.getResponseCode() == 200 成功
            else if (res.getResponseCode() == 200) {
                resultMap.put("error", "0");
                resultMap.put("resultCode", 200);
                resultMap.put("matched", res.getIsValid());
                LOG.info(resultMap.toString());
            } else if (res.getResponseCode() == 50004) {
                resultMap.put("msg", "手机号码为空");
            } else if (res.getResponseCode() == 50014) {
                resultMap.put("msg", "可发短信余量不足");
            } else if (res.getResponseCode() == 50036) {
                resultMap.put("msg", "应用被列为黑名单");
            } else if (res.getResponseCode() == 50031) {
                resultMap.put("msg", "recipients 短信接收者数量超过1000");
            } else if (res.getResponseCode() == 50037) {
                resultMap.put("msg", "短信内容存在敏感词汇");
            } else if (res.getResponseCode() == 50022) {
                resultMap.put("msg", "模板中参数未全部替换");
            } else if (res.getResponseCode() == 50034) {
                resultMap.put("msg", "重复发送");
            } else if (res.getResponseCode() == 50009) {
                resultMap.put("msg", "发送超频");
            } else {
                if (LOG.isWarnEnabled()) {
                    LOG.warn("极光短信发送失败：" + res.getOriginalContent());
                }
                System.out.println("Message=" + res.getOriginalContent());
                resultMap.put("resultCode", res.getResponseCode());
                resultMap.put("msg", "短信接口调用失败：" + res.getOriginalContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
            resultMap.put("errorMsg", "发生异常，短信调用接口失败");
        }
        return resultMap;
    }
    //发送验证码短信Test
    public static  Map<String, Object> sendSMSCodeTest(String mobile, String templateId, Map<String, String> temp_para) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (temp_para == null) {
            temp_para = new HashMap<String, String>();
        }
        SMSClient smsClient = new SMSClient(ACCESS_KEY_SECRET, ACCESSS_KEY_ID);
        try {
            SMSPayload smsPayload = SMSPayload.newBuilder()
                    .setMobileNumber(mobile)
                    .setTempId(Integer.valueOf(templateId))
                    .setTempPara(temp_para)
                    .build();
            //发送验证码短信
            SendSMSResult res = smsClient.sendSMSCode(smsPayload);
            if (res == null) {
                resultMap.put("msg", "短信调用接口失败");
            }
            //res.getResponseCode() == 200 成功
            else if (res.getResponseCode() == 200) {
                resultMap.put("error", "0");
                resultMap.put("resultCode", 200);
                resultMap.put("messageId", res.getMessageId());
                LOG.info(resultMap.toString());
            } else if (res.getResponseCode() == 50004) {
                resultMap.put("msg", "手机号码为空");
            } else if (res.getResponseCode() == 50014) {
                resultMap.put("msg", "可发短信余量不足");
            } else if (res.getResponseCode() == 50036) {
                resultMap.put("msg", "应用被列为黑名单");
            } else if (res.getResponseCode() == 50031) {
                resultMap.put("msg", "recipients 短信接收者数量超过1000");
            } else if (res.getResponseCode() == 50037) {
                resultMap.put("msg", "短信内容存在敏感词汇");
            } else if (res.getResponseCode() == 50022) {
                resultMap.put("msg", "模板中参数未全部替换");
            } else if (res.getResponseCode() == 50034) {
                resultMap.put("msg", "重复发送");
            } else if (res.getResponseCode() == 50009) {
                resultMap.put("msg", "发送超频");
            } else {
                if (LOG.isWarnEnabled()) {
                    LOG.warn("极光短信发送失败：" + res.getOriginalContent());
                }
                System.out.println("Message=" + res.getOriginalContent());
                resultMap.put("resultCode", res.getResponseCode());
                resultMap.put("msg", "短信接口调用失败：" + res.getOriginalContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
            resultMap.put("errorMsg", "发生异常，短信调用接口失败");
        }
        return resultMap;
    }

    //发送短信
    public static Map<String, Object> sendSms(String mobile, String templateId, Map<String, String> temp_para) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
/*        List<Dict> dictList = DictUtils.getDictList("KC_SMS_SWITCH");
        if(dictList!=null) {
            if (("0").equals(dictList.get(0).getValue())) {
                resultMap.put("error", "0");
                resultMap.put("resultCode", 200);
                resultMap.put("msg", "短信开关已关闭");
                System.out.println("短信开关已关闭");
                return resultMap;
            }
        }*/
        if (temp_para == null) {
            temp_para = new HashMap<String, String>();
        }
        SMSClient smsClient = new SMSClient(ACCESS_KEY_SECRET, ACCESSS_KEY_ID);
        try {
            SMSPayload smsPayload = SMSPayload.newBuilder()
                    .setMobileNumber(mobile)
                    .setTempId(Integer.valueOf(templateId))
                    .setTempPara(temp_para)
                    .build();
            //发送模板短信
            SendSMSResult res = smsClient.sendTemplateSMS(smsPayload);
            if (res == null) {
                resultMap.put("msg", "短信调用接口失败");
            }
            //res.getResponseCode() == 200 成功
            else if (res.getResponseCode() == 200) {
                resultMap.put("resultCode", 200);
                resultMap.put("messageId", res.getMessageId());
                resultMap.put("error", "0");
                resultMap.put("msg", "请求成功");
                LOG.info(resultMap.toString());
            } else if (res.getResponseCode() == 50004) {
                resultMap.put("msg", "手机号码为空");
            } else if (res.getResponseCode() == 50014) {
                resultMap.put("msg", "可发短信余量不足");
            } else if (res.getResponseCode() == 50036) {
                resultMap.put("msg", "应用被列为黑名单");
            } else if (res.getResponseCode() == 50031) {
                resultMap.put("msg", "recipients 短信接收者数量超过1000");
            } else if (res.getResponseCode() == 50037) {
                resultMap.put("msg", "短信内容存在敏感词汇");
            } else if (res.getResponseCode() == 50022) {
                resultMap.put("msg", "模板中参数未全部替换");
            } else if (res.getResponseCode() == 50034) {
                resultMap.put("msg", "重复发送");
            } else if (res.getResponseCode() == 50009) {
                resultMap.put("msg", "发送超频");
            } else {
                if (LOG.isWarnEnabled()) {
                    LOG.warn("极光短信发送失败：" + res.getOriginalContent());
                }
                System.out.println("Message=" + res.getOriginalContent());
                resultMap.put("resultCode", res.getResponseCode());
                resultMap.put("msg", "短信接口调用失败：" + res.getOriginalContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
            resultMap.put("errorMsg", "发生异常，短信调用接口失败");
        }
        return resultMap;
    }
    //发送短信 sendSmsTest
    public static Map<String, Object> sendSmsTest(String mobile, String templateId, Map<String, String> temp_para) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (temp_para == null) {
            temp_para = new HashMap<String, String>();
        }
        SMSClient smsClient = new SMSClient(ACCESS_KEY_SECRET, ACCESSS_KEY_ID);
        try {
            SMSPayload smsPayload = SMSPayload.newBuilder()
                    .setMobileNumber(mobile)
                    .setTempId(Integer.valueOf(templateId))
                    .setTempPara(temp_para)
                    .build();
            //发送模板短信
            SendSMSResult res = smsClient.sendTemplateSMS(smsPayload);
            if (res == null) {
                resultMap.put("msg", "短信调用接口失败");
            }
            //res.getResponseCode() == 200 成功
            else if (res.getResponseCode() == 200) {
                resultMap.put("resultCode", 200);
                resultMap.put("messageId", res.getMessageId());
                resultMap.put("error", "0");
                resultMap.put("msg", "请求成功");
                LOG.info(resultMap.toString());
            } else if (res.getResponseCode() == 50004) {
                resultMap.put("msg", "手机号码为空");
            } else if (res.getResponseCode() == 50014) {
                resultMap.put("msg", "可发短信余量不足");
            } else if (res.getResponseCode() == 50036) {
                resultMap.put("msg", "应用被列为黑名单");
            } else if (res.getResponseCode() == 50031) {
                resultMap.put("msg", "recipients 短信接收者数量超过1000");
            } else if (res.getResponseCode() == 50037) {
                resultMap.put("msg", "短信内容存在敏感词汇");
            } else if (res.getResponseCode() == 50022) {
                resultMap.put("msg", "模板中参数未全部替换");
            } else if (res.getResponseCode() == 50034) {
                resultMap.put("msg", "重复发送");
            } else if (res.getResponseCode() == 50009) {
                resultMap.put("msg", "发送超频");
            } else {
                if (LOG.isWarnEnabled()) {
                    LOG.warn("极光短信发送失败：" + res.getOriginalContent());
                }
                System.out.println("Message=" + res.getOriginalContent());
                resultMap.put("resultCode", res.getResponseCode());
                resultMap.put("msg", "短信接口调用失败：" + res.getOriginalContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
            resultMap.put("errorMsg", "发生异常，短信调用接口失败");
        }
        return resultMap;
    }
    //批量发送模板短信
    public static Map<String, Object> sendBatchTemplateSMS(String templateId, RecipientPayload[] recipients) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
/*        List<Dict> dictList = DictUtils.getDictList("KC_SMS_SWITCH");
        if(dictList!=null) {
            if (("0").equals(dictList.get(0).getValue())) {
                resultMap.put("error", "0");
                resultMap.put("resultCode", 200);
                resultMap.put("msg", "短信开关已关闭");
                System.out.println("短信开关已关闭");
                return resultMap;
            }
        }*/
        if (recipients == null) {
            recipients = new RecipientPayload[0];
        }
        SMSClient smsClient = new SMSClient(ACCESS_KEY_SECRET, ACCESSS_KEY_ID);
        try {
            BatchSMSPayload payload = BatchSMSPayload.newBuilder()
                    .setTempId(Integer.valueOf(templateId))
                    .setRecipients(recipients)
                    .build();
            //批量发送模板短信
            BatchSMSResult res = smsClient.sendBatchTemplateSMS(payload);
            if (res == null) {
                resultMap.put("msg", "短信调用接口失败");
            }
            //res.getResponseCode() == 200 成功
            else if (res.getResponseCode() == 200&& res.getSuccessCount()!=0) {
                resultMap.put("resultCode", 200);
                resultMap.put("error", "0");
                resultMap.put("successCount", res.getSuccessCount());
                resultMap.put("failureCount", res.getFailureCount());
                resultMap.put("failureRecipients", res.getFailureRecipients());
                LOG.info("info" + resultMap);
            } else if (res.getResponseCode() == 50004) {
                resultMap.put("msg", "手机号码为空");
            } else if (res.getResponseCode() == 50014) {
                resultMap.put("msg", "可发短信余量不足");
            } else if (res.getResponseCode() == 50036) {
                resultMap.put("msg", "应用被列为黑名单");
            } else if (res.getResponseCode() == 50031) {
                resultMap.put("msg", "recipients 短信接收者数量超过1000");
            } else if (res.getResponseCode() == 50037) {
                resultMap.put("msg", "短信内容存在敏感词汇");
            } else if (res.getResponseCode() == 50022) {
                resultMap.put("msg", "模板中参数未全部替换");
            } else if (res.getResponseCode() == 50034) {
                resultMap.put("msg", "重复发送");
            } else if (res.getResponseCode() == 50009) {
                resultMap.put("msg", "发送超频");
            } else {
                if (LOG.isWarnEnabled()) {
                    LOG.warn("极光短信发送失败：" + res.getOriginalContent());
                }
                System.out.println("Message=" + res.getOriginalContent());
                resultMap.put("resultCode", res.getResponseCode());
                resultMap.put("error", 1);
                resultMap.put("msg", "短信接口调用失败：" + res.getOriginalContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
            resultMap.put("errorMsg", "发生异常，短信调用接口失败");
        }
        return resultMap;
    }
    //批量发送模板短信Test
    public static  Map<String, Object> sendBatchTemplateSMSTest(String templateId, RecipientPayload[] recipients) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (recipients == null) {
            recipients = new RecipientPayload[0];
        }
        SMSClient smsClient = new SMSClient(ACCESS_KEY_SECRET, ACCESSS_KEY_ID);
        try {
            BatchSMSPayload payload = BatchSMSPayload.newBuilder()
                    .setTempId(Integer.valueOf(templateId))
                    .setRecipients(recipients)
                    .build();
            //批量发送模板短信
            BatchSMSResult res = smsClient.sendBatchTemplateSMS(payload);
            if (res == null) {
                resultMap.put("msg", "短信调用接口失败");
            }
            //res.getResponseCode() == 200 成功
            else if (res.getResponseCode() == 200&& res.getSuccessCount()!=0) {
                resultMap.put("resultCode", 200);
                resultMap.put("error", "0");
                resultMap.put("successCount", res.getSuccessCount());
                resultMap.put("failureCount", res.getFailureCount());
                resultMap.put("failureRecipients", res.getFailureRecipients());
                LOG.info("info" + resultMap);
            } else if (res.getResponseCode() == 50004) {
                resultMap.put("msg", "手机号码为空");
            } else if (res.getResponseCode() == 50014) {
                resultMap.put("msg", "可发短信余量不足");
            } else if (res.getResponseCode() == 50036) {
                resultMap.put("msg", "应用被列为黑名单");
            } else if (res.getResponseCode() == 50031) {
                resultMap.put("msg", "recipients 短信接收者数量超过1000");
            } else if (res.getResponseCode() == 50037) {
                resultMap.put("msg", "短信内容存在敏感词汇");
            } else if (res.getResponseCode() == 50022) {
                resultMap.put("msg", "模板中参数未全部替换");
            } else if (res.getResponseCode() == 50034) {
                resultMap.put("msg", "重复发送");
            } else if (res.getResponseCode() == 50009) {
                resultMap.put("msg", "发送超频");
            } else {
                if (LOG.isWarnEnabled()) {
                    LOG.warn("极光短信发送失败：" + res.getOriginalContent());
                }
                System.out.println("Message=" + res.getOriginalContent());
                resultMap.put("resultCode", res.getResponseCode());
                resultMap.put("error", 1);
                resultMap.put("msg", "短信接口调用失败：" + res.getOriginalContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
            resultMap.put("errorMsg", "发生异常，短信调用接口失败");
        }
        return resultMap;
    }

    //创建模版
    public static  Map<String, Object> createTemplate(int temp_id, String template, int type, int ttl, String remark) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        SMSClient smsClient = new SMSClient(ACCESS_KEY_SECRET, ACCESSS_KEY_ID);
        try {
            TemplatePayload payload = TemplatePayload.newBuilder()
                    .setTemplate("感谢您对考拉矿工的支持,您胡验证码是{{code}}，2分钟内有效！")
                    .setType(1)
                    .setTTL(120)
                    .build();
            //创建模板短信,需要先签名
            SendTempSMSResult res = smsClient.createTemplate(payload);
//          res.getResponseCode() == 200 成功
            resultMap.put("error", "0");
            resultMap.put("resultCode", 200);
            resultMap.put("messageId", res.getTempId());
            LOG.info(resultMap.toString());
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
            resultMap.put("errorMsg", "发生异常，短信模版创建失败");
        }
        return resultMap;

    }

    //修改模版
    public static  Map<String, Object> updateTemplate(int temp_id, String template, int type, int ttl, String remark) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        SMSClient smsClient = new SMSClient(ACCESS_KEY_SECRET, ACCESSS_KEY_ID);
        try {
            TemplatePayload payload = TemplatePayload.newBuilder()
                    .setTemplate("感谢您对考拉矿工的支持,2分钟内有效！")
                    .setType(1)
                    .setTTL(120)
                    .build();
            //修改模板短信
            SendTempSMSResult res = smsClient.updateTemplate(payload, temp_id);
//          res.getResponseCode() == 200 成功
            resultMap.put("error", "0");
            resultMap.put("resultCode", 200);
            resultMap.put("messageId", res.getTempId());
            LOG.info(resultMap.toString());
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
            resultMap.put("errorMsg", "发生异常，短信模版修改失败");
        }
        return resultMap;
    }

    //删除模版
    public static Map<String, Object> updateTemplate(int temp_id) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        SMSClient smsClient = new SMSClient(ACCESS_KEY_SECRET, ACCESSS_KEY_ID);
        try {
            //删除模版
            ResponseWrapper res = smsClient.deleteTemplate(temp_id);
            LOG.info(res.toString());
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
            resultMap.put("errorMsg", "发生异常，短信模版删除失败");
        }
        return resultMap;
    }

    //应用余量查询 API
    public static  Map<String, Object> getSMSBalance() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        SMSClient smsClient = new SMSClient(ACCESS_KEY_SECRET, ACCESSS_KEY_ID);
        try {
            //应用余量查询
            AppBalanceResult res = smsClient.getAppSMSBalance();
            resultMap.put("error", "0");
            resultMap.put("resultCode", 200);
            resultMap.put("devBalance", res.getAppBalance());
            LOG.info(res.toString());
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
            resultMap.put("errorMsg", "发生异常，短信模版删除失败");
        }
        return resultMap;
    }

    public static void main(String[] args) {
        //SmsUtils.sendSMS("13971601941","A",null);
        JsmsService j = new JsmsService();
   /*     Map<String, String> temp_para = new HashMap<String, String>();
        temp_para.put("code", "139716");
        //j.sendSms("13971601941",JsmsUtils.TJCGTZ,temp_para);
        j.sendSms("13971601941", JsmsUtils.YZM, temp_para);*/
        //j.createTemplate(2,"感谢您对考拉矿工的支持,2分钟内有效！",1,120,null);
        //j.getSMSBalance();
//        List<String> m = new ArrayList<String>();
//        SmsUtils.sendSMS("13971601941","1",m);
        System.out.println(JsmsService.sendValidSMSCode("13971601941","931011"));
    }
}
