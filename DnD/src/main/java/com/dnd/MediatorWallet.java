package com.dnd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.dnd.factory.CharacterFactory;
import com.dnd.factory.ClassFactory;
import com.dnd.factory.RaceFactory;

public class MediatorWallet extends CharacterDndBot implements KeyWallet{


	private static boolean characterCreateMediator = false;
	private static boolean classCreateMediator = false;


	static boolean checkMediator()
	{

		if(isCharacterCreateMediator()) 
		{
			return true; 
		}
		else if(isClassCreateMediator())
		{
			return true;
		}

		return false;
	}

	static String findMediator() {

		if(isCharacterCreateMediator()) 
		{
			return characterCreateKey; 
		}
		else if(isClassCreateMediator())
		{
			return classKey;
		}
		return null;
	}

	static void mediatorBreak() 
	{
		if(isCharacterCreateMediator()) 
		{
			setCharacterCreateMediator(false);
		}
		else if(isClassCreateMediator())
		{
			setClassCreateMediator(false);
		}
	}



	static List<List<InlineKeyboardButton>> getMediatorButtons(Message message) 
	{
		if(isCharacterCreateMediator()) 
		{
			return finishCharStartClass(message);

		}
		else if(isClassCreateMediator())
		{
			return finishClassStartRace(message);
		}
		return null;
	}

	private static List<List<InlineKeyboardButton>> finishCharStartClass(Message message)
	{

		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		for(int i = 0; i < ClassFactory.getClassArray().length; i++)
		{
			buttons.add(Arrays.asList(InlineKeyboardButton.builder()
					.text(ClassFactory.getClassArray()[i])
					.callbackData(classKey + ClassFactory.getClassArray()[i])
					.build()));
		}		

		return buttons;
	}

	public static List<List<InlineKeyboardButton>> finishClassStartRace(Message message)
	{
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		for(int i = 0; i < RaceFactory.getRaceArray().length; i=+2)
		{
			if(!RaceFactory.getRaceArray()[i+1].equals(null)&&!RaceFactory.getRaceArray()[i+2].equals(null)) 
			{
				buttons.add(Arrays.asList(

						InlineKeyboardButton.builder()
						.text(RaceFactory.getRaceArray()[i])
						.callbackData(raceKey + RaceFactory.getRaceArray()[i])
						.build(),
						InlineKeyboardButton.builder()
						.text(RaceFactory.getRaceArray()[i+1])
						.callbackData(raceKey + RaceFactory.getRaceArray()[i+1])
						.build(),
						InlineKeyboardButton.builder()
						.text(RaceFactory.getRaceArray()[i+2])
						.callbackData(raceKey + RaceFactory.getRaceArray()[i+2])
						.build()));
			}
			else if(!RaceFactory.getRaceArray()[i+1].equals(null))
			{
				buttons.add(Arrays.asList(

						InlineKeyboardButton.builder()
						.text(RaceFactory.getRaceArray()[i])
						.callbackData(raceKey + RaceFactory.getRaceArray()[i])
						.build()));
			}
			else if(!RaceFactory.getRaceArray()[i+2].equals(null))
			{
				buttons.add(Arrays.asList(

						InlineKeyboardButton.builder()
						.text(RaceFactory.getRaceArray()[i])
						.callbackData(raceKey + RaceFactory.getRaceArray()[i])
						.build(),
						InlineKeyboardButton.builder()
						.text(RaceFactory.getRaceArray()[i+1])
						.callbackData(raceKey + RaceFactory.getRaceArray()[i+1])
						.build()));
			}

		}		

		return buttons;
	}





	public static boolean isCharacterCreateMediator() {
		return characterCreateMediator;
	}

	public static boolean setCharacterCreateMediator(boolean characterCreateMediator) {
		MediatorWallet.characterCreateMediator = characterCreateMediator;
		return characterCreateMediator;
	}

	public static boolean isClassCreateMediator() {
		return classCreateMediator;
	}

	public static void setClassCreateMediator(boolean classCreateMediator) {
		MediatorWallet.classCreateMediator = classCreateMediator;
	}


}
