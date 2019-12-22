package v1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SnakePanel extends JPanel implements KeyListener{
	Snake snake;
	Food food;
	public SnakePanel() {
		snake=new Snake();
		food=new Food();
		new Run().start();
		this.setFocusable(true);
		this.addKeyListener(this);
	}
	public void paint(Graphics g) {
          g.setColor(Color.black);
          g.fillRect(0, 0, 800, 600);
          //画食物
          g.setColor(Color.BLUE);
          g.fillRect(food.foodx, food.foody, food.foodSize, food.foodSize);
          //画蛇头
          g.setColor(Color.CYAN);
          g.fillRect(snake.snakex[0], snake.snakey[0], snake.snakeSize, snake.snakeSize);
          if(snake.snakex[0]==food.foodx&&snake.snakey[0]==food.foody){
        	  food.addFood();
        	  snake.len+=1;
          }
          snake.die();
          //画蛇身
          for (int i = 2; i < snake.len; i++) {
        	  g.setColor(Color.YELLOW);
              g.fillRect(snake.snakex[i], snake.snakey[i], snake.snakeSize, snake.snakeSize);
		}
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent g) {
		switch (g.getKeyCode()) {
		case KeyEvent.VK_UP:
			if(snake.fx!="D")
			snake.fx="U";
			break;
		case KeyEvent.VK_DOWN:
			if(snake.fx!="U")
			snake.fx="D";
			break;
		case KeyEvent.VK_LEFT:
			if(snake.fx!="R")
			snake.fx="L";
			break;
		case KeyEvent.VK_RIGHT:
			if(snake.fx!="L")
			snake.fx="R";
			break;
		}
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	class Run extends Thread{
		@Override
		public void run() {
			while(true){
				snake.move();
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				repaint();
			}
		}
	}
}
