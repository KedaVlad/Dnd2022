package com.dnd;

public interface KeyWallet {

	///FOR CALLBACK///////////////////////////////////////////////////////////////////////////////////
	//MAIN KEYS
	final static String keyNumber = "[-]?[0-9]+(.[0-9]+)?";
	final static String keyCheck = "(>.+>)";
	final static String keyAnswer = "(.*)";
	final static String characterCaseKey = ">shktymhgccnie>";
	//CREATE CASE
	final static String characterCreateKey = ">asdzcrwvgylkjtr>";
	final static String startClassKey = ">regwrgvcewjklo98754zvwevdcvwev>";
	final static String chooseArchetypeClassKey = ">regwrgvczvwevdcvwev>";
	final static String finishClassKey = ">gfhsbnydynfregegvnh>";
	final static String startRaceKey = ">gfbvutynhgdbeverk9jsafds>";
	final static String chooseSubRaceKey = ">gfbvceebgnhjjsafds>";
	final static String finishRaceKey = ">dsgrnyukuwefdvhgvd>";
	final static String startStatsKey = ">dsgrnfinishHeroKeywefdvhgvd>";
	//SELECT
	final static String selectionConfirmation = ">Bdsaopl;,qfqwer[];'as./'>";
	//CHARACTER MENU
	final static String menuKey = ">regwrgvujfkeeflfewwopfcvwev>"; //in this key dose not use numbers
	final static String skillMenu = ">regwrgvcz23fewdsgwsgk7ujyh>";
	final static String spellMenu = ">wef3ggdsgs4htvdcvwev>";
	final static String bagMenu = ">regwr23ewfhlkijygrrgrgf4b4tev>";
	final static String possessionMenu = ">regwrasfafsdpossessionMenu3fewdsgk7ujyh>";
	final static String memoirsMenu = ">regwrgasdasdqwdwvmemoirsMenufewdsgk7ujyh>";
	final static String characterListMenu = ">regvcz23chawwwracterListMenufewdsgkgerregeg7ujyh>";
	
	///FOR MEDIATORS///////////////////////////////////////////////////////////////////////////////////
	//CREATE CASE
	final static String classMediatorKey = ">regwrgvczvwevdcvwevwewe>";
	final static String characterMediatorKey = ">shktymhgccniewewe>";
	final static String raceMediatorKey= ">ewefwewego6ll,ik5ujyhfdvhgvd>";
	final static String statMediatorKey = ">shktyasdvy5il;kjhfewe>";
	final static String hpMediatorKey = ">shktyawefwefasasdcvjwme>";

	///FOR SCRIPTS////////////////////////////////////////////////////////////////////////////////////
	
	final static String searchingScript = "&";
	final static String influencingScript = "@";
	final static String cloudScript = "~";
	
	final static String secondKey = ".(.)";
	
	final static String cloudPattern = "[a-zA-z ']+";
	final static String valueScript = ".*([1-5]).*";
	final static String workmanshipKey = "^(.)([a-zA-Z]*)";
	final static String spesialKey = "#";
	final static String itemkey = "*";
	final static String featureKey = "=";
	final static String spellKey = ">";
	final static String possessionKey = "<";
	final static String traitKey = "-";
	final static String statKey = "+";
	
	public static String allAfterKey(String key)
	{
		return key + "([a-zA-Z` ]*)";
	}
	
}
