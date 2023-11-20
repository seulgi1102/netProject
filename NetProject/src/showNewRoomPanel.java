import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;

class showNewRoomPanel extends JFrame implements ActionListener {
	JPanel newRoomPanel;
	private final JList list = new JList();
	showNewRoomPanel(){
		
		setBounds(0, 0, 332, 312);
		newRoomPanel = new JPanel(); 
		newRoomPanel.setBounds(0, 0, 313, 275);
		getContentPane().add(newRoomPanel);
		newRoomPanel.setLayout(null);
		
		JButton closeBtn = new JButton("방만들기");
		closeBtn.setBounds(116, 241, 81, 23);
		newRoomPanel.add(closeBtn);
		list.setBounds(0, 10, 313, 231);
		newRoomPanel.add(list);
		closeBtn.addActionListener(this);
		
		
		
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();
		// TODO Auto-generated method stub
		
	}
}