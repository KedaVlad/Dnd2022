package com.dnd.dndTable.factory;

import java.util.List;


import com.dnd.KeyWallet;
import com.dnd.Log;
import com.dnd.Names;
import com.dnd.botTable.actions.CloudAction;
import com.dnd.dndTable.DndKeyWallet;
import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.bagDnd.Items;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponProperties;
import com.dnd.dndTable.creatingDndObject.workmanship.Possession;
import com.dnd.dndTable.creatingDndObject.workmanship.Spell;
import com.dnd.dndTable.creatingDndObject.workmanship.features.Feature;
import com.dnd.dndTable.creatingDndObject.workmanship.features.InerFeature;
import com.dnd.dndTable.factory.inerComands.AddComand;
import com.dnd.dndTable.factory.inerComands.CloudComand;
import com.dnd.dndTable.factory.inerComands.InerComand;
import com.dnd.dndTable.factory.inerComands.ProfComand;
import com.dnd.dndTable.factory.inerComands.UpComand;

abstract class ScriptReader implements DndKeyWallet, Names
{

	static void execute(CharacterDnd character, InerComand comand)
	{
		if(comand instanceof AddComand)
		{
			add(character, (AddComand) comand);
		}
		else if(comand instanceof ProfComand)
		{
			prof(character, (ProfComand) comand);
		}
		else if(comand instanceof UpComand)
		{
			up(character,(UpComand) comand);
		}
		else if(comand instanceof CloudComand)
		{
			cloud(character,(CloudComand) comand);
		}
	}


	private static void cloud(CharacterDnd character, CloudComand comand) 
	{
		character.getCloud().add(CloudAction.create(comand.getName(), comand.getText()));
		
	}


	private static void up(CharacterDnd character, UpComand comand) 
	{
		long key = comand.getKey();
		if(key == STAT)
		{
			character.getRolls().up(comand.getName(), comand.getValue());
		}
		
	}


	private static void add(CharacterDnd character, AddComand comand) 
	{
		ObjectDnd[] objects = comand.getTarget();
		for(ObjectDnd object: objects)
		{
			if(object instanceof Items)
			{
				character.getBody().getMyBags().get(0).add((Items) object);
			}
			else if(object instanceof Feature)
			{
				character.getWorkmanship().addFeature((Feature) object);
				if(object instanceof InerFeature)
				{
					InerFeature target = (InerFeature) object;
					for(InerComand inerComand: target.getComand())
					{
					execute(character, inerComand);
					}
				}
			}
			else if(object instanceof Possession)
			{
				Possession target = (Possession) object;
				character.getWorkmanship().getPossessions().add(target);
			}
		}
	}

	private static void prof(CharacterDnd character, ProfComand comand)
	{
		long key = comand.getKey();
		String target = comand.getTarget();
		if(key == STAT)
		{
			
		}
	}

}
