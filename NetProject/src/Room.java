import java.util.ArrayList;
import javax.swing.*;

public class Room {
    private Integer roomNumber = 0;
    private String roomName = null;
    private ArrayList<ListItem> roomItems;
   
    public Room(Integer roomNumber, String roomName, ArrayList<ListItem> roomItems) {
        this.roomNumber = roomNumber;
        this.roomName = roomName;
        this.roomItems = roomItems;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(Integer roomNumber) {
    	this.roomNumber = roomNumber;
    }
    public String getRoomName() {
        return roomName;
    }
   

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setListItem(ArrayList<ListItem> roomItems) {
        this.roomItems = roomItems;
    }

    public ArrayList<ListItem> getRoomItems() {
        return roomItems;
    }


}