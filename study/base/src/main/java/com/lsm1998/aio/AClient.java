package com.lsm1998.aio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 异步IO客户端
 *
 */
public class AClient {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 30001;

    /**
     * 与服务器端通信的异步Channel
     */
    private AsynchronousSocketChannel clientChannel;

    private JFrame mainWin = new JFrame("多人聊天");
    private JTextArea textArea = new JTextArea(16, 48);
    private JTextField textField = new JTextField(40);
    private JButton sendBtn = new JButton("发送");

    private void init() {
        mainWin.setLayout(new BorderLayout());
        textArea.setEditable(false);
        mainWin.add(new JScrollPane(textArea), BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.add(textField);
        panel.add(sendBtn);

        Action sendAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String content = textField.getText();
                if (content.trim().length() > 0) {
                    try {
                        clientChannel.write(ByteBuffer.wrap(content.trim().getBytes("UTF-8"))).get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                textField.setText("");
            }
        };

        sendBtn.addActionListener(sendAction);

        //将"Ctrl + Enter"键和"send"关联
        textField.getInputMap().put(KeyStroke.getKeyStroke('\n', java.awt.event.InputEvent.CTRL_MASK), "send");
        //将"send"和sendAction关联
        textField.getActionMap().put("send", sendAction);

        mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWin.add(panel, BorderLayout.SOUTH);
        mainWin.pack();
        mainWin.setVisible(true);
    }

    private void connect() {
        try {
            final ByteBuffer buffer = ByteBuffer.allocate(1024);
            ExecutorService executor = Executors.newFixedThreadPool(80);
            AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.withThreadPool(executor);
            clientChannel = AsynchronousSocketChannel.open(channelGroup);
            clientChannel.connect(new InetSocketAddress(HOST, PORT)).get();

            textArea.append("---与服务器连接成功---\n");

            buffer.clear();
            clientChannel.read(buffer, null, new CompletionHandler<Integer, Object>() {
                @Override
                public void completed(Integer result, Object attachment) {
                    buffer.flip();
                    String content = StandardCharsets.UTF_8.decode(buffer).toString();
                    //显示从服务器读取的数据
                    textArea.append("某人说:" + content + "\n");
                    buffer.clear();
                    clientChannel.read(buffer, null, this);
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println("读取数据失败: " + exc);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AClient client = new AClient();
        client.init();
        client.connect();
    }
}
