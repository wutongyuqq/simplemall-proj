package com.simplemall.micro.serv.common.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.afs.model.v20180112.AuthenticateSigRequest;
import com.aliyuncs.afs.model.v20180112.AuthenticateSigResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by fanyoucai on 2018/6/2.
 */
public class SlidercaptchaValidateUtil {
    static IAcsClient client = null;
    /**
     * 阿里云滑块验证
     * @param isMobile true 表示 进行手机模式 h5页面的滑块后台验证   false 表示进行普通pc模式页面的滑块后台验证
     */
    public static Boolean captchaAliyunValidate(HttpServletRequest request, boolean isMobile) {
        //YOUR ACCESS_KEY、YOUR ACCESS_SECRET请替换成您的阿里云accesskey id和secret
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", AliyunConfig.Access_Key_ID(), AliyunConfig.Access_Key_Secret());
        client = new DefaultAcsClient(profile);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "afs", "afs.aliyuncs.com");
            String sig = request.getParameter(AliyunConfig.sig);
            String token = request.getParameter(AliyunConfig.token);
            String csessionid = request.getParameter(AliyunConfig.csessionid);
            String scene = request.getParameter(AliyunConfig.scene);
            String appkey = null;
            if(isMobile){
                appkey = AliyunConfig.appkeyMobile();
            }else{
                appkey = AliyunConfig.appkeyPc();
            }
            String remoteIp = AliyunConfig.getIpAddress(request);
            AuthenticateSigRequest requestA = new AuthenticateSigRequest();
            requestA.setSessionId(csessionid);// 必填参数，从前端获取，不可更改，android和ios只变更这个参数即可，下面参数不变保留xxx
            requestA.setSig(sig);// 必填参数，从前端获取，不可更改
            requestA.setToken(token);// 必填参数，从前端获取，不可更改
            requestA.setScene(scene);// 必填参数，从前端获取，不可更改
            requestA.setAppKey(appkey);// 必填参数，后端填写
            requestA.setRemoteIp(remoteIp);// 必填参数，后端填写
            AuthenticateSigResponse response = client.getAcsResponse(requestA);
            if (response.getCode() == 100) {
                System.out.println("验签通过");
                return true;
            } else {
                System.out.println("验签失败");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
