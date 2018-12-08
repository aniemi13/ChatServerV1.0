package com.niemiec.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerConsole {
	private BufferedReader reader;
	private MainWhileServer mainWhileServer;
	private String line = null;
	
	public ServerConsole() {
		this.reader = new BufferedReader(new InputStreamReader(System.in));
		this.mainWhileServer = new MainWhileServer(6666);
		this.mainWhileServer.start();
	}

	private void start() {
		log("Rozpoczęcie pracy servera...");
		while (true) {
			line = getLineFromConsole();
			showMenu(line);
		}
	}

	private void showMenu(String line) {
		if (line.equals("help")) {
			showHelp();
		} else if (line.equals("status")) {
			showStatus();
		} else if (line.equals("quit")) {
			shutdown();
		} else {
			log("Wpisz help, aby uzyskać więcej informacji.");
		}
	}
	
	private String getLineFromConsole() {
		String line = null;
		log(">");
		try {
			line = reader.readLine();
		} catch (IOException e) {
			log("Błąd wejścia/wyjścia: " + e);
		}
		return line;
	}

	private void showHelp() {
		String infoHelp = "status - wyświetla statystyki, quit - zakończenie pracy serwera";
		log(infoHelp);
	}

	private void showStatus() {
		// TODO Auto-generated method stub
		
	}

	private void shutdown() {
		// TODO Auto-generated method stub
		
	}
	
	public static void log(String info) {
		System.out.println(info);
	}

	public static void main(String[] args) {
		ServerConsole server = new ServerConsole();
		server.start();
	}

}
