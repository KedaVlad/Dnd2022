package com.dnd.botTable;

import com.dnd.KeyWallet;

public class MediatorWallet implements KeyWallet{


	private boolean characterCreateMediator = false;
	private boolean classCreateMediator = false;
	private boolean statMediator = false;

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	boolean checkMediator()
	{

		if(isCharacterCreateMediator()) 
		{
			return true; 
		}
		else if(isClassCreateMediator())
		{
			return true;
		}
		else if(isStatMediator())
		{
			return true;
		}

		return false;
	}

	String findMediator() 
	{

		if(isCharacterCreateMediator()) 
		{
			return characterMediatorKey; 
		}
		else if(isClassCreateMediator())
		{
			return classMediatorKey;
		}
		else if(isStatMediator())
		{
			return statMediatorkey;
		}
		return null;
	}

	void mediatorBreak() 
	{
		setCharacterCreateMediator(false);
		setClassCreateMediator(false);
		setStatMediator(false);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isCharacterCreateMediator() 
	{
		return characterCreateMediator;
	}

	public void setCharacterCreateMediator(boolean characterCreateMediator) 
	{
		this.characterCreateMediator = characterCreateMediator;
	}

	public boolean isClassCreateMediator() 
	{
		return classCreateMediator;
	}

	public void setClassCreateMediator(boolean classCreateMediator) 
	{
		this.classCreateMediator = classCreateMediator;
	}

	public boolean isStatMediator() 
	{
		return statMediator;
	}

	public void setStatMediator(boolean statMediator) 
	{
		this.statMediator = statMediator;
	}	

}
