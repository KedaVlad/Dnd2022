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

import com.dnd.Dice;
import com.dnd.KeyWallet;
import com.dnd.Log;
import com.dnd.Source;
import com.dnd.botTable.TrashCan.Circle;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.workmanship.Feature;
import com.dnd.dndTable.factory.ControlPanel.ObjectType;
import com.dnd.dndTable.factory.Json;

public class CharacterDndBot extends TelegramLongPollingBot implements KeyWallet,Source 
{

	private Map<Long, GameTable> gameTable = new HashMap<>();

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

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void onUpdateReceived(Update update) 
	{ 

		try 
		{
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
		}	
		catch (Exception e) 
		{		
			e.printStackTrace();		
		}
	}

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


	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void handleMessage(Message message) throws TelegramApiException ///<-
	{

		getGameTable().get(beacon(message)).getTrashCan().toMainСircle(message.getMessageId());

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
					clean(Circle.ALL, beacon(message));
					startCase(message);
					Log.add("/start");
					return;
				case "/myCharacters":
					clean(Circle.MAIN, beacon(message));
					characterCase(message);
					Log.add("/myCharacters");
					return;
				}
			}
		}
		else
		{
			Log.add("try add Memoir");
			if(getGameTable().get(beacon(message)).isCheckChar())
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
			}
		}
	}

	private void startCase(Message message) throws TelegramApiException
	{
		if(!getGameTable().containsKey(beacon(message)))
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
				);

	}

	private void characterCase(Message message) throws TelegramApiException 
	{


		getGameTable().get(beacon(message)).getControlPanel().cleanLocalData();

		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		buttons.add(Arrays.asList(InlineKeyboardButton.builder()
				.text("CREATE")
				.callbackData(characterCreateKey)
				.build()));
		Log.add(getGameTable().get(beacon(message)).getSavedCharacter());
		if(getGameTable().get(beacon(message)).getSavedCharacter().size() != 0) 
		{
			for(String name: getGameTable().get(beacon(message)).getSavedCharacter().keySet())
			{
				buttons.add(Arrays.asList(InlineKeyboardButton.builder()
						.text(name)
						.callbackData(characterCaseKey + name)						
						.build()));
			}

			Message toTrash = execute(
					SendMessage.builder()
					.text("Choose a Hero or CREATE new one.")
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build()
					);
			getGameTable().get(beacon(message)).getTrashCan().toMainСircle(toTrash.getMessageId());
		}
		else if(getGameTable().get(beacon(message)).getSavedCharacter().size() == 0)
		{
			Message toTrash = execute(
					SendMessage.builder()
					.text("You don't have a Hero yet, my friend. But after you CREATE them, you can find them here.")
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build()
					);
			getGameTable().get(message.getChatId()).getTrashCan().toMainСircle(toTrash.getMessageId());
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void hendlerMediator(Message message) ///<-
	{
		String key = getGameTable().get(beacon(message)).getMediatorWallet().findMediator();

		switch(key)
		{

		case characterMediatorKey:

			finishCharacter(message);
			Log.add("finishCharacter bot");
			return;

		case classMediatorKey:

			finishClass(message); 
			Log.add("finishClass bot");
			return;

		case statMediatorKey:

			finishStat(message);
			Log.add("finishStat bot");
			return;

		case hpMediatorKey:

			finishHp(message);
			Log.add("finishHp bot");
			return;


		}
	}

	private void finishCharacter(Message message)
	{
		getGameTable().get(beacon(message)).setActualGameCharacter(CharacterDnd.create(message.getText()));
		getGameTable().get(beacon(message)).getActualGameCharacter().setMyMemoirs(getGameTable().get(beacon(message)).getActualGameCharacter().getName() + "\n\n");
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		buttons.add(Arrays.asList(InlineKeyboardButton.builder()
				.text("Yes that's right")
				.callbackData(startClassKey)
				.build()));

		try 
		{
			Message botAnswer = execute(SendMessage.builder()
					.text("So can I call you - " + getGameTable().get(message.getChatId()).getActualGameCharacter().getName() + "? If not, repeat your name.")
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build()
					);
			getGameTable().get(beacon(message)).getTrashCan().toSmallСircle(message.getMessageId());
			getGameTable().get(beacon(message)).getTrashCan().toSmallСircle(botAnswer.getMessageId());
		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}

	}

	private void finishClass(Message message)
	{
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

		try 
		{
			if(lvl < 1 || lvl > 20)
			{

				getGameTable().get(beacon(message)).getControlPanel().setClassLvl(1);

				Message botAnswer = execute(SendMessage.builder()
						.text(lvl+"??? I see you're new here. Let's start with lvl 1. Are you satisfied with this option?"
								+ " If not, select another option above." + "\r\n"
								+ getGameTable().get(message.getChatId()).getControlPanel().getObjectInfo(ObjectType.CLASS))
						.chatId(message.getChatId().toString())
						.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
						.build()
						);
				getGameTable().get(beacon(message)).getTrashCan().toSmallСircle(message.getMessageId());
				getGameTable().get(beacon(message)).getTrashCan().toSmallСircle(botAnswer.getMessageId());

			}
			else
			{

				getGameTable().get(beacon(message)).getControlPanel().setClassLvl(lvl);


				Message botAnswer = execute(SendMessage.builder()
						.text("Are you satisfied with this option? If not, select another option above." + "\r\n"
								+ getGameTable().get(message.getChatId()).getControlPanel().getObjectInfo(ObjectType.CLASS))
						.chatId(message.getChatId().toString())
						.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
						.build()
						);

				getGameTable().get(beacon(message)).getTrashCan().toSmallСircle(message.getMessageId());
				getGameTable().get(beacon(message)).getTrashCan().toSmallСircle(botAnswer.getMessageId());
			} 
		}
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}



	}

	private void finishStat(Message message)
	{
		getGameTable().get(beacon(message)).getActualGameCharacter().setMyMemoirs(
				getGameTable().get(beacon(message)).getControlPanel().getObjectInfo(ObjectType.RACE));
		List<Integer> stats = new ArrayList<>();
		Pattern pat = Pattern.compile(keyNumber);
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

				getGameTable().get(beacon(message)).getTrashCan().toMainСircle(toTrash.getMessageId());
				getGameTable().get(beacon(message)).getTrashCan().toMainСircle(message.getMessageId());
			}
			else
			{
				getGameTable().get(beacon(message)).getActualGameCharacter().setMyStat(stats.get(0), stats.get(1), stats.get(2), stats.get(3), stats.get(4), stats.get(5));
				getGameTable().get(beacon(message)).getMediatorWallet().mediatorBreak();

				int stableHp = Dice.stableStartHp(getGameTable().get(beacon(message)).getActualGameCharacter());
				int randomHp = Dice.randomStartHp(getGameTable().get(beacon(message)).getActualGameCharacter());


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

				getGameTable().get(beacon(message)).getMediatorWallet().setHpMediator(true);
				getGameTable().get(beacon(message)).getTrashCan().toMainСircle(message.getMessageId());
				getGameTable().get(beacon(message)).getTrashCan().toMainСircle(toTrash.getMessageId());

			}
		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}
	}

	private void finishHp(Message message)
	{

		Pattern pat = Pattern.compile(keyNumber);
		Matcher matcher = pat.matcher(message.getText());
		Log.add(message.getText() + " finishHp !!!!!!!!!!!!!!!!!!!!!!!");
		int answer = 0;
		while (matcher.find()) 
		{
			answer = (int) Integer.parseInt(matcher.group());
		}
		if(answer <= 0) {
			answer = (int) Dice.stableStartHp(getGameTable().get(message.getChatId()).getActualGameCharacter()); 
			Message niceTry = null;
			try {
				niceTry = execute(SendMessage.builder()
						.text("Nice try... I see U very smart, but you will get stable " + answer + " HP.")
						.chatId(message.getChatId().toString())
						.build());
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
			getGameTable().get(beacon(message)).getTrashCan().toSmallСircle(niceTry.getMessageId());
		}
		getGameTable().get(beacon(message)).getActualGameCharacter().setHp(answer);
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

			getGameTable().get(beacon(message)).getTrashCan().toMainСircle(message.getMessageId()); //<- if from callback provoke error 404
			getGameTable().get(beacon(message)).getTrashCan().toMainСircle(toTrash.getMessageId());


		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}

		getGameTable().get(beacon(message)).getMediatorWallet().mediatorBreak();

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void handleCallback(CallbackQuery callbackQuery)  ///<-
	{
		String key = callbackQuery.getData().replaceAll(keyCheck + keyAnswer, "$1");

		switch (key)
		{

		case characterCaseKey:

			downloadHero(callbackQuery);
			Log.add("downloadHero bot");
			return;

			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case characterCreateKey:
			startCreateHero(callbackQuery);
			Log.add("startCreateHero bot");
			return;

		case startClassKey:
			getGameTable().get(beacon(callbackQuery));
			startCreateClass(callbackQuery);
			Log.add("startCreateClass bot");
			return;

		case chooseArchetypeClassKey:
			chooseArchetype(callbackQuery);
			Log.add("chooseArchetype bot");
			return;

		case finishClassKey:
			chooseLvlClass(callbackQuery);
			Log.add("chooseLvlClass bot");
			return;

		case startRaceKey:
			getGameTable().get(beacon(callbackQuery));
			startCreateRace(callbackQuery);
			Log.add("startCreateRace bot");
			return;

		case chooseSubRaceKey:
			chooseSubRace(callbackQuery);
			Log.add("chooseSubRace bot");
			return;

		case finishRaceKey:
			finishRace(callbackQuery);
			Log.add("finishRace bot");
			return;

		case hpMediatorKey:
			finishHp(callbackQuery.getMessage());
			Log.add("startHp bot");
			return;

		case startStatsKey:
			getGameTable().get(beacon(callbackQuery));
			startStats(callbackQuery);
			Log.add("startStats bot");
			return;
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case menuKey:
			characterMenu(callbackQuery);

			gameTable.get(beacon(callbackQuery)).save();
			Log.add("characterMenu bot");
			return;

		case skillMenu:
			skillMenu(callbackQuery);
			Log.add("skillMenu bot");
			return;

		case memoirsMenu:
			memoirsMenu(callbackQuery);
			Log.add("memoirsMenu bot");
			return;

		}

	}

	private void downloadHero(CallbackQuery callbackQuery) 
	{
		getGameTable().get(beacon(callbackQuery)).load(callbackQuery.getData().replaceAll(keyCheck + keyAnswer, "$2"));
		callbackQuery.setData(getGameTable().get(beacon(callbackQuery)).readinessСheck());
		handleCallback(callbackQuery);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void startCreateHero(CallbackQuery callbackQuery)
	{
		getGameTable().get(beacon(callbackQuery)).save();
		clean(Circle.ALL, beacon(callbackQuery));
		getGameTable().get(beacon(callbackQuery)).getMediatorWallet().setCharacterCreateMediator(true);

		Message message = callbackQuery.getMessage();
		try 
		{

			Message toTrash = execute( SendMessage.builder()
					.text("Traveler, how should I call you?!\n(Write Hero name)")
					.chatId(message.getChatId().toString())
					.build());

			getGameTable().get(beacon(callbackQuery)).getTrashCan().toMainСircle(toTrash.getMessageId());

		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}

	}

	private void startCreateClass(CallbackQuery callbackQuery)
	{
		getGameTable().get(beacon(callbackQuery)).setCheсkChar(true);
		getGameTable().get(beacon(callbackQuery)).save();
		getGameTable().get(beacon(callbackQuery)).getMediatorWallet().mediatorBreak();
		clean(Circle.MAIN, beacon(callbackQuery));

		Message message = callbackQuery.getMessage();
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		for(int i = 0; i < getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.CLASS).length; i++)
		{
			buttons.add(Arrays.asList(InlineKeyboardButton.builder()
					.text(getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.CLASS)[i])
					.callbackData(chooseArchetypeClassKey + getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.CLASS)[i])
					.build()));
		}		
		try 
		{
			Message botAnswer = execute(SendMessage.builder()
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.text("What is your class, " + getGameTable().get(beacon(callbackQuery)).getActualGameCharacter().getName() + "?")
					.chatId(message.getChatId().toString())

					.build()
					);

			getGameTable().get(beacon(callbackQuery)).getTrashCan().toMainСircle(botAnswer.getMessageId());
		} 
		catch (TelegramApiException e) {
			e.printStackTrace();
		}

	}

	private void chooseArchetype(CallbackQuery callbackQuery)
	{
		getGameTable().get(beacon(callbackQuery)).getControlPanel().setClassBeck(callbackQuery.getData().replaceAll(chooseArchetypeClassKey + keyAnswer, "$1"));
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
		try 
		{
			Message toTrash = execute(
					SendMessage.builder()
					.text(getGameTable().get(beacon(callbackQuery)).getControlPanel().getClassBeck() + ", realy? Which one?")
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build());

			getGameTable().get(beacon(callbackQuery)).getTrashCan().toMainСircle(toTrash.getMessageId());

		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}
	}

	private void chooseLvlClass(CallbackQuery callbackQuery) 
	{
		getGameTable().get(beacon(callbackQuery)).getControlPanel().setArchetypeBeck(callbackQuery.getData().replaceAll(finishClassKey + keyAnswer, "$1"));
		getGameTable().get(beacon(callbackQuery)).getMediatorWallet().setClassCreateMediator(true);
		Message message = callbackQuery.getMessage();

		try 
		{
			Message toTrash = execute( SendMessage.builder()
					.text("What LVL of Hero?(Write)")
					.chatId(message.getChatId().toString())
					.build());

			getGameTable().get(beacon(callbackQuery)).getTrashCan().toSmallСircle(toTrash.getMessageId());
		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}
	}

	private void startCreateRace(CallbackQuery callbackQuery)
	{ 
		getGameTable().get(beacon(callbackQuery)).createClass();
		getGameTable().get(beacon(callbackQuery)).getActualGameCharacter().setMyMemoirs(
				getGameTable().get(beacon(callbackQuery)).getControlPanel().getObjectInfo(ObjectType.CLASS));
		clean(Circle.MAIN, beacon(callbackQuery));

		Message message = callbackQuery.getMessage();
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		for(int i = 1; i <= getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE).length; i += 3)
		{
			if(((i + 1) > getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE).length)
					&&((i + 2) > getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE).length)) 
			{
				buttons.add(Arrays.asList(

						InlineKeyboardButton.builder()
						.text(getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE)[i-1])
						.callbackData(chooseSubRaceKey + getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE)[i-1])
						.build()));
				break;
			}
			else if((i + 2) > getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE).length)
			{
				buttons.add(Arrays.asList(

						InlineKeyboardButton.builder()
						.text(getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE)[i-1])
						.callbackData(chooseSubRaceKey + getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE)[i-1])
						.build(),
						InlineKeyboardButton.builder()
						.text(getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE)[i])
						.callbackData(chooseSubRaceKey + getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE)[i])
						.build()));
				break;
			}
			else
			{
				buttons.add(Arrays.asList(

						InlineKeyboardButton.builder()
						.text(getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE)[i-1])
						.callbackData(chooseSubRaceKey + getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE)[i-1])
						.build(),
						InlineKeyboardButton.builder()
						.text(getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE)[i])
						.callbackData(chooseSubRaceKey + getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE)[i])
						.build(),
						InlineKeyboardButton.builder()
						.text(getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE)[i+1])
						.callbackData(chooseSubRaceKey + getGameTable().get(beacon(callbackQuery)).getControlPanel().getArray(ObjectType.RACE)[i+1])
						.build()));
			}

		}
		try 
		{
			int lvl = getGameTable().get(beacon(callbackQuery)).getActualGameCharacter().getClassDnd().getLvl();

			if(lvl < 21 && lvl > 0)
			{

				Message toTrash = execute(SendMessage.builder()
						.text("From what family you are?")
						.chatId(message.getChatId().toString())
						.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
						.build()
						);

				getGameTable().get(beacon(callbackQuery)).getTrashCan().toMainСircle(toTrash.getMessageId());

			}

		}
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}

	}

	private void chooseSubRace(CallbackQuery callbackQuery)
	{
		getGameTable().get(beacon(callbackQuery)).getControlPanel().setRace(callbackQuery.getData().replaceAll(chooseSubRaceKey + keyAnswer, "$1"));
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
		try 
		{
			Message toTrash = execute(
					SendMessage.builder()
					.text(getGameTable().get(beacon(callbackQuery)).getControlPanel().getRace() + "? More specifically?")
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build());

			getGameTable().get(beacon(callbackQuery)).getTrashCan().toMainСircle(toTrash.getMessageId());

		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}
	}

	private void finishRace(CallbackQuery callbackQuery)
	{
		Message message = callbackQuery.getMessage();

		getGameTable().get(beacon(callbackQuery)).getControlPanel().setSubRace(callbackQuery.getData().replaceAll(finishRaceKey + keyAnswer,"$1"));

		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
		buttons.add(Arrays.asList(InlineKeyboardButton.builder()
				.text("Yes that's right")
				.callbackData(startStatsKey)
				.build()));


		try 
		{
			Message botAnswer = execute(SendMessage.builder()
					.text("Are you satisfied with this option? If not, select another option above." 
							+ "\r\n"+ getGameTable().get(beacon(callbackQuery)).getControlPanel().getObjectInfo(ObjectType.RACE))
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build()
					);
			getGameTable().get(beacon(callbackQuery)).getTrashCan().toSmallСircle(botAnswer.getMessageId());
		} 
		catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void startStats(CallbackQuery callbackQuery)
	{
		getGameTable().get(beacon(callbackQuery)).createRace();
		clean(Circle.MAIN, beacon(callbackQuery));
		getGameTable().get(beacon(callbackQuery)).getMediatorWallet().setStatMediator(true);

		Message message = callbackQuery.getMessage();
		try 
		{
			Message toTrash = execute( SendMessage.builder()
					.text(getGameTable().get(beacon(callbackQuery)).getControlPanel().getObjectInfo(ObjectType.STATS))
					.chatId(message.getChatId().toString())
					.build());

			getGameTable().get(beacon(callbackQuery)).getTrashCan().toMainСircle(toTrash.getMessageId());

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

	}

	private void continuedCreation(CallbackQuery callbackQuery)
	{

	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void characterMenu(CallbackQuery callbackQuery) 
	{

		getGameTable().get(beacon(callbackQuery)).getMediatorWallet().mediatorBreak();
		clean(Circle.ALL, beacon(callbackQuery));
		Message message = callbackQuery.getMessage();
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		boolean spell = true;
		if(getGameTable().get(beacon(callbackQuery)).getActualGameCharacter().getWorkmanship().getMySpells().size() == 0) spell = false;

		if(spell == true) {

			buttons.add(Arrays.asList(InlineKeyboardButton.builder()
					.text("SPELLS")
					.callbackData(spellMenu)
					.build(),
					InlineKeyboardButton.builder()
					.text("FETURE")
					.callbackData(skillMenu)
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


		String permanentBuff = "";
		for(String buff: getGameTable().get(beacon(callbackQuery)).getActualGameCharacter().getPermanentBuffs())
		{
			permanentBuff += buff + "\n";
		}
		
		try 
		{
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

			getGameTable().get(beacon(callbackQuery)).getTrashCan().toHeroСircle(permanentBuffBlock.getMessageId());
			getGameTable().get(beacon(callbackQuery)).getTrashCan().toHeroСircle(mainBlock.getMessageId());

		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}
	}

	private void memoirsMenu(CallbackQuery callbackQuery)
	{
		clean(Circle.MAIN, beacon(callbackQuery));
		Message message = callbackQuery.getMessage();
		try {

			Message toTrash = execute(SendMessage.builder()
					.chatId(message.getChatId().toString())
					.text(getGameTable().get(beacon(callbackQuery)).getActualGameCharacter().getMyMemoirs())
					.build());


			getGameTable().get(beacon(callbackQuery)).getTrashCan().toMainСircle(toTrash.getMessageId());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void skillMenu(CallbackQuery callbackQuery) 
	{
		getGameTable().get(beacon(callbackQuery)).getMediatorWallet().mediatorBreak();
		clean(Circle.MAIN, beacon(callbackQuery));

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
						.callbackData(skillMenu)
						.build()));

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

			getGameTable().get(beacon(callbackQuery)).getTrashCan().toMainСircle(toTrash.getMessageId());

		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}
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