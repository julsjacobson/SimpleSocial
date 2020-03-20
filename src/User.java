import javax.swing.*;
import java.util.*;


public class User extends JFrame {
	private int ID; 
	private String name; //Name
	private String username; //Username
	private String password; //Password
	private String bio; //User biography
	private JButton icon; //User profile picture

	
	public boolean isLoggedIn = false; 
	
	
	public User (int ID, String n, String u, String p, String b, JButton i) {
		this.ID = ID;
		this.name = n;
		this.username = u;
		this.password = p;
		this.bio = b;
		this.icon = i; 
	}
	
	public int compareTo (User otherUser) {
		return this.name.compareTo(otherUser.name); 
	}
	
	//Getter methods
	public int getID() {
		return ID; 
	}
	
	public String getName() {
		return name;
	}

	
	public String getUsername() {
		return username;
	}
	
	public String getPass() {
		return password;
	}
	
	public String getBio() {
		return bio;
	}
	
	public JButton getIcon () {
		return icon; 
	}
	

	//Mutator methods
	public void setBio(String b) {
		this.bio = b;
	}
	
	public void setIcon(JButton i) {
		this.icon = i; 
	}

	

}
