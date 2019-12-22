package GameBasic;//棋子类

import java.awt.Image;
import java.awt.Point;

public class Chess {
    public static final int RED = 1;
    public static final int BLACK = 0;
    public short player;//阵营
    public String typeName;//棋子名称
    public Point pos;//位置
    private Image chessImage;//棋子图案
    public Chess(short player,String typeName,Point pos){
        this.player=player;
        this.typeName=typeName;
        this.pos=pos;
    }
}
