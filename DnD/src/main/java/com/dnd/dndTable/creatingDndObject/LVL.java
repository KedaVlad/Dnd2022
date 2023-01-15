package com.dnd.dndTable.creatingDndObject;

import java.io.Serializable;

public class LVL implements Serializable
{
	private static final long serialVersionUID = 1L;
	int lvl;
	int experience;
	private int[] expPerLvl = {0, 300, 900, 2700, 
			6500, 14000, 23000, 34000, 
			48000, 64000, 85000, 100000, 
			120000, 140000, 165000, 195000, 
			225000, 265000, 305000, 355000};

	LVL(int lvl)
	{
		this.lvl = lvl;
		this.experience = expPerLvl[lvl-1];
	}

	public boolean addExp(int value)
	{
		this.experience += value;
		for(int i = expPerLvl.length - 1; i > 0; i--)
		{
			if(expPerLvl[i] <= experience)
			{
				if(lvl != i+1)
				{
				this.lvl = i + 1;
				return true;
				}
				return false;
			}
		}
		return false;
	}

	public String info() 
	{
		String answer = "LVL: " + lvl + "(" + experience + "|" + expPerLvl[lvl] + ")";
		return answer + "\n";
	}

}
