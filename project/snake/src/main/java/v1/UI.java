package v1;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class UI extends JFrame{
	public UI() {
		super("MY贪吃蛇");
		this.setBounds(400, 200, 815, 640);
		init();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private void init() {
		SnakePanel sp=new SnakePanel();
		sp.setBounds(0, 0, 800, 600);
		this.add(sp);
	}
}
