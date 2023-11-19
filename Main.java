package student_system;

import java.awt.*;

import javax.swing.JFrame;

 
public class Main {
 public static void main (String[] args) {
	 Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    
	 StartPage start = new StartPage();
	 start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 start.setSize(600,400);
	 int x = (int) ((dimension.getWidth() - start.getWidth()) / 2);   
	 int y = (int) ((dimension.getHeight() - start.getHeight()) / 2);
	 start.setLocation(x, y);
	 start.getContentPane().setBackground(new Color(84,78,80));
	 start.setVisible(true);

 }
}