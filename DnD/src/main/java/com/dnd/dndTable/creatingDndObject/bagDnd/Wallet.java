package com.dnd.dndTable.creatingDndObject.bagDnd;

import java.io.Serializable;

import com.dnd.Log;
import com.dnd.dndTable.ObjectDnd;

public class Wallet implements Serializable, ObjectDnd{

	private static final long serialVersionUID = 1L;

	private int bronze = 0;//CP
	private int silver = 0;//SP
	private int gold = 0;//GP
	private int plate = 0;//PP


	public void addCoin(String currency, int value)
	{
		if(currency.contains("CP"))
		{
			bronze += value;
		}
		else if(currency.contains("SP"))
		{
			silver += value;
		}
		else if(currency.contains("GP"))
		{
			gold += value;
		}
		else if(currency.contains("PP"))
		{
			plate += value;
		}
		else
		{
			Log.add("ADD COIN ERROR");
		}
	}

	public boolean lostCoin(String currency, int value)
	{
		if(currency.contains("CP"))
		{
			if(bronze >= value)
			{
			bronze -= value;
			return true;
			}
			return false;
		}
		else if(currency.contains("SP"))
		{
			if(silver >= value)
			{
				silver -= value;
			return true;
			}
			return false;
		}
		else if(currency.contains("GP"))
		{
			if(gold >= value)
			{
				gold -= value;
			return true;
			}
			return false;
		}
		else if(currency.contains("PP"))
		{
			if(plate >= value)
			{
				plate -= value;
			return true;
			}
			return false;
		}
		else
		{
			Log.add("ADD COIN ERROR");
			return false;
		}
	}

	@Override
	public long key() {
		// TODO Auto-generated method stub
		return WALLET;
	}
	public String toMenu()
	{
		return "WALLET\nBRONZE(CP):"+bronze+" \nSILVER(SP):"+silver+" \nGOLD(GP):"+gold+" \nPLATE(PP)"+plate;
	}

	public String toString()
	{

		return "WALLET: CP("+bronze+") SP("+silver+") GP("+gold+") PP("+plate+")";

	}

}
