package com.dnd.dndTable.factory;

import java.io.Serializable;

import com.dnd.botTable.Action;
import com.dnd.botTable.GameTable;
import com.dnd.botTable.actions.BotAction;
import com.dnd.botTable.actions.FactoryAction;
import com.dnd.botTable.actions.FinalAction;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.rolls.Dice;

public class ControlPanel implements Serializable {

	private static final long serialVersionUID = -231330030795056098L;
	private static final long key = 231330038;
	
 	public Action createHero()
	{
 		return CharacterFactory.startCreate();
	}
	
	public Action act(FactoryAction action)
	{
		if(action.getKey() == CharacterFactory.key)
		{
			return CharacterFactory.execute(action);
		}
		else if(action.getKey() == ClassFactory.key)
		{
			return ClassFactory.execute(action);
		}
		else if(action.getKey() == RaceFactory.key)
		{
			return RaceFactory.execute(action);
		}
		else
		{
		return null;
		}
	}
	
	public Action readiness(CharacterDnd character)
	{
		if(character.getClassDnd() == null)
		{
			return ClassFactory.startCreate(character.getName());
		}
		else if(character.getMyRace() == null)
		{
			return RaceFactory.startCreate();
		}
		else if(character.getHp() == 0)
		{
			return finish();
		}
		else
		{
			return null;
		}
	}

	public Action finish(FinalAction action, GameTable gameTable)
	{
		long key = action.getKey();
		if(key == CharacterFactory.key)
		{
			gameTable.setActualGameCharacter(CharacterFactory.finish(action));
			gameTable.save();
			return ClassFactory.startCreate(action.getLocalData().get(0));
		}
		else if(key == ClassFactory.key)
		{
			ClassFactory.finish(action, gameTable.getActualGameCharacter());
			gameTable.save();
			return RaceFactory.startCreate();
		}
		else if(key == RaceFactory.key)
		{
			RaceFactory.finish(action, gameTable.getActualGameCharacter());
			gameTable.save();
			return finish();
		}
		return null;
	}
	
	private Action finish()
	{
		String name = "ChooseStat";
		String godGift = Dice.d20() + ", " + Dice.d20() + ", " + Dice.d20() + ", " + Dice.d20() + ", " + Dice.d20() + ", " + Dice.d20();

		String text =  "Now let's see what you have in terms of characteristics.\r\n"
				+ "\r\n"
				+ "Write the value of the characteristics in order: Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma.\r\n"
				+ "1.Each stat cannot be higher than 20.\r\n"
				+ "2. Write down stats without taking into account buffs from race / class.\r\n"
				+ "\r\n"
				+ "Use the random god gift in the order you want your stats to be.\r\n"
				+ "\r\n" + godGift + "\r\n"
				+ "\r\n"
				+ "Or write down those values that are agreed with your game master.\r\n"
				+ "Examples:\r\n"
				+ " str 11 dex 12 con 13 int 14 wis 15 cha 16\r\n"
				+ " 11 12 13 14 15 16 \r\n"
				+ " 11/12/13/14/15/16";
		
		return BotAction.create(name, getKey(), true, true, text, null);
	}
	
	public static long getKey() 
	{
		return key;
	}
	
}
