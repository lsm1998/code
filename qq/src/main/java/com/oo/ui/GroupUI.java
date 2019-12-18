package com.oo.ui;

import com.oo.domain.Group;
import com.oo.domain.User;
import com.oo.mapper.GroupMapper;
import com.oo.server.App;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * 作者：刘时明
 * 日期：2018/9/30 0030
 * 时间：21:02
 * 说明：分组管理页面
 */
public class GroupUI extends JFrame
{
    private User user;
    private JButton add, update, delete;
    private JList jList;
    private GroupMapper groupMapper = App.context.getBean(GroupMapper.class);
    private MainUI mainUI;

    public GroupUI(User user, MainUI mainUI)
    {
        this.user = user;
        this.mainUI = mainUI;
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        init();
    }

    private void init()
    {
        List<Group> groups = groupMapper.getGroupsByAcc(user.getAccNumber());
        add = new JButton("新增分组");
        add.addActionListener(e ->
        {
            String str = JOptionPane.showInputDialog("输入分组名称：");
            if (str == null)
                return;
            for (Group g : groups)
            {
                if (str.trim().equals(g.getGroupName().trim()))
                {
                    JOptionPane.showMessageDialog(null, "该分组已经存在了！");
                    return;
                }
            }
            Group group = new Group();
            group.setAccNumber(user.getAccNumber());
            group.setGroupName(str);
            groupMapper.saveGroup(group);
            flash(groupMapper.getGroupsByAcc(user.getAccNumber()));
            mainUI.flashList(groupMapper.getGroupsByAcc(user.getAccNumber()));
        });
        update = new JButton("修改分组");
        update.addActionListener(e ->
        {
            String str = JOptionPane.showInputDialog("输入修改方案，格式：原分组-新分组：");
            if (str == null)
                return;
            String[] strs = str.split("-");
            if (strs.length != 2)
            {
                JOptionPane.showMessageDialog(null, "格式有误！");
                return;
            }
            Group group = groupMapper.getgroupByNameAndAcc(user.getAccNumber(), strs[1]);
            if (group != null)
            {
                JOptionPane.showMessageDialog(null, "新分组已经存在！");
                return;
            }
            group = groupMapper.getgroupByNameAndAcc(user.getAccNumber(), strs[0]);
            if (group == null)
            {
                JOptionPane.showMessageDialog(null, "原分组不存在！");
                return;
            }
            group.setGroupName(strs[1]);
            groupMapper.updateGroup(group);
            flash(groupMapper.getGroupsByAcc(user.getAccNumber()));
            mainUI.flashList(groupMapper.getGroupsByAcc(user.getAccNumber()));
        });
        delete = new JButton("删除分组");
        delete.addActionListener(e ->
        {
            if (groupMapper.getGroupsByAcc(user.getAccNumber()).size() == 1)
            {
                JOptionPane.showMessageDialog(null, "至少保留一个分组！");
                return;
            }
            if (groupMapper.getGroupsByAcc(user.getAccNumber()).size() == 5)
            {
                JOptionPane.showMessageDialog(null, "最多拥有五个分组！");
                return;
            }
            String str = JOptionPane.showInputDialog("输入分组名称：");
            if (str == null)
                return;
            Group group = groupMapper.getgroupByNameAndAcc(user.getAccNumber(), str);
            if (group == null)
            {
                JOptionPane.showMessageDialog(null, "该分组不存在！");
                return;
            }
            int result = groupMapper.getCountByByGroupId(user.getAccNumber(), group.getId());
            if (result > 0)
            {
                JOptionPane.showMessageDialog(null, "该分组内存在好友！");
            } else
            {
                groupMapper.deleteGroup(group.getId());
            }
            flash(groupMapper.getGroupsByAcc(user.getAccNumber()));
            mainUI.flashList(groupMapper.getGroupsByAcc(user.getAccNumber()));
        });
        JPanel panelS = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelS.add(add);
        panelS.add(update);
        panelS.add(delete);
        this.add(panelS, BorderLayout.SOUTH);
        jList = new JList();
        jList.setModel(new DataModel(groups));
        jList.setEnabled(false);
        this.add(jList);
    }

    class DataModel extends AbstractListModel<Object>
    {
        List<Group> data;

        public DataModel(List<Group> data)
        {
            this.data = data;
        }


        public Object getElementAt(int index)
        {
            Group info = data.get(index);
            return "分组名称:" + info.getGroupName() + "-好友数量:(" + groupMapper.getCountByByGroupId(user.getAccNumber(), info.getId()) + ")";
        }

        public int getSize()
        {
            return data.size();
        }
    }

    private void flash(List<Group> groups)
    {
        jList.setModel(new DataModel(groups));
        jList.repaint();
    }
}
