import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.border.LineBorder;

public class WaitRoom extends JPanel implements ActionListener{
	protected JPanel panel;
	protected JList frndList;
	protected JTextField textField;
	protected JPanel panelChoice;
	protected JButton newRoom;
	DataInputStream is;
	DataOutputStream os;
	static String id;
	static String ip;
	static Integer port;
	static ArrayList<String> idList;
	private JLabel lblL;


	
	public WaitRoom(String id, String ip, Integer port, ArrayList<String> initialIdList) throws IOException {
		this.id = id;
		this.ip = ip;
		this.port = port;
		

		ChatServer.userList.add(this.id);  // Add the current user's id
		
        gui(initialIdList);
		//1 this.idList = initialIdList;
		
		//1ChatServer.userList.add(this.id);
		
		//idList = ChatServer.getUserList();
		//gui(idList);
		
		//1 gui(idList);
		

	}
	//친구목록 업데이트
		public void updateFriendList(ArrayList<String> list) {
			DefaultListModel<String> listModel = new DefaultListModel<>();
			for (String data : list) {
	        listModel.addElement(data);
			}
			frndList.setModel(listModel);
	    }
	
	public void gui(ArrayList<String> list) {
		
		setBounds(0,0,400,600);
		setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(new Color(253, 237, 172));
		panel.setBounds(57, 0, 343, 600);
		add(panel);
		panel.setLayout(null);
		
		JLabel frnd = new JLabel("친구");
		frnd.setBounds(12, 5, 64, 31);
		frnd.setFont(new Font("굴림", Font.BOLD, 20));
		panel.add(frnd);
		
		
		textField = new JTextField();
		textField.setBounds(12, 31, 290, 31);
		panel.add(textField);
		textField.setText("이름 검색");
		textField.setColumns(10);
		
		frndList = new JList();
		frndList.setBounds(12, 72, 290, 510);
		
		panel.add(frndList);
		updateFriendList(list);
		
		JButton newRoom = new JButton("+");
		newRoom.setFont(new Font("굴림", Font.BOLD, 25));
		newRoom.setBounds(276,8, 26,23);
		newRoom.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(newRoom);
		
		panelChoice = new JPanel();
		panelChoice.setBackground(new Color(247, 196, 145));
		panelChoice.setBounds(0, 0, 58, 600);
		add(panelChoice);
		panelChoice.setLayout(null);
		
		JLabel Home = new JLabel("H");
		Home.setFont(new Font("굴림", Font.BOLD, 20));
		Home.setBounds(7, 10, 43, 40);
		Home.setHorizontalAlignment(SwingConstants.CENTER);
		panelChoice.add(Home);
		
		lblL = new JLabel("R");
		lblL.setFont(new Font("굴림", Font.BOLD, 20));
		lblL.setHorizontalAlignment(SwingConstants.CENTER);
		lblL.setBounds(7, 60, 43, 40);
		panelChoice.add(lblL);
		setVisible(true);
		
		newRoom.addActionListener(this);
		
		
	}
	
	public static void main(String[] args) throws IOException {
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
			showNewRoomPanel roomPanel = new showNewRoomPanel();
			/*try {
				showWaitRoom(id, ip, port);
	            //showTalkRoom(id, ip, port);
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }*/
		
		
	}
}
