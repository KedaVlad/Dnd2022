package com.dnd.dndTable.factory;

import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.workmanship.Spell;
import com.dnd.dndTable.creatingDndObject.workmanship.magicEffects.Buff;
import com.dnd.dndTable.creatingDndObject.workmanship.magicEffects.Damage;
import com.dnd.dndTable.creatingDndObject.workmanship.magicEffects.Effect;
import com.dnd.dndTable.creatingDndObject.workmanship.mechanics.Mechanics;

public abstract class Caster
{
	public enum CastStatus
	{
		ACTION, END, TIMESTATUS
	}

	public static CastStatus execute(CharacterDnd character, Effect effect)
	{
		if(effect instanceof Damage)
		{
			Damage action = (Damage) effect;
			character.getRolls().setTargetAct(action.getAction());
			return CastStatus.ACTION;
		}
		else if(effect instanceof Buff)
		{
			Buff action = (Buff) effect;
			
			if(character.getTimesBuffs().contains(effect.getName()))
			{
				InerComand comand = action.getCast();
				comand.setBack(true);
				ScriptReader.execute(character, comand);
				return CastStatus.END;
			}
			else
			{
				character.getTimesBuffs().add(effect.getName());
				ScriptReader.execute(character, action.getCast());
				return CastStatus.TIMESTATUS;
			}
		}
		return CastStatus.END;
	}
}
