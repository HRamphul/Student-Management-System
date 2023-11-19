
package student_system;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet.ColorAttribute;



public class Staff extends JFrame {

	private JLabel title, viewLecturerLabel, viewStudentLabel, addStudentLabel, addLecturerLabel, viewAttendanceLabel;
	private JTextField jtusername, jtpassword, jtcourseStudent, jtselectmodule, jtselectstudent;
	private JPasswordField password;
	private String username = "Welcome back ", selectmodule = "", selectstudent = "";
	private JButton jbaddteacher, jbviewMark, jbviewstudent, jbviewatt, jblogout, login,
			jbmoduleStudent, btnSearch, btnDefaulter;
	private JPanel jppassword, jpusername, main, jpbutton, toptitle, panelCheckBox, panelCheckBox1, addSDetails,
			jpmodulecodeS, jpradio;
	private StartPage firstPage;
	private ArrayList<String> almoduleCodes, almoduleCodes1;
	private ArrayList<JCheckBox> alcheckbox, alcheckbox1;
	private java.sql.Date dateFrom, dateTo;
	final static Color lightgreen = new Color(168, 209, 193);
	final static Font titles = new Font("Verdana", Font.BOLD, 28);
	final static Font header = new Font("Verdana", Font.BOLD, 16);
	final static Color darkgreen = new Color(107, 198, 165);
	final static Font content = new Font("Verdana", Font.PLAIN, 15);
	JTable table1, table2;
	DefaultTableModel model1, model2;
	JScrollPane scrollPaneAttendance;

	static int h = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight());
	static int w = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth());

	public Staff(StartPage start, String staffName) {
		super("");
		this.firstPage = start;
		this.username += staffName;
		FlowLayout layout = new FlowLayout();
		setLayout(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		getContentPane().setBackground(new Color(84, 78, 80));
		setVisible(true);

		JPanel topmain = new JPanel();
		topmain.setLayout(new GridLayout(0, 5));
		topmain.setLocation((int) (0.05 * w), 10);
		topmain.setSize((int) (0.9 * w), (int) (0.07 * h));
		topmain.setBackground(new Color(107, 198, 165));

		jbaddteacher = new JButton("Add marking");
		jbaddteacher.setBackground(new Color(107, 198, 165));
		jbaddteacher.setBorderPainted(false);
		jbaddteacher.setFocusable(false);


		jbviewMark = new JButton("Summarised Mark");
		jbviewMark.setBackground(new Color(107, 198, 165));
		jbviewMark.setBorderPainted(false);
		jbviewMark.setFocusable(false);

		jbviewstudent = new JButton("Consolidated Marksheet");
		jbviewstudent.setBackground(new Color(107, 198, 165));
		jbviewstudent.setBorderPainted(false);
		jbviewstudent.setFocusable(false);

		jbviewatt = new JButton("");
		jbviewatt.setBackground(new Color(107, 198, 165));
		jbviewatt.setBorderPainted(false);
		jbviewatt.setFocusable(false);
		jbviewatt.setEnabled(false);
		
		jblogout = new JButton("Logout");
		jblogout.setBackground(new Color(107, 198, 165));
		jblogout.setBorderPainted(false);
		jblogout.setFocusable(false);

		topmain.add(jbaddteacher);
//		topmain.add(jbaddstudent);
		topmain.add(jbviewMark);
		topmain.add(jbviewstudent);
		topmain.add(jbviewatt);
		topmain.add(jblogout);
//        topmain.add(top);
		add(topmain);

		// Register event for logout
		AdminLogout logoutHandler = new AdminLogout();
		jblogout.addActionListener(logoutHandler);

		// panel bottom
		JPanel bottom = new JPanel();
		CardLayout card = new CardLayout();// creating sub card layout contained by c1
		bottom.setLayout(card);
		bottom.setBounds((int) (0.05 * w), (int) (0.1 * h), (int) (0.9 * w), (int) (0.8 * h));
		bottom.setBackground(new Color(227, 227, 225));

		// --------------------------welcome------------------------------------------------------

		JPanel panelWelcome = new JPanel(new BorderLayout());
		panelWelcome.setBorder(new EmptyBorder(20, 400, 50, 400));
		JLabel welcomeTitle = new JLabel(username, SwingConstants.CENTER);
		welcomeTitle.setFont(titles);
		panelWelcome.add(welcomeTitle, BorderLayout.NORTH);

		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		panelWelcome.add(container, BorderLayout.CENTER);

		bottom.add(panelWelcome);

		
		
		
		// ---------------------view summarized mark----------------------------------//


		JPanel panelViewSmark = new JPanel(new BorderLayout());
		JPanel jptop1 = new JPanel(new GridLayout(2, 1, 0, 30));
		JPanel jpenterId1 = new JPanel(new FlowLayout());
		JTextArea displaySM = new JTextArea(7, 40);

		
		JLabel viewMarkTitle = new JLabel("Summarised Mark-sheet", SwingConstants.CENTER);
		viewMarkTitle.setFont(titles);
		
		JPanel displayPanel11 = new JPanel();
		displayPanel11.setLayout(new BoxLayout(displayPanel11, BoxLayout.Y_AXIS));
		displayPanel11.setBorder(new EmptyBorder(20, 50, 20, 50));

		panelViewSmark.removeAll();
		//add the label of summarized mark sheet on top of the main container 
		panelViewSmark.add(viewMarkTitle, BorderLayout.NORTH); 

		DefaultTableCellRenderer centerRenderer1 = new DefaultTableCellRenderer();
		centerRenderer1.setHorizontalAlignment(JLabel.CENTER);
		DefaultTableModel model11 = new DefaultTableModel();
		model11.addColumn("Student ID");
		model11.addColumn("Module Name");
		model11.addColumn("Coursework Mark");
		model11.addColumn("Exam Mark");
		model11.addColumn("Total Mark");


		
		JTable tables = new JTable(model11);
		tables.setAutoCreateRowSorter(true);

		tables.getColumnModel().getColumn(0).setCellRenderer(centerRenderer1);
		tables.getColumnModel().getColumn(1).setCellRenderer(centerRenderer1);
		tables.getColumnModel().getColumn(2).setCellRenderer(centerRenderer1);
		tables.getColumnModel().getColumn(3).setCellRenderer(centerRenderer1);
		tables.getColumnModel().getColumn(4).setCellRenderer(centerRenderer1);
	


		tables.getColumnModel().getColumn(0).setPreferredWidth(20);
		tables.getColumnModel().getColumn(1).setPreferredWidth(60);
		tables.getColumnModel().getColumn(2).setPreferredWidth(20);
		tables.getColumnModel().getColumn(3).setPreferredWidth(5);
		tables.getColumnModel().getColumn(4).setPreferredWidth(5);

		

		tables.getTableHeader().setFont(header);
		tables.getTableHeader().setBackground(darkgreen);
		tables.setFont(content);
		tables.setRowHeight(30);

		jbviewMark.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Connection conn = null;
				String dbURL = "jdbc:mysql://localhost:3306/assignment_attendance";
				String username = "root";
				String password = "";
				model11.setRowCount(0);

				try {
		            conn = DriverManager.getConnection(dbURL, username, password);
		            String query = "SELECT * FROM `student_mark` ORDER BY `student_mark`.`studentID` ASC";
		            Statement stmt = conn.createStatement();
		            ResultSet rs = stmt.executeQuery(query);

		            while (rs.next()) {
		                // Calculate the Total Mark as the sum of Course-work Mark and Exam Mark
		                int courseworkMark = rs.getInt(3);
		                int examMark = rs.getInt(4);
		                int totalMark = courseworkMark + examMark;

		                // Add the row to the model1 with Total Mark included
		                model11.addRow(new Object[] { rs.getString(1), rs.getString(2), courseworkMark, examMark,totalMark  });
		            }
					displayPanel11.removeAll();
					JScrollPane scrollPaneStudents = new JScrollPane(tables);
					displayPanel11.add(scrollPaneStudents);
					panelViewSmark.removeAll();

					panelViewSmark.add(viewMarkTitle, BorderLayout.NORTH);
					panelViewSmark.add(displayPanel11, BorderLayout.CENTER);

					panelViewSmark.add(jptop1, BorderLayout.NORTH);
					jptop1.add(viewMarkTitle);
					panelViewSmark.add(displayPanel11, BorderLayout.CENTER);


					jptop1.add(jpenterId1);

					conn.close();
				} catch (Exception e) {
					System.err.print(e);
				}
			}

		});

		
		

		
		
		
		// ------------Consolidated mark------------------//



		JPanel bottomT = new JPanel();
		CardLayout cardT = new CardLayout();// creating sub card layout contained by c1
		bottomT.setLayout(cardT);
		bottomT.setBackground(new Color(227, 227, 225));

		JPanel panelModifyLecturer = new JPanel(new BorderLayout());
		
		JLabel addmarkTitle = new JLabel("Add Marking", SwingConstants.CENTER);
		addmarkTitle.setFont(titles);



		JPanel addTDetails = new JPanel(new FlowLayout(FlowLayout.CENTER, 240, 20));
		addTDetails.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 100));
		panelModifyLecturer.setBorder(BorderFactory.createEmptyBorder(75, 250, 10, 250));
		addTDetails.setBackground(new Color(203, 219, 205));

		JPanel jpusernameT = new JPanel(new GridLayout(1, 2));
		jpusernameT.setBackground(new Color(203, 219, 205));
		JTextField jtstudID = new JTextField(20);
		JLabel jlusernameLecturer = new JLabel("Student ID:");
		jtstudID.setBackground(new Color(107, 198, 165));
		jtstudID.setForeground(new Color(84, 78, 80));
		jtstudID.setBorder(null);
		jtstudID.setFont(new Font("Sans-Serif", Font.PLAIN, 17));
		jpusernameT.add(jlusernameLecturer);
		jpusernameT.add(jtstudID);
		addTDetails.add(jpusernameT);

		JPanel jpfnameT = new JPanel(new GridLayout(1, 2));
		jpfnameT.setBackground(new Color(203, 219, 205));
		JTextField jtSubject = new JTextField(20);
		JLabel jlfnameLecturer = new JLabel("Module:");
		jtSubject.setBackground(new Color(107, 198, 165));
		jtSubject.setForeground(new Color(84, 78, 80));
		jtSubject.setBorder(null);
		jtSubject.setFont(new Font("Sans-Serif", Font.PLAIN, 17));
		jpfnameT.add(jlfnameLecturer);
		jpfnameT.add(jtSubject);
		addTDetails.add(jpfnameT);

		JPanel jplnameT = new JPanel(new GridLayout(1, 2));
		jplnameT.setBackground(new Color(203, 219, 205));
		JTextField jtCmark = new JTextField(20);
		JLabel jllnameLecturer = new JLabel("Course Mark:");
		jtCmark.setBackground(new Color(107, 198, 165));
		jtCmark.setForeground(new Color(84, 78, 80));
		jtCmark.setBorder(null);
		jtCmark.setFont(new Font("Sans-Serif", Font.PLAIN, 17));
		jplnameT.add(jllnameLecturer);
		jplnameT.add(jtCmark);
		addTDetails.add(jplnameT);


		//ExamMark
		JPanel jpsubjectT = new JPanel(new GridLayout(1, 2));
		jpsubjectT.setBackground(new Color(203, 219, 205));
		JTextField jtEmark = new JTextField(20);
		JLabel jlsubjectLecturer = new JLabel("Exam Mark:");
		jtEmark.setBackground(new Color(107, 198, 165));
		jtEmark.setForeground(new Color(84, 78, 80));
		jtEmark.setBorder(null);
		jtEmark.setFont(new Font("Sans-Serif", Font.PLAIN, 17));
		jpsubjectT.add(jlsubjectLecturer);
		jpsubjectT.add(jtEmark);
		addTDetails.add(jpsubjectT);

		//course
		JPanel jpcourse = new JPanel(new GridLayout(1, 2));
		jpcourse.setBackground(new Color(203, 219, 205));
		JTextField jtcourse = new JTextField(20);
		JLabel jlcourse = new JLabel("Course:");
		jtcourse.setBackground(new Color(107, 198, 165));
		jtcourse.setForeground(new Color(84, 78, 80));
		jtcourse.setBorder(null);
		jtcourse.setFont(new Font("Sans-Serif", Font.PLAIN, 17));
		jpcourse.add(jlcourse);
		jpcourse.add(jtcourse);
		addTDetails.add(jpcourse);
		
		//semester
		JPanel jpradio = new JPanel(new GridLayout(1,2));
		jpradio.setBackground(new Color(203, 219, 205));
		JPanel jpRadioButtons = new JPanel();
		jpRadioButtons.setBackground(new Color(203, 219, 205));
		JLabel jlradio = new JLabel("Semester: ");
		jlradio.setHorizontalAlignment(SwingConstants.LEFT); // Set the label alignment to the left
		JRadioButton radioOption1 = new JRadioButton("1");
		JRadioButton radioOption2 = new JRadioButton("2");
		radioOption1.setBackground(new Color(203, 219, 205));
		radioOption2.setBackground(new Color(203, 219, 205));
		final int[] selectedSem = {1}; // Default to '1'
		radioOption1.addActionListener(ev -> selectedSem[0] = 1);
		radioOption2.addActionListener(ev -> selectedSem[0] = 2);
		// Set the default selection to '1'
		radioOption1.setSelected(true);
		
		
		jpRadioButtons.add(radioOption1);
		jpRadioButtons.add(radioOption2);

		
		jpradio.add(jlradio);
		jpradio.add(jpRadioButtons);
		addTDetails.add(jpradio);
			
		
		// Add button
		JPanel jpAdd = new JPanel();
		jpAdd.setBackground(new Color(203, 219, 205));
		JButton AddT = new JButton("Add");
		AddT.setPreferredSize(new Dimension(150, 40));

		AddT.setBackground(new Color(80, 80, 80));
		AddT.setForeground(Color.white);
		AddT.setFocusPainted(false);
		AddT.setBorderPainted(false);
		jpAdd.add(AddT);
		addTDetails.add(jpAdd);
		
		

//		panelModifyLecturer.add(topAddT);
		bottomT.add(addTDetails, "Add Mark");
		panelModifyLecturer.add(bottomT, BorderLayout.CENTER);

		AddT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String dbURL = "jdbc:mysql://localhost:3306/assignment_attendance";
				String username = "root";
				String password = "";

				Connection conn = null;
				try {
					conn = DriverManager.getConnection(dbURL, username, password);

					String ins = " INSERT INTO student_mark VALUES (?,?,?,?,?,?)";

					PreparedStatement stmt = conn.prepareStatement(ins);
					stmt.setString(1, jtstudID.getText());
					stmt.setString(2, jtSubject.getText());
					stmt.setInt(3, Integer.parseInt(jtCmark.getText()));
					stmt.setInt(4, Integer.parseInt(jtEmark.getText()));
					stmt.setString(5, jtcourse.getText());
					stmt.setInt(6, selectedSem[0]);


					
					int row = stmt.executeUpdate();

					if (row > 0)
						JOptionPane.showMessageDialog(null, "A row has been inserted! :)");

					stmt.close();
					conn.close();

				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Insertion Failed. \n Make sure all inputs are valid");
					e.printStackTrace();
				}

			}

		});

		////////////////////////////////////////////////////

		

//		panelModifyLecturer.add(topAddT);
		panelModifyLecturer.add(bottomT);





		


		// view Consolidated Mark-sheet**//

		JPanel panelViewStudent = new JPanel(new BorderLayout());
		JPanel jptop = new JPanel(new GridLayout(2, 1, 0, 30));
		JPanel jpenterId = new JPanel(new FlowLayout());
		JTextArea displayS = new JTextArea(7, 40);

		JLabel enterSname = new JLabel("Enter semester :", SwingConstants.CENTER);
		JTextField jtentersname = new JTextField(15);
		JLabel viewStudentsTitle = new JLabel("Consolidated Mark-sheet", SwingConstants.CENTER);
		viewStudentsTitle.setFont(titles);
		
		
		
		JLabel enterCourse = new JLabel("Enter Course Name :", SwingConstants.CENTER);
		JTextField jtenterCourse = new JTextField(15);

		
		
		// search button
		JButton jbSearchSid = new JButton("Search");
		jbSearchSid.setPreferredSize(new Dimension(120, 30));
		jbSearchSid.setBackground(new Color(80, 80, 80));
		jbSearchSid.setForeground(Color.white);
		jbSearchSid.setFocusPainted(false);
		jbSearchSid.setBorderPainted(false);

		
		
		JPanel displayPanel1 = new JPanel();
		displayPanel1.setLayout(new BoxLayout(displayPanel1, BoxLayout.Y_AXIS));
		displayPanel1.setBorder(new EmptyBorder(20, 50, 20, 50));

		panelViewStudent.removeAll();
		panelViewStudent.add(viewStudentsTitle, BorderLayout.NORTH);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		DefaultTableModel model1 = new DefaultTableModel();
		model1.addColumn("Student ID");
		model1.addColumn("Module Name");
		model1.addColumn("Coursework Mark");
		model1.addColumn("Exam Mark");
		model1.addColumn("Total Mark");
		model1.addColumn("Course");
		model1.addColumn("Semester");

		
		JTable table = new JTable(model1);
		table.setAutoCreateRowSorter(true);

		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);



		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(60);
		table.getColumnModel().getColumn(2).setPreferredWidth(20);
		table.getColumnModel().getColumn(3).setPreferredWidth(5);
		table.getColumnModel().getColumn(4).setPreferredWidth(5);
		table.getColumnModel().getColumn(5).setPreferredWidth(20);
		table.getColumnModel().getColumn(6).setPreferredWidth(5);

		

		table.getTableHeader().setFont(header);
		table.getTableHeader().setBackground(darkgreen);
		table.setFont(content);
		table.setRowHeight(30);

		jbviewstudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Connection conn = null;
				String dbURL = "jdbc:mysql://localhost:3306/assignment_attendance";
				String username = "root";
				String password = "";
				model1.setRowCount(0);

				try {
		            conn = DriverManager.getConnection(dbURL, username, password);
		            String query = "SELECT * FROM `student_mark` ORDER BY `student_mark`.`studentID` ASC";
		            Statement stmt = conn.createStatement();
		            ResultSet rs = stmt.executeQuery(query);

		            while (rs.next()) {
		                // Calculate the Total Mark as the sum of Coursework Mark and Exam Mark
		                int courseworkMark = rs.getInt(3);
		                int examMark = rs.getInt(4);
		                int totalMark = courseworkMark + examMark;

		                // Add the row to the model1 with Total Mark included
		                model1.addRow(new Object[] { rs.getString(1), rs.getString(2), courseworkMark, examMark, totalMark, rs.getString(5), rs.getInt(6)  });
		            }
					displayPanel1.removeAll();
					JScrollPane scrollPaneStudents = new JScrollPane(table);
					displayPanel1.add(scrollPaneStudents);
					panelViewStudent.removeAll();

					panelViewStudent.add(viewStudentsTitle, BorderLayout.NORTH);
					panelViewStudent.add(displayPanel1, BorderLayout.CENTER);

					panelViewStudent.add(jptop, BorderLayout.NORTH);
					jptop.add(viewStudentsTitle);
					panelViewStudent.add(displayPanel1, BorderLayout.CENTER);

					jpenterId.add(enterSname);
					jpenterId.add(jtentersname);
					jpenterId.add(enterCourse);
					jpenterId.add(jtenterCourse);
					
					jpenterId.add(jbSearchSid);

					jptop.add(jpenterId);

					conn.close();
				} catch (Exception e) {
					System.err.print(e);
				}
			}

		});

		jbSearchSid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Connection conn = null;
				String dbURL = "jdbc:mysql://localhost:3306/assignment_attendance";
				String username = "root";
				String password = "";
				model1.setRowCount(0);

				try {
					conn = DriverManager.getConnection(dbURL, username, password);
					String course = jtenterCourse.getText();
					String semester = jtentersname.getText();
					String query = "SELECT * FROM student_mark";
					System.out.print("C = " + course);
					System.out.print("S =" + semester);
					
					if(course.isEmpty() && semester.isEmpty()) {
						 query = "SELECT * FROM student_mark ";
					}
					
					else if(course.isEmpty() && !semester.isEmpty()) {
						int sem = Integer.parseInt(semester);
						 query = "SELECT * FROM student_mark WHERE semester= '"
							+ sem + "'";
					}
					else if (semester.isEmpty() && !course.isEmpty()) {
						 query = "SELECT * FROM student_mark WHERE Course= '"
								+ course + "'";
						
					}
					else if(!course.isEmpty() && !semester.isEmpty()) {
						int sem = Integer.parseInt(semester);
						 query = "SELECT * FROM student_mark WHERE semester= '"
								+ sem + "'" + " AND Course = '" + course + "'"  ;
					}
					
					Statement stmt = conn.createStatement();
					System.out.print(query);
					ResultSet rs = stmt.executeQuery(query);

					while (rs.next()) {
						
		                int courseworkMark = rs.getInt(3);
		                int examMark = rs.getInt(4);
		                int totalMark = courseworkMark + examMark;

		                model1.addRow(new Object[] {rs.getString(1), rs.getString(2), 
		                							courseworkMark, examMark, totalMark,
		                							rs.getString(5), rs.getInt(6)  });

					}

					jpenterId.add(enterSname);
					jpenterId.add(jtentersname);
					jpenterId.add(jbSearchSid);

					jptop.add(jpenterId);

					conn.close();
				} catch (Exception e) {
					System.err.print(e);
				}
			}

		});

		// add student*//

		JPanel panelModifyStudent = new JPanel(new BorderLayout());






		// view attendance*//

		JPanel panelViewAttendance = new JPanel(new BorderLayout());

		bottom.add(panelWelcome);
		bottom.add(panelModifyLecturer, "Add Lecturer");
		bottom.add(panelViewSmark, "View Lecturer");
		bottom.add(panelModifyStudent, "Add Student");
		bottom.add(panelViewStudent, "View Student");
		bottom.add(panelViewAttendance, "");





		jbaddteacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				card.show(bottom, "Add Lecturer");
				jbaddteacher.setFont(new Font("Verdana", Font.BOLD, 16));
//				jbaddstudent.setFont(new Font("Verdana", Font.PLAIN, 15));
				jbviewMark.setFont(new Font("Verdana", Font.PLAIN, 15));
				jbviewstudent.setFont(new Font("Verdana", Font.PLAIN, 15));
				jbviewatt.setFont(new Font("Verdana", Font.PLAIN, 15));

				jbaddteacher.setBackground(new Color(168, 209, 193));
//				jbaddstudent.setBackground(new Color(107, 198, 165));
				jbviewMark.setBackground(new Color(107, 198, 165));
				jbviewstudent.setBackground(new Color(107, 198, 165));
				jbviewatt.setBackground(new Color(107, 198, 165));
			}
		});
		jbviewMark.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				card.show(bottom, "View Lecturer");
				jbaddteacher.setFont(new Font("Verdana", Font.PLAIN, 15));
	//			.setFont(new Font("Verdana", Font.PLAIN, 15));
				jbviewMark.setFont(new Font("Verdana", Font.BOLD, 16));
				jbviewstudent.setFont(new Font("Verdana", Font.PLAIN, 15));
				jbviewatt.setFont(new Font("Verdana", Font.PLAIN, 15));

				jbaddteacher.setBackground(new Color(107, 198, 165));
	//			jbaddstudent.setBackground(new Color(107, 198, 165));
				jbviewMark.setBackground(new Color(168, 209, 193));
				jbviewstudent.setBackground(new Color(107, 198, 165));
				jbviewatt.setBackground(new Color(107, 198, 165));
			}
		});

		jbviewstudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				card.show(bottom, "View Student");
				jbaddteacher.setFont(new Font("Verdana", Font.PLAIN, 15));
				jbviewMark.setFont(new Font("Verdana", Font.PLAIN, 15));
				jbviewstudent.setFont(new Font("Verdana", Font.BOLD, 16));
				jbviewatt.setFont(new Font("Verdana", Font.PLAIN, 15));

				jbaddteacher.setBackground(new Color(107, 198, 165));
				jbviewMark.setBackground(new Color(107, 198, 165));
				jbviewstudent.setBackground(new Color(168, 209, 193));
				jbviewatt.setBackground(new Color(107, 198, 165));
			}
		});
		jbviewatt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				card.show(bottom, "View Attendance");
				jbaddteacher.setFont(new Font("Verdana", Font.PLAIN, 15));
				jbviewMark.setFont(new Font("Verdana", Font.PLAIN, 15));
				jbviewstudent.setFont(new Font("Verdana", Font.PLAIN, 15));
				jbviewatt.setFont(new Font("Verdana", Font.BOLD, 16));

				jbaddteacher.setBackground(new Color(107, 198, 165));
				jbviewMark.setBackground(new Color(107, 198, 165));
				jbviewstudent.setBackground(new Color(107, 198, 165));
				jbviewatt.setBackground(new Color(168, 209, 193));
			}
		});

		add(bottom);

	}


	private class AdminLogout implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to Logout?", "Logout",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (option == JOptionPane.YES_OPTION) {
				dispose();
				firstPage.setVisible(true);
			}
		}
	}

}