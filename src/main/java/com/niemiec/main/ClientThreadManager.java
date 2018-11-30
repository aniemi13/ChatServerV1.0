package com.niemiec.main;

import java.net.Socket;
import java.util.ArrayList;

public class ClientThreadManager {
	private ArrayList<Client> clients;
	
	public ClientThreadManager() {
		clients = new ArrayList<>();
	}

	public void add(Socket socket) {
		clients.add(new Client(socket));
	}

}
