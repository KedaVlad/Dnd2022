package com.dnd.botTable;

import java.io.Serializable;

import com.dnd.KeyWallet;

class MediatorWallet implements KeyWallet, Serializable{

	private static final long serialVersionUID = -5414437962917485661L;
	private boolean characterCreateMediator = false;
	private boolean classCreateMediator = false;
	private boolean statMediator = false;
	private boolean hpMediator = false;

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
		else if(isHpMediator())
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
			return statMediatorKey;
		}
		else if(isHpMediator())
		{
			return hpMediatorKey;
		}
		return null;
	}

	void mediatorBreak() 
	{
		
		characterCreateMediator = false;
		classCreateMediator = false;
		statMediator = false;
		hpMediator = false;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isCharacterCreateMediator() 
	{
		return characterCreateMediator;
	}

	public void setCharacterCreateMediator(boolean characterCreateMediator) 
	{
		mediatorBreak();
		this.characterCreateMediator = characterCreateMediator;
	}

	public boolean isClassCreateMediator() 
	{
		return classCreateMediator;
	}

	public void setClassCreateMediator(boolean classCreateMediator) 
	{
		mediatorBreak();
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

	public boolean isHpMediator() 
{
		return hpMediator;
	}

	public void setHpMediator(boolean hpMediator) 
{
		this.hpMediator = hpMediator;
	}	

}
