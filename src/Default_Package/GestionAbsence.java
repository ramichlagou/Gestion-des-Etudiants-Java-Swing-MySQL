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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.AbstractButton;
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
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JCalendar;

public class GestionAbsence extends JFrame {

	protected static final String JTextField = null;
	private JPanel contentPane;
	private JTable table;
	JComboBox comboBox_nom;
	
	Connection conn = null ;
	PreparedStatement prepared= null;
	ResultSet rs = null ;
	private JDateChooser dateChooser;
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
					GestionAbsence frame = new GestionAbsence();
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
	public GestionAbsence() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1208, 598);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		contentPane.setLayout(null);
		conn =ConnectionMysqlJava.ConnecterDb();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(582, 165, 610, 378);
		contentPane.add(scrollPane);
	
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				updateTable();
			}
		});
		 comboBox_nom = new JComboBox();
		comboBox_nom.setFont(new Font("Tahoma", Font.BOLD, 11));
		comboBox_nom.setBounds(161, 247, 188, 24);
		contentPane.add(comboBox_nom);
		remplicombt();
		
		dateChooser = new JDateChooser();
		dateChooser.getCalendarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		dateChooser.setBounds(161, 282, 188, 26);
		contentPane.add(dateChooser);
		
		
		btnNewButton_3.setIcon(new ImageIcon(GestionFiliere.class.getResource("/image/iconfinder_refresh_3688457.png")));
		btnNewButton_3.setBounds(1106, 108, 67, 46);
		contentPane.add(btnNewButton_3);

		JComboBox comboBox_raison = new JComboBox();
		comboBox_raison.setModel(new DefaultComboBoxModel(new String[] {"malade", "retard"}));
		comboBox_raison.setBounds(161, 319, 188, 26);
		contentPane.add(comboBox_raison);
		
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
	
		JButton ajouter = new JButton("");
		ajouter.setIcon(new ImageIcon(GestionAbsence.class.getResource("/image/addAbs.png")));
		ajouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nom = comboBox_nom.getSelectedItem().toString();
				String date =((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
				String raison = comboBox_raison.getSelectedItem().toString();
				
				String sql ="insert into absence (nom,date,raison) values(?,?,?)";
				
				try {

					if(date.equals("")||nom.equals("selectionnez") || raison.equals("selectionnez")) {
						if(date.equals("")&&nom.equals("selectionnez")&&raison.equals("selectionnez"));
							JOptionPane.showMessageDialog(null, "Remplissez les champs");
						if(date.equals("")&&!nom.equals("selectionnez")&&raison.equals("selectionnez"))
							JOptionPane.showMessageDialog(null, "selectionnez le nom de l'étudiant");
							if(!date.equals("")&&nom.equals("selectionnez")&&raison.equals("selectionnez"));
								JOptionPane.showMessageDialog(null, "Remplissez la date !!");
								if(date.equals("")&&nom.equals("selectionnez")&&!raison.equals("selectionnez"));
								JOptionPane.showMessageDialog(null, "Remplissez la raison !!");
						
					}else {
						prepared = conn.prepareStatement(sql);
						prepared.setString(1, nom);
						prepared.setString(2, date);
						prepared.setString(3, raison);
						prepared.execute();
						dateChooser.setDateFormatString("");
						comboBox_raison.setSelectedItem("Selectionnez");
						comboBox_nom.setSelectedItem("Selectionnez");
						updateTable();
						JOptionPane.showMessageDialog(null, "absence AJOUTE :D");
					}
					
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	 
			}
		});
		ajouter.setBounds(43, 393, 110, 114);
		contentPane.add(ajouter);

		JButton supprimer = new JButton("");
		supprimer.setIcon(new ImageIcon(GestionAbsence.class.getResource("/image/modify.png")));
		supprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int ligne = table.getSelectedRow();
				if(ligne == -1) {
					JOptionPane.showMessageDialog(null, "Sélectionnez une ligne à supprimer");
				}else
				{
					int a = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment supprimr cet élèment ?", "Supprimer absence",JOptionPane.YES_NO_OPTION);
					String id = table.getModel().getValueAt(ligne,0).toString();
					
					
					String sql = "delete from absence where id = '"+id+"'";
					try {
						prepared = conn.prepareStatement(sql);
						if(a == 0) {
							prepared.execute();
							JOptionPane.showMessageDialog(null, "absence Supprimée");
						}
							updateTable();
						} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
	
			}
		});
		supprimer.setBounds(196, 393, 110, 114);
		contentPane.add(supprimer);
		
		
		
		JButton modifier = new JButton("");
		modifier.setIcon(new ImageIcon(GestionAbsence.class.getResource("/image/deleabsence.png")));
		modifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int ligne  = table.getSelectedRow();
				if(ligne == -1) {
					JOptionPane.showMessageDialog(null, "Remplissez les champs vides !!");
				}else {
					
					int a = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment modifier cet élèment ?", "Modifier absence",JOptionPane.YES_NO_OPTION);
					String id = table.getModel().getValueAt(ligne,0).toString();
					
					String sql = "update absence set  nom = ? , date = ? ,raison =?  where id = '"+id+"'";
					
					try {
						prepared =conn.prepareStatement(sql);
						
						prepared.setString(1, comboBox_nom.getSelectedItem().toString());
						prepared.setString(2, dateChooser.getDateFormatString());
						prepared.setString(3, comboBox_raison.getSelectedItem().toString());
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
		modifier.setBounds(350, 393, 110, 114);
		contentPane.add(modifier);

	
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int ligne =table.getSelectedRow();
				
				String id =table.getModel().getValueAt(ligne,0).toString();
				String sql = "select * from absence where id = '"+id+"'";
				try {
					
					prepared = conn.prepareStatement(sql);
					rs = prepared.executeQuery();
					if(rs.next()) {
						comboBox_nom.setSelectedItem(rs.getString("nom"));
						dateChooser.setDateFormatString(rs.getString("date"));
						comboBox_raison.setSelectedItem(rs.getString("raison"));
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		scrollPane.setViewportView(table);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		

		
		
		
		
		
		JLabel lblNewLabel_1_1 = new JLabel("Date :");
		lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(114, 282, 67, 31);
		contentPane.add(lblNewLabel_1_1);
		
		
		JLabel lblNewLabel_2 = new JLabel("Raison : ");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_2.setBounds(103, 324, 98, 14);
		contentPane.add(lblNewLabel_2);
		

		
		
		
		JLabel lblNewLabel_1 = new JLabel("Nom Etudiant :\r\n");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1.setBounds(62, 247, 113, 24);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(GestionFiliere.class.getResource("/image/sep2.png")));
		lblNewLabel_3.setBounds(550, 97, 9, 466);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel = new JLabel("");
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
			String sql="select * from absence";
			try {
				prepared = conn.prepareStatement(sql);
				rs=prepared.executeQuery();
				table.setModel(net.proteanit.sql.DbUtils.resultSetToTableModel(rs));
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

		public void remplicombt() {
			
			String sql = "select * from etudiant";
				
			try {
				prepared =conn.prepareStatement(sql);
				rs =prepared.executeQuery();
				while(rs.next()) {

					String nom = rs.getString("nom").toString();
					String prenom = rs.getString("prenom").toString();
					comboBox_nom.addItem(nom+" "+prenom);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	public String getDateChooserDateFormatString() {
		return dateChooser.getDateFormatString();
	}
	public void setDateChooserDateFormatString(String dateFormatString) {
		dateChooser.setDateFormatString(dateFormatString);
	}
	}


