import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ChatServer {

    static ArrayList<ServerThread> list = new ArrayList<>();
    static ArrayList<ListItem> itemList = new ArrayList<>();
    static ArrayList<Room> roomList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket ssocket = new ServerSocket(5002);

        while (true) {
            Socket s = ssocket.accept();
            DataInputStream is = new DataInputStream(s.getInputStream());
            DataOutputStream os = new DataOutputStream(s.getOutputStream());

            // 클라이언트로부터 받은 유저 정보를 읽음
           
            String userInfo = is.readUTF();
            ListItem userItem = addUser(userInfo);
        

         
            // 연결된 클라이언트들에게 유저리스트를 전송
            sendUserList();
            
            ServerThread thread = new ServerThread(s, userItem.getText(), itemList, is, os);
            list.add(thread);
            thread.start();
        }
    }
    //**
    public static void updateUser(String userId, String newStatus) {
        ListItem userToUpdate = findUserById(userId);
        if (userToUpdate != null) {
            userToUpdate.setStatus(newStatus);
            sendUserList(); // Notify all connected clients about the update
        }
    }
    // 리스트에 유저를 추가하고 유저의 아이디를 반환
    public static ListItem addUser(String userInfo) {
        String userId = null;
        String userStatus = null;
        ListItem listItem = null;

        String[] parts = userInfo.split("/");
        
        for (String part : parts) {
            if (part.startsWith("ID:")) {
                userId = part.substring(3);
            }
            if (part.startsWith("STATUS:")) {
            	userStatus = part.substring(7);
            }
        }
            listItem = findUserById(userId);

            if (listItem == null) {
                // If the user is not in the list, add a new ListItem
                listItem = new ListItem(userId, null, null);
                itemList.add(listItem);
            }else if(!listItem.getStatus().equals(null)){
            	listItem.setStatus(listItem.getStatus());
            }
            else { listItem.setStatus(userStatus);}
               
                
                System.out.println("User ID: " + listItem.getText());
                System.out.println("Received Status: " + userStatus);
                System.out.println("Updated Status: " + listItem.getStatus());
                System.out.println("Updated itemList:");
                for (ListItem item : itemList) {
                    System.out.println("ID: " + item.getText() + ", Status: " + item.getStatus());
                }

        return listItem;
    }

    //아이디로 해당유저의 아이템을 찾는 메서드
    public static ListItem findUserById(String userId) {
        for (ListItem item : itemList) {
            if (item.getText().equals(userId)) {
                return item;
            }
        }
        return null;
    }
    // 연결된 클라이언트들에게 현재 사용자 목록을 모두 클라이언트에게 전송, 모두 전송하면 END, to updateListener
    public static void sendUserList() {
        for (ServerThread t : list) {
            try {
                t.os.writeUTF("UPDATE");
                for (ListItem item : itemList) {
                	t.os.writeUTF("ID:" + item.getText() + "/"+"STATUS:" + item.getStatus());
                    //t.os.writeUTF("STATUS:"+item.getStatus()); // 
                    //t.os.writeUTF("END");
                	t.os.flush();
                }
                t.os.writeUTF("END");
                t.os.flush(); // Ensure data is sent immediately
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
class ServerThread extends Thread {
    private String name;
    final DataInputStream is;
    final DataOutputStream os;
    Socket s;
    ArrayList<ListItem> uList;
	private boolean active;

    public ServerThread(Socket s, String name, ArrayList<ListItem> list, DataInputStream is, DataOutputStream os) {
        this.is = is;
        this.os = os;
        this.name = name;
        this.s = s;
        this.uList = list;
    }

    @Override
    public void run() {
    	try {
        //모든 연결된 사용자에게 리스트가 업데이트될 필요가 있음을 알림, 새로운 유저가 로그인한경우
            os.writeUTF("UPDATE");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 업데이트된 리스트를 다시 전송
        try {
            for (ListItem user : uList) {
            	//로그인한 유저에게 현재 유저리스트의 유저들 정보 보내기. 처음로그인한 유저는 이 정보를 받아서 jlist를 표시
                os.writeUTF("ID:"+user.getText()+"/"+"STATUS:"+user.getStatus());
                //os.writeUTF(user.getStatus());
                os.flush();
                System.out.println(user.getText());
                System.out.println(user.getStatus());
                
            }
            // 리스트의 끝을 사용자에게 알림
            os.writeUTF("END");
            os.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
		String message;
		while (true) {
			try {
				//실시간으로 클라이언트에게서 메시지를 받음, 실시간 업데이트 원할때 키워드를 받으면 필요한 기능을 수행하도록 수정
				message = is.readUTF();
				System.out.println(message);
				if (message.startsWith("UPDATE")) {
					//여기서 sendUserUpdate 함수로부터 유저의 status를 실시간으로 받아서 item을 갱신. 수정하지마셍
		            // Handle the update command
		            String userId = is.readUTF(); // Read the user ID
		            String newStatus = is.readUTF();
		            System.out.println("userId: "+userId+" status: "+newStatus);
		            ListItem userToUpdate = ChatServer.findUserById(userId);
		            if (userToUpdate != null) {
		                userToUpdate.setStatus(newStatus);
		                }
				}//edit버튼을 누르면 리스트를 가져오기
				if (message.equals("REQUEST_USER_LIST")) {ChatServer.sendUserList();}
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