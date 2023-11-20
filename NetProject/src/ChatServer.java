import java.io.*;
import java.util.*;
import java.net.*;
import java.text.AttributedString;

public class ChatServer {

	static ArrayList<ServerThread> list = new ArrayList<>();
	static ArrayList<String> userList = new ArrayList<>();

	static int clientCount = 0;
	static int i = 0;
	public static void main(String[] args) throws IOException {
		ServerSocket ssocket = new ServerSocket(5000);
		
		Socket s;

		while (true) {
			s = ssocket.accept();
			
			DataInputStream is = new DataInputStream(s.getInputStream());
			DataOutputStream os = new DataOutputStream(s.getOutputStream());
			
			//서버에서 id를 받아서 스레드를 만들음
			String id = is.readUTF();
			
			String[] parts = id.split("\\|");
			String username=null;
			if(parts[0].equals("ID")) {
				username = parts[1];
				userList.add(username);
			}
			
			
			ServerThread thread = new ServerThread(s, userList.get(i), is, os);
			list.add(thread);
			thread.start();
			//os.writeUTF("ID"+"|"+userList.get(i));
			clientCount++;
			i++;
			//sendUserListToAll();
		}
		
	}

	/*
	//새로운 사용자가 로그인하면 모든 클라이언트에게 사용자 목록을 전송하는 메서드
	private static void sendUserListToAll() {
        StringBuilder userListStr = new StringBuilder();
        for (String user : userList) {
            userListStr.append(user).append(",");
        }
        userListStr.deleteCharAt(userListStr.length() - 1);  // 마지막 쉼표 제거

        for (ServerThread t : list) {
            try {
                t.os.writeUTF("updateUserList:" + userListStr.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
*/
	public static ArrayList<String>getUserList(){return userList;}
}

class ServerThread extends Thread {
	ChatServer sv = new ChatServer();
	ArrayList<String> userList = sv.getUserList();
	Scanner scn = new Scanner(System.in);
	private String name;
	final DataInputStream is;
	final DataOutputStream os;
	Socket s;
	boolean active;

	public ServerThread(Socket s, String name, DataInputStream is, DataOutputStream os) {
		this.is = is;
		this.os = os;
		this.name = name;
		this.s = s;
		this.active = true;
	}
	

	@Override
	public void run() {
		
		String message;
		while (true) {
			try {
				
				message = is.readUTF();
				System.out.println(message);
				
				
				if (message.equals("logout")) {
					this.active = false;
					this.s.close();
					break;
				}
				
				//리스트에있는 클라이언트가 쓰는 메시지를 모두에게 전송
				for (ServerThread t : ChatServer.list) {
				
					t.os.writeUTF( this.name + ":" + message); 
					
				}
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
		try {
			this.is.close();
			this.os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

