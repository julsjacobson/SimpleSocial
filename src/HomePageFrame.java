/***************************
 *author: JULIE JACOBSON
 *Controller
 *Creates frame for website
 **************************/


import javax.imageio.ImageIO;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.time.LocalDateTime;  
import javax.swing.*;
import javax.swing.border.EtchedBorder;

import java.awt.*;
import java.awt.color.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HomePageFrame extends JFrame  {
	private JButton title = new JButton("SIMPLE SOCIAL"); //Switch to home screen
	private JButton loginButton = new JButton("LOGIN"); //Switch to log in page
	private JButton signUpButton = new JButton("SIGN UP"); //Switch to sign up page
	private JButton profileButton = new JButton ("PROFILE"); //Switch to profile
	private JTextField searchField = new JTextField ("SEARCH", 30); //Search field
	private JButton logOutButton = new JButton("LOG OUT"); //Log out button


	JTextArea friendslist = new JTextArea(); //Friends
	protected JTextField statusField = new JTextField("How are you today?"); //Status field
	protected JTextArea statusArea = new JTextArea(5, 20); //Status text area
	
    JButton signup = new JButton("Sign Up");

	FileDialog fd = new FileDialog(this, "Open", FileDialog.LOAD); //FileDialog to open up icon image
	private JPanel iconPanel = new JPanel(); //Icon panel to store profile picture
	private JButton profilePicture = new JButton(""); //button for profile picture
	
	//Labels for profile, change when different users log in
	JLabel labelName = new JLabel(); 
	JLabel labelUser = new JLabel();
	JTextArea bioField = new JTextArea();
	
	Image image; //Uploaded image is stored 
	JButton defaultLabelIcon = new JButton(""); //JButton to put default image
	JButton editLabelIcon = new JButton(""); //JButton to put edit button
	
	private CardLayout c1 = new CardLayout(); //CardLayout object to switch between panels
	private JPanel cardPanel = new JPanel(c1); //Panel which switch
	
	Graph graph;
	int ID = 5; //Initial ID used for sign up after tester users
	User[] user = new User[100];  //Array of user objects

	private int loggedinID = 0; //Logged in ID show who is logged in at the time
	
	

	
	//Constructor 
	public HomePageFrame() {
		this.setTitle("Simple Social");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(1000, 800));
		
		
		
		//Test
		user[0] = new User(0 , "John Smith", "username", "password", "Write your bio here!", defaultLabelIcon);
		user[1] = new User(1 , "Julie Jacobson", "julsjacobson", "password", "Write your bio here!", defaultLabelIcon);
		user[2] = new User(2 , "Amber Jacobson", "amberjacobson", "password", "Write your bio here!", defaultLabelIcon);
		user[3] = new User(3 , "Myles Cashwell", "cashorcard", "password", "Write your bio here!", defaultLabelIcon);
		user[4] = new User(4 , "Frankie Jacobson", "frankie", "password", "Write your bio here!", defaultLabelIcon);
		user[5] = new User(5 , "Kuma Jacobson", "kumaroo", "password", "Write your bio here!", defaultLabelIcon);
		graph = new Graph(0); 
		graph = new Graph(1);
		graph = new Graph(2); 
		graph = new Graph(3);
		graph = new Graph(4); 
		graph = new Graph(5);
		
		
		graph.addFriend(graph, user[1], user[2]);
		graph.addFriend(graph, user[1], user[3]);
		graph.addFriend(graph, user[3], user[4]);
		graph.addFriend(graph, user[3], user[2]);
		
		//Set all users to logged out
		for (int i = 0; i < graph.getSize() ; i++) {
			user[i].isLoggedIn = false; 
		}
		
		this.add(getMainPanel());
		
	}
	
	/****************************
	 * Main page frame
	 * @return JPanel
	 ****************************/
	public JPanel getMainPanel () {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		
		/******************************TITLE****************************************
		 * titlePanel added to main panel
		 ***************************************************************************/
		JPanel titlePanel = new JPanel(); 
		
		titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		titlePanel.setBackground(Color.white);
		
		title.setFont(new Font("Helvetica Neue", Font.ITALIC | Font.BOLD, 35));
		title.setBorderPainted(false); 
		
		//Title action listener
		title.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c1.show(cardPanel, "Home Page"); //Send back to home page when clicked
			}
		});
		
		titlePanel.add(title);
		
		/*****************************TOOLBAR*****************************************
		 * loginButton: sends to login page
		 * signUpButton: sends to sign up page
		 * logOutButton: sends to home page, clears information
		 * profileButton: sends to profile page
		 * searchfield: searches other profiles -> sends to search results
		 *****************************************************************************/
		JPanel toolBar = new JPanel();
		toolBar.setLayout(new FlowLayout());
		toolBar.setBackground(Color.WHITE);
		
		loginButton.setFont(new Font("Helvetica Neue", 0, 15));
		loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		loginButton.setPreferredSize(new Dimension(200, 35));
		
		signUpButton.setFont(new Font("Helvetica Neue", 0, 15));
		signUpButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		signUpButton.setPreferredSize(new Dimension(200, 35));

		profileButton.setFont(new Font("Helvetica Neue", 0, 15));
		profileButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		profileButton.setPreferredSize(new Dimension(200, 35));
		profileButton.setVisible(false);
		
		logOutButton.setFont(new Font("Helvetica Neue", 0, 15));
		logOutButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		logOutButton.setPreferredSize(new Dimension(200, 35));
		logOutButton.setVisible(false);
		
		searchField.setFont(new Font("Helvetica Neue", 1, 15));
		searchField.setForeground(Color.decode("#F5F5F5"));
		searchField.setPreferredSize(new Dimension(150, 40));
		
		//CardPanel switches between pages
		cardPanel.add(getHomePage(), "Home Page");
		cardPanel.add(getLoginPage(), "Login");
		cardPanel.add(getSignUpPanel(), "Sign up");
		cardPanel.add(getProfilePage(), "Profile"); 

		
		//Login action listener
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c1.show(cardPanel, "Login");
				resetText();
			}
		});
		
		//Sign up action listener
		signUpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c1.show(cardPanel, "Sign up");
				resetText();
			}
		});
		
		//Profile action listener
		profileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c1.show(cardPanel, "Profile");
				resetText();
			}
		});
		
		//Logout action listener
		logOutButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
				c1.show(cardPanel, "Home Page");
				
				//Reset page
				loginButton.setVisible(true);
				signUpButton.setVisible(true);
				profileButton.setVisible(false);
				logOutButton.setVisible(false);
				friendslist.setText("");
				statusArea.setText("");
				
				user[loggedinID].isLoggedIn = false; //Logs user out
				
				resetText();
				}
		});
		
		
		//Search field listener
		searchField.addFocusListener(new FocusAdapter() {
		    public void focusGained(FocusEvent e) {
		        JTextField source = (JTextField)e.getComponent();
		        source.setText("");
		        source.setFont(new Font("Helvetica Neue", 0, 13));
		        source.setForeground(Color.black);
		        source.removeFocusListener(this);
		    }
		});
		
		//Searches for members of the graph and allows addition or removal of friends
		searchField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame searchResults = new JFrame(); 
				searchResults.setPreferredSize(new Dimension(400,500));
				searchResults.setBackground(Color.white); 
				searchResults.setLayout(new BorderLayout(15, 15));
				searchResults.setBackground(Color.decode("#F5F5F5"));
				
				JPanel searchPanel = new JPanel(); 
				searchPanel.setBackground(Color.WHITE);
				searchPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#D6D6D6"), 1));
				searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
				
				JLabel searchLabel = new JLabel("SEARCH RESULTS");
				searchLabel.setFont(new Font("Helvetica Neue", Font.ITALIC, 20));
				
				
				JTextArea searchResultField = new JTextArea();  
				searchResultField.setEditable(false);
				searchResultField.setFont(new Font("Helvetica Neue", 0, 13));

				for (int i = 0; i < graph.getSize() ; i++) {
					if (searchUsers(user[i].getName(), searchField.getText())) {			
						searchResultField.append(user[i].getName() + "\n\n");
					} 
				}
				
				JPanel addPanel = new JPanel();
				addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
				
				
				JTextField addQuestion = new JTextField("Would you like to add anyone?");
				addQuestion.setEditable(false);
				JButton addButton = new JButton ("ADD"); 
				addButton.setFont(new Font("Helvetica Neue", 0, 15));
				
				addQuestion.setFont(new Font("Helvetica Neue", 0, 15));
				JTextField addAnswer = new JTextField("");
				addAnswer.setFont(new Font("Helvetica Neue", 0, 15));
				addAnswer.setVisible(false);
				
				JTextField removeQuestion = new JTextField("Would you like to remove anyone?");
				removeQuestion.setEditable(false);
				JButton removeButton = new JButton ("REMOVE");
				removeButton.setFont(new Font("Helvetica Neue", 0, 15));
				
				removeQuestion.setFont(new Font("Helvetica Neue", 0, 15));
				JTextField removeAnswer = new JTextField("");
				removeAnswer.setFont(new Font("Helvetica Neue", 0, 15));
				removeAnswer.setVisible(false);
				
				addPanel.add(addQuestion);
				addPanel.add(addAnswer);
				addPanel.add(addButton);
				addPanel.add(removeQuestion);
				addPanel.add(removeButton);
				addPanel.add(removeAnswer);
				
				addButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						addButton.setVisible(false);
						addAnswer.setVisible(true);
					}
				});
				
				
				removeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						removeButton.setVisible(false);
						removeAnswer.setVisible(true);
					}
				});
				
				//Add friend
				addAnswer.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int answerID = 0;
					
						for (int i = 0; i < graph.getSize(); i++) {
							if (addAnswer.getText().equals(user[i].getName()))
									answerID = user[i].getID(); 
						}
						
							
						if (graph.isFriends(user[loggedinID], user[answerID])) 
								JOptionPane.showMessageDialog(null, "You are already friends!");
	
							
						if (!addAnswer.getText().equals(user[answerID].getName())) 
								JOptionPane.showMessageDialog(null, "Re-enter. Input is case sensitive!");
		
							
						if (!user[loggedinID].isLoggedIn) 
								JOptionPane.showMessageDialog(null, "Please log in");

						if (addAnswer.getText().equals(user[answerID].getName()) && !graph.isFriends(user[loggedinID], user[answerID]) && user[loggedinID].isLoggedIn) {
							graph.addFriend(graph, user[loggedinID], user[answerID]); 
							JOptionPane.showMessageDialog(null, "Success! You and " + user[answerID].getName() + " are friends!");
							addAnswer.setVisible(false);
							addButton.setVisible(true);
						}
						
					}
				});
				
				//Remove friend
				removeAnswer.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int answerID = 0;
					
						for (int i = 0; i < graph.getSize(); i++) {
							if (addAnswer.getText().equals(user[i].getName()))
									answerID = user[i].getID(); 
						}
						
							
						if (!graph.isFriends(user[loggedinID], user[answerID])) 
								JOptionPane.showMessageDialog(null, "You are already not friends!");
	
							
						if (!addAnswer.getText().equals(user[answerID].getName())) 
								JOptionPane.showMessageDialog(null, "Re-enter. Input is case sensitive!");
		
							
						if (!user[loggedinID].isLoggedIn) 
								JOptionPane.showMessageDialog(null, "Please log in");

						if (addAnswer.getText().equals(user[answerID].getName()) && graph.isFriends(user[loggedinID], user[answerID]) && user[loggedinID].isLoggedIn) {
							graph.removeFriend(graph, user[loggedinID], user[answerID]);
							JOptionPane.showMessageDialog(null, "Success! You and " + user[answerID].getName() + " are friends!");
							removeAnswer.setVisible(false);
							removeButton.setVisible(true);
						}
							
							
						
					}
				});
				
				searchPanel.add(searchResultField, BorderLayout.CENTER);
				
				
				searchResults.add(searchLabel, BorderLayout.NORTH);
				searchResults.add(searchPanel, BorderLayout.CENTER);
				searchResults.add(addPanel, BorderLayout.SOUTH);
				searchResults.pack();
				searchResults.setVisible(true);
				
				resetText(); 

			}
		});
			
		toolBar.add(loginButton);
		toolBar.add(signUpButton);
		toolBar.add(profileButton);	
		toolBar.add(logOutButton);
		toolBar.add(searchField);
		
		JPanel topPanel = new JPanel(); 
		topPanel.setLayout(new BorderLayout());
		
		topPanel.add(titlePanel, BorderLayout.NORTH);
		topPanel.add(toolBar, BorderLayout.SOUTH);
		
		panel.add(topPanel, BorderLayout.NORTH);
		panel.add(cardPanel, BorderLayout.CENTER);

		
		return panel; 
		
	}
	
	
	/********************************
	 * Profile page
	 * @return profile page panel
	 ********************************/
	public JPanel getProfilePage () {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.WHITE);
		
		
		/*******************PROFILE SIDE PANEL******************************************/
		JPanel outerPanel = new JPanel();
		JPanel sidePanel = new JPanel();

		
		//Setting gui interface aethetics 
		outerPanel.setBackground(Color.decode("#F5F5F5"));
		outerPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#F5F5F5"), 7));
		sidePanel.setBackground(Color.WHITE);
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));	
		sidePanel.setBorder(BorderFactory.createLineBorder(Color.decode("#D6D6D6"), 1));
		
		labelName.setFont(new Font("Helvetica Neue", Font.BOLD, 25));
		labelName.setAlignmentX(Component.LEFT_ALIGNMENT);
		labelUser.setFont(new Font("Helvetica Neue", Font.ITALIC, 15));
		labelUser.setForeground(Color.decode("#D6D6D6"));
		labelUser.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		iconPanel.setBackground(Color.WHITE);
		iconPanel.setLayout(new GridBagLayout());
		iconPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		
		
		//Upload default and edit icon
		defaultLabelIcon = loadImage("/icon/profileIcon.png");
		defaultLabelIcon.setFocusPainted(false);
		editLabelIcon = loadImage("/icon/profileIconEdit2 copy.png");
		editLabelIcon.setVisible(false);
		editLabelIcon.invalidate();	
		profilePicture.setFocusPainted(false);
		
		iconPanel.add(editLabelIcon);
		iconPanel.add(defaultLabelIcon);
		
		
		//User bio
		bioField.setLineWrap (true);
		bioField.setWrapStyleWord (false); 
		bioField.setText("Write your bio here!");
		bioField.setBorder(null);
		bioField.setBackground(Color.WHITE);
		bioField.setFont(new Font("Helvetica Neue", 0, 14));
		bioField.setEditable(false);
		bioField.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		
		
		/***EDIT COMBO BOX***/	
		String[] editProfile = {"EDIT PROFILE", "DELETE ACCOUNT"};
		final JComboBox profileCB = new JComboBox(editProfile); 
		
		JButton editIcon = new JButton("EDIT");
		editIcon.setVisible(false);
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton publish = new JButton("PUBLISH"); 
		JButton cancel = new JButton("CANCEL");
		publish.setVisible(false);
		cancel.setVisible(false);
		
		//Aesthetics
		profileCB.setFont(new Font("Helvetica Neue", 0, 14));
		buttonPanel.setBackground(Color.WHITE);
		publish.setFont(new Font("Helvetica Neue", 0, 14));
		cancel.setFont(new Font("Helvetica Neue", 0, 14));
		
		buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		buttonPanel.add(profileCB);
		buttonPanel.add(cancel);
		buttonPanel.add(publish);
		
	
		//Edit label action listener: activates button to upload a new profile picture
		editLabelIcon.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		imageUpload();
        	}
        });
		
		//Profile combo box action listner
		profileCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = (String) profileCB.getSelectedItem();
				switch(s) {
				case "EDIT PROFILE": //Allows user to edit bio and icon
					profileCB.setVisible(false);
					publish.setVisible(true);
					cancel.setVisible(true);
					
					bioField.setEditable(true);
					bioField.setForeground(Color.decode("#D6D6D6"));
					bioField.setBorder(BorderFactory.createLineBorder(Color.decode("#D6D6D6"), 1));
					defaultLabelIcon.setForeground(Color.decode("#D6D6D6"));
					
					statusField.setText("");
					statusField.setEditable(false);

					defaultLabelIcon.setVisible(false);
					
					
					editLabelIcon.setVisible(true);
					profilePicture.setVisible(false); 
					
					//Allows bio to change
					bioField.addFocusListener(new FocusAdapter() {
					    public void focusGained(FocusEvent e) {

					        bioField.setText("");
					        bioField.setFont(new Font("Helvetica Neue", 0, 13));
					        bioField.setForeground(Color.black);
					    }
					});
					
					break;
					
				case "DELETE ACCOUNT": //Deletes account
					JFrame deleteFrame = new JFrame();
					
					deleteFrame.setLayout(new GridBagLayout());
					JPanel deletePanel = new JPanel();
					deletePanel.setLayout(new BoxLayout(deletePanel, BoxLayout.Y_AXIS));
					
					deleteFrame.setPreferredSize(new Dimension(500, 400));
					JLabel deleteAcc = new JLabel("Your account has been deleted");
					deleteAcc.setFont(new Font("Helvetica Neue", 0, 15));
					
					JButton exit = new JButton("EXIT");
					exit.setFont(new Font("Helvetica Neue", 0, 15));
					exit.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							deleteFrame.dispose();
						}
					});
					

					deletePanel.add(deleteAcc);
					deletePanel.add(exit);
					
					deleteFrame.add(deletePanel);
					deleteFrame.pack();
					
					deleteFrame.setVisible(true);
					
					//Sends back to home page
					c1.show(cardPanel, "Home Page");
					loginButton.setVisible(true);
					signUpButton.setVisible(true);
					profileButton.setVisible(false);
					logOutButton.setVisible(false);
					resetText();
					break;
				}
			}
		});
		
		//Cancel edits  made to profile
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				profileCB.setVisible(true);
				cancel.setVisible(false);
				publish.setVisible(false);
				
				if (user[ID].getBio().length() != 0) {
					bioField.setText(user[ID].getBio());
				}
				else bioField.setText("Write your bio here!");
				
				
				
				bioField.setEditable(false);
				bioField.setForeground(Color.BLACK);
				bioField.setBorder(null);
				
				if (profilePicture.getIcon() != null) {
					profilePicture.setVisible(true);
					editLabelIcon.setVisible(false);
				}
				
				else {
					defaultLabelIcon.setVisible(true);
					editLabelIcon.setVisible(false);
				}
				
				
				statusField.setText("How are you today?");
				statusField.setEditable(true);
			}
		});
		
		//Publish edits made to profile and save it to user object
		publish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				profileCB.setVisible(true);
				cancel.setVisible(false);
				publish.setVisible(false);
				
				user[loggedinID].setBio(bioField.getText()); 
				bioField.setEditable(false);
				bioField.setForeground(Color.BLACK);
				bioField.setBorder(null);
				
				
				if (profilePicture.getIcon() != null) {
					profilePicture.setVisible(true);
					user[loggedinID].setIcon(profilePicture);
					editLabelIcon.setVisible(false);
				}
				
				else {
					defaultLabelIcon.setVisible(true);
					editLabelIcon.setVisible(false);
				}
				
				statusField.setText("How are you today?");
				statusField.setEditable(true);
			}
		});
		
		//Sign up action adds the icon to the profile
		signup.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				sidePanel.add(iconPanel );
			}
		});
		

		sidePanel.add(labelName);
		sidePanel.add(Box.createRigidArea(new Dimension(15, 5)));
		sidePanel.add(labelUser);
		sidePanel.add(Box.createRigidArea(new Dimension(15, 5)));
		sidePanel.add(iconPanel);
		sidePanel.add(Box.createRigidArea(new Dimension(15, 5)));
		sidePanel.add(buttonPanel);
		sidePanel.add(Box.createRigidArea(new Dimension(15, 5)));
		sidePanel.add(bioField);
		sidePanel.add(Box.createRigidArea(new Dimension(15, 10)));
		
		outerPanel.add(sidePanel);


		/*******************STATUS PANEL***************************************/
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm");  //Shows the date and time when the status is posted
		LocalDateTime now = LocalDateTime.now();  
		String dateTime = new String(dtf.format(now));  
		
		
		JPanel statusPanel = new JPanel(new GridBagLayout()); 

		statusField.setFont(new Font("Helvetica Neue", Font.ITALIC, 15));
		statusField.setForeground(Color.decode("#D6D6D6"));
		statusField.setBorder(BorderFactory.createLineBorder(Color.decode("#f5f5f5"), 1));
		
		statusArea.setEditable(false);
		statusArea.setBackground(Color.WHITE);
		statusArea.setFont(new Font("Helvetica Neue", 0, 12));
		
		
		JScrollPane scrollPane = new JScrollPane(statusArea);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#D6D6D6"), 1));
		
		
		GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
 
        c.fill = GridBagConstraints.HORIZONTAL;
        statusPanel.add(statusField, c);
 
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        statusPanel.add(scrollPane, c);
       
		statusPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#F5F5F5"), 12));
				
		statusField.addFocusListener(new FocusAdapter() {
		    public void focusGained(FocusEvent e) {
		        JTextField source = (JTextField)e.getComponent();
		        source.setText("");
		        source.setFont(new Font("Helvetica Neue", 0, 13));
		        source.setForeground(Color.BLACK);
		        source.removeFocusListener(this);
		    }
		});
		
		statusField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statusArea.setText("   " + statusField.getText() + "\n   " + dateTime + "\n\n" + statusArea.getText());
				
				statusField.selectAll(); 
				statusArea.setCaretPosition(statusArea.getDocument().getLength());

				statusField.setText("");
			}
		});
		
		
		/*******************LEFT SIDE PANEL***************************/
		JPanel outerFriendPanel = new JPanel();
		outerFriendPanel.setBackground(Color.decode("#F5F5F5"));
		outerFriendPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#F5F5F5"), 7));
		
		JPanel friendPanel = new JPanel();
		friendPanel.setLayout(new BoxLayout(friendPanel, BoxLayout.Y_AXIS));
		friendPanel.setBackground(Color.WHITE);
		friendPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#D6D6D6"), 1));
		
		JButton friendIcon = new JButton("Friend");
		friendIcon.setBorderPainted(false);
		
		friendslist.setFont(new Font("Helvetica Neue", 0, 15));
		friendslist.setBackground(Color.WHITE);
		friendslist.setEditable(false);
		

		JLabel friendLabel = new JLabel("Friends                       ");
		friendLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 25));



		
		friendPanel.add(friendLabel);
		friendPanel.add(friendslist); 
		outerFriendPanel.add(friendPanel);
		
		
		panel.add(outerPanel, BorderLayout.LINE_START);
		panel.add(statusPanel, BorderLayout.CENTER);
		panel.add(outerFriendPanel, BorderLayout.LINE_END);
		
		return panel; 
	}

	public JPanel getHomePage () {
		JPanel homePanel = new JPanel(); 
		homePanel.setLayout(new GridBagLayout());
		homePanel.setBackground(Color.decode("#F5F5F5"));
		
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new BorderLayout());
		textPanel.setBackground(Color.decode("#F5F5F5"));
		
		JLabel welcome = new JLabel ("Welcome!");
		welcome.setFont(new Font("Helvetica Neue", Font.ITALIC | Font.BOLD, 45));
		welcome.setForeground(Color.WHITE);
		welcome.setHorizontalAlignment(SwingConstants.CENTER);
		welcome.setBorder(null);
		
		JLabel homePageText = new JLabel ("Log in or sign up today!");
		homePageText.setFont(new Font ("Helvetica Neue", Font.ITALIC | Font.BOLD, 30));
		homePageText.setHorizontalAlignment(SwingConstants.CENTER);
		homePageText.setBorder(null);
		
		textPanel.add(welcome, BorderLayout.NORTH);
		textPanel.add(homePageText, BorderLayout.SOUTH);

		
		homePanel.add(textPanel);
		
		
		return homePanel;
		
		
	}
	
	public JPanel getLoginPage() {
		JFrame frame = new JFrame();
		
		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new GridBagLayout());
		loginPanel.setBackground(Color.decode("#F5F5F5"));
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.decode("#d6d6d6"), 1));
		
	    panel.setLayout(new SpringLayout());
	    panel.setBackground(Color.WHITE);
		
	    
		JLabel userLabel = new JLabel("User Name:");
		userLabel.setFont(new Font("Helvetica Neue", Font.ITALIC | Font.BOLD, 15));
        JTextField userField = new JTextField(20);
		userField.setFont(new Font("Helvetica Neue", 0, 14));
		userField.setForeground(Color.black);
        userLabel.setLabelFor(userField);
        
        JLabel password = new JLabel("Password:");
		password.setFont(new Font("Helvetica Neue", Font.ITALIC | Font.BOLD, 15));
        final JPasswordField pwField = new JPasswordField(20);
        pwField.setForeground(Color.black);
        password.setLabelFor(pwField);

        JButton login = new JButton("Login");
        login.setFont(new Font("Helvetica Neue", 0, 15));
 
       
        JButton displayPass = new JButton("Display Password");
        displayPass.setFont(new Font("Helvetica Neue", 0, 15));

        
        panel.add(userLabel);
        panel.add(userField);
        panel.add(password);
        panel.add(pwField);
        panel.add(displayPass);
        panel.add(login);
        
       
        
        displayPass.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String password = new String(pwField.getPassword());
                        JOptionPane.showMessageDialog(frame, "Password: " + password); 
                    }
                });
 


       

        
        loginPanel.add(panel);
 
        SpringUtilities.makeCompactGrid(panel,
                3, 2, //rows, cols
                6, 6, //initX, initY
                6, 6); //xPad, yPad
      
		
        login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String password = new String(pwField.getPassword());
				
				if (userField.getText().trim().length() != 0 && password.length() != 0) {
			
					for (int i = 0; i < graph.getSize() ; i++) {
						
			        	if (user[i].getUsername().equals(userField.getText())) {
			        		loggedinID = user[i].getID();
			        		break; 
			        	}
			        }
					
					if (user[loggedinID].getPass().equals(password)){
						user[loggedinID].isLoggedIn = true; 
						c1.show(cardPanel, "Profile");
						signUpButton.setVisible(false);
						loginButton.setVisible(false);
						profileButton.setVisible(true);
						logOutButton.setVisible(true);
						
						
						
						labelName.setText(user[loggedinID].getName());
						labelUser.setText(user[loggedinID].getUsername());
						bioField.setText(user[loggedinID].getBio());
						
						if (profilePicture != null) {
							user[loggedinID].setIcon(profilePicture);
						}
						
						for (int i = 0; i < graph.getSize(); i++) {
							if (graph.isFriends(user[loggedinID], user[i])) {
								friendslist.append(user[i].getName() + "\n"); 
							}
						}
		
						
					}
					
					else JOptionPane.showMessageDialog(null, "Password is incorrect");
					
					
					resetText();
		        }
				else JOptionPane.showMessageDialog(null, "Please fill both username and password!");
				
				
				 
		        
		 
					
			}
		});
        
		return loginPanel; 
	}
	
	public JPanel getSignUpPanel() {
		JPanel signUpPanel = new JPanel();
		signUpPanel.setLayout(new GridBagLayout());
		signUpPanel.setBackground(Color.decode("#F5F5F5"));
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.decode("#D6D6D6"), 1));
		
	    panel.setLayout(new SpringLayout());
	    panel.setBackground(Color.WHITE);
	    JButton chooseFile = new JButton("Choose file");
		JLabel uploadIcon = new JLabel("Upload Profile Picture: ");
	    
	    uploadIcon.setFont(new Font("Helvetica Neue", Font.ITALIC | Font.BOLD, 15));
	    chooseFile.setFont(new Font("Helvetica Neue", 0, 15));

	    
	    JLabel fname = new JLabel("First name: ");
	    fname.setFont(new Font("Helvetica Neue", Font.ITALIC | Font.BOLD, 15));
	    JTextField fnameField = new JTextField(20);
	    fnameField.setFont(new Font("Helvetica Neue", 0, 14));
	    fname.setLabelFor(fnameField);
	    
	    JLabel lname = new JLabel("Last name: ");
	    lname.setFont(new Font("Helvetica Neue", Font.ITALIC | Font.BOLD, 15));
	    JTextField lnameField = new JTextField(20);
	    lnameField.setFont(new Font("Helvetica Neue", 0, 14));
	    lname.setLabelFor(fnameField);
	    
		JLabel username = new JLabel("User Name:");
		username.setFont(new Font("Helvetica Neue", Font.ITALIC | Font.BOLD, 15));
        JTextField userField = new JTextField(20);
		userField.setFont(new Font("Helvetica Neue", 0, 14));
        username.setLabelFor(userField);
        
        JLabel password = new JLabel("Password:");
		password.setFont(new Font("Helvetica Neue", Font.ITALIC | Font.BOLD, 15));
        final JPasswordField pwField = new JPasswordField(20);
        password.setLabelFor(pwField);
 
        signup.setFont(new Font("Helvetica Neue", 0, 15));
        //signup.setForeground(Color.decode(DARK_TEAL));
        
        JButton blank = new JButton("");
        blank.setBorderPainted(false);
        
        
        chooseFile.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		imageUpload();
        		chooseFile.setText("Change Image");
        	}
        });
        
  
        
        panel.add(fname);
        panel.add(fnameField);
        panel.add(lname);
        panel.add(lnameField);
        panel.add(username);
        panel.add(userField);
        panel.add(password);
        panel.add(pwField);
        panel.add(uploadIcon);
        panel.add(chooseFile);
        panel.add(blank);
        panel.add(signup);

        
        signUpPanel.add(panel);
        
		signup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String password = new String(pwField.getPassword());
				
				if (fnameField.getText().trim().length() != 0 &&
						lnameField.getText().trim().length() != 0 &&
						userField.getText().trim().length() != 0 &&
						password.length() != 0) {
						ID++;

						graph = new Graph(ID);
						
						loggedinID = ID; 
						String name = fnameField.getText() + " " + lnameField.getText();
						user[loggedinID] = new User(ID, name, userField.getText(), password, "", null); 
						
						JOptionPane.showMessageDialog(null, "ID: " + user[loggedinID].getID() 
													+ "\nName: " + user[loggedinID].getName() 
													+ "\nUsername: " + user[loggedinID].getUsername() 
													+ "\nPassword: " + user[loggedinID].getPass());
				
						
					
						
						c1.show(cardPanel, "Profile");
						signUpButton.setVisible(false);
						loginButton.setVisible(false);
						profileButton.setVisible(true);
						logOutButton.setVisible(true);
						
						if (fd.getFile() != null) {
							iconPanel.setVisible(true);
						}
							
						fnameField.setText("");
						lnameField.setText("");
						userField.setText("");
						pwField.setText("");
						chooseFile.setText("Choose file");
						
						labelName.setText(user[loggedinID].getName());
						labelUser.setText(user[loggedinID].getUsername());
						bioField.setText(user[loggedinID].getBio());
				
						if (profilePicture != null) {
							user[ID].setIcon(profilePicture);
						}
						
						

				}
				
				else JOptionPane.showMessageDialog(null, "You have not filled out everything!");
				
			}
		});
 
        SpringUtilities.makeCompactGrid(panel, 6, 2, 6, 6, 10, 10);       
		
		
		return signUpPanel; 
	}

	
	
	public void resetText() {
		searchField.setText("SEARCH");
		searchField.setFont(new Font("Helvetica Neue", 0, 15));
		searchField.setForeground(Color.decode("#F5F5F5"));
		
		searchField.addFocusListener(new FocusAdapter() {
		    public void focusGained(FocusEvent e) {
		        JTextField source = (JTextField)e.getComponent();
		        source.setText("");
		        source.setFont(new Font("Helvetica Neue", 0, 13));
		        source.setForeground(Color.black);
		        source.removeFocusListener(this);
		    }
		});
		
		statusField.setText("How are you today?");
		statusField.setFont(new Font("Helvetica Neue", Font.ITALIC, 15));
		statusField.setForeground(Color.decode("#D6D6D6"));
		statusField.setBorder(BorderFactory.createLineBorder(Color.decode("#D6D6D6"), 1));
	
		statusField.addFocusListener(new FocusAdapter() {
		    public void focusGained(FocusEvent e) {
		        JTextField source = (JTextField)e.getComponent();
		        source.setText("");
		        source.setFont(new Font("Helvetica Neue", 0, 13));
		        source.setForeground(Color.black);
		        source.removeFocusListener(this);
		    }
		});
		
		
		
	}
	

	
	void imageUpload() {
		fd.show(); 
		JFrame imageFrame = new JFrame();
		imageFrame.setTitle("Error");
		JPanel imagePanel = new JPanel();
		imageFrame.setPreferredSize(new Dimension(400, 400));
		
		JLabel upload = new JLabel("You have not selected an image");
		upload.setFont(new Font("Helvetica Neue", 0, 15));
		
		if (fd.getFile() == null){
			imagePanel.setLayout(new GridBagLayout());
			imagePanel.add(upload);
			imageFrame.add(imagePanel);
			
			imageFrame.pack();
			imageFrame.setVisible(true);
		}
		
		else {
			String d = (fd.getDirectory() + fd.getFile());
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			image = toolkit.getImage(d);
			
			String filename = new File(new File(fd.getDirectory()), fd.getFile()).getPath();
			
			try {
				BufferedImage bf = ImageIO.read(new File(filename));
				profilePicture.setIcon(new ImageIcon (bf.getScaledInstance(175, 175, Image.SCALE_FAST)));
			}
			catch (IOException ioe) {
				System.out.println(ioe.toString());
			}
			
			
	        
	        profilePicture.setVisible(true);
	        iconPanel.add(profilePicture); 
	        iconPanel.remove(defaultLabelIcon);
	        editLabelIcon.setVisible(false);
		}
	}
	
	private JButton loadImage(String str) {
		JButton buttonIcon = new JButton("");
		try {
			BufferedImage bfimg = ImageIO.read(HomePageFrame.class.getResource(str));			
			Graphics g = bfimg.createGraphics();
			g.drawImage(bfimg, 0, 0, 175, 175, null);
			g.dispose(); 	
			buttonIcon.setIcon(new ImageIcon(bfimg));	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return buttonIcon;
	}
	
	private boolean searchUsers(String users, String searchedUser) {
		if (users.contains(searchedUser)) return true;
		else return false;
	}
	





}


