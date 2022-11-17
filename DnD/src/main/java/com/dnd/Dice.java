package com.dnd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.Dice.Roll;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;

public class Dice implements Serializable
{
	private static final long serialVersionUID = 1L;

	public enum Roll
	{
		D4, D6, D8, D10, D12, D20, D100, NO_ROLL
	}

	private String name;
	private int buff;
	private List<Roll> combo;

	public Dice(String name, int buff, Roll... combo)
	{
		this.setName(name);
		this.setBuff(buff);
		this.combo = new ArrayList<>();
		setCombo(combo);
	}

	public String execute()
	{
		String answer = this.name + ": ";
		boolean start = true;
		for(Roll roll: this.combo)
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
		}

		if(this.buff < 0)
		{
			answer += " - " + this.buff;
		}
		else if(this.buff > 0);
		{
			answer += " + " + this.buff;
		}


		return answer + " = " + roll();
	}

	public int roll()
	{
		if(this.combo.contains(Roll.NO_ROLL))
		{
			return this.buff;
		}
		else
		{
			int answer = 0;
			for(Roll roll: this.combo)
			{
				answer += roll(roll);
			}
			return answer + this.buff;
		}
	}

	public void setCombo(Roll... combo)
	{ 
		if(this.combo.contains(Roll.NO_ROLL))
		{
			this.combo.clear();
		}
		for(Roll roll: combo)
		{
			this.combo.add(roll);
		}
	}

	public List<Roll> getCombo()
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

	public static int stableHp(CharacterDnd characterDnd)
	{ 


		int start = characterDnd.getClassDnd().getDiceHits();
		int con = characterDnd.;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBuff() {
		return buff;
	}

	public void setBuff(int buff) {
		this.buff = buff;
	}
	
}
