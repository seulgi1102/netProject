import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.AbstractButton;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

@SuppressWarnings("serial")
class sendLetterPanel extends JFrame implements ActionListener {
	JPanel newRoomPanel;
	JCheckBox checkBox;
	private JScrollPane scroll;
	private static ArrayList<ListItem> itemList;
	private ArrayList<String> userNameList;
	private ArrayList<String> selectedUserList;
	private JButton confirmBtn;
	private JButton closeBtn;
	protected static Integer roomNumber = 0;
	protected Socket socket;
	protected Room room;
	protected DataInputStream is;
	protected DataOutputStream os;
	protected String id;
	
	sendLetterPanel(Room room, Socket s, Integer roomNumber, String id) throws IOException{
		this.socket = s;
		this.room = room;
		this.id = id;
		this.userNameList =room.getUserList();
		this.roomNumber = roomNumber;
		this.selectedUserList = new ArrayList<>();
		
		setBounds(0, 0, 302, 351);
		newRoomPanel = new JPanel(); 
		newRoomPanel.setBounds(0, 0, 313, 275);
		getContentPane().add(newRoomPanel);
		newRoomPanel.setLayout(null);
		newRoomPanel.setBackground(new Color(227, 227, 234));
		
		confirmBtn = new JButton("확인");
	    confirmBtn.setBounds(195, 279, 81, 23);
	    confirmBtn.addActionListener(this);
	    newRoomPanel.add(confirmBtn);

		
		JLabel lblNewLabel = new JLabel("대화상대 선택");
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(12, 46, 126, 33);
		newRoomPanel.add(lblNewLabel);
		
		JPanel pane2 = new JPanel();
	    pane2.setBackground(new Color(197, 95, 146));
	    pane2.setBounds(0, 0, 288, 46);
	    pane2.setLayout(null);
	    
	    ImageIcon letterIcon = new ImageIcon("C:\\net-project\\netProject\\NetProject\\src\\img\\letter2.png");
        int noticeWidth = 45; 
        int noticeHeight = 45; 
        Image scaledImage = letterIcon.getImage().getScaledInstance(noticeWidth , noticeHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imageLabel= new JLabel("New label");
        imageLabel.setIcon(scaledIcon);
        imageLabel.setBounds(124, 0, 52, 46);
	    
		JLabel letterLabel = new JLabel("쪽지 보내기");
		letterLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		letterLabel.setBounds(12, 0, 127, 46);
		pane2.add(letterLabel);
		newRoomPanel.add(pane2);
		//lblNewLabel_1.setBounds(128, 0, 52, 46);
		pane2.add(imageLabel);
		
		JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.setBounds(12, 99, 264, 224);
		
		
		for (String name : userNameList) {
			if(!name.equals(id)) {
		    checkBox = new JCheckBox(name);
		    panel.add(checkBox);
		    }
		}
		scroll = new JScrollPane(panel);
		scroll.setBounds(12, 78, 264, 191);
		newRoomPanel.add(scroll);
		
		closeBtn = new JButton("취소");
		closeBtn.setBounds(10, 279, 67, 23);
		newRoomPanel.add(closeBtn);
		closeBtn.addActionListener(this);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//int roomNumber = generateRoomNumber();
        //ArrayList<String> selectedUsers = getSelectedUsers();
		if(e.getSource().equals(closeBtn)){
			dispose();
		}
		if(e.getSource().equals(confirmBtn)){
			Component[] components = ((Container) scroll.getViewport().getView()).getComponents();
	        
			for (Component component : components) {
	            if (component instanceof JCheckBox) {
	                checkBox = (JCheckBox) component;
					if(checkBox.isSelected()) {
						selectedUserList.add(checkBox.getText());
						
						}
						
					}
	            }
			try {
				writeLetterContentPanel content = new writeLetterContentPanel(room, socket, roomNumber, id, selectedUserList );
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dispose();
			
		}
		// TODO Auto-generated method stub
		
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

	 private static int generateRoomNumber() {
		 	
	        return roomNumber++;
	    }
	private static ListItem findUserById(String userId) {
    	ListItem userItem = null;
        for (ListItem item : itemList) {
            if (item.getText().equals(userId)) {
            	userItem = item;
                break;
            }
        }
        return userItem;
    }

}