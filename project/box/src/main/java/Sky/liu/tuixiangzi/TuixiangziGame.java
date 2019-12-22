package Sky.liu.tuixiangzi;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TuixiangziGame extends JFrame implements ActionListener {
    JButton btnBack, btnFirst, btnNext, btnPrev;
    JButton btnLast, btnSelect, btnMusic, btnReset;
    JComboBox<String> cbMusic;
    String sMusic[] = { "1", "2", "3" };
    JMenuBar menuBar;
    JMenu mnuOption;
    JMenu mnuSet, mnuHelp;
    JMenuItem miReset, miPrev, miNext, miSelect, miExit, miBack;
    JMenuItem miMusic1, miMusic2, miMusic3, miMusic4, miMusic5;
    JMenuItem miHelp;
    String sMusicFile[] = {};
    Mypanel mainpanel;
    final int MAXLEVEL = 50;

    public static void main(String[] args) {
        new TuixiangziGame();
    }

    public TuixiangziGame() {
        super("推箱子");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(PathUtil.IMG_PATH+"/图标.jpg");
        setIconImage(image);
        Container c = getContentPane();
        c.setLayout(null);
        c.setBackground(Color.PINK);
        JLabel lblTitle = new JLabel("推箱子！！！");
        lblTitle.setFont(new Font("宋体", Font.BOLD, 20));
        lblTitle.setBounds(260, 0, 150, 30);
        add(lblTitle);
        createButton(c);
        createMenus();
        mainpanel = new Mypanel();
        mainpanel.requestFocus();
        mainpanel.setBounds(0, 40, 600, 600);
        c.add(mainpanel);
        setSize(720, 720);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainpanel.requestFocus();
    }

    // 菜单
    private void createMenus() {
        // 选项子菜单
        mnuOption = new JMenu("选项");
        miBack = new JMenuItem("悔一步");
        miReset = new JMenuItem("重置");
        miPrev = new JMenuItem("上一关");
        miNext = new JMenuItem("下一关");
        miSelect = new JMenuItem("选关");
        miExit = new JMenuItem("退出");
        mnuOption.add(miBack);
        mnuOption.add(miReset);
        mnuOption.add(miPrev);
        mnuOption.add(miNext);
        mnuOption.add(miSelect);
        mnuOption.addSeparator();
        mnuOption.add(miExit);
        // 设置子菜单
        mnuSet = new JMenu("设置");
        miMusic1 = new JMenuItem(sMusic[0]);
        miMusic2 = new JMenuItem(sMusic[1]);
        miMusic3 = new JMenuItem(sMusic[2]);
        mnuSet.add(miMusic1);
        mnuSet.add(miMusic2);
        mnuSet.add(miMusic3);
        // 帮助子菜单
        mnuHelp = new JMenu("帮助（H）");
        mnuHelp.setMnemonic('H');
        miHelp = new JMenuItem("关于我们...");
        mnuHelp.add(miHelp);

        miBack.addActionListener(this);
        miReset.addActionListener(this);
        miNext.addActionListener(this);
        miPrev.addActionListener(this);
        miExit.addActionListener(this);
        miSelect.addActionListener(this);
        miBack.addActionListener(this);
        miMusic1.addActionListener(this);
        miMusic2.addActionListener(this);
        miMusic3.addActionListener(this);
        miHelp.addActionListener(this);

        menuBar = new JMenuBar();
        menuBar.add(mnuOption);
        menuBar.add(mnuSet);
        menuBar.add(mnuHelp);
        setJMenuBar(menuBar);
    }

    // 按钮
    private void createButton(Container c) {
        btnReset = new JButton("重来");
        btnBack = new JButton("悔一步");
        btnFirst = new JButton("第一关");
        btnNext = new JButton("下一关");
        btnPrev = new JButton("上一关");
        btnLast = new JButton("最终关");
        btnSelect = new JButton("选关");
        btnMusic = new JButton("音乐关");
        JLabel lblMusic = new JLabel("选择音乐");
        cbMusic = new JComboBox<String>(sMusic);
        c.add(btnReset);
        c.add(btnBack);
        c.add(btnFirst);
        c.add(btnNext);
        c.add(btnPrev);
        c.add(btnLast);
        c.add(btnSelect);
        c.add(btnMusic);
        c.add(lblMusic);
        c.add(cbMusic);
        btnReset.setBounds(610, 80, 80, 30);
        btnBack.setBounds(610, 130, 80, 30);
        btnFirst.setBounds(610, 180, 80, 30);
        btnNext.setBounds(610, 230, 80, 30);
        btnPrev.setBounds(610, 280, 80, 30);
        btnLast.setBounds(610, 330, 80, 30);
        btnSelect.setBounds(610, 380, 80, 30);
        btnMusic.setBounds(610, 430, 80, 30);
        lblMusic.setBounds(610, 480, 80, 30);
        cbMusic.setBounds(610, 530, 80, 30);
        btnReset.addActionListener(this);
        btnBack.addActionListener(this);
        btnFirst.addActionListener(this);
        btnNext.addActionListener(this);
        btnPrev.addActionListener(this);
        btnLast.addActionListener(this);
        btnSelect.addActionListener(this);
        btnMusic.addActionListener(this);
        cbMusic.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == miHelp) {
            String str = "     作者:@卓尔不凡\n";
            str += "     QQ:487005831\n";
            str += "     版本:2017版\n";
            JOptionPane.showMessageDialog(this, str, "帮助",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == miExit) {
            System.exit(0);
        } else if (e.getSource() == btnFirst) {
            mainpanel.setLevel(0);
        } else if (e.getSource() == btnNext) {
            mainpanel.setLevel(1);
        } else if (e.getSource() == btnPrev) {
            mainpanel.setLevel(-1);
        } else if (e.getSource() == btnLast) {
            mainpanel.setLevel(MAXLEVEL);
        } else if (e.getSource() == btnSelect) {
            String slevel = JOptionPane.showInputDialog(this, "请输入1~50之间的数字",
                    "选关", JOptionPane.OK_CANCEL_OPTION);
            if (slevel != null) {
                try {
                    int level = Integer.parseInt(slevel);
                    if (level <= 0 || level >= 50) {
                        JOptionPane.showMessageDialog(this, "没有这一关！");
                        return;
                    }
                    mainpanel.setLevel(level);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "非法字符！");
                    return;
                }

            }
            System.out.println(slevel);
        }else if(e.getSource()==btnBack){
            mainpanel.goBack();
        }
    }

    // 游戏面板
    class Mypanel extends JPanel implements KeyListener {
        private int level = 1;
        private int mx;
        private int my;
        private int map[][];// 原始数据
        private int tempMap[][];// 临时数据，游戏过程更新

        Toolkit kit = Toolkit.getDefaultToolkit();
        Image mapimg[] = { kit.getImage(PathUtil.IMG_PATH+"/0.jpg"), kit.getImage(PathUtil.IMG_PATH+"/1.png"),
                kit.getImage(PathUtil.IMG_PATH+"/2.jpg"), kit.getImage(PathUtil.IMG_PATH+"/3.jpg"),
                kit.getImage(PathUtil.IMG_PATH+"/4.png"), kit.getImage(PathUtil.IMG_PATH+"/5.png"),
                kit.getImage(PathUtil.IMG_PATH+"/6.png"), kit.getImage(PathUtil.IMG_PATH+"/7.png"),
                kit.getImage(PathUtil.IMG_PATH+"/8.png"), kit.getImage(PathUtil.IMG_PATH+"/9.jpg") };

        public Mypanel() {
            this.addKeyListener(this);
            setSize(600, 600);
            readFile(level);
        }

        /*
         * 堆栈，保存推箱子过程中的每一步 左：1和11 右：2和22 上：3和33 下：4和44
         */
        Stack<Integer> step = new Stack<Integer>();

        // 读取地图
        public void readFile(int level) {
            TuixiangziRedmap redMap = new TuixiangziRedmap(level);
            this.map = redMap.getMap();
            this.mx = redMap.getMx();
            this.my = redMap.getMy();
            this.tempMap = new TuixiangziRedmap(level).getMap();
            repaint();// 重画
        }

        public void paint(Graphics g) {
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    g.drawImage(mapimg[tempMap[j][i]], i * 30, j * 30, 30, 30,
                            this);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("微软雅黑", Font.BOLD, 30));
            g.drawString("现在是第", 180, 40);
            g.drawString(String.valueOf(level), 310, 40);
            g.drawString("关", 340, 40);
        }

        // 判断是否全部推到位
        public boolean isWin() {
            boolean isok = true;
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    if ((map[i][j] == 4 || map[i][j] == 9)
                            && tempMap[i][j] != 9) {
                        isok = false;
                        break;
                    }
                }
                if (!isok)
                    break;
            }
            return isok;
        }

        // 设置关卡
        public void setLevel(int level) {
            if (level == 0) {
                this.level = 1;
            } else if (level == 50) {
                this.level = 50;
            } else if (level > 1) {
                this.level = level;
            } else {
                this.level += level;
            }
            if (this.level <= 0) {
                this.level = 1;
            } else if (this.level >= 50) {
                this.level = 50;
            }
            readFile(this.level);
            //清空上一关堆栈数据
            step.clear();
            // 设置焦点
            requestFocus();
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case 37:
                    goLeft();
                    break;
                case 38:
                    goUp();
                    break;
                case 39:
                    goRight();
                    break;
                case 40:
                    goDown();
                    break;
                default:
                    break;
            }
            if (isWin()) {
                JOptionPane.showMessageDialog(null, "过关！");
                setLevel(1);
            }
        }

        // 悔一步函数
        public void goBack(){
            //判断堆栈是否为空
            if(step.isEmpty()){
                JOptionPane.showMessageDialog(null, "你还没有任何移动！");
                return;
            }
            int n=step.pop();//取出堆栈顶元素
            switch (n) {
                case 1:
                case 11:
                    backLeft(n);
                    break;
                case 2:
                case 22:
                    backUp(n);
                    break;
                case 3:
                case 33:
                    backRight(n);
                    break;
                case 4:
                case 44:
                    backDown(n);
                    break;
            }
            requestFocus();
        }
        public void backLeft(int step) {
            if(step==1){
                tempMap[mx][my+1]=6;
                if(map[mx][my]==4||map[mx][my]==9)
                    tempMap[mx][my]=4;
                else
                    tempMap[mx][my]=2;
                my++;
            }else{
                tempMap[mx][my+1]=6;
                if(map[mx][my]==4||map[mx][my]==9){
                    tempMap[mx][my]=9;
                }else{
                    tempMap[mx][my]=3;
                }
                if(map[mx][my-1]==4||map[mx][my-1]==9){
                    tempMap[mx][my-1]=4;
                }else{
                    tempMap[mx][my-1]=2;
                }
                my++;
            }
            repaint();
        }
        public void backRight(int step) {
            if(step==3){
                tempMap[mx][my-1]=7;
                if(map[mx][my]==4||map[mx][my]==9)
                    tempMap[mx][my]=4;
                else
                    tempMap[mx][my]=2;
                my--;
            }else{
                tempMap[mx][my-1]=7;
                if(map[mx][my]==4||map[mx][my]==9){
                    tempMap[mx][my]=9;
                }else{
                    tempMap[mx][my]=3;
                }
                if(map[mx][my+1]==4||map[mx][my+1]==9){
                    tempMap[mx][my+1]=4;
                }else{
                    tempMap[mx][my+1]=2;
                }
                my--;
            }
            repaint();
        }
        public void backUp(int step) {
            if(step==2){
                tempMap[mx+1][my]=8;
                if(map[mx][my]==4||map[mx][my]==9)
                    tempMap[mx][my]=4;
                else
                    tempMap[mx][my]=2;
                mx++;
            }else{
                tempMap[mx+1][my]=8;
                if(map[mx][my]==4||map[mx][my]==9){
                    tempMap[mx][my]=9;
                }else{
                    tempMap[mx][my]=3;
                }
                if(map[mx-1][my]==4||map[mx-1][my]==9){
                    tempMap[mx-1][my]=4;
                }else{
                    tempMap[mx-1][my]=2;
                }
                mx++;
            }
            repaint();
        }
        public void backDown(int step) {
            if(step==4){
                tempMap[mx-1][my]=5;
                if(map[mx][my]==4||map[mx][my]==9)
                    tempMap[mx][my]=4;
                else
                    tempMap[mx][my]=2;
                mx--;
            }else{
                tempMap[mx-1][my]=5;
                if(map[mx][my]==4||map[mx][my]==9){
                    tempMap[mx][my]=9;
                }else{
                    tempMap[mx][my]=3;
                }
                if(map[mx+1][my]==4||map[mx+1][my]==9){
                    tempMap[mx+1][my]=4;
                }else{
                    tempMap[mx+1][my]=2;
                }
                mx--;
            }
            repaint();
        }
        @Override
        public void keyReleased(KeyEvent e) {

        }

        public void goLeft() {
            // 人的左面有箱子
            if (tempMap[mx][my - 1] == 3 || tempMap[mx][my - 1] == 9) {
                // 箱子的左面是2或者4就可以推
                if (tempMap[mx][my - 2] == 2 || tempMap[mx][my - 2] == 4) {
                    if (tempMap[mx][my - 2] == 2) {
                        tempMap[mx][my - 2] = 3;
                        tempMap[mx][my - 1] = 6;
                    } else {
                        tempMap[mx][my - 2] = 9;
                        tempMap[mx][my - 1] = 6;
                    }
                    // 原来人物的位置要和原始地图相比较
                    if (map[mx][my] == 4 || map[mx][my] == 9) {
                        tempMap[mx][my] = 4;
                    } else {
                        tempMap[mx][my] = 2;
                    }
                    my--;
                    step.push(11);
                }
                // 左面是草地
            } else if (tempMap[mx][my - 1] == 2 || tempMap[mx][my - 1] == 4
                    || tempMap[mx][my - 1] == 5) {
                tempMap[mx][my - 1] = 6;

                if (map[mx][my] == 4 || map[mx][my] == 9) {
                    tempMap[mx][my] = 4;
                } else {
                    tempMap[mx][my] = 2;
                }
                my--;
                step.push(1);
            }
            repaint();
        }

        public void goRight() {
            // 人的右面有箱子
            if (tempMap[mx][my + 1] == 3 || tempMap[mx][my + 1] == 9) {
                // 箱子的右面是2或者4就可以推
                if (tempMap[mx][my + 2] == 2 || tempMap[mx][my + 2] == 4) {
                    if (tempMap[mx][my + 2] == 2) {
                        tempMap[mx][my + 2] = 3;
                        tempMap[mx][my + 1] = 7;
                    } else {
                        tempMap[mx][my + 2] = 9;
                        tempMap[mx][my + 1] = 7;
                    }
                    // 原来人物的位置要和原始地图相比较
                    if (map[mx][my] == 4 || map[mx][my] == 9) {
                        tempMap[mx][my] = 4;
                    } else {
                        tempMap[mx][my] = 2;
                    }
                    my++;
                    step.push(33);
                }
                // 右面是草地
            } else if (tempMap[mx][my + 1] == 2 || tempMap[mx][my + 1] == 4
                    || tempMap[mx][my + 1] == 5) {
                tempMap[mx][my + 1] = 7;

                if (map[mx][my] == 4 || map[mx][my] == 9) {
                    tempMap[mx][my] = 4;
                } else {
                    tempMap[mx][my] = 2;
                }
                my++;
                step.push(3);
            }
            repaint();
        }

        public void goUp() {
            // 人的上面有箱子
            if (tempMap[mx - 1][my] == 3 || tempMap[mx - 1][my] == 9) {
                // 箱子的上面是2或者4就可以推
                if (tempMap[mx - 2][my] == 2 || tempMap[mx - 2][my] == 4) {
                    if (tempMap[mx - 2][my] == 2) {
                        tempMap[mx - 2][my] = 3;
                        tempMap[mx - 1][my] = 8;
                    } else {
                        tempMap[mx - 2][my] = 9;
                        tempMap[mx - 1][my] = 8;
                    }
                    // 原来人物的位置要和原始地图相比较
                    if (map[mx][my] == 4 || map[mx][my] == 9) {
                        tempMap[mx][my] = 4;
                    } else {
                        tempMap[mx][my] = 2;
                    }
                    mx--;
                    step.push(22);
                }
                // 前面是草地
            } else if (tempMap[mx - 1][my] == 2 || tempMap[mx - 1][my] == 4
                    || tempMap[mx - 1][my] == 5) {
                tempMap[mx - 1][my] = 8;

                if (map[mx][my] == 4 || map[mx][my] == 9) {
                    tempMap[mx][my] = 4;
                } else {
                    tempMap[mx][my] = 2;
                }
                mx--;
                step.push(2);
            }
            repaint();
        }

        public void goDown() {
            // 人的下面有箱子
            if (tempMap[mx + 1][my] == 3 || tempMap[mx + 1][my] == 9) {
                // 箱子的下面是2或者4就可以推
                if (tempMap[mx + 2][my] == 2 || tempMap[mx + 2][my] == 4) {
                    if (tempMap[mx + 2][my] == 2) {
                        tempMap[mx + 2][my] = 3;
                        tempMap[mx + 1][my] = 5;
                    } else {
                        tempMap[mx + 2][my] = 9;
                        tempMap[mx + 1][my] = 5;
                    }
                    // 原来人物的位置要和原始地图相比较
                    if (map[mx][my] == 4 || map[mx][my] == 9) {
                        tempMap[mx][my] = 4;
                    } else {
                        tempMap[mx][my] = 2;
                    }
                    mx++;
                    step.push(44);
                }
                // 下面是草地
            } else if (tempMap[mx + 1][my] == 2 || tempMap[mx + 1][my] == 4
                    || tempMap[mx + 1][my] == 5) {
                tempMap[mx + 1][my] = 5;

                if (map[mx][my] == 4 || map[mx][my] == 9) {
                    tempMap[mx][my] = 4;
                } else {
                    tempMap[mx][my] = 2;
                }
                mx++;
                step.push(4);
            }
            repaint();
        }
    }
}
