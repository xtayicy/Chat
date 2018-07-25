package client.surface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.tool.*;

import common.Message;
import common.MessageType;

/**
 * 
 * @author harry
 *
 */
@SuppressWarnings("serial")
public class Chat extends JFrame implements ActionListener {
	JTextArea textArea;
	JTextField textField;
	JButton button;
	JPanel panel;
	String ownerId;
	String friendId;

	public Chat(String ownerId, String friendId) {
		this.ownerId = ownerId;
		this.friendId = friendId;
		textArea = new JTextArea();
		textField = new JTextField(20);
		button = new JButton("发送");
		button.addActionListener(this);
		panel = new JPanel();

		panel.add(textField);
		panel.add(button);

		add(textArea);
		add(panel, "South");
		setTitle("你正在和" + friendId + "聊天");
		setSize(300, 200);
		setLocation(318, 186);
		setResizable(false);
		setVisible(true);
	}

	public void showMessage(Message message) {
		String content = message.getSender() + "对你说:" + message.getContent() + "\r\n";
		textArea.append(content);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button) {
			Message message = new Message();
			message.setMessageType(MessageType.MESSAGE);
			message.setSender(ownerId);
			message.setGetter(friendId);
			message.setContent(textField.getText());
			// message.setDate(new Date().toString);
			try {
				LinkServerThread lst = ManageThread.getLinkServerThread(ownerId);
				ObjectOutputStream oos = new ObjectOutputStream(lst.getSocket().getOutputStream());
				oos.writeObject(message);
				String content = "你对" + friendId + "说:" + textField.getText() + "\r\n";
				textArea.append(content);
				textField.setText("");
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
}
