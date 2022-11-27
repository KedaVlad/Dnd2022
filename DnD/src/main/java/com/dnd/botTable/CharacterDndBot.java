package com.dnd.botTable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.dnd.KeyWallet;
import com.dnd.Log;
import com.dnd.Source;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.workmanship.Feature;
import com.dnd.dndTable.factory.ControlPanel.ObjectType;
import com.dnd.dndTable.rolls.Dice;
import com.dnd.dndTable.factory.Json;

public class CharacterDndBot extends TelegramLongPollingBot implements KeyWallet,Source 
{

	private Map<Long, GameTable> gameTable = new HashMap<>();


	private GameTable game(Long key)
	{
		return gameTable.get(key);
	}

	private static Long beacon(Update update)
	{
		if(update.hasCallbackQuery())
		{
			return update.getCallbackQuery().getMessage().getChatId();
		}
		else
		{
			return update.getMessage().getChatId();
		}

	}

	private static Long beacon(Message message)
	{
		return message.getChatId();
	}

	private static Long beacon(CallbackQuery callbackQuery)
	{
		return callbackQuery.getMessage().getChatId();
	}

	private void clean(Long beacon) throws InterruptedException, TelegramApiException
	{

		
		if(game(beacon).script.getTrash().size() > 0)
		{
			List<Integer> target = game(beacon).script.throwAwayTrash();
			Log.add(target);
			for(Integer message: target)
			{
				Log.add("Clean" + message);
				execute(DeleteMessage.builder()
						.chatId(beacon.toString())
						.messageId(message)
						.build());
				
			}
		}
		else
		{
			game(beacon).script.throwAwayTrash();
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void onUpdateReceived(Update update) 
	{ 

		try 
		{
			if(update.hasCallbackQuery())
			{
				handleCallback(update.getCallbackQuery());
			}
			else if(gameTable.containsKey(beacon(update)) && update.hasMessage() && (game(beacon(update)).getMediatorWallet().checkMediator() == true)) 
			{
				hendlerMediator(update.getMessage());
			}		
			else if(update.hasMessage()) 
			{
				handleMessage(update.getMessage());
			}

			clean(beacon(update));
			Log.add(game(beacon(update)).script);
			/************************************************************************************************		

			if(getGameTable().get(beacon(update)) == null)
			{
				startCase(update.getMessage());
			}
			else
			{
				clean(Circle.SMALL, beacon(update));

				if(update.hasCallbackQuery())
				{
					handleCallback(update.getCallbackQuery());
				}
				else if(update.hasMessage() && (getGameTable().get(beacon(update)).getMediatorWallet().checkMediator() == true)) 
				{
					hendlerMediator(update.getMessage());
				}		
				else if(update.hasMessage()) 
				{
					handleMessage(update.getMessage());
				}
			}
			 *************************************************************************************************/
		}	
		catch (Exception e) 
		{		
			e.printStackTrace();		
		}
	}



	/*
	private void clean(Circle circle, Long beacon)
	{
		List<Integer> list = getGameTable().get(beacon).getTrashCan().getCircle(circle);

		if(!list.isEmpty() && getGameTable().get(beacon).getChatId() != 0) 
		{
			for(int i = 0; i < list.size(); i++)
			{   
				try 
				{
					execute(DeleteMessage.builder()
							.chatId(beacon.toString())
							.messageId(list.get(i))
							.build());
				} 
				catch (TelegramApiException e) 
				{
					e.printStackTrace();
				}

			}
		}

	}
	 */

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void handleMessage(Message message) throws TelegramApiException, InterruptedException
	{

		//getGameTable().get(beacon(message)).getTrashCan().toMainСircle(message.getMessageId());

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
					//clean(Circle.ALL, beacon(message));
					startCase(message);
					return;
				case "/myCharacters":
					//clean(Circle.MAIN, beacon(message));
					characterCase(message);
									return;
				}
			}
		}
		else
		{
			

			if(game(beacon(message)).isCheckChar())
			{
				game(beacon(message)).getActualGameCharacter().setMyMemoirs(message.getText());
				Message toTrash = execute(SendMessage.builder()
						.text("I will put it in your memoirs")
						.chatId(message.getChatId().toString())
						.build()
						);
				game(beacon(message)).script.prepare(toTrash.getMessageId());
			}
			else
			{
				Message toTrash = execute(SendMessage.builder()
						.text("Until you have an active hero, you have no memoirs. Therefore, unfortunately, this message recognize oblivion.")
						.chatId(message.getChatId().toString())
						.build()
						);
				game(beacon(message)).script.prepare(toTrash.getMessageId());

			}
			/*	if(getGameTable().get(beacon(message)).isCheckChar())
			{
				getGameTable().get(beacon(message)).getActualGameCharacter().setMyMemoirs(message.getText());
				Message toTrash = execute(SendMessage.builder()
						.text("I will put it in your memoirs")
						.chatId(message.getChatId().toString())
						.build()
						);
				getGameTable().get(beacon(message)).getTrashCan().toSmallСircle(message.getMessageId());
				getGameTable().get(beacon(message)).getTrashCan().toSmallСircle(toTrash.getMessageId());
			}
			else
			{
				Message toTrash = execute(SendMessage.builder()
						.text("Until you have an active hero, you have no memoirs. Therefore, unfortunately, this message recognize oblivion.")
						.chatId(message.getChatId().toString())
						.build()
						);
				getGameTable().get(beacon(message)).getTrashCan().toSmallСircle(message.getMessageId());
				getGameTable().get(beacon(message)).getTrashCan().toSmallСircle(toTrash.getMessageId());
			}*/
		}
	}

	private void startCase(Message message) throws TelegramApiException, InterruptedException
	{
		if(!getGameTable().containsKey(beacon(message)))
		{
			getGameTable().put(beacon(message), GameTable.create(message.getChatId()));
		}
		game(beacon(message)).script.goTo("Base", true);	
		game(beacon(message)).script.prepare(message.getMessageId());
		game(beacon(message)).script.goTo(start, true);	

		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
		replyKeyboardMarkup.setOneTimeKeyboard(true);
		List<KeyboardRow> buttons = new ArrayList<>();
		KeyboardRow keyboardFirstRow = new KeyboardRow();
		keyboardFirstRow.add(new KeyboardButton("/myCharacters"));

		buttons.add(keyboardFirstRow);

		replyKeyboardMarkup.setKeyboard(buttons);
		replyKeyboardMarkup.setResizeKeyboard(true);

		Message act = execute(SendMessage.builder()
				.text("Greetings!\r\n" + "/myCharacters - This command leads to your character library,"
						+ " where you can create and choose which character you play.")
				.replyMarkup(replyKeyboardMarkup)
				.chatId(message.getChatId().toString())
				.build());

		game(beacon(message)).script.toAct(act.getMessageId());


		/*if(!getGameTable().containsKey(beacon(message)))
		{
			getGameTable().put(beacon(message), GameTable.create(message.getChatId()));
		}

		clean(Circle.ALL, beacon(message));

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
				);*/

	}

	private void characterCase(Message message) throws TelegramApiException 
	{

		game(beacon(message)).getControlPanel().cleanLocalData();
		game(beacon(message)).script.prepare(message.getMessageId());

		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		buttons.add(Arrays.asList(InlineKeyboardButton.builder()
				.text("CREATE")
				.callbackData(characterCreateKey)
				.build()));


		Message act = null;

		if(game(beacon(message)).getSavedCharacter().size() != 0) 
		{
			for(String name: game(beacon(message)).getSavedCharacter().keySet())
			{
				buttons.add(Arrays.asList(InlineKeyboardButton.builder()
						.text(name)
						.callbackData(characterCaseKey + name)						
						.build()));
			}

			act = execute(
					SendMessage.builder()
					.text("Choose a Hero or CREATE new one.")
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build());

		}
		else if(getGameTable().get(beacon(message)).getSavedCharacter().size() == 0)
		{
			act = execute(
					SendMessage.builder()
					.text("You don't have a Hero yet, my friend. But after you CREATE them, you can find them here.")
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build());

		}
		game(message.getChatId()).script.goTo(start, dock);
		game(message.getChatId()).script.toAct(act.getMessageId());
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void hendlerMediator(Message message) throws TelegramApiException ///<-
	{
		String key = getGameTable().get(beacon(message)).getMediatorWallet().findMediator();

		switch(key)
		{

		case characterMediatorKey:

			finishCharacter(message);
			return;

		case classMediatorKey:

			finishClass(message); 
			return;

		case statMediatorKey:

			finishStat(message);
			return;

		case hpMediatorKey:

			finishHp(message);
			return;


		}
	}

	private void finishCharacter(Message message) throws TelegramApiException
	{
		game(beacon(message)).setActualGameCharacter(CharacterDnd.create(message.getText()));
		game(beacon(message)).getActualGameCharacter().setMyMemoirs(game(beacon(message)).getActualGameCharacter().getName() + "\n\n");
		game(beacon(message)).script.toAct(message.getMessageId());
		game(beacon(message)).script.goTo(characterCreateKey, dock);
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		buttons.add(Arrays.asList(InlineKeyboardButton.builder()
				.text("Yes that's right")
				.callbackData(startClassKey)
				.build()));


		Message act = execute(SendMessage.builder()
				.text("So can I call you - " + getGameTable().get(message.getChatId()).getActualGameCharacter().getName() + "? If not, repeat your name.")
				.chatId(message.getChatId().toString())
				.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
				.build());

		game(beacon(message)).script.toAct(act.getMessageId());

	}

	private void finishClass(Message message) throws TelegramApiException
	{
		game(beacon(message)).script.goTo(finishClassKey, dock);
		game(beacon(message)).script.toAct(message.getMessageId());

		int lvl = 0;
		Pattern pat = Pattern.compile(keyNumber);
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

		Message act = null;

		if(lvl < 1 || lvl > 20)
		{
			game(beacon(message)).getControlPanel().setClassLvl(1);

			act = execute(SendMessage.builder()
					.text(lvl+"??? I see you're new here. Let's start with lvl 1. Are you satisfied with this option?"
							+ " If not, select another option above." + "\r\n"
							+ getGameTable().get(message.getChatId()).getControlPanel().getObjectInfo(ObjectType.CLASS))
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build());
		}
		else
		{
			game(beacon(message)).getControlPanel().setClassLvl(lvl);

			act = execute(SendMessage.builder()
					.text("Are you satisfied with this option? If not, select another option above." + "\r\n"
							+ getGameTable().get(message.getChatId()).getControlPanel().getObjectInfo(ObjectType.CLASS))
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build());
		} 
		game(beacon(message)).script.toAct(act.getMessageId());
	}

	private void finishStat(Message message) throws TelegramApiException
	{
		game(beacon(message)).script.goTo(startStatsKey, dock);
		game(beacon(message)).script.toAct(message.getMessageId());

		List<Integer> stats = new ArrayList<>();
		Pattern pat = Pattern.compile(keyNumber);
		Matcher matcher = pat.matcher(message.getText());
		while (matcher.find()) 
		{
			stats.add((Integer) Integer.parseInt(matcher.group()));
		}

		Message act = null;

		if(stats.size() != 6)
		{

			act = execute(SendMessage.builder()
					.text("Instructions not followed, please try again. Make sure there are 6 values.\r\n"
							+ "Examples:\r\n"
							+ " 11 12 13 14 15 16 \r\n"
							+ " 11/12/13/14/15/16 \r\n"
							+ " str 11 dex 12 con 13 int 14 wis 15 cha 16 ")
					.chatId(message.getChatId().toString())
					.build());

		}
		else
		{
			game(beacon(message)).getActualGameCharacter().setMyStat(stats.get(0), stats.get(1), stats.get(2), stats.get(3), stats.get(4), stats.get(5));
			game(beacon(message)).getMediatorWallet().mediatorBreak();

			int stableHp = Dice.stableStartHp(game(beacon(message)).getActualGameCharacter());
			int randomHp = Dice.randomStartHp(game(beacon(message)).getActualGameCharacter());


			List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

			buttons.add(Arrays.asList(InlineKeyboardButton.builder()
					.text("Stable " + stableHp)
					.callbackData(hpMediatorKey + stableHp)
					.build(),
					InlineKeyboardButton.builder()
					.text("Random ***")
					.callbackData(hpMediatorKey + randomHp)
					.build()));


			act = execute(SendMessage.builder()
					.text("How much HP does your character have?\r\n"
							+ "\r\n"
							+ "You can choose stable or random HP count \r\n"
							+ "\r\n"
							+ "If you agreed with the game master on a different amount of HP, send its value. (Write the amount of HP)")
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.chatId(message.getChatId().toString())
					.build());

			game(beacon(message)).getMediatorWallet().setHpMediator(true);

		}

		game(beacon(message)).script.toAct(act.getMessageId());
	}

	private void finishHp(Message message) throws TelegramApiException
	{

		Pattern pat = Pattern.compile(keyNumber);
		Matcher matcher = pat.matcher(message.getText());
		int answer = 0;
		while (matcher.find()) 
		{
			answer = (int) Integer.parseInt(matcher.group());
		}
		if(answer <= 0) 
		{
			answer = (int) Dice.stableStartHp(getGameTable().get(message.getChatId()).getActualGameCharacter()); 

			Message act = execute(SendMessage.builder()
					.text("Nice try... I see U very smart, but you will get stable " + answer + " HP.")
					.chatId(message.getChatId().toString())
					.build());

			game(beacon(message)).script.toAct(act.getMessageId());
		}

		game(beacon(message)).getActualGameCharacter().setHp(answer);
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		buttons.add(Arrays.asList(InlineKeyboardButton.builder()
				.text("Let's go")
				.callbackData(menuKey)
				.build()));

		Message toTrash = execute(SendMessage.builder()
				.text("Congratulations, you are ready for adventure.")
				.chatId(message.getChatId().toString())
				.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
				.build());

		game(beacon(message)).script.toAct(message.getMessageId()); //<- if from callback provoke error 404
		game(beacon(message)).script.toAct(toTrash.getMessageId());
		game(beacon(message)).getMediatorWallet().mediatorBreak();

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void handleCallback(CallbackQuery callbackQuery) throws InterruptedException, TelegramApiException  ///<-
	{
		String key = callbackQuery.getData().replaceAll(keyCheck + keyAnswer, "$1");

		switch (key)
		{

		case characterCaseKey:

			downloadHero(callbackQuery);
			return;

			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case characterCreateKey:
			startCreateHero(callbackQuery);
			return;

		case startClassKey:
			startCreateClass(callbackQuery);
			return;

		case chooseArchetypeClassKey:
			chooseArchetype(callbackQuery);
			return;

		case finishClassKey:
			chooseLvlClass(callbackQuery);
			return;

		case startRaceKey:
			startCreateRace(callbackQuery);
			return;

		case chooseSubRaceKey:
			chooseSubRace(callbackQuery);
			return;

		case finishRaceKey:
			finishRace(callbackQuery);
			return;

		case hpMediatorKey:
			finishHp(callbackQuery.getMessage());
			return;

		case startStatsKey:
			startStats(callbackQuery);
			return;
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case menuKey:
			characterMenu(callbackQuery);

			gameTable.get(beacon(callbackQuery)).save();
			return;

		case featureMenu:
			featureMenu(callbackQuery);
			return;

		case memoirsMenu:
			memoirsMenu(callbackQuery);
			return;

		}

	}

	private void downloadHero(CallbackQuery callbackQuery) throws InterruptedException, TelegramApiException 
	{
		game(beacon(callbackQuery)).load(callbackQuery.getData().replaceAll(keyCheck + keyAnswer, "$2"));
		callbackQuery.setData(game(beacon(callbackQuery)).readinessСheck());
		handleCallback(callbackQuery);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void startCreateHero(CallbackQuery callbackQuery) throws InterruptedException, TelegramApiException
	{
		game(beacon(callbackQuery)).save();
		game(beacon(callbackQuery)).script.goTo(start, characterCreateKey);
		game(beacon(callbackQuery)).getMediatorWallet().setCharacterCreateMediator(true);

		Message message = callbackQuery.getMessage();

		Message act = execute( SendMessage.builder()
				.text("Traveler, how should I call you?!\n(Write Hero name)")
				.chatId(message.getChatId().toString())
				.build());

		//getGameTable().get(beacon(callbackQuery)).getTrashCan().toMainСircle(toTrash.getMessageId());
		game(beacon(callbackQuery)).script.toAct(act.getMessageId());

	}

	private void startCreateClass(CallbackQuery callbackQuery) throws TelegramApiException
	{
		game(beacon(callbackQuery)).setCheсkChar(true);
		game(beacon(callbackQuery)).save();
		game(beacon(callbackQuery)).getMediatorWallet().mediatorBreak();
		game(beacon(callbackQuery)).script.goTo(start, startClassKey);

		Message message = callbackQuery.getMessage();
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		for(int i = 0; i < game(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.CLASS).length; i++)
		{
			buttons.add(Arrays.asList(InlineKeyboardButton.builder()
					.text(getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.CLASS)[i])
					.callbackData(chooseArchetypeClassKey + getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.CLASS)[i])
					.build()));
		}		

		Message act = execute(SendMessage.builder()
				.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
				.text("What is your class, " + getGameTable().get(beacon(callbackQuery)).getActualGameCharacter().getName() + "?")
				.chatId(message.getChatId().toString())

				.build()
				);

		game(beacon(callbackQuery)).script.toAct(act.getMessageId());


	}

	private void chooseArchetype(CallbackQuery callbackQuery) throws TelegramApiException
	{
		game(beacon(callbackQuery)).getControlPanel().setClassBeck(callbackQuery.getData().replaceAll(chooseArchetypeClassKey + keyAnswer, "$1"));
		game(beacon(callbackQuery)).script.goTo(chooseArchetypeClassKey, true);
		Message message = callbackQuery.getMessage();
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		for(int i = 0; i < getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.ARCHETYPE).length; i++)
		{
			buttons.add(Arrays.asList(InlineKeyboardButton.builder()
					.text(getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.ARCHETYPE)[i].replaceAll(keyAnswer + ".json", "$1"))
					.callbackData(finishClassKey + getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.ARCHETYPE)[i]
							.replaceAll(keyAnswer + ".txt", "$1"))
					.build()));
		}

		Message act = execute(
				SendMessage.builder()
				.text(getGameTable().get(beacon(callbackQuery)).getControlPanel().getClassBeck() + ", realy? Which one?")
				.chatId(message.getChatId().toString())
				.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
				.build());

		game(beacon(callbackQuery)).script.toAct(act.getMessageId());

	}

	private void chooseLvlClass(CallbackQuery callbackQuery) throws TelegramApiException 
	{
		game(beacon(callbackQuery)).getControlPanel().setArchetypeBeck(callbackQuery.getData().replaceAll(finishClassKey + keyAnswer, "$1"));
		game(beacon(callbackQuery)).getMediatorWallet().setClassCreateMediator(true);
		game(beacon(callbackQuery)).script.goTo(finishClassKey, true);

		Message message = callbackQuery.getMessage();


		Message act = execute( SendMessage.builder()
				.text("What LVL of Hero?(Write)")
				.chatId(message.getChatId().toString())
				.build());

		game(beacon(callbackQuery)).script.toAct(act.getMessageId());

	}

	private void startCreateRace(CallbackQuery callbackQuery) throws TelegramApiException
	{ 
		game(beacon(callbackQuery)).createClass();
		game(beacon(callbackQuery)).getActualGameCharacter().setMyMemoirs(game(beacon(callbackQuery)).getControlPanel().getObjectInfo(ObjectType.CLASS));
		game(beacon(callbackQuery)).script.goTo(start, startRaceKey);


		Message message = callbackQuery.getMessage();
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		for(int i = 1; i <= game(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE).length; i += 3)
		{
			if(((i + 1) > getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE).length)
					&&((i + 2) > game(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE).length)) 
			{
				buttons.add(Arrays.asList(

						InlineKeyboardButton.builder()
						.text(game(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE)[i-1])
						.callbackData(chooseSubRaceKey + game(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE)[i-1])
						.build()));
				break;
			}
			else if((i + 2) > game(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE).length)
			{
				buttons.add(Arrays.asList(

						InlineKeyboardButton.builder()
						.text(game(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE)[i-1])
						.callbackData(chooseSubRaceKey + game(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE)[i-1])
						.build(),
						InlineKeyboardButton.builder()
						.text(game(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE)[i])
						.callbackData(chooseSubRaceKey + game(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE)[i])
						.build()));
				break;
			}
			else
			{
				buttons.add(Arrays.asList(

						InlineKeyboardButton.builder()
						.text(game(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE)[i-1])
						.callbackData(chooseSubRaceKey + game(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE)[i-1])
						.build(),
						InlineKeyboardButton.builder()
						.text(game(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE)[i])
						.callbackData(chooseSubRaceKey + game(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE)[i])
						.build(),
						InlineKeyboardButton.builder()
						.text(game(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE)[i+1])
						.callbackData(chooseSubRaceKey + game(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE)[i+1])
						.build()));
			}

		}

		int lvl = getGameTable().get(beacon(callbackQuery)).getActualGameCharacter().getClassDnd().getLvl();

		if(lvl < 21 && lvl > 0)
		{

			Message act = execute(SendMessage.builder()
					.text("From what family you are?")
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build()
					);

			game(beacon(callbackQuery)).script.toAct(act.getMessageId());

		}

	}

	private void chooseSubRace(CallbackQuery callbackQuery) throws TelegramApiException
	{
		game(beacon(callbackQuery)).getControlPanel().setRace(callbackQuery.getData().replaceAll(chooseSubRaceKey + keyAnswer, "$1"));
		game(beacon(callbackQuery)).script.goTo(finishRaceKey, true);

		Message message = callbackQuery.getMessage();
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		for(int i = 0; i < getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.SUBRACE).length; i++)
		{
			buttons.add(Arrays.asList(InlineKeyboardButton.builder()
					.text(getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.SUBRACE)[i].replaceAll(keyAnswer + ".json", "$1"))
					.callbackData(finishRaceKey + getGameTable().get(beacon(callbackQuery))
							.getControlPanel().getArray(ObjectType.SUBRACE)[i].replaceAll(keyAnswer + ".txt", "$1"))
					.build()));
		}

		Message act = execute(
				SendMessage.builder()
				.text(getGameTable().get(beacon(callbackQuery)).getControlPanel().getRace() + "? More specifically?")
				.chatId(message.getChatId().toString())
				.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
				.build());

		game(beacon(callbackQuery)).script.toAct(act.getMessageId());


	}

	private void finishRace(CallbackQuery callbackQuery) throws TelegramApiException
	{

		game(beacon(callbackQuery)).script.goTo(finishRaceKey, dock);
		game(beacon(callbackQuery)).getControlPanel().setSubRace(callbackQuery.getData().replaceAll(finishRaceKey + keyAnswer,"$1"));

		Message message = callbackQuery.getMessage();
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
		buttons.add(Arrays.asList(InlineKeyboardButton.builder()
				.text("Yes that's right")
				.callbackData(startStatsKey)
				.build()));

		Message act = execute(SendMessage.builder()
				.text("Are you satisfied with this option? If not, select another option above." 
						+ "\r\n"+ getGameTable().get(beacon(callbackQuery)).getControlPanel().getObjectInfo(ObjectType.RACE))
				.chatId(message.getChatId().toString())
				.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
				.build());

		game(beacon(callbackQuery)).script.toAct(act.getMessageId());

	}

	private void startStats(CallbackQuery callbackQuery) throws TelegramApiException
	{
		game(beacon(callbackQuery)).createRace();
		game(beacon(callbackQuery)).getActualGameCharacter().setMyMemoirs(game(beacon(callbackQuery)).getControlPanel().getObjectInfo(ObjectType.RACE));
		game(beacon(callbackQuery)).getMediatorWallet().setStatMediator(true);
		game(beacon(callbackQuery)).script.goTo(start, startStatsKey);

		Message message = callbackQuery.getMessage();

		Message act = execute( SendMessage.builder()
				.text(getGameTable().get(beacon(callbackQuery)).getControlPanel().getObjectInfo(ObjectType.STATS))
				.chatId(message.getChatId().toString())
				.build());

		game(beacon(callbackQuery)).script.toAct(act.getMessageId());
	}

	private void continuedCreation(CallbackQuery callbackQuery)
	{

	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void characterMenu(CallbackQuery callbackQuery) throws TelegramApiException 
	{

		game(beacon(callbackQuery)).getMediatorWallet().mediatorBreak();
		game(beacon(callbackQuery)).script.goTo(start, menuKey);
		
		Message message = callbackQuery.getMessage();
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		boolean spell = true;
		if(game(beacon(callbackQuery)).getActualGameCharacter().getWorkmanship().getMySpells().size() == 0) spell = false;

		if(spell == true) {

			buttons.add(Arrays.asList(InlineKeyboardButton.builder()
					.text("SPELLS")
					.callbackData(spellMenu)
					.build(),
					InlineKeyboardButton.builder()
					.text("FETURE")
					.callbackData(featureMenu)
					.build(),
					InlineKeyboardButton.builder()
					.text("POSSESSION")
					.callbackData(possessionMenu)
					.build()));
		} 
		else if(spell == false)
		{
			buttons.add(Arrays.asList(InlineKeyboardButton.builder()
					.text("FEATURE")
					.callbackData(featureMenu)
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


		String permanentBuff = "";
		for(String buff: game(beacon(callbackQuery)).getActualGameCharacter().getPermanentBuffs())
		{
			permanentBuff += buff + "\n";
		}

			Message permanentBuffBlock = execute( 
					SendMessage.builder()
					.text(permanentBuff)
					.chatId(message.getChatId().toString())
					.build());

			Message mainBlock = execute(
					SendMessage.builder()
					.text(getGameTable().get(beacon(callbackQuery)).getActualGameCharacter().getMenu())
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build());

			game(beacon(callbackQuery)).script.toAct(permanentBuffBlock.getMessageId());
			game(beacon(callbackQuery)).script.toAct(mainBlock.getMessageId());

	}

	private void memoirsMenu(CallbackQuery callbackQuery) throws TelegramApiException
	{
		game(beacon(callbackQuery)).script.goTo(menuKey,  memoirsMenu);
		Message message = callbackQuery.getMessage();
		
			Message act = execute(SendMessage.builder()
					.chatId(message.getChatId().toString())
					.text(getGameTable().get(beacon(callbackQuery)).getActualGameCharacter().getMyMemoirs())
					.build());
			
			game(beacon(callbackQuery)).script.toAct(act.getMessageId());
	}

	private void featureMenu(CallbackQuery callbackQuery) throws TelegramApiException 
	{
		game(beacon(callbackQuery)).script.goTo(menuKey,  featureMenu);
		getGameTable().get(beacon(callbackQuery)).getMediatorWallet().mediatorBreak();
	

		Message message = callbackQuery.getMessage();
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		String answer = "Those is your feture \n";
		if(getGameTable().get(beacon(callbackQuery)).getActualGameCharacter().getWorkmanship().getMyFeatures().isEmpty())
		{
			Log.add("skillMenu 0 size:(");
		}
		else
		{

			for (Feature skill: getGameTable().get(beacon(callbackQuery)).getActualGameCharacter().getWorkmanship().getMyFeatures())
			{
				buttons.add(Arrays.asList(InlineKeyboardButton.builder()
						.text( skill.getName())
						.callbackData(featureMenu)
						.build()));

			}

		}
		
			Message act = execute(
					SendMessage.builder()
					.text(answer)
					.chatId(message.getChatId().toString())

					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build());

			game(beacon(callbackQuery)).script.toAct(act.getMessageId());

		
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getBotUsername() 
	{
		return "@DndCharacterBot";
	}

	@Override
	public String getBotToken() 
	{
		return "5776409987:AAFh67JoMqwPJqt1daCgEqE9nbxnKl3GPKg";
	}

	public static void main(String[] args) throws TelegramApiException, IOException, InterruptedException 
	{
		CharacterDndBot bot = new  CharacterDndBot();
		TelegramBotsApi telegramBotsApi;

		telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
		telegramBotsApi.registerBot(bot);
		bot.setGameTable(Json.restor());

		while(true)
		{
			Json.backup(bot.getGameTable());
			Thread.sleep(5000); 
			System.out.println("*********************************************************************************************************************");
		}
	}

	public Map<Long, GameTable> getGameTable() 
	{
		return gameTable;
	}

	public void setGameTable(Map<Long, GameTable> gameTable) 
	{
		this.gameTable = gameTable;
	}

}