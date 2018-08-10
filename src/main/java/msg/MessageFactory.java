package msg;


import java.util.Date;
import java.util.UUID;

/**
 * Created by ZHANGZHIYU on 2018/8/7.
 */

public class MessageFactory {
   public enum  MsgTypeEnum {
       CLIENT_AGREEMENT("CLIENT_AGREEMENT", "用户同意/不同意"),
       CLIENT_GET_CONSENT_FORM("CLIENT_GET_CONSENT_FORM", "获取知情同意书"),
       CLIENT_UPLOAD_IMG("CLIENT_UPLOAD_IMG", "客户端向服务端上传签核图片"),
       CLIENT_HEARTBEAT("CLIENT_HEARTBEAT", "心跳连接服务端-PAD"),

       SERVER_SIGNATURE_CANCEL("SERVER_SIGNATURE_CANCEL", "终止消息服务端-PAD"),
       SERVER_COMPLETE("SERVER_COMPLETE", "签核完成服务端-PAD"),
       SERVER_IMG("SERVER_IMG", "服务端向客户端发送图片"),
       SERVER_DO_SIGNATURE("SERVER_DO_SIGNATURE", "服务端向客户端发送签核指令"),
       SERVER_SIGNATURE_START("SERVER_SIGNATURE_START", "开始签核信息");

       MsgTypeEnum(String msgType, String desc) {
           this.msgType = msgType;
           this.desc = desc;
       }

       private String msgType;
       private String desc;

       public String getMsgType() {
           return msgType;
       }

       public String getDesc() {
           return desc;
       }
   }

    public  static SocketMessage  getMessage(MsgTypeEnum msgType ,Object  data){
        SocketMessage  message = getModel(msgType);
        message.setData(data);
        //判断消息体是否正确
 /*       switch (msgType){
            case  CLIENT_AGREEMENT:
                break;
            case  CLIENT_GET_CONSENT_FORM:
                break;
            case  CLIENT_UPLOAD_IMG:
                break;
            case  CLIENT_HEARTBEAT:
                break;
                default:
        }*/
        return  message;
    }

    private static  SocketMessage getModel(MsgTypeEnum msgType){
        SocketMessage message = new SocketMessage();
        message.setMsgId(UUID.randomUUID().toString());
        message.setMsgTimestamp(new Date().toString());
        message.setMsgType(msgType.getMsgType());
        Aggrement aggrement = new Aggrement();
        aggrement.setAggree(1);
        message.setData(aggrement);
        return message;
    }

}
