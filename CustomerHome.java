import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

public class CustomerHome extends JFrame implements ActionListener
{
	JLabel welcomeLabel,bookidlable,booktitlelable;
	JTextField serach;
	JButton searchBtn, changePasswordBtn, viewDetailsBtn, borrowBtn, bookInfoBtn, logoutBtn;
	JPanel panel;
	JTable list;
	String userId;
	
	public CustomerHome(String userId)
	{
		super("Library Management System - Customer Home");
		
		this.userId = userId;
		
		this.setSize(800, 450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Font f1 = new Font("Cambria",Font.BOLD,15);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		welcomeLabel = new JLabel("Welcome, "+userId);
		welcomeLabel.setBounds(350, 50, 150, 30);
		panel.add(welcomeLabel);
		
		bookidlable = new JLabel("bookId");
		bookidlable.setBounds(240, 155, 230, 30);
		bookidlable.setFont(f1);
		panel.add(bookidlable);
		
		booktitlelable = new JLabel("bookTitle");
		booktitlelable.setBounds(470, 155, 230, 30);
		booktitlelable.setFont(f1);
		panel.add(booktitlelable);
		
		serach = new JTextField();
		serach.setBounds(240, 100, 210, 30);
		panel.add(serach);
		
		searchBtn = new JButton("Search");
		searchBtn.setBounds(480, 100, 80, 30);
		searchBtn.addActionListener(this);
		panel.add(searchBtn);
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(600, 50, 100, 30);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		viewDetailsBtn = new JButton("My Information");
		viewDetailsBtn.setBounds(50, 130, 150, 30);
		viewDetailsBtn.addActionListener(this);
		panel.add(viewDetailsBtn);
		
		changePasswordBtn = new JButton("Change Password");
		changePasswordBtn.setBounds(50, 180, 150, 30);
		changePasswordBtn.addActionListener(this);
		panel.add(changePasswordBtn);
		
		bookInfoBtn = new JButton("Book Information");
		bookInfoBtn.setBounds(50, 230, 150, 30);
		bookInfoBtn.addActionListener(this);
		panel.add(bookInfoBtn);
		
		borrowBtn = new JButton("Borrowed Books");
		borrowBtn.setBounds(50, 280, 150, 30);
		borrowBtn.addActionListener(this);
		panel.add(borrowBtn);
		
		list =new JTable();
		list.setBounds(240,180,460,180);
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
		else if(text.equals(changePasswordBtn.getText()))
		{
			CustomerChangePassword ccp = new CustomerChangePassword(userId);
			ccp.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(viewDetailsBtn.getText()))
		{
			CustomerInfo ci = new CustomerInfo(userId);
			ci.setVisible(true);
			this.setVisible(false);
			ci.loadFromDB();
		}
		else if(text.equals(bookInfoBtn.getText()))
		{
			CustomerViewBooks cvb = new CustomerViewBooks(userId);
			cvb.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(borrowBtn.getText()))
		{
			BorrowDetails bd = new BorrowDetails(userId);
			bd.setVisible(true);
			this.setVisible(false);
			bd.listed();
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
				
				//if(bt.equals(serach.getText()))
				//{
						table.addRow(new Object[]
					{
						rs.getString(1),
						rs.getString(2),
					});
					//list.setModel(table);
				/*}
				else
				{
					JOptionPane.showMessageDialog(this, "The Book is not FOUND");
				}*/
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
