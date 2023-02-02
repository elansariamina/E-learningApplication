package e_learning;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class Tableau extends JFrame{
	int x;
	int y;
	JLabel label = new JLabel("Drag the mouse to draw");
	JPanel panel = new JPanel();
	JPanel outils = new JPanel();
	JButton draw = new JButton("Draw"); 
	JButton colors = new JButton("Colors");
	JButton delete = new JButton("Clear");
	JButton remove = new JButton("ClearAll");
	boolean isTrue;
	Color color = Color.BLACK;
	int i = 0;
	
	


	public Tableau(String str) {
		setVisible(true);
		setSize(600, 500);
		setLayout(new FlowLayout());
		setTitle("White Board: " + str);
		setLocation(300, 70);
		
		panel.add(label);
		
		panel.setPreferredSize(new Dimension(475, 500));
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		outils.setPreferredSize(new Dimension(100, 500));
		outils.setBorder(BorderFactory.createLineBorder(Color.black));
		outils.add(draw);
		outils.add(colors);
		outils.add(delete);
		outils.add(remove);
		
		
		draw.addActionListener((ae) -> {
			isTrue = true;
			panel.addMouseMotionListener(
			new MouseMotionAdapter() {
			// store drag coordinates and repaint
			public void mouseDragged( MouseEvent event )
			{
			x = event.getX();
			y = event.getY();
			repaint();
			}
			}
		);
		});
		
		delete.addActionListener((ae) -> {
			isTrue = false;
			panel.addMouseMotionListener(
					new MouseMotionAdapter() {
					// store drag coordinates and repaint
					public void mouseDragged( MouseEvent event )
					{
					x = event.getX();
					y = event.getY();
					repaint();
					}
					}
				);
		});
		
		colors.addActionListener((ae) -> {
			Color[] couleurs = {Color.GREEN, Color.RED, Color.BLUE, Color.ORANGE, Color.BLACK};
			color = couleurs[i];
			if(i==4)
				i=0;
			else i++;
			
		});
		
		
		
		add(panel);
		add(outils);
	}
	
	public void paint( Graphics g){
		
		if(isTrue) {
			super.repaint();
			g.setColor(color);
			g.fillOval( x, y, 5, 5 );
		}else {
			g.clearRect(x, y, 25, 25);
		}
		
	}
	
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public boolean getIsTrue() {
		return isTrue;
	}
	public Color getColor() {
		return color;
	}
	
	
	public static void main(String[] args) {
		new Tableau("n");

	}
	
}
