package e_learning;

import java.io.IOException;
import java.net.*;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import exemple.EchoImpl;

public class Professeur {
	DatagramSocket socket;
	String login;
	String receivers;
	
	
	public Professeur(String l) throws SocketException {
		this.login = l;
		socket = new DatagramSocket();
		receivers = mesEtudiants(login);
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
			
			//methode pour avoir la liste des étudiants d'un prof
			public String mesEtudiants(String login) {
				sendToServeur("mesEtudiants,"+login+",");
				String reponse = receiveMessage();
				String res = "";
				String[] conv = reponse.split("-");
				System.out.println(conv[0]+" --- "+conv[1]);
				if(conv[0].equals("mesEt")) {
					res = conv[1];
				}else;
				return res;
			}
			
			//
			public String mesEtudiantsConnectés(String login) {
				sendToServeur("connectes,"+login+",");
				String reponse = receiveMessage();
				String res = "";
				String[] conv = reponse.split("-");
				System.out.println(conv[0]+" --- "+conv[1]);
				if(conv[0].equals("mesEtCn")) {
					res = conv[1];
				}else;
				return res;
			}
			
			//methode pour envoyer un message
			public void sendMessage(String message) {
				sendToServeur("envoi,"+login+","+receivers+","+message+",");
			}
			
			//
			public void partage(String str) {
				sendToServeur("partage,"+str+",");
			}
			
			//
			public void partageTableau(String str) {
				sendToServeur("board,"+str+",");
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
			
			public static void main(String[] args) throws SocketException {
				
			}
}
