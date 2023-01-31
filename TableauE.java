package e_learning;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.*;;

public class TableauE extends JFrame{
	int x;
	int y;
	boolean isTrue = true;
	Color color = Color.BLACK;
	
	JLabel label = new JLabel("Drag the mouse to draw");
	JPanel panel = new JPanel();
	
	
	public TableauE(String str) {
		setSize(600, 500);
		setLayout(new FlowLayout());
		setTitle("White Board: " + str);
		setLocation(300, 70);
		
		panel.add(label);
		
		panel.setPreferredSize(new Dimension(475, 500));
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		
		
			isTrue = true;
			panel.addMouseMotionListener(
				new MouseMotionAdapter() {
					// store drag coordinates and repaint
					public void mouseDragged( MouseEvent event ){
						
						repaint();
					}
				}
			);
		
		
		add(panel);
	}
	
	public void paint( Graphics g){
		if(isTrue) {
			super.repaint();
			g.setColor(color);
			g.fillOval( x, y, 5, 5 );
		}else {
			g.setColor(Color.WHITE);
			g.fillOval(x, y, 25, 25);
		}
	}
	
	public void setX(int xx) {
		this.x = xx;
	}
	public void setY(int yy) {
		this.y = yy;
	}
	public void setColor(Color c) {
		this.color = c;
	}
	public void setDraw(boolean b) {
		this.isTrue = b;
	}
	
	
	
	public static void main(String[] args) {
		new TableauE("n");

	}
	
}
