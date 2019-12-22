package Sky.liu.tuixiangzi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TuixiangziRedmap {
    private int map[][];
    private int mx, my;
    @SuppressWarnings("unused")
    private int level;// 保存游戏关卡

    public TuixiangziRedmap(int level) {
        this.level = level;
        String sfilename = PathUtil.MAP_PATH+"/" + level + ".map";
        String content = "";
        // 文件读取字符流
        try {
            FileReader fr = new FileReader(sfilename);
            @SuppressWarnings("resource")
            BufferedReader br = new BufferedReader(fr);
            String temp = "";
            // 一行一行读取
            while ((temp = br.readLine()) != null) {
                content += temp;
            }
            //System.out.println(content);
            //转化为字节数组
            byte b[]=content.getBytes();
            //for (byte c:b) {
            //	System.out.print(c+"\t");
            //}
            map=new int[20][20];
            int c=0;
            for (int i = 0; i <20; i++) {
                for (int j = 0; j < 20; j++) {
                    map[i][j]=b[c]-48;
                    //System.out.println(map[i][j]);
                    c++;
                    //返回人物位置
                    if(map[i][j]==5){
                        mx=i;
                        my=j;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int[][] getMap() {
        return map;
    }
    public int getMx() {
        return mx;
    }
    public int getMy() {
        return my;
    }
    //public static void main(String[] args) {
    //	new TuixiangziRedmap(0);
    //}
}
