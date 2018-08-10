import com.alibaba.fastjson.JSON;
import msg.SocketMessage;
import socket.Message;
import socket.server.SocketTransceiver;
import socket.server.TcpServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.net.ServerSocket;

public class SocketServer{
    private JTextField ip;
    private JTextField port;
    private JButton serverBtn;
    private JComboBox comboBox1;
    private JTextArea textArea1;
    private JButton sendBtn;
    private JLabel num;
    private JPanel panel1;

    public SocketServer() {
        ip.setText(getIp());
        serverBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
//                    socket = new FormSocket(Integer.parseInt(port.getText()));
//                    socket.start();
                    JOptionPane.showMessageDialog(null, "服务已启动", "提示", JOptionPane.INFORMATION_MESSAGE);
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        sendBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    Message message = JSON.parseObject(textArea1.getText(), Message.class);
//                    socket.getClient().send(message);
                }catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "发送失败", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(new SocketServer().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650,500);
        frame.setPreferredSize(new Dimension(650,500));
        frame.pack();
        frame.setVisible(true);
    }

    // 获取本机IP
    public String getIp() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostAddress().toString();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    2


    //
    public void loadCache() {

    }
}
