import java.awt.Color;
import java.awt.Font;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Login.RoundBorder;

public class example extends JPanel {
	protected JTextField editIP;
	protected JTextField editPort;
	protected JTextField editUser;
	protected JButton LoginBtn;
	final static String ServerPort = "5001";
	InetAddress ip = InetAddress.getLocalHost();
	String ipAddress = ip.getHostAddress();
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public example() {
		setBounds(0,0,390,600);
		setBackground(new Color(227, 227, 234));
		//USER
		JLabel userName = new JLabel();
		userName.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));
		userName.setHorizontalAlignment(userName.CENTER);
		userName.setBounds(120,176, 47, 27);
		userName.setText("USER");
		setLayout(null);
		
		editUser = new JTextField();
		editUser.setLocation(120, 213);
		editUser.setSize(141,35);
		add(editUser);
		add(userName);
		
		//IP
		JLabel IP = new JLabel();
		IP.setText("IP");
		IP.setHorizontalAlignment(SwingConstants.CENTER);
		IP.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));
		IP.setBounds(120, 258, 17, 27);
		add(IP);
		
		editIP = new JTextField();
		editIP.setBounds(120, 295, 141, 35);
		editIP.setText(ipAddress);
		//"127.0.0.1"
		add(editIP);
		//PORT
		JLabel Port = new JLabel();
		Port.setText("PORT");
		Port.setHorizontalAlignment(SwingConstants.CENTER);
		Port.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));
		Port.setBounds(120, 340, 51, 27);
		add(Port);
		
		editPort = new JTextField();
		editPort.setBounds(121, 377, 140, 35);
		//디폴트 포트
		editPort.setText(ServerPort);
		add(editPort);
		//LOGIN BTN
		LoginBtn = new JButton("LOGIN");
		int borderRadius = 15; // Adjust the radius as needed
		LoginBtn.setBorder(new RoundBorder(borderRadius));
		LoginBtn.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));
		LoginBtn.setBounds(102, 470, 176, 47);
		add(LoginBtn);
		//APP NAME
		JLabel appName = new JLabel();
		appName.setForeground(new Color(160, 97, 163));
		appName.setBackground(new Color(197, 95, 146));
		appName.setText("Holly Talk");
		appName.setHorizontalAlignment(SwingConstants.CENTER);
		appName.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD | Font.ITALIC, 50));
		appName.setBounds(59, 47, 250, 100);
		add(appName);
	}

}
