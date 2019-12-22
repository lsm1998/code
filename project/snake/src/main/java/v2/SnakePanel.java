package v2;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

import static v2.PathUtil.STATIC_PATH;

@SuppressWarnings("serial")
public class SnakePanel extends JPanel implements KeyListener, ActionListener {
    ImageIcon up = new ImageIcon(STATIC_PATH+"/up.png");
    ImageIcon down = new ImageIcon(STATIC_PATH+"/down.png");
    ImageIcon right = new ImageIcon(STATIC_PATH+"/right.png");
    ImageIcon left = new ImageIcon(STATIC_PATH+"/left.png");
    ImageIcon title = new ImageIcon(STATIC_PATH+"/title.png");
    ImageIcon food = new ImageIcon(STATIC_PATH+"/food.png");
    ImageIcon body = new ImageIcon(STATIC_PATH+"/body.png");
    ImageIcon back = new ImageIcon(STATIC_PATH+"/back.png");
    ImageIcon q = new ImageIcon(STATIC_PATH+"/草丛.png");
    ImageIcon t = new ImageIcon(STATIC_PATH+"/砖头.png");
    int[] snakex = new int[1000];
    int[] snakey = new int[1000];
    Random rand = new Random();
    int foodx = rand.nextInt(32) * 25 + 50;
    int foody = 450;
    int wallX;
    int wallY;
    int len = 3;// 初始长度
    int score = 0;// 得分
    String fangxiang = "R";// 初始方向
    int seep = 200;// 初始速度

    boolean isStarted = false;
    boolean isFailed = false;

    Timer timer = new Timer(seep, this);
    Timer timer2 = new Timer(seep, this);
    Timer timer3 = new Timer(seep, this);

    public SnakePanel() {
        this.setFocusable(true);
        this.addKeyListener(this);
        setup();
        timer.start();
    }

    public void paint(Graphics g) {
        this.setBackground(Color.BLACK);
        title.paintIcon(this, g, 0, 0);
        g.fillRect(0, 95, 850, 600);
        back.paintIcon(this, g, 0, 95);

        switch (len) {
            case 4:
                g.setColor(Color.RED);
                g.setFont(new Font("华文行楷", Font.BOLD, 50));
                g.drawString("First  Blood！！！", 245, 195);
                break;
            case 5:
                g.setColor(Color.RED);
                g.setFont(new Font("华文行楷", Font.BOLD, 50));
                g.drawString("Double Kill！！！", 245, 195);
                break;
            case 6:
                g.setColor(Color.RED);
                g.setFont(new Font("华文行楷", Font.BOLD, 50));
                g.drawString("Trible Kill！！！", 245, 195);
                break;
            case 7:
                g.setColor(Color.RED);
                g.setFont(new Font("华文行楷", Font.BOLD, 50));
                g.drawString("Ultra Kill！！！", 245, 195);
                break;
            case 8:
                g.setColor(Color.RED);
                g.setFont(new Font("华文行楷", Font.BOLD, 50));
                g.drawString("Panta Kill！", 245, 195);
                break;
            // 五杀
            case 9:
                g.setColor(Color.RED);
                g.setFont(new Font("华文行楷", Font.BOLD, 50));
                g.drawString(" 难度升级！", 245, 195);
                break;
            case 10:
                g.setColor(Color.RED);
                g.setFont(new Font("华文行楷", Font.BOLD, 50));
                g.drawString("you have slain an enemy!", 200, 195);
                break;
            case 11:
                g.setColor(Color.RED);
                g.setFont(new Font("华文行楷", Font.BOLD, 50));
                g.drawString("killing spree！", 245, 195);
                break;
            case 12:
                g.setColor(Color.RED);
                g.setFont(new Font("华文行楷", Font.BOLD, 50));
                g.drawString(" rampage！", 245, 195);
                break;
            case 13:
                g.setColor(Color.RED);
                g.setFont(new Font("华文行楷", Font.BOLD, 50));
                g.drawString(" unstoppable！", 245, 195);
                break;
            case 14:
                g.setColor(Color.RED);
                g.setFont(new Font("华文行楷", Font.BOLD, 50));
                g.drawString(" dominating！", 245, 195);
                break;
            case 15:
                g.setColor(Color.RED);
                g.setFont(new Font("华文行楷", Font.BOLD, 50));
                g.drawString("God Like！！！", 245, 195);
                break;
            case 16:
                g.setColor(Color.RED);
                g.setFont(new Font("华文行楷", Font.BOLD, 50));
                g.drawString("Legendnery！！！", 245, 195);
                break;
            case 17:
                g.setColor(Color.RED);
                g.setFont(new Font("华文行楷", Font.BOLD, 50));
                g.drawString("已达到最高速度！！！", 245, 195);
                break;

        }
        // q是墙。t是草
        for (int i = 200; i <= 275; i += 25) {
            t.paintIcon(this, g, i, 400);
            t.paintIcon(this, g, i + 350, 400);
            q.paintIcon(this, g, i + 350, 475);
            t.paintIcon(this, g, i + 350, 550);
        }
        for (int i = 425; i <= 525; i += 25) {
            q.paintIcon(this, g, 625, i);
            q.paintIcon(this, g, 275, i);
            t.paintIcon(this, g, 425, 400);
            q.paintIcon(this, g, 425, i);
            t.paintIcon(this, g, 275, 550);
            t.paintIcon(this, g, 425, 550);
        }

        // 画蛇头
        if (fangxiang.equals("R")) {
            right.paintIcon(this, g, snakex[0], snakey[0]);
        } else if (fangxiang.equals("L")) {
            left.paintIcon(this, g, snakex[0], snakey[0]);
        } else if (fangxiang.equals("U")) {
            up.paintIcon(this, g, snakex[0], snakey[0]);
        } else if (fangxiang.equals("D")) {
            down.paintIcon(this, g, snakex[0], snakey[0]);
        }

        // 画蛇的身体
        for (int i = 1; i < len; i++) {
            body.paintIcon(this, g, snakex[i], snakey[i]);
        }

        if (!isStarted) {
            g.setColor(Color.white);
            g.setFont(new Font("arial", Font.BOLD, 30));
            g.drawString("Press Space  to  Start/Pause", 225, 350);
        }

        if (isFailed) {
            g.setColor(Color.white);
            g.setFont(new Font("arial", Font.BOLD, 30));
            g.drawString("Game Over , Press Space  to  Restart", 175, 350);
        }

        food.paintIcon(this, g, foodx, foody);

        g.setColor(Color.white);
        g.setFont(new Font("华文行楷", Font.PLAIN, 30));
        g.drawString("Score:" + score, 700, 30);
        g.drawString("Length:" + len, 700, 65);
    }

    public void setup() {
        foodx = rand.nextInt(32) * 25 + 50;
        foody = rand.nextInt(22) * 25 + 100;
        if (foody == 400 || foody == 550) {
            foody = 450;
        }
        refresh();
        isStarted = false;
        isFailed = false;
        len = 3;
        score = 0;
        fangxiang = "R";
        snakex[0] = 100;
        snakey[0] = 100;
        snakex[1] = 75;
        snakey[1] = 100;
        snakex[2] = 50;
        snakey[2] = 100;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int KeyCode = e.getKeyCode();
        if (KeyCode == KeyEvent.VK_SPACE) {
            if (isFailed) {
                setup();
            } else {
                isStarted = !isStarted;
            }
        } else if (KeyCode == KeyEvent.VK_UP && fangxiang != "D") {
            fangxiang = "U";
        } else if (KeyCode == KeyEvent.VK_DOWN && fangxiang != "U") {
            fangxiang = "D";
        } else if (KeyCode == KeyEvent.VK_RIGHT && fangxiang != "L") {
            fangxiang = "R";
        } else if (KeyCode == KeyEvent.VK_LEFT && fangxiang != "R") {
            fangxiang = "L";
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 2.移动数据
        if (isStarted && !isFailed) {
            // 移动身体
            for (int i = len; i > 0; i--) {
                snakex[i] = snakex[i - 1];
                snakey[i] = snakey[i - 1];
            }
            // 移动头
            if (fangxiang.equals("R")) {
                snakex[0] = snakex[0] + 25;
                if (snakex[0] > 825)
                    isFailed = true;
            } else if (fangxiang.equals("L")) {
                snakex[0] = snakex[0] - 25;
                if (snakex[0] < 0)
                    isFailed = true;
            } else if (fangxiang.equals("U")) {
                snakey[0] = snakey[0] - 25;
                if (snakey[0] < 100)
                    isFailed = true;
            } else if (fangxiang.equals("D")) {
                snakey[0] = snakey[0] + 25;
                if (snakey[0] > 650)
                    isFailed = true;
            }

            // 吃食物。产生新食物
            if (snakex[0] == foodx && snakey[0] == foody) {
                if (len == 3) {
                    Music.playMusic(1);
                }
                if (len == 4) {
                    Music.playMusic(2);
                }
                if (len == 5) {
                    Music.playMusic(3);
                }
                if (len == 6) {
                    Music.playMusic(4);
                }
                if (len == 7) {
                    Music.playMusic(5);
                }
                if (len == 10) {
                    Music.playMusic(6);
                }
                if (len == 11) {
                    Music.playMusic(7);
                }
                if (len == 12) {
                    Music.playMusic(8);
                }
                if (len == 13) {
                    Music.playMusic(9);
                }
                if (len == 14) {
                    Music.playMusic(10);
                }
                if (len == 15) {
                    Music.playMusic(11);
                }
                // 不让食物产生在身体里
                for (int i = 0; i < len; i++) {
                    while (snakex[i] == foodx && snakey[i] == foody) {
                        foodx = rand.nextInt(32) * 25 + 50;
                        foody = rand.nextInt(22) * 25 + 100;
                        if (foody == 400 || foody == 550) {
                            foody = 450;
                        }
                    }
                }
                len++;
                score = (int) Math.pow(len, 2) + score;
                // 调节难度
                if (len == 9) {
                    timer.stop();
                    seep = 100;
                    timer2 = new Timer(seep, this);
                    timer2.start();
                }
                if (len == 17) {
                    timer2.stop();
                    seep = 50;
                    timer3 = new Timer(seep, this);
                    timer3.start();
                }
            }

            // 碰自己
            for (int i = 1; i < len; i++) {
                if (snakex[0] == snakex[i] && snakey[0] == snakey[i]) {
                    isFailed = true;
                    if (len >= 6) {
                        Music.playMusic1();
                    }
                }
            }
            // 碰砖块
            for (int i = 0; i <= len; i++) {
                if ((snakex[i] == 200 || snakex[i] == 225 || snakex[i] == 250
                        || snakex[i] == 275 || snakex[i] == 425
                        || snakex[i] == 550 || snakex[i] == 575
                        || snakex[i] == 600 || snakex[i] == 625)
                        && snakey[i] == 400) {
                    if (len >= 6) {
                        Music.playMusic1();
                    }
                    isFailed = true;
                }
                if ((snakex[i] == 275 || snakex[i] == 425 || snakex[i] == 550
                        || snakex[i] == 575 || snakex[i] == 625 || snakex[i] == 600)
                        && snakey[i] == 550) {
                    if (len >= 6) {
                        Music.playMusic1();
                    }
                    isFailed = true;
                }
            }
        }
        repaint();
    }

    public void refresh() {
        if (timer2 != null) {
            timer2.stop();
        }
        if (timer3 != null) {
            timer3.stop();
        }
        timer = new Timer(200, this);
        timer.start();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}