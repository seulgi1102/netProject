import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class showAllRoomPanel extends JPanel {
    public showAllRoomPanel() {
        gui();
    }

    private void gui() {
        setLayout(new BorderLayout());
        setBounds(57, 0, 343, 600);
        JPanel panel = new JPanel();
        panel.setBackground(new Color(253, 237, 172));
        panel.setLayout(null);

        JLabel label = new JLabel("All Room Content");
        label.setFont(new Font("굴림", Font.BOLD, 20));
        label.setBounds(12, 5, 200, 31);
        panel.add(label);

        // Add other components as needed

        add(panel, BorderLayout.CENTER);
    }
}