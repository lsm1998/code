/*
 * 作者：刘时明
 * 时间：2019/12/21-0:09
 * 作用：
 */
package program;

import javax.swing.*;

public class TcpClient extends JFrame
{
    private JLabel ipLabel;
    private JLabel portLabel;
    private JTextField ipText;
    private JTextField portText;
    private JTextField sendText;
    private JTextArea dataText;
    private JButton sendButton;
    private JButton connectButton;

    public TcpClient()
    {
        initComponent();
        initUI();
    }

    private void initComponent()
    {
        ipLabel = new JLabel("IP地址=");
        portLabel=new JLabel("端口=");
        sendText = new JTextField();
        ipText = new JTextField();
        portText = new JTextField();
        dataText = new JTextArea();
        sendButton = new JButton("发送");
        connectButton = new JButton("连接");
    }

    private void initUI()
    {
        this.setSize(500, 400);
        this.setTitle("TCP连接客户端");
        this.setLayout(null);
        ipLabel.setBounds(20,20,50,20);
        this.add(ipLabel);
        sendText.setBounds(70, 20, 150, 20);
        this.add(sendText);
        portLabel.setBounds(230,20,50,20);
        this.add(portLabel);
        ipText.setBounds(280, 20, 150, 20);
        this.add(ipText);
        portText.setBounds(20, 140, 50, 20);
        this.add(portText);
        dataText.setBounds(20, 200, 50, 20);
        this.add(dataText);
        sendButton.setBounds(20, 260, 50, 20);
        this.add(sendButton);
        connectButton.setBounds(220, 260, 50, 20);
        this.add(connectButton);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args)
    {
        new TcpClient();
    }
}
