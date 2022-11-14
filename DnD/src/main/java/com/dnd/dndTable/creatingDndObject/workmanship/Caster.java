package com.dnd.dndTable.creatingDndObject.workmanship;

import com.dnd.dndTable.creatingDndObject.CharacterDnd;

public class Caster
{

	public static String execute(CharacterDnd character, Feature feature)
	{
		if(feature.getClass().equals(Mechanics.class))
		{
			
		}
		else if(feature.isActive())
		{
			compleater(character, feature.getCast());
		}
		
		return null;
	}
	
	public static void execute(CharacterDnd character, Spell spell)
	{
		
	}
	
	private static void compleater(CharacterDnd character, Cast cast)
	{
		
	}
}
