import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
    class showMyProfilePanel extends JFrame implements ActionListener{
    		

    		JPanel myProfilePanel;
    		private JLabel textField;
    		private JTextArea statusTextArea;
    		protected JButton editBtn;
    		protected JButton closeBtn;
    		protected ListItem currentUser;
    		protected JLabel lblNewLabel_2;
    		private JLabel portraitLabel;
    		private ImageIcon portraitIcon;
    		showMyProfilePanel(ListItem currentUser){
    			
    			this.currentUser = currentUser;
    			setBounds(0, 0, 412, 422);
    			myProfilePanel = new JPanel();	
    			myProfilePanel.setBounds(0, 0, 424, 404);
    			getContentPane().add(myProfilePanel);
    			myProfilePanel.setLayout(null);
    			
    			editBtn = new JButton("편집");
    			editBtn.setBounds(319, 349, 67, 23);
    			myProfilePanel.add(editBtn);
    			
    			JLabel lblNewLabel = new JLabel("상태:");
    			lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
    			lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
    			lblNewLabel.setBounds(58, 253, 43, 33);
    			myProfilePanel.add(lblNewLabel);
    			
    			closeBtn = new JButton("취소");
    			closeBtn.setBounds(12, 349, 67, 23);
    			myProfilePanel.add(closeBtn);
    			    			
    			JLabel lblNewLabel_1 = new JLabel("이름:");
    			lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
    			lblNewLabel_1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
    			lblNewLabel_1.setBounds(58, 214, 43, 29);
    			myProfilePanel.add(lblNewLabel_1);
    			
    			statusTextArea = new JTextArea(this.currentUser.getStatus());
    			statusTextArea.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
    			statusTextArea.setBorder(new LineBorder(Color.WHITE));
    			statusTextArea.setColumns(10);
    			statusTextArea.setLineWrap(true);      // Enable line wrapping
    			statusTextArea.setWrapStyleWord(true);
    			statusTextArea.setRows(3);
    			statusTextArea.setBounds(107, 253, 194, 66);
    			myProfilePanel.add(statusTextArea);
    			
    			lblNewLabel_2 = new JLabel();
    			ImageIcon profileImageIcon = this.currentUser.getProfileImage();
    	        Image scaledProfileImage = scaleImage(profileImageIcon.getImage(), 142, 131);
    	        ImageIcon scaledProfileImageIcon = new ImageIcon(scaledProfileImage);
    	        lblNewLabel_2.setIcon(scaledProfileImageIcon);
    			lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
    			lblNewLabel_2.setBounds(128, 69, 142, 131);
    			lblNewLabel_2.addMouseListener(new MouseAdapter() {
    	            @Override
    	            public void mouseClicked(MouseEvent e) {
    	                SwingUtilities.invokeLater(() -> {
    	                    // Open a file chooser
    	                    System.out.println("Mouse Clicked");
    	                    JFileChooser fileChooser = new JFileChooser();
    	                    int result = fileChooser.showOpenDialog(showMyProfilePanel.this);

    	                    // If a file is selected, update the label with the new image
    	                    if (result == JFileChooser.APPROVE_OPTION) {
    	                        ImageIcon newProfileImageIcon = new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath());
    	                        Image newScaledProfileImage = newProfileImageIcon.getImage().getScaledInstance(142, 131, Image.SCALE_SMOOTH);
    	                        ImageIcon newScaledProfileImageIcon = new ImageIcon(newScaledProfileImage);
    	                        lblNewLabel_2.setIcon(newScaledProfileImageIcon);
    	                    }
    	                });
    	            }
    	        });

    	        myProfilePanel.add(lblNewLabel_2);

    	        JPanel panel = new JPanel();
    		    panel.setBackground(new Color(197, 95, 146));
    		    panel.setBounds(0, 0, 420, 46);
    		    panel.setLayout(null);
    		    
    			JLabel profile = new JLabel("나의 프로필");
    			profile.setFont(new Font("맑은 고딕", Font.BOLD, 20));
    			profile.setBounds(12, -6, 126, 60);
    			panel.add(profile);
    			myProfilePanel.add(panel);
    			portraitIcon = new ImageIcon("C:\\net-project\\netProject\\NetProject\\src\\img\\portrait.png");
    	        int noticeWidth = 35; 
    	        int noticeHeight = 35; 
    	        Image scaledImage = portraitIcon.getImage().getScaledInstance(noticeWidth , noticeHeight, Image.SCALE_SMOOTH);
    	        ImageIcon scaledIcon = new ImageIcon(scaledImage);
    			portraitLabel = new JLabel(scaledIcon);
    			portraitLabel.setBounds(124, 5, 43, 40);
    			panel.add(portraitLabel);
    			
    			JPanel panel_1 = new JPanel();
    			panel_1.setBackground(new Color(255, 255, 255));
    			panel_1.setBounds(107, 210, 194, 33);
    			myProfilePanel.add(panel_1);
    			panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
    			
    			textField = new JLabel(this.currentUser.getText());
    			panel_1.add(textField);
    			textField.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
    			textField.setBackground(new Color(255, 255, 255));
    			closeBtn.addActionListener(this);
    			editBtn.addActionListener(this);
    			setVisible(true);
    			
    			
    		}
    		private Image scaleImage(Image image, int width, int height) {
    	        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    	    }
}
