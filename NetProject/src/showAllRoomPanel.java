import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class showAllRoomPanel extends JPanel implements ListSelectionListener {
    protected JList<Room> roomList;
    protected JScrollPane scrollPane;
    protected ArrayList<Room> allRoom;
    protected Socket s;
    protected DataInputStream is;
    protected DataOutputStream os;
    protected JPanel panel;
    protected String id;
    public showAllRoomPanel(ArrayList<Room> allRoom, Socket s, String id) {
    	this.allRoom = allRoom;
    	this.id = id;
    	this.s = s;
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
        setLayout(new BorderLayout());
        setBounds(57, 0, 343, 600);
        panel = new JPanel();
        panel.setBackground(new Color(253, 237, 172));
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("My Room");
        label.setFont(new Font("굴림", Font.BOLD, 20));
        panel.add(label, BorderLayout.NORTH);

        roomList = new JList<>();
        roomList.setCellRenderer(new CustomListCellRenderer2());
        updateRoomList(list);
        roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        roomList.addListSelectionListener(this);

        scrollPane = new JScrollPane(roomList);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel, BorderLayout.CENTER);
    }
    public void valueChanged(ListSelectionEvent e) {
    	if(!e.getValueIsAdjusting()) {
    		Room selectedRoom = roomList.getSelectedValue();
    		
    		if(selectedRoom != null) {
    			int roomNumber = selectedRoom.getRoomNumber();
    			String roomName = selectedRoom.getRoomName();
    			
    			try {
    				navigateToTalkRoom(selectedRoom);
    			}catch(IOException el) {
    				el.printStackTrace();
    			}
    		}
    	}
    }
	private void navigateToTalkRoom(Room selectedRoom) throws IOException {
		// TODO Auto-generated method stub
		ChatClient.container.remove(ChatClient.wait);
		TalkRoom talkRoom = new TalkRoom(id, selectedRoom, s);
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