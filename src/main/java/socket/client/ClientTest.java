package socket.client;

import msg.Aggrement;
import msg.SignImage;
import msg.SocketMessage;
import utils.FileUtils;

import java.io.File;
import java.util.Date;
import java.util.UUID;

public class ClientTest {
    public static void main(String args[]) {
        TcpClient client = new TcpClientImpl();
        client.connect("192.168.199.110",10000);
//        while(true) {
//            try {
//                client.getTransceiver().send(mockMessage());
//                Thread.sleep(2000);
//            }catch (Exception e) {
//
//            }
//        }
    }


    public static SocketMessage mockMessage() {
        SocketMessage message = new SocketMessage();
        message.setMsgId("服务端消息");
        message.setMsgType("2");
        message.setMsgMd5("3");
        message.setMsgTimestamp(new Date().toString());
        SignImage signImage = new SignImage();
        signImage.setImgType(1);
        signImage.setImgName("11111.jpg");
        signImage.setImgContent(FileUtils.coverFileToString(new File("/APP/image/2.jpg")));
        message.setData(signImage);
        return message;
    }
}
