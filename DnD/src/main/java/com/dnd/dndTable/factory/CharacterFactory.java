package com.dnd.dndTable.factory;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.dnd.Dice;
import com.dnd.Log;
import com.dnd.Source;
import com.dnd.Log.Place;
import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;

public class CharacterFactory implements Factory,Source{


	private static File myCharactersDir;

	public static CharacterDnd create(String name) {

		save(new CharacterDnd(name));
		return load(name);

	}
	
	public static CharacterDnd update(CharacterDnd characterDnd)
	{
		Log.add("update", Place.FACTORY, Place.CHARACTER);
		save(characterDnd);
		return load(characterDnd.getName());
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
	public static void save(CharacterDnd character)  
	{
		File file = new File(myCharactersDir +"\\" + character.getName());
		try {
			if(!file.exists()) 
			{
				file.createNewFile();
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(character);
				objectOutputStream.close();
			}
			else if(file.exists())
			{
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(character);
				objectOutputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void delete(CharacterDnd character)  
	{
		if(character == null) 
		{
			
		}
		else
		{
		File file = new File(myCharactersDir +"\\" + character.getName());
		try {
			if(file.exists())
			{
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		}

	}

	public static CharacterDnd load(String nameFile) 
	{
		CharacterDnd loadedCharacter = null;
		try 
		{

			FileInputStream fileInputStream = new FileInputStream(myCharactersDir +"\\" + nameFile);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			loadedCharacter = (CharacterDnd) objectInputStream.readObject();
			objectInputStream.close();

		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return loadedCharacter;
	}

	public static String getCharacterStatInfo(ObjectDnd objectDnd) {

		CharacterDnd characterInfo = (CharacterDnd)objectDnd;

		String godGift = Dice.d20() + ", " + Dice.d20() + ", " + Dice.d20() + ", " + Dice.d20() + ", " + Dice.d20() + ", " + Dice.d20();

		String answer = characterInfo.getName() + " is from the family of" 
				+ ", the " + characterInfo.getClassDnd() 
				+ "(" + characterInfo.getClassDnd().getMyArchetypeClass() + ") class.\r\n"
				+ "Now let's see what you have in terms of characteristics.\r\n"
				+ "\r\n"
				+ "Write the value of the characteristics in order: Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma.\r\n"
				+ "1.Each stat cannot be higher than 20.\r\n"
				+ "2. Write down stats without taking into account buffs from race / class.\r\n"
				+ "\r\n"
				+ "Use the random god gift in the order you want your stats to be.\r\n"
				+ "\r\n" + "\r\n" + godGift + "\r\n"
				+ "\r\n"
				+ "Or write down those values that are agreed with your game master.";

		return answer;
	}

	public static String getObgectInfo(ObjectDnd objectDnd) {

		CharacterDnd characterInfo = (CharacterDnd)objectDnd;

		String answer = characterInfo.getName() + " is from the family of" 
				+ ", the " + characterInfo.getClassDnd() 
				+ "(" + characterInfo.getClassDnd().getMyArchetypeClass() + ") class.\r\n";

		return answer;
	}

}
