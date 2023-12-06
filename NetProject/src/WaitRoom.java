import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
    DataInputStream is;
    DataOutputStream os;
    protected String id;
    static String ip;
    static Integer port;
    protected static ArrayList<ListItem> listItem;
    static ArrayList<String> idList;
    static ArrayList<String> statusList;
    static ArrayList<Image> profilImageList;
    private JLabel Room;
    private JPanel currentPanel;
    private showAllRoomPanel allroom;
    private ChatServer server;
	public WaitRoom() {
		currentPanel = panel;
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
            Socket s = new Socket(ip, port);
            is = new DataInputStream(s.getInputStream());
            os = new DataOutputStream(s.getOutputStream());

            sendThread sendThread = new sendThread(s, id, is, os);
            sendThread.start();
         
            // 서버로부터 현재 로그인한 유저 리스트를 받음.
            receiveUserList(id, null);
            // 받은 유저리스트로 gui를 초기화
            //gui(idList);
            gui(listItem);
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
                //idList.add(user);
            	//String status = is.readUTF();
                listItem.add(new ListItem(id, null,state));
            }
            
        }
    }
/*
    public void receiveUserList(String loggedInUser) throws IOException {
    	while (true) {
            String user = is.readUTF();
        
            if (user.equals("END")) {
                break;
            }
            if (!user.equals("UPDATE")) {
                //idList.add(user);
            	ListItem item = new ListItem(user,null,null);
            	while(true) {
            		String userd = is.readUTF();
                    if (user.equals("END")) {
                        break;
                    }

                    // Assuming the next readUTF() calls are for additional information
                    
                    // Add other fields as needed
                    item.setStatus(userd);
                    listItem.add(item);
            	}
            }
            
        }
    }
    */
    //친구목록 업데이트
    public void updateFriendList(ArrayList<ListItem> list) {
        DefaultListModel listModel = new DefaultListModel<>();
        String imagePath = "C:\\Users\\USER\\git\\netProject\\NetProject\\src\\img\\defaultProfile.jpeg";
        ImageIcon img = new ImageIcon(imagePath);
        
        for (ListItem data : list) {
        	if(!data.getText().equals(id)) {
        	listModel.addElement(data);
            //listModel.addElement(data);
        	}
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
        frndList.setBounds(12, 160, 290, 380);
        frndList.setBorder(new LineBorder(Color.WHITE));
        panel.add(frndList);
        updateFriendList(list);

        scrollPane = new JScrollPane(frndList);
        scrollPane.setBounds(12, 160, 290, 380);
        scrollPane.setBorder(new LineBorder(Color.WHITE));
        panel.add(scrollPane);
        
        newRoom = new JButton("+");
        newRoom.setFont(new Font("굴림", Font.BOLD, 25));
        newRoom.setBounds(276, 8, 26, 23);
        newRoom.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel.add(newRoom);
        
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
        int newWidth = 50; // Set the desired width
        int newHeight = 50; // Set the desired height
        Image scaledImage = originalIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        
        image.setIcon(scaledIcon);
        
        currentStatus = new JLabel(item.getStatus());
        
        
        userInfo.add(image);
        userInfo.add(loggedInUser);
        userInfo.add(currentStatus);
        
        panel.add(userInfo);
        
        panelChoice = new JPanel();
        panelChoice.setBackground(new Color(247, 196, 145));
        panelChoice.setBounds(0, 0, 58, 600);
        add(panelChoice);
        panelChoice.setLayout(null);

        Home = new JLabel("H");
        Home.setFont(new Font("굴림", Font.BOLD, 20));
        Home.setBounds(7, 10, 43, 40);
        Home.setHorizontalAlignment(SwingConstants.CENTER);
        panelChoice.add(Home);

        Room = new JLabel("R");
        Room.setFont(new Font("굴림", Font.BOLD, 20));
        Room.setHorizontalAlignment(SwingConstants.CENTER);
        Room.setBounds(7, 60, 43, 40);
        panelChoice.add(Room);

        setVisible(true);

        newRoom.addActionListener(this);
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
            else if (arg0.getSource() == Room) {
        		remove(panel);
        		allroom = new showAllRoomPanel();
                add(allroom);
                revalidate();
                repaint();
            }

            else if (arg0.getSource() == Home) {
            	// Handle the "H" click event
            	remove(allroom);
            	add(panel);
            	revalidate();
            	repaint();
            }      
        }
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
    	ListItem item =findUserById(id);
        showNewRoomPanel roomPanel = new showNewRoomPanel(listItem,item, server);
            // Implement the logic for creating a new room or any other action
    }
    
    // 리스트를 계속 업데이트해주는 스레드
    class UpdateListener extends Thread {
        public void run() {
            try {
                while (true) {
                    String updateCommand = is.readUTF();
                    String id = null;
                    String status = null;
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
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @SuppressWarnings("serial")
    class showMyProfilePanel extends JFrame implements ActionListener {
    		

    		JPanel myProfilePanel;
    		private JLabel textField;
    		private JTextField statusTextField;
    		protected JButton editBtn;
    		protected JButton closeBtn;
    		protected ListItem currentUser;
    		
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
    			
    			JLabel lblNewLabel_2 = new JLabel();
    			ImageIcon profileImageIcon = this.currentUser.getProfileImage();
    	        Image scaledProfileImage = scaleImage(profileImageIcon.getImage(), 142, 131);
    	        ImageIcon scaledProfileImageIcon = new ImageIcon(scaledProfileImage);
    	        lblNewLabel_2.setIcon(scaledProfileImageIcon);
    			lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
    			lblNewLabel_2.setBounds(133, 40, 142, 131);
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
    				updateUserSettings(os);
    				sendRequestForUserList();
    				currentStatus.setText(statusTextField.getText());
    				//ChatServer.updateUser(currentUser.getText(), statusTextField.getText());
    				//updateFriendList(listItem);
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
			private void updateUserSettings(DataOutputStream os) {
    		    String userId = currentUser.getText();
    		    String newStatus = statusTextField.getText();

    		    // Send the updated information to the server
    		    sendUserUpdate(userId, newStatus);
    		    
    		    dispose();
    		}
    		//server thread의 while로 전달됨.
    		private void sendUserUpdate(String userId, String newStatus) {
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

    	    private ImageIcon getNewProfileImage() {
    	        // Implement logic to get the new profile image (e.g., from file chooser)
    	        // Return the ImageIcon representing the new profile image
    	        return null;
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
