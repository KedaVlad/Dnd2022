package com.dnd.dndTable.factory;

import java.util.List;


import com.dnd.KeyWallet;
import com.dnd.Log;
import com.dnd.Names;
import com.dnd.botTable.actions.CloudAction;
import com.dnd.dndTable.DndKeyWallet;
import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.MagicSoul;
import com.dnd.dndTable.creatingDndObject.bagDnd.Items;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponProperties;
import com.dnd.dndTable.creatingDndObject.modification.AttackModification;
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
			else if(object instanceof MagicSoul)
			{
				MagicSoul target = (MagicSoul) object;
				character.getWorkmanship().setMagicSoul(target);
			}
			else if(object instanceof Spell)
			{
				Spell target = (Spell) object;
				if(character.getWorkmanship().getMagicSoul() != null)
				{
					if(target.getLvlSpell() == 0)
					{
						character.getWorkmanship().getMagicSoul().getPoolCantrips().add(target);
					}
					else
					{
						character.getWorkmanship().getMagicSoul().getPool().add(target);
					}
				}
			}
			else if(object instanceof AttackModification)
			{

				AttackModification target = (AttackModification) object;
				if(target.isPostAttack())
				{
					if(character.getAttackMachine().getAfterAttak().contains(target))
					{
						character.getAttackMachine().getAfterAttak().remove(target);
						character.getAttackMachine().getAfterAttak().add(target);
					}
					else
					{
						character.getAttackMachine().getAfterAttak().add(target);
					}
				}
				else if(target.isPermanent())
				{
					if(character.getAttackMachine().getPermanent().contains(target))
					{
						character.getAttackMachine().getPermanent().remove(target);
						character.getAttackMachine().getPermanent().add(target);
					}
					else
					{
						character.getAttackMachine().getPermanent().add(target);
					}
				}
				else
				{
					if(character.getAttackMachine().getPreAttacks().contains(target))
					{
						character.getAttackMachine().getPreAttacks().remove(target);
						character.getAttackMachine().getPreAttacks().add(target);
					}
					else
					{
						character.getAttackMachine().getPreAttacks().add(target);
					}
				}
			}
		}
	}


}
