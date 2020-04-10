package com.lsm1998.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.junit.Test;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-10 09:08
 **/
public class SendDemo
{
    private static String accessKeyId="你的accessKeyId";
    private static String accessKeySecret="你的accessKeySecret";

    private static String signName="你的短信签名";

    private static String templateCode="你的短信模板code";

    // 固定配置
    private static String product="Dysmsapi";
    private static String domain="dysmsapi.aliyuncs.com";

    @Test
    public void test1() throws Exception
    {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers("17774582028");
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //设置参数
        String count="10";
        String persons="我啊";
        request.setTemplateParam("{\"count\":\"" +count+ "\",\"persons\":\""+persons+"\"}");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //request.setOutId("123");
        //hint 此处可能会抛出异常，注意catch
        try {
            SendSmsResponse acsResponse = acsClient.getAcsResponse(request);
            System.out.println(acsResponse.getCode());
            System.out.println(acsResponse.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
