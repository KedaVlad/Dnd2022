package com.dnd.dndTable.creatingDndObject.workmanship.mechanics;

import com.dnd.Names.Refresh;

public class Cells 
{

	private int maxima;
	private int available;
	private Refresh refresh;

	public void use()
	{
		available--;
	}
	
	public void refresh()
	{
		available = maxima;
	}
	
	public Cells(int maxima, Refresh refresh)
	{
		this.maxima = maxima;
		this.available = maxima;
		this.refresh = refresh;
	}
	public int getMaxima() {
		return maxima;
	}

	public void setMaxima(int maxima) {
		this.maxima = maxima;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	public Refresh getRefresh() {
		return refresh;
	}

	public void setRefresh(Refresh refresh) {
		this.refresh = refresh;
	}
	
}
