import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import WaitRoom.TalkRoom.MyPanel;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.FlowLayout;
import javax.swing.JScrollBar;

public class example extends JPanel {
	protected JScrollPane scrollPane;
	protected JTextField textField;
    protected static JTextArea textArea;
    protected DataInputStream is;
    protected DataOutputStream os;
    static String id;
    protected Socket s;
    protected JLabel roomName;
    protected Room selectedRoom;
	public static void main(String[] args) throws IOException {
       example w2 = new example();
    }
	public example() {
		setBackground(new Color(249, 235, 153));
        setBounds(0, 0, 378, 598);
        setBackground(new Color(227, 227, 234));
        textField = new JTextField(30);
        textField.setBounds(64, 500, 250, 40);
        textField.addActionListener(this);
        add(textField);

        textArea = new JTextArea();
        textArea.setFont(new Font("굴림", Font.PLAIN, 18));
        textArea.setBounds(0, 0, 360, 449);
        textArea.setEditable(false);
        setLayout(null);
        add(textArea);
        
                
  
        ImageIcon backIcon = new ImageIcon("C:\\net-project\\netProject\\NetProject\\src\\img\\back1.png");
        int backWidth = 40; 
        int backHeight = 40; 
        Image scaledImage4 = backIcon.getImage().getScaledInstance(backWidth, backHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon4 = new ImageIcon(scaledImage4);
        JLabel lblNewLabel = new JLabel();
        //lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 20));
        lblNewLabel.setIcon(scaledIcon4);
        lblNewLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        
        
        ImageIcon letterIcon = new ImageIcon("C:\\net-project\\netProject\\NetProject\\src\\img\\letter.png");
        Image scaledImage2 = letterIcon.getImage().getScaledInstance(backWidth, backHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon2 = new ImageIcon(scaledImage2);
        JLabel lblNewLabel_2 = new JLabel();
        //lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 20));
        lblNewLabel_2.setIcon(scaledIcon2);
        lblNewLabel_2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(197, 95, 146));
        panel.setLayout(new BorderLayout());
        panel.setBounds(0, 0, 378, 40);
        panel.add(lblNewLabel, BorderLayout.WEST);
        panel.add(lblNewLabel_2, BorderLayout.EAST);
        
        roomName = new JLabel(selectedRoom.getRoomName());
        roomName.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
        roomName.setBounds(12, 12,150, 33);
        roomName.setForeground(new Color(255, 255, 255));
        roomName.setBackground(new Color(128, 128, 192));
        panel.add(roomName);
        add(panel);
        
        ImageIcon sendIcon = new ImageIcon("C:\\net-project\\netProject\\NetProject\\src\\img\\send.png");
        Image scaledImage3 = sendIcon.getImage().getScaledInstance(backWidth, backHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon3 = new ImageIcon(scaledImage3);

        JLabel lblNewLabel_1 = new JLabel();
        lblNewLabel_1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        lblNewLabel_1.setIcon(scaledIcon3);
        lblNewLabel_1.setBounds(316, 500, 45, 40);
        add(lblNewLabel_1);
        
        
        ImageIcon imoticonIcon = new ImageIcon("C:\\net-project\\netProject\\NetProject\\src\\img\\imoticon.png");
        Image scaledImage1 = imoticonIcon.getImage().getScaledInstance(backWidth, backHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon1 = new ImageIcon(scaledImage1);
        JLabel lblNewLabel_1_1 = new JLabel();
        //lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 20));
        lblNewLabel_1_1.setIcon(scaledIcon1);
        lblNewLabel_1_1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));  
        lblNewLabel_1_1.setBounds(10, 500, 45, 40);
        add(lblNewLabel_1_1);
        
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(0, 39, 390, 449);
        add(scrollPane);
        
        setVisible(true);

	}
}
