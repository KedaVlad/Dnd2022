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
import com.dnd.botTable.actions.BotAction;
import com.dnd.dndTable.ActionObject;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.workmanship.Feature;
import com.dnd.dndTable.rolls.Dice;
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
			if(update.hasCallbackQuery())
			{
				Log.add(game.isMediator());
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
		Log.add("CALL_BACK");
		String target = callbackQuery.getData();
		game.setMediator(false);
		if(target.contains(eliminationKey + ""))
		{
			target = target.replaceAll("(.*)" + eliminationKey, "$1");
			game.getScript().dateche(target);
			return;
		}
		else if(game.getScript().beackTo(target))
		{
		
		game.getScript().finishLast();
		}
		templateExecuter(callbackQuery.getMessage(), game, game.makeAction(game.getScript().getAction().continueAction(target)));
	}

	private void handleMessage(Message message, GameTable game) throws TelegramApiException, InterruptedException
	{
		Log.add("MESSAGE");
		if(game.isMediator())
		{
			game.getScript().goToMediator();
			templateExecuter(message, game, game.makeAction(game.getScript().getAction().continueAction(message.getText())));
		}
		else if(message.hasText() && message.hasEntities()) 
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
		else
		{


			if(game.getActualGameCharacter() != null)
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

	private void templateExecuter(Message message, GameTable game, Action target) throws TelegramApiException
	{
		Log.add(game.isMediator());
		game.getScript().goTo(target);
		if(target.mediator)
		{
			game.setMediator(true);
		}
		Log.add(game.isMediator());
		
		if(target.mainAct && target.hasButtons())
		{
			List<List<InlineKeyboardButton>> buttons = buttons(target.buildButtons());

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
					.build()
					);
			game.getScript().toAct(act.getMessageId());
		}
		else
		{
			List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

			buttons.add(Arrays.asList(InlineKeyboardButton.builder()
					.text("Elimination")
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

	private List<List<InlineKeyboardButton>> buttons(String[][] target)
	{
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

		for(String[] line: target)
		{
			if(line.length == 1)
			{
				buttons.add(Arrays.asList(InlineKeyboardButton.builder()
						.text(line[0])
						.callbackData(line[0])
						.build()));
			}
			else if(line.length == 2)
			{
				buttons.add(Arrays.asList(InlineKeyboardButton.builder()
						.text(line[0])
						.callbackData(line[0])
						.build(),
						InlineKeyboardButton.builder()
						.text(line[1])
						.callbackData(line[1])
						.build()));
			}
			else if(line.length == 3)
			{
				buttons.add(Arrays.asList(InlineKeyboardButton.builder()
						.text(line[0])
						.callbackData(line[0])
						.build(),
						InlineKeyboardButton.builder()
						.text(line[1])
						.callbackData(line[1])
						.build(),
						InlineKeyboardButton.builder()
						.text(line[2])
						.callbackData(line[2])
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

		while(true)
		{
			Json.backup(bot.gameTables);
			Thread.sleep(5000); 
			System.out.println("*********************************************************************************************************************");
		}
	}
}