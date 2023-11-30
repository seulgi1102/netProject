import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class WaitRoom extends JPanel implements ActionListener {
	public WaitRoom() {
	}
	protected JScrollPane scrollPane;
	protected JLabel loggedInUser;
    protected JPanel panel;
    protected JList<String> frndList;
    protected JTextField textField;
    protected JPanel panelChoice;
    protected JButton newRoom;
    DataInputStream is;
    DataOutputStream os;
    static String id;
    static String ip;
    static Integer port;
    static ArrayList<ListItem> listItem;
    static ArrayList<String> idList;
    static ArrayList<String> statusList;
    static ArrayList<Image> profilImageList;
    private JLabel lblL;

    public static void main(String[] args) throws IOException {
        // Add your main method code here if needed
    }

    public void start(String id, String ip, Integer port) throws IOException {
        this.id = id;
        this.ip = ip;
        this.port = port;
        this.idList = new ArrayList<>();
        this.listItem = new ArrayList<>();
        try {
            Socket s = new Socket(ip, port);
            is = new DataInputStream(s.getInputStream());
            os = new DataOutputStream(s.getOutputStream());

            sendThread sendThread = new sendThread(s, id, is, os);
            sendThread.start();

            // 서버로부터 현재 로그인한 유저 리스트를 받음.
            receiveUserList(id);

            // 받은 유저리스트로 gui를 초기화
            //gui(idList);
            gui(listItem);

            // 별도의 스레드를 시작하여 서버로부터 업데이트를 지속적으로 받음
            new UpdateListener().start();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void receiveUserList(String loggedInUser) throws IOException {
    	while (true) {
            String user = is.readUTF();
        
            if (user.equals("END")) {
                break;
            }
            if (!user.equals("UPDATE")&&!user.equals(loggedInUser)) {
                //idList.add(user);
            	listItem.add(new ListItem(user,null,null));
            }
            
        }
    }

    //친구목록 업데이트
    public void updateFriendList(ArrayList<ListItem> list) {
        DefaultListModel listModel = new DefaultListModel<>();
        String imagePath = "C:\\Users\\USER\\git\\netProject\\NetProject\\src\\img\\defaultProfile.jpeg";
        ImageIcon img = new ImageIcon(imagePath);
        
        for (ListItem data : list) {
        	listModel.addElement(data);
            //listModel.addElement(data);
        }
        frndList.setModel(listModel);
    }/*
    public void updateFriendList(ArrayList<ListItem> userList) {
        DefaultListModel<ListItem> listModel = new DefaultListModel<>();

        for (ListItem listItem : userList) {
            listModel.addElement(listItem);
        }

        frndList.setModel(listModel);
    }*/
    public void gui(ArrayList<ListItem> list) {
        setBounds(0, 0, 400, 600);
        setLayout(null);

        panel = new JPanel();
        panel.setBackground(new Color(253, 237, 172));
        panel.setBounds(57, 0, 343, 600);
        add(panel);                       
        panel.setLayout(null);

        JLabel frnd = new JLabel("친구");
        frnd.setBounds(12, 5, 64, 31);
        frnd.setFont(new Font("굴림", Font.BOLD, 20));
        panel.add(frnd);

        textField = new JTextField();
        textField.setBounds(12, 31, 290, 31);
        panel.add(textField);
        textField.setText("이름 검색");
        textField.setColumns(10);

        frndList = new JList<>();
        frndList.setCellRenderer(new CustomListCellRenderer());
        frndList.setBounds(12, 133, 290, 426);
        panel.add(frndList);
        updateFriendList(list);

        scrollPane = new JScrollPane(frndList);
        scrollPane.setBounds(12, 133, 290, 426);
        panel.add(scrollPane);
        
        newRoom = new JButton("+");
        newRoom.setFont(new Font("굴림", Font.BOLD, 25));
        newRoom.setBounds(276, 8, 26, 23);
        newRoom.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel.add(newRoom);

        loggedInUser = new JLabel(id);
        loggedInUser.setBounds(12, 72, 290, 51);
        panel.add(loggedInUser);

        
        panelChoice = new JPanel();
        panelChoice.setBackground(new Color(247, 196, 145));
        panelChoice.setBounds(0, 0, 58, 600);
        add(panelChoice);
        panelChoice.setLayout(null);

        JLabel Home = new JLabel("H");
        Home.setFont(new Font("굴림", Font.BOLD, 20));
        Home.setBounds(7, 10, 43, 40);
        Home.setHorizontalAlignment(SwingConstants.CENTER);
        panelChoice.add(Home);

        lblL = new JLabel("R");
        lblL.setFont(new Font("굴림", Font.BOLD, 20));
        lblL.setHorizontalAlignment(SwingConstants.CENTER);
        lblL.setBounds(7, 60, 43, 40);
        panelChoice.add(lblL);

        setVisible(true);

        newRoom.addActionListener(this);
        loggedInUser.addMouseListener(new MyMouseListener());
    }
    class MyMouseListener extends MouseAdapter{
        public void mouseClicked(MouseEvent arg0) {    // 마우스 클릭 시
        	ListItem listItem = new ListItem(id,null,null);
        	showMyProfilePanel profilePanel = new showMyProfilePanel(listItem, os);
        	
        }        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        showNewRoomPanel roomPanel = new showNewRoomPanel();
            // Implement the logic for creating a new room or any other action
    }
    
    // 리스트를 계속 업데이트해주는 스레드
    class UpdateListener extends Thread {
        public void run() {
            try {
                while (true) {
                    String updateCommand = is.readUTF();
                    if (updateCommand.equals("UPDATE")) {
                        // Receive the updated user list from the server
                        listItem.clear();
                        receiveUserList(id);
                        
                        // 새 유저 리스트로 GUI를 업데이트
                        updateFriendList(listItem);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

class sendThread extends Thread {
    Socket socket = null;
    String name;
    DataInputStream is;
    DataOutputStream os;

    public sendThread(Socket socket, String name, DataInputStream is, DataOutputStream os) {
        this.name = name;
        this.socket = socket;
        this.os = os;
        this.is = is;
    }

    public void run() {
        try {
            os.writeUTF("ID:" + name);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//JList의 라벨의 형식
class CustomListCellRenderer extends DefaultListCellRenderer {
    private static final int PADDING = 15;

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING)
        ));

        if (value instanceof ListItem) {
            ListItem listItem = (ListItem) value;
            JLabel labelImg = new JLabel();
            if (listItem.getProfileImage() != null) {
                // Get the ImageIcon from the ListItem
                ImageIcon originalIcon = listItem.getProfileImage();

                // Resize the ImageIcon to the desired width and height
                int newWidth = 50; // Set the desired width
                int newHeight = 50; // Set the desired height
                Image scaledImage = originalIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);

                // Set the resized ImageIcon to the JLabel
                labelImg.setIcon(scaledIcon);
                panel.add(labelImg, BorderLayout.WEST);
            }
            JLabel labelText = new JLabel(listItem.getText());
            JLabel labelStatus = new JLabel(listItem.getStatus());
            panel.add(labelText, BorderLayout.CENTER);
            panel.add(labelStatus, BorderLayout.EAST);
        }

        return panel;
    }
}