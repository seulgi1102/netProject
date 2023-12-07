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

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.AbstractButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

class showNewRoomPanel extends JFrame implements ActionListener {
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
	showNewRoomPanel(ArrayList<ListItem> itemList,ListItem currentItem,DataOutputStream os){
		this.itemList = itemList;
		this.os =os;
		this.item =currentItem;
		this.selectedUserList = new ArrayList<>();
		this.userNameList = new ArrayList<>();
		setBounds(0, 0, 302, 403);
		newRoomPanel = new JPanel(); 
		newRoomPanel.setBounds(0, 0, 313, 275);
		getContentPane().add(newRoomPanel);
		newRoomPanel.setLayout(null);
		
		createRoomBtn = new JButton("방만들기");
	    createRoomBtn.setBounds(195, 4, 81, 23);
	    createRoomBtn.addActionListener(this);
	    newRoomPanel.add(createRoomBtn);

		
		JLabel lblNewLabel = new JLabel("대화상대 선택");
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(10, 115, 126, 19);
		newRoomPanel.add(lblNewLabel);
		
		
		textField = new JTextField();
		textField.setBounds(10, 37, 266, 29);
		newRoomPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("방 제목");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		lblNewLabel_1.setBounds(10, 10, 126, 29);
		newRoomPanel.add(lblNewLabel_1);
		
		JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.setBounds(12, 99, 264, 224);
		
		
		for (ListItem item : itemList) {
			if(!item.getText().equals(currentItem.getText())) {
		    checkBox = new JCheckBox(item.getText());
		    panel.add(checkBox);
		    }
		}
		scroll = new JScrollPane(panel);
		scroll.setBounds(12, 135, 264, 188);
		newRoomPanel.add(scroll);
		
		closeBtn = new JButton("취소");
		closeBtn.setBounds(209, 333, 67, 23);
		newRoomPanel.add(closeBtn);
		
		panel_1 = new JPanel();
		panel_1.setBounds(10, 76, 266, 29);
		newRoomPanel.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 1, 0, 0));
		
		lblNewLabel_2 = new JLabel("방장:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		panel_1.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel(item.getText());
		panel_1.add(lblNewLabel_3);
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
			dispose();
			
		}
		// TODO Auto-generated method stub
		
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