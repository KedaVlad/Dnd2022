package com.dnd.dndTable.factory;

import com.dnd.Log;
import com.dnd.botTable.Action;
import com.dnd.botTable.actions.FactoryAction;
import com.dnd.botTable.actions.FinalAction;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;

abstract class CharacterFactory 
{
	final static long key = 123456789;
	
	public static Action startCreate()
	{
		Log.add("characterHactory start create");
		String name = "CreateCharacter";
		String text = "Traveler, how should I call you?!\n(Write Hero name)";
		return FactoryAction.create(name, key, true, text, null);
	}

	public static Action execute(FactoryAction action)
	{
		Log.add("CharacterFactory.execute");
	
		
		switch(action.getLocalData().size())
		{
		case 1:
			return apruveAction(action);
		}
		return null;
	}
	
	private static Action apruveAction(FactoryAction action)
	{
		Log.add("CharacterFactory.apruveAction");
		
		action.setName("ApruveCharactersName");
		action.setMediator(false);
		action.setText("So can I call you - " + action.getLocalData().get(0) + "? If not, repeat your name.");
		action.setNextStep(new String[][] {{"Yeah, right"}});
		Log.add(action);
		Action answer = FinalAction.create(action);
		Log.add("bech 1" + FinalAction.create(action));
		Log.add("bich 2" + answer);
		return answer;
	}
	
	public static CharacterDnd finish(FinalAction action)
	{
		return new CharacterDnd((String)action.getLocalData().get(0));
	}
	
}


