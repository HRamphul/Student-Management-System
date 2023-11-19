package student_system;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;


import javax.swing.*;
import javax.swing.border.*;

 
 public class Login  extends JFrame{
	
	private JLabel title, jlusername, jlpassword ;
	private JTextField jtusername;
	private JPasswordField password;
 	private JButton login, signup, back;
 	private JPanel   jppassword, jpusername,  main,jpbutton, toptitle ;
 	private StartPage firstPage;
 	private String userT;
	
	Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	
	public Login (StartPage start,String usertype) {
		super ("Authentication");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,520);
		int x1 = (int) ((dimension.getWidth() - getWidth()) / 2);   
		int y1 = (int) ((dimension.getHeight() - getHeight()) / 2);
		setLocation(x1, y1);
		getContentPane().setBackground(new Color(84,78,80));
		setVisible(true);
		
		this.firstPage=start;
		this.userT=usertype;
		
		JPanel mainLayout = new JPanel();
		mainLayout.setLayout(new BoxLayout(mainLayout,BoxLayout.Y_AXIS));
		mainLayout.setBackground(new Color(84,78,80));
		mainLayout.setBorder(new EmptyBorder(10,0,0,0));
		//main panel 
		GridLayout grid = new GridLayout(4,0,0,0);
 		main = new JPanel(grid);
		main.setBackground(new Color(84,78,80));
        main.setSize(500,00);
  		main.setBorder(new EmptyBorder(10,10,10,10));
  		
  		//top icon panel
  		JPanel panelIcon = new JPanel();
  		panelIcon.setBackground(new Color(84,78,80));
  		
		//title panel
		toptitle = new JPanel ();
 		toptitle.setBackground(new Color(84,78,80));
		title = new JLabel (userT + " LOGIN");
 		title.setHorizontalTextPosition(SwingConstants.CENTER);
 		title.setVerticalTextPosition(SwingConstants.BOTTOM);
 		title.setFont(new Font("Sans-Serif", Font.BOLD, 20));  
 		title.setForeground(Color.white);
 		
 		//Icon
 		Icon icon1 = new ImageIcon(getClass().getResource("images/MainIcon.png"));
 		
// 		main.add(new JLabel(icon1));
 		toptitle.add(title);
 		main.add(toptitle);
 		
 		//creating curved line and titled border
 		LineBorder roundedLineBorder = new LineBorder(Color.white, 2, true);
 		
 		Border usernameBorder = BorderFactory.createTitledBorder(roundedLineBorder, "Username", TitledBorder.DEFAULT_JUSTIFICATION,TitledBorder.DEFAULT_POSITION, null, Color.white);
 		Border passwordBorder = BorderFactory.createTitledBorder(roundedLineBorder, "Password", TitledBorder.DEFAULT_JUSTIFICATION,TitledBorder.DEFAULT_POSITION, null, Color.white);

 //username panel
		jpusername = new JPanel ();
		jpusername.setLayout(new FlowLayout());
		jpusername.setBackground(new Color(84,78,80));
		jtusername = new JTextField("",20);
		jtusername.setBackground(new Color(107,198,165));
		jtusername.setForeground(new Color(84,78,80));
		jtusername.setBorder(usernameBorder);
		
		jtusername.setFont(new Font("Sans-Serif",Font.PLAIN,17));

		  // icon username
 		Icon imguser = new ImageIcon(getClass().getResource("images/user-4250.png"));
		JLabel icon = new JLabel (imguser);
		jlusername = new JLabel("Username");
		jpusername.add(icon);
		jpusername.add(Box.createHorizontalStrut(10));
		jpusername.add(jtusername);
		main.add(jpusername);
	//add(main);
	
	
	
	 //password panel
	jppassword = new JPanel(); 
	jppassword.setLayout(new FlowLayout());
	jppassword.setBackground(new Color(84,78,80));
	password = new JPasswordField("",20);
	password.setBackground(new Color(107,198,165));
	password.setForeground(new Color(84,78,80));
	password.setBorder(passwordBorder);
	password.setFont(new Font("Sans-Serif",Font.PLAIN,17));

	//icon password
	Icon imgpw = new ImageIcon(getClass().getResource("images/car-key-4365.png"));
	JLabel pw = new JLabel (imgpw);
	 
	jppassword.add(pw);
	jppassword.add(Box.createHorizontalStrut(10));
	jppassword.add(password);
	main.add(jppassword);
	
	
	//login button
	jpbutton = new JPanel ();
	jpbutton.setBackground(new Color(84,78,80));
	jpbutton.setLayout(new BoxLayout(jpbutton,BoxLayout.Y_AXIS));
	JPanel centre = new JPanel(new FlowLayout());
	centre.setBackground(new Color(84,78,80));
	login = new JButton("LOGIN");
  	login.setBackground(new Color(189,189,199));
  	login.setForeground(new Color(84,78,80));
  	login.setFocusPainted(false);
  	login.setBorderPainted(false);
  	centre.add(login);
  	
  	//signup button



	signup = new JButton("SIGN UP");
  	signup.setBackground(new Color(189,189,199));
  	signup.setForeground(new Color(84,78,80));
  	signup.setFocusPainted(false);
  	signup.setBorderPainted(false);
  	centre.add(signup);
  	
  	
  	//back button
  	JPanel panelBack = new JPanel();
	panelBack.setLayout(new FlowLayout());
	panelBack.setBackground(new Color(84,78,80));
	back = new JButton("BACK");
  	back.setBackground(new Color(189,189,199));
  	back.setForeground(new Color(84,78,80));
  	back.setFocusPainted(false);
  	back.setBorderPainted(false);
	panelBack.add(back);
	
	PreviousHandler previousHandler = new PreviousHandler();
	LoginHandler loginHandler = new LoginHandler();
	SignupHandler signupHandler = new SignupHandler();
	
	back.addActionListener(previousHandler);
	signup.addActionListener(signupHandler);
	login.addActionListener(loginHandler);

 
  	jpbutton.add(centre);
  	jpbutton.add(centre);
  	jpbutton.add(panelBack);
	main.add(jpbutton);
	
	panelIcon.add(new JLabel(icon1));		
	mainLayout.add(panelIcon);
	mainLayout.add(main);	
			
	add(mainLayout);
			
			
	
		 
	}//end login constructor
 
 
	
	//back handler

		private class PreviousHandler implements ActionListener{
			public void actionPerformed(ActionEvent ae) {
				firstPage.setVisible(true);
				dispose();
			}
		}
		
	//login handler
		
		private class LoginHandler implements ActionListener{
			public void actionPerformed(ActionEvent ae) {
				Connection conn = null;
				String dbURL = "jdbc:mysql://localhost:3306/assignment_attendance";
				String username = "root";
				String password1 = "";
				String check="";
				boolean found=false;
				try {
					conn = DriverManager.getConnection(dbURL,username,password1);
					
					if(userT.equals("STUDENT")) {
						check = "SELECT * from account where usertype=\"student\"";
						
					}else if(userT.equals("ADMIN")) {
						check = "SELECT * from account where usertype=\"admin\"";
					}
					else if(userT.equals("STAFF")) {
						check = "SELECT * from account where usertype=\"staff\"";

						
					}
					java.sql.Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(check);
					while(rs.next()) {
						
							//admin login
					if(rs.getString(3).equals(String.valueOf(password.getPassword())) && rs.getString(2).equals(jtusername.getText()) && userT.equals("ADMIN")) {
							JOptionPane.showMessageDialog(getContentPane(),"Admin Login Successful");
							dispose();
							Admin admin = new Admin();
							found=true;
							
							
							//staff login
						}else if(rs.getString(3).equals(String.valueOf(password.getPassword())) && rs.getString(2).equals(jtusername.getText()) && userT.equals("STAFF")) {
							JOptionPane.showMessageDialog(getContentPane(),"Staff Login Successful");
							dispose();
							Staff staff = new Staff(firstPage,jtusername.getText());
							found=true;
						}
					
						else if(rs.getString(3).equals(String.valueOf(password.getPassword())) && rs.getString(2).equals(jtusername.getText()) && userT.equals("STUDENT")) {
							JOptionPane.showMessageDialog(getContentPane(),"Student Login Successful");
							dispose();
						Student student = new Student(jtusername.getText());
							found=true;
						}
					}
					if(found==false) {
						JOptionPane.showMessageDialog(getContentPane(),"Incorrect Credentials","Error", JOptionPane.ERROR_MESSAGE);
					}
				}
					
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
// end class login
 
 		//signup listener
 	private class SignupHandler implements ActionListener {
	    @Override
	    public void actionPerformed(ActionEvent ae) {
	        Connection conn = null;
	        String dbURL = "jdbc:mysql://localhost:3306/assignment_attendance";
	        String u = "root";
	        String password1 = "";

	        // Your implementation of actionPerformed with validation and database saving
	        // ...

	        // Database connection
	        try {
	            conn = DriverManager.getConnection(dbURL, u, password1);
	            System.out.println("Connected to the database!");

	            String user = jtusername.getText();
	            char[] passwordChars = password.getPassword();
	            String password = new String(passwordChars); // Convert char[] to String
	            String userType = userT; // Assuming the userType is hardcoded as "ADMIN"

	            // Validate if the username and password fields are not blank
	            if (user.trim().isEmpty() || password.trim().isEmpty()) {
	                JOptionPane.showMessageDialog(null, "Username and password fields cannot be blank.", "Error", JOptionPane.ERROR_MESSAGE);
	                return; // Return early if fields are blank
	            }

	            // You should handle hashing and salting of the password before saving it to the database
	            // For the sake of simplicity in this example, we will directly use the password as is.

	            try {
	                // Prepare the SQL statement
	                String sql = "INSERT INTO account (username, userpassword, usertype) VALUES (?, ?, ?)";
	                PreparedStatement statement = conn.prepareStatement(sql);
	                statement.setString(1, user);
	                statement.setString(2, password);
	                statement.setString(3, userType);

	                // Execute the query
	                int rowsInserted = statement.executeUpdate();

	                if (rowsInserted > 0) {
	                	JOptionPane.showMessageDialog(null,"User account created successfully!");
	                } else {
	                	JOptionPane.showMessageDialog(null,"Failed to create user account.");
	                }

	                // Close the statement (optional but recommended)
	                statement.close();
	            } catch (SQLException ex) {
	            	JOptionPane.showMessageDialog(null,"Error while saving the user account: " + ex.getMessage());
	                // Handle the exception appropriately
	            }

	        } catch (SQLException e) {
	            System.err.println("Error connecting to the database: " + e.getMessage());
	            e.printStackTrace();
	        } finally {
	            // Close the database connection when you are done with it
	            if (conn != null) {
	                try {
	                    conn.close();
	                } catch (SQLException e) {
	                    System.err.println("Error closing the database connection: " + e.getMessage());
	                }
	            }
	        }
	    }

 	}
 }