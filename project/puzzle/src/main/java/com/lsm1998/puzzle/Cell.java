package com.lsm1998.puzzle;

import javax.swing.Icon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Cell extends JButton {
	// 图片小方格
	public Cell(Icon icon) {
		super(icon);
		this.setSize(150, 150);
	}

	// 文字图片小方格
	public Cell(String text, Icon icon) {
		super(text, icon);
		this.setSize(150, 150);
		this.setHorizontalTextPosition(CENTER);
		this.setVerticalTextPosition(CENTER);
	}
	public void move(String dirString){
		switch (dirString) {
		case "U":
			this.setLocation(this.getBounds().x, this.getBounds().y-150);
			break;
		case "D":
			this.setLocation(this.getBounds().x, this.getBounds().y+150);
			break;
		case "L":
			this.setLocation(this.getBounds().x-150, this.getBounds().y);
			break;
		case "R":
			this.setLocation(this.getBounds().x+150, this.getBounds().y);
			break;
		default:
			break;
		}
	}

}
