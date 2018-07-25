package client.tool;

import java.io.ObjectInputStream;
import java.net.Socket;

import client.surface.*;

import common.Message;
import common.MessageType;

/**
 * 
 * @author harry
 *
 */
public class LinkServerThread extends Thread {
	private Socket socket;

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public LinkServerThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		while (true) {
			try {
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				Message message = (Message) ois.readObject();

				if (message.getMessageType().equals(MessageType.MESSAGE)) {
					System.out.println("读取到从服务器发来的消息" + message.getSender() + "对" + message.getGetter() + "说:"
							+ message.getContent());
					Chat chat = ManageChat.getChat(message.getGetter() + "" + message.getSender());
					chat.showMessage(message);
				} else if (message.getMessageType().equals(MessageType.MESSAGE_RETURN_ONLINEFRIEND)) {
					String sender = message.getGetter();
					List list = ManageList.getList(sender);
					String content = message.getContent();
					String[] onLineId = content.split(" ");
					for (int i = 0; i < onLineId.length; i++) {
						list.showOnLineId(onLineId[i]);
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
