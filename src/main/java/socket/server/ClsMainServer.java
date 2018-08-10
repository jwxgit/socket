package socket.server;//package socket.server;
//
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import java.awt.*;
//import java.io.BufferedInputStream;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.Socket;
//
//public class ClsMainServer {
//
//	public static void main(String[] args) {
//		int port = 10000;
//		TcpServer server = new TcpServer(port) {
//			@Override
//			public void onConnect(SocketTransceiver client) {
//				printInfo(client, "Connect");
//			}
//
//			@Override
//			public void onConnectFailed() {
//				System.out.println("Client Connect Failed");
//			}
//
//			@Override
//			public void onReceive(SocketTransceiver client, String s) {
//				printInfo(client, "Send Data: " + s);
//				client.send(s);
//			}
//			@Override
//			public void onDisconnect(SocketTransceiver client) {
//				printInfo(client, "Disconnect");
//			}
//			@Override
//			public void onServerStop() {
//				System.out.println("--------Server Stopped--------");
//			}
//		};
//		System.out.println("--------Server Started--------");
//		server.start();
//	}
//
//	static void printInfo(SocketTransceiver st, String msg) {
//		System.out.println("Client " + st.getInetAddress().getHostAddress());
//		System.out.println("  " + msg);
//	}
//}