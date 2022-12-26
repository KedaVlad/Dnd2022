package com.dnd.dndTable.creatingDndObject;

import java.io.Serializable;

import com.dnd.Log;
import com.dnd.dndTable.Refreshable;

public class HP implements Refreshable, Serializable
{
	private static final long serialVersionUID = 1L;
	private int max = 0;
	private int now = 0;
	private int timeHp = 0;
	boolean cknoked = false;
	boolean dead = false;
	
	public void grow(int value)
	{
		this.max += value;
		this.now = this.max;
	}
	
	public void damaged(int value)
	{
		if(getTimeHp() != 0)
		{
			setTimeHp(getTimeHp() - value);
			if(getTimeHp() < 0)
			{
				now = getNow() - getTimeHp();
				setTimeHp(0);
				if(getNow() < 0)
				{
					cknoked = true;
				}
			}
		}
		else
		{
			now = getNow() - getTimeHp();
			setTimeHp(0);
			if(getNow() < 0) cknoked = true;
			if(getNow() < max*-1) dead = true;
		}
	}
	
	public void heal(int value)
	{
		if(cknoked)
		{
			now = getNow() + value;
			if(getNow() > 0) cknoked = false;
		}
		now = getNow() + value;
		if(getNow() > max)
		{
			now = max;
		}
	}

	@Override
	
	public void refresh(Time time)
	{
		if(time == Time.LONG)
		{
			now = max;
		}
	}
	
	public String toString()
	{
		String answer = "HP: " + getNow();
		if(getTimeHp() > 0)
		{
			return answer + "(+" + getTimeHp() + ")";
		}
		return answer;
	}

	public int getNow() {
		return now;
	}

	public int getTimeHp() {
		return timeHp;
	}

	public void setTimeHp(int timeHp) {
		this.timeHp = timeHp;
	}
	
}
