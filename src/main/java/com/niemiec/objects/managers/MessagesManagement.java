package com.niemiec.objects.managers;

import com.niemiec.battleship.manager.BattleshipGamesManager;
import com.niemiec.objects.ClientThread;

public class MessagesManagement {
	private final String CHECK_NICK = "cn";
	private final String READY_TO_WORK = "rw";
	private final String BATTLESHIP_MESSAGE = "bs";
	private final String BATTLESHIP_QUESTION = "question";
	private final String BATTLESHIP_OK = "bsok";
	private final String BATTLESHUP_NO = "bsno";
	private final String NICK_EXIST = "exist";
	private final String PRIVATE_MESSAGE = "pm";
	private final String GROUP_MESSAGE = "gm";
	private final String EXIT = "exit";

	private ClientThread clientThread;
	private ClientThreadManager clientThreadManager;
	private BattleshipGamesManager battleshipGamesManager;
	private String message;
	private String typeOfMessage;
	private String senderNick;
	private String receiveNick;
	private String rightMessage;

	public MessagesManagement(ClientThread clientThread, ClientThreadManager clientThreadManager, BattleshipGamesManager battleshipGamesManager) {
		this.clientThread = clientThread;
		this.clientThreadManager = clientThreadManager;
		this.battleshipGamesManager = battleshipGamesManager;
	}

	public boolean recieveTheObject(Object object) {
		message = (String) object;
		separateMessage();

		if (typeOfMessage.equals(GROUP_MESSAGE)) {
			sendGroupMessage();
		} else if (typeOfMessage.equals(PRIVATE_MESSAGE)) {
			sendPrivateMessage();
		} else if (typeOfMessage.equals(BATTLESHIP_MESSAGE)) {
			playBattleship();
		} else if (typeOfMessage.equals(CHECK_NICK)) {
			checkNick();
		} else if (typeOfMessage.equals(READY_TO_WORK)) {
			clientThreadManager.sendAllClientThreadNickList();
		} else if (typeOfMessage.equals(EXIT)) {
			deleteClientThread();
			return false;
		}
		return true;
	}

	private void playBattleship() {
		// TODO Auto-generated method stub
		if (rightMessage.equals(BATTLESHIP_OK)) {
			//ktoś zatwierdził
		}
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
		} else {
			m = "/" + CHECK_NICK + "/" + NICK_EXIST;
		}
		sendTheObject(m);
	}

	private void separateMessage() {
		getTypeOfMessageFromMessage();

		if (typeOfMessage.equals(PRIVATE_MESSAGE) || typeOfMessage.equals(BATTLESHIP_MESSAGE)) {
			getInterlocutorNickFromMessage();
		}
	}

	private void getInterlocutorNickFromMessage() {
		String[] s = message.split("/", 5);
		receiveNick = s[3];
		rightMessage = s[4];
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
