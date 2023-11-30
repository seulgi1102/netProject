import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;

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
		protected DataOutputStream os;
		showMyProfilePanel(ListItem currentUser, DataOutputStream os){
			this.os = os;
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
			
			statusTextField = new JTextField(currentUser.getStatus());
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
				updateUserSettings(os);
			}
		}
		private void updateUserSettings(DataOutputStream os) {
			String oldUserId = currentUser.getText(); // Assuming the current user ID is stored in getText() method

		    String newUserId = textField.getText();
		    String newStatus = statusTextField.getText();


	        ListItem updatedUser = new ListItem(newUserId, null, newStatus);

	        // Send the updated information to the server
	        sendUserUpdate(oldUserId, updatedUser, os);
	        // Dispose the profile panel
	        dispose();
	    }

		private void sendUserUpdate(String oldUserId, ListItem user, DataOutputStream os) {
		    try {
		        // Send the updated user information to the server
		        os.writeUTF("UPDATE_USER");
		        os.writeUTF(oldUserId); // Send the old user ID
		        os.writeUTF(user.getText()); // Send the new user ID
		        os.writeUTF(user.getStatus()); // Send the new status

		        // Notify the server that the data transmission is complete
		        os.writeUTF("END_UPDATE");

		        // Flush the output stream to ensure data is sent immediately
		        os.flush();
		    } catch (IOException e) {
		        e.printStackTrace();
		        // Handle the exception appropriately
		    }
		}

	    private ImageIcon getNewProfileImage() {
	        // Implement logic to get the new profile image (e.g., from file chooser)
	        // Return the ImageIcon representing the new profile image
	        return null;
	    }
	}