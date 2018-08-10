package socket.server;

import com.alibaba.fastjson.JSON;
import msg.SocketMessage;

public class TcpServerImpl extends TcpServer {
    private SocketTransceiver client;

    public TcpServerImpl(int port) {
        super(port);
    }
    @Override
    public void onConnect(SocketTransceiver client) {
        this.client = client;
        System.out.println("开始连接......");
    }

    @Override
    public void onConnectFailed() {
        System.out.println("连接失败......");
    }

    @Override
    public void onReceive(SocketTransceiver client, SocketMessage message) {
        String str = JSON.toJSONString(message);
        System.out.println(String.format("接收到客户端发送的信息：总共%s个字符,内容：%s",str.length(), str));
    }

    @Override
    public void onDisconnect(SocketTransceiver client) {
        System.out.println("客户端断开连接......");
    }

    @Override
    public void onServerStop() {
        System.out.println("服务端断开......");
    }


    public SocketTransceiver getTransceiver() {
        return client;
    }
}
