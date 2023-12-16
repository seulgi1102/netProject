import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class sendImoticonPanel extends JPanel {

	private JDialog imoticonDialog;
    private JLabel imoticonLabel1, imoticonLabel2, imoticonLabel3;  // Add more as needed
    private JLabel imoticonLabel4, imoticonLabel5, imoticonLabel6;  // Add more as needed
    private JButton closeButton; // Add this button
//	private ArrayList<String> userNameList;
	protected Socket socket;
	protected Room room;
	protected DataInputStream is;
	protected DataOutputStream os;
	protected String id;
	protected static Integer roomNumber = 0;

    public sendImoticonPanel(Room room, Socket s, Integer roomNumber, String id)throws IOException {
    	this.socket = s;
		this.room = room;
		this.id = id;
		this.roomNumber = roomNumber;
//		this.userNameList = userNameList;
        os = new DataOutputStream(s.getOutputStream());
        
        imoticonDialog = new JDialog();
        imoticonDialog.setTitle("Select Imoticon");
        imoticonDialog.setSize(221, 195);
        imoticonDialog.setLocation(0,600);
        
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 204, 150);
        panel.setLayout(new FlowLayout());

        int targetWidth = 68;  // 원하는 너비
        int targetHeight = 66;  // 원하는 높이
        
        ImageIcon icon1 = new ImageIcon("./src/img/imo_1.png");
        ImageIcon icon2 = new ImageIcon("./src/img/imo_2.png");
        ImageIcon icon3 = new ImageIcon("./src/img/imo_3.png");
        ImageIcon icon4 = new ImageIcon("./src/img/imo_4.png");
        ImageIcon icon5 = new ImageIcon("./src/img/imo_5.png");
        ImageIcon icon6 = new ImageIcon("./src/img/imo_6.png");

        imoticonLabel1 = new JLabel(resizeImage(icon1, targetWidth, targetHeight));
        imoticonLabel1.setBounds(0, 0, 68, 66);
        
        imoticonLabel2 = new JLabel(resizeImage(icon2, targetWidth, targetHeight));
        imoticonLabel2.setBounds(68, 0, 68, 66);

        imoticonLabel3 = new JLabel(resizeImage(icon3, targetWidth, targetHeight));
        imoticonLabel3.setBounds(136, 0, 68, 66);

        imoticonLabel4 = new JLabel(resizeImage(icon4, targetWidth, targetHeight));
        imoticonLabel4.setBounds(0, 66, 68, 66);

        imoticonLabel5 = new JLabel(resizeImage(icon5, targetWidth, targetHeight));
        imoticonLabel5.setBounds(68, 66, 68, 66);

        imoticonLabel6 = new JLabel(resizeImage(icon6, targetWidth, targetHeight));
        imoticonLabel6.setBounds(136, 66, 68, 66);

        imoticonLabel1.addMouseListener(new ActionListenerForImoticon(1));
        imoticonLabel2.addMouseListener(new ActionListenerForImoticon(2));
        imoticonLabel3.addMouseListener(new ActionListenerForImoticon(3));
        imoticonLabel4.addMouseListener(new ActionListenerForImoticon(4));
        imoticonLabel5.addMouseListener(new ActionListenerForImoticon(5));
        imoticonLabel6.addMouseListener(new ActionListenerForImoticon(6));
        panel.setLayout(null);
        
        panel.add(imoticonLabel1);
        panel.add(imoticonLabel2);
        panel.add(imoticonLabel3);
        panel.add(imoticonLabel4);
        panel.add(imoticonLabel5);
        panel.add(imoticonLabel6);
        
        closeButton = new JButton("Close");
        closeButton.setBounds(0,132,204,25);
        panel.add(closeButton);
                        
        panel.add(closeButton);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	imoticonDialog.dispose(); // Close the dialog
            }
        });
        panel.add(closeButton);
        imoticonDialog.getContentPane().add(panel);
        imoticonDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        imoticonDialog.setLocationRelativeTo(null);
        imoticonDialog.setVisible(true);
    }
    
    private ImageIcon resizeImage(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    public class ActionListenerForImoticon implements MouseListener {
        private int imoticonNumber;

        public ActionListenerForImoticon(int imoticonNumber) {
            this.imoticonNumber = imoticonNumber;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // 클릭한 이미지에 대한 동작 정의
            try {
                os.writeUTF("IMOTICON" + roomNumber + "/" + id + "/" + imoticonNumber);
                os.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
    }

}