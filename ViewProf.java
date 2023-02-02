package e_learning;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ViewProf extends JFrame{
	
	JLabel mesEtud = new JLabel("Mes étudiants:");
	JLabel lab1 = new JLabel("qlq chose");
	JLabel lab2 = new JLabel("qlq chose");
	JLabel lab3 = new JLabel("qlq chose");
	JLabel lab4 = new JLabel("qlq chose");
	
	JPanel listet = new JPanel();
	JPanel espace = new JPanel();
	JPanel send = new JPanel();
	JPanel buttons = new JPanel();
	
	JTextField msg = new JTextField(20);
	JTextArea area = new JTextArea(30, 30);
	
	JButton butFich = new JButton("Fichiers");
	JButton butImg = new JButton("Images");
	JButton butTab = new JButton("T blanc");
	JButton butOth = new JButton("Autres");
	JButton sends = new JButton("Envoyer");
	JButton logout = new JButton("Déconnexion");
	public ViewProf(Professeur p) {
		setVisible(true);
		setSize(600, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		setTitle("APPLICATION E_LEARNING");
		setLocation(300, 70);
		 
		String res = p.mesEtudiantsConnectés(p.login);
		String[] liste = res.split(";");
		JList l = new JList(liste);
		
		listet.setBorder(BorderFactory.createLineBorder(Color.black));
		espace.setBorder(BorderFactory.createLineBorder(Color.black));
		send.setBorder(BorderFactory.createLineBorder(Color.black));
		buttons.setBorder(BorderFactory.createLineBorder(Color.black));
		
		listet.setPreferredSize(new Dimension(100, 400));
		listet.setLocation(0, 0);
		espace.setPreferredSize(new Dimension(300, 400));
		espace.setLocation(120, 0);
		buttons.setPreferredSize(new Dimension(120, 400));
		buttons.setLocation(440, 0);
		send.setPreferredSize(new Dimension(300, 50));
		send.setLocation(0, 400);
		
		
		listet.add(mesEtud);
		listet.add(l);
		espace.add(area);
		send.add(msg);
		send.add(sends);
		buttons.add(butFich);
		buttons.add(butImg);
		buttons.add(butTab);
		buttons.add(butOth);
		buttons.add(logout);
		
		
		add(listet, JPanel.LEFT_ALIGNMENT);
		add(espace, JPanel.TOP_ALIGNMENT);
		add(buttons, JPanel.RIGHT_ALIGNMENT);
		add(send, JPanel.BOTTOM_ALIGNMENT);
		
		sends.addActionListener((ae) -> {
			String message = msg.getText();
			p.sendMessage(message);
			area.append("You: "+message+"\n");
			msg.setText("");
		});
		
		butFich.addActionListener((ae) -> {
			JFileChooser f = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Files", "txt", "pdf", "doc");
			f.setFileFilter(filter);
			int ff = f.showSaveDialog(null);
			System.out.println(ff);
			if (ff != JFileChooser.APPROVE_OPTION) 
				System.out.println("different");
			else {
				System.out.println(f.getSelectedFile().toPath());
				String path = f.getSelectedFile().toPath().toString();
				System.out.println(path);
				p.partage(path);
				area.append("You send a file \n");
			}
		});
		
		butImg.addActionListener((ae) -> {
			JFileChooser f = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "png", "jpeg", "gif");
			f.setFileFilter(filter);
			int ff = f.showSaveDialog(null);
			System.out.println(ff);
			if (ff != JFileChooser.APPROVE_OPTION) 
				System.out.println("different");
			else {
				System.out.println(f.getSelectedFile().toPath());
				String path = f.getSelectedFile().toPath().toString();
				System.out.println(path);
				p.partage(path);
				area.append("You send a picture \n");
			}
		});
		
		butTab.addActionListener((ae) -> {
			p.partageTableau("Excercices");
		});
		
		butOth.addActionListener((ae) -> {
			JFileChooser f = new JFileChooser();
			int ff = f.showSaveDialog(null);
			System.out.println(ff);
			if (ff != JFileChooser.APPROVE_OPTION) 
				System.out.println("different");
			else {
				System.out.println(f.getSelectedFile().toPath());
				String path = f.getSelectedFile().toPath().toString();
				System.out.println(path);
				p.partage(path);
				area.append("You send an object \n");
			}
		});
		
		logout.addActionListener((ae) -> {
			p.logout();
			setVisible(false);
			new View1();
		});
	}
	
	public static void main(String[] args) {
		
	}
}
