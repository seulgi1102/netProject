import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class waitRoom2 extends JPanel {
	public static void main(String[] args) throws IOException {
       waitRoom2 w2 = new waitRoom2();
    }
	public waitRoom2() {
		 setBounds(0, 0, 400, 600);
	        setLayout(null);

	        JPanel panel = new JPanel();
	        panel.setBackground(new Color(253, 237, 172));
	        panel.setBounds(57, 0, 343, 600);
	        add(panel);
	        panel.setLayout(null);

	        JLabel frnd = new JLabel("친구");
	        frnd.setBounds(12, 5, 64, 31);
	        frnd.setFont(new Font("굴림", Font.BOLD, 20));
	        panel.add(frnd);

	        JTextField textField = new JTextField();
	        textField.setBounds(12, 31, 290, 31);
	        panel.add(textField);
	        textField.setText("이름 검색");
	        textField.setColumns(10);

	        JList frndList = new JList<>();
	        frndList.setBounds(12, 133, 290, 426);
	        panel.add(frndList);
	        updateFriendList(list);
	        frndList.setVisible(true);
	        
	        JButton newRoom = new JButton("+");
	        newRoom.setFont(new Font("굴림", Font.BOLD, 25));
	        newRoom.setBounds(276, 8, 26, 23);
	        newRoom.setBorder(new LineBorder(new Color(0, 0, 0)));
	        panel.add(newRoom);
	        
	        JLabel loggedInUser = new JLabel();
	        loggedInUser.setBounds(12, 72, 290, 51);
	        panel.add(loggedInUser);

	        JPanel panelChoice = new JPanel();
	        panelChoice.setBackground(new Color(247, 196, 145));
	        panelChoice.setBounds(0, 0, 58, 600);
	        add(panelChoice);
	        panelChoice.setLayout(null);

	        JLabel Home = new JLabel("H");
	        Home.setFont(new Font("굴림", Font.BOLD, 20));
	        Home.setBounds(7, 10, 43, 40);
	        Home.setHorizontalAlignment(SwingConstants.CENTER);
	        panelChoice.add(Home);

	        JLabel lblL = new JLabel("R");
	        lblL.setFont(new Font("굴림", Font.BOLD, 20));
	        lblL.setHorizontalAlignment(SwingConstants.CENTER);
	        lblL.setBounds(7, 60, 43, 40);
	        panelChoice.add(lblL);

	        setVisible(true);

	        newRoom.addActionListener(this);

	}
}
