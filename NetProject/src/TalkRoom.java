import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class TalkRoom extends JFrame {
    protected JTextField textField;
    protected static JTextArea textArea;
    protected DataInputStream is;
    protected DataOutputStream os;
    static String id;
    protected MyPanel p;
    protected Socket s;
    protected JLabel roomName;
    protected Room selectedRoom;

    public TalkRoom(String id, Room selectedRoom, Socket s) throws IOException {
        this.id = id;
        this.s = s;
        this.selectedRoom = selectedRoom;
        is = new DataInputStream(s.getInputStream());
        os = new DataOutputStream(s.getOutputStream());

        p = new MyPanel();
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    String msg = is.readUTF();
                    handleIncomingMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        });
        thread.start();
    }
    private void updateUI(Runnable runnable) {
        SwingUtilities.invokeLater(runnable);
    }
    public void appendMessageToTextArea(String message) {
        updateUI(() -> textArea.append(message));
    }
    private void handleIncomingMessage(String message) {
        if (message.startsWith("MESSAGE")) {
            String allInfo = message.substring("MESSAGE".length());
            String[] allInfoParts = allInfo.split("/");

            int roomNumber = Integer.parseInt(allInfoParts[0]);
            String senderName = allInfoParts[1];
            String messageContent = allInfoParts[2];

            if (roomNumber == selectedRoom.getRoomNumber()) {
                updateUI(() -> textArea.append(senderName + ": " + messageContent + "\n"));
            }
        }
    }

	// Internal class definition
    class MyPanel extends JPanel implements ActionListener {
        public MyPanel() {
            setBackground(new Color(249, 235, 153));
            setBounds(0, 0, 400, 600);
            textField = new JTextField(30);
            textField.setBounds(12, 506, 357, 40);
            textField.addActionListener(this);

            textArea = new JTextArea(10, 30);
            textArea.setBounds(12, 53, 357, 435);
            textArea.setEditable(false);
            setLayout(null);

            roomName = new JLabel(selectedRoom.getRoomName());
            roomName.setBounds(90, 8, 200, 40);
            add(roomName);

            add(textField);
            add(textArea);

            setVisible(true);
        }

        public void actionPerformed(ActionEvent evt) {
            String message = textField.getText();
            try {
                os.writeUTF("MESSAGE" + selectedRoom.getRoomNumber() + "/" + id + "/" + message);
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            textArea.append("SENT: " + message + "\n");
            textField.selectAll();
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }

    public static void main(String[] args) throws IOException {
        // Here you can add the code to connect to the server, create a room, etc.
        // For example:
        // Socket socket = new Socket("localhost", 5000);
        // Room room = new Room(...);
        // new TalkRoom("User1", room, socket);
    }
}