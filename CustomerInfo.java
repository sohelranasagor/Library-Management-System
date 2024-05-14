import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class CustomerInfo extends JFrame implements ActionListener
{
	JLabel userLabel, cNameLabel, phoneLabel,addressLabel ;
	JTextField userTF, phoneTF1,phoneTF2, cNameTF,addressTF  ;
	JButton  updateBtn,  backBtn, logoutBtn,deleteBtn;
	JPanel panel;
	
	String userId;
	
	public CustomerInfo(String userId)
	{
		super("Library Management System - Customer Information");
		
		this.setSize(800, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.userId = userId;
		
		panel = new JPanel();
		panel.setLayout(null);
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(600, 50, 100, 30);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		userLabel = new JLabel("User ID : ");
		userLabel.setBounds(250, 150, 120, 30);
		panel.add(userLabel);
		
		userTF = new JTextField();
		userTF.setBounds(400, 150, 120, 30);
		panel.add(userTF);
		
		cNameLabel = new JLabel("Customer Name : ");
		cNameLabel.setBounds(250, 200, 120, 30);
		panel.add(cNameLabel);
		
		cNameTF = new JTextField();
		cNameTF.setBounds(400, 200, 120, 30);
		panel.add(cNameTF);
		
		phoneLabel = new JLabel("Phone No. : ");
		phoneLabel.setBounds(250, 250, 120, 30);
		panel.add(phoneLabel);
		
		phoneTF1 = new JTextField();
		phoneTF1.setBounds(400, 250, 35, 30);
		phoneTF1.setEnabled(false);
		panel.add(phoneTF1);
		
		phoneTF2 = new JTextField();
		phoneTF2.setBounds(432, 250, 87, 30);
		panel.add(phoneTF2);
		
		addressLabel = new JLabel("Address : ");
		addressLabel.setBounds(250, 300, 120, 30);
		panel.add(addressLabel);
		
		addressTF = new JTextField();
		addressTF.setBounds(400, 300, 120, 30);
		panel.add(addressTF);
		
		updateBtn = new JButton("Update");
		updateBtn.setBounds(200, 400, 120, 30);
		updateBtn.addActionListener(this);
		panel.add(updateBtn);
		
		deleteBtn = new JButton("Delete");
		deleteBtn.setBounds(350, 400, 120, 30);
		deleteBtn.addActionListener(this);
		panel.add(deleteBtn);
		
		backBtn = new JButton("Back");
		backBtn.setBounds(500, 400, 120, 30);
		backBtn.addActionListener(this);
		panel.add(backBtn);
		
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
		else if(text.equals(backBtn.getText()))
		{
			CustomerHome ch = new CustomerHome(userId);
			ch.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(updateBtn.getText()))
		{
			updateInDB();
		}
		else if(text.equals(deleteBtn.getText()))
		{
			deleteFromDB();
		}
	}
	public void loadFromDB()
	{
		String query = "SELECT `userId`, `customerName`, `phoneNumber`, `address` FROM `customer` WHERE `userId`='"+userId+"';";
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		System.out.println(query);
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("drive loaded");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop1","root","");
			System.out.println("connection done");
			st = con.createStatement();
			System.out.println("statement created");
			rs = st.executeQuery(query);
			System.out.println("results received");
			
			boolean flag=false;
			String userId = null;
			String cName = null;
			String phnNo = null;
			String address = null;
			while(rs.next())
			{
				userId = rs.getString("userId");
				cName = rs.getString("customerName");
				phnNo = rs.getString("phoneNumber");
				address = rs.getString("address");
				flag=true;
				
				userTF.setText(userId);
				userTF.setEnabled(false);
				cNameTF.setText(cName);
				phoneTF1.setText("+880");
				phoneTF2.setText(phnNo.substring(4,14));
				addressTF.setText(address);
				updateBtn.setEnabled(true);
				
			}
		}
			catch(Exception ex)
			{
				System.out.println("Exception : "+ex.getMessage());
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
		String newId = userTF.getText();
		String cName = cNameTF.getText();
		String phnNo = phoneTF1.getText()+phoneTF2.getText();
		String address = addressTF.getText();
		
		String query = "UPDATE customer SET customerName='"+cName+"', phoneNumber = '"+phnNo+"', address = '"+address+"' WHERE userId='"+newId+"'";	
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
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(this, "Oops !!!");
		}
	}
	public void deleteFromDB()
	{
		String newId = userTF.getText();
		String query1 = "DELETE from customer WHERE userId='"+newId+"';";
		String query2 = "DELETE from login WHERE userId='"+newId+"';";
		System.out.println(query1);
		System.out.println(query2);
        try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop1", "root", "");
			Statement stm = con.createStatement();
			stm.execute(query1);
			stm.execute(query2);
			stm.close();
			con.close();
			JOptionPane.showMessageDialog(this, "Success !!!");
			
			updateBtn.setEnabled(false);
			deleteBtn.setEnabled(false);
			userTF.setEnabled(true);
			userTF.setText("");
			cNameTF.setText("");
			phoneTF1.setText("");
			phoneTF2.setText("");
			addressTF.setText("");
		}
        catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Oops !!!");
        }
	}
}
	