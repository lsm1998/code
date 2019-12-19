package com.oo.server;

import com.oo.config.Application;
import com.oo.ui.LoginUI;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 作者：刘时明
 * 日期：2018/9/24
 * 时间：23:16
 * 说明：客户端程序入口
 */
public class App
{
    public static AnnotationConfigApplicationContext context;

    static
    {
        context = new AnnotationConfigApplicationContext(Application.class);
    }

    public static void main(String[] args)
    {
        new LoginUI().setVisible(true);
        //Sound sound=context.getBean(Sound.class);
        //sound.play("语音");
        //sound.play("鸽子");
    }
}
