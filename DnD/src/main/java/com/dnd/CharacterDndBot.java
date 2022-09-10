package com.dnd;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.dnd.creatingCharacter.CharacterDnd;
import com.dnd.factory.CharacterFactory;
import com.dnd.factory.ClassFactory;
import com.dnd.factory.RaceFactory;

public class CharacterDndBot extends TelegramLongPollingBot implements KeyWallet,Source 
{




	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void onUpdateReceived(Update update) 
	{ 
		if(update.hasCallbackQuery())
		{
			try 
			{
				handleCallback(update.getCallbackQuery());
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}

		}
		else if(update.hasMessage()) 
		{
			try 
			{
				handleMessage(update.getMessage());
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}

		}		
	}


	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




	private void handleMessage(Message message) throws TelegramApiException 
	{
		if(message.hasText() && (MediatorWallet.checkMediator() == true))
		{
			hendlerMediator(message);	
		}
		else if(message.hasText() && (MediatorWallet.checkMediator() == false)) {

			if(message.hasText() && message.hasEntities()) 
			{
				Optional<MessageEntity> commandEntity =
						message.getEntities()
						.stream()
						.filter(e -> "bot_command".equals(e.getType())).findFirst();

				if (commandEntity.isPresent())
				{
					String comand = message.getText().substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
					switch (comand)
					{
					case "/start":
						startCase(message);
						return;
					case "/myCharacters":
						characterCase(message);
						return;
					}
				}
			}
		}
	}

	private void startCase(Message message) throws TelegramApiException
	{

		CharacterFactory.setMyCharactersDir("" + message.getChatId());
		MediatorWallet.mediatorBreak();
		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
		replyKeyboardMarkup.setOneTimeKeyboard(true);
		List<KeyboardRow> buttons = new ArrayList<>();
		KeyboardRow keyboardFirstRow = new KeyboardRow();
		keyboardFirstRow.add(new KeyboardButton("/myCharacters"));
		//KeyboardRow keyboardSecondRow = new KeyboardRow();
		//keyboardSecondRow.add(new KeyboardButton("/start"));


		buttons.add(keyboardFirstRow);
		//buttons.add(keyboardSecondRow);

		replyKeyboardMarkup.setKeyboard(buttons);
		execute(
				SendMessage.builder()
				.text("Greetings!\r\n" + "/myCharacters - This command leads to your character library, where you can create and choose which character you play.")
				.replyMarkup(replyKeyboardMarkup)
				.chatId(message.getChatId().toString())
				.build()
				);

	}

	private void characterCase(Message message) throws TelegramApiException 
	{

		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
		String[] myCreaterdCharacters = CharacterFactory.getMyCharactersDir().list();

		buttons.add(Arrays.asList(InlineKeyboardButton.builder()
				.text("CREATE")
				.callbackData(characterCreateKey)
				.build()));

		if(myCreaterdCharacters.length > 0) 
		{
			for(int i = 0; i < myCreaterdCharacters.length; i++)
			{
				buttons.add(Arrays.asList(InlineKeyboardButton.builder()
						.text(myCreaterdCharacters[i])
						.callbackData(characterKey + myCreaterdCharacters[i])
						.build()));
			}
			execute(
					SendMessage.builder()
					.text("Choose a Hero or CREATE new one.")
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build()
					);
		}
		else
		{
			execute(
					SendMessage.builder()
					.text("You don't have a Hero yet, my friend. But after you CREATE them, you can find them here.")
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build()
					);
		}
	}


	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	private void hendlerMediator(Message message)
	{
		String key = MediatorWallet.findMediator();

		switch(key)
		{

		case characterCreateKey:
			finishCharStartClass(message);
			return;

		case classKey:
			finishClassStartRace(message);
			return;

		}


		MediatorWallet.mediatorBreak();		
	}

	private void finishCharStartClass(Message message)
	{

		CharacterFactory.create(message.getText());
		List<List<InlineKeyboardButton>> buttons;

		try {
			buttons = MediatorWallet.getMediatorButtons(message);
			execute(SendMessage.builder()
					.text("What is your class, " + CharacterFactory.getActualGameCharacter().getName() + "?")
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build()
					);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

		MediatorWallet.mediatorBreak();		
	}

	private void finishClassStartRace(Message message)
	{
		int lvl = Integer.parseInt(message.getText().replaceAll(".*(\\d{1,2}).*", "$1"));
		List<List<InlineKeyboardButton>> buttons;
		try {
			if(lvl < 21 && lvl > 0)
			{
				CharacterFactory.getActualGameCharacter().setClassDnd(ClassFactory.create(lvl));;


				buttons = MediatorWallet.getMediatorButtons(message);
				execute(SendMessage.builder()
						.text("From what family you are?")
						.chatId(message.getChatId().toString())
						.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
						.build()
						);
			}
			else {
				CharacterFactory.getActualGameCharacter().setClassDnd(ClassFactory.create(1));;


				buttons = MediatorWallet.getMediatorButtons(message);
				execute(SendMessage.builder()
						.text(lvl+"??? I see you're new here. Let's start with lvl 1. The main thing now is not to miss with Race..." + CharacterFactory.getActualGameCharacter().getName() + "?")
						.chatId(message.getChatId().toString())
						.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
						.build()
						);

			}
		}catch (TelegramApiException e) {
			e.printStackTrace();
		}
		MediatorWallet.mediatorBreak();	
	}


	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	private void handleCallback(CallbackQuery callbackQuery) 
	{
		String key = callbackQuery.getData().replaceAll("(*[a-zA-Z]*) [a-zA-Z]", "$1");

		switch (key)
		{
		case characterCreateKey:
			creatHero(callbackQuery);
			return;
		case characterKey:
			downloadHero(callbackQuery);
			return;
		case classKey:
			chooseArchetype(callbackQuery);
			return;
		case archetypeKey:
			finishClass(callbackQuery);
			return;
		case raceKey:
			chooseSubRace(callbackQuery);
		case subRaceKey:
			finishHero(callbackQuery);
			return;

		}

	}

	private void creatHero(CallbackQuery callbackQuery)
	{
		MediatorWallet.setCharacterCreateMediator(true);
		try 
		{
			execute( SendMessage.builder()
					.text("Traveler, how should I call you?!\n(Write Hero name)")
					.build());
		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}


	}

	private void chooseArchetype(CallbackQuery callbackQuery)
	{
		ClassFactory.setClassBeck(callbackQuery.getData().replaceAll(""+classKey + "([a-zA-Z])", "$1"));
		Message message = callbackQuery.getMessage();
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		for(int i = 0; i < ClassFactory.getArchetypeArray().length; i++)
		{
			buttons.add(Arrays.asList(InlineKeyboardButton.builder()
					.text(ClassFactory.getArchetypeArray()[i].replaceAll("([a-zA-Z]).txt", "$1"))
					.callbackData(archetypeKey + ClassFactory.getArchetypeArray()[i])
					.build()));
		}
		try 
		{
			execute(
					SendMessage.builder()
					.text(ClassFactory.getClassBeck() + ", realy? Which one?")
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build());
		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}
	}

	private void chooseSubRace(CallbackQuery callbackQuery)
	{
		RaceFactory.setArcherypeBeck(callbackQuery.getData().replaceAll(""+raceKey + "([a-zA-Z])", "$1"));
		Message message = callbackQuery.getMessage();
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		for(int i = 0; i < RaceFactory.getSubRaceArray().length; i++)
		{
			buttons.add(Arrays.asList(InlineKeyboardButton.builder()
					.text(ClassFactory.getArchetypeArray()[i].replaceAll("([a-zA-Z]).txt", "$1"))
					.callbackData(subRaceKey + RaceFactory.getSubRaceArray()[i])
					.build()));
		}
		try 
		{
			execute(
					SendMessage.builder()
					.text(RaceFactory.getArcherypeBeck() + "? More specifically?")
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build());
		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}
	}

	private void downloadHero(CallbackQuery callbackQuery) 
	{
		if(CharacterFactory.getActualGameCharacter() != null)
		{
			CharacterFactory.create(callbackQuery.getData()); 
			characterMenu(callbackQuery);

		}
	}

	private void finishClass(CallbackQuery callbackQuery) 
	{

		ClassFactory.setArcherypeBeck(callbackQuery.getData());
		MediatorWallet.setClassCreateMediator(true);
		try 
		{
			execute(SendMessage.builder()
					.text("What LVL of Hero?")
					.build());
		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}


	}

	private void characterMenu(CallbackQuery callbackQuery) 
	{

	}

	private void finishHero(CallbackQuery callbackQuery)
	{
		CharacterFactory.getActualGameCharacter().setRaceDnd(RaceFactory.create(callbackQuery.getData().replaceAll(subRaceKey + "(a-zA-Z)","$1")));
		CharacterFactory.save(CharacterFactory.getActualGameCharacter().getName());
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	public String getBotUsername() 
	{
		return "@DndCharacterBot";
	}

	@Override
	public String getBotToken() 
	{
		return "5776409987:AAFh67JoMqwPJqt1daCgEqE9nbxnKl3GPKg";
	}

	public static void main(String[] args) 
	{



		CharacterDndBot bot = new  CharacterDndBot();
		TelegramBotsApi telegramBotsApi;
		try 
		{
			telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
			telegramBotsApi.registerBot(bot);
		}
		catch (TelegramApiException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}