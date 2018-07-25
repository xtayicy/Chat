package server.pattern;

import java.io.*;
import java.net.*;

import common.*;

/**
 * 
 * @author harry
 *
 */
public class Service {
	public Service() {
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket(9999);
			while (true) {
				Socket socket = serverSocket.accept();
				ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
				User user = (User) objectInputStream.readObject();
				String userId = user.getUserId();
				System.out.println(user.getUserId() + "    " + user.getPassword());
				Message message = new Message();

				ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

				if (user.getPassword().equals("123456")) {
					message.setMessageType("1");
					objectOutputStream.writeObject(message);
					ServerClientThread serverThread = new ServerClientThread(socket);
					ManageThread.addThread(userId, serverThread);
					serverThread.start();
					serverThread.report(userId);
				} else {
					message.setMessageType("2");
					objectOutputStream.writeObject(message);
					socket.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(serverSocket != null){
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
