package com.niemiec.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.niemiec.battleship.manager.BattleshipGamesManager;
import com.niemiec.objects.ClientThread;
import com.niemiec.objects.managers.ClientThreadManager;

public class MainWhileServer extends Thread {
	private ServerSocket serverSocket = null;
	private int port;
	private ClientThreadManager clientThreadManager;
	private BattleshipGamesManager battleshipGamesManager;

	public MainWhileServer(int port) {
		this.port = port;
		clientThreadManager = new ClientThreadManager();
		battleshipGamesManager = new BattleshipGamesManager();
		createServerSocket();
	}

	private void createServerSocket() {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			ServerConsole.log("Błąd tworzenia polączenia głownego: " + e);
		}
	}

	@Override
	public void run() {
		Socket socket = null;
		ServerConsole.log("Jestem w metodzie run");
		while (true) {
			socket = getSocketFromClient();
			ServerConsole.log("Nadeszło nowe połączenie...");
			ClientThread clientThread = new ClientThread(socket, clientThreadManager, battleshipGamesManager);
			clientThread.start();
			ServerConsole.log("Dodano nowego klienta: " + clientThread);
		}
	}

	private Socket getSocketFromClient() {
		try {
			return serverSocket.accept();
		} catch (IOException e) {
			ServerConsole.log("Błąd tworzenia gniazda");
			return null;
		}
	}

	public void interrupt() {
		super.interrupt();
		
		try {
			serverSocket.close();
		} catch (IOException e) {
		}
	}
}
