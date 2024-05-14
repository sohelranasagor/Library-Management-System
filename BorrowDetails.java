import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

public class BorrowDetails extends JFrame implements ActionListener
{
	JPanel jPanel1;
	JLabel bookIdLabel, borrowIdLabel, borrowDateLabel, returnDateLabel;
	JButton backBtn, logoutBtn;
	JTable list;
	
	String userId;
	public BorrowDetails(String userId)
	{
		super("Library Management System - Borrowed Books");
		
		this.setSize(800, 450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.userId=userId;
		
		jPanel1 = new JPanel();
		jPanel1.setLayout(null);
		
		Font f1 = new Font("Cambria", Font.BOLD, 15);
		
		list = new JTable();
		list.setBounds(90,120,610,200);
		jPanel1.add(list);
		
		borrowIdLabel = new JLabel("borrowId");
		borrowIdLabel.setBounds(90, 95, 153, 30);
		borrowIdLabel.setFont(f1);
		jPanel1.add(borrowIdLabel);
		
		bookIdLabel = new JLabel("bookId");
		bookIdLabel.setBounds(243, 95, 153, 30);
		bookIdLabel.setFont(f1);
		jPanel1.add(bookIdLabel);
		
		borrowDateLabel = new JLabel("borrowDate");
		borrowDateLabel.setBounds(396, 95, 153, 30);
		borrowDateLabel.setFont(f1);
		jPanel1.add(borrowDateLabel);
		
		returnDateLabel = new JLabel("returnDate");
		returnDateLabel.setBounds(549, 95, 153, 30);
		returnDateLabel.setFont(f1);
		jPanel1.add(returnDateLabel);
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(600, 20, 100, 30);
		logoutBtn.addActionListener(this);
		jPanel1.add(logoutBtn);
		
		backBtn = new JButton("Back");
		backBtn.setBounds(500, 350, 120, 30);
		backBtn.addActionListener(this);
		jPanel1.add(backBtn);
		
		this.add(jPanel1);
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
		else if(text.equals(backBtn.getText()))
		{
			CustomerHome ch = new CustomerHome(userId);
			ch.setVisible(true);
			this.setVisible(false);
		}
	}
	public void listed()
	{
		DefaultTableModel table = new DefaultTableModel();
		table.addColumn("borrowId");
		table.addColumn("bookId");
		table.addColumn("borrowDate");
		table.addColumn("returnDate");
		
		Connection con;
        Statement st;
		ResultSet rs;
		
		try
		{
			String loadId=userId;
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop1","root","");
			String sql ="SELECT borrowId,bookId,borrowDate,returnDate FROM `borrowinfo` WHERE userId='"+loadId+"'";
			st = con.createStatement();
			rs = st.executeQuery(sql);
			
			while(rs.next())
			{
				table.addRow(new Object[]
				{
					rs.getString(1),
					rs.getString(2),
					rs.getString(3),
					rs.getString(4),
				});
			}
			list.setModel(table);
		}
		catch(Exception e){}
	}
}