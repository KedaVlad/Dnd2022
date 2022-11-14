package com.dnd;

import java.util.ArrayList;
import java.util.List;

import com.dnd.dndTable.creatingDndObject.CharacterDnd;

public class Dice
{
	private List<Roll> combo;

	public Dice(Roll... combo)
	{
		this.combo = new ArrayList<>();
		setCombo(combo);
	}

	public void setCombo(Roll... combo)
	{
		for(Roll roll: combo)
		{
			this.combo.add(roll);
		}
	}

	public List<Roll> getCombo()
	{
		return combo;
	}

	public static String execute(Dice dice)
	{
		if(dice.combo.size() == 1)
		{ 
			return roll(dice.combo.get(0)) + "";
		}
		else
		{
			String answer = "";
			boolean start = true;
			int finalResult = 0;
			for(Roll roll: dice.combo)
			{
				if(start == true)
				{
					answer = "" + roll(roll);
					start = false;
				}
				else 
				{
					answer += " + " + roll(roll);
				}

				finalResult += roll(roll);

			}

			return answer + " = " + finalResult;
		}
	}

	public static String execute(Dice dice, int... buff)
	{
		if(dice.combo.size() == 1)
		{ 
			String answer = roll(dice.combo.get(0)) + "";
			int finalResult = roll(dice.combo.get(0));
			for(int value: buff)
			{
				if(value < 0)
				{
					answer += " - " + value;
					finalResult += value;
				}
				else
				{
				answer += " + " + value;
				finalResult += value;
				}
			}
			
			return answer + " = " + finalResult;
		}
		else
		{
			String answer = "";
			boolean start = true;
			int finalResult = 0;
			for(Roll roll: dice.combo)
			{
				if(start == true)
				{
					answer = "" + roll(roll);
					start = false;
				}
				else 
				{
					answer += " + " + roll(roll);
				}

				finalResult += roll(roll);

			}
			for(int value: buff)
			{
				if(value < 0)
				{
					answer += " - " + value;
					finalResult += value;
				}
				else
				{
				answer += " + " + value;
				finalResult += value;
				}
			}

			return answer + " = " + finalResult;
		}
	}
	
	public enum Roll
	{
		D4, D6, D8, D10, D12, D20, D100
	}

	public static int roll(Roll roll)
	{
		switch(roll)
		{
		case D4:
			return d4();
		case D6:
			return d6();
		case D8:
			return d8();
		case D10:
			return d10();
		case D12:
			return d12();
		case D20:
			return d20();
		case D100:
			return d100();
		}

		return 0;
	}

	public static int d4()
	{
		return (int) Math.round(Math.random()*3+1);
	}

	public static int d6()
	{
		return (int) Math.round(Math.random()*5+1);
	}

	public static int d8()
	{
		return (int) Math.round(Math.random()*7+1);
	}

	public static int d10()
	{
		return (int) Math.round(Math.random()*9+1);
	}

	public static int d12()
	{
		return (int) Math.round(Math.random()*11+1);
	}

	public static int d20()
	{
		return (int) Math.round(Math.random()*19+1);
	}

	public static int d100()
	{
		return (int) Math.round(Math.random()*99+1);
	}

	public static int randomStat()
	{

		int[] allDice = {d6(), d6(), d6(), d6()};

		int answer = 0;
		int smaller = 0;

		for(int i = 0; i < allDice.length; i++)
		{

			if(i == 0)
			{
				smaller = allDice[i];

			}
			else if(smaller < allDice[i])
			{
				answer += allDice[i];
			}
			else 
			{
				answer += smaller;
				smaller = allDice[i];
			}


		}

		return answer;

	}

	public static int stableHp(CharacterDnd characterDnd)
	{ 


		int start = characterDnd.getClassDnd().getDiceHits();
		int con = (characterDnd.getMyStat().getValue(0, 2) - 10)/2;
		switch(start)
		{
		case 6:
			start = start + con;
			for(int i = 0; i < characterDnd.getClassDnd().getLvl(); i++)
			{
				start += 4 + con;
			}
			break;
		case 8:
			start = start + con;
			for(int i = 0; i < characterDnd.getClassDnd().getLvl(); i++)
			{
				start += 5 + con;
			}
			break;
		case 10:
			start = start + con;
			for(int i = 0; i < characterDnd.getClassDnd().getLvl(); i++)
			{
				start += 6 + con;
			}
			break;
		case 12:
			start = start + con;
			for(int i = 0; i < characterDnd.getClassDnd().getLvl(); i++)
			{
				start += 7 + con;
			}
			break;
		}
		return start;
	}

	public static int randomHp(CharacterDnd characterDnd)
	{

		int start = characterDnd.getClassDnd().getDiceHits();
		int con = (characterDnd.getMyStat().getValue(0, 2) - 10)/2;
		switch(start)
		{
		case 6:
			start = start + con;
			for(int i = 0; i < characterDnd.getClassDnd().getLvl(); i++)
			{
				start += d6() + con;
			}
			break;
		case 8:
			start = start + con;
			for(int i = 0; i < characterDnd.getClassDnd().getLvl(); i++)
			{
				start += d8() + con;
			}
			break;
		case 10:
			start = start + con;
			for(int i = 0; i < characterDnd.getClassDnd().getLvl(); i++)
			{
				start += d10() + con;
			}
			break;
		case 12:
			start = start + con;
			for(int i = 0; i < characterDnd.getClassDnd().getLvl(); i++)
			{
				start += d12() + con;
			}
			break;

		}

		return start;
	}



}
