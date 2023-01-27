package com.dnd.dndTable.factory;

import com.dnd.ButtonName;
import com.dnd.KeyWallet;
import com.dnd.botTable.Act;
import com.dnd.botTable.actions.Action;
import com.dnd.botTable.actions.Action.Location;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;

abstract class CharacterFactory implements KeyWallet, ButtonName
{
	final static long KEY = CHARACTER_FACTORY_K;
	final static int END = 2;
	
	public static Act execute(Action action)
	{
		int condition = 0;
		if(action.getAnswers() != null) condition = action.getAnswers().length;
		switch(condition)
		{
		case 0:
			return startCreate();
		case 1:
			return apruveAction(action);
		}
		return null;
	}
	
	private static Act startCreate()
	{
		return Act.builder()
				.name("CreateCharacter")
				.text("Traveler, how should I call you?!\n(Write Hero name)")
				.action(Action.builder()
						.location(Location.FACTORY)
						.key(KEY)
						.mediator()
						.build())
				.returnTo(START_B)
				.build();
	}

	private static Act apruveAction(Action action)
	{
		action.setButtons(new String[][] {{"Yeah, right"}});
		return Act.builder()
				.name("ApruveCharactersName")
				.text("So can I call you - " + action.getAnswers()[0] + "? If not, repeat your name.")
				.action(action)
				.build();
	}
	
	public static CharacterDnd finish(Action action)
	{
		return CharacterDnd.create(action.getAnswers()[0]);
	}
	
}


