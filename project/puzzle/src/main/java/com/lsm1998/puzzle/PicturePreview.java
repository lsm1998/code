package com.lsm1998.puzzle;


import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PicturePreview extends JPanel {
    // 绘制组件
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		String filename=MainApp.PATH+"\\"+PictureCanvas.PictureID+".gif";
		ImageIcon icon=new ImageIcon(filename);
		Image image=icon.getImage();
		g.drawImage(image, 20, 20, 450, 600, this);
	}
}
