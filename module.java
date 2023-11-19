package student_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class module extends JFrame {
    private JComboBox<String> dropdown;
    private JList<String> moduleList;
    private JButton confirmButton;
    private String studID;
    private String courseName;
    private Connection connection;

    public module(String sID, String course_name) {
        super("Module Selection");
        this.studID = sID;
        this.courseName = course_name;

        JPanel panel = new JPanel(new BorderLayout());

        JPanel coursePanel = new JPanel();
        JLabel courseLabel = new JLabel("Selected Course:");
        JLabel selectedCourseLabel = new JLabel(courseName); // Display the selected course name
        coursePanel.add(courseLabel);
        coursePanel.add(selectedCourseLabel);

        panel.add(coursePanel, BorderLayout.NORTH);

        moduleList = new JList<>();
        moduleList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(moduleList);

        panel.add(scrollPane, BorderLayout.CENTER);

        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                storeSelectedModules();
            }
        });

        panel.add(confirmButton, BorderLayout.SOUTH);

        add(panel);

        try {
            // Set up a connection to the database
            String url = "jdbc:mysql://localhost:3306/assignment_attendance";
            String username = "root";
            connection = DriverManager.getConnection(url, username, "");
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e);
        }

        updateModuleList(courseName);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,400);
     //   pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
    }

    private void updateModuleList(String courseName) {
        DefaultListModel<String> moduleListModel = new DefaultListModel<>();
        String query = "SELECT module FROM course WHERE course_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setString(1, courseName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String module = resultSet.getString("module");
                moduleListModel.addElement(module);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching modules for the selected course: " + e);
        }
        moduleList.setModel(moduleListModel);
    }


    private void storeSelectedModules() {
    	String studentID = studID;
        String selectedCourse = courseName; // Assuming you want to use the current courseName
        if (selectedCourse != null) {
            ArrayList<String> selectedModules = new ArrayList<>(moduleList.getSelectedValuesList());
            if (!selectedModules.isEmpty()) {
                String query = "INSERT INTO stud_module (studentID, course_name, module) VALUES (?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    for (String selectedModule : selectedModules) {
                        statement.setString(1, studentID);
                        statement.setString(2, selectedCourse);
                        statement.setString(3, selectedModule);
                        statement.executeUpdate();
                    }
                    JOptionPane.showMessageDialog(this, "Modules selected and stored successfully.");
                } catch (SQLException e) {
                    System.out.println("Error storing selected modules: " + e);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select at least one module.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a course first.");
        }
    }

   
 
}