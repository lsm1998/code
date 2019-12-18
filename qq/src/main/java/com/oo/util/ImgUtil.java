package com.oo.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 作者：欧阳御林
 * 日期：2018/9/29
 * 时间：8:53
 * 说明：
 */
public class ImgUtil
{
    public static void changeImg(String src, String dest, int size)
    {
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(src));)
        {
            // 字节流转图片对象
            Image bi = ImageIO.read(in);
            // 获取图像的高度，宽度
            int height = bi.getHeight(null);
            int width = bi.getWidth(null);
            // 构建图片流
            BufferedImage tag = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
            // 绘制改变尺寸后的图
            tag.getGraphics().drawImage(bi, 0, 0, size, size, null);
            // 输出流
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dest));
            ImageIO.write(tag, "jpg", out);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
