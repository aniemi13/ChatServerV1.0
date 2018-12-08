package com.niemiec.objects.managers;

import java.util.ArrayList;

import com.niemiec.objects.ClientThread;

public class ClientThreadManager {
	private ArrayList<ClientThread> clientThreadList;
	private ArrayList<String> clientThreadNickList;
	
	public ClientThreadManager() {
		clientThreadList = new ArrayList<>();
		clientThreadNickList = new ArrayList<>();
	}
	
	public synchronized void sendTheObject(String nick, Object object) {
		getClientThread(nick).sendTheObject(object);
	}
	
	public synchronized void sendAllClientThreadNickList() {
		sendTheObjectAll(clientThreadNickList.clone());
	}
	
	public synchronized void sendTheObjectAll(Object object) {
		for (ClientThread c : clientThreadList) {
			c.sendTheObject(object);
		}
	}

	public synchronized void removeClientThread(String nick) {
		ClientThread c = getClientThread(nick);
		clientThreadList.remove(c);
		clientThreadNickList.remove(nick);
		sendAllClientThreadNickList();
	}

	public synchronized boolean add(String nick, ClientThread clientThread) {
		if (getClientThread(nick) != null)
			return false;
		clientThread.setNick(nick);
		clientThreadList.add(clientThread);
		clientThreadNickList.add(nick);
		return true;
	}
	
	private synchronized ClientThread getClientThread(String nick) {
		for (ClientThread c : clientThreadList) {
			if (c.getNick().equals(nick))
				return c;
		}
		return null;
	}
}
