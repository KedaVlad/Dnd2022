package com.dnd.botTable;


import com.dnd.Log;
import com.dnd.Log.Place;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.classDnd.ClassDnd;
import com.dnd.dndTable.factory.CharacterFactory;
import com.dnd.dndTable.factory.ClassFactory;
import com.dnd.dndTable.factory.RaceFactory;


public class GameTable {

	private long chatId = 0;
	private CharacterDnd actualGameCharacter;
	private boolean chekChar = false;

	private CuttingBoard cuttingBoard = new CuttingBoard();
	private MediatorWallet mediatorWallet = new MediatorWallet();
	private TrashCan trashCan = new TrashCan();

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
 	public void createClass()
	{
		ClassFactory.create(actualGameCharacter, cuttingBoard.getClassBeck(), cuttingBoard.getClassLvl() , cuttingBoard.getArcherypeBeck());
		update();
	}

	public void createRace()
	{
		RaceFactory.create(actualGameCharacter, cuttingBoard.getRace(), cuttingBoard.getSubRace());
		update();
	}

	public void lvlUp()
	{
		
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void update()
	{
		CharacterFactory.update(actualGameCharacter);
		Log.add("update", Place.BOT, Place.GAMETABLE, Place.METHOD);
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

	public CuttingBoard getCuttingBoard() 
	{
		return cuttingBoard;
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

	public boolean isChekChar() {
		return chekChar;
	}

	public void setChekChar(boolean chekChar) {
		this.chekChar = chekChar;
	}

}
