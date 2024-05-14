import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.*;

public class EmployeeViewBooks extends JFrame implements ActionListener
{
	JLabel bookName, bookId, publicationYear, authorName, count;
	JTextField bookNameTF, authorNameTF, countTF, bookIdTF, publicationYearTF;
	JButton update, back, logout,delete,refresh,load;
	JPanel panel;
	String userId;
	
	public EmployeeViewBooks(String userId)
	{
		super("Library Management System - View Books(Employee)");
		
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
		
		update = new JButton("Update");
		update.setBounds(200, 390, 90, 30);
		update.setEnabled(false);
		update.addActionListener(this);
		panel.add(update);
		
		back = new JButton("Back");
		back.setBounds(600, 390, 80, 30);
		back.addActionListener(this);
		panel.add(back);
		
		delete = new JButton("delete");
		delete.setBounds(400, 390, 80, 30);
		delete.setEnabled(false);
		delete.addActionListener(this);
		panel.add(delete);
		
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
		
		if(text.equals(back.getText()))
		{
			EmployeeHome eh = new EmployeeHome(userId);
			eh.setVisible(true);
			this.setVisible(false);
		}
		if(text.equals(refresh.getText()))
		{
			update.setEnabled(false);
			delete.setEnabled(false);
			bookIdTF.setEnabled(true);
			bookIdTF.setText("");
			bookNameTF.setText("");
			authorNameTF.setText("");
			publicationYearTF.setText("");
			countTF.setText("");
		}
		else if(text.equals(logout.getText()))
		{
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(load.getText()))
		{
			loadFromDB();			
		}
		else if(text.equals(update.getText()))
		{
			updateInDB();
		}
		else if(text.equals(delete.getText()))
		{
			deleteFromDB();
		}
		else{}
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
				update.setEnabled(true);
				delete.setEnabled(true);
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
	public void updateInDB()
	{
		String bId = bookIdTF.getText();
		String bname = bookNameTF.getText();
		String aName = authorNameTF.getText();
		int pubyear =Integer.parseInt( publicationYearTF.getText());
        int noOfQuqntity = Integer.parseInt(countTF.getText());
		
		String query = "UPDATE book SET bookTitle='"+bname+"', authorName = '"+aName+"', publicationYear = "+pubyear+", availableQuantity = "+noOfQuqntity+" WHERE bookId='"+bId+"'";	
        Connection con=null;//for connection
        Statement st = null;//for query execution
		System.out.println(query);
        try
		{
			Class.forName("com.mysql.jdbc.Driver");//load driver
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop1","root","");
			st = con.createStatement();//create statement
			st.executeUpdate(query);
			st.close();
			con.close();
			JOptionPane.showMessageDialog(this, "Success !!!");
			
			update.setEnabled(false);
			delete.setEnabled(false);
			bookIdTF.setEnabled(true);
			bookIdTF.setText("");
			bookNameTF.setText("");
			authorNameTF.setText("");
			publicationYearTF.setText("");
			countTF.setText("");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(this, "Oops !!!");
		}
	}
	public void deleteFromDB()
	{
		String bId = bookIdTF.getText();
		String query = "DELETE from book WHERE bookId='"+bId+"';";
		System.out.println(query);
        try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop1", "root", "");
			Statement stm = con.createStatement();
			stm.execute(query);
			stm.close();
			con.close();
			JOptionPane.showMessageDialog(this, "Success !!!");
			
			update.setEnabled(false);
			delete.setEnabled(false);
			bookIdTF.setEnabled(true);
			bookIdTF.setText("");
			bookNameTF.setText("");
			authorNameTF.setText("");
			publicationYearTF.setText("");
			countTF.setText("");
		}
        catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Oops !!!");
        }
	}
	
}