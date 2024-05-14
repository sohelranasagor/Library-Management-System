import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.*;

public class CustomerViewBooks extends JFrame implements ActionListener
{
	JLabel bookName, bookId, publicationYear, authorName, count;
	JTextField bookNameTF, authorNameTF, countTF, bookIdTF, publicationYearTF;
	JButton  back, logout,refresh,load;
	JPanel panel;
	String userId;
	
	public CustomerViewBooks(String userId)
	{
		super("Library Management System - View Books(Customer)");
		
		this.userId = userId;
		this.setSize(800, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Font f1 = new Font("Cambria", Font.BOLD, 25);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		bookId = new JLabel("BOOK ID");
		bookId.setBounds(200, 140, 120, 30);
		panel.add(bookId);
		
		bookName = new JLabel("BOOK NAME");
		bookName.setBounds(200, 190, 120, 30);
		panel.add(bookName);
		
		authorName = new JLabel("AUTHOR NAME");
		authorName.setBounds(200, 240, 120, 30);
		panel.add(authorName);
		
		publicationYear = new JLabel("PUBLICATION YEAR");
		publicationYear.setBounds(200, 290, 120, 30);
		panel.add(publicationYear);
		
		count = new JLabel("NO. of BOOKS");
		count.setBounds(200, 340, 120, 30);
		panel.add(count);
		
		bookIdTF = new JTextField();
		bookIdTF.setBounds(350, 140, 200, 30);
		panel.add(bookIdTF);
		
		bookNameTF = new JTextField();
		bookNameTF.setBounds(350, 190, 200, 30);
		panel.add(bookNameTF);
		
		authorNameTF = new JTextField();
		authorNameTF.setBounds(350, 240, 200, 30);
		panel.add(authorNameTF);
		
		publicationYearTF = new JTextField();
		publicationYearTF.setBounds(350, 290, 200, 30);
		panel.add(publicationYearTF);
		
		countTF = new JTextField();
		countTF.setBounds(350, 340, 200, 30);
		panel.add(countTF);
		
		back = new JButton("Back");
		back.setBounds(400, 390, 80, 30);
		back.addActionListener(this);
		panel.add(back);
		
		refresh = new JButton("Refresh");
		refresh.setBounds(350, 90, 200, 30);
		refresh.addActionListener(this);
		panel.add(refresh);
		
		load = new JButton("Load");
		load.setBounds(570, 140, 80, 30);
		load.addActionListener(this);
		panel.add(load);
		
		logout = new JButton("Logout");
		logout.setBounds(600, 50, 80, 30);
		logout.addActionListener(this);
		panel.add(logout);
		
		this.add(panel);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(logout.getText()))
		{
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(back.getText()))
		{
			CustomerHome ch = new CustomerHome(userId);
			ch.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(refresh.getText()))
		{
			bookIdTF.setEnabled(true);
			bookIdTF.setText("");
			bookNameTF.setText("");
			authorNameTF.setText("");
			publicationYearTF.setText("");
			countTF.setText("");
		}
		
		else if(text.equals(load.getText()))
		{
			loadFromDB();			
		}
		else {}
	}
	public void loadFromDB()
	{
		String loadId = bookIdTF.getText();
		String query = "SELECT `bookId`, `bookTitle`, `authorName`, `publicationYear`, `availableQuantity` FROM `book` WHERE `bookId`='"+loadId+"';";     
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
			String bName = null;
			String aName = null;
			int pubYear = 0;
			int noOfQuqntity = 0;			
			while(rs.next())
			{
                bName = rs.getString("bookTitle");
				aName = rs.getString("authorName");
				pubYear = rs.getInt("publicationYear");
				noOfQuqntity = rs.getInt("availableQuantity");
				flag=true;
				
				bookNameTF.setText(bName);
				authorNameTF.setText(aName);
				publicationYearTF.setText(String.valueOf(pubYear));
				countTF.setText(String.valueOf(noOfQuqntity));
				bookIdTF.setEnabled(false);
				
			}
			if(!flag)
			{
				bookNameTF.setText("");
				authorNameTF.setText("");
				publicationYearTF.setText("");
				countTF.setText("");
				JOptionPane.showMessageDialog(this,"Invalid ID"); 
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