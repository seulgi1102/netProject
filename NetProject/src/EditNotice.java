import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class EditNotice extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JButton closeBtn;
	private JButton enrollBtn;
	private JTextArea textArea;
	private Socket s;
	private ArrayList<String> users;
	private Room currentRoom;
	private String userId;
	private String noticeContent;
	private Integer roomNumber;
	//private DataInputStream is;
    private DataOutputStream os;
    private JLabel lblNewLabel_1;
    //private StringBuilder messageBuilder;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public EditNotice(Socket s, String userId, ArrayList<String> users, Room currentRoom, String noticeContent) throws IOException {
		this.s = s;
		this.userId = userId;
		this.users = users;
		this.currentRoom = currentRoom;
		this.noticeContent = noticeContent;
		this.roomNumber = currentRoom.getRoomNumber();
        os = new DataOutputStream(s.getOutputStream());
		/*
        messageBuilder = new StringBuilder();
        for(String user : users) {
		messageBuilder.append(user).append("|");
		}*/
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 471, 241);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(227, 227, 234));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		JPanel panel = new JPanel();
	    panel.setBackground(new Color(197, 95, 146));
	    panel.setBounds(0, 0, 457, 46);
	    panel.setLayout(null);
	    
		JLabel lblNewLabel = new JLabel("공지사항 등록하기");
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblNewLabel.setBounds(12, 0, 176, 46);
		panel.add(lblNewLabel);
		contentPane.add(panel);
		
		
		ImageIcon notice2Icon = new ImageIcon("C:\\net-project\\netProject\\NetProject\\src\\img\\edit.png");
        int noticeWidth = 50; 
        int noticeHeight = 50; 
        Image scaledImage = notice2Icon.getImage().getScaledInstance(noticeWidth , noticeHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel noticeLabel = new JLabel();
        noticeLabel.setIcon(scaledIcon);
        noticeLabel.setBounds(177, 0, 52, 46);
		panel.add(noticeLabel);
		
		closeBtn = new JButton("취소");
		closeBtn.setForeground(new Color(0, 0, 0));
		closeBtn.setBackground(new Color(255, 255, 255));
		closeBtn.setBounds(0, 166, 95, 32);
		contentPane.add(closeBtn);
		
		enrollBtn = new JButton("등록");
		enrollBtn.setBounds(362, 166, 95, 32);
		contentPane.add(enrollBtn);
		
		textArea = new JTextArea(noticeContent);
		textArea.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		textArea.setBounds(0, 47, 457, 109);
		contentPane.add(textArea);
		textArea.setLineWrap(true);      // Enable line wrapping
		textArea.setWrapStyleWord(true);
		textArea.setColumns(10);
		textArea.setRows(5);
		
		closeBtn.addActionListener(this);
		enrollBtn.addActionListener(this);
		setVisible(true);
		

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()== closeBtn) {
			dispose();
			// TODO Auto-generated method stub
		}
		if(e.getSource()==enrollBtn) {
			String content = textArea.getText();
			try {
				os.writeUTF("NOTICE"+ roomNumber+"/"+userId+"/"+content);
				os.flush();
				//WaitRoom.Notice.setNoticeContent(content);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//sendRequestForRoomNotice();
			sendRequestForRoomList();
			dispose();
		}
	}
	private void sendRequestForRoomNotice() {
	    try {
	        os.writeUTF("REQUEST_ROOM_NOTICE"+roomNumber);
	        os.flush();
	    } catch (IOException e) {
	        e.printStackTrace();
	        // Handle the exception appropriately
	    }
	}
	 private void sendRequestForRoomList() {
		    try {
		        os.writeUTF("REQUEST_ROOM_LIST");
		        os.flush();
		    } catch (IOException e) {
		        e.printStackTrace();
		        // Handle the exception appropriately
		    }
		}
}
