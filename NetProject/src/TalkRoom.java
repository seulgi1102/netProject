import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//소스를 입력하고 Ctrl+Shift+O를 눌러서 필요한 파일을 포함한다. 
public class TalkRoom {
	final static int ServerPort = 5000;
	protected JTextField textField;
	protected JTextArea textArea;
	protected DataInputStream is;
	protected DataOutputStream os;
	static String id;
	static String ip;
	static Integer port;
	protected MyPanel p;


	public TalkRoom(String id, String ip, Integer port) throws IOException {
		this.id = id;
		this.ip = ip;
		this.port = port;

		String IdProtocol = "ID";
		String UserName = IdProtocol+"|"+this.id;

		//InetAddress ip = InetAddress.getByName("localhost");
		Socket s = new Socket(ip, port);
		is = new DataInputStream(s.getInputStream());
		os = new DataOutputStream(s.getOutputStream());
		//아이디 서버로 전달
		os.writeUTF(UserName);



		p = new MyPanel();
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {

				while (true) {
					try {
						String msg = is.readUTF();

						// 받은 패킷을 텍스트 영역에 표시한다.
						textArea.append(new String(msg) + "\n");
					} catch (EOFException e) {
						e.printStackTrace();
						break;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		thread2.start();
	}

	// 내부 클래스 정의
	class MyPanel extends JPanel implements ActionListener {
		public MyPanel() {
		setBackground(new Color(249, 235, 153));
		setBounds(0,0,429,600);
		textField = new JTextField(30);
		textField.setBounds(12, 506, 357, 40);
		textField.addActionListener(this);


		textArea = new JTextArea(10, 30);
		textArea.setBounds(12, 53, 357, 435);
		textArea.setEditable(false);
		setLayout(null);

		add(textField);
		add(textArea);

		setVisible(true);

	}

		public void actionPerformed(ActionEvent evt) {
			String s = textField.getText();

			//String MsgProtocol = "MESSAGE";
			//String message = MsgProtocol+"|"+s;

			try {
				os.writeUTF(s);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//textArea.append("SENT: " + s + "\n");
			textField.selectAll();
			textArea.setCaretPosition(textArea.getDocument().getLength());
		}
	}

	public static void main(String[] args) throws IOException {

	}
}