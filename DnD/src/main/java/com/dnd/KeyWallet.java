package com.dnd;

import com.dnd.dndTable.DndKeyWallet;

public interface KeyWallet extends DndKeyWallet {

	final static long hp = 100000001;
	final static long menu = 100000002;
	final static long toMenu = 100000003;
	final static long createOrDelete = 100000004;
	final static long start = 1000000005;
	final static long downloadHero = 100000006;
	final static long rolls = 100000007;
	final static long toRolls = 100000008;
	
	final static long eliminationKey = 666666666;
	final static long buttonsKey = 254123765;
	
	final static String startText ="/characters - This command leads to your character library,"
			+ " where you can create and choose which character you play.\n";
	
	final static String rollsText = "Choose a dice to throw, or write your own formula.\n"
			+ "To refer to a dice, use the D(or d) available dices you see in the console.\n"
			+ "For example: -d4 + 10 + 6d6 - 12 + d100";
	
	
	
	///FOR CALLBACK///////////////////////////////////////////////////////////////////////////////////
	//MAIN KEYS
	final static String keyNumber = "[-]?[0-9]+(.[0-9]+)?";
	final static String keyCheck = "(>.+>)";
	final static String keyAnswer = "(.*)";

}
