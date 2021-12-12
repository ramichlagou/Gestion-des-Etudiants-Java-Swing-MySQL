package Default_Package;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
public class GestionNote extends JFrame {

	private JPanel contentPane;
	private JTextField textField_note;
	JComboBox comboBox_etud;
	JComboBox comboBox_matiere;
	 
	Connection conn =null ; 
	PreparedStatement prepared = null ; 
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
					GestionNote frame = new GestionNote();
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
	public GestionNote() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1208, 598);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		conn = ConnectionMysqlJava.ConnecterDb();
		
		
		JLabel lblNewLabel_1 = new JLabel("id_etudiant :\r\n");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1.setBounds(69, 173, 86, 27);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("id_matiere :\r\n");
		lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(66, 214, 86, 27);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("note :\r\n");
		lblNewLabel_1_2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(103, 258, 45, 27);
		contentPane.add(lblNewLabel_1_2);
		
		textField_note = new JTextField();
		textField_note.setColumns(10);
		textField_note.setBounds(150, 259, 184, 32);
		contentPane.add(textField_note);
		 comboBox_etud = new JComboBox();
		 comboBox_etud.setModel(new DefaultComboBoxModel(new String[] {"Selectionnez"}));
		comboBox_etud.setBounds(151, 171, 184, 32);
		contentPane.add(comboBox_etud);
		remplfield();
		
		 comboBox_matiere = new JComboBox();
		comboBox_matiere.setModel(new DefaultComboBoxModel(new String[] {"Selectionnez"}));
		comboBox_matiere.setBounds(150, 212, 184, 32);
		contentPane.add(comboBox_matiere);
		remplfield_matiere();
		
		JButton btnAjouter = new JButton("");
		btnAjouter.setIcon(new ImageIcon(GestionNote.class.getResource("/image/addnotes.png")));
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id_etudiant =comboBox_etud.getSelectedItem().toString();
				String id_matiere = comboBox_matiere.getSelectedItem().toString();
				String note = textField_note.getText().toString();
				
				String sql ="insert into evaluation (id_etudiant,id_matiere,note) values(?,?,?)";
				
				try {

					if(id_etudiant.equals("Selectionnez")||id_matiere.equals("Selectionnez") || note.equals("")) {
						if(id_etudiant.equals("Selectionnez")&&id_matiere.equals("Selectionnez")&&note.equals(""))
							JOptionPane.showMessageDialog(null, "Remplissez les champs");
						if(id_etudiant.equals("Selectionnez")&&!id_matiere.equals("Selectionnez")&&!note.equals(""))
							JOptionPane.showMessageDialog(null, "Selectionnez id_etudiant");
							if(!id_etudiant.equals("Selectionnez")&&id_matiere.equals("Selectionnez")&&!note.equals(""))
								JOptionPane.showMessageDialog(null, "Selectionnez id_matiere !!");
							if(!id_etudiant.equals("Selectionnez")&&!id_matiere.equals("Selectionnez")&&note.equals(""))
								JOptionPane.showMessageDialog(null, "Remplissez la note !!");
					}else {
						prepared = conn.prepareStatement(sql);
						prepared.setString(1,id_etudiant );
						prepared.setString(2, id_matiere);
						prepared.setString(3, note);
						prepared.execute();
						comboBox_etud.setSelectedItem("Selectionnez");
						comboBox_matiere.setSelectedItem("Selectionnez");
						textField_note.setText("Remplissez la note");
						updateTable();
						JOptionPane.showMessageDialog(null, "Matiere AJOUTE :D");
					}
		
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		
			
		});
		btnAjouter.setBounds(48, 377, 128, 128);
		contentPane.add(btnAjouter);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon(GestionNote.class.getResource("/image/mdynotes.png")));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int ligne=table.getSelectedRow();
				if(ligne == -1) {
					JOptionPane.showMessageDialog(null, "sélectionner une ligne à modifiée");
				}
				else {
					int a = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment modifier cet élèment ?", "Modifier Etudiant",JOptionPane.YES_NO_OPTION);
					String idEtud =table.getModel().getValueAt(ligne,0).toString();
					String sql = "update evaluation set id_etudiant=?,id_matiere=?,note=? where id_etudiant = '"+idEtud+"'";
				
					
					try {
							
						prepared=conn.prepareStatement(sql);
						
						prepared.setString(1,comboBox_etud.getSelectedItem().toString());
						prepared.setString(2,comboBox_matiere.getSelectedItem().toString());
						prepared.setString(3, textField_note.getText().toString());
				
						if(a==0) {
							prepared.execute();
							JOptionPane.showMessageDialog(null, "ETUDIANT MODIFIEE :D");
						}
						
						updateTable();
				
						comboBox_etud.setSelectedItem("Selectionnez");
						comboBox_matiere.setSelectedItem("Selectionnez");
						textField_note.setText("");
					
					
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
				}
				
			}
		});
		btnNewButton_1.setBounds(195, 377, 128, 128);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setIcon(new ImageIcon(GestionNote.class.getResource("/image/delnotes.png")));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
int ligne=table.getSelectedRow();
				
				if (ligne == -1) {
					JOptionPane.showMessageDialog(null, "sélectionner une ligne à supprinée");
				}else {
					int a = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment supprimer cet élèment ?", "Supprimer Etudiant",JOptionPane.YES_NO_OPTION);

					try {
						String idEtud =table.getModel().getValueAt(ligne,0).toString();
						String sql = "delete from evaluation  where id_etudiant = '"+idEtud+"' ";
						prepared = conn.prepareStatement(sql);
						if(a==0) {
							prepared.execute();
							
							JOptionPane.showMessageDialog(null, "ETUDIANT SUPPRIME");
						}
						updateTable();
						textField_note.setText("");
						comboBox_etud.setSelectedItem("");
						comboBox_matiere.setSelectedItem("");
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			
				
			}
		});
		btnNewButton_2.setBounds(345, 377, 128, 128);
		contentPane.add(btnNewButton_2);
		
	
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(554, 165, 610, 378);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int ligne =table.getSelectedRow();
				String idEtud =table.getModel().getValueAt(ligne,0).toString();
				String sql = "select * from evaluation where id_etudiant = '"+idEtud+"'";
				try {
					
					prepared = conn.prepareStatement(sql);
					rs = prepared.executeQuery();
					if(rs.next()) {
						comboBox_etud.setSelectedItem(rs.getString("id_etudiant"));
						comboBox_matiere.setSelectedItem(rs.getString("id_matiere"));
						textField_note.setText(rs.getString("note"));
						
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		scrollPane.setViewportView(table);
		JButton btnNewButton_4 = new JButton("");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuAdministrateur obj = new MenuAdministrateur();
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
				
				 fermer();
			}
		});
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				updateTable();
			}
		});
		btnNewButton_3.setIcon(new ImageIcon(GestionFiliere.class.getResource("/image/iconfinder_refresh_3688457.png")));
		btnNewButton_3.setBounds(1106, 108, 67, 46);
		contentPane.add(btnNewButton_3);
		btnNewButton_4.setIcon(new ImageIcon(GestionFiliere.class.getResource("/image/iconfinder_arrow-left_3688523.png")));
		btnNewButton_4.setBounds(20, 113, 67, 31);
		contentPane.add(btnNewButton_4);
		
		JLabel lblNewLabel_2 = new JLabel("tableau des Notes : ");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_2.setBounds(564, 127, 165, 27);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(GestionNote.class.getResource("/image/backGestNot.png")));
		lblNewLabel.setBounds(10, 99, 1182, 450);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		JLabel background = new JLabel("");
		background.setIcon(new ImageIcon("C:\\Users\\chleg\\OneDrive\\Bureau\\projet java\\image\\BACKGRAOUND.png"));
		background.setFont(new Font("Times New Roman", Font.BOLD, 14));
		background.setBounds(10, 0, 1192, 549);
		contentPane.add(background);
		
		

	}
	public void remplfield() {
		
	String sql = "select * from etudiant";
	
	try {
		prepared =conn.prepareStatement(sql);
		rs =prepared.executeQuery();
		while(rs.next()) {

			String id = rs.getString("id_etudiant").toString();
			comboBox_etud.addItem(id);
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
}
	public void remplfield_matiere() {
		String sql ="select * from matiere";
		try {
			prepared = conn.prepareStatement(sql);
			rs =prepared.executeQuery();
			while(rs.next()) {
				String id =rs.getString("id_matiere").toString();
				comboBox_matiere.addItem(id);
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	public void updateTable() {
		String sql="select * from evaluation";
		try {
			prepared = conn.prepareStatement(sql);
			rs=prepared.executeQuery();
			table.setModel(net.proteanit.sql.DbUtils.resultSetToTableModel(rs));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	

	
}
}
