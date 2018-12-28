package com.niemiec.objects;

import java.net.Socket;

import com.niemiec.battleship.manager.BattleshipGamesManager;
import com.niemiec.connections.InputOutputStream;
import com.niemiec.objects.managers.ClientThreadManager;
import com.niemiec.objects.managers.MessagesManagement;

public class ClientThread extends Thread {
	private String nick;
	private InputOutputStream inputOutputStream;
	private MessagesManagement messagesManagement;

	public ClientThread(Socket socket, ClientThreadManager clientThreadManager, BattleshipGamesManager battleshipGamesManager) {
		this.nick = null;
		this.inputOutputStream = new InputOutputStream(socket);
		this.messagesManagement = new MessagesManagement(this, clientThreadManager, battleshipGamesManager);
	}

	@Override
	public void run() {
		Object object = null;
		while (true) {
			object = inputOutputStream.receiveTheObject();
			if (!messagesManagement.recieveTheObject(object)) {
				break;
			}
		}
		interrupt();
	}
	
	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNick() {
		return nick;
	}
	
	public void sendTheObject(Object object) {
		inputOutputStream.sendTheObject(object);
	}
	
	public void interrupt() {
		inputOutputStream.closeInputOutputStream();
		super.interrupt();
	}
}
