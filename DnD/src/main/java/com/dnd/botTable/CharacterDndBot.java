package com.dnd.botTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
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

import com.dnd.Dice;
import com.dnd.KeyWallet;
import com.dnd.Log;
import com.dnd.Log.Place;
import com.dnd.Source;
import com.dnd.botTable.TrashCan.Circle;
import com.dnd.dndTable.creatingDndObject.skills.Feature;
import com.dnd.dndTable.factory.CharacterFactory;
import com.dnd.dndTable.factory.ClassFactory;
import com.dnd.dndTable.factory.RaceFactory;

public class CharacterDndBot extends TelegramLongPollingBot implements KeyWallet,Source 
{

	private GameTable gameTable = new GameTable();

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void onUpdateReceived(Update update) 
	{ 

		clean(gameTable.getTrashCan().getCircle(Circle.SMALL));


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
		else if(update.hasMessage() && (gameTable.getMediatorWallet().checkMediator() == true)) 
		{

			try 
			{

				hendlerMediator(update.getMessage());

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

	private void clean(List<Integer> list)
	{
		if(!list.isEmpty() && gameTable.getChatId() != 0) {
			for(int i = 0; i < list.size(); i++)
			{   
				try {
					execute(DeleteMessage.builder().chatId(gameTable.getChatId()).messageId(list.get(i)).build());
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}

			}
		}

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void handleMessage(Message message) throws TelegramApiException ///<-
	{

		gameTable.getTrashCan().toMainСircle(message.getMessageId());

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
					clean(gameTable.getTrashCan().getCircle(Circle.ALL));
					startCase(message);
					Log.add("/start", Place.BOT, Place.COMMAND);
					return;
				case "/myCharacters":
					clean(gameTable.getTrashCan().getCircle(Circle.MAIN));
					characterCase(message);
					Log.add("/myCharacters", Place.BOT, Place.COMMAND);
					return;
				}
			}
		}
		else
		{
			Log.add("try add Memoir", Place.BOT, Place.COMMAND);
			if(gameTable.isChekChar())
			{
				gameTable.getActualGameCharacter().setMyMemoirs(message.getText());
				Message toTrash = execute(SendMessage.builder()
						.text("I will put it in your memoirs")
						.chatId(message.getChatId().toString())
						.build()
						);
				gameTable.getTrashCan().toSmallСircle(message.getMessageId());
				gameTable.getTrashCan().toSmallСircle(toTrash.getMessageId());
			}
			else
			{
				Message toTrash = execute(SendMessage.builder()
						.text("Until you have an active hero, you have no memoirs. Therefore, unfortunately, this message recognize oblivion.")
						.chatId(message.getChatId().toString())
						.build()
						);
				gameTable.getTrashCan().toSmallСircle(message.getMessageId());
				gameTable.getTrashCan().toSmallСircle(toTrash.getMessageId());
			}
		}
	}

	private void startCase(Message message) throws TelegramApiException
	{
		gameTable.setChatId(message.getChatId());
		CharacterFactory.setMyCharactersDir("" + gameTable.getChatId());

		gameTable.getMediatorWallet().mediatorBreak();
		gameTable.setChekChar(false);
		clean(gameTable.getTrashCan().getCircle(Circle.ALL));

		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
		replyKeyboardMarkup.setOneTimeKeyboard(true);
		List<KeyboardRow> buttons = new ArrayList<>();
		KeyboardRow keyboardFirstRow = new KeyboardRow();
		keyboardFirstRow.add(new KeyboardButton("/myCharacters"));

		buttons.add(keyboardFirstRow);

		replyKeyboardMarkup.setKeyboard(buttons);
		replyKeyboardMarkup.setResizeKeyboard(true);
		execute(
				SendMessage.builder()
				.text("Greetings!\r\n" + "/myCharacters - This command leads to your character library,"
				+ " where you can create and choose which character you play.")
				.replyMarkup(replyKeyboardMarkup)
				.chatId(message.getChatId().toString())
				.build()
				);

	}

	private void characterCase(Message message) throws TelegramApiException 
	{

		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		buttons.add(Arrays.asList(InlineKeyboardButton.builder()
				.text("CREATE")
				.callbackData(characterCreateKey)
				.build()));

		if(!CharacterFactory.getMyCharactersDir().list().equals(null)) 
		{
			String[] myCreaterdCharacters = CharacterFactory.getMyCharactersDir().list();
			for(int i = 0; i < myCreaterdCharacters.length; i++)
			{
				buttons.add(Arrays.asList(InlineKeyboardButton.builder()
						.text(myCreaterdCharacters[i])
						.callbackData(characterCaseKey + myCreaterdCharacters[i])						
						.build()));
			}
			Message toTrash = execute(
					SendMessage.builder()
					.text("Choose a Hero or CREATE new one.")
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build()
					);
			gameTable.getTrashCan().toMainСircle(toTrash.getMessageId());
		}
		else if(CharacterFactory.getMyCharactersDir().list().equals(null))
		{
			Message toTrash = execute(
					SendMessage.builder()
					.text("You don't have a Hero yet, my friend. But after you CREATE them, you can find them here.")
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build()
					);
			gameTable.getTrashCan().toMainСircle(toTrash.getMessageId());
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void hendlerMediator(Message message) ///<-
	{
		String key = gameTable.getMediatorWallet().findMediator();

		switch(key)
		{

		case characterMediatorKey:

			finishCharacter(message);
			Log.add("finishCharacter", Place.BOT, Place.MEDIATOR, Place.CREATING);
			return;

		case classMediatorKey:

			finishClass(message); 
			Log.add("finishClass", Place.BOT, Place.MEDIATOR, Place.CREATING);
			return;

		case statMediatorKey:

			finishStat(message);
			Log.add("finishStat", Place.BOT, Place.MEDIATOR, Place.CREATING);
			return;

		case hpMediatorKey:

			finishHp(message);
			Log.add("finishHp", Place.BOT, Place.MEDIATOR, Place.CREATING);
			return;


		}
	}

	private void finishCharacter(Message message)
	{
		if(gameTable.isChekChar() == false) CharacterFactory.delete(gameTable.getActualGameCharacter());
		gameTable.setActualGameCharacter(CharacterFactory.create(message.getText()));
		gameTable.getActualGameCharacter().setMyMemoirs(gameTable.getActualGameCharacter().getName() + "\n\n");
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		buttons.add(Arrays.asList(InlineKeyboardButton.builder()
				.text("Yes that's right")
				.callbackData(startClassKey)
				.build()));

		try 
		{
			Message botAnswer = execute(SendMessage.builder()
					.text("So can I call you - " + gameTable.getActualGameCharacter().getName() + "? If not, repeat your name.")
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build()
					);
			gameTable.getTrashCan().toSmallСircle(message.getMessageId());
			gameTable.getTrashCan().toSmallСircle(botAnswer.getMessageId());
		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}

		gameTable.update();
	}

	private void finishClass(Message message)
	{
		int lvl = 0;
		Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
		Matcher matcher = pat.matcher(message.getText());
		while (matcher.find()) 
		{
			lvl = ((Integer) Integer.parseInt(matcher.group()));
		}


		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
		buttons.add(Arrays.asList(InlineKeyboardButton.builder()
				.text("Yes that's right")
				.callbackData(startRaceKey)
				.build()));

		try 
		{
			if(lvl < 1 || lvl > 20)
			{

				gameTable.getCuttingBoard().setClassLvl(1);
				gameTable.createClass();

				Message botAnswer = execute(SendMessage.builder()
						.text(lvl+"??? I see you're new here. Let's start with lvl 1. Are you satisfied with this option? If not,"
								+ " select another option above." + "\r\n"+ ClassFactory.getObgectInfo(gameTable.getActualGameCharacter().getClassDnd()))
						.chatId(message.getChatId().toString())
						.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
						.build()
						);
				gameTable.getTrashCan().toSmallСircle(message.getMessageId());
				gameTable.getTrashCan().toSmallСircle(botAnswer.getMessageId());

			}
			else
			{

				gameTable.getCuttingBoard().setClassLvl(lvl);
				gameTable.createClass();

				Message botAnswer = execute(SendMessage.builder()
						.text("Are you satisfied with this option? If not, select another option above." + "\r\n" 
								+ ClassFactory.getObgectInfo(gameTable.getActualGameCharacter().getClassDnd()))
						.chatId(message.getChatId().toString())
						.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
						.build()
						);

				gameTable.getActualGameCharacter().setMyMemoirs(ClassFactory.getObgectInfo(gameTable.getActualGameCharacter().getClassDnd()));
				gameTable.getTrashCan().toSmallСircle(message.getMessageId());
				gameTable.getTrashCan().toSmallСircle(botAnswer.getMessageId());
			} 
		}
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}

		Log.add("Class " + gameTable.getActualGameCharacter().getClassDnd().getMyArchetypeClass(), Place.BOT);

		Log.add("Class back " + gameTable.getCuttingBoard().getArcherypeBeck(), Place.BOT);

	}

	private void finishStat(Message message)
	{
		gameTable.getActualGameCharacter().setMyMemoirs(RaceFactory.getObgectInfo(gameTable.getActualGameCharacter().getMyRace()));
		List<Integer> stats = new ArrayList<>();
		Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
		Matcher matcher = pat.matcher(message.getText());
		while (matcher.find()) 
		{
			stats.add((Integer) Integer.parseInt(matcher.group()));
		}
		try 
		{
			if(stats.size() != 6)
			{

				Message toTrash = execute(SendMessage.builder()
						.text("Instructions not followed, please try again. Make sure there are 6 values.\r\n"
								+ "Examples:\r\n"
								+ " 11 12 13 14 15 16 \r\n"
								+ " 11/12/13/14/15/16 \r\n"
								+ " str 11 dex 12 con 13 int 14 wis 15 cha 16 ")
						.chatId(message.getChatId().toString())
						.build());

				gameTable.getTrashCan().toMainСircle(toTrash.getMessageId());
				gameTable.getTrashCan().toMainСircle(message.getMessageId());
			}
			else
			{
				gameTable.getActualGameCharacter().setMyStat(stats.get(0), stats.get(1), stats.get(2), stats.get(3), stats.get(4), stats.get(5));
				gameTable.update();
				gameTable.getMediatorWallet().mediatorBreak();

				int stableHp = Dice.stableHp(gameTable.getActualGameCharacter());
				int randomHp = Dice.randomHp(gameTable.getActualGameCharacter());


				List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

				buttons.add(Arrays.asList(InlineKeyboardButton.builder()
						.text("Stable " + stableHp)
						.callbackData(hpMediatorKey + stableHp)
						.build(),
						InlineKeyboardButton.builder()
						.text("Random ***")
						.callbackData(hpMediatorKey + randomHp)
						.build()));


				Message toTrash = execute(SendMessage.builder()
						.text("How much HP does your character have?\r\n"
								+ "\r\n"
								+ "You can choose stable or random HP count \r\n"
								+ "\r\n"
								+ "If you agreed with the game master on a different amount of HP, send its value. (Write the amount of HP)")
						.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
						.chatId(message.getChatId().toString())
						.build());

				gameTable.getMediatorWallet().setHpMediator(true);
				gameTable.getTrashCan().toMainСircle(message.getMessageId());
				gameTable.getTrashCan().toMainСircle(toTrash.getMessageId());

			}
		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}
	}

	private void finishHp(Message message)
	{

		Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
		Matcher matcher = pat.matcher(message.getText());
		while (matcher.find()) 
		{
			gameTable.getActualGameCharacter().setHp((int) Integer.parseInt(matcher.group()));
		}
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		buttons.add(Arrays.asList(InlineKeyboardButton.builder()
				.text("Let's go")
				.callbackData(menuKey)
				.build()));
		try 
		{
			Message toTrash = execute(SendMessage.builder()
					.text("Congratulations, you are ready for adventure.")
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build());

			gameTable.getTrashCan().toMainСircle(message.getMessageId());
			gameTable.getTrashCan().toMainСircle(toTrash.getMessageId());


		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}
		gameTable.update();
		gameTable.getMediatorWallet().mediatorBreak();

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void handleCallback(CallbackQuery callbackQuery)  ///<-
	{
		String key = callbackQuery.getData().replaceAll(keyCheck + keyAnswer, "$1");


		switch (key)
		{

		case characterCaseKey:

			downloadHero(callbackQuery);
			Log.add("downloadHero", Place.BOT, Place.CALLBACK);
			return;

			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case characterCreateKey:
			startCreateHero(callbackQuery);
			Log.add("startCreateHero", Place.BOT, Place.CALLBACK, Place.CREATING);
			return;

		case startClassKey:
			startCreateClass(callbackQuery);
			Log.add("startCreateClass", Place.BOT, Place.CALLBACK, Place.CREATING);
			return;

		case classKey:
			chooseArchetype(callbackQuery);
			Log.add("chooseArchetype", Place.BOT, Place.CALLBACK, Place.CREATING);
			return;

		case archetypeKey:
			chooseLvlClass(callbackQuery);
			Log.add("chooseLvlClass", Place.BOT, Place.CALLBACK, Place.CREATING);
			return;

		case startRaceKey:
			startCreateRace(callbackQuery);
			Log.add("startCreateRace", Place.BOT, Place.CALLBACK, Place.CREATING);
			return;

		case raceKey:
			chooseSubRace(callbackQuery);
			Log.add("chooseSubRace", Place.BOT, Place.CALLBACK, Place.CREATING);
			return;

		case subRaceKey:
			finishRace(callbackQuery);
			Log.add("finishRace", Place.BOT, Place.CALLBACK, Place.CREATING);
			return;

		case hpMediatorKey:
			startHp(callbackQuery);
			Log.add("startHp", Place.BOT, Place.CALLBACK, Place.CREATING);
			return;

		case finishHeroKey:
			startStats(callbackQuery);
			Log.add("startStats", Place.BOT, Place.CALLBACK, Place.CREATING);
			return;
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case menuKey:
			characterMenu(callbackQuery);
			Log.add("characterMenu", Place.BOT, Place.CALLBACK, Place.PLAY);
			return;

		case skillMenu:
			skillMenu(callbackQuery);
			Log.add("skillMenu", Place.BOT, Place.CALLBACK, Place.PLAY);
			return;

		case memoirsMenu:
			memoirsMenu(callbackQuery);
			Log.add("memoirsMenu", Place.BOT, Place.CALLBACK, Place.PLAY);
			return;

		}

	}

	private void downloadHero(CallbackQuery callbackQuery) 
	{

		if(gameTable.isChekChar())
		{
			CharacterFactory.save(gameTable.getActualGameCharacter());
			gameTable.setActualGameCharacter(CharacterFactory.load(callbackQuery.getData().replaceAll(keyCheck + keyAnswer, "$2"))); 
			characterMenu(callbackQuery);

		}
		else 
		{
			gameTable.setChekChar(true);
			gameTable.setActualGameCharacter(CharacterFactory.load(callbackQuery.getData().replaceAll(keyCheck + keyAnswer, "$2"))); 
			characterMenu(callbackQuery);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void startCreateHero(CallbackQuery callbackQuery)
	{
		gameTable.getMediatorWallet().mediatorBreak();
		clean(gameTable.getTrashCan().getCircle(Circle.MAIN));
		gameTable.getMediatorWallet().setCharacterCreateMediator(true);

		Message message = callbackQuery.getMessage();
		try 
		{

			Message toTrash = execute( SendMessage.builder()
					.text("Traveler, how should I call you?!\n(Write Hero name)")
					.chatId(message.getChatId().toString())
					.build());

			gameTable.getTrashCan().toMainСircle(toTrash.getMessageId());

		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}

	}

	private void startCreateClass(CallbackQuery callbackQuery)
	{
		gameTable.getMediatorWallet().mediatorBreak();
		clean(gameTable.getTrashCan().getCircle(Circle.MAIN));

		Message message = callbackQuery.getMessage();
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		for(int i = 0; i < ClassFactory.getClassArray().length; i++)
		{
			buttons.add(Arrays.asList(InlineKeyboardButton.builder()
					.text(ClassFactory.getClassArray()[i])
					.callbackData(classKey + ClassFactory.getClassArray()[i])
					.build()));
		}		
		try 
		{
			Message botAnswer = execute(SendMessage.builder()
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.text("What is your class, " + gameTable.getActualGameCharacter().getName() + "?")
					.chatId(message.getChatId().toString())

					.build()
					);

			gameTable.getTrashCan().toMainСircle(botAnswer.getMessageId());
		} 
		catch (TelegramApiException e) {
			e.printStackTrace();
		}

	}

	private void chooseArchetype(CallbackQuery callbackQuery)
	{
		gameTable.getCuttingBoard().setClassBeck(callbackQuery.getData().replaceAll(classKey + keyAnswer, "$1"));
		Message message = callbackQuery.getMessage();
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		for(int i = 0; i < ClassFactory.getArchetypeArray(gameTable.getCuttingBoard().getClassBeck()).length; i++)
		{
			buttons.add(Arrays.asList(InlineKeyboardButton.builder()
					.text(ClassFactory.getArchetypeArray(gameTable.getCuttingBoard().getClassBeck())[i].replaceAll(keyAnswer + ".txt", "$1"))
					.callbackData(archetypeKey + ClassFactory.getArchetypeArray(gameTable.getCuttingBoard().getClassBeck())[i]
							.replaceAll(keyAnswer + ".txt", "$1"))
					.build()));
		}
		try 
		{
			Message toTrash = execute(
					SendMessage.builder()
					.text(gameTable.getCuttingBoard().getClassBeck() + ", realy? Which one?")
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build());

			gameTable.getTrashCan().toMainСircle(toTrash.getMessageId());

		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}
	}

	private void chooseLvlClass(CallbackQuery callbackQuery) 
	{
		gameTable.getCuttingBoard().setArcherypeBeck(callbackQuery.getData().replaceAll(archetypeKey + keyAnswer, "$1"));
		gameTable.getMediatorWallet().mediatorBreak();
		gameTable.getMediatorWallet().setClassCreateMediator(true);
		Message message = callbackQuery.getMessage();
		try 
		{
			Message toTrash = execute( SendMessage.builder()
					.text("What LVL of Hero?(Write)")
					.chatId(message.getChatId().toString())
					.build());

			gameTable.getTrashCan().toMainСircle(toTrash.getMessageId());

		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}


	}

	private void startCreateRace(CallbackQuery callbackQuery)
	{
		gameTable.getMediatorWallet().mediatorBreak();
		clean(gameTable.getTrashCan().getCircle(Circle.MAIN));

		Message message = callbackQuery.getMessage();
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		for(int i = 1; i <= RaceFactory.getRaceArray().length; i += 3)
		{
			if(((i + 1) > RaceFactory.getRaceArray().length)&&((i + 2) > RaceFactory.getRaceArray().length)) 
			{
				buttons.add(Arrays.asList(

						InlineKeyboardButton.builder()
						.text(RaceFactory.getRaceArray()[i-1])
						.callbackData(raceKey + RaceFactory.getRaceArray()[i-1])
						.build()));
				break;
			}
			else if((i + 2) > RaceFactory.getRaceArray().length)
			{
				buttons.add(Arrays.asList(

						InlineKeyboardButton.builder()
						.text(RaceFactory.getRaceArray()[i-1])
						.callbackData(raceKey + RaceFactory.getRaceArray()[i-1])
						.build(),
						InlineKeyboardButton.builder()
						.text(RaceFactory.getRaceArray()[i])
						.callbackData(raceKey + RaceFactory.getRaceArray()[i])
						.build()));
				break;
			}
			else
			{
				buttons.add(Arrays.asList(

						InlineKeyboardButton.builder()
						.text(RaceFactory.getRaceArray()[i-1])
						.callbackData(raceKey + RaceFactory.getRaceArray()[i-1])
						.build(),
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
		try 
		{
			int lvl = gameTable.getActualGameCharacter().getClassDnd().getLvl();

			if(lvl < 21 && lvl > 0)
			{

				Message toTrash = execute(SendMessage.builder()
						.text("From what family you are?")
						.chatId(message.getChatId().toString())
						.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
						.build()
						);

				gameTable.getTrashCan().toMainСircle(toTrash.getMessageId());

			}

		}
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}

	}

	private void chooseSubRace(CallbackQuery callbackQuery)
	{
		gameTable.getCuttingBoard().setRace(callbackQuery.getData().replaceAll(raceKey + keyAnswer, "$1"));
		Message message = callbackQuery.getMessage();
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		for(int i = 0; i < RaceFactory.getSubRaceArray(gameTable.getCuttingBoard().getRace()).length; i++)
		{
			buttons.add(Arrays.asList(InlineKeyboardButton.builder()
					.text(RaceFactory.getSubRaceArray(gameTable.getCuttingBoard().getRace())[i].replaceAll(keyAnswer + ".txt", "$1"))
					.callbackData(subRaceKey + RaceFactory.getSubRaceArray(gameTable.getCuttingBoard().getRace())[i].replaceAll(keyAnswer + ".txt", "$1"))
					.build()));
		}
		try 
		{
			Message toTrash = execute(
					SendMessage.builder()
					.text(gameTable.getCuttingBoard().getRace() + "? More specifically?")
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build());

			gameTable.getTrashCan().toMainСircle(toTrash.getMessageId());

		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}
	}

	private void finishRace(CallbackQuery callbackQuery)
	{
		Message message = callbackQuery.getMessage();

		gameTable.getCuttingBoard().setSubRace(callbackQuery.getData().replaceAll(subRaceKey + keyAnswer,"$1"));
		gameTable.createRace();

		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
		buttons.add(Arrays.asList(InlineKeyboardButton.builder()
				.text("Yes that's right")
				.callbackData(finishHeroKey)
				.build()));


		try 
		{
			Message botAnswer = execute(SendMessage.builder()
					.text("Are you satisfied with this option? If not, select another option above." 
							+ "\r\n"+ RaceFactory.getObgectInfo(gameTable.getActualGameCharacter().getMyRace()))
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build()
					);
			gameTable.getTrashCan().toSmallСircle(botAnswer.getMessageId());
		} 
		catch (TelegramApiException e) {
			e.printStackTrace();
		}
		gameTable.update();
	}

	private void startStats(CallbackQuery callbackQuery)
	{
		gameTable.getMediatorWallet().mediatorBreak();
		clean(gameTable.getTrashCan().getCircle(Circle.MAIN));
		gameTable.getMediatorWallet().setStatMediator(true);

		Message message = callbackQuery.getMessage();
		try 
		{
			Message toTrash = execute( SendMessage.builder()
					.text(CharacterFactory.getCharacterStatInfo(gameTable.getActualGameCharacter()))
					.chatId(message.getChatId().toString())
					.build());

			gameTable.getTrashCan().toMainСircle(toTrash.getMessageId());

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

	}

	private void startHp(CallbackQuery callbackQuery)
	{
		gameTable.getMediatorWallet().mediatorBreak();
		clean(gameTable.getTrashCan().getCircle(Circle.MAIN));

		Message message = callbackQuery.getMessage();

		Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
		Matcher matcher = pat.matcher(callbackQuery.getData());
		while (matcher.find()) 
		{
			gameTable.getActualGameCharacter().setHp((int) Integer.parseInt(matcher.group()));
		}

		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		buttons.add(Arrays.asList(InlineKeyboardButton.builder()
				.text("Let's go")
				.callbackData(menuKey)
				.build()));
		try 
		{
			Message toTrash = execute(SendMessage.builder()
					.text("Congratulations, you are ready for adventure.")
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build());

			gameTable.setChekChar(true);
			gameTable.getTrashCan().toMainСircle(toTrash.getMessageId());




		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}
		gameTable.update();
		gameTable.getMediatorWallet().mediatorBreak();

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void characterMenu(CallbackQuery callbackQuery) 
	{


		gameTable.getMediatorWallet().mediatorBreak();
		clean(gameTable.getTrashCan().getCircle(Circle.ALL));

		Message message = callbackQuery.getMessage();
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();


		boolean spell = true;
		/*Pattern pat = Pattern.compile("Using Spells");
		String name;
		Matcher find;
		for(int i = 0; i < gameTable.getActualGameCharacter().getMySkills().size(); i++)
		{
			name = gameTable.getActualGameCharacter().getMySkills().get(i).getName();
			find = pat.matcher(name);
			if(find.find()) 
			{
				spell = true;
			}
		}*/

		if(spell == true) {

			buttons.add(Arrays.asList(InlineKeyboardButton.builder()
					.text("SPELLS")
					.callbackData(spellMenu)
					.build(),
					InlineKeyboardButton.builder()
					.text("SKILLS")
					.callbackData(skillMenu)
					.build(),
					InlineKeyboardButton.builder()
					.text("POSSESSION")
					.callbackData(possessionMenu)
					.build()));
		} 
		else if(spell = false)
		{
			buttons.add(Arrays.asList(InlineKeyboardButton.builder()
					.text("SKILLS")
					.callbackData(skillMenu)
					.build(),
					InlineKeyboardButton.builder()
					.text("POSSESSION")
					.callbackData(possessionMenu)
					.build()));
		}
		buttons.add(Arrays.asList(InlineKeyboardButton.builder()
				.text("BAG")
				.callbackData(bagMenu)
				.build(),
				InlineKeyboardButton.builder()
				.text("MEMOIRS")
				.callbackData(memoirsMenu)
				.build()));
		buttons.add(Arrays.asList(InlineKeyboardButton.builder()
				.text("CHARACTER LIST")
				.callbackData(characterListMenu)
				.build()));


		try 
		{
			Message toTrash = execute(
					SendMessage.builder()
					.text(gameTable.getActualGameCharacter().getMenu())
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build());

			gameTable.getTrashCan().toBigСircle(toTrash.getMessageId());

		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}
	}

	private void memoirsMenu(CallbackQuery callbackQuery)
	{
		clean(gameTable.getTrashCan().getCircle(Circle.MAIN));
		Message message = callbackQuery.getMessage();
		try {

			Message toTrash = execute(SendMessage.builder()
					.chatId(message.getChatId().toString())
					.text(gameTable.getActualGameCharacter().getMyMemoirs())
					.build());


			gameTable.getTrashCan().toMainСircle(toTrash.getMessageId());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void skillMenu(CallbackQuery callbackQuery) 
	{
		gameTable.getMediatorWallet().mediatorBreak();
		clean(gameTable.getTrashCan().getCircle(Circle.MAIN));

		Message message = callbackQuery.getMessage();
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		String answer = "Choose spell \n";
		if(gameTable.getActualGameCharacter().getWorkmanship().getMyFeatures().isEmpty())
		{
			Log.add("0 size:(", Place.BOT);
		}
		else
		{
			Log.add(gameTable.getActualGameCharacter().getWorkmanship().getMyFeatures().size() + 
					gameTable.getActualGameCharacter().getWorkmanship().getMyFeatures().get(1).getName(),
					Place.BOT, Place.PLAY);
			for (Feature skill: gameTable.getActualGameCharacter().getWorkmanship().getMyFeatures())
			{
				Log.add(gameTable.getActualGameCharacter().getWorkmanship().getMyFeatures().size() + 
						gameTable.getActualGameCharacter().getWorkmanship().getMyFeatures().get(2).getName(),
						Place.BOT, Place.PLAY);
				Log.add("createButtonSkill", Place.BOT);

				buttons.add(Arrays.asList(InlineKeyboardButton.builder()
						.text( skill.getName())
						.callbackData(skillMenu)
						.build()));
				answer += skill;
			}

		}
		try 
		{
			Message toTrash = execute(
					SendMessage.builder()
					.text(answer)
					.chatId(message.getChatId().toString())

					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build());

			gameTable.getTrashCan().toMainСircle(toTrash.getMessageId());

		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}
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
			e.printStackTrace();
		}

	}

}