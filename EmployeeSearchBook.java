import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

public class EmployeeSearchBook extends JFrame implements ActionListener
{
	JLabel bookidlable,booktitlelable;
	JTextField serach;
	JButton searchBtn,logoutBtn,back;
	JPanel panel;
	JTable list;
	String userId;
	
	public EmployeeSearchBook(String userId)
	{
		super("Library Management System - Search Book(Employee)");
		
		this.userId = userId;
		this.setSize(800, 450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Font f1 = new Font("Cambria",Font.BOLD,15);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		searchBtn = new JButton("Search");
		searchBtn.setBounds(480, 70, 80, 30);
		searchBtn.addActionListener(this);
		panel.add(searchBtn);
		
		serach = new JTextField();
		serach.setBounds(240, 70, 210, 30);
		panel.add(serach);
		
		bookidlable = new JLabel("bookId");
		bookidlable.setBounds(101, 125, 230, 30);
		bookidlable.setFont(f1);
		panel.add(bookidlable);
		
		booktitlelable = new JLabel("bookTitle");
		booktitlelable.setBounds(380, 125, 230, 30);
		booktitlelable.setFont(f1);
		panel.add(booktitlelable);
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(600, 20, 100, 30);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		back = new JButton("Back");
		back.setBounds(600,370,80,30);
		back.addActionListener(this);
		panel.add(back);
		
		list =new JTable();
		list.setBounds(100,150,560,180);
		panel.add(list);
		
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
		else if(text.equals(back.getText()))
		{
			EmployeeHome eh = new EmployeeHome(userId);
			eh.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(searchBtn.getText()))
		{
			list();
		}
		else{}
	}
	public void list()
	{
		DefaultTableModel table = new DefaultTableModel();
		table.addColumn("bookId");
		table.addColumn("bookName");
		
		Connection con = null;
        Statement st = null;
		ResultSet rs = null;
		
		try
		{
			String loadId=serach.getText();
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop1","root","");
			String sql ="SELECT bookId,bookTitle FROM `book` WHERE bookTitle like '"+"%"+loadId+"%"+"%'";
			st = con.createStatement();
			rs = st.executeQuery(sql);
			
			boolean flag=false;
			while(rs.next())
			{
				String bt = rs.getString("bookTitle");
				flag=true;
				
					table.addRow(new Object[]
					{
						rs.getString(1),
						rs.getString(2),
					});
			}
			list.setModel(table);
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