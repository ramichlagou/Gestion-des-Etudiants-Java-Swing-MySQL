package Default_Package;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import java.awt.Button;


public class Gestionutilisateur extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	String userold =null;
	/* variables de manipulation de la base de données*/
	Connection conn = null ;
	PreparedStatement prepared= null;
	ResultSet rs = null ;
	private JTable table;
	
	public void fermer() {
		dispose();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gestionutilisateur frame = new Gestionutilisateur();
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
	public Gestionutilisateur() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1208, 598);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		conn =ConnectionMysqlJava.ConnecterDb();
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(180, 277, 187, 39);
		contentPane.add(textField);
		
		JLabel lblPassword = new JLabel("password :");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPassword.setBounds(90, 288, 108, 14);
		contentPane.add(lblPassword);
		
		JLabel lblNewLabel_1 = new JLabel("username :");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(90, 238, 105, 14);
		contentPane.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
//				String sql = "select password from users where username=?";
//				try {
//					prepared =conn.prepareStatement(sql);
//					prepared.setString(1,textField_1.getText().toString());
//					rs =prepared.executeQuery();
//					if(rs.next()) {
//						String password = rs.getString("password");
//						textField.setText(password);
//					}
//			
//					
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			
				
			}
		});
		textField_1.setColumns(10);
		textField_1.setBounds(180, 227, 187, 39);
		contentPane.add(textField_1);
		
		JButton btnNewButton = new JButton("Ajouter");
		btnNewButton.setIcon(new ImageIcon(Gestionutilisateur.class.getResource("/image/iconfinder_pencil_3688460.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String sql = "insert into users(username,password) values(?,?)";
				
				try {
					prepared = conn.prepareStatement(sql);
					prepared.setString(1,textField_1.getText().toString());
					prepared.setString(2,textField.getText().toString());
					if(!textField_1.getText().equals("")&&!textField.getText().equals("")) {
						prepared.execute();
						JOptionPane.showMessageDialog(null,"Utilisateur ajoutée");
						updateTable();
						
						textField.setText("");
						textField_1.setText("");
					}else {
						JOptionPane.showMessageDialog(null,"Remplissez les champs vides !!");
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		btnNewButton.setBounds(23, 358, 147, 63);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Modifier");
		btnNewButton_1.setIcon(new ImageIcon(Gestionutilisateur.class.getResource("/image/iconfinder_wrench_3688431.png")));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String sql = "update users set  password = ? where username =? ";
				try {
					if(textField.getText().equals("")&&textField_1.getText().equals("")) {
						JOptionPane.showMessageDialog(null,"Sélectionner la ligne à modifier");
					}
					else {
						int a = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment modifier cet élèment ?", "Modifier Utilisateur",JOptionPane.YES_NO_OPTION);

						prepared=conn.prepareStatement(sql);
						prepared.setString(2,textField_1.getText().toString());
						prepared.setString(1,textField.getText().toString());
						if(a==0) {
							prepared.execute();
							JOptionPane.showMessageDialog(null,"les modifications sont appliquées");
						}
						updateTable();
						textField.setText("");
						textField_1.setText("");
						
					}
				
				} 
				
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		btnNewButton_1.setBounds(180, 358, 155, 63);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Supprimer\r\n\r\n");
		btnNewButton_2.setIcon(new ImageIcon(Gestionutilisateur.class.getResource("/image/iconfinder_trash_3688440.png")));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "delete from users where username = ? and password =?  ";
				 
				try {
					if(textField.getText().equals("")&&textField_1.getText().equals(""))
					{
						JOptionPane.showMessageDialog(null, "Remplissez les champs à supprimer");
					}else {
						int a = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment supprimer cet élèment ?", "Supprimer Utilisateur",JOptionPane.YES_NO_OPTION);

						prepared = conn.prepareStatement(sql);
						prepared.setString(1,textField_1.getText().toString());
						prepared.setString(2,textField.getText().toString());
						if(a==0) {
							prepared.execute();
							JOptionPane.showMessageDialog(null,"utilisateur supprimé");
						}
					
						textField_1.setText("");
						textField.setText("");
						updateTable();
					}
				
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_2.setBounds(345, 358, 162, 63);
		contentPane.add(btnNewButton_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(656, 189, 505, 331);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int ligne = table.getSelectedRow();
				
				String username = table.getModel().getValueAt(ligne, 0).toString();
				String password = table.getModel().getValueAt(ligne, 1).toString();
				textField_1.setText(username);
				textField.setText(password);
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.setIcon(new ImageIcon(Gestionutilisateur.class.getResource("/image/iconfinder_refresh_3688457.png")));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTable();
			}
		});
		btnNewButton_3.setBounds(1109, 104, 73, 63);
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuAdministrateur obj = new MenuAdministrateur();
				obj.setVisible(rootPaneCheckingEnabled);
				obj.setLocationRelativeTo(null);
				fermer();
			}
		});
		btnNewButton_4.setIcon(new ImageIcon(Gestionutilisateur.class.getResource("/image/iconfinder_arrow-left_3688523.png")));
		btnNewButton_4.setBounds(22, 104, 89, 56);
		contentPane.add(btnNewButton_4);
		
		JLabel lblNewLabel_2 = new JLabel("Tableau des utilisateurs");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_2.setBounds(657, 141, 176, 33);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(Gestionutilisateur.class.getResource("/image/sep.png")));
		lblNewLabel_3.setBounds(575, 101, 11, 448);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(Gestionutilisateur.class.getResource("/image/back2.png")));
		lblNewLabel.setBounds(10, 101, 1182, 448);
		contentPane.add(lblNewLabel);
		
		
		JLabel background = new JLabel("");
		background.setHorizontalAlignment(SwingConstants.CENTER);
		background.setBackground(UIManager.getColor("infoText"));
		background.setIcon(new ImageIcon("C:\\Users\\chleg\\OneDrive\\Bureau\\projet java\\image\\BACKGRAOUND.png"));
		background.setFont(new Font("Times New Roman", Font.BOLD, 14));
		background.setBounds(10, 0, 1182, 549);
		contentPane.add(background);
	}
	public void updateTable() {
		String sql="select * from users";
		try {
			prepared = conn.prepareStatement(sql);
			rs=prepared.executeQuery();
			table.setModel(net.proteanit.sql.DbUtils.resultSetToTableModel(rs));
			
		} catch (SQLException e) {			e.printStackTrace();
		}
		
	}
}
