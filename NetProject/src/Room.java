import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class Room {
    private Integer roomNumber = 0;
    private String roomName = "";
    private ArrayList<ListItem> roomItems;
    ArrayList<String> UList = new ArrayList<>();
    private String roomNotice ="공지사항을 입력해주세요.";
    private Integer roomImageNumber = 0;
    private String roomContent = "";
    public Room(Integer roomNumber, String roomName, ArrayList<ListItem> roomItems) {
        this.roomNumber = roomNumber;
        this.roomName = roomName;
        this.roomItems = roomItems;
        
    }
    public void setRoomContent(String content) {
    	this.roomContent = content;
    }
    public String getRoomContent() {
    	return roomContent;
    }
    public void setImageNumber(Integer number) {
    	this.roomImageNumber = number;
    }
    public Integer getImageNumber() {
    	return roomImageNumber;
    }
    public void setNotice(String roomNotice) {
    	this.roomNotice =roomNotice;
    }
    public String getNotice() {
    	return roomNotice;
    }
    public void setUserList(ArrayList<String> userList) {
    	UList = userList;
    }
    public ArrayList<String> getUserList(){
    	return UList;
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