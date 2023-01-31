package e_learning;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.ColorModel;
import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class PartageImpl extends UnicastRemoteObject implements PartageInterface{

	private static final long serialVersionUID = 1L;
	List<File> files = new ArrayList<File>();
	List<Image> images = new ArrayList<Image>();
	public Tableau board;
	public int x, y;
	public Color color;
	public boolean draw;
	public boolean isSend;
	public boolean isBoardSend;
	


	protected PartageImpl() throws RemoteException {
		super();
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean getDraw() {
		return draw;
	}

	public void setDraw(boolean draw) {
		this.draw = draw;
	}

	public File getFile() {
		return files.get(files.size()-1);
	}

	public void setFile(File file) {
		this.files.add(file);
	}
	

	public void setImage(Image image) {
		this.images.add(image);
	}
	
	public Image getImage() {
		return this.images.get(images.size()-1);
	}

	public Tableau getBoard() {
		return board;
	}

	public void setBoard(Tableau board) {
		this.board = board;
	}
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}


	public void fileShare(File file) {
		setFile(file);
	}
	public File getFileShared() {
		return getFile();
	}
	public void imageShare(Image image) {
		setImage(image);
	}
	public Image getImageShared() {
		return getImage();
	}
	public void tableauShare(Tableau t) {
			setBoard(t);
	}
	public Tableau getTableauShared() {
		
		return getBoard();
	}
	public void boardShare(Tableau t) {
		int x = t.getX();
		int y = t.getY();
		Color c = t.getColor();
		boolean b = t.getIsTrue();
		setXY(x, y);
		setColor(c);
		setDraw(b);
	}
	public boolean getIsSend() {
		return isSend;
	}
	public void setIsSend(boolean b) {
		this.isSend = b;
	}
	public boolean getIsBoardSend() {
		return isBoardSend;
	}
	public void setIsBoardSend(boolean b) {
		this.isBoardSend = b;
	}
}
