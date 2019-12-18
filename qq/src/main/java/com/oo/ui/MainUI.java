package com.oo.ui;

import com.oo.bean.Sound;
import com.oo.domain.Friends;
import com.oo.domain.Group;
import com.oo.domain.Msg;
import com.oo.domain.User;
import com.oo.mapper.FriendsMapper;
import com.oo.mapper.GroupMapper;
import com.oo.mapper.MsgMapper;
import com.oo.mapper.UserMapper;
import com.oo.server.App;
import com.oo.util.Cmd;
import com.oo.util.SendCmd;
import com.oo.util.SendMsg;
import com.oo.yuyin.Client;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：刘时明
 * 日期：2018/9/26
 * 时间：11:06
 * 说明：
 */
public class MainUI extends JFrame implements MouseListener
{
    private Socket socket;
    private UserMapper userMapper = App.context.getBean(UserMapper.class);
    private FriendsMapper friendsMapper = App.context.getBean(FriendsMapper.class);
    private GroupMapper groupMapper = App.context.getBean(GroupMapper.class);
    private MsgMapper msgMapper = App.context.getBean(MsgMapper.class);
    JLabel bgImg, lblmyInfo;
    private JTabbedPane tabbedPane;
    private JPopupMenu popupMenu;
    private JMenuItem[] menuItems;
    private JButton btnFind, btnGroup;
    private User myInfo, friendInfo;
    private List<Group> groupList;
    private JList[] jLists;
    private List<User>[] friendsList;
    private List<User> friendList;
    private Map<Long, ChatUI> chatWin = new HashMap<>();

    public MainUI(User myInfo, Socket socket)
    {
        this.myInfo = myInfo;
        this.socket = socket;
        groupList = groupMapper.getGroupsByAcc(myInfo.getAccNumber());
        friendList = userMapper.getMyFriend(myInfo.getAccNumber());

        jLists = new JList[groupList.size()];
        friendsList = new List[groupList.size()];
        this.setResizable(false);
        this.setIconImage(new ImageIcon("src\\main\\resources\\img\\logo.png").getImage());
        this.setTitle("oo");
        bgImg = new JLabel(new ImageIcon("src\\main\\resources\\img\\MainBg.jpg"));
        bgImg.setLayout(new BorderLayout());
        // 背景透明
        bgImg.setOpaque(false);
        this.add(bgImg);

        UIManager.put("TabbedPane.contentOpaque", false);
        tabbedPane = new JTabbedPane();
        tabbedPane.setOpaque(false);
        for (int i = 0; i < groupList.size(); i++)
        {
            List<Friends> l = friendsMapper.getFriendsByMyAccAndGroupName((int) myInfo.getAccNumber(), groupList.get(i).getGroupName());
            String[] strs = new String[l.size()];
            for (int j = 0; j < l.size(); j++)
            {
                strs[j] = userMapper.getUserByAccNumber(l.get(j).getFriendsId()).getNickName();
                System.out.println("好友昵称=" + strs[j]);
            }
            jLists[i] = new JList(strs);
            List<User> temp = userMapper.getFriendByAccAndGroupName(this.myInfo.getAccNumber(), groupList.get(i).getGroupName());
            friendsList[i] = temp;
            jLists[i].setModel(new DataModel(temp));
            jLists[i].setCellRenderer(new MyHeadImg(temp));
            jLists[i].setOpaque(false);
            jLists[i].addMouseListener(this);
            tabbedPane.addTab(groupList.get(i).getGroupName(), jLists[i]);
        }
        bgImg.add(tabbedPane);
        // 设置个人信息
        JPanel panelN = new JPanel(new GridLayout(1, 2));

        lblmyInfo = new JLabel(myInfo.getNickName() + "[" + myInfo.getAutoGraph() + "]", new ImageIcon(myInfo.getHead_img()), JLabel.LEFT);
        panelN.setLayout(new GridLayout(2, 2));
        panelN.add(lblmyInfo);
        lblmyInfo.addMouseListener(this);
        JPanel panelS = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JTextField findText = new JTextField(8);
        findText.setOpaque(false);
        JButton findBtn = new JButton(new ImageIcon("src\\main\\resources\\img\\CheckBut.png"));
        findBtn.setFocusPainted(false);
        findBtn.setBorderPainted(false);
        findBtn.setOpaque(false);
        findBtn.setContentAreaFilled(false);

        JButton btn1 = new JButton(new ImageIcon("src\\main\\resources\\img\\starBut.png"));
        btn1.setFocusPainted(false);
        btn1.setBorderPainted(false);
        btn1.setOpaque(false);
        btn1.setContentAreaFilled(false);

        JButton btn2 = new JButton("群聊");
        btn2.addActionListener(e -> openChat(true));

        panelS.add(findBtn);
        panelS.add(btn1);
        panelS.add(btn2);
        panelS.setOpaque(false);
        panelN.add(panelS);
        panelN.setOpaque(false);
        bgImg.add(panelN, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        btnFind = new JButton("查找好友");
        btnFind.addActionListener(e -> new FindUI(myInfo, this));
        btnGroup = new JButton("分组管理");
        btnGroup.addActionListener(e -> group());
        panel.setOpaque(false);
        panel.add(btnGroup);
        panel.add(btnFind);
        bgImg.add(panel, BorderLayout.SOUTH);
        this.setSize(300, 700);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        createMenu();
        new ReceiveThread().start();
        // 上线通知
        SendCmd.sendAll(friendList, myInfo, Cmd.CMD_ONLINE);
        SendCmd.sendAll(userMapper.getAll(), myInfo, Cmd.CMD_AllONLINE);


        // 接收离线消息
        List<Msg> msgList = msgMapper.getMsgByAcc(myInfo.getAccNumber());
        if (msgList.size() != 0)
        {
            JOptionPane.showMessageDialog(null, "有" + msgMapper.getFriendNum(myInfo.getAccNumber()) + "人为你发送了" + msgList.size() + "条消息");
            for (Msg msg : msgList)
            {
                App.context.getBean(Sound.class).play("消息");
                friendInfo = userMapper.getUserByAccNumber(msg.getMyId());
                ChatUI chat = openChat(false);
                try
                {
                    ByteArrayInputStream bais = new ByteArrayInputStream(msg.getContent());
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    SendMsg date = (SendMsg) ois.readObject();
                    chat.appendView(friendInfo.getNickName(), date.doc);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            // 设置为已读
            msgMapper.readMsg(myInfo.getAccNumber());
        }
    }

    class DataModel extends AbstractListModel<Object>
    {
        List<User> data;

        public DataModel(List<User> data)
        {
            this.data = data;
        }

        public Object getElementAt(int index)
        {
            User info = data.get(index);
            String str;
            if (info.getFlag() == 1)
            {
                str = "";
            } else
            {
                str = "[离线]";
            }
            return str + info.getNickName() + "(" + info.getAccNumber() + ")     " + info.getAutoGraph();
        }

        public int getSize()
        {
            return data.size();
        }
    }

    class MyHeadImg extends DefaultListCellRenderer
    {
        List<User> datas;

        public MyHeadImg(List<User> datas)
        {
            this.datas = datas;
        }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                      boolean cellHasFocus)
        {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (index >= 0 && index < datas.size())
            {
                User user = datas.get(index);
                setIcon(new ImageIcon(user.getHead_img()));
                String str;
                if (user.getFlag() == 1)
                {
                    str = "";
                } else
                {
                    str = "[离线]";
                }
                setText(str + user.getNickName() + "(" + user.getAccNumber() + ")       [" + user.getAutoGraph() + "]");
            }

            if (isSelected)
            {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else
            {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setEnabled(list.isEnabled());
            setFont(list.getFont());
            setOpaque(false);
            return this;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if (e.getSource() == lblmyInfo)
        {
            if (e.getClickCount() == 2)
            {
                // 编辑资料
                new MyInfoUI(myInfo, this);
            }
        }
        for (int i = 0; i < jLists.length; i++)
        {
            if (e.getSource() == jLists[i])
            {
                if (jLists[i].getSelectedIndex() >= 0)
                {
                    friendInfo = friendsList[i].get(jLists[i].getSelectedIndex());
                }
                if (e.getButton() == 3)
                {
                    if (jLists[i].getSelectedIndex() >= 0)
                    {
                        popupMenu.show(jLists[i], e.getX(), e.getY());
                    }
                } else if (e.getClickCount() == 2)
                {
                    if (jLists[i].getSelectedIndex() >= 0)
                    {
                        openChat(false);
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e)
    {

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }

    private void createMenu()
    {
        popupMenu = new JPopupMenu();
        int size = groupList.size();
        menuItems = new JMenuItem[size + 2];
        for (int i = 0; i < menuItems.length - 2; i++)
        {
            String groupName = groupList.get(i).getGroupName();
            menuItems[i] = new JMenuItem("移到" + groupName + "组");
            popupMenu.add(menuItems[i]);
            menuItems[i].addActionListener(e -> changGroup(groupName));
        }
        size = menuItems.length;
        menuItems[size - 1] = new JMenuItem("查看好友信息");
        popupMenu.add(menuItems[size - 1]);
        menuItems[size - 2] = new JMenuItem("删除好友");
        popupMenu.add(menuItems[size - 2]);
    }

    private void changGroup(String groupName)
    {
        long myId = myInfo.getAccNumber();
        long friendsId = friendInfo.getAccNumber();
        int groupId = groupMapper.getgroupByNameAndAcc(myInfo.getAccNumber(), groupName).getId();
        friendsMapper.changGroupid(myId, friendsId, groupId);
        flashList(groupMapper.getGroupsByAcc(myInfo.getAccNumber()));
    }

    private void group()
    {
        new GroupUI(myInfo, this).setVisible(true);
    }

    public void flashList(List<Group> groupList)
    {
        //groupList = groupMapper.getGroupsByAcc(myInfo.getAccNumber());
        tabbedPane.removeAll();
        jLists = new JList[groupList.size()];
        friendsList = new List[groupList.size()];
        for (int i = 0; i < groupList.size(); i++)
        {
            List<Friends> l = friendsMapper.getFriendsByMyAccAndGroupName((int) myInfo.getAccNumber(), groupList.get(i).getGroupName());
            String[] strs = new String[l.size()];
            for (int j = 0; j < l.size(); j++)
            {
                strs[j] = userMapper.getUserByAccNumber(l.get(j).getFriendsId()).getNickName();
            }
            jLists[i] = new JList(strs);
            List<User> temp = userMapper.getFriendByAccAndGroupName(this.myInfo.getAccNumber(), groupList.get(i).getGroupName());
            friendsList[i] = temp;
            jLists[i].setModel(new DataModel(temp));
            jLists[i].setCellRenderer(new MyHeadImg(temp));
            jLists[i].setOpaque(false);
            jLists[i].addMouseListener(this);
            tabbedPane.addTab(groupList.get(i).getGroupName(), jLists[i]);
        }
        tabbedPane.repaint();
    }

    public ChatUI openChat(boolean isAll)
    {
        ChatUI chat;
        if (isAll)
        {
            chat = chatWin.get(-1L);
            System.out.println("从map取出chat值");
        } else
        {
            chat = chatWin.get(friendInfo.getAccNumber());
        }
        System.out.println("char=" + chat);
        if (chat == null)
        {
            if (isAll)
            {
                chat = new ChatUI(myInfo, friendInfo, userMapper.getAll(), isAll);
                chatWin.put(-1L, chat);
            } else
            {
                chat = new ChatUI(myInfo, friendInfo, friendList, isAll);
                chatWin.put(friendInfo.getAccNumber(), chat);
            }
        }
        chat.setVisible(true);
        return chat;
    }

    // 接收线程类
    class ReceiveThread extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                while (myInfo.getPort() == 0)
                {
                    Thread.sleep(500);
                }
                DatagramSocket socket = new DatagramSocket(myInfo.getPort());
                while (true)
                {
                    byte b[] = new byte[1024 * 512];
                    DatagramPacket pack = new DatagramPacket(b, 0, b.length);
                    //接收信息
                    socket.receive(pack);
                    ByteArrayInputStream bais = new ByteArrayInputStream(pack.getData());
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    SendMsg msg = (SendMsg) ois.readObject();
                    myInfo = msg.friendInfo;
                    friendInfo = msg.myInfo;
                    System.out.println("接收到命令：" + msg.cmd);
                    switch (msg.cmd)
                    {
                        case Cmd.CMD_ONLINE: //上线
                            App.context.getBean(Sound.class).play("系统提示");
                            flashList(groupMapper.getGroupsByAcc(myInfo.getAccNumber()));
                            new TipUI(friendInfo);
                            break;
                        case Cmd.CMD_OFFLINE:
                            flashList(groupMapper.getGroupsByAcc(myInfo.getAccNumber()));
                            new TipUI(friendInfo);
                            break;
                        case Cmd.CMD_ALL:
                            App.context.getBean(Sound.class).play("消息");
                            ChatUI chatAll = openChat(true);
                            try
                            {
                                // 追加内容到聊天框
                                chatAll.appendView(msg.myInfo.getNickName(), msg.doc);
                            } catch (BadLocationException e)
                            {
                                e.printStackTrace();
                            }
                            break;
                        case Cmd.CMD_YYOK:
                            JOptionPane.showMessageDialog(null, friendInfo.getNickName() + "接收了你的语音通话");
                            break;
                        case Cmd.CMD_YYON:
                            JOptionPane.showMessageDialog(null, friendInfo.getNickName() + "拒绝了你的语音通话");
                            break;
                        case Cmd.CMD_SEND:
                            //聊天消息
                            App.context.getBean(Sound.class).play("消息");
                            ChatUI chat = openChat(false);
                            try
                            {
                                // 追加内容到聊天框
                                chat.appendView(msg.myInfo.getNickName(), msg.doc);
                            } catch (BadLocationException e)
                            {
                                e.printStackTrace();
                            }
                            break;
                        case Cmd.CMD_SHAKE:
                            App.context.getBean(Sound.class).play("抖动");
                            chat = openChat(false);
                            chat.shake();
                            break;
                        case Cmd.CMD_YY:
                            // 请求语音
                            App.context.getBean(Sound.class).play("语音");
                            SendMsg msg2 = new SendMsg();
                            if (JOptionPane.showConfirmDialog(null, friendInfo.getNickName() + "请求与你语音通话，是否同意？", "语音通话", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
                            {
                                chat = openChat(false);
                                msg2.cmd = Cmd.CMD_YYOK;
                                JTextPane jTextPane = new JTextPane();
                                jTextPane.setText("正在和" + friendInfo.getNickName() + "语音通话。。。");
                                StyledDocument vdoc = jTextPane.getStyledDocument();
                                chat.appendView(msg.myInfo.getNickName(), vdoc);
                                new Client(myInfo.getAccNumber());
                            } else
                            {
                                msg2.cmd = Cmd.CMD_YYON;
                            }
                            msg2.myInfo = myInfo;
                            msg2.friendInfo = friendInfo;
                            SendCmd.send(msg2);
                            //flashList(groupMapper.getGroupsByAcc(myInfo.getAccNumber()));
                            break;
                        case Cmd.CMD_AllONLINE:
                            if (chatWin.get(-1L) != null)
                            {
                                chatWin.get(-1L).flash();
                            }
                            break;
                        case Cmd.CMD_ADDFRI:
                            //添加好友
                            App.context.getBean(Sound.class).play("系统提示");
                            String str = "【" + friendInfo.getNickName() + "】请求添加你为好友，是否同意？";
                            SendMsg msg3 = new SendMsg();
                            if (JOptionPane.showConfirmDialog(null, str, "添加好友", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
                            {
                                msg3.cmd = Cmd.CMD_ARGEE;
                                // 添加好友
                                Friends friends = new Friends();
                                friends.setMyId((int) myInfo.getAccNumber());
                                friends.setFriendsId((int) friendInfo.getAccNumber());
                                int id = groupMapper.getGroupsByAcc(myInfo.getAccNumber()).get(0).getId();
                                friends.setGroupId(id);
                                friendsMapper.saveFriends(friends);
                                flashList(groupMapper.getGroupsByAcc(myInfo.getAccNumber()));
                            } else
                            {
                                msg3.cmd = Cmd.CMD_REFUSE;
                            }
                            msg3.myInfo = myInfo;
                            msg3.friendInfo = friendInfo;
                            SendCmd.send(msg3);
                            break;
                        case Cmd.CMD_ARGEE:
                            Friends friends = new Friends();
                            friends.setMyId((int) myInfo.getAccNumber());
                            friends.setFriendsId((int) friendInfo.getAccNumber());
                            int id = groupMapper.getGroupsByAcc(myInfo.getAccNumber()).get(0).getId();
                            friends.setGroupId(id);
                            friendsMapper.saveFriends(friends);
                            flashList(groupMapper.getGroupsByAcc(myInfo.getAccNumber()));
                            break;
                        case Cmd.CMD_REFUSE:
                            str = "【" + friendInfo.getNickName() + "】拒绝了你的好友请求。";
                            JOptionPane.showMessageDialog(null, str);
                            break;
                        case Cmd.CMD_DELFRIEND:
                            flashList(groupMapper.getGroupsByAcc(myInfo.getAccNumber()));
                            break;
                        case Cmd.CMD_FILE:
                            App.context.getBean(Sound.class).play("系统提示");
                            str = friendInfo.getNickName() + "发送了一个【" + msg.fileName + "文件】，是否接收？";
                            if (JOptionPane.showConfirmDialog(null, "是否接收文件", "接收文件", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
                            {
                                JFileChooser chooser = new JFileChooser(" ");
                                chooser.setDialogType(JFileChooser.SAVE_DIALOG);
                                chooser.setDialogTitle("保存文件");
                                if (chooser.showOpenDialog(null) == chooser.APPROVE_OPTION)
                                {
                                    String ext = msg.fileName.substring(msg.fileName.indexOf('.'), msg.fileName.length());
                                    String filename = chooser.getSelectedFile().getAbsolutePath() + ext;
                                    FileOutputStream fos = new FileOutputStream(filename);
                                    fos.write(msg.b);
                                    fos.flush();
                                    fos.close();
                                }
                            }
                    }
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
