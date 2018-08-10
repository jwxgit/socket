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
                System.out.println("控制台输入字符串开始,输入msgType发送对应消息");
                System.out.println("1.SERVER_PUSH_IMAGE     2.SERVER_COMPLETE      3.SERVER_DO_SIGNATURE");
                System.out.println("4.SERVER_SIGNATURE_CANCEL     5.SERVER_SIGNATURE_START");

                Scanner input =new Scanner(System.in);
                String instr = input.nextLine();
                if(tcp.getTransceiver() == null) {
                    System.out.println("当前无客户端连接......");
                    continue;
                }
                SocketMessage message = null;
                if(instr.equals("1")) {
                    message = mockSERVER_PUSH_IMAGE();
                } else if(instr.equals("2")){
                    message = mockSERVER_COMPLETE();
                }else if(instr.equals("3")){
                    message = mockSERVER_DO_SIGNATURE();
                }else if(instr.equals("4")){
                    message = mockSERVER_SIGNATURE_CANCEL();
                }else if(instr.equals("5")){
                    message = mockSERVER_SIGNATURE_START();
                }
                System.out.println("当前发送消息：" + JSON.toJSONString(message));
                tcp.getTransceiver().send(message);
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
                "      \"01-kajiemiao_1\",\n" +
                "      \"02-yigan-A5_1\"\n" +
                "    ],\n" +
                "    \"signTypes\": [\n" +
                "      1,\n" +
                "      2,\n" +
                "      3\n" +
                "    ]\n" +
                "  }\n" +
                "}";

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
        SocketMessage message = JSON.parseObject(json, SocketMessage.class);
        message.setData(JSON.toJavaObject((JSONObject)message.getData(), ImageInfo.class));
        ImageInfo imageInfo = (ImageInfo)message.getData();
        imageInfo.setImgContent(FileUtils.coverFileToString(new File("assets/01-kajiemiao_1.jpg")));
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
        SocketMessage message = JSON.parseObject(json, SocketMessage.class);
        message.setData(JSON.toJavaObject((JSONObject)message.getData(), Completion.class));
        return message;
    }


}
