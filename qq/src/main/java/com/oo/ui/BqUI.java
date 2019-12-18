package com.oo.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BqUI extends JFrame implements MouseListener
{
	JLabel[] bqicon;
	String iconlist[];
	ChatUI chat;

	public BqUI(ChatUI chat, int x, int y)
	{
		this.chat = chat;
		setUndecorated(true);
		setResizable(false);
		setAlwaysOnTop(true);
		Container con = getContentPane();
		con.setLayout(new FlowLayout(FlowLayout.LEFT));
		con.setBackground(Color.WHITE);
		File file = new File("src\\main\\resources\\bq");
		iconlist = file.list();
		bqicon = new JLabel[iconlist.length];
		for (int i = 0; i < iconlist.length; i++)
		{
			bqicon[i] = new JLabel(new ImageIcon("src\\main\\resources\\bq\\" + iconlist[i]));
			bqicon[i].setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
			bqicon[i].addMouseListener(this);
			add(bqicon[i]);
		}
		setSize(300, 320);
		setVisible(true);
		setLocation(x, y);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void mouseClicked(MouseEvent e)
	{
		if (e.getClickCount() == 2)
		{
			for (int i = 0; i < iconlist.length; i++)
			{
				if (e.getSource() == bqicon[i])
				{
					if(e.getButton() == 3)
					{
						dispose();
						break;
					}
					chat.txtSend.insertIcon(bqicon[i].getIcon());
					dispose();
				}
			}
		}
	}

	public void mouseEntered(MouseEvent e)
	{

		for (int i = 0; i < iconlist.length; i++)
		{
			if (e.getSource() == bqicon[i])
			{
				bqicon[i].setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
				break;
			}
		}
	}

	public void mouseExited(MouseEvent e)
	{
		for (int i = 0; i < iconlist.length; i++)
		{
			if (e.getSource() == bqicon[i])
			{
				bqicon[i].setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
				break;
			}
		}
	}

	public void mousePressed(MouseEvent e)
	{
		

	}

	public void mouseReleased(MouseEvent e)
	{

	}
}
