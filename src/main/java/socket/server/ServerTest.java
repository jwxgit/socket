package socket.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import msg.*;
import socket.Message;
import utils.FileUtils;
import utils.MessageType;

import java.io.File;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

public class ServerTest {
    public static void main(String[] args) {
        TcpServerImpl tcp = new TcpServerImpl(10000);
        tcp.start();
        while(true) {
            try {
                System.out.println("控制台输入字符串开始,输入1发送消息");
                Scanner input =new Scanner(System.in);
                String instr = input.nextLine();
                if(instr.equals(MessageType.SERVER_PUSH_IMAGE)) {
                    tcp.getTransceiver().send(mockSERVER_PUSH_IMAGE());
                } else if(instr.equals(MessageType.SERVER_COMPLETE)){
                    tcp.getTransceiver().send(mockSERVER_COMPLETE());
                }else if(instr.equals(MessageType.SERVER_DO_SIGNATURE)){
                    tcp.getTransceiver().send(mockSERVER_SIGNATURE_START());
                }else if(instr.equals(MessageType.SERVER_SIGNATURE_CANCEL)){
                    tcp.getTransceiver().send(mockSERVER_SIGNATURE_CANCEL());
                }else if(instr.equals(MessageType.SERVER_SIGNATURE_START)){
                    tcp.getTransceiver().send(mockSERVER_SIGNATURE_START());
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static SocketMessage mockMessage() {
        SocketMessage message = new SocketMessage();
        message.setMsgId("服务端消息");
        message.setMsgType(MessageType.SERVER_PUSH_IMAGE);
        message.setMsgMd5("3");
        message.setMsgTimestamp(new Date().toString());
        SignImage signImage = new SignImage();
        signImage.setImgType(1);
        signImage.setImgName("11111.jpg");
        signImage.setImgContent(FileUtils.coverFileToString(new File("/APP/image/2.jpg")));
        message.setData(signImage);
        return message;
    }

    // 跳转签核页面指令
    public static SocketMessage mockSERVER_SIGNATURE_START() {
        String json = "{\n" +
                "  \"msgId\": \"1\",\n" +
                "  \"msgType\": \"SERVER_SIGNATURE_START\",\n" +
                "  \"msgMd5\": \"MD5\",\n" +
                "  \"msgTimestamp\": \"2018-08-02 13:37:36.235\",\n" +
                "  \"data\": {\n" +
                "    \"workbenchId\": \"1\",\n" +
                "    \"workbenName\": \"1号登记台\",\n" +
                "    \"chilCardNo\": \"23123213\",\n" +
                "    \"chilCode\": \"350321199902128889\",\n" +
                "    \"chilSex\": \"男\",\n" +
                "    \"chilName\": \"张三\",\n" +
                "    \"displayNum\": \"23123213\",\n" +
                "    \"inoculations\": [\n" +
                "      {\n" +
                "        \"inocBactCode\": \"0101\",\n" +
                "        \"inocBactName\": \"卡介苗\",\n" +
                "        \"inocNurse\": \"张医生\",\n" +
                "        \"inocBatchNo\": \"20150708-1\",\n" +
                "        \"inocCorpName\": \"武汉生物\",\n" +
                "        \"inocValiddate\": \"2019-02-13\",\n" +
                "        \"inocModel\": \"JFADFJLJALKSDJF\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"inocBactCode\": \"0201\",\n" +
                "        \"inocBactName\": \"乙肝CHO\",\n" +
                "        \"inocNurse\": \"张医生\",\n" +
                "        \"inocBatchNo\": \"20150708-1\",\n" +
                "        \"inocCorpName\": \"武汉生物\",\n" +
                "        \"inocValiddate\": \"2019-02-13\",\n" +
                "        \"inocModel\": \"JFADFJLJALKSDJF\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"heathQuestion\": [\n" +
                "      \"JFADFJLJALKSDJF\",\n" +
                "      \"JFADFJLJALKSDJF\"\n" +
                "    ],\n" +
                "    \"signTypes\": [\n" +
                "      1,\n" +
                "      2,\n" +
                "      3\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        SocketMessage socketMessage = new SocketMessage();
        SocketMessage message = JSON.parseObject(json, SocketMessage.class);
        message.setData(JSON.toJavaObject((JSONObject)message.getData(),ChildInfo.class));
        return message;
    }

    // 推送图片
    public static SocketMessage mockSERVER_PUSH_IMAGE() {
        String json = "{\n" +
                "  \"msgId\": \"2\",\n" +
                "  \"msgType\": \"SERVER_PUSH_IMAGE\",\n" +
                "  \"msgMd5\": \"MD5\",\n" +
                "  \"msgTimestamp\": \"2018-08-02 13:37:36.235\",\n" +
                "  \"data\": {\n" +
                "    \"chilCode\": \"儿童编码\",\n" +
                "    \"imgId\": \"JFADFJLJALKSDJF\",\n" +
                "    \"imgName\": \"JFADFJLJALKSDJF.png\",\n" +
                "    \"imgconent\": \"Base64\"\n" +
                "  }\n" +
                "}";
        SocketMessage socketMessage = new SocketMessage();
        SocketMessage message = JSON.parseObject(json, SocketMessage.class);
        message.setData(JSON.toJavaObject((JSONObject)message.getData(), ImageInfo.class));
        return message;
    }

    // 发送指令
    public static SocketMessage mockSERVER_DO_SIGNATURE() {
        String json = "{\n" +
                "  \"msgId\": \"2\",\n" +
                "  \"msgType\": \"SERVER_SIGNATURE_START\",\n" +
                "  \"msgMd5\": \"MD5\",\n" +
                "  \"msgTimestamp\": \"2018-08-02 13:37:36.235\",\n" +
                "  \"data\": {\n" +
                "    \"chilCode\": \"儿童编码\",\n" +
                "    \"operation\": \"010\"\n" +
                "  }\n" +
                "}";
        SocketMessage socketMessage = new SocketMessage();
        SocketMessage message = JSON.parseObject(json, SocketMessage.class);
        message.setData(JSON.toJavaObject((JSONObject)message.getData(), Command.class));
        return message;
    }

    // 取消指令
    public static SocketMessage mockSERVER_SIGNATURE_CANCEL() {
        String json = "{\n" +
                "  \"msgId\": \"2\",\n" +
                "  \"msgType\": \"SERVER_SIGNATURE_START\",\n" +
                "  \"msgMd5\": \"MD5\",\n" +
                "  \"msgTimestamp\": \"2018-08-02 13:37:36.235\",\n" +
                "  \"data\":null" +
                "}";
        SocketMessage socketMessage = new SocketMessage();
        SocketMessage message = JSON.parseObject(json, SocketMessage.class);
        message.setData(JSON.toJavaObject((JSONObject)message.getData(), Command.class));
        return message;
    }

    // 推送图片
    public static SocketMessage mockSERVER_COMPLETE() {
        String json = "{\n" +
                "  \"msgId\": \"2\",\n" +
                "  \"msgType\": \"SERVER_SIGNATURE_START\",\n" +
                "  \"msgMd5\": \"MD5\",\n" +
                "  \"msgTimestamp\": \"2018-08-02 13:37:36.235\",\n" +
                "  \"data\": {\n" +
                "    \"chilCode\": \"儿童编码\",\n" +
                "    \"complete\": 1\n" +
                "  }\n" +
                "}";
        SocketMessage socketMessage = new SocketMessage();
        SocketMessage message = JSON.parseObject(json, SocketMessage.class);
        message.setData(JSON.toJavaObject((JSONObject)message.getData(), Completion.class));
        return message;
    }


}
