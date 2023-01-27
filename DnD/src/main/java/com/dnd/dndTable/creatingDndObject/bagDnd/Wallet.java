package com.dnd.dndTable.creatingDndObject.bagDnd;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dnd.Executor;
import com.dnd.botTable.Act;
import com.dnd.botTable.actions.Action;
import com.dnd.botTable.actions.Action.Location;

public class Wallet implements Executor 
{

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
			return false;
		}
	}

	@Override
	public Act execute(Action action)
	{
		int condition = Executor.condition(action);
		switch(condition)
		{
		case 1:
			return walletMenu(action);
		case 2:
			return changeWallet(action);
		case 3:
			return changeCarrencyValue(action);
		default:
			return Act.builder().returnTo(STUFF_B, STUFF_B).build();
		}
	}

	private Act walletMenu(Action action)
	{
		action.setButtons(new String[][] {{"CP", "SP", "GP", "PP"}});
		return Act.builder()
				.name(WALLET_B)
				.text(info()+ "\n Choose currency...")
				.action(action)
				.build();
	}

	private Act changeWallet(Action action)
	{
		action.setMediator();
		return Act.builder()
				.name("changeWallet")
				.text("Earned(+) or spent(-)?(Write)")
				.action(action)
				.build();
	}

	private Act changeCarrencyValue(Action action)
	{
		if(action.getAnswers()[2].matches("\\+(\\d+)"))
		{
			addCoin(action.getAnswers()[1], Integer.parseInt(action.getAnswers()[2].replaceAll("\\+(\\d+)", "$1")));
			return Act.builder().returnTo(STUFF_B, WALLET_B).build();
		}
		else if(action.getAnswers()[2].matches("-(\\d+)"))
		{
			if(lostCoin(action.getAnswers()[1], Integer.parseInt(action.getAnswers()[2].replaceAll("-(\\d+)", "$1"))))
			{
				return Act.builder().returnTo(STUFF_B, WALLET_B).build();
			}
			else
			{
				return Act.builder().name("DeadEnd").text("You don`t have enough coins for that ;(").build();
			}
		}
		else
		{
			return Act.builder().name("DeadEnd").text("So earned(+) or spent(-)? Examples : +10, -10").build();
		}
	}


	public String toString()
	{
		return "WALLET: CP("+bronze+") SP("+silver+") GP("+gold+") PP("+plate+")";
	}

	@Override
	public long key() {
		// TODO Auto-generated method stub
		return STUFF;
	}

	public String info() 
	{
		return "WALLET\nBRONZE(CP):"+bronze+" \nSILVER(SP):"+silver+" \nGOLD(GP):"+gold+" \nPLATE(PP)"+plate;
	}

}
