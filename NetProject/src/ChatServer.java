import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {

    static ArrayList<ServerThread> list = new ArrayList<>();
    private static ArrayList<ServerThread> threadList = new ArrayList<>();
    static ArrayList<ListItem> itemList = new ArrayList<>();
    static ArrayList<Room> roomList = new ArrayList<>();
    private static Integer roomNumber = 0;
    public static void main(String[] args) throws IOException {
        ServerSocket ssocket = new ServerSocket(5002);

        while (true) {
            Socket s = ssocket.accept();
            DataInputStream is = new DataInputStream(s.getInputStream());
            DataOutputStream os = new DataOutputStream(s.getOutputStream());

            // 클라이언트로부터 받은 유저 정보를 읽음
           
            String userInfo = is.readUTF();
            ListItem userItem = addUser(userInfo);
            //현재 유저의 방 리스트를 얻어왔음.
            ArrayList<Room> userRooms = getRoomById(userItem.getText());
            
            // 연결된 클라이언트들에게 유저리스트를 전송
            sendUserList();
            // 연결된 클라이언트들에게  현재 방 리스트를 전송.
            
            ServerThread thread = new ServerThread(s, userItem.getText(),roomList, itemList, is, os);
            list.add(thread);
            
            //sendRoomInfo(userRooms, thread);
            thread.start();
        }
    }
    public static Integer getNextRoomNumber() {
        return roomNumber++;
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
    //아이디로 룸 찾기
    static ArrayList<Room> getRoomById(String userId) {
    	Room userRoom = null;
    	ArrayList<Room> userRooms = new ArrayList<>();
        for (Room room : roomList) {
        	
        	ArrayList<ListItem> items = room.getRoomItems();
        	for(ListItem item : items) {
        		if (item.getText().equals(userId)) {
        			
                	userRoom = room;
                    break;
                }
        	}
        	//-------
        	if(!userRooms.contains(userRoom)) {
            userRooms.add(userRoom);
            }
        }
        return userRooms;
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
    //선택받은 유저들에게만 sendRoomList하기위해 스레드리스트를 만들기

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
    public static void sendRoomInfo(ArrayList<Room> userRooms, ServerThread thread) throws IOException {
            try {
            	//userRooms에 방이 없는경우
            	thread.os.writeUTF("ROOM_UPDATE");
            	for (Room room : userRooms) {
            		if (room != null) {
            		//thread.os.writeUTF("RNAME:" + room.getRoomName() + "/"+"RNUM:" + room.getRoomNumber() + "/"+"RITEMS"+room.getRoomItems());
            		thread.os.writeUTF("RNAME:" + room.getRoomName() + "/"+"RNUM:" + room.getRoomNumber());
            		thread.os.flush();
            		System.out.println("RNAME:" + room.getRoomName() + "/"+"RNUM:" + room.getRoomNumber());
            	
            		}
            	}
            	thread.os.writeUTF("END");
            	thread.os.flush();
            	
	            } catch (IOException e) {
	            e.printStackTrace();
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
	private ArrayList<Room> rooms;
	private ArrayList<Room> currentUserRooms = new ArrayList<>();
    public ServerThread(Socket s, String name, ArrayList<Room> rooms, ArrayList<ListItem> list,DataInputStream is, DataOutputStream os) {
        this.is = is;
        this.os = os;
        this.name = name;
        this.s = s;
        this.uList = list;
        this.rooms = rooms;
    }
    public String getThreadName() {
    	return name;
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
				if (message.equals("REQUEST_USER_LIST")) { ChatServer.sendUserList();}
				
				if (message.equals("REQUEST_ROOM_LIST")) {
				    // Get the room list of each user in the current room
				            ArrayList<ServerThread> Threads = findUserThreadByRoom(currentUserRooms);
				            for (ServerThread thread : Threads) {
				                // Get the updated room list for the user
				                ArrayList<Room> updatedUserRooms = ChatServer.getRoomById(thread.getThreadName());
				                // Send the updated room list to the user
				                ChatServer.sendRoomInfo(updatedUserRooms, thread);
				            } 
				    }
				
				//방만들기 시 신호와함께 유저리스트, 방제 보낸것을 받음
				
				if (message.startsWith("ROOM")) {
					//String message2 = is.readUTF();
					ArrayList<String> selectedUsers = new ArrayList<>();
					 ArrayList<ListItem> selectedUserList = new ArrayList<>();
					 
					try {
					    

					    // Read "ROOM" and user names
					    String data;
					    while (!(data = is.readUTF()).equals("/")) {
					    	//data = is.readUTF();
					        selectedUsers.add(data);
					    }

					    // Read room subject
					    String roomSubject = is.readUTF();

					    // Process the received data
					   
					    for (String user : selectedUsers) {
					        selectedUserList.add(ChatServer.findUserById(user));
					    }

					    // Create room and print information
					    Room room = new Room(ChatServer.getNextRoomNumber(), roomSubject, selectedUserList);
					    System.out.println("Room no:" + room.getRoomNumber() + " Room name:" + room.getRoomName());

					    for (ListItem item : room.getRoomItems()) {
					        System.out.println("Selected users:" + item.getText());
					    }
					    //만들어진 방을 서버의 방리스트에 추가 
					    ChatServer.roomList.add(room);
					    //현재유저의 초대받은 방들 찾기
					    currentUserRooms = ChatServer.getRoomById(name);
					    //현재 유저에게 초대받은 방을 보내기
					    ChatServer.sendRoomInfo(currentUserRooms, this);

					    /*
					    try {
					        Thread.sleep(10000); // Delay (for demonstration purposes)
					    } catch (InterruptedException e) {
					        e.printStackTrace();
					    }*/
					} catch (IOException e) {
					    e.printStackTrace();
					    // Handle the exception appropriately (e.g., close resources, log, etc.)
					}
				}
				
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
	public static ArrayList<ServerThread> findUserThreadByRoom(ArrayList<Room> currentRooms) {
		 ArrayList<ServerThread> selectedThreads = new ArrayList<>();
    for (ServerThread thread : ChatServer.list) {
    	for (Room room : currentRooms) {
        	ArrayList<ListItem> items = room.getRoomItems();
        	for(ListItem item : items) {
		        if (thread.getThreadName().equals(item.getText())) {
		        	if (!selectedThreads.contains(thread)) {
                        selectedThreads.add(thread);
                    }
		        }
	        }
	    }
    }
    return selectedThreads;
}

}