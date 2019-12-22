package v2;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static v2.PathUtil.STATIC_PATH;

public class Music {
    @SuppressWarnings("deprecation")
    static void playMusic(int i) {// 背景音乐播放
        try {
            URL cb = null;
            switch (i) {
                case 1:
                    File f1 = new File(STATIC_PATH+"/第一滴血.wav"); // 引号里面的是音乐文件所在的路径
                    cb = f1.toURL();
                    break;
                case 2:
                    File f2 = new File(STATIC_PATH+"/2.wav"); // 引号里面的是音乐文件所在的路径
                    cb = f2.toURL();
                    break;
                case 3:
                    File f3 = new File(STATIC_PATH+"/3.wav"); // 引号里面的是音乐文件所在的路径
                    cb = f3.toURL();
                    break;
                case 4:
                    File f4= new File(STATIC_PATH+"/4.wav"); // 引号里面的是音乐文件所在的路径
                    cb = f4.toURL();
                    break;
                case 5:
                    File f5 = new File(STATIC_PATH+"/5.wav"); // 引号里面的是音乐文件所在的路径
                    cb = f5.toURL();
                    break;
                case 6:
                    File f6 = new File(STATIC_PATH+"/kill spare.wav"); // 引号里面的是音乐文件所在的路径
                    cb = f6.toURL();
                    break;
                case 7:
                    File f7= new File(STATIC_PATH+"/rampage.wav"); // 引号里面的是音乐文件所在的路径
                    cb = f7.toURL();
                    break;
                case 8:
                    File f8= new File(STATIC_PATH+"/unstoppedable.wav"); // 引号里面的是音乐文件所在的路径
                    cb = f8.toURL();
                    break;
                case 9:
                    File f9= new File(STATIC_PATH+"/domminating.wav"); // 引号里面的是音乐文件所在的路径
                    cb = f9.toURL();
                    break;
                case 10:
                    File f10= new File(STATIC_PATH+"/god like.wav"); // 引号里面的是音乐文件所在的路径
                    cb = f10.toURL();
                    break;
                case 11:
                    File f11= new File(STATIC_PATH+"/legendary.wav"); // 引号里面的是音乐文件所在的路径
                    cb = f11.toURL();
                    break;
            }


            AudioClip aau;
            aau = Applet.newAudioClip(cb);
            aau.play();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    static  void playMusic(){//背景音乐播放
        try {
            URL cb;
            File f = new File(STATIC_PATH+"/钢琴.wav"); // 引号里面的是音乐文件所在的路径
            cb = f.toURL();
            AudioClip aau;
            aau = Applet.newAudioClip(cb);
            aau.play();
            aau.loop();//循环播放
            System.out.println("可以播放");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    static void playMusic1(){//背景音乐播放
        try {
            URL cb;
            File f = new File(STATIC_PATH+"/die.wav"); // 引号里面的是音乐文件所在的路径
            cb = f.toURL();
            AudioClip aau;
            aau = Applet.newAudioClip(cb);
            aau.play();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
