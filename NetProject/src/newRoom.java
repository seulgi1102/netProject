import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
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
import javax.swing.border.LineBorder;

class newRoom extends JFrame implements ActionListener {
	JPanel newRoomPanel;
	private JTextField textField;
	JCheckBox checkBox;
	private JScrollPane scroll;
	private static ArrayList<ListItem> itemList;
	private static ListItem item;
	private ArrayList<ListItem> selectedUserList;
	private ArrayList<String> userNameList;
	private DataOutputStream os;
	private JButton createRoomBtn;
	private JButton closeBtn;
	private JPanel panel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	protected static Integer roomNumber = 0;
	newRoom(ArrayList<ListItem> itemList,ListItem currentItem,DataOutputStream os){
		this.itemList = itemList;
		this.os =os;
		this.item =currentItem;
		this.selectedUserList = new ArrayList<>();
		this.userNameList = new ArrayList<>();
		setBounds(0, 0, 334, 447);
		setBackground(new Color(227, 227, 234));
		newRoomPanel = new JPanel(); 
		newRoomPanel.setBounds(0, 0, 313, 275);
		newRoomPanel.setBackground(new Color(227, 227, 234));
		getContentPane().add(newRoomPanel);
		newRoomPanel.setLayout(null);
		
		createRoomBtn = new JButton("방만들기");
	    createRoomBtn.setBounds(214, 377, 94, 23);
	    createRoomBtn.addActionListener(this);
	    newRoomPanel.add(createRoomBtn);
		
		
		textField = new JTextField();
		textField.setBorder(new LineBorder(Color.WHITE));
		textField.setFont(new Font("굴림", Font.PLAIN, 15));
		textField.setBounds(16, 76, 288, 29);
		newRoomPanel.add(textField);
		textField.setColumns(10);
		JPanel panel2 = new JPanel();
	    panel2.setBackground(new Color(197, 95, 146));
	    panel2.setBounds(0, 1, 526, 46);
	    panel2.setLayout(null);
	    
		JLabel newRoom = new JLabel("방만들기");
		newRoom.setBounds(12, 0, 86, 46);
		newRoom.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		panel2.add(newRoom);
		newRoomPanel.add(panel2);
		
		
		ImageIcon door2Icon = new ImageIcon("C:\\net-project\\netProject\\NetProject\\src\\img\\door2.png");
        int backWidth = 40; 
        int backHeight = 40; 
        Image scaledImage4 = door2Icon.getImage().getScaledInstance(backWidth, backHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon4 = new ImageIcon(scaledImage4);
        JLabel doorLabel = new JLabel("");
		doorLabel.setBounds(100, 1, 52, 45);
		doorLabel.setIcon(scaledIcon4);
		panel2.add(doorLabel);
        
		JLabel lblNewLabel = new JLabel("대화상대 선택");
		lblNewLabel.setBounds(16, 146, 206, 33);
		newRoomPanel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		JLabel lblNewLabel_1 = new JLabel("방 제목");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lblNewLabel_1.setBounds(16, 54, 138, 23);
		newRoomPanel.add(lblNewLabel_1);
		
		JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.setBorder(new LineBorder(Color.WHITE));
		panel.setBounds(12, 99, 264, 224);
		
		
		for (ListItem item : itemList) {
			if(!item.getText().equals(currentItem.getText())) {
		    checkBox = new JCheckBox(item.getText());
		    panel.add(checkBox);
		    }
		}
		scroll = new JScrollPane(panel);
		scroll.setBounds(16, 179, 292, 193);
		newRoomPanel.add(scroll);
		
		closeBtn = new JButton("취소");
		closeBtn.setBounds(135, 377, 67, 23);
		newRoomPanel.add(closeBtn);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(74, 115, 196, 29);
		newRoomPanel.add(panel_1);
		panel_1.setLayout(null);
		
		lblNewLabel_3 = new JLabel(item.getText());
		lblNewLabel_3.setBounds(12, 0, 184, 29);
		panel_1.add(lblNewLabel_3);
		
		lblNewLabel_2 = new JLabel("방장:");
		lblNewLabel_2.setBounds(26, 115, 36, 29);
		newRoomPanel.add(lblNewLabel_2);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		closeBtn.addActionListener(this);
		/*for (ListItem item : itemList) {
            JCheckBox checkBox = new JCheckBox(item.getText());
            panel_1.add(checkBox);
            checkBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (checkBox.isSelected()) {
                        // 체크박스가 선택된 경우 아이디를 리스트에 추가
                        selectedUserList.add(item.getText());
                    } else {
                        // 체크박스가 해제된 경우 아이디를 리스트에서 제거
                        selectedUserList.remove(item.getText());
                    }
                }
            });
        }
		*/
		
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//int roomNumber = generateRoomNumber();
        //ArrayList<String> selectedUsers = getSelectedUsers();
		if(e.getSource().equals(closeBtn)){
			dispose();
		}
		if(e.getSource().equals(createRoomBtn)){
			Component[] components = ((Container) scroll.getViewport().getView()).getComponents();
	        
			for (Component component : components) {
	            if (component instanceof JCheckBox) {
	                checkBox = (JCheckBox) component;
					if(checkBox.isSelected()) {
						userNameList.add(checkBox.getText());
						
						}
						
					}
	            }
	        for(String user : userNameList) {
				ListItem item = findUserById(user);
				selectedUserList.add(item);
	        }
	        selectedUserList.add(item);
	        String title = textField.getText();	
	        //방의 제목, 선택한 유저의 이름을 서버 while문으로보냄
	        try {
				os.writeUTF("ROOM");
		        for(ListItem item : selectedUserList) {
		        	os.writeUTF(item.getText());	
		        }
		        os.writeUTF("/");
		        os.writeUTF(title);
				os.flush();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
	        /*
			for(ListItem user : selectedUserList) {
				System.out.println(user.getText());
			}
			
			System.out.println(textField.getText());
			//System.out.println("room:"+number);
			*/
	        sendRequestForRoomList();
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