import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
public class CreateNewAccount extends JFrame implements ActionListener
{
	JLabel userLabel, passLabel, cNameLabel, phoneLabel, addressLabel;
	JTextField userTF, passTF, phoneTF1, phoneTF2, cNameTF, addressTF;
	JButton createBtn, backBtn;
	JPanel panel;
	
	String userId;
	
	CreateNewAccount(String userId)
	{
		super("Library Management System - Create New Account");
		
		this.setSize(800, 450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.userId = userId;
		
		panel = new JPanel();
		panel.setLayout(null);
		
		userLabel = new JLabel("User ID : ");
		userLabel.setBounds(250, 100, 120, 30);
		panel.add(userLabel);
		
		userTF = new JTextField();
		userTF.setBounds(400, 100, 120, 30);
		panel.add(userTF);
		
		passLabel = new JLabel("Password : ");
		passLabel.setBounds(250, 150, 120, 30);
		panel.add(passLabel);
		
		passTF = new JTextField();
		passTF.setBounds(400, 150, 120, 30);
		//passTF.setEnabled(false);
		panel.add(passTF);
		
		cNameLabel = new JLabel("Customer Name : ");
		cNameLabel.setBounds(250, 200, 120, 30);
		panel.add(cNameLabel);
		
		cNameTF = new JTextField();
		cNameTF.setBounds(400, 200, 120, 30);
		panel.add(cNameTF);
		
		phoneLabel = new JLabel("Phone No. : ");
		phoneLabel.setBounds(250, 250, 120, 30);
		panel.add(phoneLabel);
		
		phoneTF1 = new JTextField("+880");
		phoneTF1.setBounds(400, 250, 35, 30);
		phoneTF1.setEnabled(false);
		phoneTF1.setForeground(Color.BLACK);
		panel.add(phoneTF1);
		
		phoneTF2 = new JTextField();
		phoneTF2.setBounds(435, 250, 85, 30);
		panel.add(phoneTF2);
		
		addressLabel = new JLabel("Address : ");
		addressLabel.setBounds(250, 300, 120, 30);
		panel.add(addressLabel);
		
		addressTF = new JTextField();
		addressTF.setBounds(400, 300, 120, 30);
		panel.add(addressTF);
		
		createBtn = new JButton("Create");
		createBtn.setBounds(250, 350, 120, 30);
		createBtn.addActionListener(this);
		panel.add(createBtn);
		
		backBtn = new JButton("Back");
		backBtn.setBounds(400, 350, 120, 30);
		backBtn.addActionListener(this);
		panel.add(backBtn);
		
		this.add(panel);
	}
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		if(text.equals(backBtn.getText()))
		{
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(createBtn.getText()))
		{
			insertIntoDB();
		}
	}
	public void insertIntoDB()
	{
		String newId = userTF.getText();
		String newPass = passTF.getText();
		String cName = cNameTF.getText();
		String phnNo = phoneTF1.getText()+phoneTF2.getText();
		String address = addressTF.getText();
		int status = 1;
		
		
		String query1 = "INSERT INTO Customer VALUES ('"+newId+"','"+cName+"','"+ phnNo+"','"+address+"');";
		String query2 = "INSERT INTO Login VALUES ('"+newId+"','"+newPass+"',"+status+");";
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
		}
        catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Oops !!!");
        }
    }	
}