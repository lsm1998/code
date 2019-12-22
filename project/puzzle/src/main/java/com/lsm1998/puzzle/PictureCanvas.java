package com.lsm1998.puzzle;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PictureCanvas extends JPanel implements MouseListener {
	public static int PictureID = 1;
	public static int stepNum = 0;
	private boolean hasAddActionListener = false;// 是否点击了按钮
	private Cell[] cell;
	private Rectangle nullCell;

	public PictureCanvas() {
		this.setLayout(null);
		cell = new Cell[12];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				ImageIcon icon = new ImageIcon(MainApp.PATH+"\\" + PictureID + "-"
						+ (i * 3 + j + 1) + ".gif");
				cell[i * 3 + j] = new Cell(icon);
				cell[i * 3 + j].setLocation(20 + j * 150, 20 + i * 150);
				this.add(cell[i * 3 + j]);
			}
		}
		this.remove(cell[11]);// 删除图片方法
		nullCell = new Rectangle(320, 470, 150, 150);
	}

	public void reLoaPictureAddNumber() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				ImageIcon icon = new ImageIcon(MainApp.PATH+"\\" + PictureID + "-"
						+ (i * 3 + j + 1) + ".gif");
				cell[i * 3 + j].setIcon(icon);
				cell[i * 3 + j].setText("" + (i * 3 + j + 1));
				cell[i * 3 + j].setVerticalTextPosition(this.getY() / 2);
				cell[i * 3 + j].setHorizontalTextPosition(this.getX() / 2);
			}
		}
	}

	public void reLoaPictureClearNumber() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				ImageIcon icon = new ImageIcon(MainApp.PATH+"\\" + PictureID + "-"
						+ (i * 3 + j + 1) + ".gif");
				cell[i * 3 + j].setIcon(icon);
				cell[i * 3 + j].setText("");
			}
		}
	}

	public void start() {
		if (!hasAddActionListener) {
			// 添加监听
			for (int i = 0; i < 11; i++) {
				cell[i].addMouseListener(this);
			}
			hasAddActionListener = true;
		}
		while (cell[0].getBounds().x <= 150 + 20
				&& cell[0].getBounds().y <= 170) {
			int nullX = nullCell.getBounds().x;
			int nullY = nullCell.getBounds().y;
			int direction = (int) (Math.random() * 4);
			switch (direction) {
			case 0:
				nullX -= 150;
				cellMove(nullX, nullY, "R");
				break;
			case 1:
				nullX += 150;
				cellMove(nullX, nullY, "L");
				break;
			case 2:
				nullY -= 150;
				cellMove(nullX, nullY, "D");
				break;
			case 3:
				nullY += 150;
				cellMove(nullX, nullY, "U");
				break;

			default:
				break;
			}
		}
	}

	// 方格与空方格的移动
	private void cellMove(int nullX, int nullY, String direction) {
		for (int i = 0; i < 11; i++) {
			if (cell[i].getBounds().x == nullX
					&& cell[i].getBounds().y == nullY) {
				cell[i].move(direction);
				nullCell.setLocation(nullX, nullY);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {// 鼠标按下
		Cell button = (Cell) e.getSource();
		int clickX = button.getBounds().x;
		int clickY = button.getBounds().y;
		int nullX = nullCell.getBounds().x;
		int nullY = nullCell.getBounds().y;
		if (clickX == nullX && clickY - nullY == 150) {// 下面
			button.move("U");
		} else if (clickX == nullX && clickY - nullY == -150) {// 上面
			button.move("D");
		} else if (clickX - nullX == 150 && clickY == nullY) {// 右面
			button.move("L");
		} else if (clickX - nullX == -150 && clickY == nullY) {// 左面
			button.move("R");
		}else{
			return;
		}
		nullCell.setLocation(clickX, clickY);
		stepNum++;
		PictureMainFrame.step.setText("步数:"+stepNum);
		this.repaint();
		//游戏是否结束
		if(this.isFinish()){
			JOptionPane.showMessageDialog(this, "游戏结束，你赢了！\\n 所用步数："+stepNum);
			//撤销操作
			for (int i = 0; i < 11; i++) {
				cell[i].removeMouseListener(this);
			}
			hasAddActionListener=false;
		}
	}
	//根据坐标判断是否完成
	private boolean isFinish(){
		for (int i = 0; i < 11; i++) {
			int x=cell[i].getBounds().x;
			int y=cell[i].getBounds().y;
			if((y-20)/150*3+(x-20)/150!=i){
				return false;
			}
		}
		return true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
