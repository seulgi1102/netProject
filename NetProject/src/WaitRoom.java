import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class WaitRoom extends JPanel implements ActionListener {
	protected JScrollPane scrollPane;
	protected JLabel loggedInUser;
    protected JPanel panel;
    protected JList<String> frndList;
    protected JTextField textField;
    protected JPanel panelChoice;
    protected JButton newRoom;
    protected JLabel Home;
    protected JLabel image;
    protected JLabel currentStatus;
    protected JLabel CreateRoom;
    DataInputStream is;
    DataOutputStream os;
    protected static String id;
    static String ip;
    static Integer port;
    static ArrayList<Room> roomList = new ArrayList<>();
    protected static ArrayList<ListItem> listItem;
    static ArrayList<String> idList;
    static ArrayList<String> statusList;
    static ArrayList<Image> profilImageList;
    protected Socket s;
    private JLabel Room;
    private JPanel currentPanel;
    private showAllRoomPanel allroom;
    protected TalkRoom talkRoom;
    private ChatServer server;
	public WaitRoom() throws IOException {
		currentPanel = panel;
		//updateShowAllRoomPanel(roomList);
		//allroom = new showAllRoomPanel(roomList, s, id);
	}
    public static void main(String[] args) throws IOException {
        // Add your main method code here if needed
    }
    public void setChatServer(ChatServer server) {
        this.server = server;
    }

    public void start(String id, String ip, Integer port) throws IOException {
        this.id = id;
        this.ip = ip;
        this.port = port;
        this.idList = new ArrayList<>();
        this.listItem = new ArrayList<>();
        
        try {
            s = new Socket(ip, port);
            is = new DataInputStream(s.getInputStream());
            os = new DataOutputStream(s.getOutputStream());

            sendThread sendThread = new sendThread(s, id, is, os);
            sendThread.start();
         
            // 서버로부터 현재 로그인한 유저 리스트를 받음.
            receiveUserList(id, null);
            // 받은 유저리스트로 gui를 초기화
            //gui(idList);
            gui(listItem);
            currentPanel = panel;
            // 별도의 스레드를 시작하여 서버로부터 업데이트를 지속적으로 받음
            new UpdateListener().start();
            



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static ListItem findUserById(String userId) {
    	ListItem userItem = null;
        for (ListItem item : listItem) {
            if (item.getText().equals(userId)) {
            	userItem = item;
                break;
            }
        }
        return userItem;
    }
    //갱신된 아이템들을 리스트에 넣음
    public void receiveUserList(String loggedInUser, String status) throws IOException {
    	while (true) {
            String user = is.readUTF(); //두번하면 두번 입력받아야됨..;
            String id = null;
            String state = status;
            //sendUserList에서
            if (user.equals("END")) {
                break;
            }
            if (!user.equals("UPDATE")) {
                // Split the received string using the delimiter
                String[] parts = user.split("/");
                
                for (String part : parts) {
                    if (part.startsWith("ID:")) {
                        id = part.substring(3);
                    }
                    if (part.startsWith("STATUS:")) {
                        state = part.substring(7);
                        if(state.equals(null)) {
                        	state = "";
                        }
                    }
                }
                listItem.add(new ListItem(id, null,state));
            }
            
        }
    }
    private void receiveRoomInfo() throws IOException {
    	roomList.clear();
        while (true) {
            String roomInfo = is.readUTF();
            if (roomInfo.equals("END")) {
                break;
            }
            
            // Process the received room information
            String[] parts = roomInfo.split("/");
            String roomName = null;
            Integer roomNumber = null;
            // Extract room information as needed
            for (String part : parts) {
                if (part.startsWith("RNAME:")) {
                    roomName = part.substring(6);
                } else if (part.startsWith("RNUM:")) {
                    roomNumber = Integer.parseInt(part.substring(5));
                } 
            }
            roomList.add(new Room(roomNumber, roomName, null));
            // Do something with the room information, e.g., update GUI
            
            // ... (update GUI or perform other actions as needed)
        }
        updateShowAllRoomPanel(roomList);
        for(Room room : roomList) {
            System.out.println("Received User Name:" + id);
            System.out.println("Received Room Name: " + room.getRoomName());
            System.out.println("Received Room Number: " + room.getRoomName());
            }
    }

    // 방만들기 시 바로 초대받은 유저들의 showAllRoomPanel의 roomList가 업데이트 된다. 
    private void updateShowAllRoomPanel(ArrayList<Room> updatedRoomList) {
        if (allroom != null) {
            allroom.updateRoomList(updatedRoomList);
        }
    }

    //친구목록 업데이트
    public void updateFriendList(ArrayList<ListItem> list) {
        DefaultListModel listModel = new DefaultListModel<>();
        String imagePath = "C:\\net-project\\netProject\\NetProject\\src\\img";
        ImageIcon img = new ImageIcon(imagePath);
        
        for (ListItem data : list) {
        	if(!data.getText().equals(id)) {
        	listModel.addElement(data);
            //listModel.addElement(data);
        	}
        }
        frndList.setModel(listModel);
    }
    public void gui(ArrayList<ListItem> list) {
        setBounds(0, 0, 400, 600);
        setLayout(null);

        panel = new JPanel();
        panel.setBackground(new Color(227, 227, 234));
        panel.setBounds(57, 0, 343, 600);
        add(panel);                       
        panel.setLayout(null);

        //JLabel frnd = new JLabel("<html><font color='#edbd05'>H</font>olly <font color='#edbd05'>T</font>alk.</html>");
        JLabel frnd = new JLabel("Holly Talk.");
        frnd.setForeground(new Color(160, 97, 163));//진한보라
        frnd.setBounds(12, 6, 162, 41);
        frnd.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD , 30));
        panel.add(frnd);

        frndList = new JList<>();
        frndList.setCellRenderer(new CustomListCellRenderer());
        frndList.setBounds(12, 160, 290, 380);
        frndList.setBorder(new LineBorder(Color.WHITE));
        panel.add(frndList);
        updateFriendList(list);

        scrollPane = new JScrollPane(frndList);
        scrollPane.setBounds(12, 160, 290, 380);
        scrollPane.setBorder(new LineBorder(Color.WHITE));
        panel.add(scrollPane);
        
        ImageIcon CreateRoomIcon = new ImageIcon("C:\\net-project\\netProject\\NetProject\\src\\img\\door.png");
        int CRWidth = 40; 
        int CRHeight = 40; 
        Image scaledImage3 = CreateRoomIcon.getImage().getScaledInstance(CRWidth, CRHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon3 = new ImageIcon(scaledImage3);
        CreateRoom = new JLabel();
        CreateRoom.setBounds(265, 12, 52, 41);
        CreateRoom.setIcon(scaledIcon3);
        panel.add(CreateRoom);
        
        
        
        JPanel userInfo = new JPanel();
        userInfo.setBounds(12, 72, 290, 80);
        userInfo.setLayout(new GridLayout(1,2));   
        userInfo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        ListItem item =findUserById(id);      
        loggedInUser = new JLabel(item.getText());
        
        
        image = new JLabel();
        ImageIcon originalIcon = item.getProfileImage();
        // Resize the ImageIcon to the desired width and height
        int newWidth = 40; // Set the desired width
        int newHeight = 40; // Set the desired height
        Image scaledImage = originalIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        
        image.setIcon(scaledIcon);
        
        currentStatus = new JLabel(item.getStatus());
        
        
        userInfo.add(image);
        userInfo.add(loggedInUser);
        userInfo.add(currentStatus);
        
        panel.add(userInfo);
        
        panelChoice = new JPanel();
        panelChoice.setBackground(new Color(197, 95, 146));
        panelChoice.setBounds(0, 0, 58, 600);
        add(panelChoice);
        panelChoice.setLayout(null);
        
        ImageIcon homeIcon = new ImageIcon("C:\\net-project\\netProject\\NetProject\\src\\img\\home.png");
        int homeWidth = 40; 
        int homeHeight = 40; 
        Image scaledImage1 = homeIcon.getImage().getScaledInstance(homeWidth, homeHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon1 = new ImageIcon(scaledImage1);
        Home = new JLabel();
        Home.setIcon(scaledIcon1);
        
        Home.setFont(new Font("굴림", Font.BOLD, 20));
        Home.setBounds(7, 10, 43, 40);
        Home.setHorizontalAlignment(SwingConstants.CENTER);
        panelChoice.add(Home);
        
        ImageIcon roomIcon = new ImageIcon("C:\\net-project\\netProject\\NetProject\\src\\img\\chat.png");
        Image scaledImage2 = roomIcon.getImage().getScaledInstance(homeWidth, homeHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon2 = new ImageIcon(scaledImage2);
        Room = new JLabel();
        Room.setIcon(scaledIcon2);
        Room.setFont(new Font("굴림", Font.BOLD, 20));
        Room.setHorizontalAlignment(SwingConstants.CENTER);
        Room.setBounds(7, 60, 43, 40);
        panelChoice.add(Room);

        setVisible(true);

        //newRoom.addActionListener(this);
        CreateRoom.addMouseListener(new MyMouseListener());
        loggedInUser.addMouseListener(new MyMouseListener());
        Room.addMouseListener(new MyMouseListener());
        Home.addMouseListener(new MyMouseListener());
    }
    class MyMouseListener extends MouseAdapter {
        public void mouseClicked(MouseEvent arg0) {
        	System.out.println("Mouse clicked event triggered.");
            if (arg0.getSource() == loggedInUser) { 
            	 ListItem loggedInUserItem = null;
                 for (ListItem item : listItem) {
                     // Use equals() to compare strings
                     if (item.getText().equals(id)) {
                         loggedInUserItem = item;
                         break;
                     }
                 }

                 if (loggedInUserItem != null) {
                     showMyProfilePanel profilePanel = new showMyProfilePanel(loggedInUserItem);
                     profilePanel.setVisible(true);
                 }
            }
            else if(arg0.getSource() == CreateRoom) {
            	ListItem item =findUserById(id);
                showNewRoomPanel roomPanel = new showNewRoomPanel(listItem,item, os);
            }
            else if (arg0.getSource() == Room) {
            	//if(currentPanel==(panel)) {
            	//currentPanel = allroom;
            	showRoomList();
            	//}
            	//remove(panel);
            	
        		//allroom = new showAllRoomPanel(roomList, s, id); // 마우스클릭시 새로 패널을 만드는것은 안됨. 
            	
                //add(allroom);
                //revalidate();
                //repaint();
            }

            else if (arg0.getSource() == Home) {
            	//if(currentPanel==allroom) {
            	//removeCurrentPanel();
            	//currentPanel = panel;
            	remove(allroom);
            	add(panel);
            	revalidate();
            	repaint();
            	
            }      
        }
    }
    private void showRoomList() {
        try {
            sendRequestForRoomList(); // Request room list from the server
            //receiveRoomInfo(); // Receive and update room information
            if(roomList == null) {
            	roomList = new ArrayList<>();
            }
            //updateShowAllRoomPanel(roomList); // Update the showAllRoomPanel with the room list
            remove(panel);
            allroom = new showAllRoomPanel(roomList, s, id);
            add(allroom); // Add the showAllRoomPanel to the WaitRoom panel
            revalidate();
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        private void sendRequestForRoomList() {
		    try {
		        os.writeUTF("REQUEST_ROOM_LIST");
		        os.flush();
		    } catch (IOException e) {
		        e.printStackTrace();
		        // Handle the exception appropriately
		    }
		}
	
 
    @Override
    public void actionPerformed(ActionEvent e) {
    	ListItem item =findUserById(id);
        showNewRoomPanel roomPanel = new showNewRoomPanel(listItem,item, os);
            
    }
    //--------------------------------------------UpdateListener 실시간 업데이트 스레드
    // 리스트를 계속 업데이트해주는 스레드, sendUserUpdate에서 받음.
    class UpdateListener extends Thread {
        public void run() {
            try {
                while (true) {
                    String updateCommand = is.readUTF();
                    String id = null;
                    String status = null;
                    if (updateCommand.startsWith("MESSAGE")) {
                        String allInfo = updateCommand.substring("MESSAGE".length());
                        String[] allInfoParts = allInfo.split("/");
                        if (allInfoParts.length >= 2) {
                            String senderName = allInfoParts[0];
                            String messageContent = allInfoParts[1];
                            allroom.talkRoom.textArea.append(senderName + ": " + messageContent + "\n");
                        } else {
                            // Handle the case where the expected parts are not present in allInfo
                            System.err.println("Invalid message format: " + allInfo);
                        }
                        
                    }
                    if (updateCommand.equals("UPDATE")) {
                        // Receive the updated user list from the server
                        //수정이 필요
                    	listItem.clear();
                        /*String editStatus = null;
                        ListItem loggedInUserItem = findUserById(id);
                        if (loggedInUserItem != null) {
                            editStatus = loggedInUserItem.getStatus();}*/
                    	String[] parts = updateCommand.split("/");
                        
                        for (String part : parts) {
                            if (part.startsWith("ID:")) {
                                id = part.substring(3);
                            }
                            if (part.startsWith("STATUS:")) {
                                status = part.substring(7);
                            }
                        }
                        
                        receiveUserList(id, status);
                        // 새 유저 리스트로 GUI를 업데이트
                        updateFriendList(listItem);
                    }else if(updateCommand.equals("ROOM_UPDATE")) {
                    	receiveRoomInfo();
                    	for(Room room : roomList) {
                    	System.out.println("Received rooms: " + room.getRoomName());
                    	}
                    	//showAllRoomPanel showAllRoomPanel = new showAllRoomPanel(roomList);
						//showAllRoomPanel.updateRoomList(roomList);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
//---------------------------------------------------------TalkRoom 채팅방 클래스
//내포클래스로 수정
    public class TalkRoom extends JFrame {
    	protected JScrollPane scrollPane;
        protected JTextField textField;
        protected static JTextArea textArea;
        protected DataInputStream is;
        protected DataOutputStream os;
        static String id;
        protected MyPanel p;
        protected Socket s;
        protected JLabel roomName;
        protected Room selectedRoom;
        protected ImageIcon backIcon;
        protected JLabel backLabel;
        protected JLabel sendLabel;
        public TalkRoom(String id, Room selectedRoom, Socket s) throws IOException {
            this.id = id;
            this.s = s;
            this.selectedRoom = selectedRoom;
            is = new DataInputStream(s.getInputStream());
            os = new DataOutputStream(s.getOutputStream());

            p = new MyPanel();
        }

    	// Internal class definition
        class MyPanel extends JPanel implements ActionListener {
            public MyPanel() {
            	setBackground(new Color(249, 235, 153));
                setBounds(0, 0, 390, 600);
                setBackground(new Color(227, 227, 234));
                textField = new JTextField(30);
                textField.setBounds(64, 500, 250, 40);
                textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));  
                //textField.addActionListener(this);
                add(textField);

                textArea = new JTextArea();
                textArea.setFont(new Font("굴림", Font.PLAIN, 18));
                textArea.setBounds(0, 0, 360, 449);
                textArea.setEditable(false);
                setLayout(null);
                add(textArea);
                
                        
          
                backIcon = new ImageIcon("C:\\net-project\\netProject\\NetProject\\src\\img\\back1.png");
                int backWidth = 40; 
                int backHeight = 40; 
                Image scaledImage4 = backIcon.getImage().getScaledInstance(backWidth, backHeight, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon4 = new ImageIcon(scaledImage4);
                backLabel = new JLabel();
                //lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 20));
                backLabel.setIcon(scaledIcon4);
                backLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                
                
                
                ImageIcon letterIcon = new ImageIcon("C:\\net-project\\netProject\\NetProject\\src\\img\\letter.png");
                Image scaledImage2 = letterIcon.getImage().getScaledInstance(backWidth, backHeight, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon2 = new ImageIcon(scaledImage2);
                JLabel letterLabel = new JLabel();
                //lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 20));
                letterLabel.setIcon(scaledIcon2);
                letterLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JPanel panel = new JPanel();
                panel.setBackground(new Color(197, 95, 146));
                panel.setLayout(new BorderLayout());
                panel.setBounds(0, 0, 378, 40);
                panel.add(backLabel, BorderLayout.WEST);
                panel.add(letterLabel, BorderLayout.EAST);
                
                roomName = new JLabel(selectedRoom.getRoomName());
                roomName.setFont(new Font("굴림", Font.PLAIN, 20));
                roomName.setBounds(12, 12,150, 33);
                roomName.setForeground(new Color(255, 255, 255));
                roomName.setBackground(new Color(128, 128, 192));
                panel.add(roomName);
                add(panel);
                
                ImageIcon sendIcon = new ImageIcon("C:\\net-project\\netProject\\NetProject\\src\\img\\send.png");
                Image scaledImage3 = sendIcon.getImage().getScaledInstance(backWidth, backHeight, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon3 = new ImageIcon(scaledImage3);

                sendLabel = new JLabel();
                sendLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                sendLabel.setIcon(scaledIcon3);
                sendLabel.setBounds(316, 500, 45, 40);
                add(sendLabel);
                
                
                ImageIcon imoticonIcon = new ImageIcon("C:\\net-project\\netProject\\NetProject\\src\\img\\imoticon.png");
                Image scaledImage1 = imoticonIcon.getImage().getScaledInstance(backWidth, backHeight, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon1 = new ImageIcon(scaledImage1);
                JLabel imoticonLabel= new JLabel();
                //lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 20));
                imoticonLabel.setIcon(scaledIcon1);
                imoticonLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));  
                imoticonLabel.setBounds(10, 500, 45, 40);
                add(imoticonLabel);
                
                scrollPane = new JScrollPane(textArea);
                scrollPane.setBounds(0, 39, 390, 449);
                add(scrollPane);
                backLabel.addMouseListener(new MyMouseListener());
                sendLabel.addMouseListener(new MyMouseListener());
                setVisible(true);
        	}
            class MyMouseListener extends MouseAdapter {
                public void mouseClicked(MouseEvent arg0) {
                	
                    if (arg0.getSource() == backLabel) {
                    	ChatClient.container.remove(allroom.talkRoom.p);
                		allroom = new showAllRoomPanel(roomList, s, id);
                		ChatClient.wait.panel = allroom;
                		ChatClient.container.add(ChatClient.wait);
                		ChatClient.frame.revalidate();
                		ChatClient.frame.repaint();
                    
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
                }
            }
            public void actionPerformed(ActionEvent evt) {
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
        }

        public static void main(String[] args) throws IOException {
            // Here you can add the code to connect to the server, create a room, etc.
            // For example:
            // Socket socket = new Socket("localhost", 5000);
            // Room room = new Room(...);
            // new TalkRoom("User1", room, socket);
        }
    }
    //--------------------------------------------------------showAllRoomPanel 방목록을 보여주는 클래스
    public class showAllRoomPanel extends JPanel implements ListSelectionListener {
        protected JList<Room> roomList;
        protected JScrollPane scrollPane;
        protected ArrayList<Room> allRoom;
        protected Socket s;
        protected DataInputStream is;
        protected DataOutputStream os;
        protected JPanel panel;
        protected String id;
        protected Room selectedRoom;
        protected TalkRoom talkRoom;
        public showAllRoomPanel(ArrayList<Room> allRoom, Socket s, String id) {
        	this.allRoom = allRoom;
        	this.id = id;
        	this.s = s;
        	currentPanel = allroom;
        	//allRoom.add(new Room(1, "Test Room 1", null));
            //allRoom.add(new Room(2, "Test Room 2", null));
            //allRoom.add(new Room(3, "Test Room 3", null));
            
            gui(this.allRoom);
        }
        public void setAllRoom(ArrayList<Room> allRoom) {
        	this.allRoom = allRoom;
        }
        public void updateRoomList(ArrayList<Room> roomList) {
            DefaultListModel<Room> listModel = new DefaultListModel<>();

            for (Room room : roomList) {
                listModel.addElement(room);
            }
            this.roomList.setModel(listModel);
        }

        private void gui(ArrayList<Room> list) {
            setLayout(null);
            setBounds(57, 0, 343, 600);

            panel = new JPanel();
            panel.setBackground(new Color(227, 227, 234));
            panel.setBounds(0, 0, 343, 600);
            add(panel);
            panel.setLayout(null);

            //JLabel label = new JLabel("<html><font color='#edbd05'> R</font>oom </html>");
            JLabel label = new JLabel(" Room.");
            label.setForeground(new Color(160, 97, 163));
            //label.setForeground(new Color(197, 95, 146)); //보라색
            //label.setForeground(new Color(237, 185, 5));//노란색
            label.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 30));
            label.setBounds(10, 10, 200, 30); // Adjust the bounds as needed
            panel.add(label);

            roomList = new JList<>();
            roomList.setCellRenderer(new CustomListCellRenderer2());
            updateRoomList(list);
            roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            roomList.addListSelectionListener(this);

            scrollPane = new JScrollPane(roomList);
            scrollPane.setBorder(new LineBorder(Color.WHITE));
            scrollPane.setBounds(10, 50, 300, 500); // Adjust the bounds as needed
            panel.add(scrollPane);
        }
        public void valueChanged(ListSelectionEvent e) {
        	if(!e.getValueIsAdjusting()) {
        		Room selectedRoom = roomList.getSelectedValue();
        
        		if(selectedRoom != null) {
        			int roomNumber = selectedRoom.getRoomNumber();
        			String roomName = selectedRoom.getRoomName();
        			
        			try {
        				navigateToTalkRoom(selectedRoom);
        				//서버로 해당유저가 입장했음을 알리는 신호를 보내야함,
        			}catch(IOException el) {
        				el.printStackTrace();
        			}
        		}
        	}
        }
    	private void navigateToTalkRoom(Room selectedRoom) throws IOException {
    		// TODO Auto-generated method stub
    		ChatClient.container.remove(ChatClient.wait);
    		talkRoom = new TalkRoom(id, selectedRoom, s);
    		
    		ChatClient.container.add(talkRoom.p);
    		ChatClient.frame.revalidate();
    		ChatClient.frame.repaint();
    	}
    }

    class CustomListCellRenderer2 extends DefaultListCellRenderer {
        private static final int PADDING = 15;

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(1,1));
            panel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.WHITE),
                    BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING)
            ));

            if (value instanceof Room) {
                Room room = (Room) value;

                JLabel labelRoomName = new JLabel(room.getRoomName());
                JLabel labelRoomNumber = new JLabel("Room " + room.getRoomNumber());
                panel.add(labelRoomNumber);
                panel.add(labelRoomName);
            }

            return panel;
        }
    }
    //------------------------------------------------------showMyProfilePanel
    @SuppressWarnings("serial")
    class showMyProfilePanel extends JFrame implements ActionListener {
    		

    		JPanel myProfilePanel;
    		private JLabel textField;
    		private JTextField statusTextField;
    		protected JButton editBtn;
    		protected JButton closeBtn;
    		protected ListItem currentUser;
    		protected JLabel lblNewLabel_2;
    		showMyProfilePanel(ListItem currentUser){
    			
    			this.currentUser = currentUser;
    			setBounds(0, 0, 434, 422);
    			myProfilePanel = new JPanel();	
    			myProfilePanel.setBounds(0, 0, 424, 404);
    			getContentPane().add(myProfilePanel);
    			myProfilePanel.setLayout(null);
    			
    			editBtn = new JButton("편집");
    			editBtn.setBounds(327, 349, 81, 23);
    			myProfilePanel.add(editBtn);
    			
    			JLabel lblNewLabel = new JLabel("상태 메시지:");
    			lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 13));
    			lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
    			lblNewLabel.setBounds(34, 208, 99, 33);
    			myProfilePanel.add(lblNewLabel);
    			
    			closeBtn = new JButton("취소");
    			closeBtn.setBounds(260, 349, 67, 23);
    			myProfilePanel.add(closeBtn);
    			
    			textField = new JLabel(this.currentUser.getText());
    			textField.setBounds(133, 181, 194, 29);
    			myProfilePanel.add(textField);
    			    			
    			JLabel lblNewLabel_1 = new JLabel("이름:");
    			lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
    			lblNewLabel_1.setFont(new Font("맑은 고딕", Font.BOLD, 13));
    			lblNewLabel_1.setBounds(78, 179, 55, 29);
    			myProfilePanel.add(lblNewLabel_1);
    			
    			statusTextField = new JTextField(this.currentUser.getStatus());
    			statusTextField.setColumns(10);
    			statusTextField.setBounds(133, 219, 194, 120);
    			myProfilePanel.add(statusTextField);
    			
    			lblNewLabel_2 = new JLabel();
    			ImageIcon profileImageIcon = this.currentUser.getProfileImage();
    	        Image scaledProfileImage = scaleImage(profileImageIcon.getImage(), 142, 131);
    	        ImageIcon scaledProfileImageIcon = new ImageIcon(scaledProfileImage);
    	        lblNewLabel_2.setIcon(scaledProfileImageIcon);
    			lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
    			lblNewLabel_2.setBounds(133, 40, 142, 131);
    			lblNewLabel_2.addMouseListener(new MouseAdapter() {
    	            @Override
    	            public void mouseClicked(MouseEvent e) {
    	                SwingUtilities.invokeLater(() -> {
    	                    // Open a file chooser
    	                    System.out.println("Mouse Clicked");
    	                    JFileChooser fileChooser = new JFileChooser();
    	                    int result = fileChooser.showOpenDialog(showMyProfilePanel.this);

    	                    // If a file is selected, update the label with the new image
    	                    if (result == JFileChooser.APPROVE_OPTION) {
    	                        ImageIcon newProfileImageIcon = new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath());
    	                        Image newScaledProfileImage = newProfileImageIcon.getImage().getScaledInstance(142, 131, Image.SCALE_SMOOTH);
    	                        ImageIcon newScaledProfileImageIcon = new ImageIcon(newScaledProfileImage);
    	                        lblNewLabel_2.setIcon(newScaledProfileImageIcon);
    	                    }
    	                });
    	            }
    	        });

    	        myProfilePanel.add(lblNewLabel_2);

    			
    			JLabel lblNewLabel_1_1 = new JLabel("나의 프로필");
    			lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.LEFT);
    			lblNewLabel_1_1.setFont(new Font("맑은 고딕", Font.BOLD, 17));
    			lblNewLabel_1_1.setBounds(12, 10, 160, 44);
    			myProfilePanel.add(lblNewLabel_1_1);
    			closeBtn.addActionListener(this);
    			editBtn.addActionListener(this);
    			setVisible(true);
    			
    			
    		}
    		private Image scaleImage(Image image, int width, int height) {
    	        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    	    }

    		@Override
    		public void actionPerformed(ActionEvent e) {
    			if(e.getSource()== closeBtn) {
    				dispose();
    				// TODO Auto-generated method stub
    			}
    			if(e.getSource()==editBtn) {
    				updateUserSettings();
    				sendRequestForUserList();
    				currentStatus.setText(statusTextField.getText());
    				image.setIcon(lblNewLabel_2.getIcon());
    				dispose();
    			}
    		}
    		//편집버튼을 누르면 유저의 리스트를 요구하는 메서드
    		private void sendRequestForUserList() {
    		    try {
    		        os.writeUTF("REQUEST_USER_LIST");
    		        os.flush();
    		    } catch (IOException e) {
    		        e.printStackTrace();
    		        // Handle the exception appropriately
    		    }
    		}
			private void updateUserSettings() {
    		    String userId = currentUser.getText();
    		    String newStatus = statusTextField.getText();
    		    ImageIcon profileImageIcon = (ImageIcon) lblNewLabel_2.getIcon();

    		    // Send the updated information to the server
    		    sendUserUpdate(userId, newStatus, profileImageIcon);
    		    dispose();
    		}
    		//서버스레드의 while문으로 전달됨.
    		private void sendUserUpdate(String userId, String newStatus,ImageIcon profileImageIcon) {
    		    try {
    		       
    		        os.writeUTF("UPDATE");

    		        os.writeUTF(userId);
    		        os.writeUTF(newStatus); // Send the new status
    		        
    		        
    		        os.writeUTF("END");

    		        
    		        os.flush();
    		    } catch (IOException e) {
    		        e.printStackTrace();
    		        // Handle the exception appropriately
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
        panel.setLayout(new GridLayout(1,2));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE),
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
                panel.add(labelImg);
            }
            JLabel labelText = new JLabel(listItem.getText());        
            JLabel labelStatus = new JLabel(listItem.getStatus());
            panel.add(labelText);
            panel.add(labelStatus);
        }

        return panel;
    }
   
}