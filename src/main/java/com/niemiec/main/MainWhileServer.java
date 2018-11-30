package com.niemiec.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainWhileServer extends Thread {
	private ServerSocket serverSocket = null;
	private Server server;
	private int port;
	private ClientThreadManager clientThreadManager;

	public MainWhileServer(Server server, int port, ClientThreadManager clientThreadManager) {
		this.server = server;
		this.port = port;
		this.clientThreadManager = clientThreadManager;
		createServerSocket();
	}
	
	private void createServerSocket() {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			server.log("Błąd tworzenia polączenia głownego: " + e);
		}
	}

	public void run() {
		server.log("Jestem w metodzie run");
		Socket socket = getSocketFromServerSocket();
		clientThreadManager.add(socket);
	}

	private Socket getSocketFromServerSocket() {
		try {
			return serverSocket.accept();
		} catch (IOException e) {
			server.log("Błąd tworzenia gniazda");
			return null;
		}
	}

}
