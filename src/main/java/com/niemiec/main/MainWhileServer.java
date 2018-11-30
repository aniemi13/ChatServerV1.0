package com.niemiec.main;

import java.io.IOException;
import java.net.ServerSocket;

public class MainWhileServer extends Thread {
	private ServerSocket serverSocket = null;
	private Server server;
	private int port;

	public MainWhileServer(Server server, int port) {
		this.server = server;
		this.port = port;
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
	}

}
