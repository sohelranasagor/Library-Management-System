import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.*;
import java.util.*;

public class AddBook extends JFrame implements ActionListener
{
	JLabel title, bookName, bookId, publicationYear, authorName, count;
	JTextField bookNameTF, authorNameTF, countTF, bookIdTF, publicationYearTF;
	JButton addBook, back, logout, auto;
	JPanel panel;
	String userId;
	
	public AddBook(String userId)
	{
		super("Library Management System - Add New Books");
		
		this.userId = userId;
		this.setSize(800, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Font f1 = new Font("Cambria", Font.BOLD, 25);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		bookId = new JLabel("BOOK ID");
		bookId.setBounds(200, 90, 120, 30);
		panel.add(bookId);
		
		bookName = new JLabel("BOOK NAME");
		bookName.setBounds(200, 140, 120, 30);
		panel.add(bookName);
		
		authorName = new JLabel("AUTHOR NAME");
		authorName.setBounds(200, 190, 120, 30);
		panel.add(authorName);
		
		publicationYear = new JLabel("PUBLICATION YEAR");
		publicationYear.setBounds(200, 240, 120, 30);
		panel.add(publicationYear);
		
		count = new JLabel("NO. of BOOKS");
		count.setBounds(200, 290, 120, 30);
		panel.add(count);
		
		auto = new JButton("Auto Generate");
		auto.setBounds(600, 90, 150, 30);
		auto.addActionListener(this);
		panel.add(auto);
		
		bookIdTF = new JTextField();
		bookIdTF.setBounds(350, 90, 200, 30);
		panel.add(bookIdTF);
		
		bookNameTF = new JTextField();
		bookNameTF.setBounds(350, 140, 200, 30);
		panel.add(bookNameTF);
		
		authorNameTF = new JTextField();
		authorNameTF.setBounds(350, 190, 200, 30);
		panel.add(authorNameTF);
		
		publicationYearTF = new JTextField();
		publicationYearTF.setBounds(350, 240, 200, 30);
		panel.add(publicationYearTF);
		
		countTF = new JTextField();
		countTF.setBounds(350, 290, 200, 30);
		panel.add(countTF);
		
		addBook = new JButton("Add Book");
		addBook.setBounds(450, 340, 100, 30);
		addBook.addActionListener(this);
		panel.add(addBook);
		
		back = new JButton("Back");
		back.setBounds(200, 340, 80, 30);
		back.addActionListener(this);
		panel.add(back);
		
		logout = new JButton("Logout");
		logout.setBounds(680, 30, 80, 30);
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
			EmployeeHome eh = new EmployeeHome(userId);
			eh.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(auto.getText()))
		{
			Random r = new Random();
			bookIdTF.setText(r.nextInt(89999)+10000+"");
			bookIdTF.setEnabled(false);
		}
		else if(text.equals(addBook.getText()))
		{
			addbooks();
		}
	}
	public void addbooks()
	{
		String bId = bookIdTF.getText();
		String bname = bookNameTF.getText();
		String aName = authorNameTF.getText();
		int pubyear =Integer.parseInt( publicationYearTF.getText());
        int noOfBook = Integer.parseInt(countTF.getText());
		
		String query = "INSERT INTO Book VALUES ('"+bId+"','"+bname+"','"+ aName+"',"+pubyear+","+noOfBook+");";
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
			bookIdTF.setEnabled(true);
			bookNameTF.setText("");
			authorNameTF.setText("");
			countTF.setText("");
			bookIdTF.setText("");
			publicationYearTF.setText("");
		}
        catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Oops !!!");
        }
	}
}