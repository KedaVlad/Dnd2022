package com.dnd.botTable;

import java.util.ArrayList;
import java.util.List;

public class TrashCan {

	private List<Integer> bigСircle = new ArrayList<>();
	private List<Integer> mainСircle = new ArrayList<>();
	private List<Integer> smallСircle = new ArrayList<>();
	
	
	public List<Integer> getAllСircle() 
	{
		List<Integer> all = new ArrayList<>();
		all.addAll(bigСircle);
		all.addAll(mainСircle);
		all.addAll(smallСircle);
		
		return all;
	}
	
	
	public List<Integer> getBigСircle() 
	{
		return bigСircle;
	}
	
	public void toBigСircle(Integer messageId) 
	{
		bigСircle.add(messageId);
	}
	
	public List<Integer> getMainСircle() 
	{
		return mainСircle;
	}
	
	public void toMainСircle(Integer messageId) 
	{
	mainСircle.add(messageId);
	}
	
	public List<Integer> getSmallСircle() 
	{	
		return smallСircle;
	}
	
	public void toSmallСircle(Integer messageId) 
	{
		smallСircle.add(messageId);	
	}
}
