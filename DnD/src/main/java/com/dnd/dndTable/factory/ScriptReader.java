package com.dnd.dndTable.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dnd.KeyWallet;
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
		/*if(comand.contains(statKey) && comand.contains(spesialKey))
		{
			character.getMyStat().spesialize(comand);
		}
		if(comand.contains(statKey))
		{
			character.getMyStat().buff(comand);
		}*/
		
	}


	
	private static void cloud(CharacterDnd character, String comand)
	{
		if(comand.contains(statKey))
		{
			cloudStat(character, comand);
		}
	}
	
	protected static void cloudStat(CharacterDnd character, String comand)
	{
		int value =  (int) Integer.parseInt(comand.replaceAll(valueScript, "$1"));
		List<String> cloud = new ArrayList<>();
		
		Pattern pattern = Pattern.compile(cloudPattern);
		Matcher matcher = pattern.matcher(comand);
		
		while(matcher.find())
		{
			cloud.add(matcher.group());
		}
		
	character.getCloud().setPerformans(comand.replaceAll(secondKey, "$1") + value, cloud);
	}

	
}
