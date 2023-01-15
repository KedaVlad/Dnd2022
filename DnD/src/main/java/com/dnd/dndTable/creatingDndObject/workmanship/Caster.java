package com.dnd.dndTable.creatingDndObject.workmanship;

import com.dnd.Log;
import com.dnd.botTable.Action;
import com.dnd.dndTable.creatingDndObject.workmanship.casts.Cast;
import com.dnd.dndTable.creatingDndObject.workmanship.casts.Summon;

public class Caster 
{
 
	public static Action cast(Cast cast)
	{
		if(cast instanceof Summon)
		{
		return null;
		}
		Log.add("Cast error");
		return null;
	}
	
	
}
