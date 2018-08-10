package socket.client;

import com.alibaba.fastjson.JSON;
import msg.Aggrement;
import msg.SocketMessage;
import socket.Message;
import utils.FileUtils;

import java.io.File;
import java.util.Date;

/**
 * TCP Socket客户端
 * 
 */
public class TcpClientImpl extends TcpClient {
	private SocketTransceiver client;

	@Override
	public void onConnect(SocketTransceiver transceiver) {
		System.out.println("已连接到服务端......");
	}

	@Override
	public void onConnectFailed() {
		System.out.println("连接失败......");
	}

	@Override
	public void onReceive(SocketTransceiver transceiver, SocketMessage message) {
		System.out.println("接收到服务端数据：" + JSON.toJSONString(message));
		// 发送报文测试
		message.setMsgId("客户端消息");
		transceiver.send(message);
//		if(s.equals("1")) {
//			String str = JSON.toJSONString(mockMessage());
//			System.out.println(String.format("客户端发送报文长度：%s,内容=%s",str.length(),str));
//			transceiver.send(str);
//		}
	}

	@Override
	public void onDisconnect(SocketTransceiver transceiver) {
		System.out.println("与服务端断开了连接.....");
	}


	public static SocketMessage mockMessage() {
		SocketMessage message = new SocketMessage();
		message.setMsgId("服务端消息");
		message.setMsgType("2");
		message.setMsgMd5("3");
		message.setMsgTimestamp(new Date().toString());
		Aggrement aggrement = new Aggrement();
		aggrement.setAggree(1);
		message.setData(aggrement);
		return message;
	}


}