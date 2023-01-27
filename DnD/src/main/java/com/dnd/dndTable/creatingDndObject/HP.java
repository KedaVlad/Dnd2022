package com.dnd.dndTable.creatingDndObject;

import java.io.Serializable;

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
		if(timeHp > 0)
		{
			timeHp  -= value;
			if(timeHp < 0)
			{
				now = now - timeHp;
				timeHp = 0;
				if(now < 0) cknoked = true;
				if(now < max*-1) dead = true;

			}
		}
		else
		{
			now -= value;
			if(now < 0) cknoked = true;
			if(now < max*-1) dead = true;
		}
	}

	public void heal(int value)
	{
		now += value;
		if(now > 0) cknoked = false;
		if(now > max)
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

	public int getNow() {
		return now;
	}

	public int getTimeHp() {
		return timeHp;
	}

	public void setTimeHp(int timeHp) {
		this.timeHp = timeHp;
	}

	public String info() 
	{
		String answer = "HP: " + getNow();
		if(timeHp > 0)
		{
			answer += "(+" + getTimeHp() + ")";
		}
		if(dead)
		{
			answer += "(DEAD)";
		}
		else if(cknoked)
		{
			answer += "(cknoked)";
		}
		return answer + "\n";
	}

}
