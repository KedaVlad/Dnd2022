package com.dnd.dndTable.factory;

import java.util.List;


import com.dnd.KeyWallet;
import com.dnd.Log;
import com.dnd.Names;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;

abstract class ScriptReader implements Script, Names
{

	static void execute(CharacterDnd character, InerComand comand)
	{

		Log.add("ScriptReader execute " + comand);
		if(comand.isCloud() && comand.isEffect())
		{
			act(character, comand);
		}
		else if(comand.isCloud())
		{
			cloud(character, comand);
		}
		else if(comand.isEffect())
		{
			change(character, comand);
		}
		else
		{
			build(character, comand);
		}

	}

	private static void change(CharacterDnd character, InerComand comand) {
		
		
	}

	private static void act(CharacterDnd character, InerComand comand) {
		
		
	}

	private static void cloud(CharacterDnd character, InerComand comand)
	{
		if(comand.getKey().contains(lvlUpScript))
		{
			cloudStat(character, comand.getKey(), comand.getComand());
		}
		else
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

	private static void build(CharacterDnd character, InerComand comand)
	{
		try {
			if(comand.getKey().contains(statKey))
			{
				character.getRolls().up(Json.convertor(comand.getComand().get(0).get(1), Stat.class),
						(Integer)Integer.parseInt(comand.getComand().get(0).get(0).toString()));
			}
			else if(comand.getKey().contains(competenseKey))
			{
				character.getRolls().toCompetense(comand.getComand().get(0).get(1).toString());
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
