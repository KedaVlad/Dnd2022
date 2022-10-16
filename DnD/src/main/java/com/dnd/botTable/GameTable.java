package com.dnd.botTable;


import com.dnd.Dice;
import com.dnd.KeyWallet;
import com.dnd.Log;
import com.dnd.Log.Place;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.classDnd.ClassDnd;
import com.dnd.dndTable.factory.ControlPanel;


public class GameTable implements KeyWallet{

	private long chatId = 0;
	private CharacterDnd actualGameCharacter;
	private boolean chekChar = false;

	private ControlPanel controlPanel = new ControlPanel();
	private MediatorWallet mediatorWallet = new MediatorWallet();
	private TrashCan trashCan = new TrashCan();

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
 	public void createClass()
	{
 		getControlPanel().createClass(actualGameCharacter);
	
	}

	public void createRace()
	{
		getControlPanel().createRace(actualGameCharacter);
		
	}

	public void lvlUp()
	{
		
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void update()
	{
		getControlPanel().save(actualGameCharacter);
		Log.add("update", Place.BOT, Place.GAMETABLE, Place.METHOD);
	}

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
		controlPanel.setMyCharactersDir("" + getChatId());
	}

	public TrashCan getTrashCan() 
	{
		return trashCan;
	}

	public void setTrashCan(TrashCan trashCan) 
	{
		this.trashCan = trashCan;
	}

	public boolean isChekChar() {
		return chekChar;
	}

	public void setChekChar(boolean chekChar) {
		this.chekChar = chekChar;
	}

	public ControlPanel getControlPanel() {
		return controlPanel;
	}

}
