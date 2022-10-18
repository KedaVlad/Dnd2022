package com.dnd.dndTable.factory;

import com.dnd.KeyWallet;
import com.dnd.Log;
import com.dnd.Log.Place;
import com.dnd.Names;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;

abstract class ScriptReader implements KeyWallet, Names
{

	static void execute(CharacterDnd character, String comand)
	{
		
		if(comand.contains(searchingScript))
		{
			search(character, comand);
		}
		else if(comand.contains(influencingScript))
		{
			influence(character, comand);
		}
		else if(comand.contains(cloudScript))
		{
			cloud(character, comand);
		}
	
	}
	
	private static void search(CharacterDnd character, String comand)
	{
		
		if(comand.contains(featureKey))
		{
			WorkmanshipFactory.createFeature(character, comand);
		}
		else if(comand.contains(spellKey))
		{
			WorkmanshipFactory.createSpell(character, comand);
		}
		else if(comand.contains(possessionKey))
		{
			WorkmanshipFactory.createPossession(character, comand);
		}
		else if(comand.contains(traitKey))
		{
			WorkmanshipFactory.createTrait(character, comand);
		}
		
	}
	
	private static void influence(CharacterDnd character, String comand)
	{
		if(comand.contains(statKey))
		{
			influenceStat(character, comand);
		}
		
	}
	
	private static void cloud(CharacterDnd character, String comand)
	{
		
	}
	
	private static void influenceStat(CharacterDnd character, String comand)
	{
		int value =  (int) Integer.parseInt(comand.replaceAll(valueScript, "$1"));
		
		for(int i = 0; i < character.getMyStat().getSaveRolls().size(); i++)
		{
			character.getMyStat().getSaveRolls().get(i).
		}
		
	}
	
}
