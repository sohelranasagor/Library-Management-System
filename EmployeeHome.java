import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class EmployeeHome extends JFrame implements ActionListener
{
	JLabel welcomeLabel;
	JButton manageEmployeeBtn, changePasswordBtn, viewDetailsBtn, logoutBtn,addBook,customerInfo,returnBook,manageBook,borrowBook,searchBook;
	JPanel panel;
	String userId;
	
	public EmployeeHome(String userId)
	{
		super("Library Management System - Employee Home");
		
		this.userId = userId;
		this.setSize(800,450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		welcomeLabel = new JLabel("Welcome, "+userId);
		welcomeLabel.setBounds(350, 50, 100, 30);
		panel.add(welcomeLabel);
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(600, 50, 100, 30);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		changePasswordBtn = new JButton("Change Password");
		changePasswordBtn.setBounds(50, 120, 150, 30);
		changePasswordBtn.addActionListener(this);
		panel.add(changePasswordBtn);
		
		manageEmployeeBtn = new JButton("Manage Employee");
		manageEmployeeBtn.setBounds(220, 120, 150, 30);
		manageEmployeeBtn.addActionListener(this);
		panel.add(manageEmployeeBtn);
		
		viewDetailsBtn = new JButton("My Information");
		viewDetailsBtn.setBounds(400, 120, 150, 30);
		viewDetailsBtn.addActionListener(this);
		panel.add(viewDetailsBtn);
		
		customerInfo = new JButton("Customer Info");
		customerInfo.setBounds(580, 120, 150, 30);
		customerInfo.addActionListener(this);
		panel.add(customerInfo);
		
		addBook = new JButton("Add Book");
		addBook.setBounds(220,170,150,30);
		addBook.addActionListener(this);
		panel.add(addBook);
		
		returnBook = new JButton("Return Book");
		returnBook.setBounds(50,170,150,30);
		returnBook.addActionListener(this);
		panel.add(returnBook);
		
		manageBook = new JButton("Manage Book");
		manageBook.setBounds(400,170,150,30);
		manageBook.addActionListener(this);
		panel.add(manageBook);
		
		borrowBook = new JButton("Borrow Book");
		borrowBook.setBounds(580,170,150,30);
		borrowBook.addActionListener(this);
		panel.add(borrowBook);
		
		searchBook = new JButton("Search Book");
		searchBook.setBounds(50,220,150,30);
		searchBook.addActionListener(this);
		panel.add(searchBook);
		
		this.add(panel);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(logoutBtn.getText()))
		{
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(addBook.getText()))
		{
			AddBook ab = new AddBook(userId);
			ab.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(returnBook.getText()))
		{
			returnBook rb = new returnBook(userId);
			rb.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(viewDetailsBtn.getText()))
		{
			EmployeeInfo ei = new EmployeeInfo(userId);
			ei.setVisible(true);
			this.setVisible(false);
			ei.loadFromDB();
		}
		else if(text.equals(changePasswordBtn.getText()))
		{
			EmployeeChangePassword ecp = new EmployeeChangePassword(userId);
			ecp.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(customerInfo.getText()))
		{
			EmployeeToCustomerInfo etci = new EmployeeToCustomerInfo(userId);
			etci.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(manageBook.getText()))
		{
			EmployeeViewBooks evb = new EmployeeViewBooks(userId);
			evb.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(manageEmployeeBtn.getText()))
		{
			checkRole();
		}
		else if(text.equals(borrowBook.getText()))
		{
			BorrowBook bb = new BorrowBook(userId);
			bb.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(searchBook.getText()))
		{
			EmployeeSearchBook esb = new EmployeeSearchBook(userId);
			esb.setVisible(true);
			this.setVisible(false);
		}
		else{}
	}
	public void checkRole()
	{
		String loadId=userId;
		String query = "SELECT `employeeName`, `phoneNumber`, `role`, `salary` FROM `employee` WHERE `userId`='"+loadId+"';";  
		Connection con=null;//for connection
        Statement st = null;//for query execution
		ResultSet rs = null;//to get row by row result from DB
		System.out.println(query);
		try
		{
			Class.forName("com.mysql.jdbc.Driver");//load driver
			System.out.println("driver loaded");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop1","root","");
			System.out.println("connection done");//connection with database established
			st = con.createStatement();//create statement
			System.out.println("statement created");
			rs = st.executeQuery(query);//getting result
			System.out.println("results received");
			
			boolean flag = false;
			while(rs.next())
			{
				String role = rs.getString("role");
				flag=true;
				if(role.equals("Manager"))
				{
					ManageEmployee me = new ManageEmployee(userId);
					me.setVisible(true);
					this.setVisible(false);
				}
				else
				{
					JOptionPane.showMessageDialog(this, "Access Denied");
				}
			}
		}
			catch(Exception ex)
		{
			System.out.println("Exception : " +ex.getMessage());
        }
        finally
		{
            try
			{
                if(rs!=null)
					rs.close();

                if(st!=null)
					st.close();

                if(con!=null)
					con.close();
            }
            catch(Exception ex){}
        }
		
	}
}