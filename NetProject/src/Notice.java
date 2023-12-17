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
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class Notice extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton closeBtn;
	private JButton editBtn;
	private JTextArea textArea;
	private Socket s;
	private ArrayList<String> users;
	private Room currentRoom;
	private String userId;
	private String noticeContent = "";

	public static void main(String[] args) {
	
	}

	/**
	 * Create the frame.
	 */
	public Notice(Socket s, String userId, Room currentRoom) {
		this.s = s;
		this.userId = userId;
		this.users = new ArrayList<>();
		this.currentRoom = currentRoom;
		this.users = currentRoom.getUserList();
		if (currentRoom.getNotice() == null) {
            this.noticeContent = "공지사항을 입력해주세요.";
        } else {
            this.noticeContent = currentRoom.getNotice();
        }
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 540, 283);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("공지사항 게시판");
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblNewLabel.setBounds(12, 0, 343, 55);
		contentPane.add(lblNewLabel);
		
		closeBtn = new JButton("취소");
		closeBtn.setBounds(12, 200, 95, 32);
		contentPane.add(closeBtn);
		
		editBtn = new JButton("수정");
		editBtn.setBounds(413, 200, 95, 32);
		contentPane.add(editBtn);
		
		textArea = new JTextArea(noticeContent);
		textArea.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		textArea.setBounds(12, 48, 496, 140);
		textArea.setEditable(false);
		contentPane.add(textArea);
		
		closeBtn.addActionListener(this);
		editBtn.addActionListener(this);
		setVisible(true);

	}
	
	private void updateNotice() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()== closeBtn) {
			dispose();
			// TODO Auto-generated method stub
		}
		if(e.getSource()==editBtn) {
			noticeContent = textArea.getText();
			try {
				EditNotice editNotice = new EditNotice(s, userId, users, currentRoom, noticeContent);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			dispose();
		}
	}
}
