import javax.swing.*;

public class ListItem {
    private String text;  // User ID
    private ImageIcon profileImage;
    private String status;
    private static final ImageIcon DEFAULT_PROFILE_IMAGE = new ImageIcon("C:\\Users\\USER\\git\\netProject\\NetProject\\src\\img\\defaultProfile.jpeg");
    
    public ListItem(String text, ImageIcon profileImage, String status) {
        this.text = text;
        this.profileImage = (profileImage != null) ? profileImage : DEFAULT_PROFILE_IMAGE;
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public ImageIcon getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ImageIcon profileImage) {
        this.profileImage = profileImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}