import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import WaitRoom.TalkRoom.MyMouseListener;

public class example extends JFrame {
	protected JScrollPane scrollPane;
    protected JTextField textField;
    protected static JTextArea textArea;
    protected DataInputStream is;
    protected DataOutputStream os;
    static String id;
    protected Socket s;
    protected JLabel roomName;
    protected Room selectedRoom;
    protected ImageIcon backIcon;
    protected JLabel backLabel;
    protected JLabel sendLabel;
    protected JLabel letterLabel;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel noticeLabel;
	private ImageIcon noticeIcon;
	public static void main(String[] args) throws IOException {
		example e = new example(null, null, null);
	}

	/**
	 * Create the frame.
	 */
	 public example(String id, Room selectedRoom, Socket s) throws IOException {
         this.id = id;
         this.s = s;
         this.selectedRoom = selectedRoom;
         is = new DataInputStream(s.getInputStream());
         os = new DataOutputStream(s.getOutputStream());
		setBackground(new Color(249, 235, 153));
        setBounds(0, 0, 390, 600);
        setBackground(new Color(227, 227, 234));
        textField = new JTextField(30);
        textField.setBounds(64, 500, 250, 40);
        textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));  
        //textField.addActionListener(this);
        getContentPane().add(textField);

        textArea = new JTextArea();
        textArea.setFont(new Font("굴림", Font.PLAIN, 18));
        textArea.setBounds(0, 0, 360, 449);
        textArea.setEditable(false);
        getContentPane().setLayout(null);
        getContentPane().add(textArea);
        
                
  
        backIcon = new ImageIcon("C:\\net-project\\netProject\\NetProject\\src\\img\\back1.png");
        int backWidth = 40; 
        int backHeight = 40; 
        Image scaledImage4 = backIcon.getImage().getScaledInstance(backWidth, backHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon4 = new ImageIcon(scaledImage4);
        backLabel = new JLabel();
        backLabel.setBounds(0, 0, 60, 40);
        //lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 20));
        backLabel.setIcon(scaledIcon4);
        backLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        
        
        ImageIcon letterIcon = new ImageIcon("C:\\net-project\\netProject\\NetProject\\src\\img\\letter.png");
        Image scaledImage2 = letterIcon.getImage().getScaledInstance(backWidth, backHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon2 = new ImageIcon(scaledImage2);
        letterLabel = new JLabel();
        letterLabel.setBounds(318, 0, 60, 40);
        //lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 20));
        letterLabel.setIcon(scaledIcon2);
        letterLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(197, 95, 146));
        panel.setBounds(0, 0, 378, 40);
        panel.setLayout(null);
        panel.add(backLabel);
        panel.add(letterLabel);
        
        roomName = new JLabel(selectedRoom.getRoomName());
        roomName.setFont(new Font("굴림", Font.PLAIN, 20));
        roomName.setBounds(60, 0,258, 40);
        roomName.setForeground(new Color(255, 255, 255));
        roomName.setBackground(new Color(128, 128, 192));
        panel.add(roomName);
        getContentPane().add(panel);
        
        noticeIcon = new ImageIcon("C:\\net-project\\netProject\\NetProject\\src\\img\\notice.png");
        int noticeWidth = 35; 
        int noticeHeight = 35; 
        Image scaledImage = noticeIcon.getImage().getScaledInstance(noticeWidth , noticeHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        noticeLabel = new JLabel();
        noticeLabel.setIcon(scaledIcon);
        noticeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        noticeLabel.setBounds(255, 0, 60, 40);
        panel.add(noticeLabel);
        
        ImageIcon sendIcon = new ImageIcon("C:\\net-project\\netProject\\NetProject\\src\\img\\send.png");
        Image scaledImage3 = sendIcon.getImage().getScaledInstance(backWidth, backHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon3 = new ImageIcon(scaledImage3);

        sendLabel = new JLabel();
        sendLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        sendLabel.setIcon(scaledIcon3);
        sendLabel.setBounds(316, 500, 45, 40);
        getContentPane().add(sendLabel);
        
        
        ImageIcon imoticonIcon = new ImageIcon("C:\\net-project\\netProject\\NetProject\\src\\img\\imoticon.png");
        Image scaledImage1 = imoticonIcon.getImage().getScaledInstance(backWidth, backHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon1 = new ImageIcon(scaledImage1);
        JLabel imoticonLabel= new JLabel();
        //lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 20));
        imoticonLabel.setIcon(scaledIcon1);
        imoticonLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));  
        imoticonLabel.setBounds(10, 500, 45, 40);
        getContentPane().add(imoticonLabel);
        
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(0, 39, 390, 449);
        getContentPane().add(scrollPane);
        backLabel.addMouseListener(new MyMouseListener());
        sendLabel.addMouseListener(new MyMouseListener());
        letterLabel.addMouseListener(new MyMouseListener());
        sendLabel.addMouseListener(new MyMouseListener());
        setVisible(true);
	}
            class MyMouseListener extends MouseAdapter {
                public void mouseClicked(MouseEvent arg0) {
                	
                    if (arg0.getSource() == backLabel) {
                    	//ChatClient.container.remove(allroom.talkRoom.p);
                		dispose();
                    
                    }

                    else if (arg0.getSource() == sendLabel) {
                    	String message = textField.getText();
                        try {
                            os.writeUTF("MESSAGE" + selectedRoom.getRoomNumber() + "/" + id + "/" + message);
                            os.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        textArea.append(id+" : " + message + "\n");
                        textField.selectAll();
                        textArea.setCaretPosition(textArea.getDocument().getLength());
                    }
                    else if (arg0.getSource() == letterLabel) {
                    	try {
							sendLetterPanel letter = new sendLetterPanel(selectedRoom, s, selectedRoom.getRoomNumber(), id);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }
                    else if(arg0.getSource() == noticeLabel) {
                    	
                    }
                }
            }
            
        }