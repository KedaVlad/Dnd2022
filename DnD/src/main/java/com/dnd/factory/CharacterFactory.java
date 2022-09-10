package com.dnd.factory;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import com.dnd.Source;
import com.dnd.creatingCharacter.CharacterDnd;

public class CharacterFactory implements Factory,Source{


	private static File myCharactersDir;
	private static CharacterDnd actualGameCharacter;


	public static CharacterDnd create(String name) {

		save(name);
		load(name);

		return actualGameCharacter;

	}


	public static File getMyCharactersDir() {
		return myCharactersDir;
	}



	public static void setMyCharactersDir(String userName) {

		CharacterFactory.myCharactersDir = new File(userSource + userName);
		if(!myCharactersDir.exists()) {
			myCharactersDir.mkdir();
		}
	}

	//save and load
	public static void save(String characterName)  
	{
		if(CharacterFactory.getActualGameCharacter().equals(null)) 
		{
			actualGameCharacter = new CharacterDnd(characterName);
		}
		File file = new File(myCharactersDir +"\\" + actualGameCharacter.getName());
		try {
			if(!file.exists()) 
			{
				file.createNewFile();
				FileOutputStream fileOutputStream;

				fileOutputStream = new FileOutputStream(file);

				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(actualGameCharacter);
				objectOutputStream.close();
			}
			else if(file.exists() && (file.getName().equals(actualGameCharacter.getName())))
			{
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(actualGameCharacter);
				objectOutputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void load(String nameFile) 
	{
		if(CharacterFactory.getActualGameCharacter()!= null)
		{
			try 
			{
				CharacterFactory.save(actualGameCharacter.getName());
				FileInputStream fileInputStream = new FileInputStream(nameFile);
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
				actualGameCharacter = (CharacterDnd) objectInputStream.readObject();
				objectInputStream.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}



	}


	public static CharacterDnd getActualGameCharacter() {
		return actualGameCharacter;
	}


	public static void setActualGameCharacter(CharacterDnd actualGameCharacter) {
		CharacterFactory.actualGameCharacter = actualGameCharacter;
	}

}
