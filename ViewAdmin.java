package e_learning;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class ViewAdmin extends JFrame{
	Admin ad;
	boolean b;
	JLabel newProf = new JLabel("Nouveau professeur:", JLabel.CENTER);
	JLabel newEtud = new JLabel("Nouveau étudiant:", JLabel.CENTER);
	JLabel newClass = new JLabel("Nouveau classe:", JLabel.CENTER);
	JLabel nomProf = new JLabel("Nom:");
	JLabel passProf = new JLabel("Mot de passe:");
	JLabel matiere = new JLabel("Matière:");
	JLabel prof = new JLabel("Quel professeur:");
	JLabel nomEtud = new JLabel("Nom:");
	JLabel passEtud = new JLabel("Mot de passe:");
	JLabel classe = new JLabel("Quelle classe");
	
	JPanel panelProf = new JPanel();
	JPanel panelEtud = new JPanel();
	JPanel panelClass = new JPanel();
	JPanel panelLogout = new JPanel();
	JTextField nomP = new JTextField(20);
	JTextField passP = new JTextField(20);
	JTextField matiereP = new JTextField(20);
	JTextField nomE = new JTextField(20);
	JTextField passE = new JTextField(20);
	JComboBox comboClass;
	JComboBox comboProf;
	JButton addP = new JButton("Créer");
	JButton addC = new JButton("Créer");
	JButton addE = new JButton("Créer");
	JButton logout = new JButton("Se déconnecter");
	
	public ViewAdmin() throws IOException {
		this.ad = new Admin();
		setVisible(true);
		setSize(700, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		setTitle("APPLICATION E_LEARNING");
		setLocation(200, 70);
		setLayout(new GridLayout(7, 1));
		
		String[] listeP = ad.listeProfs();
		comboProf = new JComboBox(listeP);
		String[] listeC = ad.listeClass();
		comboClass = new JComboBox(listeC);
		
		newProf.setForeground(Color.BLUE.darker());
		newProf.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		newEtud.setForeground(Color.BLUE.darker());
		newEtud.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		newClass.setForeground(Color.BLUE.darker());
		newClass.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		add(newProf);
		
		panelProf.setLayout(new GridLayout(4, 1));
		panelProf.setBorder(new EmptyBorder(0,20,0,20));
		panelProf.add(nomProf);
		panelProf.add(nomP);
		panelProf.add(passProf);
		panelProf.add(passP);
		panelProf.add(matiere);
		panelProf.add(matiereP);
		panelProf.add(addP);
		add(panelProf);
		b = false;
		panelProf.setVisible(b);
		newProf.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				b = !b;
				panelProf.setVisible(b);
			}
		});
		
		panelClass.setLayout(new GridLayout(2, 1));
		panelClass.setBorder(new EmptyBorder(0,20,0,20));
		add(newClass);
		panelClass.add(prof);
		panelClass.add(comboProf);
		panelClass.add(addC);
		add(panelClass);
		panelClass.setVisible(b);
		newClass.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				b = !b;
				panelClass.setVisible(b);
			}
		});
		
		panelEtud.setLayout(new GridLayout(4, 1));
		panelEtud.setBorder(new EmptyBorder(0,20,0,20));
		add(newEtud);
		panelEtud.add(nomEtud);
		panelEtud.add(nomE);
		panelEtud.add(passEtud);
		panelEtud.add(passE);
		panelEtud.add(classe);
		panelEtud.add(comboClass);
		panelEtud.add(addE);
		add(panelEtud);
		panelEtud.setVisible(b);
		newEtud.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				b = !b;
				panelEtud.setVisible(b);
			}
		});
		
		panelLogout.setLayout(new GridLayout(4, 1));
		panelLogout.setBorder(new EmptyBorder(20,90,0,90));
		panelLogout.add(logout);
		add(panelLogout);
		
		
		addP.addActionListener((ae) -> {
			String nom = nomP.getText();
			String pass = passP.getText();
			String matiere = matiereP.getText();
			ad.newProf(nom, pass, matiere);
		});
		
		addC.addActionListener((ae) -> {
			String prof = (String) comboProf.getSelectedItem();
			ad.newClass(prof);
		});
		
		addE.addActionListener((ae) -> {
			String nom = nomE.getText();
			String pass = passE.getText();
			String clss = (String) comboClass.getSelectedItem();
			ad.newEtud(nom, pass, clss);
		});
		
		logout.addActionListener((ae) -> {
			setVisible(false);
			new View1();
		});
	}
	
	public static void main(String[] args) throws IOException {
		
	}
}
