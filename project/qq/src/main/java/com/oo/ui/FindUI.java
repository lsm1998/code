package com.oo.ui;

import com.oo.domain.Friends;
import com.oo.domain.User;
import com.oo.mapper.FriendsMapper;
import com.oo.mapper.GroupMapper;
import com.oo.mapper.UserMapper;
import com.oo.server.App;
import com.oo.util.Cmd;
import com.oo.util.SendCmd;
import com.oo.util.SendMsg;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * 作者：刘时明
 * 日期：2018/10/4
 * 时间：11:42
 * 说明：
 */
public class FindUI extends JFrame implements ActionListener, MouseListener
{
    private MainUI mainUI;
    private JLabel lblQQcode, lblNickName, lblAge;
    private JTextField txtQQcode, txtNickName, txtAge;
    private JComboBox<Object> cbSex, cbStatus;
    private JTable dataTable;
    private JButton btnFind, btnAdd, btnClose;
    private User myInfo;
    private Vector<String> vHead;
    private Vector<Vector<Object>> vData;
    private String sSex[] = {"不选择", "男", "女"};
    private String sStatus[] = {"不选择", "在线", "离线"};
    private Map<String, String> map = new HashMap<>();
    private List<User> list;
    private UserMapper userMapper = App.context.getBean(UserMapper.class);
    private FriendsMapper friendsMapper = App.context.getBean(FriendsMapper.class);
    //private GroupMapper groupMapper = App.context.getBean(GroupMapper.class);

    public FindUI(User user,MainUI mainUI)
    {
        super("查找好友");
        this.myInfo = user;
        this.mainUI=mainUI;
        init();
    }

    public void init()
    {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lblQQcode = new JLabel("QQ账号");
        lblNickName = new JLabel("昵称");
        lblAge = new JLabel("年龄");
        txtQQcode = new JTextField(8);
        txtNickName = new JTextField(8);
        txtAge = new JTextField(5);
        cbSex = new JComboBox<>(sSex);
        cbStatus = new JComboBox<>(sStatus);
        btnFind = new JButton("查找");
        topPanel.add(lblQQcode);
        topPanel.add(txtQQcode);
        topPanel.add(lblNickName);
        topPanel.add(txtNickName);
        topPanel.add(lblAge);
        topPanel.add(txtAge);
        topPanel.add(cbSex);
        topPanel.add(cbStatus);
        topPanel.add(btnFind);
        add(topPanel, BorderLayout.NORTH);
        String[] columnNames = {"头像", "账号", "昵称", "年龄", "性别", "状态", "签名"};
        vHead = new Vector<>();
        for (int i = 0; i < columnNames.length; i++)
        {
            vHead.addElement(columnNames[i]);
        }
        list = userMapper.findUser(map);
        map.clear();
        vData = new Vector<>();
        System.out.println("集合赋值");
        for (int i = 0; i < list.size(); i++)
        {
            User user = list.get(i);
            //{"头像", "账号", "昵称", "年龄", "性别", "状态", "签名"}
            vData.add(new Vector<>());
            vData.get(i).add(user.getHead_img());
            vData.get(i).add(user.getAccNumber());
            vData.get(i).add(user.getNickName());
            vData.get(i).add(user.getAge());
            vData.get(i).add(user.getSex());
            vData.get(i).add(user.getFlag());
            vData.get(i).add(user.getAutoGraph());
        }
        dataTable = new JTable(new MyTableModel(vHead, vData));
        dataTable.addMouseListener(this);
        dataTable.setRowHeight(60);
        add(new JScrollPane(dataTable));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAdd = new JButton("添加好友");
        btnClose = new JButton("关闭窗口");
        bottomPanel.add(btnAdd);
        bottomPanel.add(btnClose);
        add(bottomPanel, BorderLayout.SOUTH);
        btnFind.addActionListener(this);
        btnAdd.addActionListener(this);
        btnClose.addActionListener(this);
        setSize(800, 500);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    class MyTableModel extends AbstractTableModel
    {
        Vector<String> vHead;
        Vector<Vector<Object>> data;

        public MyTableModel(Vector<String> vHead, Vector<Vector<Object>> vData)
        {
            this.vHead = vHead;
            this.data = vData;
        }

        public int getColumnCount()
        {
            return vHead.size();
        }

        public int getRowCount()
        {
            return data.size();
        }

        public String getColumnName(int col)
        {
            return vHead.get(col);
        }

        public Object getValueAt(int row, int col)
        {
            Vector<Object> rowData = vData.get(row);
            if (col == 0)
            {
                return new ImageIcon((byte[]) rowData.get(col));
            } else
            {
                return rowData.get(col);
            }
        }

        @Override
        public Class<?> getColumnClass(int c)
        {
            if (c == 0)
            {
                return ImageIcon.class;
            } else
            {
                return super.getColumnClass(c);
            }
        }

        public boolean isCellEditable(int row, int col)
        {
            return false;
        }

        public void setValueAt(Object value, int row, int col)
        {
            Vector<Object> rowData = vData.get(row);
            rowData.set(col, value);
            fireTableCellUpdated(row, col);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnFind)
        {
            String accNumber = txtQQcode.getText().trim();
            String nickname = txtNickName.getText().trim();
            String age = txtAge.getText().trim();
            if (!accNumber.equals(""))
            {
                map.put("accNumber", accNumber);
            }
            if (!nickname.equals(""))
            {
                map.put("nickname", nickname);
            }
            if (!age.equals(""))
            {
                map.put("age", age);
            }
            String sex = sSex[cbSex.getSelectedIndex()];
            String flag = sStatus[cbStatus.getSelectedIndex()];
            if (!sex.equals("不选择"))
            {
                map.put("sex", sex);
            }
            if (!flag.equals("不选择"))
            {
                map.put("flag", flag.equals("在线") ? "1" : "0");
            }
            list = userMapper.findUser(map);
            map.clear();
            vData = new Vector<>();
            System.out.println("集合赋值");
            for (int i = 0; i < list.size(); i++)
            {
                User user = list.get(i);
                vData.add(new Vector<>());
                vData.get(i).add(user.getHead_img());
                vData.get(i).add(user.getAccNumber());
                vData.get(i).add(user.getNickName());
                vData.get(i).add(user.getAge());
                vData.get(i).add(user.getSex());
                vData.get(i).add(user.getFlag());
                vData.get(i).add(user.getAutoGraph());
            }
            dataTable.setModel(new MyTableModel(vHead, vData));
        } else if (e.getSource() == btnAdd)
        {
            int index = dataTable.getSelectedRow();
            if (index >= 0)
            {
                Vector<Object> row = vData.get(index);
                long acc = Integer.parseInt(row.get(1).toString());
                User friendInfo = userMapper.getUserByAccNumber(acc);
                String str = "是否添加" + friendInfo.getNickName() + "为你的好友?";
                if (JOptionPane.showConfirmDialog(this, str, "添加好友",
                        JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
                {
                    SendMsg msg = new SendMsg();
                    msg.cmd = Cmd.CMD_ADDFRI;
                    msg.myInfo = myInfo;
                    msg.friendInfo = friendInfo;
                    SendCmd.send(msg);
                }
            }
        } else if (e.getSource() == btnClose)
        {
            dispose();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if (e.getSource() == dataTable)
        {
            int index = dataTable.getSelectedRow();
            if (index >= 0)
            {
                if (e.getClickCount() == 2)
                {
                    Vector<Object> row = vData.get(index);
                    long acc = Integer.parseInt(row.get(1).toString());
                    if (acc == myInfo.getAccNumber())
                    {
                        JOptionPane.showMessageDialog(this, "不能添加自己为好友!");
                        return;
                    }
                    if (friendsMapper.isFriends(myInfo.getAccNumber(), acc) != null)
                    {
                        JOptionPane.showMessageDialog(this, "你们已经是好友了");
                        return;
                    }
                    User friendInfo = userMapper.getUserByAccNumber(acc);
                    String str = "是否添加" + friendInfo.getNickName() + "为你的好友?";
                    if (JOptionPane.showConfirmDialog(this, str, "好友添加",
                            JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
                    {
                        SendMsg msg = new SendMsg();
                        msg.cmd = Cmd.CMD_ADDFRI;
                        msg.myInfo = myInfo;
                        msg.friendInfo = friendInfo;
                        SendCmd.send(msg);
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
}
