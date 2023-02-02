package e_learning;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.io.IOException;
import java.net.SocketException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class View1 extends JFrame{
	JPanel panel2 = new JPanel();
	JPanel panel3 = new JPanel();
	JLabel label= new JLabel("S'authentifier", JLabel.CENTER);
	JLabel l = new JLabel("Login: ");
	JLabel p = new JLabel("Password: ");
	JLabel msg = new JLabel("", JLabel.CENTER);
	JTextField login = new JTextField(20);
	JPasswordField password = new JPasswordField(20);
	JButton signin = new JButton("Se connecter");
	
	public View1() {
		setVisible(true); 
		setSize(600, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		setTitle("APPLICATION E_LEARNING");
		setLocation(300, 70);
		setLayout(new GridLayout(5, 1));
		
		add(label);
		panel2.setLayout(new GridLayout(4,1));
		panel2.setBorder(new EmptyBorder(0,70,0,70));
		panel2.add(l);
		panel2.add(login); 
		panel2.add(p);
		panel2.add(password);
		add(panel2);
		add(msg);
		panel3.add(signin);
		add(panel3);
		
		
		signin.addActionListener( (ae) -> {
			String log = login.getText();
			String pass = password.getText();
			
			if(pass.startsWith("e")) {
				Etudiant e;
				try {
					e = new Etudiant(log);
					String rr = e.login(log, pass, "etudiant");
					if(rr.equals("true")) {
						setVisible(false);
						new ViewEtudiant(e);
					}else {
						login.setText("");
						password.setText("");
						msg.setText("Login or password incorrect!");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}else if(pass.startsWith("p")) {
				Professeur p;
				try {
					p = new Professeur(log);
					String rr = p.login(log, pass, "professeur");
					if(rr.equals("true")) {
						setVisible(false);
						new ViewProf(p);
					}else {
						login.setText("");
						password.setText("");
						msg.setText("Login or password incorrect!");
					}
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else if(pass.startsWith("a")){
				if(log.equals("admin") && pass.equals("a000")) {
					setVisible(false);
					try {
						new ViewAdmin();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					login.setText("");
					password.setText("");
					msg.setText("Login or password incorrect!");
				}
			}else {
				login.setText("");
				password.setText("");
				msg.setText("Login or password incorrect!");
			}
		});
	}
	
	
	
	public static void main(String[] args) {
		View1 v = new View1();
	}
}
