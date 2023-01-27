package com.dnd.dndTable.creatingDndObject.workmanship;


import com.dnd.botTable.Act;
import com.dnd.botTable.actions.Action;
import com.dnd.dndTable.creatingDndObject.workmanship.casts.Cast;
import com.dnd.dndTable.creatingDndObject.workmanship.casts.Summon;

public class Caster 
{
	public static Act cast(Cast cast)
	{
		if(cast instanceof Summon)
		{
		
		}
		return Act.builder().name("DeadEnd").text("Not ready").build();
	}
	
	
}
