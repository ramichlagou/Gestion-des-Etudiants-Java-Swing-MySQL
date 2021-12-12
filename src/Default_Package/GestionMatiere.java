package Default_Package;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.sql.*;

public class GestionMatiere extends JFrame {

	private JPanel contentPane;
	JComboBox comboBox_id_prof ;
	
	Connection conn = null ;
	ResultSet rs = null ;
	PreparedStatement prepared = null;
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
					GestionMatiere frame = new GestionMatiere();
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
	public GestionMatiere() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1208, 598);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		conn =ConnectionMysqlJava.ConnecterDb();
		contentPane.setLayout(null);
		
	
	
		JButton btnACTUALISER = new JButton("");
		btnACTUALISER.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				updateTable();
			}
		});
		btnACTUALISER.setIcon(new ImageIcon(GestionFiliere.class.getResource("/image/iconfinder_refresh_3688457.png")));
		btnACTUALISER.setBounds(1106, 108, 67, 46);
		contentPane.add(btnACTUALISER);
		
		JButton btnPRECEDENT = new JButton("");
		btnPRECEDENT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuAdministrateur obj = new MenuAdministrateur();
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
				fermer();
			}
		});
		btnPRECEDENT.setIcon(new ImageIcon(GestionFiliere.class.getResource("/image/iconfinder_arrow-left_3688523.png")));
		btnPRECEDENT.setBounds(20, 113, 67, 31);
		contentPane.add(btnPRECEDENT);
		
	
		
		JComboBox comboBox_Matiere = new JComboBox();
		comboBox_Matiere.setModel(new DefaultComboBoxModel(new String[] {"S\u00E9lectionnez", "programmation orient\u00E9e objet java", "D\u00E9veloppement WEB2 javaScript", "M\u00E9thodologies de conception sys info", "R\u00E9seau CCNA2 3", "Syst\u00E9me exploitation Linux", "Recherche op\u00E9rationnelle", " Algorithme num\u00E9rique"}));
		comboBox_Matiere.setBounds(175, 197, 219, 33);
		contentPane.add(comboBox_Matiere);

		JLabel lblNewLabel_1_1 = new JLabel("Matiere :");
		lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(108, 197, 67, 31);
		contentPane.add(lblNewLabel_1_1);
		
		JComboBox comboBox_coefficient = new JComboBox();
		comboBox_coefficient.setModel(new DefaultComboBoxModel(new String[] {"Selectionnez", "0.5", "1", "1.5", "2", "2.5", "3", "3.5", "4"}));
		comboBox_coefficient.setBounds(175, 241, 219, 33);
		contentPane.add(comboBox_coefficient);
		
		JLabel lblNewLabel_1 = new JLabel("Coefficient :");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1.setBounds(83, 244, 90, 24);
		contentPane.add(lblNewLabel_1);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int ligne =table.getSelectedRow();
				
				String id =table.getModel().getValueAt(ligne,0).toString();
				String sql = "select * from matiere where id_matiere = '"+id+"'";
				try {
					
					prepared = conn.prepareStatement(sql);
					rs = prepared.executeQuery();
					if(rs.next()) {
						comboBox_Matiere.setSelectedItem(rs.getString("libelle"));
						comboBox_coefficient.setSelectedItem(rs.getString("coefficient"));

					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		scrollPane.setBounds(569, 165, 610, 378);
		contentPane.add(scrollPane);
		
		JButton ajouter = new JButton("");
		ajouter.setIcon(new ImageIcon(GestionMatiere.class.getResource("/image/addmatieres.png")));
		ajouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String coefficient =comboBox_coefficient.getSelectedItem().toString();
				String matiere = comboBox_Matiere.getSelectedItem().toString();
				String sql ="insert into matiere (libelle,coefficient) values(?,?)";
				
				try {

					if(coefficient.equals("Selectionnez")||matiere.equals("Selectionnez")) {
						if(coefficient.equals("Selectionnez")&&matiere.equals("Selectionnez"))
							JOptionPane.showMessageDialog(null, "Remplissez les champs");
						if(coefficient.equals("Selectionnez")&&!matiere.equals("Selectionnez"))
							JOptionPane.showMessageDialog(null, "Selectionnez le coefficient");
							if(!coefficient.equals("Selectionnez")&&matiere.equals("Selectionnez"))
								JOptionPane.showMessageDialog(null, "Selectionnez le nom du matiere !!");
						
					}else {
						prepared = conn.prepareStatement(sql);
						prepared.setString(1, matiere);
						prepared.setString(2, coefficient);
						prepared.execute();
						comboBox_coefficient.setSelectedItem("Selectionnez");
						comboBox_Matiere.setSelectedItem("Selectionnez");
						updateTable();
						JOptionPane.showMessageDialog(null, "Matiere AJOUTE :D");
					}
					
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	 
			}
			
		});
		ajouter.setBounds(31, 388, 128, 128);
		contentPane.add(ajouter);
		
		JButton supprimer = new JButton("");
		supprimer.setIcon(new ImageIcon(GestionMatiere.class.getResource("/image/delmatieres.png")));
		supprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int ligne = table.getSelectedRow();
				
				if(ligne == -1) {
					JOptionPane.showMessageDialog(null, "Sélectionnez une ligne à supprimer");
				}else
				{
					int a = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment supprimr cet élèment ?", "Supprimer Matiere",JOptionPane.YES_NO_OPTION);
					String id = table.getModel().getValueAt(ligne,0).toString();
					
					
					
					String sql = "delete from matiere where id_matiere = '"+id+"'";
					try {
						prepared = conn.prepareStatement(sql);
						if(a == 0) {
							prepared.execute();
							JOptionPane.showMessageDialog(null, "Matiere Supprimée");
						}
							updateTable();
						} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
				
			}
		});
		supprimer.setBounds(339, 388, 128, 128);
		contentPane.add(supprimer);
		
		JButton modifier = new JButton("");
		modifier.setIcon(new ImageIcon(GestionMatiere.class.getResource("/image/mdymatieres.png")));
		modifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int ligne  = table.getSelectedRow();
				if(ligne == -1) {
					JOptionPane.showMessageDialog(null, "Remplissez les champs vides !!");
				}else {
					
					int a = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment modifier cet élèment ?", "Modifier Matiere",JOptionPane.YES_NO_OPTION);
					String id = table.getModel().getValueAt(ligne,0).toString();
					
					String sql ="UPDATE matiere SET libelle=?,coefficient=? WHERE id_matiere = '"+id+"'";
					try {
						prepared =conn.prepareStatement(sql);
						prepared.setString(1, comboBox_Matiere.getSelectedItem().toString());
						prepared.setString(2, comboBox_coefficient.getSelectedItem().toString());
						if(a==0) {
							prepared.execute();
							JOptionPane.showMessageDialog(null, "Les champs sont modifiés");
						}
						updateTable();
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				
				
				
			}
		});
		modifier.setBounds(185, 388, 128, 128);
		contentPane.add(modifier);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(GestionFiliere.class.getResource("/image/sep2.png")));
		lblNewLabel_3.setBounds(550, 97, 9, 466);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(GestionMatiere.class.getResource("/image/background getMat.png")));
		lblNewLabel.setBounds(10, 105, 1182, 454);
		contentPane.add(lblNewLabel);
		
		JLabel background = new JLabel("");
		background.setForeground(SystemColor.desktop);
		background.setBackground(new Color(204, 51, 0));
		background.setIcon(new ImageIcon("C:\\Users\\chleg\\OneDrive\\Bureau\\projet java\\image\\BACKGRAOUND.png"));
		background.setFont(UIManager.getFont("CheckBox.font"));
		background.setBounds(10, 0, 1182, 558);
		contentPane.add(background);
		
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int ligne =table.getSelectedRow();
				
				String id =table.getModel().getValueAt(ligne,0).toString();
				String sql = "select * from matiere where id_matiere = '"+id+"'";
				try {
					prepared = conn.prepareStatement(sql);
					rs = prepared.executeQuery();
					if(rs.next()) {
						comboBox_Matiere.setSelectedItem(rs.getString("libelle"));
						comboBox_coefficient.setSelectedItem(rs.getString("coefficient"));
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

		}
		
		public void updateTable() {
			String sql="select * from Matiere";
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
