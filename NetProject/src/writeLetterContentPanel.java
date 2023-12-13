import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class writeLetterContentPanel extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea textArea;
	private JScrollPane scroll;
	private static ArrayList<ListItem> itemList;
	private ArrayList<String> userNameList;
	private JButton sendBtn;
	private JButton closeBtn;
	protected static Integer roomNumber = 0;
	protected Socket socket;
	protected Room room;
	protected DataInputStream is;
	protected DataOutputStream os;
	protected String id;
	StringBuilder messageBuilder2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
	}
	public writeLetterContentPanel(Room room, Socket s, Integer roomNumber, String id, ArrayList<String> userNameList ) throws IOException {
		this.socket = s;
		this.room = room;
		this.id = id;
		this.roomNumber = roomNumber;
		this.userNameList = userNameList;
		is = new DataInputStream(s.getInputStream());
        os = new DataOutputStream(s.getOutputStream());
        
		StringBuilder messageBuilder = new StringBuilder();
        for(String user : userNameList) {
		messageBuilder.append(user).append("  ");
		}
        messageBuilder2 = new StringBuilder();
        for(String user : userNameList) {
		messageBuilder2.append(user).append("|");
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 464, 393);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setBounds(12, 58, 427, 245);
		contentPane.add(textArea);
		textArea.setLineWrap(true);      // Enable line wrapping
		textArea.setWrapStyleWord(true);
		textArea.setColumns(10);
		textArea.setRows(10);
		
		JLabel lblNewLabel = new JLabel("To.");
		lblNewLabel.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 20));
		lblNewLabel.setBounds(12, 17, 49, 31);
		contentPane.add(lblNewLabel);
		
		sendBtn = new JButton("보내기");
		sendBtn.setBounds(344, 313, 95, 34);
		contentPane.add(sendBtn);
		
		closeBtn = new JButton("취소");
		closeBtn.setBounds(12, 313, 95, 34);
		contentPane.add(closeBtn);
		
		JLabel lblNewLabel_1 = new JLabel(messageBuilder.toString());
		lblNewLabel_1.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 20));
		lblNewLabel_1.setBounds(57, 20, 331, 25);
		contentPane.add(lblNewLabel_1);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(12, 57, 427, 250);
		contentPane.add(scrollPane);
		setVisible(true);
		sendBtn.addActionListener(this);
		closeBtn.addActionListener(this);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//int roomNumber = generateRoomNumber();
        //ArrayList<String> selectedUsers = getSelectedUsers();
		if(e.getSource().equals(closeBtn)){
			SwingUtilities.invokeLater(() -> {
			    dispose();
			});
		}
		if(e.getSource().equals(sendBtn)){
			String message = textArea.getText();
	        try {
	            os.writeUTF("LETTER" + roomNumber + "/" +messageBuilder2+ "/" + id + "/" + message);
	            os.flush();
	        } catch (IOException e1) {
	            e1.printStackTrace();
	        }
			
		}
		// TODO Auto-generated method stub
		
	}
}
