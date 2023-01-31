package e_learning;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.jar.JarFile;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import exemple.Echo;

public class ViewEtudiant extends JFrame{
	JLabel label = new JLabel("Class Room:");
	JLabel labelFiles = new JLabel("Ressources");
	JPanel titre = new JPanel();
	JPanel panel = new JPanel();
	JPanel files = new JPanel();
	JButton logout = new JButton("Se déconnecter");
	JTextArea area;
	
	
	public ViewEtudiant(Etudiant e){
		
		setVisible(true);
		setSize(600, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		setTitle("APPLICATION E_LEARNING");
		setLocation(300, 70);
		
		area = new JTextArea(30, 30);
		panel.setBorder(new EmptyBorder(10,30,0,30));
		panel.setAlignmentY(BOTTOM_ALIGNMENT);
		titre.add(label);
		add(titre);
		panel.add(area);
		add(panel);
		files.add(labelFiles);
		add(files);
		add(logout);
		
		
		titre.setPreferredSize(new Dimension(100, 400));
		label.setLocation(10, 10);
		panel.setPreferredSize(new Dimension(300, 400));
		panel.setLocation(0, 150);
		files.setPreferredSize(new Dimension(150, 400));
		files.setLocation(310, 150);
		
		TableauE tab = new TableauE("Exercices");
		
		Thread pth = new Thread() {
			public void run() {
				while(true) {
					PartageInterface od;
					try {
						String url = "rmi://Localhost/irisi";
						od = (PartageInterface) Naming.lookup(url);
						if(od.getIsSend()) {
							JButton neww = new JButton("new file");
							neww.addActionListener((ae) -> {
								try {
									File f = od.getFileShared();
									f.setReadable(true);
									if(!Desktop.isDesktopSupported()){  
										System.out.println("not supported");  
										return;  
									}  
									Desktop desktop = Desktop.getDesktop();
									if(f.exists())         //checks file exists or not  
										desktop.open(f);   //opens the specified file
									System.out.println(od.getIsSend());
									neww.setText("Opened");
								}catch(Exception ex) {
									ex.getMessage();
								}
								System.out.println("Tout va bien");
							});
							files.add(neww);
								
							od.setIsSend(false);
						}
						if(od.getIsBoardSend()) {
							tab.setVisible(true);
							int x = od.getX();
							int y = od.getY();
							Color c = od.getColor();
							boolean b = od.getDraw();
							tab.setX(x);
							tab.setY(y);
							tab.setColor(c);
							tab.setDraw(b);
							tab.repaint();
							od.setIsBoardSend(false);
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		};
		pth.start();
		
		Thread th = new Thread(){
			public void run() {
				while(true) {
					String msg = e.receiveMessage();
					String[] tab = msg.split("-");
					area.append("Prof: "+tab[1]+"\n");
					System.out.println("Msg recu::: " + msg+", uuuu "+tab[1]);
				}
			}
		};
		th.start();
		
		
		
		logout.addActionListener((ae) -> {
			e.logout();
			setVisible(false);
			new View1();
		});
		
		
		
		
		
		
	}
	public static void main(String[] args) {
		
	}
}
