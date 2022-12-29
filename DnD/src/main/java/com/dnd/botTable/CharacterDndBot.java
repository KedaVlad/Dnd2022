package com.dnd.botTable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.dnd.botTable.actions.ArrayAction;
import com.dnd.botTable.actions.BotAction;
import com.dnd.dndTable.factory.Json;

public class CharacterDndBot extends TelegramLongPollingBot implements KeyWallet
{
	private Map<Long, GameTable> gameTables = new HashMap<>();

	private GameTable initialize(Update update) throws TelegramApiException
	{
		Message message = null;

		if(update.hasCallbackQuery())
		{
			message = update.getCallbackQuery().getMessage();
		}
		else
		{
			message = update.getMessage();
		}
		long key = message.getChatId();
		if(!gameTables.containsKey(key))
		{
			gameTables.put(key, GameTable.create(message));
			greetings(message, gameTables.get(key));
		}
		//create else if for size gameTables for control memory
		return gameTables.get(key);
	}

	public void onUpdateReceived(Update update) 
	{ 
		try 
		{
			GameTable game = initialize(update);	
			if(game.getActualGameCharacter() != null) Log.add(game.getActualGameCharacter().getHp() + " HPHPHPHPHPHPHPHPHP");
			if(update.hasCallbackQuery())
			{
				handleCallback(update.getCallbackQuery(), game);
			}	
			else if(update.hasMessage()) 
			{
				handleMessage(update.getMessage(),game);
			}
			clean(game);
		}	
		catch (Exception e) 
		{		
			e.printStackTrace();		
		}
	}

	private void clean(GameTable game) throws InterruptedException, TelegramApiException
	{

		if(game.getScript().getTrash().size() > 0)
		{
			List<Integer> target = game.getScript().throwAwayTrash();
			for(Integer message: target)
			{
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

	private void handleCallback(CallbackQuery callbackQuery, GameTable game) throws TelegramApiException
	{
		String string = callbackQuery.getData();
		if(string.contains(eliminationKey + ""))
		{
			String target = string.replaceAll("(.*)" + eliminationKey, "$1");
			game.getScript().dateche(target);
			return;
		}
		else if(string.contains(buttonsKey + ""))
		{
			String regex = "([a-zA-Z]+)" + buttonsKey + "(.+)";
			String target = string.replaceAll(regex, "$1");
			String callback = string.replaceAll(regex, "$2");
			game.getScript().beackTo(target);
			templateExecuter(callbackQuery.getMessage(), game, game.makeAction(game.getScript().getAction().continueAction(callback)));
		}
		else
		{
			String regex = "([a-zA-Z]+)(.*)";
			String target = string.replaceAll(regex, "$1");
			String callback = string.replaceAll(regex, "$2");
			Log.add("SHOOD BACK TO " + target);
			Log.add("CALLBACK " + callback);
			game.getScript().beackTo(target);
			templateExecuter(callbackQuery.getMessage(), game, game.makeAction(game.getScript().getAction().continueAction(callback)));
		}
	}

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
					game.getScript().beackTo(message.getChatId());
					game.getScript().prepare(message.getMessageId());	
					templateExecuter(message, game, BotAction.create("START", start , true, false, startText, null));	
					return;
				case "/myCharacters":
					game.getScript().beackTo(start);
					game.getScript().prepare(message.getMessageId());
					templateExecuter(message, game, game.characterCase());
					return;
				}
			}
		}
		else if(game.getScript().isMediator())
		{
			game.getScript().toAct(message.getMessageId());
			templateExecuter(message, game, game.makeAction(game.getScript().getAction().continueAction(message.getText())));
		}
		else
		{


			if(game.getActualGameCharacter() != null)
			{
				game.getActualGameCharacter().addMemoirs(message.getText());
				Message toTrash = execute(SendMessage.builder()
						.text("I will put it in your memoirs")
						.chatId(message.getChatId().toString())
						.build()
						);
				game.getScript().prepare(toTrash.getMessageId());
				game.getScript().prepare(message.getMessageId());
			}
			else
			{
				Message toTrash = execute(SendMessage.builder()
						.text("Until you have an active hero, you have no memoirs. Therefore, unfortunately, this message recognize oblivion.")
						.chatId(message.getChatId().toString())
						.build()
						);
				game.getScript().prepare(toTrash.getMessageId());
				game.getScript().prepare(message.getMessageId());

			}
		}
	}

	private void templateExecuter(Message message, GameTable game, Action target) throws TelegramApiException
	{
		if(target.hasBack())
		{
			game.getScript().beackTo(target.backTo()[0]);
			if(target.backTo()[1] != null)
			{
				target = game.makeAction(game.getScript().getAction().continueAction(target.backTo()[1]));
			}
		}

		game.getScript().up(target);	

		if(target instanceof ArrayAction)
		{
			ArrayAction array = (ArrayAction) target;
			for(Action action: array.getPool())
			{
				Log.add(action + " --- " + action.hasButtons());
				if(action.hasButtons())
				{
					List<List<InlineKeyboardButton>> buttons = buttons(action, action.key);
					Message act = execute(SendMessage.builder()
							.text(action.text)
							.chatId(message.getChatId().toString())
							.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
							.build());
					game.getScript().toAct(act.getMessageId());

				}
				else
				{
					Message act = execute(SendMessage.builder()
							.text(action.text)
							.chatId(message.getChatId().toString())
							.build());
					game.getScript().toAct(act.getMessageId());
				}
			}
		}
		else
		{
			if(target.mainAct && target.hasButtons())
			{
				List<List<InlineKeyboardButton>> buttons = buttons(target, buttonsKey);
				Message act = execute(SendMessage.builder()
						.text(target.text)
						.chatId(message.getChatId().toString())
						.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
						.build());
				game.getScript().toAct(act.getMessageId());

			}
			else if(target.mainAct)
			{
				Message act = execute(SendMessage.builder()
						.text(target.text)
						.chatId(message.getChatId().toString())
						.build());
				game.getScript().toAct(act.getMessageId());
			}
			else
			{
				List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
				buttons.add(Arrays.asList(InlineKeyboardButton.builder()
						.text("ELIMINATION")
						.callbackData(target.name + eliminationKey)
						.build()));
				Message act = execute(SendMessage.builder()
						.text(target.text)
						.chatId(message.getChatId().toString())
						.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
						.build());
				game.getScript().toDatached(target.name, act.getMessageId());
			}
		}
	}

	private List<List<InlineKeyboardButton>> buttons(Action action, long key)
	{
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
		String[][] target = action.buildButtons();
		for(String[] line: target)
		{
			if(line.length == 1)
			{
				buttons.add(Arrays.asList(InlineKeyboardButton.builder()
						.text(line[0])
						.callbackData(action.getName() + key + line[0])
						.build()));
			}
			else if(line.length == 2)
			{
				buttons.add(Arrays.asList(InlineKeyboardButton.builder()
						.text(line[0])
						.callbackData(action.getName() + key + line[0])
						.build(),
						InlineKeyboardButton.builder()
						.text(line[1])
						.callbackData(action.getName() + key + line[1])
						.build()));
			}
			else if(line.length == 3)
			{
				buttons.add(Arrays.asList(InlineKeyboardButton.builder()
						.text(line[0])
						.callbackData(action.getName() + key + line[0])
						.build(),
						InlineKeyboardButton.builder()
						.text(line[1])
						.callbackData(action.getName() + key + line[1])
						.build(),
						InlineKeyboardButton.builder()
						.text(line[2])
						.callbackData(action.getName() + key + line[2])
						.build()));
			}
			else if(line.length == 4)
			{
				buttons.add(Arrays.asList(InlineKeyboardButton.builder()
						.text(line[0])
						.callbackData(action.getName() + key + line[0])
						.build(),
						InlineKeyboardButton.builder()
						.text(line[1])
						.callbackData(action.getName() + key + line[1])
						.build(),
						InlineKeyboardButton.builder()
						.text(line[2])
						.callbackData(action.getName() + key + line[2])
						.build(),
						InlineKeyboardButton.builder()
						.text(line[3])
						.callbackData(action.getName() + key + line[3])
						.build()));
			}
			else if(line.length == 5)
			{
				buttons.add(Arrays.asList(InlineKeyboardButton.builder()
						.text(line[0])
						.callbackData(action.getName() + key + line[0])
						.build(),
						InlineKeyboardButton.builder()
						.text(line[1])
						.callbackData(action.getName() + key + line[1])
						.build(),
						InlineKeyboardButton.builder()
						.text(line[2])
						.callbackData(action.getName() + key + line[2])
						.build(),
						InlineKeyboardButton.builder()
						.text(line[3])
						.callbackData(action.getName() + key + line[3])
						.build(),
						InlineKeyboardButton.builder()
						.text(line[4])
						.callbackData(action.getName() + key + line[4])
						.build()));
			}
			else if(line.length == 6)
			{
				buttons.add(Arrays.asList(InlineKeyboardButton.builder()
						.text(line[0])
						.callbackData(action.getName() + key + line[0])
						.build(),
						InlineKeyboardButton.builder()
						.text(line[1])
						.callbackData(action.getName() + key + line[1])
						.build(),
						InlineKeyboardButton.builder()
						.text(line[2])
						.callbackData(action.getName() + key + line[2])
						.build(),
						InlineKeyboardButton.builder()
						.text(line[3])
						.callbackData(action.getName() + key + line[3])
						.build(),
						InlineKeyboardButton.builder()
						.text(line[4])
						.callbackData(action.getName() + key + line[4])
						.build(),
						InlineKeyboardButton.builder()
						.text(line[5])
						.callbackData(action.getName() + key + line[5])
						.build()));
			}
			else if(line.length == 7)
			{
				buttons.add(Arrays.asList(InlineKeyboardButton.builder()
						.text(line[0])
						.callbackData(action.getName() + key + line[0])
						.build(),
						InlineKeyboardButton.builder()
						.text(line[1])
						.callbackData(action.getName() + key + line[1])
						.build(),
						InlineKeyboardButton.builder()
						.text(line[2])
						.callbackData(action.getName() + key + line[2])
						.build(),
						InlineKeyboardButton.builder()
						.text(line[3])
						.callbackData(action.getName() + key + line[3])
						.build(),
						InlineKeyboardButton.builder()
						.text(line[4])
						.callbackData(action.getName() + key + line[4])
						.build(),
						InlineKeyboardButton.builder()
						.text(line[5])
						.callbackData(action.getName() + key + line[5])
						.build(),
						InlineKeyboardButton.builder()
						.text(line[6])
						.callbackData(action.getName() + key + line[6])
						.build()));
			}
		}
		return buttons;
	}

	private void greetings(Message message, GameTable game) throws TelegramApiException
	{
		List<KeyboardRow> buttons = new ArrayList<>();
		KeyboardRow keyboardFirstRow = new KeyboardRow();
		keyboardFirstRow.add(new KeyboardButton("/myCharacters"));
		buttons.add(keyboardFirstRow);

		Message act = execute(SendMessage.builder()
				.text("Greetings!")
				.replyMarkup(ReplyKeyboardMarkup.builder()
						.oneTimeKeyboard(true)
						.keyboard(buttons)
						.resizeKeyboard(true)
						.build())
				.chatId(message.getChatId().toString())
				.build());

		game.getScript().toAct(act.getMessageId());
	}

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
		bot.gameTables = Json.restor();
		/*
		while(true)
		{
			Json.backup(bot.gameTables);
			Thread.sleep(5000); 
			System.out.println("*********************************************************************************************************************");
		}
		 */	
	}
}