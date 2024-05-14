import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.*;

public class EmployeeChangePassword extends JFrame implements ActionListener

{
	private JLabel Oldpass, Newpass;
	private JPasswordField OldpassTF, NewPassTF;
	private JButton Changebtn, Backbtn,Logoutbtn;
	private JPanel panel;
	
	String userId;
	
	public EmployeeChangePassword(String userId)
	{
	    super("Library Management System - Change Password(For Employee)");
	
		this.setSize(800, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.userId = userId;
		
		panel = new JPanel();
		panel.setLayout(null);
		
		
		Oldpass = new JLabel("Old Password");
		Oldpass.setBounds(150, 100, 80, 30);		
		panel.add(Oldpass);
		panel.add(Oldpass);
		
		Newpass = new JLabel("New Password");
		Newpass.setBounds(140, 170, 90, 30);		
		panel.add(Newpass);
		panel.add(Newpass);
		
		OldpassTF = new JPasswordField();
		OldpassTF.setBounds(251, 100, 120, 30);
		panel.add(OldpassTF);
		
		
		NewPassTF = new JPasswordField();
		NewPassTF.setBounds(251, 170, 120, 30);
		panel.add(NewPassTF);
		
		Changebtn = new JButton("Change");
		Changebtn.setBounds(200, 250, 80, 30);
		Changebtn.addActionListener(this);
		panel.add(Changebtn);
		
		Backbtn = new JButton("Back");
		Backbtn.setBounds(330, 250, 75, 30);
		Backbtn.addActionListener(this);
		panel.add(Backbtn);
		
		Logoutbtn = new JButton("Logout");
		Logoutbtn.setBounds(500, 50, 100, 30);
		Logoutbtn.addActionListener(this);
		panel.add(Logoutbtn);
		
		this.add(panel);
		
	}
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(Logoutbtn.getText()))
		{
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(Backbtn.getText()))
		{
			EmployeeHome eh = new EmployeeHome(userId);
			eh.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(Changebtn.getText()))
		{
			changepass();
		}
	}
	public void changepass()
	{
		String loadId=userId;
		String query = "SELECT `userId`, `password`, `status` FROM `login` WHERE `userId`='"+loadId+"';";  
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
				String loadId1=userId;
				String pass = rs.getString("password");
				flag=true;
				if(pass.equals(OldpassTF.getText()))
				{
					String loadId2=userId;
					String newpass=NewPassTF.getText();
					flag=true;
				
					String query1 = "UPDATE login SET password='"+newpass+"' WHERE userId='"+loadId2+"'";	
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
						JOptionPane.showMessageDialog(this, "Success !!!");
			
		
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
						JOptionPane.showMessageDialog(this, "Oops !!!");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(this, "Old Password Incorrect");
					OldpassTF.setText("");
					NewPassTF.setText("");
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