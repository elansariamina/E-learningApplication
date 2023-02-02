package e_learning;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.sql.*;
import java.util.*;

import javax.imageio.ImageIO;


public class Serveur {

	BDConnexion bd;
	Statement state;
	DatagramSocket socket;
	DatagramPacket packet;
	ArrayList<Session> listeSession;
	int port;
	PartageImpl obj;
	
	public Serveur(int port) throws SocketException, SQLException {
		this.port = port;
		this.socket = new DatagramSocket(port);
		listeSession = new ArrayList<Session>();
		bd = new BDConnexion();
		state = bd.etablirConnexion();
		
		try {
			obj = new PartageImpl();
			LocateRegistry.createRegistry(1099);
			Naming.rebind("irisi", obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	
	//
	public int idFromLogin(String login, String table) {
		String requete = "SELECT id FROM `"+table+"` WHERE `login` = '"+login+"'";
		int id = 0;
		try {
			ResultSet res = bd.getData(state, requete);
			while(res.next()) {
				id = res.getInt("id");
			}
			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	}
	
	//methode pour verifier l'authentification
		public String verifierLogin(String[] infos) throws SQLException {
			String login = infos[1];
			String password = infos[2];
			String who = infos[3];
			String requete = "SELECT * FROM `"+who+"` WHERE `login` = '"+login+"'";
			ResultSet res = bd.getData(state, requete);
			String tampon = "";
			while(res.next()) {
				//Si le password est correct, on enregistre le prof dans la session des personnes connectés
				if((res.getString("password")).equals(password)) {
					enregistrerSession(res.getString("login"));
					tampon = "true";
				}else {
					tampon = "false";
				}
			}
			if(tampon.equals("")) {
				tampon = "false";
			}
			return tampon;
		}
		
		//methode pour ajouter une ligne dans la table session
		public void enregistrerSession(String login) throws SQLException {
			InetAddress address = packet.getAddress();
			int port = packet.getPort();
			Session ses = new Session(login, address, port);
			listeSession.add(ses);
		}
		
		//methode pour creer un nouveau prof
		public String newProfes(String[] infos) {
			String login = infos[1];
			String password = infos[2];
			String matiere = infos[3];
			String requete = "INSERT INTO `professeur`(`login`, `password`, `matiere`) VALUES('"+login+"', '"+password+"', '"+matiere+"')";
			try {
				bd.setData(state, requete);
				return "true";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "false";
			}
		}
		
		//liste des professeurs
		public String listePs() {
			String requete = "SELECT login FROM `professeur`";
			String result = "";
			try {
				ResultSet res = bd.getData(state, requete);
				while(res.next()) {
					result += res.getString("login")+"-";
				}
				return result;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "rien";
			}
		}
		
		//methode pour creer un nouveau prof
		public String newClasse(String[] infos) {
			String prof = infos[1];
			int id = this.idFromLogin(prof, "professeur");
			String requete = "INSERT INTO `classe`(`id_prof`) VALUES('"+id+"')";
			try {
				bd.setData(state, requete);
				return "true";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "false";
			}
		}
		
		//liste des classes
		public String listeCs() {
			String requete = "SELECT num_cl FROM `classe`";
			String result = "";
			try {
				ResultSet res = bd.getData(state, requete);
				while(res.next()) {
					result += res.getInt("num_cl")+"-";
				}
				return result;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "rien";
			}
		}
		
		//methode pour creer nouveau etudiant
		public String newEtudiant(String[] infos) {
			String login = infos[1];
			String password = infos[2];
			String classe = infos[3];
			String requete = "INSERT INTO `etudiant`(`login`, `password`, `numCl`) VALUES('"+login+"', '"+password+"', '"+classe+"')";
			try {
				bd.setData(state, requete);
				return "true";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "false";
			}
		}
		
		//
		public String mesEtuds(String[] infos) {
			String login = infos[1];
			int idProf = this.idFromLogin(login, "professeur");
			String requete = "SELECT login FROM `etudiant`, `classe` WHERE `num_cl` = `numCl` AND `id_prof` = '"+idProf+"'";
			String result = "";
			System.out.println("res:: "+ result+", id:: "+ idProf);
			try {
				ResultSet res = bd.getData(state, requete);
				while(res.next()) {
					result += res.getString("login")+";";
				}
				System.out.println("res:: "+ result+", id:: "+ idProf);
				return result;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "rien";
			}
		}
		
		//
		public String mesEtudsC(String[] infos) {
			String mesEt = mesEtuds(infos);
			String[] res = mesEt.split(";");
			String etudiantsConnectes = " ";
			for(String e : res) {
				for(Session s : listeSession) {
					if(e.equals(s.login)) {
						etudiantsConnectes += e+";";
					}
				}
			}
			return etudiantsConnectes;
		}
		
		//methode pour transferer un message vers un client
		public String sendMsgs(String[] infos) {
			try {
				String message = infos[3];
				obj.msgShare(message);
				System.out.println("aaaa ," + obj.msgs.size());
	            System.out.println("messge distant est enregistrer");
	            obj.setIsMsgSend(true);
				return "true";
			}catch(Exception e) {
				e.getMessage();
				return "false";
			}
		}
		
		//
		public String partageFile(String[] infos) {
			try {
	            File file = new File(infos[1]);
	            obj.fileShare(file);
				System.out.println("aaaa ," + obj.files.size());
	            System.out.println("objet distant est enregistrer");
	            obj.setIsSend(true);
				System.out.println(obj.isSend);
	            return "true";
	        } catch (Exception e) {
	            e.getMessage();
	            return "false";
	        }
		}
		
		//
		public String partageBoard(String[] infos) {
			try {
				Tableau t = new Tableau(infos[1]);
				while(t.isShowing()) {
					obj.boardShare(t);
					obj.setIsBoardSend(true);
				}
				System.out.println("objet distant est enregistrer");
	            return "true";
	        } catch (Exception e) {
	            e.getMessage();
	            return "false";
	        }
		}
		
		//
		public String deconnexion(String[] infos) {
			String nom = infos[1];
			System.out.println("----------- " + listeSession.size());
			for(int i=0; i< listeSession.size(); i++) {
				System.out.println("ssss " + listeSession.get(i).login + " >>> " + nom);
				if(listeSession.get(i).login.equalsIgnoreCase(nom)) {
					listeSession.remove(i);
				}
			}
			System.out.println("----------- " + listeSession.size());
			return "true";
		}

		//methodes d'envoi et reception entre le client et le serveur
		public void receiveAndSend() throws SQLException  {
			try {
				byte[] tampon = new byte[1024];
				packet = new DatagramPacket(tampon, tampon.length);
				socket.receive(packet);
				InetAddress adres = packet.getAddress();
				int port = packet.getPort();
				String msg = new String(packet.getData(), 0, packet.getLength());
				System.out.println("Le message envoyé est : " + msg);
				String[] tabMsg = msg.split(",");
				String tomp = "";
				switch(tabMsg[0]){
				case "connexion":
					tomp = "login-"+verifierLogin(tabMsg)+"-";
					break;
				case "newp":
					tomp = "np-"+newProfes(tabMsg)+"-";
					break;
				case "listeP":
					tomp = listePs();
					break;
				case "newc":
					tomp = "nc-"+newClasse(tabMsg)+"-";
					break;
				case "listeC":
					tomp = listeCs();
					break;
				case "newe":
					tomp = "ne-"+newEtudiant(tabMsg)+"-";
					break;
				case "mesEtudiants":
					tomp = "mesEt-"+mesEtuds(tabMsg)+"-";
					break;
				case "connectes":
					tomp = "mesEtCn-"+mesEtudsC(tabMsg)+"-";
					break;
				case "envoi":
					tomp = "chat-"+sendMsgs(tabMsg)+"-";
					break;
				case "partage":
					tomp = "prtg-"+this.partageFile(tabMsg)+"-";
					break;
				case "board":
					tomp = "brd-"+this.partageBoard(tabMsg)+"-";
					break;
				case "logout":
					tomp = "lgt-"+this.deconnexion(tabMsg)+"-";
				default:
					break;
				}
				System.out.println(tomp);
			byte[] tabByte = tomp.getBytes();
				System.out.println("Adresse du sender: " + adres);
				System.out.println("port du sender: " + port);
			DatagramPacket packetIn = new DatagramPacket(tabByte, tabByte.length, adres, port);
			socket.send(packetIn);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
		
		public static void main(String[] args) throws SocketException, SQLException {
			Serveur s = new Serveur(3000);
			while(true) {
				s.receiveAndSend();
			}
		}
}


//messagerie avec RMI  //uuuu
//password connu son utiliser les choix  //uuuu
//ne pas utiliser button pour recevoir les fichiers, utiliser lien 
//essayer de disabler les panels f admin frame  //uuuu
//ajouter le fait de modifier et supprimer
//reecrire le rapport
//la conception