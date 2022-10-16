package com.dnd.dndTable.factory;

import com.dnd.KeyWallet;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;

abstract class ScriptReader implements KeyWallet
{

	static void execute(CharacterDnd character, String comand)
	{
		if(comand.contains(featureKey))
		{
			search(character, comand);
		}
		else if(comand.contains(influencingScript))
		{
			influence(character, comand);
		}
	
	}
	
	private static void search(CharacterDnd character, String comand)
	{
		if(comand.contains(featureKey))
		{
			WorkmanshipFactory.createFeatures(character, comand);
		}
	}
	
	private static void influence(CharacterDnd character, String comand)
	{
		
	}
	
}
