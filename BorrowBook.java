import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.*;
import java.util.*;

public class BorrowBook extends JFrame implements ActionListener

{
	private JLabel borrowID, bookid, userid,borrowdate,returndate;
    private JTextField borrowidTF;
    private JTextField bookidTF;
    private JTextField useridTF;
    private JTextField borrowdateTF;
    private JTextField returndateTF;
    private JButton btnChkavaliablity,btnConfirmBorrowing,btnLogout, btnBack,auto;
    private JPanel panel;
    String userId;
 
 
 public BorrowBook(String userId)
 {
	 super("Library Management System - Informations Of Borrowed Book");
		
		this.userId = userId;
		this.setSize(800, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		borrowID = new JLabel("Borrow ID ");
		borrowID.setBounds(200, 60, 80, 20);
		panel.add(borrowID);
		
		bookid= new JLabel("Book ID ");
		bookid.setBounds(200, 130, 80, 20);
		panel.add(bookid);
		
		userid = new JLabel("User ID");
		userid.setBounds(200, 190, 100, 30);
		panel.add(userid);
		
		borrowdate = new JLabel("Borrow Date");
		borrowdate.setBounds(200, 250, 100, 30);
		panel.add(borrowdate);
		
		returndate = new JLabel("Return Date");
		returndate.setBounds(200, 300, 100, 30);
		panel.add(returndate);
		
		auto = new JButton("Auto Generate");
		auto.setBounds(540, 60, 150, 30);
		auto.addActionListener(this);
		panel.add(auto);
		
		btnConfirmBorrowing = new JButton("Confirm Borrowing");
		btnConfirmBorrowing.setBounds(540, 340, 150, 30);
		btnConfirmBorrowing.addActionListener(this);
		btnConfirmBorrowing.setEnabled(false);
		panel.add(btnConfirmBorrowing);
		
		btnChkavaliablity = new JButton("Check Avialiblity");
		btnChkavaliablity.setBounds(540, 130, 150, 30);
		btnChkavaliablity.addActionListener(this);
		panel.add(btnChkavaliablity);
		
		btnBack = new JButton("Back");
		btnBack.setBounds(120, 340, 80, 30);
		btnBack.addActionListener(this);
		panel.add(btnBack);
		
		btnLogout = new JButton("Logout");
		btnLogout.setBounds(680, 20, 80, 30);
		btnLogout.addActionListener(this);
		panel.add(btnLogout);
		
		borrowidTF = new JTextField();
		borrowidTF.setBounds(280, 60, 200, 30);
		panel.add(borrowidTF);
		
		bookidTF = new JTextField();
		bookidTF.setBounds(280, 130, 200, 30);
		panel.add(bookidTF);
		
		useridTF = new JTextField();
		useridTF.setBounds(280, 190, 200, 30);
		panel.add(useridTF);
		
		borrowdateTF = new JTextField();
		borrowdateTF.setBounds(280, 250, 200, 30);
		panel.add(borrowdateTF);
		
		returndateTF = new JTextField();
		returndateTF.setBounds(280, 300, 200, 30);
		panel.add(returndateTF);
		
		this.add(panel);

	}
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(btnLogout.getText()))
		{
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(btnBack.getText()))
		{
			EmployeeHome eh = new EmployeeHome(userId);
			eh.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(auto.getText()))
		{
			Random r = new Random();
			borrowidTF.setText(r.nextInt(8999)+1000+"");
			borrowidTF.setEnabled(false);
		}
		else if(text.equals(btnConfirmBorrowing.getText()))
		{
			confirmBorrow();
		}
		else if(text.equals(btnChkavaliablity.getText()))
		{
			checkAvailibility();
		}
		
	}
	public void confirmBorrow()
	{
		String borrowID = borrowidTF.getText();
		String bookid = bookidTF.getText();
		String userid = useridTF.getText();
		String borrowdate = borrowdateTF.getText();
		String returndate = returndateTF.getText();
		
		
		String query = "INSERT INTO BorrowInfo VALUES ('"+borrowID+"','"+bookid+"','"+ userid+"','"+borrowdate+"','"+returndate+"');";
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
			updateAvailableQuantity();
			borrowidTF.setEnabled(true);
			borrowidTF.setText("");
			bookidTF.setText("");
			useridTF.setText("");
			borrowdateTF.setText("");
			borrowdateTF.setText("");
			returndateTF.setText("");
		}
        catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Oops !!!");
        }
	}
	public void updateAvailableQuantity()
	{
		String bookId = bookidTF.getText();
		String query = "SELECT `bookId`, `bookTitle`, `authorName`, `publicationYear`, `availableQuantity` FROM `book` WHERE `bookId`='"+bookId+"';";
		Connection con = null;//for connection
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
				int newquantity = noOfQuqntity-1;
				flag = true;
				
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
			
	public void checkAvailibility()
	{
		String loadId = bookidTF.getText();
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
						
			while(rs.next())
			{
				int noOfQuqntity = rs.getInt("availableQuantity");
				flag=true;
				
				if(noOfQuqntity==0)
				{
					JOptionPane.showMessageDialog(this, "No");
					useridTF.setEnabled(false);
					borrowdateTF.setEnabled(false);
					returndateTF.setEnabled(false);
				}
				else if(noOfQuqntity!=0)
				{
					JOptionPane.showMessageDialog(this, "Yes");
					useridTF.setEnabled(true);
					borrowdateTF.setEnabled(true);
					returndateTF.setEnabled(true);
					btnConfirmBorrowing.setEnabled(true);
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
