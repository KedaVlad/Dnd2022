package com.dnd.dndTable.creatingDndObject.characteristic;

import java.io.Serializable;

import com.dnd.dndTable.creatingDndObject.ObjectDnd;
import com.dnd.dndTable.rolls.Dice;
import com.dnd.dndTable.rolls.Dice.Roll;

public class Stat implements Serializable, ObjectDnd
{
	private static final long serialVersionUID = 1L;
	Stats name;
	private int value;
	private int maxValue = 20;
	private int[] elseSourceValue = {0, 0};

	//Dice dice;

	public int getValue()
	{
		int target = value + elseSourceValue[1];
		if(elseSourceValue[0] != 0)
		{
			return elseSourceValue[0];
		}
		else
		{
			return target;
		}
	}

	public int getModificator()
	{
		return (getValue()- 10)/2;
	}

	public Dice getDice()
	{
		return new Dice(name.toString(), getModificator(), Roll.NO_ROLL);
	}

	Stat(Stats name)
	{
		this.name = name;
		this.value = 0;
	}

	void up(int value)
	{
		this.value += value;
	}

	public String SR()
	{
		return getModificator() + " " + name.toString();
	}

	public String toString()
	{
		return value +"(" + getModificator() + ")" + " " +name.toString();
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	
	public String[][] buildStatChangeButtons()
	{

		String[][] base = new String[][] {{"-3","-2","-1","+1","+2","+3"}};
		int max = maxValue - value;
		int min = value - 3;
		if(max > 3) max = 3;
		if(min > 3) min = 3;
		String[][] answer = new String[1][min+max];
		if(answer[0].length == 6)
		{
			return base;
		}
		else if(min < max)
		{
			int j = 0;
			for(int i = 6 - answer[0].length; i < 6; i++)
			{
				answer[0][j] = base[0][i];
				j++;
			}
		}
		else
		{
			for(int i = 0; i < answer[0].length; i ++)
			{
				answer[0][i] = base[0][i];
			}
		}
		return answer;
	}
	
	public enum Stats
	{
		STRENGTH("Strength"), 
		DEXTERITY("Dexterity"), 
		CONSTITUTION("Constitution"), 
		INTELLIGENSE("Intelligense"), 
		WISDOM("Wisdom"), 
		CHARISMA("Charisma");
		
		String name;
		Stats(String name)
		{
			this.name = name;
		}
		
		public String toString()
		{
			return name;
		}
		
	}

}

