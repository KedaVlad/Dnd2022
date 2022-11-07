package com.dnd.botTable;


import java.io.File;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.dnd.Dice;
import com.dnd.KeyWallet;
import com.dnd.Log;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.classDnd.ClassDnd;
import com.dnd.dndTable.factory.ControlPanel;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class GameTable implements KeyWallet, Serializable
{


	private static final long serialVersionUID = -8448308501857106456L;


	private long chatId;


	private Map<String, CharacterDnd> savedCharacter = new LinkedHashMap<>();
	@JsonIgnore
	private CharacterDnd actualGameCharacter;
	@JsonIgnore
	private boolean chekChar = false;
	@JsonIgnore
	private ControlPanel controlPanel = new ControlPanel();
	@JsonIgnore
	private MediatorWallet mediatorWallet = new MediatorWallet();
	@JsonIgnore
	private TrashCan trashCan = new TrashCan();

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public void createClass()
	{
		getControlPanel().createClass(actualGameCharacter);
		save();
		getMediatorWallet().mediatorBreak();
	}

	public void createRace()
	{
		getControlPanel().createRace(actualGameCharacter);
		save();
		getMediatorWallet().mediatorBreak();
	}

	public void lvlUp()
	{


	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String readinessСheck()
	{
		if(actualGameCharacter.getClassDnd() == null)
		{
			return startClassKey;
		}
		else if(actualGameCharacter.getMyRace() == null)
		{
			return startRaceKey;
		}
		else if(actualGameCharacter.getMyStat() == null)
		{
			return startStatsKey;
		}
		else if(actualGameCharacter.getHp() == 0)
		{
			Dice.stableHp(actualGameCharacter);
		}
		return menuKey;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public CharacterDnd getActualGameCharacter() 
	{
		return actualGameCharacter;
	}

	public void setActualGameCharacter(CharacterDnd actualGameCharacter) 
	{
		this.actualGameCharacter = actualGameCharacter;
	}

	public MediatorWallet getMediatorWallet() 
	{
		return mediatorWallet;
	}

	public void setMediatorWallet(MediatorWallet mediatorWallet) 
	{
		this.mediatorWallet = mediatorWallet;
	}

	public long getChatId() 
	{
		return chatId;
	}

	public void setChatId(long chatId) 
	{
		this.chatId = chatId;
	}

	public TrashCan getTrashCan() 
	{
		return trashCan;
	}

	public void setTrashCan(TrashCan trashCan) 
	{
		this.trashCan = trashCan;
	}

	public boolean isCheckChar() {

		if(actualGameCharacter == null)
		{
			return false;
		}
		else
		{
			return chekChar;
		}
	}

	public void setCheсkChar(boolean chekChar) {
		this.chekChar = chekChar;
	}

	public ControlPanel getControlPanel() {
		return controlPanel;
	}

	public static GameTable create(long chatId) {

		GameTable gameTable = new GameTable();

		gameTable.setChatId(chatId);
		gameTable.getMediatorWallet().mediatorBreak();
		gameTable.setCheсkChar(false);
		gameTable.getControlPanel().cleanLocalData();

		return gameTable;

	}

	public Map<String, CharacterDnd> getSavedCharacter()
	{
		return savedCharacter;
	}

	public void load(String name)
	{
		save();
		actualGameCharacter = savedCharacter.get(name);
		setCheсkChar(true);
		getMediatorWallet().mediatorBreak();
	}

	public void save() 
	{
		Log.add(isCheckChar() + "Сюда смотри");
		Log.add(actualGameCharacter);
		
		if(isCheckChar())
		{
			Log.add("Save Complate");
			savedCharacter.put(actualGameCharacter.getName(), actualGameCharacter);
		}
	}

	public void delete(String name) 
	{
		savedCharacter.remove(name);
	}

}
