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


	private static File myCharactersDir = null;
	private static CharacterDnd actualGameCharacter = null;
	private static boolean chekHero = false;


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
		File file = new File(myCharactersDir +"\\" + characterName);
		try {
			if(!file.exists()) 
			{
				file.createNewFile();
				FileOutputStream fileOutputStream;

				fileOutputStream = new FileOutputStream(file);

				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(new CharacterDnd(characterName));
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

	public static CharacterDnd load(String nameFile) 
	{
		
		
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		try 
		{
			
			/*if(CharacterFactory.getActualGameCharacter()!= null)
			{*/

				//CharacterFactory.save(actualGameCharacter.getName());
				fileInputStream = new FileInputStream(myCharactersDir +"\\" +nameFile);
				objectInputStream = new ObjectInputStream(fileInputStream);
				return (CharacterDnd) objectInputStream.readObject();
				
			//}
			/*else if(CharacterFactory.getActualGameCharacter() == null) 
			{
				FileInputStream fileInputStream = new FileInputStream(myCharactersDir +"\\" +nameFile);
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
				actualGameCharacter = (CharacterDnd) objectInputStream.readObject();
				objectInputStream.close();
			}*/
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				objectInputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
return null;
	}


	public static CharacterDnd getActualGameCharacter() {
		return actualGameCharacter;
	}


	public static void setActualGameCharacter(CharacterDnd actualGameCharacter) {
		CharacterFactory.actualGameCharacter = actualGameCharacter;
	}


	public static boolean isChekHero() {
		return chekHero;
	}


	public static void setChekHero(boolean chekHero) {
		CharacterFactory.chekHero = chekHero;
	}

}