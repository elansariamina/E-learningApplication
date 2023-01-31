package e_learning;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Admin {
	DatagramSocket adminSocket;
	
	public Admin() throws IOException {
		this.adminSocket = new DatagramSocket();
	}
	
	//methode pour creer un nouveau prof
	public void newProf(String login, String pass, String mat) {
		sendToServeur("newp,"+login+","+pass+","+mat+",");
	}
	
	//methode pour avoir liste des profs
	public String[] listeProfs() {
		sendToServeur("listeP,");
		String res = receiveMessage();
		String[] resF = res.split("-");
		return resF;
	}
	
	//methode pour creer une nouvelle classe
	public void newClass(String prof) {
		sendToServeur("newc,"+prof+",");
	}
	
	//methode pour avoir liste des classes
	public String[] listeClass() {
		sendToServeur("listeC,");
		String res = receiveMessage();
		String[] resF = res.split("-");
		return resF;
	}
	
	//methode pour creer un nouveau etudiant
	public void newEtud(String login, String pass, String classe) {
		sendToServeur("newe,"+login+","+pass+","+classe+",");
	}
	
	//methode pour recevoir du serveur
	public String receiveMessage(){
		byte[] b = new byte[1024];
		String conversation = "";
		DatagramPacket packet = new DatagramPacket(b, b.length);
		try {
			adminSocket.receive(packet);
			conversation = new String(packet.getData(), 0, packet.getLength());
			System.out.println("recu: " + conversation );
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conversation;
	}
	
	//methode d'envoi de données au serveur
	public void sendToServeur(String msg) {
			try {
				byte[] buffer = new byte[1024];
				InetAddress inetaddress = InetAddress.getByName("Localhost");
				String msgToSend = msg;
				buffer = msgToSend.getBytes();
				DatagramPacket packetOut = new DatagramPacket(buffer, buffer.length, inetaddress, 3000);
				adminSocket.send(packetOut); 
			}catch(IOException e) {
				e.getStackTrace();
			}
		
	}
}
