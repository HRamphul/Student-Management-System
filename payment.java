package student_system;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;
 


public class payment extends JFrame {
	JPanel payment1 = new JPanel(new GridLayout(3,1));
	private JRadioButton cashRadio, creditCardRadio , directDebitRadio;
	private JPanel p1,p2,p3;
	private JLabel l1,l2;
	private JButton submit;
	private JTextField t1;
	String selectedPaymentMethod = null;
	public payment(String studentID) {
		super("Payment ");
		 cashRadio = new JRadioButton("Cash");
         creditCardRadio = new JRadioButton("Credit Card");
         directDebitRadio = new JRadioButton("Direct Debit");
		ButtonGroup gp = new ButtonGroup();
		gp.add(cashRadio);
		gp.add(creditCardRadio);
		gp.add(directDebitRadio);
		l2 = new JLabel ("Please choose the payment method : ");
		p1 = new JPanel(new GridLayout(4,1));
		p1.add(l2);
		p1.add(cashRadio);
		p1.add(creditCardRadio);
		p1.add(directDebitRadio);
		submit = new JButton("Submit");
		p2 = new JPanel(new FlowLayout());
		p2.add(submit);
		l1 = new JLabel("Payment Summary ");
		t1 = new JTextField(30);
		t1.setEditable(false);
		p3 = new JPanel(new FlowLayout());
		p3.add(l1);
		p3.add(t1);
		payment1.add(p1);
		payment1.add(p2);
		payment1.add(p3);
		add(payment1);
		 setSize(400, 200);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setLocationRelativeTo(null);
         setVisible(true);  
		
		submit.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (cashRadio.isSelected()) {
		            selectedPaymentMethod = "Cash";
		        } else if (creditCardRadio.isSelected()) {
		            selectedPaymentMethod = "Credit Card";
		        } else if (directDebitRadio.isSelected()) {
		            selectedPaymentMethod = "Direct Debit";
		        }
		        t1.setText("Proceed to payment by " + selectedPaymentMethod + " at the registrar");

		        // Insert the selected payment method into the database
		        try {
		            String url = "jdbc:mysql://localhost:3306/assignment_attendance";
		            String username = "root";
		            String password = "";

		            Connection connection = DriverManager.getConnection(url, username, password);
		            String query = "INSERT INTO stud_payment (studentID, payment) VALUES (?, ?)";
		            PreparedStatement statement = connection.prepareStatement(query);
		            statement.setString(1, studentID);
		            statement.setString(2, selectedPaymentMethod);
		            statement.executeUpdate();
		            statement.close();
		            connection.close();

		            // Inform the user that the data has been stored in the database
		            JOptionPane.showMessageDialog(payment.this, "Payment method stored in the database.");
		        } catch (SQLException ex) {
		            System.out.println("Error inserting payment method into the database: " + ex);
		            JOptionPane.showMessageDialog(payment.this, "Error inserting payment method into the database. \n"+ ex);
		        }
		    }
		});

		
	}
	public String getSelectedPaymentMethod() {
        return selectedPaymentMethod;
    }
	

	    	 
}