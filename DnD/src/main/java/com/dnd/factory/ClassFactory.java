package com.dnd.factory;

import com.dnd.creatingCharacter.classDnd.ClassDnd;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.dnd.Source;
import com.dnd.creatingCharacter.classDnd.*;

public class ClassFactory implements Factory,Source 
{
	
	private static File dirClass = null;
	private static File dirArchetype = null;
	private static String classBeck = null;
	private static String archerypeBeck = null;

	public static ClassDnd create(int lvl) 
	{
		switch(getClassBeck())
		{
		case "Artificer":
		return refactor(new Rogue(lvl, getArcherypeBeck()));
		case "Barbarian":
		return refactor(new Barbarian(lvl, getArcherypeBeck()));
		case "Bard":
			return refactor(new Bard(lvl, getArcherypeBeck()));
		case "Blood Hunter":
			return refactor(new BloodHunter(lvl, getArcherypeBeck()));
		case "Cleric":
			return refactor(new Cleric(lvl, getArcherypeBeck()));
		case "Druid":
			return refactor(new Druid(lvl, getArcherypeBeck()));
		case "Fighter":
			return refactor(new Fighter(lvl, getArcherypeBeck()));
		case "Monk":
			return refactor(new Monk(lvl, getArcherypeBeck()));
		case "Rogue":
			return refactor(new Rogue(lvl, getArcherypeBeck()));
		case "Warlock":
			return refactor(new Warlock(lvl, getArcherypeBeck()));
		case "Wizard":
			return refactor(new Wizard(lvl, getArcherypeBeck()));
			
		}
		return null;
	}
	
	public static List<String> getWorkmanshipClass(ClassDnd classDnd)
	{
		List<String> pool = new ArrayList<>();
		Scanner classScanner = null;
		Pattern findNameSkills = Pattern.compile("\\*[a-zA-Z]*");
		Pattern findNameSpells = Pattern.compile(">[a-zA-Z]*");
		Pattern findNamePossession = Pattern.compile("-[a-zA-Z]*");
		try {
			classScanner = new Scanner(classDnd.getMyClassMainFile());
			boolean checkLvl = false;
			while(classScanner.hasNextLine()&& !checkLvl) {
				String workmanship = classScanner.nextLine();
				Matcher rightSkill = findNameSkills.matcher(workmanship);
				Matcher rightSpell = findNameSpells.matcher(workmanship);
				Matcher rightPossession = findNamePossession.matcher(workmanship);
				if(rightSkill.find()||rightSpell.find()||rightPossession.find()) 
				{
					pool.add(workmanship);

				} else if(workmanship.contains(""+classDnd.getLvl()+1)) {
					checkLvl = true;
				}
			} 
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} finally {
			classScanner.close();
		}
		return pool;
	}
	
	private static ClassDnd refactor(ClassDnd classDnd)
	{
		classDnd.setMainFile(new File(getClassBeck()));
		
		WorkmanshipFactory.getWorkmanship(getWorkmanshipClass(classDnd));
		
		return classDnd;
	}
	
	public static String[] getClassArray() 
	{
		dirClass = new File(classSource);
		String[] allClasses = dirClass.list();
		return allClasses;
	}
	
	public static String[] getArchetypeArray() 
	{
		dirArchetype = new File(classSource +"//"+ getClassBeck());
		String[] allArchetypes = dirArchetype.list();
		return allArchetypes;
	}

	

	public static String getClassBeck() {
		return classBeck;
	}

	public static void setClassBeck(String classBeck) {
		ClassFactory.classBeck = classBeck;
	}

	public static String getArcherypeBeck() {
		return archerypeBeck;
	}

	public static void setArcherypeBeck(String archerypeBeck) {
		ClassFactory.archerypeBeck = archerypeBeck;
	}
	

	

}
