import java.net.InetSocketAddress;
import java.nio.channels.NotYetConnectedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;


public class MyWebSocketServer extends WebSocketServer {
	/**
	 * 用户对应的webSocket连接,用于告警权限管理<br/>
	 * key=用户id<br/>
	 * value=用户打开的webSocket连接集合
	 */
	public static Map<String,List<WebSocket>> userConnMap = new HashMap<String,List<WebSocket>>();
	
	public MyWebSocketServer(String ip,int port) {
		super(new InetSocketAddress( ip,port ));
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote ) {
		for(Entry<String,List<WebSocket>> entry : userConnMap.entrySet()){
			List<WebSocket> listconn = entry.getValue();
			if(listconn.contains(conn)){
				listconn.remove(conn);
			}
		}
		System.out.println( "closed" );
	}

	@Override
	public void onError( WebSocket conn, Exception ex ) {
		System.out.println( "Error:" );
		ex.printStackTrace();
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		List<WebSocket> userConns = userConnMap.get(message);
		if(userConns==null){
			userConns = new ArrayList<WebSocket>();
			userConnMap.put(message, userConns);
		}
		if(!userConns.contains(conn)){
			userConns.add(conn);
		}
		
		for(int i=0;i<10;i++){
			try {
				Thread.sleep(2000);
				Map<String,Object> dataMsg = new HashMap<String, Object>();
				dataMsg.put("hostIp", "192.168.20.234"+i);
				dataMsg.put("uuid", ""+i);
				dataMsg.put("hostName", "test"+i);
				dataMsg.put("isExist", false);
				dataMsg.put("alarmLevel", i%3);
				dataMsg.put("alarmNumber", 1);
				dataMsg.put("alarmState", 3);
				dataMsg.put("isIgnored", false);
				dataMsg.put("alarmType", "capac"+i);
				dataMsg.put("whetherToPush",true);
				dataMsg.put("lastAlarmTime", new Date().getTime()+i);
				dataMsg.put("indicatorBcode", "capac_size"+i);
				dataMsg.put("indicatorName", "test");
				dataMsg.put("ignoreNumber", 0);
				dataMsg.put("isSysLog", "no");
				dataMsg.put("command", "cmd");
				dataMsg.put("partId", "test1"+i);
				dataMsg.put("partName", "test1"+i);
				dataMsg.put("commandResult", "");
				dataMsg.put("instanceId", 54+i);
				dataMsg.put("alarmMessage", i+"_2016-04-28 16:16:34(192.168.20.13)[CAPAC.WARNING]:操作系统  文件系统当前使用大小  /rpool[27700508kb(maxValue:286949376kb)]告警阀值(1.0---80.0)");
				conn.send(JSONUtil.javaBeanToJson(dataMsg));
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (NotYetConnectedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		System.out.println( "///////////Opened connection number");
		
	}
	public static void main(String[] args){

		new MyWebSocketServer("192.168.20.166",9003).start();
	}
	
}
