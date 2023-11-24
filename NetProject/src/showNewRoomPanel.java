import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Font;

class showNewRoomPanel extends JFrame implements ActionListener {
	JPanel newRoomPanel;
	private final JList list = new JList();
	private JTextField textField;
	showNewRoomPanel(){
		
		setBounds(0, 0, 302, 403);
		newRoomPanel = new JPanel(); 
		newRoomPanel.setBounds(0, 0, 313, 275);
		getContentPane().add(newRoomPanel);
		newRoomPanel.setLayout(null);
		
		JButton closeBtn = new JButton("방만들기");
		closeBtn.setBounds(195, 4, 81, 23);
		newRoomPanel.add(closeBtn);
		list.setBounds(10, 91, 266, 232);
		newRoomPanel.add(list);
		
		JLabel lblNewLabel = new JLabel("대화상대 선택");
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(10, 70, 126, 19);
		newRoomPanel.add(lblNewLabel);
		
		JButton closeBtn_1 = new JButton("취소");
		closeBtn_1.setBounds(209, 333, 67, 23);
		newRoomPanel.add(closeBtn_1);
		
		textField = new JTextField();
		textField.setBounds(10, 37, 266, 29);
		newRoomPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("방 제목");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		lblNewLabel_1.setBounds(10, 10, 126, 29);
		newRoomPanel.add(lblNewLabel_1);
		closeBtn.addActionListener(this);
		
		
		
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();
		// TODO Auto-generated method stub
		
	}
}