package Default_Package;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuAdministrateur extends JFrame {

	private JPanel contentPane;
	
	
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
					MenuAdministrateur frame = new MenuAdministrateur();
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
	public MenuAdministrateur() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1208, 598);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Gestion des utilisateurs");
		lblNewLabel_1.setFont(new Font("Myanmar Text", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(274, 293, 191, 22);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Gestionutilisateur obj = new Gestionutilisateur();
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
				fermer();
				
			}
		});
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\chleg\\OneDrive\\Bureau\\projet java\\image\\ajouter utilisateur.jfif"));
		btnNewButton.setBounds(264, 109, 183, 177);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GestionEtudiants obj = new GestionEtudiants();
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
				fermer();
			}
		});
		btnNewButton_1.setIcon(new ImageIcon("C:\\Users\\chleg\\OneDrive\\Bureau\\projet java\\image\\etudiant.jpg"));
		btnNewButton_1.setBounds(479, 109, 183, 177);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Gestion des Etudiants");
		lblNewLabel_1_1.setFont(new Font("Myanmar Text", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(489, 293, 191, 22);
		contentPane.add(lblNewLabel_1_1);
		
		JButton btnNewButton_1_1 = new JButton("");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GestionNote obj = new GestionNote();
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
				fermer();
			}
		});
		btnNewButton_1_1.setIcon(new ImageIcon("C:\\Users\\chleg\\OneDrive\\Bureau\\projet java\\image\\gestion des notes.jpg"));
		btnNewButton_1_1.setBounds(479, 315, 183, 177);
		contentPane.add(btnNewButton_1_1);
		
		JButton btnNewButton_1_1_1 = new JButton("");
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				GestionFiliere obj = new GestionFiliere();
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
				fermer();
				
			}
		});
		btnNewButton_1_1_1.setIcon(new ImageIcon("C:\\Users\\chleg\\OneDrive\\Bureau\\projet java\\image\\fili\u00E9res.png"));
		btnNewButton_1_1_1.setBounds(690, 109, 183, 177);
		contentPane.add(btnNewButton_1_1_1);
		
		JButton btnNewButton_1_1_2 = new JButton("");
		btnNewButton_1_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				GestionAbsence obj = new GestionAbsence();
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
				fermer();
				
			}
		});
		btnNewButton_1_1_2.setIcon(new ImageIcon("C:\\Users\\chleg\\OneDrive\\Bureau\\projet java\\image\\gestion absence.png"));
		btnNewButton_1_1_2.setBounds(690, 315, 183, 177);
		contentPane.add(btnNewButton_1_1_2);
		
		JButton btnNewButton_1_1_3 = new JButton("");
		btnNewButton_1_1_3.setIcon(new ImageIcon("C:\\Users\\chleg\\OneDrive\\Bureau\\projet java\\image\\gestion matieres.png"));
		btnNewButton_1_1_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GestionMatiere obj = new GestionMatiere();
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
				fermer();
				
				
			}
		});
		btnNewButton_1_1_3.setBounds(264, 315, 183, 177);
		contentPane.add(btnNewButton_1_1_3);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Gestion des absences");
		lblNewLabel_1_1_1.setFont(new Font("Myanmar Text", Font.PLAIN, 14));
		lblNewLabel_1_1_1.setBounds(700, 499, 191, 22);
		contentPane.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Gestion des matir\u00E8res");
		lblNewLabel_1_1_2.setFont(new Font("Myanmar Text", Font.PLAIN, 14));
		lblNewLabel_1_1_2.setBounds(274, 503, 191, 22);
		contentPane.add(lblNewLabel_1_1_2);
		
		JLabel lblNewLabel_1_1_3 = new JLabel("Gestion des notes");
		lblNewLabel_1_1_3.setFont(new Font("Myanmar Text", Font.PLAIN, 14));
		lblNewLabel_1_1_3.setBounds(489, 503, 191, 22);
		contentPane.add(lblNewLabel_1_1_3);
		
		JLabel lblNewLabel_1_1_4 = new JLabel("Gestion des Fili\u00E9res");
		lblNewLabel_1_1_4.setFont(new Font("Myanmar Text", Font.PLAIN, 14));
		lblNewLabel_1_1_4.setBounds(710, 293, 191, 22);
		contentPane.add(lblNewLabel_1_1_4);
		
		
		JLabel background = new JLabel("");
		background.setIcon(new ImageIcon("C:\\Users\\chleg\\OneDrive\\Bureau\\projet java\\image\\BACKGRAOUND.png"));
		background.setFont(new Font("Times New Roman", Font.BOLD, 14));
		background.setBounds(0, 0, 1182, 549);
		contentPane.add(background);
		
	}


}
