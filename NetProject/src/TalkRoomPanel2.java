import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;

class TalkRoomPanel2 extends JPanel implements ActionListener {
	protected JTextField textField;
	protected JTextArea textArea;
	protected Container container;
	DataInputStream is;
	DataOutputStream os;
	

	static String id;
	static String ip;
	static Integer port;

	public TalkRoomPanel2() {
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
	
	public String getId() {return id;}
	public void actionPerformed(ActionEvent evt) {
		String s = textField.getText();
		String id = getId();
		try {
			os.writeUTF(s);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		textArea.append("SENT "+id+":"+ s + "\n");
		textField.selectAll();
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}
	public JTextArea getTextArea() {
		return this.textArea;
	}

public static void main(String[] args) throws IOException {

	//MessengerMulti m = new MessengerMulti();
	}
}
