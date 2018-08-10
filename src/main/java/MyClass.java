import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class MyClass extends JFrame {
    JLabel label;
    public MyClass() throws IOException {
        setLayout(new BorderLayout(0, 0));

        JPanel ipPanel = new JPanel(new BorderLayout(5, 5));
        final JTextField ipField = new JTextField();
        ipField.setText("192.168.199.110");
        ipPanel.add(ipField, BorderLayout.CENTER);
        ipPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel portPanel = new JPanel(new BorderLayout(5, 5));
        final JTextField portField = new JTextField();
        portPanel.add(portField, BorderLayout.CENTER);
        portPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel btnPanel = new JPanel(new BorderLayout(5, 5));
        JButton btn = new JButton("链接");
        btnPanel.add(btn, BorderLayout.CENTER);


        JPanel panelContainer = new JPanel(new BorderLayout());
        panelContainer.add(ipPanel, BorderLayout.NORTH);
        panelContainer.add(portPanel, BorderLayout.CENTER);
        panelContainer.add(btnPanel, BorderLayout.SOUTH);


        JPanel panelContainer2 = new JPanel(new BorderLayout());
        panelContainer2.add(panelContainer, BorderLayout.NORTH);

        label = new JLabel();

//        Image image = ImageIO.read(new File("/Users/wanjian/Desktop/img.jpg"));
//        label.setIcon(new ImageIcon(image));
        label.setBorder(new EmptyBorder(5, 5, 5, 5));
        add(panelContainer2, BorderLayout.NORTH);

        add(label, BorderLayout.CENTER);

        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setBounds(360, 20, 350, 600);

        setTitle("屏幕共享 by 万剑");
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    read(ipField.getText(), portField.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        });


//        label.addMouseListener(new MouseAdapter() {
//        });
    }

    private void read(final String ip, final String port) throws IOException {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Socket socket = new Socket(ip, Integer.parseInt(port));
                    BufferedInputStream inputStream = new BufferedInputStream(socket.getInputStream());
                    byte[] bytes = null;
                    while (true) {
                        long s1=System.currentTimeMillis();
                        int version=inputStream.read();
                        if (version==-1){
                            return;
                        }
                        int length = readInt(inputStream);
                        if (bytes == null) {
                            bytes = new byte[length];
                        }
                        if (bytes.length < length) {
                            bytes = new byte[length];
                        }
                        int read = 0;
                        while ((read < length)) {
                            read += inputStream.read(bytes, read, length - read);
                        }
                        InputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                        long s2=System.currentTimeMillis();
                        Image image = ImageIO.read(byteArrayInputStream);
                        label.setIcon(new ImageIcon(image));
                        long s3=System.currentTimeMillis();

                        System.out.println("读取: "+(s2-s1)+"    解码: "+(s3-s2)+"  "+length);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private int readInt(InputStream inputStream) throws IOException {
        int b1 = inputStream.read();
        int b2 = inputStream.read();
        int b3 = inputStream.read();
        int b4 = inputStream.read();

        return (b1 << 24) | (b2 << 16) | (b3 << 8) | b4;
    }

    public static void main(String[] args) throws IOException {
        new MyClass().setVisible(true);

    }

}
