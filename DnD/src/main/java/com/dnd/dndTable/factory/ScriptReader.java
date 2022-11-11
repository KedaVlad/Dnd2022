package com.dnd.dndTable.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dnd.KeyWallet;
import com.dnd.Log;
import com.dnd.Names;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.workmanship.Feature;
import com.dnd.dndTable.creatingDndObject.workmanship.Possession;
import com.dnd.dndTable.creatingDndObject.workmanship.Spell;
import com.fasterxml.jackson.core.JsonProcessingException;

abstract class ScriptReader implements KeyWallet, Names
{

	static void execute(CharacterDnd character, InerComand comand)
	{

		Log.add("ScriptReader execute " + comand);
		if(comand.isCloud())
		{
			cloud(character, comand);
		}
		else 
		{
			influence(character, comand);
		}

	}

	private static void cloud(CharacterDnd character, InerComand comand)
	{
		if(comand.getKey().contains(statKey))
		{
			cloudStat(character, comand.getKey(), comand.getComand());
		}
		else if(comand.getKey().contains(workmanshipKey))
		{
			cloudWorkmanship(character, comand.getKey(), comand.getComand());
		}

	}


	protected static void cloudWorkmanship(CharacterDnd character, String key, List<List<Object>> comand)
	{
		int lifeTime = ((Integer) Integer.parseInt(comand.get(0).get(0).toString()));
		String term = comand.get(0).get(1).toString();
		List<Object> performans = comand.get(1);
		character.getCloud().setPerforman(key, lifeTime, term, performans);
	}

	protected static void cloudStat(CharacterDnd character, String key, List<List<Object>> comand)
	{

		int lifeTime = ((Integer) Integer.parseInt(comand.get(0).get(0).toString()));
		String term = comand.get(0).get(1).toString();
		List<Object> performans = comand.get(1);
		character.getCloud().setPerforman(key, lifeTime, term, performans);

	}

	private static void influence(CharacterDnd character, InerComand comand)
	{
		try {
			if(comand.getKey().contains(statKey))
			{
				character.getMyStat().buff(comand.getComand().get(0).get(1).toString(),
						(Integer)Integer.parseInt(comand.getComand().get(0).get(0).toString()));
			}
			else if(comand.getKey().contains(competenseKey))
			{
				character.getMyStat().buffCompetense(comand.getComand().get(0).get(1).toString());
			}
			else if(comand.getKey().contains(featureKey))
			{
				WorkmanshipFactory.createFeature(character, comand.getComand().get(0).get(0).toString());
			}	
			else if(comand.getKey().contains(spellKey))
			{
				WorkmanshipFactory.createSpell(character, comand.getComand().get(0).get(0).toString());
			}
			else if(comand.getKey().contains(possessionKey))
			{
				WorkmanshipFactory.createPossession(character, comand.getComand().get(0).get(0).toString());
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
