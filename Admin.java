package student_system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Admin extends JFrame{
	
	private ArrayList<String> courses = new ArrayList<>();
    private ArrayList<String> staff = new ArrayList<>();
    private Font content = new Font("Verdana", Font.PLAIN, 13);

    public Admin() {
        super("Student Management System - Admin Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Add Course", createAddCoursePanel());
        tabbedPane.addTab("Remove/Archive Course", createRemoveCoursePanel());
        tabbedPane.addTab("Manage Staff", createManageStaffPanel());

        tabbedPane.setTabPlacement(JTabbedPane.TOP);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        add(tabbedPane, BorderLayout.CENTER);

        getContentPane().setBackground(new Color(150, 200, 150));
        setSize(900,400);

        setVisible(true);
    }

    private JPanel createAddCoursePanel() {
        JPanel panelCreateCourse = new JPanel();
        panelCreateCourse.setLayout(new FlowLayout());
        panelCreateCourse.setBackground(new Color(150, 200, 150));

        JLabel idLabel = new JLabel("Course ID:");
        idLabel.setFont(content);
        JTextField idField = new JTextField(10);

        JLabel nameLabel = new JLabel("Course Name:");
        nameLabel.setFont(content);
        JTextField nameField = new JTextField(20);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseId = idField.getText();
                String courseName = nameField.getText();
                courses.add(courseName);
                
                Connection conn = null;
                PreparedStatement pstmt = null;
                String dbURL = "jdbc:mysql://localhost:3306/assignment_attendance";
                String username = "root";
                String password = "";
                
                try {
                    conn = DriverManager.getConnection(dbURL, username, password);

                    String sql = "INSERT INTO a_course (course_id, course_name) VALUES (?, ?)";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, courseId);
                    pstmt.setString(2, courseName);
                    pstmt.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Course added successfully");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error adding course: " + ex.getMessage());
                    ex.printStackTrace();
                } finally {
                    try {
                        if (pstmt != null) {
                            pstmt.close();
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }

                idField.setText("");
                nameField.setText(""); 
            }
        });

        panelCreateCourse.add(idLabel);
        panelCreateCourse.add(idField);
        panelCreateCourse.add(nameLabel);
        panelCreateCourse.add(nameField);
        panelCreateCourse.add(addButton);

        return panelCreateCourse;
    }


    private JPanel createRemoveCoursePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(242, 246, 252));

        DefaultListModel<String> courseListModel = new DefaultListModel<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String dbURL = "jdbc:mysql://localhost:3306/assignment_attendance";
        String username = "root";
        String password = "";

        try {
            conn = DriverManager.getConnection(dbURL, username, password);

            String sql = "SELECT course_name FROM a_course";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String courseName = rs.getString("course_name");
                courseListModel.addElement(courseName);
                courses.add(courseName);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error fetching courses: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        JList<String> courseList = new JList<>(courseListModel);

        JButton removeButton = new JButton("Remove/Archive");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCourse = courseList.getSelectedValue();

                Connection conn = null;
                PreparedStatement pstmt = null;
                String dbURL = "jdbc:mysql://localhost:3306/assignment_attendance";
                String username = "root";
                String password = "";
            
                try {
                    conn = DriverManager.getConnection(dbURL, username, password);

                    String sql = "DELETE FROM a_course WHERE course_name = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, selectedCourse);
                    int rowsAffected = pstmt.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Course removed successfully");
                        
                        courseListModel.removeElement(selectedCourse);
                    } else {
                        JOptionPane.showMessageDialog(null, "Course not found in the database");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error removing course: " + ex.getMessage());
                    ex.printStackTrace();
                } finally {
                    try {
                        if (pstmt != null) {
                            pstmt.close();
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        panel.add(new JScrollPane(courseList), BorderLayout.CENTER);
        panel.add(removeButton, BorderLayout.SOUTH);

        return panel;
    }


    private JPanel createManageStaffPanel() {
    	JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(150, 200, 150));

        mainPanel.add(createAddStaffForm());
        mainPanel.add(createModifyStaffForm());
        mainPanel.add(createRemoveStaffList());

        return mainPanel;
    }
    
    private JPanel createAddStaffForm() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(new Color(150, 200, 150));

        JTextField idField = new JTextField(10);
        JTextField nameField = new JTextField(10);
        JTextField dobField = new JTextField(10);
        JTextField contactNumField = new JTextField(10);


        JRadioButton maleButton = new JRadioButton("M");
        JRadioButton femaleButton = new JRadioButton("F");

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String name = nameField.getText();
                String gender = maleButton.isSelected() ? "M" : "F";
                String dob = dobField.getText();
                String contactNum = contactNumField.getText();

                Connection conn = null;
                PreparedStatement pstmt = null;
                String dbURL = "jdbc:mysql://localhost:3306/assignment_attendance";
                String username = "root";
                String password = "";
                
                try {
                    conn = DriverManager.getConnection(dbURL, username, password);

                    String sql = "INSERT INTO a_staff (id, name, gender, dob, contact_num) VALUES (?, ?, ?, ?, ?)";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, id);
                    pstmt.setString(2, name);
                    pstmt.setString(3, gender);
                    pstmt.setString(4, dob);
                    pstmt.setString(5, contactNum);
                    pstmt.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Staff added successfully");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error adding staff: " + ex.getMessage());
                    ex.printStackTrace();
                } finally {
                    try {
                        if (pstmt != null) {
                            pstmt.close();
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                idField.setText("");
                nameField.setText("");
                genderGroup.clearSelection();
                dobField.setText("");
                contactNumField.setText("");
            }
        });

        JLabel idLabel = new JLabel("ID:");
        idLabel.setFont(content);
        panel.add(idLabel);
        panel.add(idField);
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(content);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(new JLabel("Gender:"));
        maleButton.setBackground(new Color(150, 200, 150));
        femaleButton.setBackground(new Color(150, 200, 150));
        maleButton.setFont(content);
        femaleButton.setFont(content);
        panel.add(maleButton);
        panel.add(femaleButton);
        JLabel dobLabel = new JLabel("DOB:");
        dobLabel.setFont(content);
        panel.add(dobLabel);
        panel.add(dobField);
        JLabel contNumLabel = new JLabel("Contact Number:");
        contNumLabel.setFont(content);
        panel.add(contNumLabel);
        panel.add(contactNumField);
        panel.add(addButton);

        return panel;
    }

    private JPanel createModifyStaffForm() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(new Color(150, 200, 150));

        JTextField idField = new JTextField(10);
        JTextField nameField = new JTextField(10);
        JTextField contactNumField = new JTextField(10);

        JButton modifyButton = new JButton("Modify");
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String name = nameField.getText();
                String contactNum = contactNumField.getText();

                Connection conn = null;
                PreparedStatement pstmt = null;
                String dbURL = "jdbc:mysql://localhost:3306/assignment_attendance";
                String username = "root";
                String password = "";
                
                try {
                    conn = DriverManager.getConnection(dbURL, username, password);

                    String sql = "UPDATE a_staff SET name = ?, contact_num = ? WHERE id = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, name);
                    pstmt.setString(2, contactNum);
                    pstmt.setString(3, id);
                    pstmt.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Staff modified successfully");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error modifying staff: " + ex.getMessage());
                    ex.printStackTrace();
                } finally {
                    try {
                        if (pstmt != null) {
                            pstmt.close();
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }

                idField.setText("");
                nameField.setText("");
                contactNumField.setText("");
            }
        });

        JLabel labelID = new JLabel("ID:");
        labelID.setFont(content);
        panel.add(labelID);
        panel.add(idField);
        JLabel newNameLabel = new JLabel("New Name:");
        newNameLabel.setFont(content);
        panel.add(newNameLabel);
        panel.add(nameField);
        JLabel newContNumLabel = new JLabel("New Contact Number:");
        newContNumLabel.setFont(content);
        panel.add(newContNumLabel);
        panel.add(contactNumField);
        panel.add(modifyButton);

        return panel;
    }

    private JPanel createRemoveStaffList() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(150, 200, 150));

        DefaultListModel<String> staffListModel = new DefaultListModel<>();
        JList<String> staffList = new JList<>(staffListModel);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String dbURL = "jdbc:mysql://localhost:3306/assignment_attendance";
        String username = "root";
        String password = "";

        try {
            conn = DriverManager.getConnection(dbURL, username, password);

            String sql = "SELECT id, name FROM a_staff";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                staffListModel.addElement(id + ": " + name);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error fetching staff: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedStaff = staffList.getSelectedValue();
                String id = selectedStaff.split(":")[0];

                Connection conn = null;
                PreparedStatement pstmt = null;
                String dbURL = "jdbc:mysql://localhost:3306/assignment_attendance";
                String username = "root";
                String password = "";
                
                try {
                    conn = DriverManager.getConnection(dbURL, username, password);

                    String sql = "DELETE FROM a_staff WHERE id = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, id);
                    pstmt.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Staff removed successfully");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error removing staff: " + ex.getMessage());
                    ex.printStackTrace();
                } finally {
                    try {
                        if (pstmt != null) {
                            pstmt.close();
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }

                staffListModel.removeElement(selectedStaff);
            }
        });

        panel.add(new JScrollPane(staffList), BorderLayout.CENTER);
        panel.add(removeButton, BorderLayout.SOUTH);
        
        return panel;
    }

}

