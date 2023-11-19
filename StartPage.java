package student_system;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
 

 
 public class StartPage  extends JFrame{
	 private JButton jbAdmin,jbStaff, jbStudent;
	 
	 public StartPage () {
		super ("");
		JPanel mainLayout = new JPanel();
		mainLayout.setLayout(new BoxLayout(mainLayout,BoxLayout.Y_AXIS));
		mainLayout.setBorder(new EmptyBorder(10,10,10,10));
		mainLayout.setBackground(new Color(84,78,80));
		
		JPanel main = new JPanel (new GridLayout(3,0,0,20));
		main.setBackground(new Color(84,78,80));

		JPanel top = new JPanel();
		top.setBackground(new Color(84,78,80));
		top.setBorder(new EmptyBorder(20,30,30,0));
 		top.setBackground(new Color(189,189,199));
 			 
  		JLabel select =  new JLabel("Select User Type" );
 		select.setFont(new Font("Verdana", Font.BOLD, 20));
 		top.add(select);
 		mainLayout.add(top);
 		mainLayout.add(Box.createVerticalStrut(10));
            
            
			 
		JPanel icons = new JPanel (new GridLayout(1,2,100, 0));
		icons.setBackground(new Color(84,78,80));
			 
		Icon icon1 = new ImageIcon(getClass().getResource("images/admin.png"));
		icons.add(new JLabel(icon1));
		 		
	 	Icon icon2 = new ImageIcon(getClass().getResource("images/teacher.png"));
		icons.add(new JLabel(icon2));
 		 		
	 	Icon icon3 = new ImageIcon(getClass().getResource("images/student.png"));
		icons.add(new JLabel(icon3));
		
		main.add(icons);
		 	
		 	
		JPanel jpbutton = new JPanel (  );
		jpbutton.setBackground(new Color(84,78,80));

 		jpbutton.setLayout(null);
		jbAdmin = new JButton("Admin");
		jbAdmin.setBounds(5, 20, 100, 40);
		jbAdmin.setBackground(new Color(189,189,199));
		jbAdmin.setBorderPainted(false);
		jbAdmin.setFocusPainted(false);
			
		jbStaff = new JButton("Staff");
		jbStaff.setBounds(240, 20, 100, 40);
		jbStaff.setBackground(new Color(189,189,199));
		jbStaff.setBorderPainted(false);
		jbStaff.setFocusPainted(false);
		
		jbStudent = new JButton("Student");
		jbStudent.setBounds(450, 20, 100, 40);
		jbStudent.setBackground(new Color(189,189,199));
		jbStudent.setBorderPainted(false);
		jbStudent.setFocusPainted(false);
	  	jpbutton.add(jbAdmin); 
	  	jpbutton.add(jbStaff); 
	  	jpbutton.add(jbStudent); 
	  	
	  	
	  	ButtonHandler handler = new ButtonHandler();
	  	jbAdmin.addActionListener(handler);
	  	jbStaff.addActionListener(handler);
	  	jbStudent.addActionListener(handler);

		main.add(jpbutton);
			
		mainLayout.add(main);
		add(mainLayout);
	 }
	 
	 
	 private class ButtonHandler implements ActionListener{
		 public void actionPerformed(ActionEvent e) {
			 if(e.getSource()==jbAdmin) {
				 setVisible(false);	 
				 Login login = new Login(StartPage.this,"ADMIN");
			 }
			 if(e.getSource()==jbStaff) {
				 setVisible(false);	 
				 Login login = new Login(StartPage.this,"STAFF");
			 }
			 if(e.getSource()==jbStudent) {
				 setVisible(false);	 
				 Login login = new Login(StartPage.this,"STUDENT");
			 }
			 
		 }
	 }
 }
