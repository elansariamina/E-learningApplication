package e_learning;

import java.io.IOException;
import java.net.*;

public class Etudiant {
	DatagramSocket socket;
	String login;
	
	
	
	public Etudiant(String l) throws IOException {
		socket = new DatagramSocket();
		this.login = l;
	}
	
	
	//methode pour se connecter à l'application
		public String login(String login, String password, String who){
			sendToServeur("connexion,"+login+","+password+","+who+",");
			String reponse = receiveMessage(); 
			String res = "";
			String[] conv = reponse.split("-");
			System.out.println(conv[0]+" --- "+conv[1]);
			if(conv[0].equals("login")) {
				res = conv[1];
			}else;
			return res;
		}
		
		//
		public void logout() {
			sendToServeur("logout,"+login+",");
		}
		
		//methode pour recevoir du serveur
		public String receiveMessage(){
			byte[] b = new byte[1024];
			String conversation = "";
			DatagramPacket packet = new DatagramPacket(b, b.length);
			try {
				socket.receive(packet);
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
					socket.send(packetOut); 
				}catch(IOException e) {
					e.getStackTrace();
				}
			
		}
		
		public static void main(String[] args) throws IOException {
			
		}
}
