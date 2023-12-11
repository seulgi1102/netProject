
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Color;

public class Login extends JPanel {
	protected JTextField editIP;
	protected JTextField editPort;
	protected JTextField editUser;
	protected JButton LoginBtn;
	final static String ServerPort = "5001";
	InetAddress ip = InetAddress.getLocalHost();
	String ipAddress = ip.getHostAddress();
	public Login() throws IOException {
		setBackground(new Color(253, 237, 172));
	
		gui();

	}
	public void gui() {
		setBounds(0,0,429,600);
		//USER
		JLabel userName = new JLabel();
		userName.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));
		userName.setHorizontalAlignment(userName.CENTER);
		userName.setBounds(132,234, 47, 27);
		userName.setText("USER");
		setLayout(null);
		
		editUser = new JTextField();
		editUser.setLocation(132, 267);
		editUser.setSize(141,35);
		add(editUser);
		add(userName);
		
		//IP
		JLabel IP = new JLabel();
		IP.setText("IP");
		IP.setHorizontalAlignment(SwingConstants.CENTER);
		IP.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));
		IP.setBounds(132, 312, 17, 27);
		add(IP);
		
		editIP = new JTextField();
		editIP.setBounds(132, 338, 141, 35);
		editIP.setText(ipAddress);
		//"127.0.0.1"
		add(editIP);
		//PORT
		JLabel Port = new JLabel();
		Port.setText("PORT");
		Port.setHorizontalAlignment(SwingConstants.CENTER);
		Port.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));
		Port.setBounds(132, 383, 51, 27);
		add(Port);
		
		editPort = new JTextField();
		editPort.setBounds(132, 410, 140, 35);
		//디폴트 포트
		editPort.setText(ServerPort);
		add(editPort);
		//LOGIN BTN
		LoginBtn = new JButton("LOGIN");
		LoginBtn.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));
		LoginBtn.setBounds(117, 490, 176, 47);
		add(LoginBtn);
		//APP NAME
		JLabel appName = new JLabel();
		appName.setText("App");
		appName.setHorizontalAlignment(SwingConstants.CENTER);
		appName.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 50));
		appName.setBounds(152, 118, 93, 67);
		add(appName);
		
	}
	public static void main(String[] args) {
	
	}
}