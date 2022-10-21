package com.dnd.localData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.botTable.GameTable;

public class Conector implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	private List<GameTable> session = new ArrayList<>();

	public List<GameTable> getSession() 
	{
		return session;
	}

	public void setSession(List<GameTable> session) 
	{
		this.session = session;
	}
	
}
