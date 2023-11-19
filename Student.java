package student_system;

import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.*;
import javax.swing.*;

public class Student extends JFrame {
    private String studID;

    public Student(String studentID) {
        super("Student Portal");
        this.studID = studentID; 

        // Create buttons for enrollment, module, and payment
        Color primaryColor = new Color(84, 78, 80); // Dark gray
        Color secondaryColor = new Color(189, 189, 199); // Light gray

        // Create buttons for enrollment, module, and payment
        JButton enrollmentButton = new JButton("Enrollment");
        JButton moduleButton = new JButton("Module Selection");
        JButton paymentButton = new JButton("Payment");

        // Set button colors and styles
        enrollmentButton.setBackground(secondaryColor);
        enrollmentButton.setBorderPainted(false);
        enrollmentButton.setFocusPainted(false);
        moduleButton.setBackground(secondaryColor);
        moduleButton.setBorderPainted(false);
        moduleButton.setFocusPainted(false);
        paymentButton.setBackground(secondaryColor);
        paymentButton.setBorderPainted(false);
        paymentButton.setFocusPainted(false);

        // Add action listeners to the buttons
        enrollmentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openEnrollmentFrame();
            }
        });

        moduleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openModuleFrame();
            }
        });

        paymentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openPaymentFrame();
            }
        });

        // Create a panel to hold the buttons with improved aesthetics
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        buttonPanel.setBackground(primaryColor);
        buttonPanel.add(enrollmentButton);
        buttonPanel.add(moduleButton);
        buttonPanel.add(paymentButton);

        // Add the panel to the frame
        this.add(buttonPanel);
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
        // Set frame properties
    }
        
        // Method to open Enrollment JFrame
        private void openEnrollmentFrame() {
            enrollement enrollmentFrame = new enrollement(studID);
            enrollmentFrame.setVisible(true);
        }

        // Method to open Module JFrame
        private void openModuleFrame() {
            // Fetch the courseName from the database based on the studentID
            String courseName = getCourseNameFromDatabase(studID);

            if (courseName != null) {
                module moduleFrame = new module(studID, courseName);
                moduleFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Please Enroll to a Course first!");
            }
        }

        // Method to fetch the courseName from the 'course' table based on the studentID
        private String getCourseNameFromDatabase(String studID) {
            String courseName = null;

            try {
                String url = "jdbc:mysql://localhost:3306/assignment_attendance";
                String username = "root";
                String password = "";
                String studentID = studID;

                Connection conn = DriverManager.getConnection(url, username, password);
                String query = "SELECT course_name FROM student WHERE studentID = ?";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, studentID);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    courseName = resultSet.getString("course_name");
                }

                resultSet.close();
                statement.close();
                conn.close();
            } catch (SQLException ex) {
                System.out.println("Error fetching course name from the database: " + ex);
            }

            return courseName;
        }

        // Method to open Payment JFrame
        private void openPaymentFrame() {
            payment paymentFrame = new payment(studID);
            paymentFrame.setVisible(true);
        }
        
        
        
    
}


    

