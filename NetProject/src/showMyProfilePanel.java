import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Image;

@SuppressWarnings("serial")
class showMyProfilePanel extends JFrame implements ActionListener {
		

		JPanel myProfilePanel;
		private JLabel textField;
		private JTextField statusTextField;
		protected JButton editBtn;
		protected JButton closeBtn;
		protected ListItem currentUser;
		protected DataOutputStream os; 
		private JLabel lblNewLabel_2;
		showMyProfilePanel(ListItem currentUser, DataOutputStream os){
			this.os = os;
			this.currentUser = currentUser;
			setBounds(0, 0, 434, 422);
			myProfilePanel = new JPanel();	
			myProfilePanel.setBounds(0, 0, 424, 404);
			getContentPane().add(myProfilePanel);
			myProfilePanel.setLayout(null);
			
			editBtn = new JButton("편집");
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
			
			textField = new JLabel(this.currentUser.getText());
			textField.setBounds(133, 181, 194, 29);
			myProfilePanel.add(textField);
			
			
			JLabel lblNewLabel_1 = new JLabel("이름:");
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_1.setFont(new Font("맑은 고딕", Font.BOLD, 13));
			lblNewLabel_1.setBounds(78, 179, 55, 29);
			myProfilePanel.add(lblNewLabel_1);
			
			statusTextField = new JTextField(this.currentUser.getStatus());
			statusTextField.setColumns(10);
			statusTextField.setBounds(133, 219, 194, 120);
			myProfilePanel.add(statusTextField);
			
			 lblNewLabel_2 = new JLabel();
		     ImageIcon profileImageIcon = this.currentUser.getProfileImage();
		     Image scaledProfileImage = scaleImage(profileImageIcon.getImage(), 142, 131);
		     ImageIcon scaledProfileImageIcon = new ImageIcon(scaledProfileImage);
		     lblNewLabel_2.setIcon(scaledProfileImageIcon);
		     lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		     lblNewLabel_2.setBounds(133, 40, 142, 131);
		     lblNewLabel_2.addMouseListener(new MouseAdapter() {
		            @Override
		            public void mouseClicked(MouseEvent e) {
		                openFileChooser();
		            }
		        });
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
		private void openFileChooser() {
	        JFileChooser fileChooser = new JFileChooser();
	        int result = fileChooser.showOpenDialog(this);

	        if (result == JFileChooser.APPROVE_OPTION) {
	            // Get the selected file
	            java.io.File selectedFile = fileChooser.getSelectedFile();

	            // Update the profile image
	            ImageIcon newProfileImageIcon = new ImageIcon(selectedFile.getPath());
	            Image scaledProfileImage = scaleImage(newProfileImageIcon.getImage(), 142, 131);
	            ImageIcon scaledProfileImageIcon = new ImageIcon(scaledProfileImage);
	            lblNewLabel_2.setIcon(scaledProfileImageIcon);

	            // You may want to save the new profile image or handle it as needed
	            // For example, you can call getNewProfileImage() to get the updated ImageIcon
	            ImageIcon updatedProfileImage = getNewProfileImage();
	            // Further logic...
	        }
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
				dispose();
			}
		}
		private void updateUserSettings(DataOutputStream os) {
		    String userId = currentUser.getText();
		    String newStatus = statusTextField.getText();
		    ImageIcon newProfileImage = getNewProfileImage();
		    // Send the updated information to the server
		    sendUserUpdate(userId, newStatus, newProfileImage, os);
		    // Dispose the profile panel
		    dispose();
		}

		private void sendUserUpdate(String userId, String newStatus, ImageIcon newProfileImage, DataOutputStream os) {
		    try {
		      
		        os.writeUTF("UPDATE");
		        os.writeUTF("CURRENTUID:"+userId); // Send the old user ID 
		        os.writeUTF("STATUS:"+newStatus); // Send the new status
		        
		        ByteArrayOutputStream baos = new ByteArrayOutputStream();
		        ObjectOutputStream oos = new ObjectOutputStream(baos);
		        oos.writeObject(newProfileImage);
		        byte[] imageBytes = baos.toByteArray();
		        os.writeInt(imageBytes.length);
		        os.write(imageBytes);
		        
		        os.writeUTF("END");

		        // Flush the output stream to ensure data is sent immediately
		        os.flush();
		    } catch (IOException e) {
		        e.printStackTrace();
		        // Handle the exception appropriately
		    }
		}

	    private ImageIcon getNewProfileImage() {
	       
	        return null;
	    }
	   
	}