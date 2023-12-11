
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Component;

public class Login extends JPanel {
	protected JTextField editIP;
	protected JTextField editPort;
	protected JTextField editUser;
	protected JButton LoginBtn;
	final static String ServerPort = "5001";
	InetAddress ip = InetAddress.getLocalHost();
	String ipAddress = ip.getHostAddress();
	public Login() throws IOException {

	
		gui();

	}
	public void gui() {
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
		appName.setText("<html><font color='#edbd05'>H</font>olly <font color='#edbd05'>T</font>alk</html>");
		appName.setHorizontalAlignment(SwingConstants.CENTER);
		appName.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD | Font.ITALIC, 50));
		appName.setBounds(59, 47, 250, 100);
		add(appName);
	}
	public static void main(String[] args) {
	
	}
	public class RoundBorder implements Border {

	    private int radius;

	    public RoundBorder(int radius) {
	        this.radius = radius;
	    }

	    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
	        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
	    }

	    @Override
	    public Insets getBorderInsets(Component c) {
	        return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
	    }

	    @Override
	    public boolean isBorderOpaque() {
	        return true;
	    }
	}
}
