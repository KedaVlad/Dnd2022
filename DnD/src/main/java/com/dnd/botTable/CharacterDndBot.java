package com.dnd.botTable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.commands.GetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.dnd.ButtonName;
import com.dnd.KeyWallet;
import com.dnd.botTable.actions.BaseAction;

public class CharacterDndBot extends TelegramLongPollingBot implements KeyWallet, ButtonName
{

	private Session SESSION = Session.getInstance();

	public void onUpdateReceived(Update update) 
	{ 
		try 
		{
			executor(SESSION.execute(update));	
			DataControler.save();
		}	
		catch (Exception e) 
		{		
			e.printStackTrace();		
		}
	}

	private void cleanTrash(User user) throws TelegramApiException
	{
		for(Integer i: user.SCRIPT.trashThrowOut())
		{
			execute(DeleteMessage.builder()
					.chatId(user.ID)
					.messageId(i)
					.build());
		}
	}

	private void executor(User user) throws TelegramApiException
	{
		if(user.CHARACTERS.hasCloud())
		{
			for(Act act: user.CHARACTERS.clouds.clouds)
			{
				Message message = execute(buildMessage(act, user.ID, CLOUD_ACT));
				act.toCircle(message.getMessageId());
			}
			user.CHARACTERS.clouds.cloudsWorked.addAll(user.CHARACTERS.clouds.clouds);
			user.CHARACTERS.clouds.clouds.clear();
		}
		Act act = user.SCRIPT.target;
		if(act != null)
		{
				if(act instanceof ArrayActs)
				{
					ArrayActs array = (ArrayActs) act;
					for(int i = 0; i < array.pool.length; i++)
					{
						Message arrAct = execute(buildMessage(array.pool[i], user.ID, array.keys[i]));
						act.toCircle(arrAct.getMessageId());
					}
				}
				else
				{
					Message message = execute(buildMessage(act, user.ID, MAIN_TREE));
					act.toCircle(message.getMessageId());
				}
		}
		cleanTrash(user);
	}

	private SendMessage buildMessage(Act act, long chatId, long key)
	{
		SendMessageBuilder builder = SendMessage.builder()
				.text(act.text)
				.chatId(chatId);

		if(act.action != null && act.action.hasButtons())
		{
			if(act.action.isReplyButtons())
			{
				builder.replyMarkup(replyKeyboard(act.action));
			}
			else
			{
				builder.replyMarkup(inlineKeyboard(act, key));
			}
		}
		return builder.build();
	}

	private InlineKeyboardMarkup inlineKeyboard(Act act, long key)
	{
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
		String[][] target = act.action.buildButtons();
		for(String[] line: target)
		{
			List<InlineKeyboardButton> buttonLine = new ArrayList<>();
			for(String button: line)
			{
				buttonLine.add(InlineKeyboardButton.builder()
						.text(button)
						.callbackData(act.name + key + button)
						.build());
			}
			buttons.add(buttonLine);
		}	
		return InlineKeyboardMarkup.builder().keyboard(buttons).build();
	}

	private ReplyKeyboardMarkup replyKeyboard(BaseAction action)
	{
		List<KeyboardRow> buttons = new ArrayList<>();
		String[][] target = action.buildButtons();
		for(String[] line: target)
		{
			KeyboardRow keyboardRow = new KeyboardRow();
			List<KeyboardButton> row = new ArrayList<>();
			for(String button: line)
			{
			row.add(
					KeyboardButton.builder()
					.text(button)
					.build());
			}
			keyboardRow.addAll(row);
			buttons.add(keyboardRow);
		}
		return ReplyKeyboardMarkup.builder().keyboard(buttons).resizeKeyboard(true).build();
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
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
		telegramBotsApi.registerBot(bot);
		bot.execute(new GetMyCommands());
		bot.SESSION.MODER.addTable(GameTable.create(123456789));
	}
}