package v2;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import static v2.PathUtil.STATIC_PATH;

@SuppressWarnings("serial")
public class Title extends JFrame {

    static JFrame frame = new JFrame("游戏登录");

    public Title() {
        Music.playMusic();
        frame.setBounds(150, 50, 1080, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        title1 panel = new title1();
        frame.add(panel);
        JButton button = new JButton(new ImageIcon(STATIC_PATH+"/start.png"));
        button.setBorderPainted(false);
        button.setBounds(420, 10, 216, 169);
        panel.setLayout(null);// 自定义布局
        panel.add(button);

        // 设置图标
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(STATIC_PATH+"/图标.jpg");
        frame.setIconImage(image);
        frame.setVisible(true);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeThis();
                JFrame frame = new JFrame("疯狂贪吃蛇");
                // 设置在屏幕的位置
                frame.setBounds(225, 5, 850, 720);
                frame.setResizable(false);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // 设置用户关闭框架时的相应动作
                SnakePanel panel = new SnakePanel();
                frame.add(panel);
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Image image = toolkit.getImage(STATIC_PATH+"/图标.jpg");
                frame.setIconImage(image);
                frame.setVisible(true);
            }
        });
    }
    //
    public static void main(String[] args) {
        new Title();
    }

    protected void closeThis() {
        frame.dispose();
    }
}
@SuppressWarnings("serial")
class title1 extends JPanel {
    ImageIcon body1 = new ImageIcon(STATIC_PATH+"/body1.png");

    public void paint(Graphics g) {
        this.setBackground(Color.BLACK);
        body1.paintIcon(this, g, 0, 0);
    }

}