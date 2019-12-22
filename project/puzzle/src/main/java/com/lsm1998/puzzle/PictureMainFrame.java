package com.lsm1998.puzzle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class PictureMainFrame extends JFrame {
	private String[] items = { "泰勒斯威夫特", "罗微" };
	private JRadioButton clearNumInfo;
	private ButtonGroup buttonGroup;
	private JRadioButton addNumInfo;
	private PictureCanvas canvas;
	private PicturePreview preview;
	private JComboBox<String> box;
	private JTextField name;
	public static JTextField step;
	private JButton start;

	public PictureMainFrame() {
		// TODO Auto-generated constructor stub
		init();
		addComponent();
		addPreviewImage();
		addActionListener();
	}

	private void addActionListener() {
		addNumInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.reLoaPictureAddNumber();
			}
		});
		clearNumInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.reLoaPictureClearNumber();
			}
		});
		box.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int num = box.getSelectedIndex();// 获取图片序号，默认0开始
				PictureCanvas.PictureID = num + 1;
				preview.repaint();
				canvas.reLoaPictureClearNumber();
				name.setText("图片名称：" + box.getSelectedItem());
				PictureCanvas.stepNum = 0;
				step.setText("步数：" + PictureCanvas.stepNum);
				clearNumInfo.setSelected(true);
			}
		});
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PictureCanvas.stepNum = 0;
				step.setText("步数：" + PictureCanvas.stepNum);
				canvas.start();
			}
		});
	}

	private void addPreviewImage() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		canvas = new PictureCanvas();
		canvas.setBorder(new TitledBorder("拼图区"));// 设置边框和标题
		preview = new PicturePreview();
		preview.setBorder(new TitledBorder("预览区"));
		panel.add(canvas, BorderLayout.WEST);
		panel.add(preview, BorderLayout.EAST);
		this.add(panel, BorderLayout.CENTER);
	}

	private void addComponent() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		JPanel leftpanel = new JPanel();
		leftpanel.setBackground(Color.pink);
		panel.add(leftpanel, BorderLayout.WEST);
		leftpanel.setBorder(new TitledBorder("按钮区"));
		this.add(panel, BorderLayout.NORTH);
		addNumInfo = new JRadioButton("数字提示", false);
		clearNumInfo = new JRadioButton("清除显示", false);
		buttonGroup = new ButtonGroup();
		box = new JComboBox<String>(items);
		start = new JButton("开始");
		buttonGroup.add(addNumInfo);
		buttonGroup.add(clearNumInfo);
		addNumInfo.setBackground(Color.pink);
		clearNumInfo.setBackground(Color.pink);
		start.setBackground(Color.pink);
		leftpanel.add(addNumInfo);
		leftpanel.add(clearNumInfo);
		leftpanel.add(new JLabel("            选择图片："));
		leftpanel.add(box);
		leftpanel.add(start);
		JPanel righpanel = new JPanel();
		righpanel.setLayout(new GridLayout(1, 2));
		name = new JTextField("图片名称：泰勒斯威夫特");
		step = new JTextField("步数：0");
		// 文本不可修改
		name.setEditable(false);
		step.setEditable(false);
		righpanel.add(name, BorderLayout.WEST);
		righpanel.add(step, BorderLayout.EAST);
		righpanel.setBackground(Color.pink);
		panel.add(righpanel, BorderLayout.EAST);
		righpanel.setBorder(new TitledBorder("游戏状态"));
		this.add(panel, BorderLayout.NORTH);
	}

	private void init() {
		this.setTitle("拼图游戏");
		this.setSize(1000, 720);
		this.setLocation(350, 100);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
