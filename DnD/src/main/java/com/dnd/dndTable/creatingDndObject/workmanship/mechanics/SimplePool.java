package com.dnd.dndTable.creatingDndObject.workmanship.mechanics;

import java.util.ArrayList;
import java.util.List;

public class SimplePool<T> {

	private int activeMaxSize;
	
	private List<T> active = new ArrayList<>();
	
	public void add(T object)
	{
		if(active.size() >= activeMaxSize)
		{
		active.add(object);
		}
	}
	
	public void giveBack(int target)
	{
		this.active.remove(target);
	}

	public List<T> getActive() 
	{
		return active;
	}

	public void setActive(List<T> active)
	{
		this.active = active;
	}

	public int getActiveMaxSize() {
		return activeMaxSize;
	}

	public void setActiveMaxSize(int activeMaxSize) 
	{
		this.activeMaxSize = activeMaxSize;
	}

	
	
	
	
	
}
