import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.io.IOException;

//소스를 입력하고 Ctrl+Shift+O를 눌러서 필요한 파일을 포함한다. 
public class ChatClient implements ActionListener{
	static JPanel present = new JPanel();
	static Login login;
	static JFrame frame;
	static TalkRoom talk;
	static WaitRoom wait;
	static Container container;
	ArrayList<String> userList;
	ArrayList<WaitRoom> roomList = new ArrayList<>();
	int i = 0;
	public ChatClient() throws IOException {
		frame = new JFrame();
		frame.setBounds(0,0,400,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		container = frame.getContentPane();
		login = new Login();
		container.add(login);
		login.LoginBtn.addActionListener(this);
		frame.setVisible(true);
	}
	void showWaitRoom(String id, String ip, Integer port) throws IOException {
        container.remove(login);
        wait = new WaitRoom();
        //
        ChatServer chatServer = new ChatServer();
        wait.setChatServer(chatServer);
        wait.start(id, ip, port);
        //
        //roomList.add(wait);
        //handleLogin(roomList, id);
        container.add(wait);
        //사용자 추가
       // wait.addNewUser(id);
// 프레임 다시 표시
        frame.revalidate();
        frame.repaint();
	}
	
	private void showTalkRoom(String id, String ip, Integer port ) throws IOException {
        container.remove(login);
        talk = new TalkRoom(id, ip, port);
        
        container.add(talk.p);
        
// 프레임 다시 표시
        frame.revalidate();
        frame.repaint();
    }
	public void connection(String id, String ip, Integer port) {}
	
	static void setDefault (Login login){
		present.add(login);
		present.setVisible(true);
		
	}
	/*
	public void handleLogin(ArrayList<WaitRoom> room, String newUser) {
	
		
		for(WaitRoom data:roomList) {
			roomList.get(i).userLoggedIn(newUser);
		}
        // Assume waitRoom is an instance of WaitRoom
        
        }*/
	@Override
	public void actionPerformed(ActionEvent e) {
			if(e.getSource()== login.LoginBtn) {
				String id = login.editUser.getText();
				String ip = login.editIP.getText();
				Integer port = Integer.parseInt(login.editPort.getText());
				//???
				//ArrayList<String> list = new ChatServer().getUserList();
				try {
					
					showWaitRoom(id, ip, port);
		            //showTalkRoom(id, ip, port);
		        } catch (IOException ex) {
		            ex.printStackTrace();
		        }
			}
			// TODO Auto-generated method stub*/

		}
	/*
	// Method to handle updates from the server
    public void handleServerUpdate(String message) {
        // Check if the message contains a user list update
        if (message.startsWith("USERLIST:")) {
            String userListStr = message.substring(9);
            String[] users = userListStr.split(",");
            ArrayList<String> updatedUserList = new ArrayList<>(Arrays.asList(users));

            // Update the WaitRoom with the new user list
            if (wait != null) {
                wait.updateFriendList(updatedUserList);
            }
        }
    }*/
	public static void main(String[] args) throws IOException{
		new ChatClient();
	}
		
}
	

