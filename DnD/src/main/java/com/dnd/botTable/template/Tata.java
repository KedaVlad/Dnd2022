package com.dnd.botTable.template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import com.dnd.KeyWallet;
import com.dnd.dndTable.factory.Json;
import com.dnd.dndTable.rolls.actions.HeroAction;
import com.fasterxml.jackson.core.JsonProcessingException;

public class Tata implements KeyWallet
{
	private String key;
	private String text;
	private boolean mainAct;
	private boolean mediator;
	private List<List<InlineKeyboardButton>> buttons;
	
	
	
	public static void main(String[] args) throws JsonProcessingException 
	{
		Tata tata = new Tata();
		tata.key = startClassKey;

		tata.buttons = new ArrayList<>();

	
		tata.buttons.add(Arrays.asList(InlineKeyboardButton.builder()
					.text("pam, pam")
					.callbackData("1231233")
					.build()));
		
		tata.mainAct = true;
		
		System.out.println(Json.stingify(Json.toJson(tata)));

	}
	
	
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<List<InlineKeyboardButton>> getButtons() {
		return buttons;
	}
	public void setButtons(List<List<InlineKeyboardButton>> buttons) {
		this.buttons = buttons;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isMainAct() {
		return mainAct;
	}
	public void setMainAct(boolean mainAct) {
		this.mainAct = mainAct;
	}
	public boolean isMediator() {
		return mediator;
	}
	public void setMediator(boolean mediator) {
		this.mediator = mediator;
	}
}
