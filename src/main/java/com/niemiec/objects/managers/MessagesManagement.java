package com.niemiec.objects.managers;

import com.niemiec.objects.ClientThread;

public class MessagesManagement {
	private final String CHECK_NICK = "cn";
	private final String NICK_EXIST = "exist";
	private final String PRIVATE_MESSAGE = "pm";
	private final String GROUP_MESSAGE = "gm";
	private final String EXIT = "exit";

	private ClientThread clientThread;
	private ClientThreadManager clientThreadManager;
	private String message;
	private String typeOfMessage;
	private String senderNick;
	private String receiveNick;

	public MessagesManagement(ClientThread clientThread, ClientThreadManager clientThreadManager) {
		this.clientThread = clientThread;
		this.clientThreadManager = clientThreadManager;
	}

	public boolean recieveTheObject(Object object) {
		message = (String) object;
		separateMessage();

		if (typeOfMessage.equals(GROUP_MESSAGE)) {
			sendGroupMessage();
		} else if (typeOfMessage.equals(PRIVATE_MESSAGE)) {
			sendPrivateMessage();
		} else if (typeOfMessage.equals(CHECK_NICK)) {
			checkNick();		
		} else if (typeOfMessage.equals(EXIT)) {
			deleteClientThread();
			return false;
		}
		return true;
	}

	private void deleteClientThread() {
		clientThreadManager.removeClientThread(senderNick);
	}

	private void sendPrivateMessage() {
		clientThreadManager.sendTheObject(receiveNick, message);
	}

	private void sendGroupMessage() {
		clientThreadManager.sendTheObjectAll(message);
	}

	private void checkNick() {
		String m;
		if (clientThreadManager.add(senderNick, clientThread)) {
			m = "/" + CHECK_NICK + "/" + "notexist";
			sendTheObject(m);
			clientThreadManager.sendAllClientThreadNickList();
		} else {
			m = "/" + CHECK_NICK + "/" + NICK_EXIST;
			sendTheObject(m);
		}
	}

	private void separateMessage() {
		getTypeOfMessageFromMessage();

		if (typeOfMessage.equals(PRIVATE_MESSAGE)) {
			getNickAndInterlocutorNickAndRightMessageFromMessage();
		}
	}

	private void getNickAndInterlocutorNickAndRightMessageFromMessage() {
		String[] s = message.split("/", 5);
		receiveNick = s[3];
	}

	private void getTypeOfMessageFromMessage() {
		String[] s = message.split("/", 3);
		typeOfMessage = s[1];
		senderNick = s[2];
	}

	private void sendTheObject(Object object) {
		clientThread.sendTheObject(object);
	}

}
