package com.dnd.botTable;

import java.util.ArrayList;
import java.util.List;

import com.dnd.dndTable.creatingDndObject.CharacterDnd;


public class GameTable {

	private long chatId;
	private CharacterDnd actualGameCharacter;

	
	private CuttingBoard cuttingBoard = new CuttingBoard();
	private MediatorWallet mediatorWallet = new MediatorWallet();
	private TrashCan trashCan = new TrashCan();

	
	
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

	
}
