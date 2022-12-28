package com.dnd.dndTable.rolls;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.Log;
import com.dnd.Names.Stat;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.rolls.Dice.Roll;

public class Dice implements Serializable
{
	private static final long serialVersionUID = 1L;

	
	public enum Roll
	{
		D4, D6, D8, D10, D12, D20, D100, NO_ROLL
	}

	private String name;
	private int buff;
	private Roll[] combo;
	private int[] results;

	public Dice() {};
	
	public Dice(String name, int buff, Roll... combo)
	{
		this.setName(name);
		this.setBuff(buff);
		this.setCombo(combo);
		results = new int[combo.length + 1];
	}

	public String execute()
	{
		String answer = this.name + ": ";

		for(int i = 0; i < this.getCombo().length; i++)
		{
			this.getResults()[i] = roll(this.getCombo()[i]);				
		}
		this.getResults()[this.getResults().length-1] = this.buff;
		answer += summ();
		
		if(results.length > 2 ||
				results.length > 1 &&
				results[0] != 0 &&
				results[1] != 0)
		{
		answer += "(";
		boolean start = true;
		for(int i = 0; i < results.length; i++)
		{
			int target = results[i];
			if(start && (target != 0))
			{
				answer += "" + target;
				start = false;
			}
			else if(target < 0)
			{
				answer += " - " + target*-1;
			}
			else if(target > 0)
			{
				answer += " + " + target;
			}
			else
			{
				continue;
			}
			
		}
		
		return answer + ")";
		}
		return answer;
	}

	public int roll()
	{
		for(int i = 0; i < this.getCombo().length; i++)
		{
			this.getResults()[i] = roll(this.getCombo()[i]);				
		}
		this.getResults()[this.getResults().length-1] = this.buff;
		return summ();
	}

	int summ()
	{
		int answer = 0;
		for(int target: getResults())
		{
			answer += target;
		}
		return answer;
	}

	public Roll[] getCombo()
	{
		return combo;
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
		default:
			break;
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

	public static int stableStartHp(CharacterDnd character)
	{ 
		int start = character.getClassDnd().getFirstHp() + character.getRolls().getValue(Stat.CONSTITUTION.toString());
		
		for(int i = 1; i < character.getClassDnd().getLvl(); i++)
		{
			start += character.getRolls().rollHp(character.getClassDnd(), false);
		}

		return start;
	}

	public static int randomStartHp(CharacterDnd character)
	{

		int start = character.getClassDnd().getFirstHp() + character.getRolls().getValue(Stat.CONSTITUTION.toString());

		for(int i = 1; i < character.getClassDnd().getLvl(); i++)
		{
			start += character.getRolls().rollHp(character.getClassDnd(), true);
		}

		return start;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public int getBuff() 
	{
		return buff;
	}

	public void setBuff(int buff) 
	{
		this.buff = buff;
	}

	public void addBuff(int buff)
	{
		this.buff += buff;
	}

	public int[] getResults() {
		return results;
	}

	
	public void setCombo(Roll... combo) {
		this.combo = combo;
	}

}

