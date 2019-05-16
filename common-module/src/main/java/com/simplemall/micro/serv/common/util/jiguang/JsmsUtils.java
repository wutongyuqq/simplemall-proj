package com.simplemall.micro.serv.common.util.jiguang;

import cn.jsms.api.common.model.RecipientPayload;
import com.simplemall.micro.serv.common.util.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Map;
import java.util.Random;

/**
 * 编码工具类
 *
 * @author fanyoucai
 */
public final class JsmsUtils {
    private JsmsUtils() {
        throw new IllegalStateException("Utility class");
    }
    private static Logger logger = LoggerFactory.getLogger(JsmsUtils.class);
    private static BaseSmsService baseSmsService;
    //短信头 签名，签名要到极光控制台审核
    static final String NAME = "考拉矿工";
    //极光短信模板 在极光控制台的短信服务模块  查看模板管理，找到相关场景的模板code
     //public static final String YZM = "151183";//验证码短信模板   code
     public static final String YZM = "1";//验证码短信模板   code
     public static final String CZTZ = "151987";//充值通知短信模板 充值（会计完成充值）
     public static final String GJTZ = "151986";//购机通知短信模板 购机（用户下单成功）
     static final String TBTZ = "150853";//提币通知短信模板 提币（出纳点击确认）
     static final String TXTZ = "150852";//提现通知短信模板 提现（出纳点击确认）
     static final String TJCGTZ = "150885";//推荐成功顺利购机通知短信模板 推荐（发给推荐人）
     public static final String WTMBTZ = "151985";//委托卖币通知短信模板
    //自定义短信
     static final String CZYHTX = "150964";//充值用户提醒（发给已经充值的用户提醒他们抢购）
     public static final String SCSY = "150856";//首次收益（按每批矿机的订单，在收益日+1日的中午13：00发送，仅一次）
     public static final String DFJN = "151864";//电费缴纳提醒 （在缴费到期的时间上提前3天发送，如果没缴费，三天每天发送）
     public static final String SQJDF = "152908";//尊敬的用户，您申请的授权缴纳电费明细如下：{{detail}}共计 {{money}}元。短信授权码： {{dfcode}}授权成功后，系统将自动从您考拉账户中扣除此金额缴纳电费托管费。
     public static final String SQJDFCG= "152909";//尊敬的用户，您好！您于{{date}} 申请的代缴电费扣款成功，查看缴纳详情：https://www.51btceth.com/
    public static final String JDFCGMX= "152935";// 缴电费成功明细 (尊敬的用户，您已缴纳电费明细如下：{{detail}} 共计 {{money}}元。)
    public static final String SRZF= "155442";// 给用户发送生日祝福
    public static final String XYDX= "156393";// 给用户发送续约短信
    public static final String BJYJ= "157084";// 币价预警
    private static BaseSmsService getBaseSmsService() {
        if (null == baseSmsService) {
            baseSmsService = SpringContextHolder.getBean(BaseSmsService.class);
        }
        return baseSmsService;
    }
    /**
     * 产生一个验证码，只包含验证码本身，不包含数字。用于阿里云短信接口
     * 2018-5-9
     * @return
     */
    public static String getCode(){
        Random rand = new Random();
        int num =0;
        while (num<=1000){
            num =rand.nextInt(1000000);
        }
        String code =String.valueOf(num);
        while(code.length()<6){
            code ="0"+code;
        }
        //this.validCode = code;
        return code;
    }
    /**
     * 发送信息
     *
     * @param mobile
     * @param templateId
     * @param temp_para
     * @return
     * @throws ParseException
     * @throws UnknownHostException
     */
    public static Map<String, Object> sendSMS(String mobile, String templateId, Map<String, String> temp_para) {

        try {
            return getBaseSmsService().sendMessage(mobile, templateId, temp_para);
        } catch (UnknownHostException e) {
            logger.info(e.getMessage());
            return null;
        } catch (ParseException e) {
            logger.info(e.getMessage());
            return null;
        }

    }
    
    /**
     * 为了避免在某些情况下测试服务器要测试短信发送功能，但是又不要给客户发送其他短信，
     * 则使用该通道避过短信控制开关，直接发短信，而其他短信还是用短信开关来控制
     * @param mobile
     * @param templateId
     * @param temp_para
     * @return
     */
    public static Map<String, Object> sendSMSTest(String mobile, String templateId, Map<String, String> temp_para) {

        try {
            return getBaseSmsService().sendMessageTest(mobile, templateId, temp_para);
        } catch (UnknownHostException e) {
            logger.info(e.getMessage());
            return null;
        } catch (ParseException e) {
            logger.info(e.getMessage());
            return null;
        }

    }
    /**
     * 发送验证码
     *
     * @param mobile
     * @param templateId
     * @param temp_para
     * @return
     * @throws ParseException
     * @throws UnknownHostException
     */
    public static Map<String, Object> sendSMSCode(String mobile, String templateId, Map<String, String> temp_para) {

        try {
            return getBaseSmsService().sendSMSCode(mobile, templateId, temp_para);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return null;
        }

    }
    public static Map<String, Object> sendSMSCodeTest(String mobile, String templateId, Map<String, String> temp_para) {

        try {
            return getBaseSmsService().sendSMSCodeTest(mobile, templateId, temp_para);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return null;
        }

    }
    /**
     * 发送批量信息
     *
     * @param templateId
     * @param recipients
     * @return
     * @throws ParseException
     * @throws UnknownHostException
     */
    public static Map<String, Object> sendBatchTemplateSMS(String templateId, RecipientPayload[] recipients) {
            return getBaseSmsService().sendBatchTemplateSMS(templateId,recipients);
    }
    /**
     * 发送批量信息
     *
     * @param templateId
     * @param recipients
     * @return
     * @throws ParseException
     * @throws UnknownHostException
     */
    public static Map<String, Object> sendBatchTemplateSMSTest(String templateId, RecipientPayload[] recipients) {
        return getBaseSmsService().sendBatchTemplateSMSTest(templateId,recipients);
    }
}
