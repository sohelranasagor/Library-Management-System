import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
public class returnBook extends JFrame implements ActionListener
{
	private JLabel info,bookId,borrowId;
	private JTextField bId,brwId;
	private JButton returnBook,logOut,back;
	private JPanel panel;
	
	String userId;
	
	public returnBook(String userId)
	{
		super("Library Management System - Return Book");
		
		this.setSize(800, 450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.userId = userId;
		
		panel = new JPanel();
		panel.setLayout(null);
		
		Font f1 = new Font("Cambria", Font.BOLD, 25);
		
		info = new JLabel("Return Book");
		info.setBounds(275, 30, 150, 30);
		info.setFont(f1);
		panel.add(info);
		
		borrowId = new JLabel("Borrow ID");
		borrowId.setBounds(200,100,120,30);
		panel.add(borrowId);
		
		brwId = new JTextField();
		brwId.setBounds(400,100,120,30);
		panel.add(brwId);
		
		bookId = new JLabel("Book ID");
		bookId.setBounds(200,150,120,30);
		panel.add(bookId);
		
		bId = new JTextField();
		bId.setBounds(400,150,120,30);
		panel.add(bId);
		
		returnBook = new JButton("Return Book");
		returnBook.setBounds(400,300,120,30);
		returnBook.addActionListener(this);
		panel.add(returnBook);
		
		back = new JButton("Back");
		back.setBounds(200,300,80,30);
		back.addActionListener(this);
		panel.add(back);
		
		logOut = new JButton("LogOut");
		logOut.setBounds(600,50,80,30);
		logOut.addActionListener(this);
		panel.add(logOut);
		
		this.add(panel);
	}
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(logOut.getText()))
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
		else if(text.equals(returnBook.getText()))
		{
			returnBook();
		}
	}
	public void returnBook()
	{
		String brId=brwId.getText();
		String bkId=bId.getText();
		String query = "SELECT `borrowId`, `bookId`, `userId`, `borrowDate`, `returnDate` FROM `borrowInfo` WHERE borrowId='"+brId+"';"; 
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
				//String loadId1=userId;
				String brrowId = rs.getString("borrowId");
				String bkkId = rs.getString("bookId");
				String usId = rs.getString("userId");
				flag=true;
				if(brrowId.equals(brwId.getText()) && bkkId.equals(bId.getText()))
				{
					String newId = brwId.getText();
					String query2 = "DELETE from borrowInfo WHERE borrowId='"+newId+"';";
					System.out.println(query2);
					try
					{
						Class.forName("com.mysql.jdbc.Driver");
						Connection cont = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop1", "root", "");
						Statement stm = cont.createStatement();
						stm.execute(query2);
						stm.close();
						cont.close();
						JOptionPane.showMessageDialog(this, "Success For "+usId);
						updateAvailableQuantity();
						bId.setText("");
						brwId.setText("");
					}
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(this, "Oops !!!");
					}
				}
				else
				{
					flag=false;
					JOptionPane.showMessageDialog(this, "Borrow ID or Book ID doesn't Match");
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
	public void updateAvailableQuantity()
	{
		String bookId = bId.getText();
		String query = "SELECT `bookId`, `bookTitle`, `authorName`, `publicationYear`, `availableQuantity` FROM `book` WHERE `bookId`='"+bookId+"';";
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
			int noOfQuqntity = 0;			
			while(rs.next())
			{
				noOfQuqntity = rs.getInt("availableQuantity");
				int newquantity =noOfQuqntity+1;
				flag=true;
				
				String query1 = "UPDATE book SET availableQuantity="+newquantity+" WHERE bookId='"+bookId+"'";	
				Connection cont=null;//for connection
				Statement stm = null;//for query execution
				System.out.println(query1);
				try
				{
					Class.forName("com.mysql.jdbc.Driver");//load driver
					cont = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop1","root","");
					stm = con.createStatement();//create statement
					stm.executeUpdate(query1);
					stm.close();
					cont.close();
					//JOptionPane.showMessageDialog(this, "Success !!!");
			
		
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
					JOptionPane.showMessageDialog(this, "Oops !!!");
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