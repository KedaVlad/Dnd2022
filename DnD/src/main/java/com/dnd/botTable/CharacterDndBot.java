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
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
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
import com.dnd.dndTable.rolls.actions.HeroAction;
import com.dnd.dndTable.factory.Json;

public class CharacterDndBot extends TelegramLongPollingBot implements KeyWallet
{

	private Map<Long, GameTable> gameTable = new HashMap<>();

	//EditMessageText.builder().build();

	//EditMessageReplyMarkup.builder().chatId(beacon).messageId(message).replyMarkup(null).build();

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

	private void clean(GameTable game) throws InterruptedException, TelegramApiException
	{

		if(game.getScript().getTrash().size() > 0)
		{
			List<Integer> target = game.getScript().throwAwayTrash();
			Log.add(target);
			for(Integer message: target)
			{
				Log.add("Clean" + message);
				execute(DeleteMessage.builder()
						.chatId(game.getChatId())
						.messageId(message)
						.build());
			}
		}
		else
		{
			game.getScript().throwAwayTrash();
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void onUpdateReceived(Update update) 
	{ 
		if(!getGameTable().containsKey(beacon(update)))
		{
			getGameTable().put(beacon(update), GameTable.create(beacon(update)));
		}

		GameTable game = game(beacon(update));

		try 
		{

			if(update.hasCallbackQuery())
			{
				handleCallback(update.getCallbackQuery(), game);
			}
			else if(gameTable.containsKey(beacon(update)) && update.hasMessage() && (game.getMediatorWallet().checkMediator() == true)) 
			{
				hendlerMediator(update.getMessage(),game);
			}		
			else if(update.hasMessage()) 
			{
				handleMessage(update.getMessage(),game);
			}

			clean(game);
			Log.add(game(beacon(update)).getScript());

		}	
		catch (Exception e) 
		{		
			e.printStackTrace();		
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void handleMessage(Message message, GameTable game) throws TelegramApiException, InterruptedException
	{
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
					startCase(message, game);
					return;
				case "/myCharacters":
					characterCase(message, game);
					return;
				}
			}
		}
		else
		{


			if(game.isCheckChar())
			{
				game.getActualGameCharacter().setMyMemoirs(message.getText());
				Message toTrash = execute(SendMessage.builder()
						.text("I will put it in your memoirs")
						.chatId(message.getChatId().toString())
						.build()
						);
				game.getScript().prepare(toTrash.getMessageId());
			}
			else
			{
				Message toTrash = execute(SendMessage.builder()
						.text("Until you have an active hero, you have no memoirs. Therefore, unfortunately, this message recognize oblivion.")
						.chatId(message.getChatId().toString())
						.build()
						);
				game.getScript().prepare(toTrash.getMessageId());

			}
		}
	}

	private void startCase(Message message, GameTable game) throws TelegramApiException, InterruptedException
	{

		game.getScript().goTo("Base", true);	
		game.getScript().prepare(message.getMessageId());
		game.getScript().goTo(start, true);	

		List<KeyboardRow> buttons = new ArrayList<>();
		KeyboardRow keyboardFirstRow = new KeyboardRow();
		keyboardFirstRow.add(new KeyboardButton("/myCharacters"));
		buttons.add(keyboardFirstRow);

		Message act = execute(SendMessage.builder()
				.text("Greetings!\r\n" + "/myCharacters - This command leads to your character library,"
						+ " where you can create and choose which character you play.")
				.replyMarkup(ReplyKeyboardMarkup.builder()
						.oneTimeKeyboard(true)
						.keyboard(buttons)
						.resizeKeyboard(true)
						.build())
				.chatId(message.getChatId().toString())
				.build());

		game.getScript().toAct(act.getMessageId());
	}

	private void characterCase(Message message, GameTable game) throws TelegramApiException 
	{

		game.getControlPanel().cleanLocalData();
		game.getScript().prepare(message.getMessageId());

		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		buttons.add(Arrays.asList(InlineKeyboardButton.builder()
				.text("CREATE")
				.callbackData(characterCreateKey)
				.build()));


		Message act = null;

		if(game.getSavedCharacter().size() != 0) 
		{
			for(String name: game.getSavedCharacter().keySet())
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
		else if(game.getSavedCharacter().size() == 0)
		{
			act = execute(
					SendMessage.builder()
					.text("You don't have a Hero yet, my friend. But after you CREATE them, you can find them here.")
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build());

		}
		game.getScript().goTo(start, dock);
		game.getScript().toAct(act.getMessageId());
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void hendlerMediator(Message message, GameTable game) throws TelegramApiException ///<-
	{
		String key = game.getMediatorWallet().findMediator();

		switch(key)
		{

		case characterMediatorKey:
			finishCharacter(message, game);
			return;

		case classMediatorKey:
			finishClass(message, game); 
			return;

		case statMediatorKey:
			finishStat(message, game);
			return;

		case hpMediatorKey:
			finishHp(message, game);
			return;


		}
	}

	private void finishCharacter(Message message, GameTable game) throws TelegramApiException
	{
		game.setActualGameCharacter(CharacterDnd.create(message.getText()));
		game.getActualGameCharacter().setMyMemoirs(game.getActualGameCharacter().getName() + "\n\n");
		game.getScript().toAct(message.getMessageId());
		game.getScript().goTo(characterCreateKey, dock);
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		buttons.add(Arrays.asList(InlineKeyboardButton.builder()
				.text("Yes that's right")
				.callbackData(startClassKey)
				.build()));

		Message act = execute(SendMessage.builder()
				.text("So can I call you - " + game.getActualGameCharacter().getName() + "? If not, repeat your name.")
				.chatId(message.getChatId().toString())
				.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
				.build());

		game.getScript().toAct(act.getMessageId());

	}

	private void finishClass(Message message, GameTable game) throws TelegramApiException
	{
		game.getScript().goTo(finishClassKey, dock);
		game.getScript().toAct(message.getMessageId());

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
			game.getControlPanel().setClassLvl(1);

			act = execute(SendMessage.builder()
					.text(lvl+"??? I see you're new here. Let's start with lvl 1. Are you satisfied with this option?"
							+ " If not, select another option above." + "\r\n"
							+ game.getControlPanel().getObjectInfo(ObjectType.CLASS))
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build());
		}
		else
		{
			game.getControlPanel().setClassLvl(lvl);

			act = execute(SendMessage.builder()
					.text("Are you satisfied with this option? If not, select another option above." + "\r\n"
							+ game.getControlPanel().getObjectInfo(ObjectType.CLASS))
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build());
		} 
		game.getScript().toAct(act.getMessageId());
	}

	private void finishStat(Message message, GameTable game) throws TelegramApiException
	{
		game.getScript().goTo(startStatsKey, dock);
		game.getScript().toAct(message.getMessageId());

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
			game.getActualGameCharacter().setMyStat(stats.get(0), stats.get(1), stats.get(2), stats.get(3), stats.get(4), stats.get(5));
			game.getMediatorWallet().mediatorBreak();

			int stableHp = Dice.stableStartHp(game.getActualGameCharacter());
			int randomHp = Dice.randomStartHp(game.getActualGameCharacter());

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

			game.getMediatorWallet().setHpMediator(true);
		}
		game.getScript().toAct(act.getMessageId());
	}

	private void finishHp(Message message, GameTable game) throws TelegramApiException
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
			answer = (int) Dice.stableStartHp(game.getActualGameCharacter()); 

			Message act = execute(SendMessage.builder()
					.text("Nice try... I see U very smart, but you will get stable " + answer + " HP.")
					.chatId(message.getChatId().toString())
					.build());

			game.getScript().toAct(act.getMessageId());
		}

		game.getActualGameCharacter().setHp(answer);
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

		game.getScript().toAct(message.getMessageId()); //<- if from callback provoke error 404
		game.getScript().toAct(toTrash.getMessageId());
		game.getMediatorWallet().mediatorBreak();

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void handleCallback(CallbackQuery callbackQuery, GameTable game) throws InterruptedException, TelegramApiException  ///<-
	{
		String key = callbackQuery.getData().replaceAll(keyCheck + keyAnswer, "$1");

		switch (key)
		{
		case startAction:
			startAction(callbackQuery, game);
			return;

		case action:
			action(callbackQuery, game);
			return;

		case characterCaseKey:
			downloadHero(callbackQuery, game);
			return;

			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case characterCreateKey:
			startCreateHero(callbackQuery, game);
			return;

		case startClassKey:
			startCreateClass(callbackQuery, game);
			return;

		case chooseArchetypeClassKey:
			chooseArchetype(callbackQuery, game);
			return;

		case finishClassKey:
			chooseLvlClass(callbackQuery, game);
			return;

		case startRaceKey:
			startCreateRace(callbackQuery, game);
			return;

		case chooseSubRaceKey:
			chooseSubRace(callbackQuery, game);
			return;

		case finishRaceKey:
			finishRace(callbackQuery, game);
			return;

		case hpMediatorKey:
			finishHp(callbackQuery.getMessage(), game);
			return;

		case startStatsKey:
			startStats(callbackQuery, game);
			return;
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case menuKey:
			characterMenu(callbackQuery, game);
			game.save();
			return;

		case featureMenu:
			featureMenu(callbackQuery, game);
			return;

		case memoirsMenu:
			memoirsMenu(callbackQuery, game);
			return;

		}

	}

	private void templateExecuter(Message message, GameTable game, Template target) throws TelegramApiException
	{
		game.getScript().goTo(target.getAction(), target.isMainAct());
		if(target.isMainAct() && target.hasButtons())
		{
			List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

			for(String key: target.getButtons())
			{
				buttons.add(Arrays.asList(InlineKeyboardButton.builder()
						.text(key)
						.callbackData(action + key)
						.build()));
			}

			Message act = execute(SendMessage.builder()
					.text(target.getText())
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build());

			game.getScript().toAct(act.getMessageId());

		}
		else if(target.isMainAct())
		{
			Message act = execute(SendMessage.builder()
					.text(target.getText())
					.chatId(message.getChatId().toString())
					.build()
					);
			game.getScript().toAct(act.getMessageId());
		}
		else
		{
			List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

			buttons.add(Arrays.asList(InlineKeyboardButton.builder()
					.text("Elimination")
					.callbackData(eliminationKey + target.getName())
					.build()));

			Message act = execute(SendMessage.builder()
					.text(target.getText())
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build());

			game.getScript().toUndependet(act.getMessageId());
		}
	}

	private void action(CallbackQuery callbackQuery, GameTable game) throws TelegramApiException
	{
		String target = callbackQuery.getData().replaceAll(keyCheck + keyAnswer, "$2");
		game.getScript().goTo(target, true);
		Template template = game.getActualGameCharacter().act(game.getScript().getAction());
		templateExecuter(callbackQuery.getMessage(), game, template);
	}

	private void startAction(CallbackQuery callbackQuery, GameTable game) throws TelegramApiException
	{
		Template template = null;
		String key = callbackQuery.getData();
		Pattern pat = Pattern.compile(keyNumber);
		Matcher matcher = pat.matcher(key);
		int target = ((Integer) Integer.parseInt(matcher.group()));
		if(key.contains(weapon))
		{

		}
		else if(key.contains(feature))
		{
			List<Feature> features = game.getActualGameCharacter().getWorkmanship().getMyFeatures();
			template = game.getActualGameCharacter().registAction(features.get(target));
		}

		templateExecuter(callbackQuery.getMessage(), game, template);

	}

	private void downloadHero(CallbackQuery callbackQuery, GameTable game) throws InterruptedException, TelegramApiException 
	{
		game.load(callbackQuery.getData().replaceAll(keyCheck + keyAnswer, "$2"));
		callbackQuery.setData(game.readinessСheck());
		handleCallback(callbackQuery, game);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void startCreateHero(CallbackQuery callbackQuery, GameTable game) throws InterruptedException, TelegramApiException
	{
		game.save();
		game.getScript().goTo(start, characterCreateKey);
		game.getMediatorWallet().setCharacterCreateMediator(true);

		Message message = callbackQuery.getMessage();

		Message act = execute( SendMessage.builder()
				.text("Traveler, how should I call you?!\n(Write Hero name)")
				.chatId(message.getChatId().toString())
				.build());

		game.getScript().toAct(act.getMessageId());

	}

	private void startCreateClass(CallbackQuery callbackQuery, GameTable game) throws TelegramApiException
	{
		game.setCheсkChar(true);
		game.save();
		game.getMediatorWallet().mediatorBreak();
		game.getScript().goTo(start, startClassKey);

		Message message = callbackQuery.getMessage();
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		for(int i = 0; i < game.getControlPanel().getArray(ObjectType.CLASS).length; i++)
		{
			buttons.add(Arrays.asList(InlineKeyboardButton.builder()
					.text(game.getControlPanel().getArray(ObjectType.CLASS)[i])
					.callbackData(chooseArchetypeClassKey + game.getControlPanel().getArray(ObjectType.CLASS)[i])
					.build()));
		}		

		Message act = execute(SendMessage.builder()
				.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
				.text("What is your class, " + game.getActualGameCharacter().getName() + "?")
				.chatId(message.getChatId().toString())

				.build()
				);

		game.getScript().toAct(act.getMessageId());


	}

	private void chooseArchetype(CallbackQuery callbackQuery, GameTable game) throws TelegramApiException
	{
		game.getControlPanel().setClassBeck(callbackQuery.getData().replaceAll(chooseArchetypeClassKey + keyAnswer, "$1"));
		game.getScript().goTo(chooseArchetypeClassKey, true);
		Message message = callbackQuery.getMessage();
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		for(int i = 0; i < game.getControlPanel().getArray(ObjectType.ARCHETYPE).length; i++)
		{
			buttons.add(Arrays.asList(InlineKeyboardButton.builder()
					.text(game.getControlPanel().getArray(ObjectType.ARCHETYPE)[i].replaceAll(keyAnswer + ".json", "$1"))
					.callbackData(finishClassKey + game.getControlPanel().getArray(ObjectType.ARCHETYPE)[i]
							.replaceAll(keyAnswer + ".txt", "$1"))
					.build()));
		}

		Message act = execute(
				SendMessage.builder()
				.text(game.getControlPanel().getClassBeck() + ", realy? Which one?")
				.chatId(message.getChatId().toString())
				.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
				.build());

		game.getScript().toAct(act.getMessageId());

	}

	private void chooseLvlClass(CallbackQuery callbackQuery, GameTable game) throws TelegramApiException 
	{
		game.getControlPanel().setArchetypeBeck(callbackQuery.getData().replaceAll(finishClassKey + keyAnswer, "$1"));
		game.getMediatorWallet().setClassCreateMediator(true);
		game.getScript().goTo(finishClassKey, true);

		Message message = callbackQuery.getMessage();

		Message act = execute( SendMessage.builder()
				.text("What LVL of Hero?(Write)")
				.chatId(message.getChatId().toString())
				.build());

		game.getScript().toAct(act.getMessageId());

	}

	private void startCreateRace(CallbackQuery callbackQuery, GameTable game) throws TelegramApiException
	{ 
		game.createClass();
		game.getActualGameCharacter().setMyMemoirs(game.getControlPanel().getObjectInfo(ObjectType.CLASS));
		game.getScript().goTo(start, startRaceKey);

		Message message = callbackQuery.getMessage();
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		for(int i = 1; i <= game.getControlPanel().getArray(ObjectType.RACE).length; i += 3)
		{
			if(((i + 1) > game.getControlPanel().getArray(ObjectType.RACE).length)
					&&((i + 2) > game.getControlPanel().getArray(ObjectType.RACE).length)) 
			{
				buttons.add(Arrays.asList(

						InlineKeyboardButton.builder()
						.text(game.getControlPanel().getArray(ObjectType.RACE)[i-1])
						.callbackData(chooseSubRaceKey + game.getControlPanel().getArray(ObjectType.RACE)[i-1])
						.build()));
				break;
			}
			else if((i + 2) > game.getControlPanel().getArray(ObjectType.RACE).length)
			{
				buttons.add(Arrays.asList(

						InlineKeyboardButton.builder()
						.text(game.getControlPanel().getArray(ObjectType.RACE)[i-1])
						.callbackData(chooseSubRaceKey + game.getControlPanel().getArray(ObjectType.RACE)[i-1])
						.build(),
						InlineKeyboardButton.builder()
						.text(game.getControlPanel().getArray(ObjectType.RACE)[i])
						.callbackData(chooseSubRaceKey + game.getControlPanel().getArray(ObjectType.RACE)[i])
						.build()));
				break;
			}
			else
			{
				buttons.add(Arrays.asList(

						InlineKeyboardButton.builder()
						.text(game.getControlPanel().getArray(ObjectType.RACE)[i-1])
						.callbackData(chooseSubRaceKey + game.getControlPanel().getArray(ObjectType.RACE)[i-1])
						.build(),
						InlineKeyboardButton.builder()
						.text(game.getControlPanel().getArray(ObjectType.RACE)[i])
						.callbackData(chooseSubRaceKey + game.getControlPanel().getArray(ObjectType.RACE)[i])
						.build(),
						InlineKeyboardButton.builder()
						.text(game.getControlPanel().getArray(ObjectType.RACE)[i+1])
						.callbackData(chooseSubRaceKey + game.getControlPanel().getArray(ObjectType.RACE)[i+1])
						.build()));
			}
		}

		int lvl = game.getActualGameCharacter().getClassDnd().getLvl();

		if(lvl < 21 && lvl > 0)
		{
			Message act = execute(SendMessage.builder()
					.text("From what family you are?")
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build()
					);

			game.getScript().toAct(act.getMessageId());
		}
	}

	private void chooseSubRace(CallbackQuery callbackQuery, GameTable game) throws TelegramApiException
	{
		game.getControlPanel().setRace(callbackQuery.getData().replaceAll(chooseSubRaceKey + keyAnswer, "$1"));
		game.getScript().goTo(finishRaceKey, true);

		Message message = callbackQuery.getMessage();
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		for(int i = 0; i < game.getControlPanel().getArray(ObjectType.SUBRACE).length; i++)
		{
			buttons.add(Arrays.asList(InlineKeyboardButton.builder()
					.text(game.getControlPanel().getArray(ObjectType.SUBRACE)[i].replaceAll(keyAnswer + ".json", "$1"))
					.callbackData(finishRaceKey + game.getControlPanel().getArray(ObjectType.SUBRACE)[i].replaceAll(keyAnswer + ".txt", "$1"))
					.build()));
		}

		Message act = execute(
				SendMessage.builder()
				.text(game.getControlPanel().getRace() + "? More specifically?")
				.chatId(message.getChatId().toString())
				.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
				.build());

		game.getScript().toAct(act.getMessageId());
	}

	private void finishRace(CallbackQuery callbackQuery, GameTable game) throws TelegramApiException
	{

		game.getScript().goTo(finishRaceKey, dock);
		game.getControlPanel().setSubRace(callbackQuery.getData().replaceAll(finishRaceKey + keyAnswer,"$1"));

		Message message = callbackQuery.getMessage();
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
		buttons.add(Arrays.asList(InlineKeyboardButton.builder()
				.text("Yes that's right")
				.callbackData(startStatsKey)
				.build()));

		Message act = execute(SendMessage.builder()
				.text("Are you satisfied with this option? If not, select another option above." 
						+ "\r\n"+ game.getControlPanel().getObjectInfo(ObjectType.RACE))
				.chatId(message.getChatId().toString())
				.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
				.build());

		game.getScript().toAct(act.getMessageId());

	}

	private void startStats(CallbackQuery callbackQuery, GameTable game) throws TelegramApiException
	{
		game.createRace();
		game.getActualGameCharacter().setMyMemoirs(game.getControlPanel().getObjectInfo(ObjectType.RACE));
		game.getMediatorWallet().setStatMediator(true);
		game.getScript().goTo(start, startStatsKey);

		Message message = callbackQuery.getMessage();

		Message act = execute( SendMessage.builder()
				.text(game.getControlPanel().getObjectInfo(ObjectType.STATS))
				.chatId(message.getChatId().toString())
				.build());

		game.getScript().toAct(act.getMessageId());
	}

	private void continuedCreation(CallbackQuery callbackQuery)
	{

	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void characterMenu(CallbackQuery callbackQuery, GameTable game) throws TelegramApiException 
	{

		game.getMediatorWallet().mediatorBreak();
		game.getScript().goTo(start, menuKey);

		Message message = callbackQuery.getMessage();
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		boolean spell = true;
		if(game.getActualGameCharacter().getWorkmanship().getMySpells().size() == 0) spell = false;

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
		for(String buff: game.getActualGameCharacter().getPermanentBuffs())
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
				.text(game.getActualGameCharacter().getMenu())
				.chatId(message.getChatId().toString())
				.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
				.build());

		game.getScript().toAct(permanentBuffBlock.getMessageId());
		game.getScript().toAct(mainBlock.getMessageId());

	}

	private void memoirsMenu(CallbackQuery callbackQuery, GameTable game) throws TelegramApiException
	{
		game.getScript().goTo(menuKey,  memoirsMenu);
		Message message = callbackQuery.getMessage();

		Message act = execute(SendMessage.builder()
				.chatId(message.getChatId().toString())
				.text(game.getActualGameCharacter().getMyMemoirs())
				.build());

		game.getScript().toAct(act.getMessageId());
	}

	private void featureMenu(CallbackQuery callbackQuery, GameTable game) throws TelegramApiException 
	{
		game.getScript().goTo(menuKey,  featureMenu);
		game.getMediatorWallet().mediatorBreak();

		Message message = callbackQuery.getMessage();
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		String answer = "Those is your feature \n";
		if(game.getActualGameCharacter().getWorkmanship().getMyFeatures().isEmpty())
		{
			Log.add("skillMenu 0 size:(");
		}
		else
		{
			List<Feature> target = game.getActualGameCharacter().getWorkmanship().getMyFeatures();
			for(int i = 0; i < target.size(); i++)
			{
				buttons.add(Arrays.asList(InlineKeyboardButton.builder()
						.text( target.get(i).getName())
						.callbackData(startAction + target.get(i).objectKey() + i)
						.build()));
			}
			/*	for (Feature skill: getGameTable().get(beacon(callbackQuery)).getActualGameCharacter().getWorkmanship().getMyFeatures())
			{
				buttons.add(Arrays.asList(InlineKeyboardButton.builder()
						.text( skill.getName())
						.callbackData(skill.objectKey())
						.build()));

			}*/

		}

		Message act = execute(
				SendMessage.builder()
				.text(answer)
				.chatId(message.getChatId().toString())
				.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
				.build());

		game.getScript().toAct(act.getMessageId());


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