package com.dnd.dndTable.creatingDndObject.workmanship.mechanics;

import java.util.ArrayList;
import java.util.List;

public class SimplePool<T> extends Pool<T> {

	private int activeMaxSize;
	
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


	public int getActiveMaxSize() {
		return activeMaxSize;
	}

	public void setActiveMaxSize(int activeMaxSize) 
	{
		this.activeMaxSize = activeMaxSize;
	}

	
	
	
	
	
}
