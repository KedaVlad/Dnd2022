package com.dnd;

import com.dnd.dndTable.DndKeyWallet;

public interface KeyWallet extends DndKeyWallet {

	final static long start = 0000000000;
	final static long hp = 000000001;
	final static long menu = 000000002;
	final static long toMenu = 000000003;
	final static long characterCase = 000000004;
	final static long eliminationKey = 666666666;
	
	final static long buttonsKey = 254123765;
	
	final static String startText ="/myCharacters - This command leads to your character library,"
			+ " where you can create and choose which character you play.\n";
	
	
	
	///FOR CALLBACK///////////////////////////////////////////////////////////////////////////////////
	//MAIN KEYS
	final static String keyNumber = "[-]?[0-9]+(.[0-9]+)?";
	final static String keyCheck = "(>.+>)";
	final static String keyAnswer = "(.*)";

}
