package com.dnd.dndTable.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dnd.KeyWallet;
import com.dnd.Log;
import com.dnd.Source;
import com.dnd.dndTable.creatingDndObject.*;
import com.dnd.dndTable.creatingDndObject.classDnd.ClassDnd;
import com.dnd.dndTable.creatingDndObject.raceDnd.RaceDnd;
import com.dnd.dndTable.creatingDndObject.workmanship.Feature;
import com.dnd.dndTable.creatingDndObject.workmanship.Possession;
import com.dnd.dndTable.creatingDndObject.workmanship.Spell;
import com.dnd.dndTable.creatingDndObject.workmanship.Trait;


abstract class WorkmanshipFactory implements Source, KeyWallet {

	private final static Map<String, Feature> features = Json.getFeatures();

	

	static void createFeature(CharacterDnd character, String skill) 
	{	
		if(!character.getWorkmanship().getMyFeatures().contains(new Feature(skill)))
		{
			character.getWorkmanship().getMyFeatures().add(features.get(skill));
			
			if(features.get(skill).getInerComands() != null)
			{
				for(InerComand comand: features.get(skill).getInerComands())
				ScriptReader.execute(character, comand);
			}
		}
	}

	static void createTrait(CharacterDnd character, String trait) 
	{
		if(!character.getWorkmanship().getMyFeatures().contains(new Trait(trait)))
		{
			character.getWorkmanship().getMyFeatures().add(new Trait(trait));
		}
	}

	static void createPossession(CharacterDnd character, String possession) 
	{
		if(!character.getWorkmanship().getMyPossessions().contains(new Possession(possession)))
		{
			character.getWorkmanship().getMyPossessions().add(new Possession(possession));
		}
	}

	static void createSpell(CharacterDnd character, String spell) 
	{
		if(!character.getWorkmanship().getMySpells().contains(new Spell(spell)))
		{
			character.getWorkmanship().getMySpells().add(new Spell(spell));
		}
	}

}