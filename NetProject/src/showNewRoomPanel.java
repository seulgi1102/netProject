import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
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
	private JScrollPane scroll;
	private ArrayList<ListItem> itemList;
	private ArrayList<String> selectedUserList;
	private ChatServer chatServer;
	private JButton createRoomBtn;
	private JButton closeBtn;
	showNewRoomPanel(ArrayList<ListItem> itemList,ChatServer server){
		this.itemList = itemList;
		this.chatServer =server;
		this.selectedUserList = new ArrayList<>();
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
		lblNewLabel.setBounds(10, 70, 126, 19);
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
		    JCheckBox checkBox = new JCheckBox(item.getText());
		    panel.add(checkBox);
		}

		scroll = new JScrollPane(panel);
		scroll.setBounds(12, 99, 264, 224);

		newRoomPanel.add(scroll);
		
		closeBtn = new JButton("취소");
		closeBtn.setBounds(209, 333, 67, 23);
		newRoomPanel.add(closeBtn);
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
			dispose();
		}
		// TODO Auto-generated method stub
		
	}

}