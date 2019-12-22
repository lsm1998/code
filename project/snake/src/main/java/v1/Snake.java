package v1;

import javax.swing.JOptionPane;

public class Snake {
	Food food;
	int snakex[] = new int[20];
	int snakey[] = new int[20];
	int snakeSize;
	int len=2;
	String fx = "R";

	public Snake() {
		snakex[0]=200;
		snakey[0]=200;
		snakex[1]=225;
		snakey[1]=200;
		snakeSize = 25;
	}

	public void move() {
		switch (fx) {
		case "R":
			snakex[0]+=25;
			break;
		case "L":
			snakex[0]-=25;
			break;
		case "U":
			snakey[0]-=25;
			break;
		case "D":
			snakey[0]+=25;
			break;
		}
		for (int i = len; i >0; i--) {
			snakex[i]=snakex[i-1];
			snakey[i]=snakey[i-1];
		}
	}
	public void die(){
		if(snakex[0]>775||snakex[0]<0||snakey[0]>575||snakey[0]<0){
			JOptionPane.showMessageDialog(null, "游戏结束");
			System.exit(0);
		}
		for (int i = 2; i < len; i++) {
			if(snakex[0]==snakex[i]&&snakey[0]==snakey[i]){
				JOptionPane.showMessageDialog(null, "游戏结束");
				System.exit(0);
			}
		}
	}
}
