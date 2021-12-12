package Default_Package;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IndicationPassword extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	Connection conn = null ;
	PreparedStatement prepared= null;
	ResultSet rs = null ;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IndicationPassword frame = new IndicationPassword();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public IndicationPassword() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 584, 301);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		conn =ConnectionMysqlJava.ConnecterDb();
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(72, 72, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(" Username:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblNewLabel_1.setBounds(82, 64, 81, 27);
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String username = textField.getText().toString();
				String sql = "select password from users where username = ?";
				try {
					prepared = conn.prepareStatement(sql) ;
					prepared.setString(1,username);
					rs = prepared.executeQuery();
					if(rs.next()) {
						String pass = rs.getString("password");
						String pass1 = pass.substring(0,3);
						
						textField_1.setText("les 3 premieres lettres du mot de pass"+" "+":" +" "+ pass1 );
					}
					
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		textField.setBounds(149, 59, 125, 36);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {				
			}
		});
		textField_1.setEditable(false);
		textField_1.setBounds(149, 111, 392, 36);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
	}
}
