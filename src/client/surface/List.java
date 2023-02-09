package client.surface;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import client.tool.ManageChat;

/**
 * 
 * @author harry
 *
 */
@SuppressWarnings("serial")
public class List extends JFrame implements ActionListener, MouseListener {
	JPanel panel, panelCenter, panelSouth;
	JButton btn_panel, btn2_panel, btn3_panel;
	JScrollPane scrollPane;
	JLabel[] label;
	String owner;
	JPanel panelNorth2, panelCenter2, panel2;
	JButton btn_panel2, btn2_panel2, btn3_panel2;
	JScrollPane scrollPane2;
	JLabel[] label2;
	CardLayout cardLayout;

	public List(String ownerId) {
		btn_panel = new JButton("我的好友");
		btn2_panel = new JButton("陌生人");
		btn2_panel.addActionListener(this);
		btn3_panel = new JButton("黑名单");

		panel = new JPanel(new BorderLayout());
		panelCenter = new JPanel(new GridLayout(50, 1, 4, 4));
		label = new JLabel[50];

		for (int i = 0; i < label.length; i++) {
			label[i] = new JLabel(i + 1 + "", new ImageIcon("image/dog.jpg"), JLabel.LEFT);
			label[i].setEnabled(false);
			if (label[i].getText().equals(ownerId)) {
				label[i].setEnabled(true);
			}
			label[i].addMouseListener(this);
			panelCenter.add(label[i]);
		}
		panelSouth = new JPanel(new GridLayout(2, 1));
		panelSouth.add(btn2_panel);
		panelSouth.add(btn3_panel);
		scrollPane = new JScrollPane(panelCenter);

		panel.add(btn_panel, "North");
		panel.add(scrollPane, "Center");
		panel.add(panelSouth, "South");

		this.owner = ownerId;

		btn_panel2 = new JButton("我的好友");
		btn_panel2.addActionListener(this);
		btn2_panel2 = new JButton("陌生人");
		btn3_panel2 = new JButton("黑名单");

		panel2 = new JPanel(new BorderLayout());
		panelCenter2 = new JPanel(new GridLayout(20, 1, 4, 4));
		label2 = new JLabel[20];

		for (int i = 0; i < label2.length; i++) {
			label2[i] = new JLabel(i + 1 + "", new ImageIcon("image/dog.jpg"), JLabel.LEFT);
			panelCenter2.add(label2[i]);
		}
		panelNorth2 = new JPanel(new GridLayout(2, 1));
		panelNorth2.add(btn_panel2);
		panelNorth2.add(btn2_panel2);
		scrollPane2 = new JScrollPane(panelCenter2);

		panel2.add(panelNorth2, "North");
		panel2.add(scrollPane2, "Center");
		panel2.add(btn3_panel2, "South");

		cardLayout = new CardLayout();

		this.setLayout(cardLayout);
		this.add(panel, "1");
		this.add(panel2, "2");
		this.setTitle(ownerId);
		this.setSize(200, 400);
		this.setLocation(300, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void showOnLineId(String id) {
		int i = Integer.parseInt(id) - 1;
		label[i].setEnabled(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn2_panel) {
			cardLayout.show(this.getContentPane(), "2");
		} else if (e.getSource() == btn_panel2) {
			cardLayout.show(this.getContentPane(), "1");
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			String strFriendId = ((JLabel) e.getSource()).getText();
			Chat chat = new Chat(owner, strFriendId);
			ManageChat.addChat(owner + "" + strFriendId, chat);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JLabel label = (JLabel) e.getSource();
		label.setForeground(Color.red);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JLabel label = (JLabel) e.getSource();
		label.setForeground(Color.black);
	}
}
