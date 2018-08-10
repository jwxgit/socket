package utils;
 
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetUtil {

	/***
	 *  true:already in using  false:not using 
	 * @param port
	 */
	public static boolean isLoclePortUsing(int port) {
		boolean flag = true;
		try {
			flag = isPortUsing("127.0.0.1", port);
		} catch (Exception e) {
		}
		return flag;
	}

	/***
	 *  true:already in using  false:not using 
	 * @param host
	 * @param port
	 * @throws UnknownHostException
	 */
	public static boolean isPortUsing(String host, int port) throws UnknownHostException {
		boolean flag = false;
		InetAddress theAddress = InetAddress.getByName(host);
		try {
			Socket socket = new Socket(theAddress, port);
			flag = true;
		} catch (IOException e) {
		}
		return flag;
	}

	public static String getIp(){
		try {
			InetAddress addr = InetAddress.getLocalHost();
			return addr.getHostAddress().toString(); //获取本机ip
		}catch (Exception e) {
			return null;
		}
	}

	public static String getConnectString() {
		try {
			int portStart = 10000;
			while (isPortUsing(getIp(),portStart)) {
				portStart ++;
				// 如果加到20000还没有，那么说明失败，防止死循环
				if(portStart == 20000)
					throw new RuntimeException("无法获取连接字符串");
			}
			return getIp() + ":"+ portStart;
		}catch (Exception e) {
			return null;
		}
	}

}