import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

public class WaitRoom1 extends JPanel implements ActionListener{
	public static void main(String[] args) throws IOException {
       WaitRoom1 w2 = new WaitRoom1();
    }
	public WaitRoom1() {
		 setBounds(0, 0, 400, 600);
	        setLayout(null);

	        JPanel panel = new JPanel();
	        panel.setBackground(new Color(227, 227, 234));
	        panel.setBounds(57, 0, 343, 600);
	        add(panel);
	        panel.setLayout(null);

	        JLabel frnd = new JLabel("Holly Talk");
	        frnd.setBounds(12, 6, 162, 41);
	        frnd.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 30));
	        panel.add(frnd);

	        JList frndList = new JList<>();
	        frndList.setBounds(12, 173, 290, 386);
	        panel.add(frndList);
	        updateFriendList(list);
	        frndList.setVisible(true);
	        
	        JButton newRoom = new JButton("+");
	        newRoom.setFont(new Font("굴림", Font.BOLD, 25));
	        newRoom.setBounds(276, 8, 26, 23);
	        newRoom.setBorder(new LineBorder(new Color(0, 0, 0)));
	        panel.add(newRoom);
	        
	        JPanel panel_1 = new JPanel();
	        panel_1.setBounds(12, 72, 290, 91);
	        panel.add(panel_1);
	        panel_1.setLayout(new BorderLayout());
	        
	        JLabel lblNewLabel = new JLabel("New label");
	        panel_1.add(lblNewLabel, BorderLayout.WEST);
	        
	        JLabel lblNewLabel_1 = new JLabel("New label");
	        panel_1.add(lblNewLabel_1, BorderLayout.CENTER);
	        
	        JLabel lblNewLabel_2 = new JLabel("New label");
	        panel_1.add(lblNewLabel_2,BorderLayout.EAST);
	        
	        JLabel lblNewLabel_3 = new JLabel("New label");
	        lblNewLabel_3.setBounds(250, 12, 52, 41);
	        panel.add(lblNewLabel_3);

	        JPanel panelChoice = new JPanel();
	        panelChoice.setBackground(new Color(197, 95, 146));
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
