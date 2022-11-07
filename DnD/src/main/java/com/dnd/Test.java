package com.dnd;

import java.io.IOException;
import java.util.Map;

import com.dnd.botTable.CharacterDndBot;
import com.dnd.botTable.GameTable;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.classDnd.ClassDnd;
import com.dnd.dndTable.creatingDndObject.classDnd.Rogue;
import com.dnd.dndTable.factory.InerComand;
import com.dnd.dndTable.factory.Json;

public class Test implements Source {

	String name;
	public Test(String name) {
	this.name = name;
	}
	
	
	public String toString()
	{
		return name;
	}

	public static String getName(Object object)
	{
		return object.toString();
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
CharacterDndBot bot = new CharacterDndBot();

	
	Json.backup(bot.getGameTable());
	System.out.println("0//////////////////////////////////////////////");
	
	}

}
