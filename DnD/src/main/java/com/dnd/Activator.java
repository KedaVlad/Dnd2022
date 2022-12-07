package com.dnd;

import java.util.ArrayList;
import java.util.List;


import com.dnd.dndTable.rolls.actions.HeroAction;

public class Activator {

	private List<HeroAction> pool = new ArrayList<>();
	
	public boolean isEmpty()
	{
		return pool.size() == 0;
	}

	public HeroAction execute(int target)
	{
		HeroAction answer = pool.get(target);
		pool.clear();
		pool.addAll(answer.getNextStep());
		return answer;
	}

	public List<HeroAction> getPool() 
	{
		return pool;
	}

	public void setPool(List<HeroAction> pool) 
	{
		this.pool = pool;
	}
	
}
