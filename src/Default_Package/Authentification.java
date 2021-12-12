package Default_Package;
import java.sql.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Panel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import java.awt.Label;
import javax.swing.JTextField;

import java.sql.Driver;
import com.mysql.jdbc.*;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;

public class Authentification extends JFrame {

	private JFrame frame;
	private JTextField usernameField;
	
	Connection conn = null ;
	PreparedStatement prepared= null;
	ResultSet rs = null ;
	private JPasswordField passwordField;
	public void fermer() {
		frame.dispose();
	}

     public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Authentification window = new Authentification();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Authentification() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 1208, 598);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		conn =ConnectionMysqlJava.ConnecterDb();
		
		
		usernameField = new JTextField();
		usernameField.setColumns(10);
		usernameField.setBounds(443, 254, 224, 35);
		frame.getContentPane().add(usernameField);
		
		JButton btnNewButton = new JButton("Se Connecter");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String username = usernameField.getText().toString();	
				String password = passwordField.getText().toString();
				
				String sql = "select username,password from users";
				try {
					prepared = conn.prepareStatement(sql);
					rs = prepared.executeQuery();
					
				int i=0;
				if(username.equals("") || password.equals("")) {
					JOptionPane.showMessageDialog(null, "Remplissez les champs vides !");
					
				}else {
				
					
					while(rs.next()) {
						String username1 = rs.getString("username");
						String password1 = rs.getString("password");
						if(username1.equals(username) && !password1.equals(password))
						{
							JOptionPane.showMessageDialog(null, "Mot de passe incorrect :( ");
						}
					if (username1.equals(username) && password1.equals(password)) {
							JOptionPane.showMessageDialog(null, "Connexion réussite :D");
							i = 1 ;
							MenuAdministrateur obj =new MenuAdministrateur();
							obj.setVisible(true);
							obj.setLocationRelativeTo(null);
							fermer();
							
						}
					
				
					}if(i==0) {
						
							JOptionPane.showMessageDialog(null, "Username introuvable :( ");
							}
					
						
					
				}
					
					}catch (SQLException es) {
					es.printStackTrace();					
				}
			}
		});
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnNewButton.setBounds(441, 368, 226, 33);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Username:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1.setBounds(339, 256, 94, 29);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Password:");
		lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(339, 315, 94, 29);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("Mot de passe oubli\u00E9 !");
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				IndicationPassword obj = new IndicationPassword();
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
				
			}
		});
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(547, 349, 133, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(442, 311, 225, 33);
		frame.getContentPane().add(passwordField);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\chleg\\OneDrive\\Bureau\\projet java\\image\\BACKGRAOUND.png"));
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel.setBounds(0, 0, 1192, 559);
		frame.getContentPane().add(lblNewLabel);
	}
}
