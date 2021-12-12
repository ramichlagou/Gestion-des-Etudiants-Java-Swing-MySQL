package Default_Package;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import java.awt.Choice;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class GestionFiliere extends JFrame {

	private JPanel contentPane;
	private JTextField Field_filiere;
	private JTable table;
	Connection conn = null ;
	PreparedStatement prepared= null;
	ResultSet rs = null ;
	
	
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
					GestionFiliere frame = new GestionFiliere();
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
	public GestionFiliere() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1208, 598);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		conn =ConnectionMysqlJava.ConnecterDb();
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(569, 165, 610, 378);
		contentPane.add(scrollPane);
	
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				updateTable();
			}
		});
		btnNewButton_3.setIcon(new ImageIcon(GestionFiliere.class.getResource("/image/iconfinder_refresh_3688457.png")));
		btnNewButton_3.setBounds(1106, 108, 67, 46);
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuAdministrateur obj = new MenuAdministrateur();
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
				
				 fermer();
			}
		});
		btnNewButton_4.setIcon(new ImageIcon(GestionFiliere.class.getResource("/image/iconfinder_arrow-left_3688523.png")));
		btnNewButton_4.setBounds(20, 113, 67, 31);
		contentPane.add(btnNewButton_4);
		
		Field_filiere = new JTextField();
		Field_filiere.setColumns(10);
		Field_filiere.setBounds(158, 239, 219, 31);
		contentPane.add(Field_filiere);
		
		JComboBox comboBox_type = new JComboBox();
		comboBox_type.setModel(new DefaultComboBoxModel(new String[] {"S\u00E9lectionnez", " cycle ingenieur", "licence ", "pr\u00E9paratoire"}));
		comboBox_type.setBounds(158, 283, 219, 33);
		contentPane.add(comboBox_type);
		
		JButton ajouter = new JButton("");
		ajouter.setIcon(new ImageIcon(GestionFiliere.class.getResource("/image/addfili\u00E9res.png")));
		ajouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nom_filiere = Field_filiere.getText().toString();
				String type = comboBox_type.getSelectedItem().toString();
				
				String sql ="insert into filiére (nom_filiére,type) values(?,?)";
				
				try {

					if(nom_filiere.equals("")||type.equals("Sélectionnez")) {
						if(nom_filiere.equals("")&&type.equals("Sélectionnez"))
							JOptionPane.showMessageDialog(null, "Remplissez les champs");
						if(nom_filiere.equals("")&&!type.equals("Sélectionnez"))
							JOptionPane.showMessageDialog(null, "Tapez le nom du Filiére");
							if(!nom_filiere.equals("")&&type.equals("Sélectionnez"))
								JOptionPane.showMessageDialog(null, "Remplissez nom_Filiére !!");
						
					}else {
						prepared = conn.prepareStatement(sql);
						prepared.setString(1, nom_filiere);
						prepared.setString(2, type);
						prepared.execute();
						Field_filiere.setText("");
						comboBox_type.setSelectedItem("Sélectionnez");
						updateTable();
						JOptionPane.showMessageDialog(null, "FILIERE AJOUTE :D");
					}
					
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	 
			}
		});
		ajouter.setBounds(44, 393, 128, 128);
		contentPane.add(ajouter);

		JButton supprimer = new JButton("");
		supprimer.setIcon(new ImageIcon(GestionFiliere.class.getResource("/image/mdyfili\u00E9res.png")));
		supprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int ligne = table.getSelectedRow();
				if(ligne == -1) {
					JOptionPane.showMessageDialog(null, "Sélectionnez une ligne à supprimer");
				}else
				{
					int a = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment supprimr cet élèment ?", "Supprimer Filiére",JOptionPane.YES_NO_OPTION);
					String id = table.getModel().getValueAt(ligne,0).toString();
					
					
					String sql = "delete from filiére where id_filiére = '"+id+"'";
					try {
						prepared = conn.prepareStatement(sql);
						if(a == 0) {
							prepared.execute();
							JOptionPane.showMessageDialog(null, "Filiére Supprimée");
						}
							updateTable();
						} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
	
			}
		});
		supprimer.setBounds(196, 393, 128, 128);
		contentPane.add(supprimer);
		
		
		
		JButton modifier = new JButton("");
		modifier.setIcon(new ImageIcon(GestionFiliere.class.getResource("/image/delfili\u00E9res.png")));
		modifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int ligne  = table.getSelectedRow();
				if(ligne == -1) {
					JOptionPane.showMessageDialog(null, "Remplissez les champs vides !!");
				}else {
					
					int a = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment modifier cet élèment ?", "Modifier Filiére",JOptionPane.YES_NO_OPTION);
					String id = table.getModel().getValueAt(ligne,0).toString();
					
					String sql = "update filiére set  nom_filiére = ? , type = ?  where id_filiére = '"+id+"'";
					
					try {
						prepared =conn.prepareStatement(sql);
						prepared.setString(1, Field_filiere.getText().toString());
						prepared.setString(2, comboBox_type.getSelectedItem().toString());
						if(a==0) {
							prepared.execute();
							JOptionPane.showMessageDialog(null, "Les champs sont modifiés");
						}
						updateTable();
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
		
			}
		});
		modifier.setBounds(349, 393, 128, 128);
		contentPane.add(modifier);
		
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int ligne =table.getSelectedRow();
				
				String id =table.getModel().getValueAt(ligne,0).toString();
				String sql = "select * from filiére where id_filiére = '"+id+"'";
				try {
					
					prepared = conn.prepareStatement(sql);
					rs = prepared.executeQuery();
					if(rs.next()) {
						Field_filiere.setText(rs.getString("nom_filiére"));
						comboBox_type.setSelectedItem(rs.getString("type"));
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		scrollPane.setViewportView(table);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.LIGHT_GRAY);
		menuBar.setBounds(10, 0, 104, 22);
		contentPane.add(menuBar);
		
		JMenu mnNewMenu = new JMenu("fichier");
		mnNewMenu.setBackground(Color.GRAY);
		menuBar.add(mnNewMenu);
		
		JMenu mnNewMenu_2 = new JMenu("ouvrir");
		mnNewMenu.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Gestion des etudiants");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GestionEtudiants obj = new GestionEtudiants();
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
				fermer();
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("fermer");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				fermer();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_2);
		
		
		
		
		JLabel lblNewLabel_1_1 = new JLabel("Type :");
		lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(105, 283, 67, 31);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1 = new JLabel("Nom_Fili\u00E9re :");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1.setBounds(62, 247, 90, 24);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(GestionFiliere.class.getResource("/image/sep2.png")));
		lblNewLabel_3.setBounds(550, 97, 9, 466);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(GestionFiliere.class.getResource("/image/background getFili.png")));
		lblNewLabel.setBounds(10, 105, 1182, 454);
		contentPane.add(lblNewLabel);
		
		JLabel background = new JLabel("");
		background.setForeground(SystemColor.desktop);
		background.setBackground(new Color(204, 51, 0));
		background.setIcon(new ImageIcon("C:\\Users\\chleg\\OneDrive\\Bureau\\projet java\\image\\BACKGRAOUND.png"));
		background.setFont(UIManager.getFont("CheckBox.font"));
		background.setBounds(10, 0, 1182, 558);
		contentPane.add(background);
		}
		
		public void updateTable() {
			String sql="select * from filiére";
			try {
				prepared = conn.prepareStatement(sql);
				rs=prepared.executeQuery();
				table.setModel(net.proteanit.sql.DbUtils.resultSetToTableModel(rs));
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	
		
	}
}
