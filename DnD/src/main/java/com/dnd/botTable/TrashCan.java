package com.dnd.botTable;

import java.util.ArrayList;
import java.util.List;

import com.dnd.Log;

public class TrashCan {

	public enum Circle 
	{
		SMALL, MAIN, BIG, ALL
	}

	private List<Integer> bigСircle = new ArrayList<>();
	private List<Integer> mainСircle = new ArrayList<>();
	private List<Integer> smallСircle = new ArrayList<>();

	public List<Integer> getCircle(Circle size)
	{
		switch(size)
		{
		case ALL:
			return getAllСircle();
		case BIG:
			return getBigСircle();
		case MAIN:
			return getMainСircle();
		case SMALL:
			return getSmallСircle();
		}
		return null;
	}

	private List<Integer> getAllСircle() 
	{
		List<Integer> all = new ArrayList<>();
		all.addAll(getBigСircle());
		all.addAll(getMainСircle());
		all.addAll(getSmallСircle());
		return all;
	}

	private List<Integer> getBigСircle() 
	{
		List<Integer> big = new ArrayList<>();
		big.addAll(bigСircle);
		bigСircle.clear();
		Log.add("TrashCan BIG");
		return big;
	}

	public void toBigСircle(Integer messageId) 
	{
		bigСircle.add(messageId);
	}

	private List<Integer> getMainСircle() 
	{
		List<Integer> main = new ArrayList<>();
		main.addAll(mainСircle);
		mainСircle.clear();
		Log.add("TrashCan MAIN");
		return main;
	}

	public void toMainСircle(Integer messageId) 
	{
		mainСircle.add(messageId);
	}

	private List<Integer> getSmallСircle() 
	{	
		List<Integer> small = new ArrayList<>();
		small.addAll(smallСircle);	
		smallСircle.clear();
		Log.add("TrashCan SMALL");
		return small;
	}

	public void toSmallСircle(Integer messageId) 
	{
		smallСircle.add(messageId);	
	}

}
