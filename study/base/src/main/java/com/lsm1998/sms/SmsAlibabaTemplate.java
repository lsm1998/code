package com.lsm1998.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class SmsAlibabaTemplate implements SmsTemplate
{
    private final String ConnectTimeout = "10000";

    private final String ReadTimeout = "10000";

    // 固定配置
    private static final String product = "Dysmsapi";
    // 固定配置
    private static final String domain = "dysmsapi.aliyuncs.com";

    private final String accessKeyId;

    private final String accessKeySecret;

    private SmsAlibabaTemplate(String accessKeyId, String accessKeySecret)
    {
        // 可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", ConnectTimeout);
        System.setProperty("sun.net.client.defaultReadTimeout", ReadTimeout);

        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
    }

    public SendSmsResponse send(String signName, String phone, String templateCode, String templateParam) throws Exception
    {
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //设置参数
        request.setTemplateParam(templateParam);
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //request.setOutId("123");
        //hint 此处可能会抛出异常，注意catch
        return acsClient.getAcsResponse(request);
    }
}
