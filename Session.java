package e_learning;

import java.net.InetAddress;

public class Session {
	String login;
	InetAddress address;
	int port;
	
	public Session(String l, InetAddress a, int p) {
		this.login = l;
		this.address = a;
		this.port = p;
	}
}
