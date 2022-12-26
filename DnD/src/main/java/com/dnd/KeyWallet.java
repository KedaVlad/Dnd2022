package com.dnd;

import com.dnd.dndTable.DndKeyWallet;

public interface KeyWallet extends DndKeyWallet {

	final static long hp = 100000001;
	final static long menu = 100000002;
	final static long toMenu = 100000003;
	final static long characterCase = 100000004;
	final static long start = 1000000005;
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
