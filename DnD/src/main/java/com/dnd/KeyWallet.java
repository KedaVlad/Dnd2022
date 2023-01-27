package com.dnd;


public interface KeyWallet {

	public final static long NO_ANSWER = 101101001;
	public final static long CHARACTER = 110000000;
	
	public final static long STUFF = 350000000;
	public final static long CHARACTERISTIC = 360000000;
	public final static long ABILITY = 370000000;
	public final static long DEBUFF = 380000000;
	public final static long MEMOIRS = 390000000;
	public final static long REST = 390020000;
	public final static long ATTACK_MACHINE = 390022000;
	final static long ControlPanel = 101230001;
	final static long hp = 100000001;
	final static long menu = 100000002;
	final static long toMenu = 100000003;
	final static long create = 100000004;
	final static long start = 1000000005;
	final static long downloadHero = 100000006;
	final static long rolls = 100000007;
	final static long toRolls = 100000008;
	final static long STAT = 100000009;
	
	final static long CLOUD_ACT = 666666666;
	final static long MAIN_TREE = 254123765;
	
	
	final static long CHARACTER_FACTORY_K = create;
	final static long CLASS_FACTORY_K = 200000001;
	final static long RACE_FACTORY_K = 200000002;
	final static long ITEM_FACTORY_K = 200000003;
	
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
