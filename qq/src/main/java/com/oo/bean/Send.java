package com.oo.bean;

import com.oo.domain.User;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 作者：刘时明
 * 日期：2018/10/1 0001
 * 时间：1:01
 * 说明：发送数据组件类
 */
@Component
public class Send
{
    @Async
    public void sendFile(File file, User target)
    {

    }
}
