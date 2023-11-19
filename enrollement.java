package student_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class enrollement extends JFrame {

    private JPanel mainPanel, formPanel, buttonPanel;
    private JComboBox<String> courseDropdown;
    private JTextField facultyField;
    private JLabel titleLabel, courseLabel, facultyLabel;
    private String studentID;

    public enrollement(String ID) {
        super("Course Enrollment");
        this.studentID = ID;

        // Set the theme colors
        Color primaryColor = new Color(84, 78, 80); // Dark gray
        Color secondaryColor = new Color(189, 189, 199); // Light gray

        mainPanel = new JPanel(new BorderLayout(10, 10));
        formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel = new JPanel();

        titleLabel = new JLabel("Course Enrollment", SwingConstants.CENTER); 
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        courseLabel = new JLabel("Select Course:");
        courseDropdown = new JComboBox<>();
        populateDropdown();

        facultyLabel = new JLabel("Faculty Name:");
        facultyField = new JTextField(10);

        formPanel.add(courseLabel);
        formPanel.add(courseDropdown);
        formPanel.add(facultyLabel);
        formPanel.add(facultyField);

        JButton button = new JButton("Register");
        // Action listener for button
        button.addActionListener(e -> {
            String selectedCourse = (String) courseDropdown.getSelectedItem();
            String faculty = facultyField.getText();
            if (faculty.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Faculty Name.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                registerCourse(selectedCourse, faculty, this.studentID);
            }
        });

        // Add item listener for the dropdown
        courseDropdown.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedCourse = (String) courseDropdown.getSelectedItem();
                    // You can add additional functionality when a course is selected.
                }
            }
        });

        buttonPanel.add(button);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Changed to dispose the frame instead of exit on close
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
        setBackground(primaryColor);

        // Set custom icon for the frame
      
        // Add tooltips for components
        courseDropdown.setToolTipText("Select the course you want to enroll in.");
        facultyField.setToolTipText("Enter the name of the faculty.");

        // Clear text fields after successful course registration
        clearTextFieldsOnSuccess();
    }

    private void populateDropdown() {
        List<String> courseNames = fetchCourseNamesFromDatabase();

        for (String course : courseNames) {
            courseDropdown.addItem(course);
        }
    }

    private List<String> fetchCourseNamesFromDatabase() {
        List<String> courseNames = new ArrayList<>();

        // JDBC Connection parameters
        String url = "jdbc:mysql://localhost:3306/assignment_attendance";
        String user = "root";
        String password = "";

        // Database query to retrieve course names
        String query = "SELECT DISTINCT course_id, course_name FROM course;";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String courseName = resultSet.getString("course_name");
                courseNames.add(courseName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseNames;
    }

    private void registerCourse(String selectedCourse, String faculty, String studentID) {
        // JDBC Connection parameters
        String url = "jdbc:mysql://localhost:3306/assignment_attendance";
        String user = "root";
        String password = "";

        // Database insert query
        String insertQuery = "INSERT INTO student (studentid, course_name, faculty) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            // Set the values for the prepared statement
            preparedStatement.setString(1, studentID); // Use the passed studentID
            preparedStatement.setString(2, selectedCourse);
            preparedStatement.setString(3, faculty);

            // Execute the insert query
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Course registration successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Clear the text fields after successful registration
                clearTextFields();
            } else {
                JOptionPane.showMessageDialog(this, "Course registration failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Course registration failed.\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearTextFields() {
        facultyField.setText("");
    }

    private void clearTextFieldsOnSuccess() {
        // Add a WindowListener to the frame
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                // Clear text fields when the frame is closed
                clearTextFields();
            }
        });
    }
}
