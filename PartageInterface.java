package e_learning;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PartageInterface extends Remote{
	
	public boolean getIsSend() throws RemoteException;
	public void setIsSend(boolean b) throws RemoteException;
	
	public boolean getIsBoardSend() throws RemoteException;
	public void setIsBoardSend(boolean b) throws RemoteException;
	
	public void fileShare(File file) throws RemoteException;
	public File getFileShared() throws RemoteException;
	
	public void imageShare(Image img) throws RemoteException;
	public Image getImageShared() throws RemoteException;
	
	public void tableauShare(Tableau t) throws RemoteException;
	public Tableau getTableauShared() throws RemoteException;
	
	public void boardShare(Tableau t) throws RemoteException;
	public int getX() throws RemoteException;
	public int getY() throws RemoteException;
	public Color getColor() throws RemoteException;
	public boolean getDraw() throws RemoteException;
}
