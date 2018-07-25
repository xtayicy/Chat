package client.surface;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.*;

import client.tool.*;
import common.Message;
import common.MessageType;
import common.User;
import client.model.*;

/**
 * 
 * @author harry
 *
 */
@SuppressWarnings("serial")
public class Login extends JFrame implements ActionListener {
	JLabel label;
	JTabbedPane tabbedPane;
	JPanel panelLeft, panelMid, panelRight;
	JLabel label_panelLeft, label2_panelLeft, label3_panelLeft, label4_panelLeft;
	JButton btn_panelLeft;
	JTextField textField_panelLeft;
	JPasswordField passwordField_panelLeft;
	JCheckBox checkBox_panelLeft, checkBox2_panelLeft;
	JPanel panel;
	JButton btn_panel, btn2_panel, btn3_panel;

	public static void main(String[] args) {
		new Login();
	}

	public Login() {
		label = new JLabel(new ImageIcon("image/banner01.gif"));

		panelLeft = new JPanel(new GridLayout(3, 3));
		label_panelLeft = new JLabel("QQ号码", JLabel.CENTER);
		label2_panelLeft = new JLabel("QQ密码", JLabel.CENTER);
		label3_panelLeft = new JLabel("忘记密码", JLabel.CENTER);
		label3_panelLeft.setForeground(Color.blue);
		label4_panelLeft = new JLabel("申请密码保护", JLabel.CENTER);
		btn_panelLeft = new JButton("清除");
		textField_panelLeft = new JTextField();
		passwordField_panelLeft = new JPasswordField();
		checkBox_panelLeft = new JCheckBox("隐身登陆");
		checkBox2_panelLeft = new JCheckBox("记住密码");
		panelLeft.add(label_panelLeft);
		panelLeft.add(textField_panelLeft);
		panelLeft.add(btn_panelLeft);
		panelLeft.add(label2_panelLeft);
		panelLeft.add(passwordField_panelLeft);
		panelLeft.add(label3_panelLeft);
		panelLeft.add(checkBox_panelLeft);
		panelLeft.add(checkBox2_panelLeft);
		panelLeft.add(label4_panelLeft);

		tabbedPane = new JTabbedPane();
		tabbedPane.add("QQ号码", panelLeft);
		panelMid = new JPanel();
		tabbedPane.add("手机号码", panelMid);
		panelRight = new JPanel();
		tabbedPane.add("电子邮件", panelRight);

		panel = new JPanel();
		btn_panel = new JButton("登陆");
		btn_panel.addActionListener(this);
		btn2_panel = new JButton("取消");
		btn3_panel = new JButton("向导");
		panel.add(btn_panel);
		panel.add(btn2_panel);
		panel.add(btn3_panel);

		this.add(label, "North");
		this.add(tabbedPane, "Center");
		this.add(panel, "South");

		this.setTitle("用户登陆");
		this.setSize(351, 240);
		this.setLocation(300, 280);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_panel) {
			Authentication authentication = new Authentication();
			User user = new User();
			String userId = textField_panelLeft.getText().trim();
			user.setUserId(userId);
			user.setPassword(new String(passwordField_panelLeft.getPassword()));

			if (authentication.checkUser(user)) {
				List list = new List(user.getUserId());
				ManageList.addList(userId, list);
				Socket socket = ManageThread.getLinkServerThread(userId).getSocket();
				try {
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					Message message = new Message();
					message.setMessageType(MessageType.MESSAGE_GET_ONLINEFRIEND);
					message.setSender(userId);
					oos.writeObject(message);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "用户名或密码错误");
			}
		}
	}
}
