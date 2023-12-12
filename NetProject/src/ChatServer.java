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
        ServerSocket ssocket = new ServerSocket(5001);

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
    public static void sendMessage(ServerThread t, String userName, String message) {
        try {
            if (t.s != null && !t.s.isClosed()&& !t.getThreadName().equals(userName)) {
                t.os.writeUTF("MESSAGE" +userName+"/"+message);
                t.os.flush();
                System.out.println("SENT: " + userName + " To: " + t.getThreadName() + " : " + message);
            } else {
                // Handle the case where the socket is closed (e.g., client disconnected)
                // You might want to remove the ServerThread from the list in this case
            }
        } catch (IOException e) {
            // Handle the exception (e.g., log or print the error)
            e.printStackTrace();
        }
    }

    // 연결된 클라이언트들에게 현재 사용자 목록을 모두 클라이언트에게 전송, 모두 전송하면 END, to updateListener
    public static void sendUserList() {
        for (ServerThread t : list) {
            try {
            	if (t.s != null && !t.s.isClosed()) {
                t.os.writeUTF("UPDATE");
                for (ListItem item : itemList) {
                	t.os.writeUTF("ID:" + item.getText() + "/"+"STATUS:" + item.getStatus());
                    //t.os.writeUTF("STATUS:"+item.getStatus()); // 
                    //t.os.writeUTF("END");
                	//t.os.flush();
                }
                t.os.writeUTF("END");
                t.os.flush(); // Ensure data is sent immediately
                }
            } catch (IOException e) {
                e.printStackTrace();
                list.remove(t);
            }
        }
    }
    public static void sendRoomInfo(ArrayList<Room> userRooms, ServerThread thread) throws IOException {
            try {
            	 if (thread.s != null && !thread.s.isClosed()) {
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
            	
	            } else {
	                System.out.println("Socket is closed. Unable to send room info.");
	            	}
            	 } catch (IOException e) {
	            e.printStackTrace();
            }
        
    }
	public static Room getRoomByRoomNumber(int roomNumber) {
		Room userRoom = null;
		ArrayList<Room> userRooms = new ArrayList<>();
		for (Room room : roomList) {
			if(room.getRoomNumber().equals(roomNumber)) {
				userRoom = room;
				break;
			}
		}
		return userRoom;
	}
	public static ServerThread findUserThreadById(String userId) {
    for (ServerThread thread : list) {
        if (thread.getThreadName().equals(userId)) {
            return thread;
        }
    }
    return null;
}
	
}
class ServerThread extends Thread {
    private String name;
    final DataInputStream is;
    final DataOutputStream os;
    Socket s;
    ArrayList<ListItem> uList;
    private boolean active;
    private static ArrayList<Room> rooms;

    private ArrayList<Room> currentUserRooms = new ArrayList<>();

    public ServerThread(Socket s, String name, ArrayList<Room> rooms, ArrayList<ListItem> list, DataInputStream is, DataOutputStream os) {
        this.is = is;
        this.os = os;
        this.name = name;
        this.s = s;
        this.uList = list;
        this.rooms = rooms;
        this.active = true;
        this.currentUserRooms = getRoomById(name);
    }
    static ArrayList<Room> getRoomById(String userId) {
    	Room userRoom = null;
    	ArrayList<Room> userRooms = new ArrayList<>();
        for (Room room : rooms) {
        	
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
    public String getThreadName() {
        return name;
    }

    @Override
    public void run() { //처음로그인한 사람이 이것을 전달받아 화면에띄움
        try {
            os.writeUTF("UPDATE");
            for (ListItem user : uList) {
                os.writeUTF("ID:" + user.getText() + "/" + "STATUS:" + user.getStatus());
                os.flush();
            }
            os.writeUTF("END");
            os.flush();
        } catch (IOException e) {
            handleSocketException(e);
        }

        String message;
        while (active) {
            try {
                message = is.readUTF();
                System.out.println(message);

                //String message = is.readUTF();
                if(message.startsWith("MESSAGE")) {
					//String allInfo = is.readUTF();
					String allInfo = message.substring(7);
					String[] allInfoParts = allInfo.split("/");
					
					int roomNumber = Integer.parseInt(allInfoParts[0]);
					String userName = allInfoParts[1];
					String messages = allInfoParts[2];
					System.out.println("Server: while() / roomNumber: "+roomNumber+" userNmae: "+userName+" message: "+messages);
					Room room = ChatServer.getRoomByRoomNumber(roomNumber);
					ArrayList<ServerThread> Threads = findUserThreadByRoom(room);
					for(ServerThread t : Threads) {
						ChatServer.sendMessage(t,userName,messages);
					}

				}
                if (message.startsWith("UPDATE")) {
                    String userId = is.readUTF();
                    String newStatus = is.readUTF();
                    System.out.println("userId: " + userId + " status: " + newStatus);
                    ListItem userToUpdate = ChatServer.findUserById(userId);
                    if (userToUpdate != null) {
                        userToUpdate.setStatus(newStatus);
                    }
                }

                if (message.equals("REQUEST_USER_LIST")) {
                    ChatServer.sendUserList();
                }

                if (message.equals("REQUEST_ROOM_LIST")) {
                	if(!(currentUserRooms == null)) {
                   ArrayList<ServerThread> threads = findUserThreadByCurrentRooms(currentUserRooms);
                   
                   for (ServerThread thread : threads) {
                        ArrayList<Room> updatedUserRooms = ChatServer.getRoomById(thread.getThreadName());
                        ChatServer.sendRoomInfo(updatedUserRooms, thread);
                	
                    }
                   }
                
                }

                if (message.startsWith("ROOM")) {
                    ArrayList<String> selectedUsers = new ArrayList<>();
                    ArrayList<ListItem> selectedUserList = new ArrayList<>();
                    try {
                        String data;
                        while (!(data = is.readUTF()).equals("/")) {
                            selectedUsers.add(data);
                        }

                        String roomSubject = is.readUTF();

                        for (String user : selectedUsers) {
                            selectedUserList.add(ChatServer.findUserById(user));
                        }

                        Room room = new Room(ChatServer.getNextRoomNumber(), roomSubject, selectedUserList);
                        System.out.println("Room no:" + room.getRoomNumber() + " Room name:" + room.getRoomName());

                        for (ListItem item : room.getRoomItems()) {
                            System.out.println("Selected users:" + item.getText());
                        }

                        ChatServer.roomList.add(room);
                        currentUserRooms = ChatServer.getRoomById(name);
                        ChatServer.sendRoomInfo(currentUserRooms, this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (message.equals("logout")) {
                    this.active = false;
                    this.s.close();
                    break;
                }

               
            } catch (SocketException e) {
                handleSocketException(e);
            } catch (IOException e) {
                handleSocketException(e);
            }
        }

        try {
            this.is.close();
            this.os.close();
            this.s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void handleSocketException(IOException e) {
        if (e instanceof SocketException && e.getMessage().equalsIgnoreCase("Connection reset")) {
            System.out.println("Client disconnected: " + name);
            active = false;
            try {
                is.close();
                os.close();
                s.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } else {
            e.printStackTrace();
        }
    }
    public static ArrayList<ServerThread> findUserThreadByRoom(Room room) {
        ArrayList<ServerThread> selectedThreads = new ArrayList<>();
        for (ServerThread thread : ChatServer.list) {
            ArrayList<ListItem> items = room.getRoomItems();
            for (ListItem item : items) {
                if (thread.getThreadName().equals(item.getText())) {
                    selectedThreads.add(thread);
                    System.out.println("방의 참여자:" + item.getText());
                }
            }
        }
        return selectedThreads;
    }

    public static ArrayList<ServerThread> findUserThreadByCurrentRooms(ArrayList<Room> currentRooms) {
        ArrayList<ServerThread> selectedThreads = new ArrayList<>();
        
        for (ServerThread thread : ChatServer.list) {
            for (Room room : currentRooms) {
            	if (room != null) { 
                ArrayList<ListItem> items = room.getRoomItems();
                for (ListItem item : items) {
                    if (thread.getThreadName().equals(item.getText())) {
                        if (!selectedThreads.contains(thread)) {
                            selectedThreads.add(thread);
                        }
                    }
                    }
                }
            
            }
        }
        
        return selectedThreads;
    }
}