import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ChatServer {

    static ArrayList<ServerThread> list = new ArrayList<>();
    static ArrayList<String> userList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket ssocket = new ServerSocket(5001);

        while (true) {
            Socket s = ssocket.accept();
            DataInputStream is = new DataInputStream(s.getInputStream());
            DataOutputStream os = new DataOutputStream(s.getOutputStream());

            // 클라이언트로부터 받은 유저아이디를 읽음
            String id = is.readUTF();
            String userId = addUser(id);

            // 연결된 클라이언트들에게 유저리스트를 전송
            sendUserList();
            
            ServerThread thread = new ServerThread(s, userId, userList, is, os);
            list.add(thread);
            thread.start();
        }
    }

    // 리스트에 유저를 추가하고 유저의 아이디를 반환
    public static String addUser(String id) {
        String userId = null;
        if (id.startsWith("ID:")) {
            userId = id.substring(3);
            userList.add(userId);
        }
        return userId;
    }

    // 연결된 클라이언트들에게 현재 사용자 목록을 모두 클라이언트에게 전송, 모두 전송하면 END
    public static void sendUserList() {
        for (ServerThread t : list) {
            try {
                t.os.writeUTF("UPDATE");
                for (String user : userList) {
                    t.os.writeUTF(user);
                }
                // Signal the end of the user list
                t.os.writeUTF("END");
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
    ArrayList<String> uList;
	private boolean active;

    public ServerThread(Socket s, String name, ArrayList<String> list, DataInputStream is, DataOutputStream os) {
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
            for (String user : uList) {
                os.writeUTF(user);
            }
            // 리스트의 끝을 사용자에게 알림
            os.writeUTF("END");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
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


