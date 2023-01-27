package com.dnd.botTable;

import java.util.ArrayList;


public class ArrayActsBuilder
{
	private Act[] pool;
	private String name;
	private String returnTarget;
	
	private ArrayActsBuilder(){}
	
	public static ArrayActsBuilder builder()
	{
		return new ArrayActsBuilder();
	}
	
	public ArrayActsBuilder name(String name)
	{
		this.name = name;
		return this;
	}
	
	public ArrayActsBuilder returnTo(String nameAct)
	{
		this.returnTarget = nameAct;
		return this;
	}
	
	public ArrayActsBuilder pool(Act...actions)
	{
		this.pool = actions;
		return this;
	}
	
	public ArrayActs build()
	{
		ArrayActs answer = new ArrayActs();
		answer.name = this.name;
		answer.pool = this.pool;
		answer.returnTarget = this.returnTarget;
		long[] keys = new long[this.pool.length];
		long baseKey = 100000000;
		for(Act act: this.pool)
		{
			act.name = this.name;
		}
		for(int i = 0; i < this.pool.length; i++)
		{
			keys[i] = baseKey;
			baseKey++;
		}
		answer.keys = keys;
		answer.actCircle = new ArrayList<>();
		return answer;
	}

}

class ArrayActs extends Act
{
	private static final long serialVersionUID = 1L;
	Act[] pool;
	long[] keys;
	
	Act getTarget(long key)
	{
		for(int i = 0; i < keys.length; i++)
		{
			if(keys[i] == key)
			{
				return pool[i];
			}
		}
		return null;
	}
	
	boolean hasAction()
	{
		return pool[0].action != null;
	}
	
	boolean hasMediator()
	{
		return false;
	}
	
	boolean hasReply(String string)
	{
		return pool[0].action != null && pool[0].action.replyContain(string);
	}
	
	boolean hasCloud()
	{
		return false;
	}
}
