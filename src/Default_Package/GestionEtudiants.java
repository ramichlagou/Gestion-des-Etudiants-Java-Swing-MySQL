package Default_Package;
import java.awt.BorderLayout;
import java.awt.Desktop;
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
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.Segment;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64.InputStream;

public class GestionEtudiants extends JFrame {

	protected static final BaseColor BaseColor = null;

	private JPanel contentPane;
	
	Connection conn = null ;
	PreparedStatement prepared= null;
	ResultSet rs = null ;
	private JTextField Field_prenom;
	private JTextField Field_nom;
	private JTextField Field_num;
	private JTextField Field_adresse;
	private JTextField Field_date;
	private JTextField Field_cin;
	private JTable table;
	private String paths;
	private JLabel labimg;
	JComboBox comboBox_ty ;
	
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
					GestionEtudiants frame = new GestionEtudiants();
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
	public GestionEtudiants() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1208, 598);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		conn =ConnectionMysqlJava.ConnecterDb();
		
		JLabel lblNewLabel_1 = new JLabel("Pr\u00E9nom :");
		lblNewLabel_1.setFont(new Font("Century", Font.BOLD, 14));
		lblNewLabel_1.setBounds(92, 114, 81, 35);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Nom :");
		lblNewLabel_1_1.setFont(new Font("Century", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(115, 148, 123, 35);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Adresse :");
		lblNewLabel_1_2.setFont(new Font("Century", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(92, 304, 133, 35);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("CIN :");
		lblNewLabel_1_3.setFont(new Font("Century", Font.BOLD, 14));
		lblNewLabel_1_3.setBounds(115, 190, 81, 31);
		contentPane.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_4 = new JLabel("Date Naissance :");
		lblNewLabel_1_4.setFont(new Font("Century", Font.BOLD, 14));
		lblNewLabel_1_4.setBounds(39, 270, 144, 23);
		contentPane.add(lblNewLabel_1_4);
		
		JLabel lblNewLabel_1_5 = new JLabel("Num tel :");
		lblNewLabel_1_5.setFont(new Font("Century", Font.BOLD, 14));
		lblNewLabel_1_5.setBounds(92, 224, 81, 35);
		contentPane.add(lblNewLabel_1_5);
		
		Field_prenom = new JTextField();
		Field_prenom.setColumns(10);
		Field_prenom.setBounds(175, 110, 123, 35);
		contentPane.add(Field_prenom);
		
		Field_nom = new JTextField();
		Field_nom.setColumns(10);
		Field_nom.setBounds(175, 148, 123, 35);
		contentPane.add(Field_nom);
		
		Field_num = new JTextField();
		Field_num.setColumns(10);
		Field_num.setBounds(175, 224, 123, 35);
		contentPane.add(Field_num);
		
		Field_adresse = new JTextField();
		Field_adresse.setColumns(10);
		Field_adresse.setBounds(175, 300, 204, 35);
		contentPane.add(Field_adresse);
		
		Field_date = new JTextField();
		Field_date.setColumns(10);
		Field_date.setBounds(175, 262, 123, 35);
		contentPane.add(Field_date);
		
		Field_cin = new JTextField();
		Field_cin.setColumns(10);
		Field_cin.setBounds(175, 186, 123, 35);
		contentPane.add(Field_cin);
		
		
		JPanel panel = new JPanel();
		panel.setBounds(358, 114, 161, 145);
		contentPane.add(panel);
		panel.setLayout(null);
		
		 labimg = new JLabel("");
		labimg.setBounds(0, 0, 161, 145);
		panel.add(labimg);
		
		
		JButton btn_ajouter = new JButton("");
		btn_ajouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String prenom = Field_prenom.getText().toString();
				String nom = Field_nom.getText().toString();
				String cin = Field_cin.getText().toString();
				String num =Field_num.getText().toString();
				String adresse = Field_adresse.getText().toString();
				String date_naissance = Field_date.getText().toString();
				String filiere = comboBox_ty.getSelectedItem().toString();
				String sql = "insert into etudiant(nom , prenom , cin , tel , date_naissance , adresse,filiere,image) values(?,?,?,?,?,?,?,?)";
				if(!prenom.equals("")&&!nom.equals("")&&!cin.equals("")&&!num.equals("")&&!adresse.equals("")&&!date_naissance.equals("")&&!filiere.equals("")) 	{
				 try {
					 
					 FileInputStream img= new FileInputStream(new File(paths));
					prepared = conn.prepareStatement(sql);
					prepared.setString(1, nom);
					prepared.setString(2, prenom);
					prepared.setString(3, cin);
					prepared.setString(4,num);
					prepared.setString(5, date_naissance);
					prepared.setString(6, adresse);
					prepared.setString(7, filiere);
					prepared.setBlob(8, img);
					prepared.execute();
						JOptionPane.showMessageDialog(null,"Etudiant ajoutée");
						updateTable();
						Field_prenom.setText("");
						Field_nom.setText("");
						Field_cin.setText("");
						Field_num.setText("");
						Field_adresse.setText("");
						Field_date.setText("");
						comboBox_ty.setSelectedItem("");
						labimg.setText("");
	
			}catch (SQLException | FileNotFoundException e1) {e1.printStackTrace();}
					
				 
				}
				 
				else {
					JOptionPane.showMessageDialog(null,"Remplissez les champs vides !!");
				}
				}
			
		});
		btn_ajouter.setIcon(new ImageIcon(GestionEtudiants.class.getResource("/image/Add-Male-User.ico.jpg")));
		btn_ajouter.setBounds(18, 400, 128, 128);
		contentPane.add(btn_ajouter);
		
		JButton btn_modifier = new JButton("");
		btn_modifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ligne=table.getSelectedRow();
				if(ligne == -1) {
					JOptionPane.showMessageDialog(null, "sélectionner une ligne à modifiée");
				}
				else {
					int a = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment modifier cet élèment ?", "Modifier Etudiant",JOptionPane.YES_NO_OPTION);
					String id =table.getModel().getValueAt(ligne,0).toString();
					
					String sql = "update etudiant set prenom=?,nom=?,cin=?,tel=?,date_naissance=?,adresse=? ,filiere =? ,image = ? where id_etudiant = '"+id+"' ";
				
					
					try {
							java.io.InputStream im = new FileInputStream(new File(paths));	
						prepared=conn.prepareStatement(sql);
						
						prepared.setString(1,Field_prenom.getText().toString());
						prepared.setString(2, Field_nom.getText().toString());
						prepared.setString(3, Field_cin.getText().toString());
						prepared.setString(4, Field_num.getText().toString());
						prepared.setString(5, Field_date.getText().toString());
						prepared.setString(6,Field_adresse.getText().toString());
						prepared.setString(7, comboBox_ty.getSelectedItem().toString());
						prepared.setBlob(8,im);
						if(a==0) {
							prepared.execute();
							JOptionPane.showMessageDialog(null, "ETUDIANT MODIFIEE :D");
						}
						
						updateTable();
					
						
						Field_prenom.setText("");
						Field_nom.setText("");
						Field_cin.setText("");
						Field_num.setText("");
						Field_adresse.setText("");
						Field_date.setText("");
						comboBox_ty.setSelectedItem("");
						labimg.setText("");
					} catch (SQLException | FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btn_modifier.setIcon(new ImageIcon(GestionEtudiants.class.getResource("/image/Edit-Male-User.ico.jpg")));
		btn_modifier.setBounds(155, 400, 128, 128);
		contentPane.add(btn_modifier);
		
		JButton btn_supprimer = new JButton("");
		btn_supprimer.setIcon(new ImageIcon(GestionEtudiants.class.getResource("/image/Remove-Male-User.ico.jpg")));
		btn_supprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int ligne=table.getSelectedRow();
				
				if (ligne == -1) {
					JOptionPane.showMessageDialog(null, "sélectionner une ligne à supprinée");
				}else {
					int a = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment supprimer cet élèment ?", "Supprimer Etudiant",JOptionPane.YES_NO_OPTION);

					try {
						String id =table.getModel().getValueAt(ligne,0).toString();
						
						String sql = "delete from etudiant  where id_etudiant = '"+id+"' ";
						prepared = conn.prepareStatement(sql);
						if(a==0) {
							prepared.execute();
							
							JOptionPane.showMessageDialog(null, "ETUDIANT SUPPRIME");
						}
						updateTable();
						Field_prenom.setText("");
						Field_nom.setText("");
						Field_cin.setText("");
						Field_num.setText("");
						Field_adresse.setText("");
						Field_date.setText("");
						comboBox_ty.setSelectedItem("");
						labimg.setText("");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			
				
				
			}
		});
		btn_supprimer.setBounds(291, 400, 128, 128);
		contentPane.add(btn_supprimer);
		
		JButton btn_imprimer = new JButton("");
		btn_imprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 com.itextpdf.text.Document  doc =new com.itextpdf.text.Document() ;
				 
				 String sql = "select * from etudiant";
				 
				 try {
					 prepared = conn.prepareStatement(sql);
					 rs = prepared.executeQuery();
					 
					PdfWriter.getInstance(doc, new FileOutputStream("C:\\Users\\chleg\\OneDrive\\Bureau\\projet java\\Etudiant.pdf"));
					 doc.open();
//					 
					 
					 Image img = Image.getInstance("C:\\Users\\chleg\\OneDrive\\Bureau\\projet java\\image\\LOGO.png");
				   doc.add(img);
				 doc.add(new Paragraph(" "));
				 doc.add(new Paragraph("Liste des étudiants :"));
				 doc.add(new Paragraph(" "));
				 
				 PdfPTable table = new PdfPTable(7);
				table.setWidthPercentage(100);
				PdfPCell cell;
				cell = new PdfPCell(new Phrase("Prenom",FontFactory.getFont("Arial",12)));
				cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				cell.setBackgroundColor(BaseColor.GRAY);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("nom",FontFactory.getFont("Arial",12)));
				cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				cell.setBackgroundColor(BaseColor.GRAY);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("CIN",FontFactory.getFont("Arial",12)));
				cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				cell.setBackgroundColor(BaseColor.GRAY);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("Num",FontFactory.getFont("Arial",12)));
				cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				cell.setBackgroundColor(BaseColor.GRAY);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("Date Naissance",FontFactory.getFont("Arial",12)));
				cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				cell.setBackgroundColor(BaseColor.GRAY);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("Adresse",FontFactory.getFont("Arial",12)));
				cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				cell.setBackgroundColor(BaseColor.GRAY);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("Filiere",FontFactory.getFont("Arial",12)));
				cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				cell.setBackgroundColor(BaseColor.GRAY);
				table.addCell(cell);
				
				////////////////////////////////////////////////////////////////
				///////////////////////////////////////////////////////////////
				while(rs.next()) {
					cell = new PdfPCell(new Phrase(rs.getString("nom").toString(),FontFactory.getFont("Arial",12)));
					cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
					table.addCell(cell);
					
					cell = new PdfPCell(new Phrase(rs.getString("prenom").toString(),FontFactory.getFont("Arial",12)));
					cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
					table.addCell(cell);
					
					cell = new PdfPCell(new Phrase(rs.getString("cin").toString(),FontFactory.getFont("Arial",12)));
					cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
					table.addCell(cell);
					
					cell = new PdfPCell(new Phrase(rs.getString("tel").toString(),FontFactory.getFont("Arial",12)));
					cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
					table.addCell(cell);
					
					cell = new PdfPCell(new Phrase(rs.getString("date_naissance").toString(),FontFactory.getFont("Arial",12)));
					cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
					table.addCell(cell);
					
					cell = new PdfPCell(new Phrase(rs.getString("adresse").toString(),FontFactory.getFont("Arial",12)));
					cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
					table.addCell(cell);
					
					cell = new PdfPCell(new Phrase(rs.getString("filiere"),FontFactory.getFont("Arial",12)));
					cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
					table.addCell(cell);
					
				}
				doc.add(table);
				
		
				 doc.close();
				 Desktop.getDesktop().open(new File("C:\\Users\\chleg\\OneDrive\\Bureau\\projet java\\Etudiant.pdf"));
				 
				 } catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (DocumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					 
 
			
			}
			
		});
		btn_imprimer.setIcon(new ImageIcon(GestionEtudiants.class.getResource("/image/imprimer.png")));
		btn_imprimer.setBounds(427, 400, 115, 129);
		contentPane.add(btn_imprimer);
		
		JLabel lblNewLabel_2 = new JLabel("Fili\u00E8re :\r\n");
		lblNewLabel_2.setFont(new Font("Century", Font.BOLD, 14));
		lblNewLabel_2.setBounds(92, 346, 81, 31);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(GestionEtudiants.class.getResource("/image/sep2.png")));
		lblNewLabel_3.setBounds(550, 96, 9, 453);
		contentPane.add(lblNewLabel_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(569, 153, 618, 391);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int ligne=table.getSelectedRow();
				String id =table.getModel().getValueAt(ligne,0).toString();
				
				String sql = "select * from etudiant where id_etudiant = '"+id+"' ";
				try {
					prepared = conn.prepareStatement(sql);
					rs =prepared.executeQuery();
					if(rs.next()) {
						Field_prenom.setText(rs.getString("prenom"));
						Field_nom.setText(rs.getString("nom"));
						Field_cin.setText(rs.getString("cin"));
						Field_num.setText(rs.getString("tel"));
						Field_adresse.setText(rs.getString("adresse"));
						Field_date.setText(rs.getString("date_naissance"));
						comboBox_ty.setSelectedItem(rs.getString("filiere"));
					
					byte[] img = rs.getBytes("image");
					ImageIcon image = new ImageIcon(img);
					java.awt.Image imge = image.getImage();
					java.awt.Image myimg = imge.getScaledInstance(labimg.getWidth(), labimg.getHeight(),java.awt.Image.SCALE_SMOOTH);
					ImageIcon imags = new ImageIcon(myimg);
					labimg.setIcon(imags);
					
					
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel_4 = new JLabel("Tableau des Etudiants :");
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_4.setBounds(639, 120, 215, 25);
		contentPane.add(lblNewLabel_4);
		
		JButton btnNewButton_5 = new JButton("");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuAdministrateur obj = new MenuAdministrateur();
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
				fermer();
				
			}
		});
		btnNewButton_5.setIcon(new ImageIcon(GestionEtudiants.class.getResource("/image/iconfinder_arrow-left_3688523.png")));
		btnNewButton_5.setBounds(18, 110, 64, 38);
		contentPane.add(btnNewButton_5);
		
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTable();
			}
		});
		btnNewButton_3.setIcon(new ImageIcon(GestionEtudiants.class.getResource("/image/iconfinder_refresh_3688457.png")));
		btnNewButton_3.setBounds(1128, 101, 64, 44);
		contentPane.add(btnNewButton_3);
		
	
		
		JButton btnParcourir = new JButton("Parcourir");
		btnParcourir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser =new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("IMAGE","jpg","png","gif");
				fileChooser.addChoosableFileFilter(filter);
				int result = fileChooser.showSaveDialog(null);
				
				if( result== JFileChooser.APPROVE_OPTION) {
					File selectedfile = fileChooser.getSelectedFile();
					String path = selectedfile.getAbsolutePath();
					ImageIcon myImage = new ImageIcon(path);
					java.awt.Image img = myImage.getImage();
					java.awt.Image  newImage = img.getScaledInstance(labimg.getWidth(), labimg.getHeight(), java.awt.Image.SCALE_SMOOTH);
					ImageIcon finalImage = new ImageIcon(newImage);
					labimg.setIcon(finalImage);
					
					paths = path ;
				}else 
				{
					if(result == JFileChooser.CANCEL_OPTION) {
						JOptionPane.showMessageDialog(null, "vous n'avez rien selectionner");
					}
				}
			}
		});
		btnParcourir.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnParcourir.setBounds(359, 260, 159, 33);
		contentPane.add(btnParcourir);
		
		comboBox_ty = new JComboBox();
		comboBox_ty.setBounds(175, 339, 203, 35);
		contentPane.add(comboBox_ty);
		remplicombt();
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(GestionEtudiants.class.getResource("/image/background getEtud.png")));
		lblNewLabel.setBounds(10, 99, 1192, 450);
		contentPane.add(lblNewLabel);
		
		JLabel background = new JLabel("");
		background.setIcon(new ImageIcon("C:\\Users\\chleg\\OneDrive\\Bureau\\projet java\\image\\BACKGRAOUND.png"));
		background.setFont(new Font("Times New Roman", Font.BOLD, 14));
		background.setBounds(10, 0, 1192, 549);
		contentPane.add(background);
		}
		public void updateTable() {
			String sql="select * from etudiant";
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
			
			String sql = "select * from Filiére";
				
			try {
				prepared =conn.prepareStatement(sql);
				rs =prepared.executeQuery();
				while(rs.next()) {

					String nom = rs.getString("nom_filiére").toString();
					comboBox_ty.addItem(nom);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
}
