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
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.MessageId;
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
import com.dnd.Source;
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

		clean(gameTable.getTrashCan().getSmallСircle());

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

		if(list.size() > 0) {
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
					clean(gameTable.getTrashCan().getMainСircle());
					startCase(message);
					return;
				case "/myCharacters":
					clean(gameTable.getTrashCan().getMainСircle());
					characterCase(message);
					return;
				}
			}
		}
	}

	private void startCase(Message message) throws TelegramApiException
	{
		gameTable.setChatId(message.getChatId());
		CharacterFactory.setMyCharactersDir("" + gameTable.getChatId());

		gameTable.getMediatorWallet().mediatorBreak();
		clean(gameTable.getTrashCan().getAllСircle());

		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
		//replyKeyboardMarkup.setOneTimeKeyboard(true);
		List<KeyboardRow> buttons = new ArrayList<>();
		KeyboardRow keyboardFirstRow = new KeyboardRow();
		keyboardFirstRow.add(new KeyboardButton("/myCharacters"));

		buttons.add(keyboardFirstRow);

		replyKeyboardMarkup.setKeyboard(buttons);
		replyKeyboardMarkup.setResizeKeyboard(true);
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

			finishChar(message);
			return;

		case classMediatorKey:

			finishClass(message); 	
			return;

		case statMediatorkey:

			finishHero(message);
			return;



		}
	}

	private void finishChar(Message message)
	{
		gameTable.setActualGameCharacter(CharacterFactory.create(message.getText()));
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
			gameTable.getTrashCan().toMainСircle(message.getMessageId());
			gameTable.getTrashCan().toSmallСircle(botAnswer.getMessageId());
		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}

		CharacterFactory.save(gameTable.getActualGameCharacter());
	}

	private void finishClass(Message message)
	{
		int lvl = Integer.parseInt(message.getText().replaceAll(keyLvl, "$1"));
		if(lvl < 0 && lvl > 20) lvl = 1;

		ClassFactory.create(gameTable.getActualGameCharacter(),
				gameTable.getCuttingBoard().getClassBeck(),
				lvl,
				gameTable.getCuttingBoard().getArcherypeBeck());

		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
		buttons.add(Arrays.asList(InlineKeyboardButton.builder()
				.text("Yes that's right")
				.callbackData(startRaceKey)
				.build()));


		try 
		{
			Message botAnswer = execute(SendMessage.builder()
					.text("Are you satisfied with this option? If not, select another option above." + "\r\n"+ ClassFactory.getObgectInfo(gameTable.getActualGameCharacter().getClassDnd()))
					.chatId(message.getChatId().toString())
					.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
					.build()
					);
			gameTable.getTrashCan().toMainСircle(message.getMessageId());
			gameTable.getTrashCan().toSmallСircle(botAnswer.getMessageId());
		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}

		CharacterFactory.save(gameTable.getActualGameCharacter());

	}

	private void finishHero(Message message)
	{
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
								+ " 11/12/13/14/15 /16 \r\n"
								+ " str 11 dex 12 con 13 int 14 wis 15 cha 16 ")
						.chatId(message.getChatId().toString())
						.build());

				gameTable.getTrashCan().toMainСircle(toTrash.getMessageId());
				gameTable.getTrashCan().toMainСircle(message.getMessageId());
			}
			else
			{
				gameTable.getActualGameCharacter().setStats(stats.get(0), stats.get(1), stats.get(2), stats.get(3), stats.get(4), stats.get(5));
				gameTable.getMediatorWallet().mediatorBreak();
				Message toTrash = execute(SendMessage.builder()
						.text("Nice, your HERO ready for adventure!")
						.chatId(message.getChatId().toString())
						.build());

				gameTable.getTrashCan().toMainСircle(message.getMessageId());
				gameTable.getTrashCan().toMainСircle(toTrash.getMessageId());
				clean(gameTable.getTrashCan().getMainСircle());

			}
		} 
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}
		CharacterFactory.save(gameTable.getActualGameCharacter());
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
			return;

		case characterCreateKey:
			startCreateHero(callbackQuery);
			return;

		case startClassKey:
			startCreateClass(callbackQuery);
			return;

		case classKey:
			chooseArchetype(callbackQuery);
			return;

		case archetypeKey:
			chooseLvlClass(callbackQuery);
			return;

		case startRaceKey:
			startCreateRace(callbackQuery);
			return;

		case raceKey:
			chooseSubRace(callbackQuery);
			return;

		case subRaceKey:
			finishRace(callbackQuery);
			return;

		case finishHeroKey:
			startStats(callbackQuery);
			return;

		}

	}

	private void startCreateHero(CallbackQuery callbackQuery)
	{
		gameTable.getMediatorWallet().mediatorBreak();
		clean(gameTable.getTrashCan().getMainСircle());
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
		clean(gameTable.getTrashCan().getMainСircle());

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
					.callbackData(archetypeKey + ClassFactory.getArchetypeArray(gameTable.getCuttingBoard().getClassBeck())[i].replaceAll(keyAnswer + ".txt", "$1"))
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
		clean(gameTable.getTrashCan().getMainСircle());

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
			else 
			{

				Message toTrash = execute(SendMessage.builder()
						.text(lvl+"??? I see you're new here. Let's start with lvl 1. The main thing now is not to miss with Race..." + gameTable.getActualGameCharacter().getName() + "?")
						.chatId(message.getChatId().toString())
						.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
						.build()
						);

				gameTable.getTrashCan().toMainСircle(message.getMessageId());
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
		RaceFactory.create(gameTable.getActualGameCharacter(), gameTable.getCuttingBoard().getRace(), gameTable.getCuttingBoard().getSubRace());

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
		CharacterFactory.save(gameTable.getActualGameCharacter());
	}

	private void startStats(CallbackQuery callbackQuery)
	{
		gameTable.getMediatorWallet().mediatorBreak();
		clean(gameTable.getTrashCan().getMainСircle());
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

	private void downloadHero(CallbackQuery callbackQuery) 
	{
		if(gameTable.getActualGameCharacter() != null)
		{
			CharacterFactory.save(gameTable.getActualGameCharacter());
			gameTable.setActualGameCharacter(CharacterFactory.load(callbackQuery.getData())); 
			characterMenu(callbackQuery);

		}
	}

	private void characterMenu(CallbackQuery callbackQuery) 
	{
		Message message = callbackQuery.getMessage();
		try {
			Message charMenu = execute(SendMessage.builder()
					.chatId(message.getChatId().toString())
					.text(CharacterFactory.getObgectInfo(gameTable.getActualGameCharacter()))
					.build());
			gameTable.getTrashCan().toBigСircle(charMenu.getMessageId());
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
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