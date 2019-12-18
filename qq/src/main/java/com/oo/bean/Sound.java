package com.oo.bean;

import javazoom.jl.player.Player;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * 作者：刘时明
 * 日期：2018/10/1
 * 时间：0:46
 * 说明：音频播放组件类
 */
@Component
public class Sound
{
    @Async
    public void play(String filePath)
    {
        filePath = "src\\main\\resources\\sound\\" + filePath+".mp3";
        Player player = null;
        try
        {
            player = new Player(new BufferedInputStream(new FileInputStream(new File(filePath))));
            player.play();
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            if (player != null)
                player.close();
        }
    }
}
