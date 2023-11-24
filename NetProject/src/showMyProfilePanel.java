import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Image;

class showMyProfilePanel extends JFrame implements ActionListener {
		
		JPanel myProfilePanel;
		private JTextField textField;
		private JTextField statusTextField;
		protected String currentUserId;
		protected JButton editBtn;
		protected JButton closeBtn;
		protected ListItem currentUser;
		showMyProfilePanel(ListItem currentUser){
			this.currentUser = currentUser;
			setBounds(0, 0, 434, 422);
			myProfilePanel = new JPanel();	
			myProfilePanel.setBounds(0, 0, 424, 404);
			getContentPane().add(myProfilePanel);
			myProfilePanel.setLayout(null);
			
			JButton editBtn = new JButton("편집");
			editBtn.setBounds(327, 349, 81, 23);
			myProfilePanel.add(editBtn);
			
			JLabel lblNewLabel = new JLabel("상태 메시지:");
			lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 13));
			lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel.setBounds(34, 208, 99, 33);
			myProfilePanel.add(lblNewLabel);
			
			closeBtn = new JButton("취소");
			closeBtn.setBounds(260, 349, 67, 23);
			myProfilePanel.add(closeBtn);
			
			textField = new JTextField(currentUser.getText());
			textField.setBounds(133, 181, 194, 29);
			myProfilePanel.add(textField);
			textField.setColumns(10);
			
			JLabel lblNewLabel_1 = new JLabel("이름:");
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_1.setFont(new Font("맑은 고딕", Font.BOLD, 13));
			lblNewLabel_1.setBounds(78, 179, 55, 29);
			myProfilePanel.add(lblNewLabel_1);
			
			statusTextField = new JTextField();
			statusTextField.setColumns(10);
			statusTextField.setBounds(133, 219, 194, 120);
			myProfilePanel.add(statusTextField);
			
			JLabel lblNewLabel_2 = new JLabel();
			ImageIcon profileImageIcon = currentUser.getProfileImage();
	        Image scaledProfileImage = scaleImage(profileImageIcon.getImage(), 142, 131);
	        ImageIcon scaledProfileImageIcon = new ImageIcon(scaledProfileImage);
	        lblNewLabel_2.setIcon(scaledProfileImageIcon);
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_2.setBounds(133, 40, 142, 131);
			myProfilePanel.add(lblNewLabel_2);
			
			JLabel lblNewLabel_1_1 = new JLabel("나의 프로필");
			lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_1_1.setFont(new Font("맑은 고딕", Font.BOLD, 17));
			lblNewLabel_1_1.setBounds(12, 10, 160, 44);
			myProfilePanel.add(lblNewLabel_1_1);
			closeBtn.addActionListener(this);
			editBtn.addActionListener(this);
			
			
			setVisible(true);
		}
		private Image scaleImage(Image image, int width, int height) {
	        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	    }

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()== closeBtn) {
				dispose();
				// TODO Auto-generated method stub
			}
			if(e.getSource()==editBtn) {
				updateUserSettings();
			}
		}
		private void updateUserSettings() {
	        // Update the status and profile image for the currently logged-in user
	        String newStatus = statusTextField.getText();
	        // Set the profile image based on user input (you'll need to implement this)
	        ImageIcon newProfileImage = getNewProfileImage(); 

	        currentUser.setStatus(newStatus);
	        currentUser.setProfileImage(newProfileImage);

	        // Send the updated information to the server
	        sendUserUpdate(currentUser);

	        // Update the JList with the new information
	       // updateFriendList(idList);
	    }

	    private void sendUserUpdate(ListItem user) {
	        try {
	            // Modify this method to send the updated profile information to the server
	            // (e.g., user ID, new status, new profile image)
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    private ImageIcon getNewProfileImage() {
	        // Implement logic to get the new profile image (e.g., from file chooser)
	        // Return the ImageIcon representing the new profile image
	        return null;
	    }
	}